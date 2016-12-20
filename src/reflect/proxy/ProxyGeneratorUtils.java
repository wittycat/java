package reflect.proxy;

import java.io.FileOutputStream;
import java.io.IOException;

import reflect.DemoBean;
import sun.misc.ProxyGenerator;

/** 
 * @author:chenxun
 * @2016年12月19日 上午1:20:47 
 * @Theme:
 * @Reference:http://www.ibm.com/developerworks/cn/java/j-lo-proxy1/index.html
 * @Descript:
 */
public class ProxyGeneratorUtils {
	/** 
     * 把代理类的字节码写到硬盘上 
     * @param path 保存路径 
     */  
    public static void writeProxyClassToHardDisk(String path) {  
        // 第一种方法（未经验证） 
        // System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", true);  
          
        // 第二种方法  （已经验证）
        // 获取代理类的字节码  
        byte[] classFile = ProxyGenerator.generateProxyClass("$Proxy11", DemoBean.class.getInterfaces());  
          
        FileOutputStream out = null;  
          
        try {  
            out = new FileOutputStream(path);  
            out.write(classFile);  
            out.flush();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                out.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
}
