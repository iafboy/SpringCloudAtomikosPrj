package com.demo.datasource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
/**
 * @Configuration 和@MapperScan两个注解
 * DataSource1Config类的两个注解开启，TestMyBatisConfig1类的两个注解关闭，就代表使用单数据库事物
 * TestMyBatisConfig1类的两个注解开启，DataSource1Config类的两个注解关闭，就代表使用分布式数据库事物
  *  因为TestMyBatisConfig1使用了atomikos
 *
 */

public class DataSource1Config {

	public DataSource testDataSource() {
		return DataSourceBuilder.create().build();
	}

//	@Bean(name = "test1SqlSessionFactory")
//	@Primary
	public SqlSessionFactory testSqlSessionFactory(@Qualifier("dataSource1") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);

		/*
		 * bean.setMapperLocations( new
		 * PathMatchingResourcePatternResolver().getResources(
		 * "classpath:mybatis/mapper/test1/*.xml"));
		 */
		return bean.getObject();
	}

	@Bean(name = "test1TransactionManager")
	@Primary
	public DataSourceTransactionManager testTransactionManager(@Qualifier("dataSource1") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = "test1SqlSessionTemplate")
	@Primary
	public SqlSessionTemplate testSqlSessionTemplate(
			@Qualifier("test1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}
