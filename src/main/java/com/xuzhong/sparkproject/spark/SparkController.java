package com.xuzhong.sparkproject.spark;

import java.util.Date;

import com.xuzhong.sparkproject.conf.ConfigurationManager;
import com.xuzhong.sparkproject.domain.Task;
import com.xuzhong.sparkproject.service.TaskService;
import com.xuzhong.sparkproject.spark.page.PageOneStepConvertRateSpark;
import com.xuzhong.sparkproject.spark.session.UserVisitSessionAnalyzeSpark;
import com.xuzhong.sparkproject.util.ApplicationContextUtils;
import com.xuzhong.sparkproject.util.Constants;

public class SparkController {
	
	private static String session = ConfigurationManager.getProperty(Constants.SPARK_TASKNAME_SESSION);
	private static String page = ConfigurationManager.getProperty(Constants.SPARK_TASKNAME_PAGE);
	
	public static void doTask(int taskId) {
		TaskService taskService = ApplicationContextUtils.getBean(TaskService.class);
		Task task = taskService.selectByPrimaryKey(taskId);
		if(task == null) {
			System.out.println(new Date() + ": cannot find this task with id [" + taskId + "].");  
			return;
		}
		String taskName = task.getTaskName();
		
		if(taskName.equals(session)){
			UserVisitSessionAnalyzeSpark.run(task);
		}else if(taskName.equals(page)){
			PageOneStepConvertRateSpark.run(task);
		}
	}

}
