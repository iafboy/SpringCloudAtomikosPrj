
package com.demo.db1.service;

import java.util.List;

import com.demo.db1.dao.DB1_UserMapper;
import com.demo.db2.dao.DB2_UserMapper;
import com.demo.db2.service.DB2_UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.db2.dao.DB2_UserMapper;
import com.demo.db2.service.DB2_UserService;
import com.demo.entity.Users;




@Service
public class DB1_UserService {
	@Autowired
	private DB1_UserMapper db1UserMapper;
	@Autowired
	private DB2_UserMapper db2UserMapper;
	@Autowired
	 private DB2_UserService userService2;
	
	@Transactional
	public String insertDB1(String name, Integer age) {
		db1UserMapper.insert(name, age);//test1 //
		//不同数据库。test1,test2
		//userService2.insertDB2(name, age);//test2  
		db2UserMapper.insert(name, age);//test2   
		
//		int i = 1 / 0;//
		return "success";
	}

	@Transactional
	public void insert2DB1(String name, Integer age) {
		db1UserMapper.insert(name, age);
//		int i = 2 / 0; // error 2，配合insertTwoDBsWithError分布式事物回滚演示
	}

	public List<Users> queryAll() {
		List<Users> list = db1UserMapper.queryAll();
		return list;
	}
	
	
}
