package application.dao.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;

import application.cache.AppContext;

public final class DbUtil {
	private Logger log = LoggerFactory.getLogger(DbUtil.class);
	private DataSource dbSource;
	private String url;
	private String driver;
	private String username;
	private String password;

	private DbUtil() {
		url = AppContext.getDbConfig("odbc.conn-name", "jdbc:odbc:IDataBase");
		driver = AppContext.getDbConfig("odbc.driver", "sun.jdbc.odbc.JdbcOdbcDriver");
		username = AppContext.getDbConfig("odbc.username");
		password = AppContext.getDbConfig("odbc.password");
		DruidDataSource db = new DruidDataSource();
		db.setUrl(url);
		db.setDriverClassName(driver);
		db.setUsername(username);
		db.setPassword(password);
		try {
			db.init();
			dbSource = db;
			log.info("数据源加载成功,driver: {} ,url: {} ,username: {}", driver, url, username);
		} catch (SQLException e) {
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

	public static void main(String[] args) throws SQLException {
		try (Connection conn = getInstance().getSource().getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select  * from jkcolumn ");
				ResultSet rs = pstmt.executeQuery();) {
			while (rs.next()) {
				System.out.println(rs.getString(1));
			}
		}
	}
}
