package concurrent;
/** 
 * @author：chenxun
 * 创建时间：2016年10月8日 下午8:58:33 
 * 参考：
 * 说明： Object类的方法：
 *      Wait()是Object类的方法，范围是使该Object实例所处的线程。
 *      Wait方法进入等待状态时会释放同步锁(如例中的lock对象)
 *      notify()将随机唤醒一个在对象上等待的线程，没有一个都没有，则什么都不做。
 *      notifyAll()唤醒的所有线程，将在notify()线程释放了对象监视器以后才执行，并不是notify了以后马上执行。
 *      --------------------------------------------------------------------
 *      Thread类的方法：
 *      Sleep()是Thread类专属的静态方法，针对一个特定的线程。
 *      Sleep方法不会释放同步锁
 *      interrupt()终止线程，前提该线程必须持有锁，如果线程在调用 Object 类的 wait()、wait(long) 
 *      或 wait(long, int) 方法，或者该类的 join()、join(long)、join(long, int)、
 *      sleep(long) 或 sleep(long, int) 方法过程中受阻，则其中断状态将被清除，也就是无效
 */
public class TWaitNotify {
   
  static volatile int   waitCount ;
  
  public synchronized static void addWaitCount() {
	  waitCount++;
  }

public static void main(String[] args) {
	   final Object lock = new Object();
	   
	   Thread t1 = new Thread(MyTask(lock));
	   Thread t2 = new Thread(MyTask(lock));
	   Thread t3 = new Thread(MyNotify(lock));
	   
	   t1.setName("t1");
	   t2.setName("t2");
	   t3.setName("t3");
	   //没有调用start()就不处于活动线程
	   System.out.println("setName-count-"+Thread.activeCount());
	   
	   //时间上先启动，不代表这两个线程一定会先获取锁；这个正式 问题1所描述的，通过addWaitCount（）方法解决
	   t1.start();
	   t2.start();

	   while (Thread.activeCount()!=3) 
		   Thread.yield();
	   System.out.println(Thread.activeCount());
	   
	   t3.start();
	   
	   while (Thread.activeCount()>1) 
		   Thread.yield();
	   System.out.println(Thread.activeCount());
	   
	   System.out.println("over");
 }

   public static Runnable MyNotify(final Object lock) {
		return new Runnable() {
			@Override
			public void run() {
				  
				 while (TWaitNotify.waitCount!=2) {
					System.out.println("还有未处于等待的线程");
					Thread.yield();
				 }
				
				 synchronized (lock) {
//					lock.notify();
					 /**
					  * 问题1.如果还有一个线程没有处于wait状态，那么就会造成没有处于等待的线程一直等待,结束不了，如何解决？
					  */
				    lock.notifyAll();
					System.out.println("notify-"+Thread.currentThread().getName());
				 }
			}
		 };
	}

	/**
	 * @param lock
	 * @return
	 */
	public static Runnable MyTask(final Object lock) {
		return new Runnable() {
			@Override
			public void run() {
				     System.out.println("out-"+Thread.currentThread().getName());
					 synchronized (lock) {  
						   TWaitNotify.addWaitCount();
						   try {
								System.out.println("before-"+Thread.currentThread().getName());
//								Thread.currentThread().interrupt();
								lock.wait();
								System.out.println("after-"+Thread.currentThread().getName());
							} catch (InterruptedException e) {
						    }
					}
			  }
		 };
	}
}
