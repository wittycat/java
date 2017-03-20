package io;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

/** 
 * @author：chenxun
 * createDate：2016年12月11日 上午10:59:59 
 * Theme:
 * reference:
 * descript:
 */
public class TWrite {
    
	@Test
	public void write() throws IOException {
		File file = new File("d:/io2");
		if(!file.exists()){
			file.mkdirs();
		}
//	    file = new File("d:/io2/test.txt");
//	    if(!file.exists()){
//			file.createNewFile();
//		}
	    FileWriter fileWriter = new FileWriter("d:/io2/test.txt");
	    System.out.println(fileWriter.getEncoding());

	    fileWriter.write("io 测试");
	    fileWriter.close();
	}
	
	@Test
	public void read01() throws IOException {
	    FileReader fileReader = new FileReader("d:/io2/test.txt");
	    char[] b = new char[10];
	    @SuppressWarnings("unused")
		int l;
	    do {
	    	System.out.println(String.valueOf(b));
			b = new char[10];
		} while ((l = fileReader.read(b))!=-1);
	    
//		while ((l = fileReader.read(b))!=-1) {
//			System.out.println(String.valueOf(b));
//			b = new char[10];
//		}
	    
	    fileReader.close();
	}
}
