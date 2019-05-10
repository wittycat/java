package reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

/**
 * @author:chenxun
 * @2016年12月17日 下午11:34:23
 * @Theme:
 * @Reference:
 * @Descript:反射测试 ,反射以Class为基础,可以获得Field，Constructor，Method
 */
public class ReflectUtils {
	public static void proccess(String classPath) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> clazz;
		try {
        	 clazz = Class.forName(classPath);
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundException("路径不对，请检查");
		}
        System.out.println("-------------01.common---------------------------");
        //必须提供默认的无惨构造函数
        try {
        	System.out.println("实例："+classPath+"@"+clazz.newInstance().hashCode());
		} catch (Exception e) {
			System.out.println("实例："+classPath+"无法实例,错误信息,"+e.getMessage());
		}
        
        System.out.println("-------------02.field----------------------------");
        Field[] fields = clazz.getFields();
		new ReflectUtils.ReflectDescription(fields);
		
		System.out.println("-------------03.constructor----------------------");
        Constructor<?>[] constructors = clazz.getConstructors();
        for (Constructor<?> constructor : constructors) {
        	new ReflectUtils.ReflectDescription(constructor);
        	System.out.println("constructor实例"+(constructor.getParameterCount()==1?
        			constructor.newInstance("有参构造函数执行"):
        				constructor.newInstance()));
        	
		}
        
        System.out.println("-------------04.method---------------------------");
        Method[] methods = clazz.getMethods();
//        BaseBean newInstance = (BaseBean)clazz.newInstance();
//        Method tempMethod = null;
    for (Method method : methods) {
        	new ReflectUtils.ReflectDescription(method);
//        	if("setField".equals(method.getName())){
//        		method.invoke(newInstance, "方法字段赋值");
//        	}
//        	if("getField".equals(method.getName())){
//        		tempMethod = method;
//        	}
		}
//        String invoke =(String)tempMethod.invoke(newInstance, null);
//      	System.out.println(invoke);
	}
	/**
	 * 描述信息的输出
	 */
	private static class  ReflectDescription{
		private Integer modifier;
		private String name;
		
		public ReflectDescription(Field[] field) {
			System.out.format("public字段描述:%s %n",FieldDescription(field));
		}
		
		public ReflectDescription(Method method) {
			this.modifier = method.getModifiers();
			this.name = method.getName();
			System.out.format("方法描述:%s %s %s (%s) %n",Modifier.toString(modifier),
					method.getGenericReturnType().getTypeName(),
					name,
					ParameterDescription(method.getGenericParameterTypes(),method.getParameters()));
		}
		
		public ReflectDescription(Constructor<?> constructor) {
			this.modifier = constructor.getModifiers();
			this.name = constructor.getName();
			System.out.format("构造函数描述:%s %s (%s) %n",Modifier.toString(modifier),name,
					ParameterDescription(constructor.getGenericParameterTypes(), constructor.getParameters()));
		}
		
		/**
		 * 参数描述
		 * @param types
		 * @param parameters
		 * @return
		 */
		private String ParameterDescription(Type[] types,Parameter[] parameters) {
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < parameters.length; i++) {
				builder.append(String.format("%s %s %s",types[i].getTypeName(),
						parameters[i].getName(),(parameters.length-1)==i?"":","));
			}
			return builder.toString();
		}
		
		/**
		 * 字段描述
		 */
		private String FieldDescription(Field[] fields) {
			StringBuilder builder = new StringBuilder();
			Field field;
			for (int i = 0; i < fields.length; i++) {
			    field = fields[i];
				builder.append(String.format("%s %s %s %s",
						Modifier.toString(field.getModifiers()),
						field.getGenericType().getTypeName(),
						field.getName(),(fields.length-1)==i?"":","));
			}
			return builder.toString();
		}
	}
}
