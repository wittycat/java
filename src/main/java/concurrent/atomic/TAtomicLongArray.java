package concurrent.atomic;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLongArray;

/** 
 * @Author:chenxun
 * @2017年1月17日 上午12:11:02 
 * @Theme:
 * @Reference:
 * @Descript:
 * 1.本例子总会正常运行结束  ，运气不好时间比较长罢了
 * 2.数组的原子更新是通过索引号 更新的具体数值的
 *******************************************************
 *             【atomic包概述】
 * 1.原子类主要提供：3类（int，long，Reference），每类3种（本身，数组，字段）的更新
 *   外加Boolean（底层转化为int）
 *   具体实现对应于Unsafe类的方法
 *   	unsafe.compareAndSwapInt(arg0, arg1, arg2, arg3)
 * 		unsafe.compareAndSwapLong(arg0, arg1, arg2, arg3)
 * 		unsafe.compareAndSwapObject(arg0, arg1, arg2, arg3)
 *   若想要实现其他基本类型，可以通过转化为int 或long类型去操作
 */
public class TAtomicLongArray {
	public static void main(String[] args) {
		final AtomicLongArray atomicLongArray = new AtomicLongArray(10);
		System.out.println(atomicLongArray.toString());
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		
		final ConcurrentSkipListMap<String,Long> map = new ConcurrentSkipListMap<String,Long>();
		
		for (int i = 0; i < 100; i++) {
			final Integer integer = new Integer(i);
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						countDownLatch.await();
						boolean f = false;
						long count = 0; 
						while(!f){
							count++;
							f = atomicLongArray.compareAndSet(0,(integer-1)==-1?0:(integer-1),integer);
						}
						map.putIfAbsent(Thread.currentThread().getName(), count);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
		
		while(Thread.activeCount()<101)
			Thread.yield();
		
		countDownLatch.countDown();
		
		while(Thread.activeCount()>1)
			Thread.yield();
		
		System.out.format("expect index[0]=%d  %s %n",99, atomicLongArray.toString());
		
		System.out.println(map.toString());

	}
}
