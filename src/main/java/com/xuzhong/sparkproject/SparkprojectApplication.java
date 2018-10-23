package com.xuzhong.sparkproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.xuzhong.sparkproject.spark.SparkController;


@SpringBootApplication
public class SparkprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SparkprojectApplication.class, args);
		
		try {
			int taskId = Integer.valueOf(args[0]);
			SparkController.doTask(taskId);
		} catch (NumberFormatException e) {
			System.out.println("======参数输入异常！=======");
		}
	}
}
