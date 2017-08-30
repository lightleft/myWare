package application.util;

public final class StringUtil {
	public static final String toString(Object source, String df) {
		if (source == null) {
			return df;
		}
		String result = source.toString();
		if (result.isEmpty()) {
			return df;
		}
		if ("null".equalsIgnoreCase(result)) {
			return df;
		}
		return result;
	}

	public static final String toString(Object source) {
		return toString(source, "");
	}
}
