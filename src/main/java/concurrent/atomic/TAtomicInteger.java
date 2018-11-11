package concurrent.atomic;

import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.util.concurrent.CountDownLatch;

/** 
 * @Author:chenxun
 * @2017年1月15日 上午12:34:14 
 * @Theme:
 * @Reference:
 * @Descript:
 */
public class TAtomicInteger {
	
	static Unsafe unsafe;
	static long valueOffset;
	static{
		try {
			/**
			 * 方式1
			 */
//			Field f = Unsafe.class.getDeclaredField("theUnsafe");
//			f.setAccessible(true);
//			unsafe = (Unsafe) f.get(null);
			/**
			 * 方式2
			 */
			Constructor<Unsafe> unsafeConstructor = Unsafe.class.getDeclaredConstructor();
			unsafeConstructor.setAccessible(true);
		    unsafe = unsafeConstructor.newInstance();
		    valueOffset = unsafe.objectFieldOffset(TAtomicInteger.class.getDeclaredField("value"));
		} catch (Exception e) {
			 throw new Error(e);
		}
	}
	
	private int value ;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public final int getAndIncrement(int n) {
	    return unsafe.getAndAddInt(this, valueOffset,n)+n;
	}
	
	public static void main(String[] args) {
		final TAtomicInteger cAtomicInteger = new TAtomicInteger();
		System.out.println("init value="+cAtomicInteger.getValue());
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						countDownLatch.await();
						cAtomicInteger.getAndIncrement(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
		
		while (Thread.activeCount()!=102)
			Thread.yield();
		
		countDownLatch.countDown();
		while (Thread.activeCount()>2)
			Thread.yield();
		
		System.out.println("期望200 value="+cAtomicInteger.getValue());
		
	}
	
}
