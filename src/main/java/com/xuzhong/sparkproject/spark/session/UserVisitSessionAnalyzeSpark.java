package com.xuzhong.sparkproject.spark.session;

import java.util.List;

import org.apache.spark.Accumulator;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.storage.StorageLevel;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.xuzhong.sparkproject.domain.Task;
import com.xuzhong.sparkproject.spark.session.rdd.FilteredSessionid2AggrInfoRDD;
import com.xuzhong.sparkproject.spark.session.rdd.RandomExtractSessionRDD;
import com.xuzhong.sparkproject.spark.session.rdd.SessionId2detailRDD;
import com.xuzhong.sparkproject.spark.session.rdd.Sessionid2AggrInfoRDD;
import com.xuzhong.sparkproject.spark.session.rdd.Top10CategaryRDD;
import com.xuzhong.sparkproject.spark.session.rdd.Top10SessionRDD;
import com.xuzhong.sparkproject.util.Constants;
import com.xuzhong.sparkproject.util.SparkUtils;

import it.unimi.dsi.fastutil.ints.IntList;
import scala.Tuple2;

/**
 * 用户访问session分析Spark作业
 * 
 * 接收用户创建的分析任务，用户可能指定的条件如下：
 * 
 * 1、时间范围：起始日期~结束日期
 * 2、性别：男或女
 * 3、年龄范围
 * 4、职业：多选
 * 5、城市：多选
 * 6、搜索词：多个搜索词，只要某个session中的任何一个action搜索过指定的关键词，那么session就符合条件
 * 7、点击品类：多个品类，只要某个session中的任何一个action点击过某个品类，那么session就符合条件
 * 
 * 我们的spark作业如何接受用户创建的任务？
 * 
 * J2EE平台在接收用户创建任务的请求之后，会将任务信息插入MySQL的task表中，任务参数以JSON格式封装在task_param
 * 字段中
 * 
 * 接着J2EE平台会执行我们的spark-submit shell脚本，并将taskid作为参数传递给spark-submit shell脚本
 * spark-submit shell脚本，在执行时，是可以接收参数的，并且会将接收的参数，传递给Spark作业的main函数
 * 参数就封装在main函数的args数组中
 * 
 * 这是spark本身提供的特性
 * 
 * @author Administrator
 *
 */
