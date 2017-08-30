package application.cache;

import java.util.List;

import application.biz.TableService;
import application.model.TableModel;

public class Cache {

	private static TableService tableService = AppRegister.getBean("tableService", TableService.class);

	public static void init() {
		initTable();
	}

	public static void initTable() {
		List<TableModel> tables = tableService.getTableAll();
		TableUtil.init(tables);
	}
}
