package concurrent.lock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** 
 * @Author:chenxun
 * @2016年12月31日 下午9:38:15 
 * @Theme:等待队列和同步队列中线程的打印 
 * 和lock ,unLock,await,signal(signalAll:相当于循环操作signal)的逻辑分析
 * @Reference:
 * @Descript
 * <ul>
 * <li>lock:获取锁不到阻塞该线程，加入同步队列</li>
 * <li>unLock:释放锁唤醒后继节点</li>
 * <li>await:主要干三件事:1.阻塞该线程 2.添加到等待队列3. 唤醒后继线程</li>
 * <li>signalAll:主要干1件事:加入同步队列</li>
 * </ul>
 */
public class TReentrantLock3 {
	
	
	static class InnerReentrantLock  extends ReentrantLock{
	
		private static final long serialVersionUID = 1L;

		public InnerReentrantLock (boolean fair){
			super(fair);
		}

		public Collection<Thread> getQueuedThreads() {
			ArrayList<Thread> list = new ArrayList<Thread>(super.getQueuedThreads());
			java.util.Collections.reverse(list);
			return list;
		}
		
		public Collection<Thread> getWaitingThreads(Condition condition) {
			ArrayList<Thread> list = new ArrayList<Thread>(super.getWaitingThreads(condition));
			java.util.Collections.reverse(list);
			return list;
		}
	}
	
	static class Job extends Thread{
		Lock lock;
		Boolean falg;
		Condition condition;
		public Job (Lock lock,Boolean falg ,Condition condition){
			Objects.requireNonNull(lock);
			Objects.requireNonNull(falg);
			Objects.requireNonNull(condition);
			this.falg = falg;
			this.lock = lock;
			this.condition = condition;
		}
		
		@Override
		public void run() {
			//[**获取锁不到阻塞该线程，加入同步队列**]
			lock.lock();
			try {
				doingOneSecond(1);
				if(falg){
					System.out.format("%s 获取锁%n同步队列的线程:%s %n条件队列的线程:%s %n%n",
							getName(),
							((InnerReentrantLock)lock).getQueuedThreads(),
							((InnerReentrantLock)lock).getWaitingThreads(condition)
							);
				}else{
					System.out.format("%s 获取锁,准备进入条件队列%n同步队列的线程:%s %n进入前条件队列的线程:%s %n%n",
							getName(),
							((InnerReentrantLock)lock).getQueuedThreads(),
							((InnerReentrantLock)lock).getWaitingThreads(condition)
							);
					  
					//[**主要干三件事:1.阻塞该线程 2.添加到等待队列3. 唤醒后继线程**]
					condition.await();
					doingOneSecond(1);//保证从等待队列的线程都已经进入同步队列
					
					System.out.format("%s 获取锁%n同步队列的线程:%s %n条件队列的线程:%s %n%n",
							getName(),
							((InnerReentrantLock)lock).getQueuedThreads(),
							((InnerReentrantLock)lock).getWaitingThreads(condition)
							);
				}
			} catch (InterruptedException e) {
			}finally{
				//[**释放锁唤醒后继节点**]
				lock.unlock();
			}
		}
		
		/**
		 * 改变线程默认的toString打印
		 */
		@Override
		public String toString() {
			return getName();
		}
	}
	
	public static void main(String[] args) {
		
		InnerReentrantLock lock  = new InnerReentrantLock(true);
		Condition condition = lock.newCondition();
		LinkedList<Job> list = new  LinkedList<Job>();
		for (int i = 0; i < 10; i++) {
			if(i%2==0){
				Job thread = new Job(lock,true,condition);
				thread.setName("sync"+i);
				thread.start();
			}else{
				Job thread = new Job(lock,false,condition);
				thread.setName("block"+i);
				thread.start();
				list.add(thread);
			}
		}
		//保证非同步队列线程都执行完了
		doingOneSecond(11);
		
		lock.lock();
		System.out.println("=========唤醒:从条件队列转移到同步队列========");
		//[**主要干1件事:加入同步队列 **]
		condition.signalAll();
		lock.unlock();
		
		
		//all over
		while(Thread.activeCount()>1)
			Thread.yield();
	}
	
	public static void doingOneSecond(int i) {
		try {
			Thread.sleep(i*1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
