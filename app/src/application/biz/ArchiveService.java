package application.biz;

import application.anno.BeanAnno;
import application.anno.RegistAnno;
import application.dao.ArchiveDao;
import application.model.PageBean;

@RegistAnno("archiveService")
public class ArchiveService {

	@BeanAnno("archiveDao")
	private ArchiveDao archiveDao;

	public PageBean getArch(PageBean page) {
		return archiveDao.getArch(page);
	}
}
