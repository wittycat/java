package concurrent.collection.queue;

/**
 * @Author:chenxun
 * @2017年1月8日 下午6:22:18
 * @Theme:
 * @Reference:
 * @Descript:
 * <ul>
 * <li>区别：while比if执行完后多一次检测</li>
 * <li>if：只会进行一次判断，执行if代码块完后就会执行if之后的代码</li>
 * <li>while：符合while条件后，执行完while代码块里面的代码，执行完后会再次检查while条件，符合继续执行；不符合跳过</li>
 * <li>while：多使用于多线程唤醒后的再次检查条件</li>
 * </ul>
 */
public class TWhileAndIf {
	public static void whileMethod() {
		try {
			System.out.println("whileMethod invoke");
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
		}
	}
	
	public static boolean check(int[] f) {
	    	System.out.println("check invoke");
			return f[0] < 1;
	}

	public static void main(String[] args) {
		final int[] f = { 0 };
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2500L);
					f[0] = 1;
					System.out.println("f update");
				} catch (InterruptedException e) {
				}
			}
		}).start();
//		while (check(f)) {
//			whileMethod();
//		}
		
//		if (check(f)) {
//			whileMethod();
//		}
	}
}
