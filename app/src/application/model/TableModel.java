package application.model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableModel implements Comparator<TableModel> {
	private Integer dbId;
	private String enName;
	private String chName;
	private Integer dbType;
	private Integer volDbId;
	private List<ColumnModel> column;
	private Map<Integer, ColumnModel> idCols;
	private Map<String, ColumnModel> enCols;
	private Map<String, ColumnModel> chCols;

	public Integer getDbId() {
		return dbId;
	}

	public void setDbId(Integer dbId) {
		this.dbId = dbId;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getChName() {
		return chName;
	}

	public void setChName(String chName) {
		this.chName = chName;
	}

	public Integer getDbType() {
		return dbType;
	}

	public void setDbType(Integer dbType) {
		this.dbType = dbType;
	}

	public Integer getVolDbId() {
		return volDbId;
	}

	public void setVolDbId(Integer volDbId) {
		this.volDbId = volDbId;
	}

	public List<ColumnModel> getColumn() {
		return column;
	}

	public void setColumn(List<ColumnModel> column) {
		this.column = column;
		idCols = new HashMap<>();
		enCols = new HashMap<>();
		chCols = new HashMap<>();
		for (ColumnModel col : column) {
			idCols.put(col.getColId(), col);
			enCols.put(col.getColEnName(), col);
			chCols.put(col.getColChName(), col);
		}
	}

	public ColumnModel getColumnById(Integer id) {
		return idCols.get(id);
	}

	public ColumnModel getColumnByEnName(String enName) {
		return enCols.get(enName);
	}

	public ColumnModel getColumnByChName(String chName) {
		return chCols.get(chName);
	}

	public String[] getColumnChNames() {
		String[] colChNames = new String[chCols.size()];
		return chCols.keySet().toArray(colChNames);
	}

	@Override
	public int compare(TableModel o1, TableModel o2) {
		int swap = -1;
		int notSwap = 1;
		if (o1.getDbId() > o2.getDbId()) {
			return swap;
		}
		return notSwap;
	}

	@Override
	public String toString() {
		return this.chName;
	}
}
