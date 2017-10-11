package com.bhl.dams.damsactiviti.configs;

import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

@EnableTransactionManagement
@Configuration
@MapperScan("com.bhl.dams.damsactiviti.dao")
public class DataSourceConfigurer implements TransactionManagementConfigurer {

	private DataSourceProperty dataSourceProperty;

	@Bean
	@ConfigurationProperties(prefix = DataSourceProperty.PREFIX, ignoreUnknownFields = false)
	public DataSourceProperty dataSourceProperty() {
		return new DataSourceProperty();
	}

	@Primary // 默认数据源
	@Bean(name = "dataSource", destroyMethod = "close", initMethod = "init")
	public DataSource Construction() throws SQLException {
		DruidDataSource dataSource = new DruidDataSource();

		// 不这么配置 读取不到配置类 一直为null
		this.dataSourceProperty = dataSourceProperty();
		dataSource.setUrl(dataSourceProperty.getUrl());
		dataSource.setUsername(dataSourceProperty.getUsername());
		dataSource.setPassword(dataSourceProperty.getPassword());
		dataSource.setDriverClassName(dataSourceProperty.getDriver());
		// 配置最大连接
		dataSource.setMaxActive(20);
		// 配置初始连接
		dataSource.setInitialSize(1);
		// 配置最小连接
		dataSource.setMinIdle(1);
		// 连接等待超时时间
		dataSource.setMaxWait(60000);
		// 间隔多久进行检测,关闭空闲连接
		dataSource.setTimeBetweenEvictionRunsMillis(60000);
		// 一个连接最小生存时间
		dataSource.setMinEvictableIdleTimeMillis(300000);
		// 用来检测是否有效的sql
		dataSource.setValidationQuery("select 'x' FROM DUAL");
		dataSource.setTestWhileIdle(true);
		dataSource.setTestOnBorrow(false);
		dataSource.setTestOnReturn(false);
		// 打开PSCache,并指定每个连接的PSCache大小
		dataSource.setPoolPreparedStatements(true);
		dataSource.setMaxOpenPreparedStatements(20);
		// 配置sql监控的filter
		dataSource.setFilters("stat,wall,log4j");
		try {
			dataSource.init();
		} catch (SQLException e) {
			throw new RuntimeException("druid datasource init fail");
		}
		return dataSource;
	}
	@Bean
	public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mybatis/*.xml"));
		return sqlSessionFactoryBean.getObject();
	}
	
	// 配置 servlet和filter
	@Bean
	public ServletRegistrationBean druidServlet() {
		ServletRegistrationBean reg = new ServletRegistrationBean();
		reg.setServlet(new StatViewServlet());
		reg.addUrlMappings("/druid/*");
		reg.addInitParameter("loginUsername", "xiao");
		reg.addInitParameter("loginPassword", "xiang");
		return reg;
	}

	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new WebStatFilter());
		filterRegistrationBean.addUrlPatterns("/*");
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
		return filterRegistrationBean;
	}

	// 创建事务管理器1
	@Bean(name = "txManager")
	public PlatformTransactionManager txManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Resource(name = "txManager")
	private PlatformTransactionManager txManager;

	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return txManager;
	}

}