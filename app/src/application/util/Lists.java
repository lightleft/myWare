package application.util;

import java.util.ArrayList;
import java.util.List;

public final class Lists {
	public static final <T> List<T> asList(List<T> list) {
		return new ArrayList<>(list);
	}
	public static final <T> List<T> asList() {
		return new ArrayList<>();
	}
}
