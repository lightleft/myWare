package application.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 属性注入注解,AppRegister向其管理的bean中注入对应属性
 * 
 * @author Administrator
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeanAnno {
	/**
	 * 注册器中bean标识符 
	 * 
	 * @return
	 */
	String value();

	/**
	 * true是若注册器中无对应bean将抛出异常,false则忽略
	 * 
	 * @return
	 */
	boolean requ() default true;
}
