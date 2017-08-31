package application.model;

import java.util.List;

public class Trems {
	private List<Trem> trems;
	private boolean flag = false;
	private String sql;
	private Object[] objs;

	public Trems(List<Trem> trems) {
		super();
		this.trems = trems;
	}

	public Trems() {
		super();
	}

	public List<Trem> getTrems() {
		return trems;
	}

	public void setTrems(List<Trem> trems) {
		this.trems = trems;
	}

	private void handler(String t) {
		if (trems == null || trems.isEmpty()) {
			this.sql = "";
			this.objs = null;
		} else {
			StringBuilder sb = new StringBuilder();
			for (Trem trem : trems) {
				sb.append(trem.getSql(t));
			}
			this.sql = sb.toString();
		}
		flag = true;
	}

	public String getSql(String t) {
		if (!flag) {
			handler(t);
		}
		return this.sql;
	}

	public Object[] getObjs() {
		return this.objs;
	}
}
