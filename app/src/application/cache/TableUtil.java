package application.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.model.TableModel;

public final class TableUtil {
	private static List<TableModel> models;
	private static Map<Object, TableModel> maps = new HashMap<>();

	public static final void init(List<TableModel> models) {
		TableUtil.models = models;

		if (models != null && models.size() > 0)
			for (TableModel table : TableUtil.models) {
				maps.put(table.getDbId(), table);
				maps.put(table.getChName(), table);
				maps.put(table.getEnName(), table);
			}

	}

	private static TableModel getTable(Object idOrName) {
		return maps.get(idOrName);
	}

	public static TableModel getTableById(Integer id) {
		return getTable(id);
	}

	public static TableModel getTableByName(String name) {
		return getTable(name);
	}
}
