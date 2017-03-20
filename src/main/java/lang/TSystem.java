package lang;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

/** 
 * @author:chenxun
 * @2016年12月21日 下午11:06:40 
 * @Theme:
 * @Reference:
 * @Descript:
 */
public class TSystem {  
	public static void main(String[] args) throws IOException {
		//使用01：
		System.out.format("currentTimeMillis is %d %n", System.currentTimeMillis());
		
		//使用02： Properties是Hashtable的一个子类，那么就符合key-value形式
		Properties properties = System.getProperties();
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("D:/io/properties_02.txt"));
		properties.store(bufferedWriter, null);
		bufferedWriter.close();
		//使用02-第二种输出方式
		PrintWriter printWriter = new PrintWriter("D:/io/properties_03.txt");
		Set<Entry<Object, Object>> entrySet = properties.entrySet();
		int lineNum = 0 ;
		for (Entry<Object, Object> entry : entrySet) {
			lineNum++;
			printWriter.format("第%2d行,key:%s,value:%s %s", lineNum,entry.getKey(),entry.getValue(),System.lineSeparator());
		}
		printWriter.close();
		
		//使用03：System 提供的高效数组拷贝方式arraycopy ArrayList就是通过这个函数进行动态扩展
		int length = 10;
		String[] src = new String[length];
		for (int i = 0; i < length; i++) {
			src[i] = UUID.randomUUID().toString().replace("-", "");
		}
		System.out.println(Arrays.toString(src));
		String[] dest = new String[src.length];
		System.arraycopy(src,0, dest, 0, src.length);
		System.out.println(Arrays.toString(dest));
		
		//使用04：gc和exit都是直接调用Runtime的
		System.gc();
		System.exit(0);
	}
}
