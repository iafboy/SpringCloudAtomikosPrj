
package com.demo.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import com.demo.db1.service.DB1_UserService;
import com.demo.db2.service.DB2_UserService;
import com.demo.service.IndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
public class IndexController {
	private static Logger log = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private DB1_UserService userService1;
	@Autowired
	private DB2_UserService userService2;
	@Autowired
	private IndexService indexService;
	
	//想查看数据源，可以这么注解
	@Resource
	@Qualifier("dataSource1")
	private DataSource dataSource1;

	@RequestMapping("/insertDB1")
	public String insertTest001(String name, Integer age) {
		// userMapperTest01.insert(name, age);
		userService1.insertDB1(name, age);
		return "success insertDB1";
	}

	@RequestMapping("/insertDB2")
	public String insertTest002(String name, Integer age) {
		userService2.insertDB2(name, age);
		return "success insertDB2";
	}

	/**
	 * atomikos效果：分布式事物。两个数据库都插入值
	 * 
	 * @param name
	 * @param age
	 * @return
	 */
	@RequestMapping("/insertTwoDBs")
	public String insertTwoDBs(String name, Integer age) {
		indexService.insertTwoDBs(name, age);
		return "success insertTwoDBs";
	}

	/**
	 * atomikos效果：分布式事物。 演示发生异常分布式事物回滚
	 * 
	 * @param name
	 * @param age
	 * @return
	 */
	@RequestMapping("/insertTwoDBsWithError")
	public String insertTwoDBsWithError(String name, Integer age) {
		indexService.insertTwoDBsWithError(name, age);
		return "success insertTwoDBs";
	}

	/**
	 * atomikos效果：分布式事物。 演示发生异常分布式事物回滚
	 *  直接调用mapper方式
	 * @param name
	 * @param age
	 * @return
	 */
	@RequestMapping("/insertTwoDBsUseMapperWithError")
	public String insertTwoDBsUseMapperWithError(String name, Integer age) {
		indexService.insertTwoDBsUseMapperWithError(name, age);
		return "success insertTwoDBsUseMapperWithError";
	}

	/**
	 * 获取两个数据库的所有数据
	 * @return
	 */
	@RequestMapping("/queryAll")
	public List queryAll() {
		List list = indexService.queryAll();
		list.add(new Date().toLocaleString()); //加上时间戳，方便postman观察结果
		return list;
	}

	/**
	 *  删除两个数据库的所有数据
	 * @return
	 */
	@RequestMapping("/deleteAll")
	public String deleteAll() {
		indexService.deleteAll();
		return "success delete all";
	}

}
