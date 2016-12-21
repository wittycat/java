package lang;
/** 
 * @author：chenxun
 * 创建时间：2016年9月28日 上午12:06:13 
 * 参考：http://www.jb51.net/article/59935.htm
 * 说明： <1>创建一个字符串，在编译时已经处理。
 *      <2>字符串常量池中存放的时引用还是对象，这个问题是最常见的。字符串常量池存放的是对象引用，不是对象。
 * 在Java中，对象都创建在堆内存中。
 *      <3>javap主要用于帮助开发者深入了解Java编译器的机制
 * 
 */
public class TCreateHaoManyString1 {
	
//	D:\>javac CreateHaoManyString1.java
//
//	D:\>javap -c CreateHaoManyString1
//	警告: 二进制文件CreateHaoManyString1包含com.chenxun.CreateHaoManyString1
//	Compiled from "CreateHaoManyString1.java"
//	public class com.chenxun.CreateHaoManyString1 {
//	  public com.chenxun.CreateHaoManyString1();
//	    Code:
//	       0: aload_0
//	       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
//	       4: return
//
//	  public static void main(java.lang.String[]);
//	    Code:
//	       0: ldc           #2                  // String abc  只创建一个字符串
//	       2: astore_1
//	       3: return
//	}

	public static void main(String[] args) {
		   @SuppressWarnings("unused")
		String info = "a"+ "b"+ "c";
	}
}