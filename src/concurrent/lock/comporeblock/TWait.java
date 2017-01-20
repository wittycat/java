package concurrent.lock.comporeblock;
/** 
 * @author:chenxun
 * @2016年12月27日 上午12:05:35 
 * @Theme:
 * @Reference:http://www.cnblogs.com/skywang12345/p/3505784.html
 * @Descript:Condition和synchronized依赖锁
 * 针对锁的锁唯一的监听器去让线程等待或唤醒
 */
public class TWait {
	public static void main(String[] args) {
		  
		          ThreadA ta = new ThreadA("ta");
		  
		          synchronized(ta) { // 通过synchronized(ta)获取“对象ta的同步锁”
		              try {
		                 System.out.println(Thread.currentThread().getName()+" start ta");
		                 ta.start();
		 
		                 System.out.println(Thread.currentThread().getName()+" block");
		                 // 主线程等待
		                 ta.wait();
		
		                 System.out.println(Thread.currentThread().getName()+" continue");
		             } catch (InterruptedException e) {
		                 e.printStackTrace();
		             }
		         }
		     }
		 
		     static class ThreadA extends Thread{
		 
		         public ThreadA(String name) {
		             super(name);
		         }
		 
		         public void run() {
		             synchronized (this) { // 通过synchronized(this)获取“当前对象的同步锁”
		                 System.out.println(Thread.currentThread().getName()+" wakup others");
		                 this.notify();    // 唤醒“当前对象上的等待线程”
		             }
		         }
		     }
}
