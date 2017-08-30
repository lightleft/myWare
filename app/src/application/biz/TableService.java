package application.biz;

import java.util.List;

import application.anno.BeanAnno;
import application.anno.RegistAnno;
import application.dao.TableDao;
import application.model.TableModel;

@RegistAnno("tableService")
public class TableService {
	@BeanAnno("tableDao")
	private TableDao tableDao;

	public List<TableModel> getTableAll() {
		List<TableModel> result = tableDao.getTableAll();
		return result;
	}
}
