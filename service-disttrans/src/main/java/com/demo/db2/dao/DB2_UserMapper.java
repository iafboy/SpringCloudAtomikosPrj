package com.demo.db2.dao;

import java.util.List;

import com.demo.entity.Users;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


//test1 DB
public interface DB2_UserMapper {
	@Select("SELECT * FROM USERS WHERE NAME = #{name}")
	Users findByName(@Param("name") String name);

	@Insert("INSERT INTO USERS(NAME, AGE) VALUES(#{name}, #{age})")
	int insert(@Param("name") String name, @Param("age") Integer age);

	/**
	 * 用于演示插入数据库异常的情况
	 */
	@Insert("INSERT INTO not_exists_table_USERS(NAME, AGE) VALUES(#{name}, #{age})")
	int insertNotExistsTable(@Param("name") String name, @Param("age") Integer age);
	
	@Delete("Delete from USERS")
	void deleteAll();

	@Select("select 'oracle2' as id,t.* from USERS t")
	List<Users> queryAll();
}
