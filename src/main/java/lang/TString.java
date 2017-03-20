package lang;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

/** 
 * @author：chenxun
 * 创建时间：2016年5月29日 下午12:44:06  
 * 参考：
 * 说明： 1.除过new 可以产生String对象，字符串还有第二种创建对象的方法的直接赋值String s1 = "abc";
 *        推荐使用直接赋值创建字符串
 *      2.字符串会放在堆内存的String Pool(字符串常量池)中【内存位置比较特殊】，gc不会对他回收的。
 *      3.String 为final型，无子类。方法中提供的有返回String的对象的，都是新创建的，不会对池中原有的进行修改，
 *        所以不存在安全问题
 *      4.
 *      
 */
public class TString {
	/**
	 * 
	 */
	@Test
    public void testIsSame() {
		// TODO Auto-generated method stub
        String s1 = "abc";
        String s2 = "abc";
        String s3 = new String("abc");
        /**
         * 当调用 intern 方法时，如果池已经包含一个等于此 String 对象的字符串（用 equals(Object) 方法确定），
         * 则返回池中的字符串。否则，将此 String 对象添加到池中，并返回此 String 对象的引用。
         */
        String s4 = s3.intern();
        System.out.println(s1 == s2);//true
        System.out.println(s1 == s3);//false
        System.out.println(s1 == s4);//true
	}
	@Test
	public void enCode() {
		String s1 = "汉字";
		byte[] bytes = null;
		try {
			bytes = s1.getBytes("UTF-8");//当workspase采用gbk编码，这设置UTF-8编码
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		/**
		 * 此时这按照workspase的gbk解码 就会乱码
		 * 解决：new String(bytes,"UTF-8");
		 */
		System.out.println(new String(bytes));
		
	}
}
