package lang;
/** 
 * @author：chenxun
 * 创建时间：2016年9月28日 上午12:06:13 
 * 参考：http://www.jb51.net/article/59934.htm
 * 说明：一共创建2个字符串， info为编译后优化的StringBuilder的toString来的，不属于直接创建字符串
 */
public class TCreateHaoManyString2 {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		  String a = "a";
		  String b = "b";
	      String info = a+ b;
	}
//	D:\>javac CreateHaoManyString2.java
//
//	D:\>javap -c CreateHaoManyString2
//	警告: 二进制文件CreateHaoManyString2包含com.chenxun.CreateHaoManyString2
//	Compiled from "CreateHaoManyString2.java"
//	public class com.chenxun.CreateHaoManyString2 {
//	  public com.chenxun.CreateHaoManyString2();
//	    Code:
//	       0: aload_0
//	       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
//	       4: return
//
//	  public static void main(java.lang.String[]);
//	    Code:
//	       0: ldc           #2                  // String a
//	       2: astore_1
//	       3: ldc           #2                  // String a
//	       5: astore_2
//	       6: new           #3                  // class java/lang/StringBuilder
//	       9: dup
//	      10: invokespecial #4                  // Method java/lang/StringBuilder."<init>":()V
//	      13: aload_1
//	      14: invokevirtual #5                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
//	      17: aload_2
//	      18: invokevirtual #5                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
//	      21: invokevirtual #6                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
//	      24: astore_3
//	      25: return
//	}
}
