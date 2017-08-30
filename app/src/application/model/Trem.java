package application.model;

public class Trem {
	private String colChName;
	private String value;
	private String rela;
	private ColumnModel colModel;

	public ColumnModel getColModel() {
		return colModel;
	}

	public void setColModel(ColumnModel colModel) {
		this.colModel = colModel;
	}

	public String getColChName() {
		return colChName;
	}

	public void setColChName(String colChName) {
		this.colChName = colChName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRela() {
		return rela;
	}

	public void setRela(String rela) {
		this.rela = rela;
	}

}
