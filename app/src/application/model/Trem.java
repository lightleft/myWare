package application.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.cache.ColumnUtil;

public class Trem {
	private static final Logger LOG = LoggerFactory.getLogger(Trem.class);

	public static enum Rela {
		小于等于("elt", "<="), 小于("lt", "<"), 大于等于("egt", ">="), 大于("gt", ">"), 包含("like", "like"), 等于("eq",
				"="), 不等于("neq", "!="), 不包含("nlike", "not like");
		Rela(String name, String r) {
			this.name = name;
			this.r = r;
		}

		private final String name;
		private final String r;

		public String getName() {
			return name;
		}

		public String getR() {
			return r;
		}

		private String getSQL(String colName, Object value) {
			String sql = null;
			switch (this.name) {
			case "elt": {
				sql = " and " + colName + " <= '" + value + "' ";
				break;
			}
			case "lt": {
				sql = " and " + colName + " < '" + value + "' ";
				break;
			}
			case "egt": {
				sql = " and " + colName + " >= '" + value + "' ";
				break;
			}
			case "gt": {
				sql = " and " + colName + " > '" + value + "' ";
				break;
			}
			case "eq": {
				sql = " and " + colName + " = '" + value + "' ";
				break;
			}
			case "neq": {
				sql = " and " + colName + " != '" + value + "' ";
				break;
			}
			case "like": {
				sql = " and " + colName + " like '%" + value + "%' ";
				break;
			}
			case "nlike": {
				sql = " and " + colName + " not like '%" + value + "%' ";
				break;
			}
			default:
				break;
			}
			return sql;
		}
	}

	private String value;
	private Rela rela;
	private ColumnModel colModel;

	public Trem() {
	}

	public Trem(String value, Rela rela) {
		this.value = value;
		this.rela = rela;
	}

	public Trem(String value, Rela rela, ColumnModel colModel) {
		this.value = value;
		this.rela = rela;
		this.colModel = colModel;
	}

	public ColumnModel getColModel() {
		return colModel;
	}

	public void setColModel(ColumnModel colModel) {
		this.colModel = colModel;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Rela getRela() {
		return rela;
	}

	public void setRela(Rela rela) {
		this.rela = rela;
	}

	public String getSql(String t) {
		if (ColumnUtil.isInt(this.colModel)) {
			try {
				int val = Integer.parseInt(this.value);
				return this.rela.getSQL(t + "." + this.colModel.getColEnName(), val);
			} catch (Exception e) {
				LOG.warn("过滤条件转换错误,试图向整数型字段过滤其他类型值--->{},{}", this.colModel.getColChName(), this.value);
			}
		} else if (ColumnUtil.isString(this.colModel)) {
			return this.rela.getSQL(t + "." + this.colModel.getColEnName(), this.value);
		} else if (ColumnUtil.isBoolean(this.colModel)) {
			try {
				boolean val = Boolean.parseBoolean(this.value);
				return this.rela.getSQL(t + "." + this.colModel.getColEnName(), val);
			} catch (Exception e) {
				LOG.warn("过滤条件转换错误,试图向布尔型字段过滤其他类型值--->{},{}", this.colModel.getColChName(), this.value);
			}
		}
		return "";
	}
}
