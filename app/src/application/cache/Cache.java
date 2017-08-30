package application.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.biz.TableService;
import application.model.ColumnModel;
import application.model.TableModel;

public class Cache {
	private static Logger log = LoggerFactory.getLogger(Cache.class);
	private static TableService tableService = AppRegister.getBean("tableService", TableService.class);

	public static void init() {
		initTable();
		initColumn();
	}

	private static void initColumn() {
		List<ColumnModel> columns = tableService.getColumnAll();
		ColumnUtil.init(columns);
		log.info("cache load end!");
	}

	public static void initTable() {
		List<TableModel> tables = tableService.getTableAll();
		TableUtil.init(tables);
	}
}
