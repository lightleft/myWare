package com.bhl.dams.damsactiviti.configs;

/**
 * 读取数据库配置文件
 * 
 * @author chenj
 *
 */
public class DataSourceProperty {

	public DataSourceProperty() {
		super();
	}

	// 对应配置文件里的配置键
	public final static String PREFIX = "datasource";

	private String driver;
	private String url;
	private String username;
	private String password;

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}