package application.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.model.ColumnModel;
import application.model.TableModel;

public class ColumnUtil {
	private static boolean INIT_FLAG = false;

	public static void init(List<ColumnModel> columns) {
		if (INIT_FLAG)
			return;
		Map<Integer, List<ColumnModel>> tableCols = new HashMap<>();
		for (ColumnModel col : columns) {
			Integer dbId = col.getDbId();
			List<ColumnModel> ls = tableCols.get(dbId);
			if (ls == null) {
				ls = new ArrayList<>();
				tableCols.put(dbId, ls);
			}
			ls.add(col);
		}
		for (Integer dbId : tableCols.keySet()) {
			TableUtil.getTableById(dbId).setColumn(tableCols.get(dbId));
		}
		INIT_FLAG = true;
	}

	public static ColumnModel getColumnModelByDbIdAndColId(Integer dbId, Integer colId) {
		TableModel table = TableUtil.getTableById(dbId);
		if (table != null) {
			return table.getColumnById(colId);
		}
		return null;
	}

	public static List<ColumnModel> getColumnsByDbId(Integer dbId) {
		TableModel table = TableUtil.getTableById(dbId);
		if (table != null) {
			return table.getColumn();
		}
		return null;
	}

	public static List<ColumnModel> getColumnsByName(String name) {
		TableModel table = TableUtil.getTableByName(name);
		if (table != null) {
			return table.getColumn();
		}
		return null;
	}
}
