package application.model;

import java.util.Comparator;

public class ColumnModel implements Comparator<ColumnModel> {
	public static final String CHAR_TYPE = "字符型";
	public static final String INT_TYPE = "整数型";
	public static final String DATA_TYPE = "日期型";
	public static final String BOOLEAN_TYPE = "布尔型";
	private Integer dbId;
	private Integer colId;
	private String colEnName;
	private String colChName;
	private String colType;
	private Integer colWidth;
	public Integer getDbId() {
		return dbId;
	}

	public void setDbId(Integer dbId) {
		this.dbId = dbId;
	}

	public Integer getColId() {
		return colId;
	}

	public void setColId(Integer colId) {
		this.colId = colId;
	}

	public String getColEnName() {
		return colEnName;
	}

	public void setColEnName(String colEnName) {
		this.colEnName = colEnName;
	}

	public String getColChName() {
		return colChName;
	}

	public void setColChName(String colChName) {
		this.colChName = colChName;
	}

	public String getColType() {
		return colType;
	}

	public void setColType(String colType) {
		this.colType = colType;
	}

	public Integer getColWidth() {
		return colWidth;
	}

	public void setColWidth(Integer colWidth) {
		this.colWidth = colWidth;
	}

	@Override
	public int compare(ColumnModel o1, ColumnModel o2) {
		int swap = -1;
		int notSwap = 1;
		if (o1.getDbId() > o2.getDbId()) {
			return swap;
		}
		if (o1.getColId() > o2.getColId()) {
			return swap;
		}
		return notSwap;
	}

}
