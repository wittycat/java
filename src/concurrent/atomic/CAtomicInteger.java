package concurrent.atomic;

import java.lang.reflect.Constructor;
import java.util.concurrent.CountDownLatch;

import sun.misc.Unsafe;

/** 
 * @Author:chenxun
 * @2017年1月15日 上午12:34:14 
 * @Theme:
 * @Reference:
 * @Descript:
 */
public class CAtomicInteger {
	
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
		    valueOffset = unsafe.objectFieldOffset(CAtomicInteger.class.getDeclaredField("value"));
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
		CAtomicInteger cAtomicInteger = new CAtomicInteger();
		System.out.println("init value="+cAtomicInteger.getValue());
		CountDownLatch countDownLatch = new CountDownLatch(1);
		
		for (int i = 0; i < 10000; i++) {
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
		
		while (Thread.activeCount()!=10001) 
			Thread.yield();
		
		countDownLatch.countDown();
		while (Thread.activeCount()>1) 
			Thread.yield();
		
		System.out.println("期望20000 value="+cAtomicInteger.getValue());
		
	}
	
}
