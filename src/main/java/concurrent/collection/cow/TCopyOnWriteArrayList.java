package concurrent.collection.cow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/** 
 *
 * @author chenxun
 * @date 2017年1月13日 下午2:08:18 
 * @引用： http://ifeve.com/java-copy-on-write/
 * <pre>
 *   java1.8 对CopyOnWriteArrayList 和 Collections.synchronizedList(new ArrayList<String>())
 * 性能测试结论：1.6下 CopyOnWriteArrayList 优于 synchronizedList网上结论;
 * 但在1.8下基本差不多,可能是jvm对synchronized 不断的优化
 * </pre>
 * <ul>
 * <li>1.Copy-On-Write简称COW，是一种用于程序设计中的优化策略，采用数组存储。</li>
 * <li>2.使用场景：多读少写（读不加锁，写会加锁，防止多个线程多多个拷贝同时造成数据混乱，所以会加锁防止此问题）</li>
 * <li>3.写时采用复制新的容器，进行修改；新容器为原容器中对象的引用，使用完把新容器赋值给类的原有容器引用</li>
 * <li>4.CopyOnWriteArraySet 采用CopyOnWriteArrayList 作为存储。基本差不多</li>
 * <li>5.复制新的容器时，这是一个比较耗费性能的操作</li>
 * <li>6.CopyOnWrite的缺点
 *     <ul>
 *        <li>内存占用问题。因为CopyOnWrite的写时复制机制，所以在进行写操作的时候，内存里会同时驻扎两个对象的内存，旧的对象和新写入的对象
 *        （注意:在复制的时候只是复制容器里的引用，只是在写的时候会创建新对象添加到新容器里，而旧容器的对象还在使用，所以有两份对象内存）。</li>
 *        <li>数据一致性问题。CopyOnWrite容器只能保证数据的最终一致性，不能保证数据的实时一致性。所以如果若希望写入的的数据，马上能读到，
 *         不建议使用CopyOnWrite容器。</li>
 *     </ul>
 * </li>
 * </ul>
 */
public class TCopyOnWriteArrayList {
   public static void main(String[] args) throws InterruptedException {
//	   final CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<String>();
	   final List<String> list = Collections.synchronizedList(new ArrayList<String>());
	   for (int i = 0; i < 100; i++) {
		   list.add(String.valueOf(i));
	   }
	   
	   int[] threadCount = {32,64,128,256,512,1024,2056};
	   for (int i = 0; i < threadCount.length; i++) {
		   long nanoTime = System.nanoTime();
		   final CountDownLatch countDownLatch = new CountDownLatch(threadCount[i]);
		   final CountDownLatch awaitDownLatch = new CountDownLatch(1);
		   for (int j = 0; j < threadCount[i]; j++) {
				  new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							awaitDownLatch.await();
							list.get(50);
							countDownLatch.countDown();
						} catch (InterruptedException e) {
						}
					}
				}).start();
		  }
		  awaitDownLatch.countDown();
		  countDownLatch.await();
		  //时间扩大1<<10 倍
		  long convert = TimeUnit.SECONDS.convert((System.nanoTime() - nanoTime)*1<<10, TimeUnit.NANOSECONDS);
		  System.out.format("%d个线程用时%d秒%n",threadCount[i],convert);
	   }
   }
}
