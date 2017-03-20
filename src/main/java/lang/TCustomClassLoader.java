package lang;

import java.io.IOException;
import java.io.InputStream;

/** 
 * @author：chenxun
 * createDate：2016年11月9日 上午12:09:13 
 * Theme:
 * reference:
 * descript:
 */
public class TCustomClassLoader  {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		ClassLoader classLoader = new ClassLoader() {
			@Override
			public Class<?> loadClass(String name)throws ClassNotFoundException {
				try {
					String className = name.substring(name.lastIndexOf(".") + 1) + ".class";
					InputStream inputStream = getClass().getResourceAsStream(className);
					if (inputStream != null) {
						//采用自己定义的类加载器
						byte[] byte1 = new byte[inputStream.available()];
						System.out.println(inputStream.available());
//						for (int i = 0; i < byte1.length; i++) {
//							System.out.println(byte1[i]);
//						}
						inputStream.read(byte1);
						System.out.print(byte1.length);
						return defineClass(name, byte1, 0, byte1.length);
					} else {
						// 等于空采用默认父类类加载器
						return super.loadClass(name);
					}
				} catch (IOException e) {
					throw new ClassNotFoundException(name);
				}
			}
		};
		
	    Object newInstance = classLoader.loadClass("lang.TCustomClassLoader").newInstance();
		
		System.out.println(newInstance.hashCode());
		TCustomClassLoader myClassLoader = new TCustomClassLoader();
		System.out.println(new TCustomClassLoader().hashCode());
		System.out.println(newInstance instanceof lang.TCustomClassLoader);
		
//		 Launcher launcher = sun.misc.Launcher.getLauncher();
//		 URLClassPath bootstrapClassPath = sun.misc.Launcher.getBootstrapClassPath();
//		 System.out.println(launcher);
//		 URL[] urLs = bootstrapClassPath.getURLs();
//		 for (URL url : urLs) {
//			 System.out.println(url.getPath());
//		 }
	}
}
