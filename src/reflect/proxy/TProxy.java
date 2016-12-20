package reflect.proxy;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import reflect.DemoBean;
import reflect.IDemoBean;

/** 
 * @author:chenxun
 * @2016年12月18日 下午4:42:34 
 * @Theme:
 * @Reference:
 * @Descript:
 */
public class TProxy {
   public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, FileNotFoundException, IOException {
	   IDemoBean demoBean = new DemoBean();
	   InvocationHandler h = new MyInvocationHandler(demoBean);
	   IDemoBean newProxyInstance = (IDemoBean)Proxy.newProxyInstance(IDemoBean.class.getClassLoader(), new Class[]{IDemoBean.class}, h);
	   System.out.println(demoBean==newProxyInstance);
	   System.out.println(newProxyInstance.getClass().getName());   
	   
	   newProxyInstance.setField("field赋值");
	   
	   
//	   ProxyGeneratorUtils.writeProxyClassToHardDisk("d:/io/$Proxy11.class"); 
//	   $Proxy11 $Proxy11 = new $Proxy11(h);
//	   $Proxy11.setField("field赋值");
	   
   }
   static class MyInvocationHandler implements InvocationHandler{
	   
	    private IDemoBean iDemoBean;
	    
		public MyInvocationHandler(IDemoBean iDemoBean) {
			this.iDemoBean = iDemoBean;
		}
        /**
         * Method的invoke传入的对象一定不能是h的invoke方法传入的proxy对象，
         * proxy对象是代理对象传入的自己本身，如果传入Proxy，将会发生代理对象
         * 和h对象之间的递归调用，直到栈溢出，正确应该传入h持有的被代理对象,即iDemoBean。h的invoke方
         * 法传入的proxy对象也没有什么实际作用
         */
		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			System.out.println("before");
			return method.invoke(iDemoBean, args);
		}
   }
}
