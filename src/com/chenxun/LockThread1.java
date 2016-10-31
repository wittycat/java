package com.chenxun;


/** 
 * @author：chenxun
 * 创建时间：2016年10月5日 下午11:25:16 
 * 参考：
 * 说明：
 */
public class LockThread1 {

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			new Thread(thread1).start();
			new Thread(thread2).start();
		}
	}
	
	static String  lock= "lock";
	
	static Runnable thread1 =  new Runnable() {
		public void run() {
				synchronized (lock) {
					System.out.println("thread1"+Thread.currentThread().getName());
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
		}
	};
	static Runnable thread2 =  new Runnable() {
		public void run() {
				synchronized (lock) {
					System.out.println("thread2"+Thread.currentThread().getName());
					lock.notifyAll();
				}
		}
	};
	

}
