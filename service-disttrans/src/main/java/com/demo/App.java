package com.demo;

import com.demo.datasource.DBConfig1;
import com.demo.datasource.DBConfig2;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 *
 */
@SpringBootApplication
@EnableConfigurationProperties(value = { DBConfig1.class, DBConfig2.class })

@MapperScan(basePackages = { "com.demo.mapper" })

@EnableEurekaClient
@EnableDiscoveryClient
public class App {
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