@Component
public class UserVisitSessionAnalyzeSpark {
	

	
	public static void run(Task task) {
		System.out.println("=============UserVisitSessionAnalyzeSpark : run=============");
		
		// 构建Spark上下文
		SparkConf conf = new SparkConf()
				.setAppName(Constants.SPARK_APP_NAME_SESSION)
//				.set("spark.default.parallelism", "100")//设置一个Spark Application的并行度
				.set("spark.storage.memoryFraction", "0.3")//reduce端内存占比  默认0.2
				.set("spark.shuffle.file.buffer", "64")//map端内存缓冲  默认32k
				.set("spark.shuffle.consolidateFiles", "true")//合并map端输出文件
				.set("spark.shuffle.memoryFraction", "0.3")    
				.set("spark.reducer.maxSizeInFlight", "24")  
				.set("spark.shuffle.io.maxRetries", "60")  
				.set("spark.shuffle.io.retryWait", "60") 
				.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")//ryo序列化
				.registerKryoClasses(new Class[]{
						CategorySortKey.class,
						IntList.class});
		
		SparkUtils.setMaster(conf);  
		
		/**
		 * 比如，获取top10热门品类功能中，二次排序，自定义了一个Key
		 * 那个key是需要在进行shuffle的时候，进行网络传输的，因此也是要求实现序列化的
		 * 启用Kryo机制以后，就会用Kryo去序列化和反序列化CategorySortKey
		 * 所以这里要求，为了获取最佳性能，注册一下我们自定义的类
		 */
		
		JavaSparkContext sc = new JavaSparkContext(conf);
		SQLContext sqlContext = SparkUtils.getSQLContext(sc.sc());		
		
		
		// 生成模拟测试数据
		//user_visit_action [2018-09-17,73,9e20665ff7d046538aed9c45928f260f,9,2018-09-17 14:42:20,null,46,69,null,null,null,null]
		//user_info[0,user0,name0,2,professional20,city63,female]
		SparkUtils.mockData(sc, sqlContext);
		
		// 首先得查询出来指定的任务，并获取任务的查询参数
		int taskId = task.getTaskId();
		JSONObject taskParam = JSONObject.parseObject(task.getTaskParam());

		
		// 如果要进行session粒度的数据聚合
		// 首先要从user_visit_action表中，查询出来指定日期范围内的行为数据
		//[2018-09-17,73,9e20665ff7d046538aed9c45928f260f,9,2018-09-17 14:42:20,null,46,69,null,null,null,null]
		/**
		 * actionRDD，就是一个公共RDD
		 * 第一，要用ationRDD，获取到一个公共的sessionid为key的PairRDD
		 * 第二，actionRDD，用在了session聚合环节里面
		 * 
		 * sessionid为key的PairRDD，是确定了，在后面要多次使用的
		 * 1、与通过筛选的sessionid进行join，获取通过筛选的session的明细数据
		 * 2、将这个RDD，直接传入aggregateBySession方法，进行session聚合统计
		 * 
		 * 重构完以后，actionRDD，就只在最开始，使用一次，用来生成以sessionid为key的RDD
		 * 
		 */
		JavaRDD<Row> actionRDD = SparkUtils.getActionRDDByDateRange(sqlContext, taskParam);
		//[(9e20665ff7d046538aed9c45928f260f,[2018-09-17,73,9e20665ff7d046538aed9c45928f260f,9,2018-09-17 14:42:20,null,46,69,null,null,null,null])]
		JavaPairRDD<String, Row> sessionid2actionRDD = SparkUtils.getSessionid2ActionRDD(actionRDD);
		
		/**
		 * 持久化，很简单，就是对RDD调用persist()方法，并传入一个持久化级别
		 * 
		 * 如果是persist(StorageLevel.MEMORY_ONLY())，纯内存，无序列化，那么就可以用cache()方法来替代
		 * StorageLevel.MEMORY_ONLY_SER()，第二选择
		 * StorageLevel.MEMORY_AND_DISK()，第三选择
		 * StorageLevel.MEMORY_AND_DISK_SER()，第四选择
		 * StorageLevel.DISK_ONLY()，第五选择
		 * 
		 * 如果内存充足，要使用双副本高可靠机制
		 * 选择后缀带_2的策略
		 * StorageLevel.MEMORY_ONLY_2()
		 * 
		 */
		sessionid2actionRDD = sessionid2actionRDD.persist(StorageLevel.MEMORY_ONLY());
		
		// 首先，可以将行为数据，按照session_id进行groupByKey分组
		// 此时的数据的粒度就是session粒度了，然后呢，可以将session粒度的数据
		// 与用户信息数据，进行join
		// 然后就可以获取到session粒度的数据，同时呢，数据里面还包含了session对应的user的信息
		// 到这里为止，获取的数据是<sessionid,(sessionid,searchKeywords,clickCategoryIds,age,professional,city,sex)>  
		//[(d9784352d65d49858fc84dddee21dbae,sessionid=d9784352d65d49858fc84dddee21dbae|searchKeywords=|clickCategoryIds=0,69|visitLength=217|stepLength=2|startTime=Mon Sep 17 02:19:51 CST 2018|age=44|professional=professional32|city=city38|sex=female)]
		JavaPairRDD<String, String> sessionid2AggrInfoRDD = Sessionid2AggrInfoRDD.aggregateBySession(sqlContext, sessionid2actionRDD);
		
		// 接着，就要针对session粒度的聚合数据，按照使用者指定的筛选参数进行数据过滤
		// 相当于我们自己编写的算子，是要访问外面的任务参数对象的
		// 所以，大家记得我们之前说的，匿名内部类（算子函数），访问外部对象，是要给外部对象使用final修饰的
		// 重构，同时进行过滤和统计
		Accumulator<String> sessionAggrStatAccumulator = sc.accumulator(
				"", new SessionAggrStatAccumulator());
		
		//[(d9784352d65d49858fc84dddee21dbae,sessionid=d9784352d65d49858fc84dddee21dbae|searchKeywords=|clickCategoryIds=0,69|visitLength=217|stepLength=2|startTime=Mon Sep 17 02:19:51 CST 2018|age=44|professional=professional32|city=city38|sex=female)]
		JavaPairRDD<String, String> filteredSessionid2AggrInfoRDD = FilteredSessionid2AggrInfoRDD.filterSessionAndAggrStat(
				sessionid2AggrInfoRDD, taskParam, sessionAggrStatAccumulator);
		//持久化
		filteredSessionid2AggrInfoRDD = filteredSessionid2AggrInfoRDD.persist(StorageLevel.MEMORY_ONLY());
		
		//通过筛选条件的session的访问明细数据RDD
		JavaPairRDD<String, Row> sessionid2detailRDD = SessionId2detailRDD.getSessionId2detailRDD(filteredSessionid2AggrInfoRDD,sessionid2actionRDD);
		//持久化
		sessionid2detailRDD = sessionid2detailRDD.persist(StorageLevel.MEMORY_ONLY());
		/**
		 * 对于Accumulator这种分布式累加计算的变量的使用，有一个重要说明
		 * 从Accumulator中，获取数据，插入数据库的时候，一定要，一定要，是在有某一个action操作以后
		 * 再进行。。。
		 * 如果没有action的话，那么整个程序根本不会运行。。。
		 * 是不是在calculateAndPersisitAggrStat方法之后，运行一个action操作，比如count、take
		 * 不对！！！
		 * 必须把能够触发job执行的操作，放在最终写入MySQL方法之前
		 * 计算出来的结果，在J2EE中，是怎么显示的，是用两张柱状图显示	
		 */
		System.err.println("filteredSessionid2AggrInfoRDD.count======" + filteredSessionid2AggrInfoRDD.count());
		
		RandomExtractSessionRDD.randomExtractSession(sc,taskId,filteredSessionid2AggrInfoRDD,sessionid2detailRDD);
		
		/**
		 * 特别说明
		 * 我们知道，要将上一个功能的session聚合统计数据获取到，就必须是在一个action操作触发job之后
		 * 才能从Accumulator中获取数据，否则是获取不到数据的，因为没有job执行，Accumulator的值为空
		 * 所以，我们在这里，将随机抽取的功能的实现代码，放在session聚合统计功能的最终计算和写库之前
		 * 因为随机抽取功能中，有一个countByKey算子，是action操作，会触发job
		 */
		
		// 计算出各个范围的session占比，并写入MySQL
		RandomExtractSessionRDD.calculateAndPersistAggrStat(sessionAggrStatAccumulator.value(),
				task.getTaskId());
		
		/**
		 * session聚合统计（统计出访问时长和访问步长，各个区间的session数量占总session数量的比例）
		 * 
		 * 如果不进行重构，直接来实现，思路：
		 * 1、actionRDD，映射成<sessionid,Row>的格式
		 * 2、按sessionid聚合，计算出每个session的访问时长和访问步长，生成一个新的RDD
		 * 3、遍历新生成的RDD，将每个session的访问时长和访问步长，去更新自定义Accumulator中的对应的值
		 * 4、使用自定义Accumulator中的统计值，去计算各个区间的比例
		 * 5、将最后计算出来的结果，写入MySQL对应的表中
		 * 
		 * 普通实现思路的问题：
		 * 1、为什么还要用actionRDD，去映射？其实我们之前在session聚合的时候，映射已经做过了。多此一举
		 * 2、是不是一定要，为了session的聚合这个功能，单独去遍历一遍session？其实没有必要，已经有session数据
		 * 		之前过滤session的时候，其实，就相当于，是在遍历session，那么这里就没有必要再过滤一遍了
		 * 
		 * 重构实现思路：
		 * 1、不要去生成任何新的RDD（处理上亿的数据）
		 * 2、不要去单独遍历一遍session的数据（处理上千万的数据）
		 * 3、可以在进行session聚合的时候，就直接计算出来每个session的访问时长和访问步长
		 * 4、在进行过滤的时候，本来就要遍历所有的聚合session信息，此时，就可以在某个session通过筛选条件后
		 * 		将其访问时长和访问步长，累加到自定义的Accumulator上面去
		 * 5、就是两种截然不同的思考方式，和实现方式，在面对上亿，上千万数据的时候，甚至可以节省时间长达
		 * 		半个小时，或者数个小时
		 * 
		 * 开发Spark大型复杂项目的一些经验准则：
		 * 1、尽量少生成RDD
		 * 2、尽量少对RDD进行算子操作，如果有可能，尽量在一个算子里面，实现多个需要做的功能
		 * 3、尽量少对RDD进行shuffle算子操作，比如groupByKey、reduceByKey、sortByKey（map、mapToPair）
		 * 		shuffle操作，会导致大量的磁盘读写，严重降低性能
		 * 		有shuffle的算子，和没有shuffle的算子，甚至性能，会达到几十分钟，甚至数个小时的差别
		 * 		有shfufle的算子，很容易导致数据倾斜，一旦数据倾斜，简直就是性能杀手（完整的解决方案）
		 * 4、无论做什么功能，性能第一
		 * 		在传统的J2EE或者.NET后者PHP，软件/系统/网站开发中，我认为是架构和可维护性，可扩展性的重要
		 * 		程度，远远高于了性能，大量的分布式的架构，设计模式，代码的划分，类的划分（高并发网站除外）
		 * 
		 * 		在大数据项目中，比如MapReduce、Hive、Spark、Storm，我认为性能的重要程度，远远大于一些代码
		 * 		的规范，和设计模式，代码的划分，类的划分；大数据，大数据，最重要的，就是性能
		 * 		主要就是因为大数据以及大数据项目的特点，决定了，大数据的程序和项目的速度，都比较慢
		 * 		如果不优先考虑性能的话，会导致一个大数据处理程序运行时间长度数个小时，甚至数十个小时
		 * 		此时，对于用户体验，简直就是一场灾难
		 * 		
		 * 		所以，推荐大数据项目，在开发和代码的架构中，优先考虑性能；其次考虑功能代码的划分、解耦合
		 * 
		 * 		我们如果采用第一种实现方案，那么其实就是代码划分（解耦合、可维护）优先，设计优先
		 * 		如果采用第二种方案，那么其实就是性能优先
		 * 
		 * 		讲了这么多，其实大家不要以为我是在岔开话题，大家不要觉得项目的课程，就是单纯的项目本身以及
		 * 		代码coding最重要，其实项目，我觉得，最重要的，除了技术本身和项目经验以外；非常重要的一点，就是
		 * 		积累了，处理各种问题的经验
		 * 
		 */
		
		//获取top10热门品类,并写入mysql
		List<Tuple2<CategorySortKey, String>> top10CategaryList = Top10CategaryRDD.getTop10Categary(sessionid2detailRDD,taskId);
		
		//获取top10活跃session，并写入mysql
		Top10SessionRDD.getTop10Session(top10CategaryList,sessionid2detailRDD,taskId,sc);
		
		// 关闭Spark上下文
		sc.close(); 
	}
}