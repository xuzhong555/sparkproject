package com.xuzhong.sparkproject.spark;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.hadoop.hive.ql.parse.HiveParser.rowFormat_return;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xuzhong.sparkproject.SparkprojectApplication;

import scala.Tuple2;

@SuppressWarnings("all")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SparkprojectApplication.class)
public class sparkTest implements Serializable {

	private String file = "C://Users//xz//Downloads//ceshi.txt";
	private String file1 = "C://Users//xz//Downloads//ceshi1.txt";
	
//	private SparkConf conf = new SparkConf().setAppName("Spakr").setMaster("local");
//	private JavaSparkContext sc = new JavaSparkContext(conf);
//	private SQLContext sqlContext = new SQLContext(sc);

	
	@Test
	/**
	 * 数据aa 98 bb 89 cc 77 aa 89 aa 67 cc 99 bb 78 bb 87 cc 67 aa 88
	 * 1 按key分组   2分组后按value排序  3 取出排序后的前两个
	 */
	public void Testsort() {
		SparkConf conf = new SparkConf().setAppName("Spakr").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		SQLContext sqlContext = new SQLContext(sc);
		
		//读取文件
		JavaRDD<String> textFile = sc.textFile(file1);
		//按空格分开
		JavaRDD<String[]> textFileRDD = textFile.map(line -> line.split(" "));
		
		//1 按key分组
		 JavaPairRDD<String, Iterable<String>> groupByKeyRDD = textFileRDD.mapToPair(row -> {
			 return new Tuple2<String, String>(row[0], row[1]);
		 }).groupByKey();
		 
		 //2 按value降序
		 JavaRDD<Tuple2<String, List>> sortByValue = groupByKeyRDD.map(v1 -> {
			List<String> r3 = new ArrayList<>();
			String r1 = v1._1;
			List<String> r2 = copyIterator(v1._2.iterator()).subList(0, 2);
			r3.addAll(r2);
			r3.sort(new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					return o1.compareTo(o2);
				}
			});
			return new Tuple2<String, List>(r1, r3);
		 });
		 
		System.err.println(sortByValue.collect());
		
		sc.close();
		
	}
	
	public static <T> List<T> copyIterator(Iterator<T> iter) {
	    List<T> copy = new ArrayList<T>();
	    while (iter.hasNext())
	        copy.add(iter.next());
	    return copy;
	}
	
	
	@Test
	public void Test1() {
		SparkConf conf = new SparkConf().setAppName("Spakr").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		SQLContext sqlContext = new SQLContext(sc);
		List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
		JavaRDD<Integer> distData = sc.parallelize(data);
		List<Integer> collect = distData.map(n -> n * n).collect();
		for (Integer integer : collect) {
			System.err.println(integer);
		}
		sc.close();
	}
	
	@Test
	public void TextSearchTest() {
		SparkConf conf = new SparkConf().setAppName("Spakr").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		SQLContext sqlContext = new SQLContext(sc);
		// Creates a DataFrame having a single column named "line"
		JavaRDD<String> textFile = sc.textFile(file);
		JavaRDD<Row> rowRDD = textFile.map(RowFactory::create);
		
		List<StructField> fields = Arrays.asList(
		  DataTypes.createStructField("line", DataTypes.StringType, true));
		
		StructType schema = DataTypes.createStructType(fields);
		DataFrame df = sqlContext.createDataFrame(rowRDD, schema);

		DataFrame errors = df.filter(df.col("line").like("%ERROR%"));
		// Counts all the errors
		long count2 = errors.count();
		// Counts errors mentioning MySQL
		long count = errors.filter(df.col("line").like("%MySQL%")).count();
		// Fetches the MySQL errors as an array of strings
		Row[] collect = errors.filter(df.col("line").like("%MySQL%")).collect();
		System.err.println("errors count"+count2);
		System.err.println("%MySQL% count"+count);
		System.err.println("%MySQL% collect"+collect.toString());
		
		sc.close();
	}
	
	
	@Test
	public void PiEstimationTest() {
		SparkConf conf = new SparkConf().setAppName("Spakr").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		SQLContext sqlContext = new SQLContext(sc);
		
		List<Integer> list = new ArrayList<>(1000);
		for (int i = 0; i < 1000; i++) {
		  list.add(i);
		}

		long count = sc.parallelize(list).filter(i -> {
		  double x = Math.random();
		  double y = Math.random();
		  return x*x + y*y < 1;
		}).count();
		System.err.println("Pi is roughly " + 4.0 * count / 1000);
		
		sc.close();
		
	}
	
	
	@Test
	public void wordcountTest() {
		SparkConf conf = new SparkConf().setAppName("Spakr").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		SQLContext sqlContext = new SQLContext(sc);

		JavaRDD<String> textFile = sc.textFile(file);
		System.err.println("textFile count:" + textFile.count());
		
		textFile.flatMap(x -> {
				return Arrays.asList(x.split(" "));
			}) 
			.mapToPair(x -> {
				return new Tuple2<>(x,1);})
			.reduceByKey((x,y) ->{
				return x+y;})
			.foreach(x ->
			{
				System.out.println(x._1+"出现了"+x._2);
			});
		
		 sc.close();
		
//		JavaRDD<String> words = textFile.flatMap(new FlatMapFunction<String, String>() {
//
//			public Iterable<String> call(String line) throws Exception {
//				System.err.println("FlatMapFunction:Iterable: " + Arrays.asList(line.split(" ")));
//				return Arrays.asList(line.split(" "));
//			}
//		});
//		JavaPairRDD<String, Integer> pairs = words.mapToPair(new PairFunction<String, String, Integer>() {
//
//            public Tuple2<String, Integer> call(String word) throws Exception {
//            	System.err.println("PairFunction:Tuple2: " + new Tuple2<String,Integer>(word,1));
//                return new Tuple2<String,Integer>(word,1);
//            }
//        });
//
//        JavaPairRDD<String, Integer> wordsCount = pairs.reduceByKey(new Function2<Integer, Integer, Integer>() {
//
//            public Integer call(Integer v1, Integer v2) throws Exception {
//                System.err.println("Function2:call: " + v1 + v2);
//                return v1 + v2;
//            }
//        });
//
//        wordsCount.foreach(new VoidFunction<Tuple2<String,Integer>>() {
//
//            public void call(Tuple2<String, Integer> pairs) throws Exception {
//                System.err.println("wordsCount: " + pairs._1+":"+pairs._2);
//            }
//        });
//
//        sc.close();
	}

}
