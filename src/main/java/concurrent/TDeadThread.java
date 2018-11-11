package concurrent;


/** 
 * @author：chenxun
 * 创建时间：2016年10月5日 下午11:25:16 
 * 参考：死锁测试
 * 说明：
 */
public class TDeadThread {

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			System.out.println("===="+i+1+"=====");
			new Thread(thread1).start();
			new Thread(thread2).start();
		}
	}
	
	static String  lock1 = "lock1";
	static String lock2 = "lock2";
	
	static Runnable thread1 =  new Runnable() {
		@Override
		public void run() {
			synchronized (lock1) {
				synchronized (lock2) {
					System.out.format("线程:%s", Thread.currentThread().getName());
				}
			}
		}
	};
	static Runnable thread2 =  new Runnable() {
		public void run() {
			synchronized (lock2) {
				synchronized (lock1) {
					System.out.format("线程:%s", Thread.currentThread().getName());
				}
			}
		}
	};
	

}
