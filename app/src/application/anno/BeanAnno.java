package application.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ����ע��ע��,AppRegister��������bean��ע���Ӧ����
 * 
 * @author Administrator
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeanAnno {
	/**
	 * ע������bean��ʶ�� 
	 * 
	 * @return
	 */
	String value();

	/**
	 * true����ע�������޶�Ӧbean���׳��쳣,false�����
	 * 
	 * @return
	 */
	boolean requ() default true;
}
