package lang;


/** 
 * @Author:chenxun
 * @2017年1月15日 上午2:43:23 
 * @Theme:测试数字不同进制表示
 * @Reference:
 * @Descript:
 */
public class TNumberRadix {
  public static void main(String[] args) {
	  int a =17;
//	  String binaryString = Integer.toBinaryString(a);
//	  String octalString = Integer.toOctalString(a);
//	  String hexString = Integer.toHexString(a);
//	  
//	  System.out.format("无符号二进制:%s%n", binaryString);
//	  System.out.format("无符号八进制:%s%n", octalString);
//	  System.out.format("无符号十六进制:%s%n", hexString);
	  
//	  a = a>>>2;
//	  System.out.format("无符号补零二进制:%s%n", Integer.toBinaryString(a));
//	  System.out.format("BigDecimal:%d%n",new BigDecimal(2).pow(31).intValue()-1);
	  
	  System.out.format("十进制:%d%n", Integer.MAX_VALUE);
	  System.out.format("二进制:%s%n",Integer.toBinaryString(Integer.MAX_VALUE));
	  
	  /**
	   * int 最大值使用这种表示  不是特别理解
	   */
	  a = (1<<30)^(1<<30)-1;
	  System.out.format("十进制:%d%n", a);
	  System.out.format("二进制:%s%n", Integer.toBinaryString(a));
	  System.out.format("二进制:%s%n", Integer.toBinaryString((1<<31)));

	  //异或运算结论:相同的为0  不同的为1
//	  System.out.format("异或运算:%s%n", 1^1);
//	  System.out.format("异或运算:%s%n", 0^0);
//	  System.out.format("异或运算:%s%n", 1^0);
//	  System.out.format("异或运算:%s%n", 0^1);

  }
}
