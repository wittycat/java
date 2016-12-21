package lang;

/**
 * @author：chenxun 创建时间：2016年6月11日 下午12:28:02 参考： 说明：
 * 1.不要在finally中定义return ，
 * 原因一：它会覆盖try中的return 值 
 * 原因二：它会吃掉catch中的throw 异常，永远不会往上抛
 *        与此类似不要在finally中定义：System.exit(0)和
 *        Runtime.getRuntime().exit(0); 它会导致方法没有返回值
 */
public class TException {
 
	public static void main(String[] args) {
		System.out.println(fun());
	}

	@SuppressWarnings("unused")
	public static int fun() {
		try {
			System.out.println("try");
			int a = 1 / 0;
			return 1;
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("catch");
			throw new ArithmeticException("/ by zero");
		} finally {
			System.out.println("finally");
			// System.exit(0);
			Runtime.getRuntime().exit(0);
			// return 2;
		}
	}
}
