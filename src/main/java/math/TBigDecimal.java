package math;

import java.math.BigDecimal;
import java.text.NumberFormat;

/** 
 * @author：chenxun
 * 创建时间：2016年6月10日 下午10:59:01 
 * 参考：
 * 说明：
 * BigDecimal一共有4个构造方法
 * BigDecimal(int) 创建一个具有参数所指定整数值的对象。
 * BigDecimal(double) 创建一个具有参数所指定双精度值的对象。
 * BigDecimal(long) 创建一个具有参数所指定长整数值的对象。
 * BigDecimal(String) 创建一个具有参数所指定以字符串表示的数值的对象。
 * BigDecimal 的运算方式 不支持 + - * / 这类的运算 它有自己的运算方法
 * BigDecimal add(BigDecimal augend) 加法运算
 * BigDecimal subtract(BigDecimal subtrahend) 减法运算
 * BigDecimal multiply(BigDecimal multiplicand) 乘法运算
 * BigDecimal divide(BigDecimal divisor) 除法运算
 * 
 * BigDecimal，用来对超过16位有效位的数进行精确的运算。
 * 双精度浮点型变量double可以处理16位有效数。在实际应用中，需要对更大或者更小的数进行运算和处理。
 * float和double只能用来做科学计算或者是工程计算，在商业计算中要用java.math.BigDecimal。
 * BigDecimal所创建的是对象，我们不能使用传统的+、-、*、/等算术运算符直接对其对象进行数学运算，
 * 而必须调用其相对应的方法
 */
public class TBigDecimal {
	public static void main(String[] args) {
		
		BigDecimal bigLoanAmount = new BigDecimal(101.1);   //创建BigDecimal对象
		BigDecimal bigInterestRate = new BigDecimal(101.2);
		
		BigDecimal bigInterest = bigLoanAmount.multiply(bigInterestRate); //BigDecimal运算
		
		NumberFormat currency = NumberFormat.getCurrencyInstance();    //建立货币格式化引用
		NumberFormat percent = NumberFormat.getPercentInstance();     //建立百分比格式化用
		
		percent.setMaximumFractionDigits(3);               //百分比小数点最多3位
		//利用BigDecimal对象作为参数在format()中调用货币和百分比格式化
		System.out.println("Loan amount:\t" + currency.format(bigLoanAmount));
		System.out.println("Interest rate:\t" + percent.format(bigInterestRate));
		System.out.println("Interest:\t" + currency.format(bigInterest));
	}
}
