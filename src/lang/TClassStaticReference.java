package lang;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/** 
 *
 * @author chenxun
 * @date 2017年1月18日 上午11:29:56 
 *
 */
public class TClassStaticReference {
	
	/**
	 * 1.类的static变量（基本类型和引用类型）只会伴随类加载一次
	 * 2.应该：单例的饿汉式就是定义一个static的引用类型
	 */
	static AtomicInteger integer ;
	static int a = 0 ;
    static int b;
	
	static{
		 integer = new AtomicInteger();
		 b = 4 + new Random().nextInt(10);
	}
   
	@SuppressWarnings({ "static-access" })
	public static void main(String[] args) {
		
		TClassStaticReference tClassReference = new TClassStaticReference();
		System.out.println(tClassReference.integer.getAndIncrement());
		System.out.println(++tClassReference.a);
		System.out.println(tClassReference.b);
		System.out.println("=====================================");
		TClassStaticReference tClassReference2 = new TClassStaticReference();
		System.out.println(tClassReference2.integer.getAndIncrement());
		System.out.println(++tClassReference.a);
		System.out.println(tClassReference.b);
		
		System.out.println(tClassReference.integer == tClassReference2.integer);
	}
}
