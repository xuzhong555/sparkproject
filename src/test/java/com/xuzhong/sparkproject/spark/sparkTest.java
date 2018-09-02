package com.xuzhong.sparkproject.spark;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
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
import org.springframework.test.context.web.WebAppConfiguration;

import com.xuzhong.sparkproject.SparkprojectApplication;

import scala.Tuple2;

@SuppressWarnings("all")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SparkprojectApplication.class)
public class sparkTest implements Serializable {

	private String file = "C://Users//xz//Downloads//ceshi.txt";
	
	private SparkConf conf = new SparkConf().setAppName("Spakr").setMaster("local");
	private JavaSparkContext sc = new JavaSparkContext(conf);
	private SQLContext sqlContext = new SQLContext(sc);
	
	@Test
	public void TextSearchTest() {
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
	}
	
	
	@Test
	public void PiEstimationTest() {
		
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
		
	}
	
	
	@Test
	public void wordcountTest() {

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
//                // TODO Auto-generated method stub
//                System.err.println("wordsCount: " + pairs._1+":"+pairs._2);
//            }
//        });
//
//        sc.close();
	}

}
