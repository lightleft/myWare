package application.dao;

import java.util.List;
import java.util.Map;

import application.anno.RegistAnno;
import application.dao.base.BaseDao;
import application.dao.util.ListMapHandler;
import application.model.PageBean;

@RegistAnno("archiveDao")
public class ArchiveDao extends BaseDao {
	public PageBean getArch(PageBean page) {
		List<Map<String, Object>> result = null;
		String whereSql = " where 1 = 1 " + page.getSql("t");
		String sqlCount = "select count(1) from " + page.getTable().getEnName() + " t " + whereSql;
		int total = getCount(sqlCount);
		page.setTotal(total);
		StringBuilder sql = new StringBuilder("select t.*,rowid=identity(12)  into #tmp from ");
		sql.append(page.getTable().getEnName()).append(" t ").append(whereSql)
				.append(" select * from #tmp where rowid> ").append(page.getStart()).append(" and rowid <= ")
				.append(page.getEnd());
		result = searchForSql(sql.toString(), new ListMapHandler());
		page.setData(result);
		return page;
	}
}
