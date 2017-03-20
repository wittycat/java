package concurrent.collection.queue;

import java.util.concurrent.LinkedBlockingDeque;

/** 
 * @author:chenxun
 * @2017年1月9日 下午9:55:40 
 * @Theme:
 * @Reference:
 * 链表实现的有界阻塞双端队列，支持同端存取(FILO)和异端存取（FIFO）。
 * 如果未指定容量，那么容量将等于 Integer.MAX_VALUE
 * @Descript:
 */
public class TLinkedBlockingDeque {
	public static void main(String[] args) throws InterruptedException {
//		method1();
//		method2();
		method3();
	}
	 /**
     * 栈的方法：实现的为先进后出
     * @throws InterruptedException
     */
	public static void method3() throws InterruptedException {
		final LinkedBlockingDeque<String> synchronousQueue = new LinkedBlockingDeque<String>();
		new Thread(new Runnable() {
			@Override
			public void run() {
				int i = 0;
				while(true){
					String valueOf = String.valueOf(++i);
					System.out.format("放入%s%n", valueOf);
					synchronousQueue.push(valueOf);
					if(i==100)
					     break;
				}
			}
		}).start();
		
		Thread.sleep(1000L);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					try {
						if(synchronousQueue.peek()!=null){
							System.out.format("take:%s%n%n",synchronousQueue.pop());
							Thread.sleep(100L);
						}else{
							 break;
						}
					} catch (InterruptedException e) {
					}
				}
			}
		}).start();
	}
	
	 /**
     * 同端存取：实现的为先进后出
     * @throws InterruptedException
     */
	public static void method2() throws InterruptedException {
		final LinkedBlockingDeque<String> synchronousQueue = new LinkedBlockingDeque<String>();
		new Thread(new Runnable() {
			@Override
			public void run() {
				int i = 0;
				while(true){
					try {
						String valueOf = String.valueOf(++i);
						System.out.format("放入%s%n", valueOf);
						synchronousQueue.putFirst(valueOf);
						if(i==100)
						     break;
					} catch (InterruptedException e) {
					}
				}
			}
		}).start();
		
		Thread.sleep(1000L);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					try {
						if(synchronousQueue.peek()!=null){
							System.out.format("take:%s%n%n",synchronousQueue.takeFirst());
							Thread.sleep(100L);
						}else{
							 break;
						}
					} catch (InterruptedException e) {
					}
				}
			}
		}).start();
	}
	
    /**
     * 不同端存取：实现先进先出
     * @throws InterruptedException
     */
	public static void method1() throws InterruptedException {
		final LinkedBlockingDeque<String> synchronousQueue = new LinkedBlockingDeque<String>();
		new Thread(new Runnable() {
			@Override
			public void run() {
				int i = 0;
				while(true){
					try {
						String valueOf = String.valueOf(++i);
						System.out.format("放入%s%n", valueOf);
//						synchronousQueue.put(valueOf);
						//相当于
						synchronousQueue.putLast(valueOf);
						if(i==100)
						     break;
					} catch (InterruptedException e) {
					}
				}
			}
		}).start();
		
		Thread.sleep(1000L);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					try {
						if(synchronousQueue.peek()!=null){
							System.out.format("take:%s%n%n",synchronousQueue.take());
							//相当于
							System.out.format("take:%s%n%n",synchronousQueue.takeFirst());
							Thread.sleep(100L);
						}else{
							 break;
						}
					} catch (InterruptedException e) {
					}
				}
			}
		}).start();
	}
}
