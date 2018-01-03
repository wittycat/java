package reflect;

import org.junit.Test;

/** 
 * @author：chenxun
 * 创建时间：2016年9月24日 下午10:32:10 
 * 参考：
 * 说明：ClassLoad和Class.ForName的获取clazz的不同
 *      1.区别loadClass 和 forName(类全名,false, **ClassLoader())相等
 *      2. forName(类全名) 调用  forName(类全名,true, **ClassLoader())
 *         会初始化静态变量和静态代码块  ; loadClass不会
 */
public class TClassLoadAndClassForName {

	static class TestClass {
		static {
			System.out.println("static 块执行..");
		}

		public TestClass() {
			System.out.println("构造函数  执行..");
		}
	}

	@Test
	public void  testClassLoad() throws ClassNotFoundException, Exception{
		Class<?> loadClass = Thread.currentThread()
		                     .getContextClassLoader()
		                     .loadClass("reflect.TestClass");
		
		@SuppressWarnings("unused")
		TestClass testClass = (TestClass) loadClass.newInstance();
	}
	
	@Test
	public void  testClassFor() throws ClassNotFoundException, Exception{
//		Class<?> clazz = Class.forName("reflect.TestClass");
		Class<?> clazz = Class.forName("reflect.TestClass",
				true, this.getClass().getClassLoader());
		
		@SuppressWarnings("unused")
		TestClass testClass = (TestClass) clazz.newInstance();
	}


}

