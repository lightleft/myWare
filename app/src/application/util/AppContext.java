package application.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AppContext {
	private static List<?> RELAS = new ArrayList<>();
	private static Map<String, ?> MAPS = new HashMap<>();
	private static Logger log = LoggerFactory.getLogger(AppContext.class);

	public static void init() {
		try (InputStream is = new FileInputStream("config.properties")) {
		} catch (Exception e) {
			log.error("配置项加载失败,一场信息：{}", e.getMessage());
		}
		log.info("配置项加载完成！");
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
}
