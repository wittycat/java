package lang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/** 
 * @author:chenxun
 * @2016年12月21日 下午11:05:17 
 * @Theme:
 * @Reference:
 * @Descript:Runtime 是一个单例实体,采用饿汉式创建
 */
public class TRuntime {  
public static void main(String[] args) throws IOException, InterruptedException {
		
		Runtime runtime = Runtime.getRuntime();
		//使用01:常用于线程池的coreThread size 
		System.out.format("availableProcessors is %d %n", runtime.availableProcessors());
		
		//使用02:
		int M = 1024*1024;
		System.out.format("Memory size(M), max:%1$d,total:%2$d,free:%3$d %n", runtime.maxMemory()/M,runtime.totalMemory()/M,runtime.freeMemory()/M);
		
		//使用03:手动gc
		runtime.gc();
		
		//使用04:执行外部程序 :cmd /? :cmd参数查看
		//数组中的命令为连续执行的
//	    String[] cmdarray = {"C:/Program Files (x86)/Google/Chrome/Application/chrome.exe","www.oracle.com"};
		// /c:执行字符串指定的命令然后终止
//		String[] cmdarray = {"cmd.exe","/c","cmd /?"};
//		String[] cmdarray = {"cmd.exe","/c","java"};//这种不出输出任何结果，但在命令行下java 是由结果的。疑惑？
		String[] cmdarray = {"cmd.exe","/c","javap"};
		//在此种情况下windows 的cmd 路径不能写成 / 否则提示：命令语法不正确
//	    String[] cmdarray = {"cmd.exe","/c" ,"copy D:\\io2\\test.txt D:\\io2\\copy.txt"};
		//这种使用不会显示cmd窗口
		Process exec = runtime.exec(cmdarray);
		//输出cmd下的执行结果
		BufferedReader reader = new BufferedReader(new InputStreamReader(exec.getInputStream(),"gbk"));
		String line;
		while ((line = reader.readLine())!=null) {
			System.out.println(line);
		}
		
		//使用05:退出jvm
//		runtime.exit(0);
//		System.out.println(" jvm stop ,this not print ");
	} 
}
