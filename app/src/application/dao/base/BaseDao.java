package application.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.dao.util.DbUtil;
import application.dao.util.RsHandler;

public abstract class BaseDao {
	private DbUtil du = DbUtil.getInstance();
	private Logger log = LoggerFactory.getLogger(this.getClass());

	public int getCount(String sql, Object... objs) {
		int c = -1;
		try (Connection conn = du.getConn();
				PreparedStatement ps = getPs(conn, sql, objs);
				ResultSet rs = ps.executeQuery();) {
			if (rs.next()) {
				c = rs.getInt(1);
			}
			log.info("search success! sql ---> {}", sql);
		} catch (SQLException e) {
			log.error("search error! error ---> {}", e.getMessage());
		}
		return c;
	}

	public <T> T searchForSql(String sql, RsHandler<T> handler, Object... objs) {
		T t = null;
		try (Connection conn = du.getConn();
				PreparedStatement ps = getPs(conn, sql, objs);
				ResultSet rs = ps.executeQuery();) {
			t = handler.handler(rs);
			log.info("search success! sql ---> {}", sql);
		} catch (SQLException e) {
			log.error("search error! error ---> {}", e.getMessage());
		}
		return t;
	}

	private PreparedStatement getPs(Connection conn, String sql, Object... objs) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(sql);
		setParams(ps, objs);
		return ps;
	}

	public int update(String sql, Object... objs) {
		int result = -1;
		try (Connection conn = du.getConn(); PreparedStatement ps = getPs(conn, sql, objs);) {
			result = ps.executeUpdate();
			log.info("search success! sql ---> {}", sql);
		} catch (SQLException e) {
			log.error("search error! error ---> {}", e.getMessage());
		}
		return result;
	}

	private PreparedStatement setParams(PreparedStatement ps, Object... objs) throws SQLException {
		if (objs == null || objs.length < 1) {
			return ps;
		}
		for (int i = 0; i < objs.length; i++) {
			ps.setObject((i + 1), objs[i]);
		}
		return ps;
	}

}
