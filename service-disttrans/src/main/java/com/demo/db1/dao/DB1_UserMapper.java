package com.demo.db1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.demo.entity.Users;

public interface DB1_UserMapper {
	@Select("SELECT * FROM USERS WHERE NAME = #{name}")
	Users findByName(@Param("name") String name);

	@Insert("INSERT INTO USERS(NAME, AGE) VALUES(#{name}, #{age})")
	int insert(@Param("name") String name, @Param("age") Integer age);

	@Delete("Delete from USERS")
	void deleteAll();

	@Select("select 'oracle1' as id,t.* from USERS t")
	List<Users> queryAll();
}
