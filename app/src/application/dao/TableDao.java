package application.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.anno.RegistAnno;
import application.dao.base.BaseDao;
import application.dao.util.RsHandler;
import application.model.TableModel;

@RegistAnno("tableDao")
public class TableDao extends BaseDao {

	public List<TableModel> getTableAll() {
		List<TableModel> result = null;
		String sql = "select t.db_id dbId,t.dbeng enName,t.dbchin chName,t.dbtype dbType,t.vol_dbid volDbId "
				+ "from jktable t";
		result=searchForSql(sql, new RsHandler<List<TableModel>>() {
			@Override
			public List<TableModel> handler(ResultSet rs) throws SQLException {
				List<TableModel> result = null;
				while (rs.next()) {
					if (result == null) {
						result = new ArrayList<>();
					}
					TableModel tm = new TableModel();
					tm.setDbId(rs.getInt("dbId"));
					tm.setEnName(rs.getString("enName"));
					tm.setChName(rs.getString("chName"));
					tm.setDbType(rs.getInt("dbType"));
					tm.setVolDbId(rs.getInt("volDbId"));
					result.add(tm);
				}
				return result;
			}
		});
		return result;
	}

}
