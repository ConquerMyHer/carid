package com.ldblock.carid.config.db;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;


/**
 * 配置mybatis
 * @author 老徐
 *
 */
@Configuration
@MapperScan(basePackages="com.ldblock.carid.dao") //使用通用Mapper的MapperScan代替Mybatis自带的,指明basepackage，避免每个类都加上@Mapper，多数据源需要指明这类是与哪个sqlSessionFactory关联
public class MybatisConfig {	
//	1、Autodetect an existing DataSource
//	自动发现一个存在的DataSource，多个源需要明确的指明
//	2、Will create and register an instance of a SqlSessionFactory passing that DataSource as an input using the SqlSessionFactoryBean
//	利用SqlSessionFactoryBean创建并注册SqlSessionFactory
//	3、Will create and register an instance of a SqlSessionTemplate got out of the SqlSessionFactory
//	创建并注册SqlSessionTemplate
//	4、Auto-scan your mappers, link them to the SqlSessionTemplate and register them to Spring context so they can be injected into your beans
//	自动扫描Mappers，并注册到Spring上下文环境方便程序的注入使用
	
	//mybatis-plug的配置
	@Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor pageInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        pageInterceptor.setOptimizeJoin(true);//优化部分leftJoin
        //pageInterceptor.setMaxLimit(500L);//设置最大返回限制,默认是-1不限制
        //pageInterceptor.setOverflow(false);//设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        interceptor.addInnerInterceptor(pageInterceptor);
        return interceptor;
    }
	
}
