package com.demo.service;

import java.util.ArrayList;
import java.util.List;

import com.demo.db1.dao.DB1_UserMapper;
import com.demo.db1.service.DB1_UserService;
import com.demo.db2.dao.DB2_UserMapper;
import com.demo.db2.service.DB2_UserService;
import com.demo.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class IndexService {
	@Autowired
	private DB1_UserMapper db1UserMapper;
	@Autowired
	private DB2_UserMapper db2UserMapper;
	@Autowired
	private DB1_UserService db1UserService;
	@Autowired
	private DB2_UserService db2UserService;
	/**
	 *  atomikos效果：分布式事物。两个数据库都插入值
	 * @return
	 */
	@Transactional
	public void insertTwoDBs(String name, Integer age) {
		db1UserMapper.insert(name, age);
		db2UserMapper.insert(name, age);
	}

	@Transactional
	public void deleteAll() {
		db1UserMapper.deleteAll();
		//不同数据库。test1,test2
		//userService2.insertDB2(name, age);
		db2UserMapper.deleteAll();//test2   
//		int i = 1 / 0;//
	}

	/**
	 * atomikos效果：分布式事物。
	  *  演示发生异常分布式事物回滚
	  *  这里无论error 1、2、3，任何一处发生异常，分布式事物都会回滚
	 */
	@Transactional //(rollbackFor = { Exception.class })
	public void insertTwoDBsWithError(String name, Integer age)  {
		db1UserService.insert2DB1(name, age);
		db2UserService.insert2DB2(name, age);
		//int i = 1 / 0; // error 1
	}	
	
	/**
	 * atomikos效果：分布式事物。
	  *  演示发生异常分布式事物回滚
	  *  这里无论error 1、2、3，任何一处发生异常，分布式事物都会回滚
	  *  此方法效果等同于insertTwoDBsWithError
	 */
	@Transactional
	public void insertTwoDBsUseMapperWithError(String name, Integer age) {
		db1UserMapper.insert(name, age);
		db2UserMapper.insert(name, age);
		db2UserMapper.insertNotExistsTable(name, age);
	}

	public List queryAll() {
		List all = new ArrayList();
		
		List<Users> list1 = db1UserService.queryAll();
		if(CollectionUtils.isEmpty(list1)) {
			all.add("db1 没有任何数据！");
		}else {
			all.addAll(list1);
		}
		
		List<Users> list2 = db2UserService.queryAll();
		if(CollectionUtils.isEmpty(list2)) {
			all.add("db2 没有任何数据！");
		}else {
			all.addAll(list2);
		}
		
		return all;
	}
	
}
