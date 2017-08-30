package application.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.model.TableModel;

public final class TableUtil {
	private static List<TableModel> MODELS;
	private static Map<String, TableModel> CH_MAPS = new HashMap<>();
	private static Map<Integer, TableModel> ID_MAPS = new HashMap<>();
	private static Map<String, TableModel> EN_MAPS = new HashMap<>();

	public static final void init(List<TableModel> models) {
		TableUtil.MODELS = models;

		if (models != null && models.size() > 0)
			for (TableModel table : TableUtil.MODELS) {
				ID_MAPS.put(table.getDbId(), table);
				CH_MAPS.put(table.getChName(), table);
				EN_MAPS.put(table.getEnName(), table);
			}

	}

	public static TableModel getTableById(Integer id) {
		return ID_MAPS.get(id);
	}

	public static TableModel getTableByName(String name) {
		TableModel table = null;
		table = EN_MAPS.get(name);
		if (table == null)
			table = CH_MAPS.get(name);
		return table;
	}

	public static int getTableCounts() {
		return MODELS.size();
	}

	public static String[] getChNames() {
		String[] tableChNames = new String[getTableCounts()];
		for (int i = 0; i < MODELS.size(); i++) {
			tableChNames[i] = MODELS.get(i).getChName();
		}
		return tableChNames;
	}
}
