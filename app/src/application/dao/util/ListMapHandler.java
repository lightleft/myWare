package application.dao.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListMapHandler implements RsHandler<List<Map<String, Object>>> {

	@Override
	public List<Map<String, Object>> handler(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int colCount = rsmd.getColumnCount();
		if (colCount < 1) {
			return null;
		}
		String[] colNames = new String[colCount];
		for (int i = 0; i < colCount; i++) {
			colNames[i] = rsmd.getColumnLabel(i + 1);
		}
		List<Map<String, Object>> result = new ArrayList<>();
		while (rs.next()) {
			Map<String, Object> map = new HashMap<>();
			for (int i = 0; i < colCount; i++) {
				map.put(colNames[i], rs.getObject(colNames[i]));
			}
			result.add(map);
		}
		return result;
	}

}
