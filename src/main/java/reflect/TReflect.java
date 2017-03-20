package reflect;

import java.lang.reflect.InvocationTargetException;


/**
 * @author:chenxun
 * @2016年12月17日 下午11:34:23
 * @Theme:
 * @Reference:
 * @Descript:反射测试 ,反射以Class为基础,可以获得Field，Constructor，Method
 */
public class TReflect {
	public static void main(String[] args){
		try {
			ReflectUtils.proccess("reflect.DemoBean");
//			ReflectUtils.proccess("java.util.Objects");
//			ReflectUtils.proccess("java.lang.String");
//			ReflectUtils.proccess("java.util.HashMap");
//			ReflectUtils.proccess("junit.framework.ComparisonCompactor");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
