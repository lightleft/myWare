package cn.bhl.xlsp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cn.bhl.xlsp.parse.ParseService;
import cn.bhl.xlsp.parse.SQLParse;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		SQLParse sp = new SQLParse(new ParseService() {
			private App app = new App();
			@Override
			public void run(String sql, Map<String, String> p, Map<String, String> params) {
				Map<String, Object> getP = app.get(sql, p);
				for (Entry<String, Object> entry : getP.entrySet()) {
					params.put(entry.getKey(), entry.getValue() == null ? "" : entry.getValue().toString());
				}
			}
		});
		sp.fill("tem-jjwj-final1.xlsx", "D:\\test", null);
	}

	public Map<String, Object> get(String sql, Map<String, String> p) {
		return find(sql,p);
	}

	private String username = "root";
	private String password = "root";
	private String url = "jdbc:mysql://127.0.0.1:3306/springboot";
	private String driverName = "com.mysql.jdbc.Driver";

	public App() {
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private Connection getConn() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public Map<String, Object> find(String sql, Map<String, String> p) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, Object> d = null;
		try {
			conn = getConn();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData rd = rs.getMetaData();
			if (rs.next()) {
				int l = rd.getColumnCount();
				d = new HashMap<>();
				for (int i = 0; i < l; i++) {
					String key = rd.getColumnLabel(i+1);
					d.put(key, rs.getObject(key));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, ps, conn);
		}
		return d;
	}

	private void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) {
		try {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
