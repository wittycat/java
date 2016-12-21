package lang;

import java.net.URL;

/** 
 * @author：chenxun
 * 创建时间：2016年9月28日 上午1:10:58 
 * 参考：http://blog.csdn.net/xyang81/article/details/7292380
 * 说明：BootStrap ClassLoader：称为启动类加载器，是Java类加载层次中最顶层的类加载器，负责加载JDK中的核心类库，
 * 如：rt.jar、resources.jar、charsets.jar等，可通过如下程序获得该类加载器从哪些地方加载了相关的jar或class文件
 */
public class TBootStrapClassLoader {
   public static void main(String[] args) {
	   URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();  
	   for (int i = 0; i < urls.length; i++) {  
	       System.out.println(urls[i].toExternalForm());  
	   }  
   }
}