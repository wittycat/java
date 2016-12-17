package util;

import java.util.Date;


/** 
 * @author：chenxun
 * createDate：2016年12月14日 下午10:49:34 
 * Theme:
 * reference:
 * descript:
 */
public class TFormat {
	/**
	 * 常规类型、字符类型和数值类型的格式说明符的语法如下： 
	 * %[argument_index$][flags][width][.precision]conversion  这是一个基本单元
	 * 解析过程： 先根据正则把format 划分为 基本单元，然后和参数匹配输入，
	 * @param args
	 */
	public static void main(String[] args) {
		//多个参数一个输入 ：默认使用第一个 不报错
		//n个参数n个输出  ：位置一一对应
		System.out.format("%s%s%n", "a","b");//数字$ 表示参数的索引位置
		//小数点后有5位小数  空位使用0替补
		System.out.format("%.5f%n",1.23);
		//一个参数多出使用时必须添加索引 
		//日期必须添加T或t ，y只能输出年的后2位
		// 所有大小写一样作用
		System.out.format("%1$ty-%1$tm-%1$td %n",new Date());
		System.out.format("%1$Ty-%1$Tm-%1$Td %n",new Date());
		System.out.format("%1$s-%1$s-%1$s%n","a");
		
		//format和 printf 调用的是一个方法
		//换行  
		System.out.format("%n");
		System.out.printf("100的16进制数是：%x %n", 100);  
		
		//%d %%  相当于 输出一个数 + 输出一个%
		System.out.printf("结果是 ：%d%% %n", 30);  
	}
}
