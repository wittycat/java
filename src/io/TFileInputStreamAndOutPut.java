package io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

import org.junit.Test;

/** 
 * @author：chenxun
 * createDate：2016年12月10日 下午4:13:04 
 * Theme:
 * reference:
 * descript:
 */
public class TFileInputStreamAndOutPut {
	
	@Test
	public void write01() throws UnsupportedEncodingException, IOException{
		File file = new File("d:/io");
		if(file.exists()? true:file.mkdirs()){}
		
	    file = new File("d:/io/test.txt");
	    if(file.exists()? true:file.createNewFile()){}
	    
//		file = File.createTempFile("我测试", ".txt", file);
	    
		FileOutputStream fileOutputStream = new  FileOutputStream(file);
		fileOutputStream.write("io 测试".getBytes("UTF-8"));
		/*
		105
		111
		32
		-26
		-75
		-117
		-24
		-81
		-107
		 */
		byte[] bytes = "io 测试".getBytes("UTF-8");
		for (int i = 0; i < bytes.length; i++) {
			System.out.println(bytes[i]);
		}
		
		fileOutputStream.close();
	}
	
	/**
	 * 字符串 长度 
	 * 字符编码：汉字utf-8:3个字节;
	 *        汉字gbk:2个字节;
	 *        字母无论utf-8还是gbk都是1个字节
	 * @throws IOException
	 */
	@Test
	public void encode01() throws IOException{
		String s= "io 测试";
		System.out.format("s length is %s \n", s.length());
		char[] charArray = s.toCharArray();
		System.out.format("charArray length is %s \n", charArray.length);
		for (int i = 0; i < charArray.length; i++) {
//		     System.out.println(charArray[i]);
		     printByte(String.valueOf(charArray[i]).getBytes("UTF-8"));
		}
	}
   /**
    * 1.in.available() 可以认为是移动的指针
    * 2.创建byte数组 
    * @throws IOException
    */
   @Test
   public void read01() throws IOException{
	  InputStream in= new FileInputStream(new File("d:/io/test.txt"));
	  int available = in.available();
	  byte[] b = new byte[available];
	  System.out.println(in.available());
	  for (int i = 0; i < available; i++) {
		  System.out.println(in.available());
		  b[i] = (byte) in.read();
	  }
	  in.close();
	  System.out.println(new String(b, "UTF-8"));
   }
   
   /**
    * 直接写入ByteArrayOutputStream 流中
    * @throws IOException
    */
   @Test
   public void read02() throws IOException{
		  InputStream in= new FileInputStream(new File("d:/io/test.txt"));
		  byte[] b = new byte[4];
		  ByteArrayOutputStream outStream=new ByteArrayOutputStream();  
		  int len = 0;
//		  while ((len = in.read(b))!=-1) {
//			System.out.println(len);
////			printByte(b);
//			outStream.write(b, 0, len);
//			b = new byte[4];
//		  }
		  do {
		    System.out.println(len);
			outStream.write(b, 0, len);
			b = new byte[4];
		  } while ((len = in.read(b))!=-1);
		  System.out.println(new String(outStream.toByteArray(), "UTF-8"));
		  in.close();
		  outStream.close();
   }
   /**
    * @throws URISyntaxException 
    * @throws IOException 
    * 读取网络资源
    */
   @Test
   public void readNetSourse03() throws URISyntaxException, IOException{
	   URL uri = new URL("http://h.hiphotos.baidu.com/image/pic/item/a2cc7cd98d1001e9460fd63bbd0e7bec54e797d7.jpg");
	   InputStream inputStream  = uri.openStream();
	   File file2 = new File("d:/io");
	   if(!file2.exists()){
		   file2.mkdirs();
	   }
	   @SuppressWarnings("static-access")
	   FileOutputStream fileOutputStream = new FileOutputStream(file2.createTempFile("picture", ".jpg", file2));
	   int l;
	   byte[] b = new byte[1024];
       while ((l= inputStream.read(b))!=-1) {
    		fileOutputStream.write(b, 0, l);
	   }
       inputStream.close();
       fileOutputStream.close();
   }
   
   private void printByte(byte[]  b){
//		StringBuilder sb = new StringBuilder();
//		for (int i = 0; i < b.length; i++) {
//			sb.append(b[i]+"#");
//		}
//		System.out.println(sb.toString());
	  
		System.out.println( Arrays.toString(b));
	}
}
