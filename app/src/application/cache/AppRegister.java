package application.cache;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.Main;
import application.anno.BeanAnno;
import application.anno.RegistAnno;

public final class AppRegister {
	private static final Map<String, Object> apps = new HashMap<>();
	private static Logger log = LoggerFactory.getLogger(AppRegister.class);

	public static void init() {
		try {
			load("dao");
			load("biz");
			giveBean();
			log.info("register load end");
		} catch (Exception e) {
			log.info("register load fail,error info:{}", e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name, Class<T> clazz) {
		T t = null;
		Object obj = apps.get(name);
		if (obj == null)
			return t;
		try {
			t = (T) obj;
		} catch (Exception e) {
			log.error("get bean fail,error :{}", e);
		}
		return t;
	}

	private final static void load(String subPageName) throws Exception {
		URL url = Main.class.getResource(subPageName);
		String pack = Main.class.getPackage().getName();
		File path = new File(url.getFile());
		File[] files = path.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File file, String name) {
				return name.endsWith(".class");
			}

		});
		if (files != null)
			for (File file : files) {
				String className = file.getAbsolutePath();
				className = className.replace(File.separatorChar, '.');
				className = className.substring(className.indexOf(pack), className.length() - 6);
				Class<?> slazz = Class.forName(className);
				RegistAnno exportHandlerAnno = slazz.getAnnotation(RegistAnno.class);
				if (exportHandlerAnno != null) {
					String key = exportHandlerAnno.value();
					Object instance = slazz.newInstance();
					Object obj = apps.put(key, instance);
					if (obj != null) {
						throw new IllegalStateException("regist fail, " + key + " ÷ÿ∏¥§ç");
					}
					log.info("regist {} success", instance.getClass().getName());
				}
			}
	}

	private final static void giveBean() throws Exception {
		for (Object obj : apps.values()) {
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				BeanAnno anno = field.getAnnotation(BeanAnno.class);
				if (anno != null) {
					String key = anno.value();
					Object fieldBean = apps.get(key);
					boolean accessFlag = field.isAccessible();
					if (fieldBean != null) {
						field.setAccessible(true);
						field.set(obj, fieldBean);
						field.setAccessible(accessFlag);
					} else {
						if (anno.requ()) {
							throw new IllegalStateException(obj + "give field " + field.getName() + " fail");
						}
					}
				}
			}
		}
	}
}
