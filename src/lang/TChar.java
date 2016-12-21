package lang;
/** 
 * @author：chenxun
 * 创建时间：2016年6月10日 下午11:31:17 
 * 参考：
 * 说明：byte <（short=char）< int < long < float < double
                  如果从小转换到大，可以自动完成，而从大到小，必须强制转换。
       short和char两种相同类型也必须强制转换。
 */
public class TChar {
  public static void main(String[] args) {
	char c = 'a';
	System.out.println((int)++c);
	for (int i = 10000; i < 10950; i++) {
		System.out.println(i+":"+(char)i);
	}
	
	//System.out.println(2^7-1);
  }
}
