package concurrent.collection.queue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:chenxun
 * @2017年1月7日 上午3:17:33
 * @Theme:自定义阻塞队列实现
 * @Reference:
 * @Descript
 * 自定义阻塞队列需要注意的问题（属于通知模式实现）
 * <ol>
 * <li>putIndex和takeIndex，当到达数组末端时，必须从0开始</li>
 * <li>
 *     while和if的选择
 *     必须使用while，因为在signal（）take1线程时，只会从阻塞队列转移到同步队列（不见得会获取锁真正唤醒），
 *     此时可能已经有其他take2线程获取到了锁，把队列中仅有的元素移除了，此时take2线程执行完后是释放锁，唤醒后
 *     继节点也就是take1线程。此时take1线程应该再次判断，条件不满足继续阻塞.
 *     <br/>-------------------------------------------------------------------------<br/>
 *     能否通过公平锁或不公平锁避免这个问题？
 *     不能，因为上面的take2线程可以认为是不公平锁的插队线程。 <br/>
 *     加入是公平锁呢？上面 take1线程顺利获取到了锁，取走了唯一的元素。而新线程在获取不到锁时，加入同步队列。当线程 take1
 *     线程执行完后释放锁， take2线程被唤醒，也必须再次检测while条件。否则将返回null元素，不符合阻塞队列。
 * </li>
 * <li>signal和signalAll:使用这俩个都可以，只不过只会有一个线程获取锁得到元素，所以使用signal比signalAll更恰当</li>
 * <ol>
 */
public class CArrayBlockingQueue<E> {

	private ReentrantLock lock = new ReentrantLock();
	private Condition empty;
	private Condition full;
	private final Object[] obj;
	private int putIndex;
	private int takeIndex;
	private int count;

	public CArrayBlockingQueue(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException("capacity must over zero");
		}
		this.lock = new ReentrantLock(true);
		this.empty = lock.newCondition();
		this.full = lock.newCondition();
		this.obj = new Object[capacity];
		putIndex = takeIndex = count = 0;
	}

	public void put(E e) throws InterruptedException {
		lock.lock();
		try {
			while (count == obj.length)
				full.await();
			obj[putIndex++] = e;
			if (putIndex == (obj.length))
				putIndex = 0;
			count++;
			empty.signal();
		} finally {
			lock.unlock();
		}
	}

	public E take() throws InterruptedException {
		lock.lock();
		try {
			/**
			 * 唤醒线程后，继续检查while条件，不满足继续阻塞（注意while比if执行完后多一次检测）
			 */
			while (count == 0)
				empty.await();
			@SuppressWarnings("unchecked")
			E e = (E) obj[takeIndex];
			obj[takeIndex] = null;
			if (++takeIndex == (obj.length))
				takeIndex = 0;
			count--;
			full.signal();
			//测试使用
//			Objects.requireNonNull(e);
			return e;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 测试
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		final CArrayBlockingQueue<String> quene = new CArrayBlockingQueue<String>(10);

		// 生产者
		new Thread(new Runnable() {
			@Override
			public void run() {
				int i = 0;
				while (true) {
					String valueOf = String.valueOf(++i);
					try {
						quene.put(valueOf);
						System.out.format("生产--------%s%n", valueOf);
						// Thread.sleep(1000L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

		for (int i = 0; i < 2; i++) {
			// 消费者
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {
							quene.take();
							System.out.format("消费****%s****%s%n", Thread
									.currentThread().getName(), quene.take());
							// Thread.sleep(2000L);
						} catch (InterruptedException e) {
						}
					}
				}
			}).start();
		}

		while (Thread.activeCount() > 1)
			Thread.yield();
	}
}
