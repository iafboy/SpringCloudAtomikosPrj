package com.demo.datasource;

import java.sql.SQLException;

import javax.sql.DataSource;;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import oracle.jdbc.xa.client.OracleXADataSource;
/**
 * @Configuration 和@MapperScan两个注解
 * DataSource1Config类的两个注解开启，TestMyBatisConfig1类的两个注解关闭，就代表使用单数据库事物
 * TestMyBatisConfig1类的两个注解开启，DataSource1Config类的两个注解关闭，就代表使用分布式数据库事物
  *  因为TestMyBatisConfig1使用了atomikos
 *
 */
@Configuration
// basePackages 最好分开配置 如果放在同一个文件夹可能会报错
@MapperScan(basePackages = "com.demo.db1", sqlSessionTemplateRef = "testSqlSessionTemplate")
public class TestMyBatisConfig1 {
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(TestMyBatisConfig1.class);

	// 配置数据源
	@Primary
	@Bean(name = "dataSource1")
	public DataSource testDataSource(DBConfig1 testConfig) throws SQLException {
		//Atomikos统一管理分布式事务
		AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();

//		Properties p = new Properties();
//		p.setProperty ( "user" , testConfig.getUsername() );
//		p.setProperty ( "password" , testConfig.getPassword() );
//		p.setProperty ( "URL" , testConfig.getUrl() );
//		xaDataSource.setXaProperties ( p );

		//用druidXADataSource方式或者上面的Properties方式都可以
		DruidXADataSource druidXADataSource = new DruidXADataSource();
		LOG.info("----------"+testConfig.getUrl());
		//druidXADataSource.setConnectionProperties("config.decrypt=false;config.decrypt.key="+testConfig.getPublicKey());
		druidXADataSource.setFilters("config,stat");
		druidXADataSource.setUrl(testConfig.getUrl());
		druidXADataSource.setUsername(testConfig.getUsername());
		druidXADataSource.setPassword(testConfig.getPassword());
		
		xaDataSource.setUniqueResourceName("oracle1");
		xaDataSource.setXaDataSource(druidXADataSource);
		xaDataSource.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
		xaDataSource.setMaxLifetime(testConfig.getMaxLifetime());
		xaDataSource.setMinPoolSize(testConfig.getMinPoolSize());
		xaDataSource.setMaxPoolSize(testConfig.getMaxPoolSize());
		xaDataSource.setBorrowConnectionTimeout(testConfig.getBorrowConnectionTimeout());
		xaDataSource.setLoginTimeout(testConfig.getLoginTimeout());
		xaDataSource.setMaintenanceInterval(testConfig.getMaintenanceInterval());
		xaDataSource.setMaxIdleTime(testConfig.getMaxIdleTime());
		xaDataSource.setTestQuery(testConfig.getTestQuery());
		
		LOG.info("分布式事物dataSource1实例化成功");
//		return xaDataSource;
		return xaDataSource;
	}

	@Primary
	@Bean(name = "testSqlSessionFactory")
	public SqlSessionFactory testSqlSessionFactory(@Qualifier("dataSource1") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		return bean.getObject();
	}

	@Primary
	@Bean(name = "testSqlSessionTemplate")
	public SqlSessionTemplate testSqlSessionTemplate(
			@Qualifier("testSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
