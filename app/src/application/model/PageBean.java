package application.model;

import java.util.List;
import java.util.Map;

public class PageBean {
	private int index = 1;
	private TableModel table;
	private int size = 20;
	private List<Map<String, Object>> data;
	private int total;

	public PageBean() {
	}

	public PageBean(TableModel table) {
		super();
		this.table = table;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public TableModel getTable() {
		return table;
	}

	public void setTable(TableModel table) {
		this.table = table;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<Map<String, Object>> getData() {
		return data;
	}

	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getStart() {
		return (index - 1) * size;
	}

	public int getEnd() {
		return (index) * size;
	}

	public int getPageNumbers() {
		int numbers = 1;
		if (total == 0) {
			return numbers;
		}
		numbers = total / size;
		if (total % size != 0) {
			numbers += 1;
		}
		return numbers;
	}

	public int getNowPageCount() {
		int pageCount = size;
		if (getEnd() <= total) {
			return pageCount;
		} else {
			return total - getStart();
		}
	}

	public boolean hasNextPage() {
		return !(getPageNumbers() == index);
	}

	public boolean hasPerPage() {
		return !(index == 1);
	}
}
