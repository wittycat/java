package collection;

/**
 * @author：chenxun 
 * 创建时间：2016年5月28日 下午10:21:20 
 * 参考： 
 * 说明：
 */
public class ComporeTime {
	public static Long start() {
		return System.currentTimeMillis();
	}

	public static Long end() {
		return System.currentTimeMillis();
	}

	public static void minus(Long end, Long start) {
		System.out.println("用时：" + (end - start)+"毫秒");
	}
	
	public static String getMinus(Long start) {
		return "用时：" + (ComporeTime.end() - start)+"毫秒";
	}
	
	public static void getOutMinus(Long start) {
		System.out.println("用时：" + (ComporeTime.end() - start)+"毫秒");
	}
}
