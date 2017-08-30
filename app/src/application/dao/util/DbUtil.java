package application.dao.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import application.cache.AppContext;

public final class DbUtil {
	private Logger log = LoggerFactory.getLogger(DbUtil.class);
	private DataSource dbSource;
	private String url;
	private String driver;
	private String username;

	private DbUtil() {
		url = AppContext.getDbConfig("odbc.conn-name", "jdbc:odbc:IDataBase");
		driver = AppContext.getDbConfig("odbc.driver", "sun.jdbc.odbc.JdbcOdbcDriver");
		username = AppContext.getDbConfig("odbc.username");
		try {
			DruidDataSource db = (DruidDataSource) DruidDataSourceFactory.createDataSource(AppContext.getDbConfigs());
			dbSource = db;
			log.info("数据源加载完成,driver: {} ,url: {} ,username: {}", driver, url, username);
		} catch (Exception e) {
			log.error("数据源加载失败,异常信息:{}", e.getMessage());
		}

	}

	private static class DbHelper {
		private static final DbUtil db = new DbUtil();
	}

	public static final DbUtil getInstance() {
		return DbHelper.db;
	}

	public Connection getConn() throws SQLException {
		return dbSource.getConnection();
	}

	public DataSource getSource() {
		return dbSource;
	}
}
