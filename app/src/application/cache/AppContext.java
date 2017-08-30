package application.cache;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AppContext {

	private static Properties dbProp = new Properties();
	private static List<?> RELAS = new ArrayList<>();
	private static Map<String, ?> MAPS = new HashMap<>();
	private static Logger log = LoggerFactory.getLogger(AppContext.class);

	public static void init() {
		try (InputStream conis = new FileInputStream("config.properties");
				InputStream appis = new FileInputStream("db.properties");) {
			dbProp.load(appis);
			log.info("config load success");
		} catch (Exception e) {
			log.error("config load fail,error info: {}", e.getMessage());
		}
	}

	public static void refresh() {
		log.debug("config refresh success");
	}

	public static List<?> getRelas() {
		return RELAS;
	}

	public static Object getRelaByKey(Object key) {
		return MAPS.get(key);
	}

	public static String getDbConfig(String key, String df) {
		return dbProp.getProperty(key, df).trim();
	}

	public static String getDbConfig(String key) {
		return dbProp.getProperty(key, "").trim();
	}

	public static Properties getDbConfigs() {
		return dbProp;
	}
}
