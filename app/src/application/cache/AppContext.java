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

	private static Properties prop = new Properties();
	private static List<?> RELAS = new ArrayList<>();
	private static Map<String, ?> MAPS = new HashMap<>();
	private static Logger log = LoggerFactory.getLogger(AppContext.class);

	public static void init() {
		try (InputStream conis = new FileInputStream("config.properties");
				InputStream appis = new FileInputStream("appctx.properties");) {
			prop.load(appis);
			log.info("配置项加载完成！");
		} catch (Exception e) {
			log.error("配置项加载失败,一场信息：{}", e.getMessage());
		}
	}

	public static void refresh() {
		log.debug("配置项更新完成！");
	}

	public static List<?> getRelas() {
		return RELAS;
	}

	public static Object getRelaByKey(Object key) {
		return MAPS.get(key);
	}

	public static String getDbConfig(String key, String df) {
		return prop.getProperty(key, df).trim();
	}

	public static String getDbConfig(String key) {
		return prop.getProperty(key, "").trim();
	}
}
