package application.model;

public class TableModel {
	private Integer dbId;
	private String enName;
	private String chName;
	private Integer dbType;
	private Integer volDbId;

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

}
