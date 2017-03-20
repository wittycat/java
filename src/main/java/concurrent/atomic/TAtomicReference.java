package concurrent.atomic;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/** 
 * @Author:chenxun
 * @2017年1月16日 下午4:46:00
 * @Theme:
 * @Reference:
 * @Descript:
 * 
 */
public class TAtomicReference {

	static class IntegerBean{
		
		private volatile int a;

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

		final AtomicReference<IntegerBean> atomicReference = new AtomicReference<IntegerBean>();
		final IntegerBean integerBean = new IntegerBean(-1);
		atomicReference.set(integerBean);
        
		final ArrayBlockingQueue<IntegerBean> synchronousQueue = new ArrayBlockingQueue<IntegerBean>(1);
		int c = 100;
		for (int i = 0; i < c; i++) {
			final Integer integer = new Integer(i);
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						int intValue = integer.intValue();
						//保证线程依次按照顺序更新
						TimeUnit.MILLISECONDS.sleep(intValue*100);
						IntegerBean bean = new IntegerBean(intValue);
						atomicReference.compareAndSet(intValue!=0?synchronousQueue.take():integerBean, bean);
						synchronousQueue.put(bean);
					} catch (InterruptedException e) {
					}
				}
			}).start();
		}
		
		while(Thread.activeCount()>1)
			Thread.yield();
		
		System.out.format("a expect =%d  %s",c-1, atomicReference.get().toString());

	}

}
