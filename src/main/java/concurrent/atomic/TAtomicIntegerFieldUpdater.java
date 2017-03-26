package concurrent.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/** 
 * @Author:chenxun
 * @2017年1月16日 下午4:46:00
 * @Theme:
 * @Reference:
 * @Descript:
 * 1.原子更新字段类,通过创建更新器
 * 2.内部在构造函数中通过反射获取类的字段 ,进行修改
 */
public class TAtomicIntegerFieldUpdater {

	static class IntegerBean{
		
		/**
		 * 必须是有volatile 修饰,
		 * 必须是是int 类型,
		 */
		public volatile int a;

		public IntegerBean(int a) {
			this.a = a;
		}

		public int getA() {
			return a;
		}

		public void setA(int a) {
			this.a = a;
		}
		
		@Override
		public String toString() {
			return "IntegerBean [a=" + a + "]";
		}
	}

	public static void main(String[] args) {

		final CountDownLatch countDownLatch = new CountDownLatch(1);
		final AtomicIntegerFieldUpdater<IntegerBean> updater =
				AtomicIntegerFieldUpdater.newUpdater(IntegerBean.class, "a");
		final IntegerBean integerBean = new IntegerBean(0);

		for (int i = 0; i < 1000; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						countDownLatch.await();
						updater.getAndAdd(integerBean, 2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
		
		//all threads start 
		while(Thread.activeCount()<1002)
			Thread.yield();
		
		//off
		countDownLatch.countDown();
		
		while(Thread.activeCount()>2)
			Thread.yield();
		
		
		System.out.println(integerBean.toString());

	}

}
