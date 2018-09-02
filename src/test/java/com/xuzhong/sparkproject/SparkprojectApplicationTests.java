package com.xuzhong.sparkproject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.xuzhong.sparkproject.dao.TaskMapper;
import com.xuzhong.sparkproject.domain.Task;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SparkprojectApplication.class)
@WebAppConfiguration
@TestPropertySource("classpath:config.properties")
public class SparkprojectApplicationTests {


	
	
	/**
	 * 操作数据库
	 */
	@Autowired
	private TaskMapper taskMapper;
	@Test
	public void daoTest() {
		Task task = taskMapper.selectByPrimaryKey(1);
		System.err.println(task.getTaskName());
	}
	
	
	/**
	 * 读取配置文件
	 * @ConfigurationProperties(prefix = "com.zyd")
		PropertySource默认取application.properties
		@PropertySource(value = "config.properties")
	 */
	@Value("${jdbc.driver}")
	private String value;
	@Test
	public void getPropertiesTest() {
		
		System.out.println("value="+value);
	}
}
