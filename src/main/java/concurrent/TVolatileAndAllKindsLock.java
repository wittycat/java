package concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** 
 * @author：chenxun
 * createDate：2016年11月12日 下午6:25:42 
 * Theme:
 * reference:
 * descript:
 * 1.volatile并不能保障变量的原子性，只能保障可见性，所以运行结果一般小于1000000
 * 2.顺便测试线程组：主线程（和其子线组的线程）属于一个线程组
 * 3.顺便各种锁使用
 */
public class TVolatileAndAllKindsLock {
	
	private volatile static int a;//不能保障结果为1000000
	//写锁和读锁测试
//	static ReadLock lock = new ReentrantReadWriteLock().readLock();//能保障结果为1000000
//	static WriteLock lock = new ReentrantReadWriteLock().writeLock();//不能保障结果为1000000
	//尝试获取锁测试
	static Lock lock = new ReentrantLock();//重入锁
	static int count ;//尝试未获取锁计数
	
	public static int getA() {
		return a;
	}
	public static void addA()  {
		
//		synchronized (Tvolatile.class) {//能保障结果为1000000
			 a++;
//		}
			 
		//写锁和读锁测试
//		try {
//			lock.lock();
//			return a++;
//		}finally{
//			lock.unlock();
//		}
		
		//尝试获取锁测试
//		if(lock.tryLock()){
//			try {
//				lock.lock();
//				a++;
//			}finally{
//				lock.unlock();
//			}
//		}else{
//			synchronized (Tvolatile.class){
//				count++;
//			}
//		}
		
	}
	public static void main(String[] args) {
        long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
	 			@Override
	 			public void run() {
	 				System.out.println(Thread.currentThread().getThreadGroup().getName());
	 				for (int j = 0; j < 100; j++) {
	 					new Thread(new Runnable() {
							@Override
							public void run() {
								for (int k = 0; k < 10; k++) {
									if(k%100==0)
										System.out.println(k+"==="+Thread.currentThread().getThreadGroup().getName());
									addA();
								}
							}
						}).start();
					}
	 			}
	 		}).start();
		}
        
        System.out.println(Thread.activeCount());
        
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        System.out.println(threadGroup.getName());
        System.out.println(threadGroup.activeCount());
        
        while( Thread.activeCount()>1)//获取main的线程组线程总数
        	Thread.yield();//暂停主线程
        
        
        System.out.println((System.currentTimeMillis()-start)+"毫秒");
        
        System.out.println(Thread.activeCount());
        System.out.println(Thread.currentThread().getName());
        
        System.out.println(getA());
        System.out.println("not get lock count:"+count);
	}

}
