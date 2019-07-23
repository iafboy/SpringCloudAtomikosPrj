
package com.demo.db2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.db2.dao.DB2_UserMapper;
import com.demo.entity.Users;




@Service
public class DB2_UserService {
	@Autowired
	private DB2_UserMapper userMapperDB2;

	// @Transactional(rollbackFor = { Exception.class })
//	@Transactional(value = "test2TransactionManager")
	@Transactional
	public String insertDB2(String name, Integer age) {
		userMapperDB2.insert(name, age);
		 int i =1/0;
		return "success";
	}
	
	@Transactional
	public void insert2DB2(String name, Integer age) {
		userMapperDB2.insert(name, age);
		int i = 3 / 0; // error 3，配合insertTwoDBsWithError分布式事物回滚演示
	}

	public List<Users> queryAll() {
		List<Users> list = userMapperDB2.queryAll();
		return list;
	}
 
 
}
