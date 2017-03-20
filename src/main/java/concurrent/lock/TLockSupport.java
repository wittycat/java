package concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import collection.ComporeTime;

/** 
 * @Author:chenxun
 * @2016年12月26日 下午9:35:37 
 * @Theme:
 * @Reference:
 * @Descript:
 */
public class TLockSupport {
       public static void main(String[] args) throws InterruptedException {
    	   Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println("before");
					
					//一组 
//					LockSupport.park(this);//LockSupport.getBlocker(thread) 返回this 对象
//					LockSupport.park();//LockSupport.getBlocker(thread) 返回null
					
					Long start = ComporeTime.start();
					//一组 
//					LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(6));
//					LockSupport.parkNanos(this,TimeUnit.SECONDS.toNanos(4));
					
					//一组 TODO???
//					LockSupport.parkUntil(TimeUnit.SECONDS.toNanos(2));
					LockSupport.parkUntil(this,TimeUnit.SECONDS.toNanos(2));
					
					ComporeTime.getOutMinus(start);
					
					System.out.format("in -blocker:%s%n",this.toString());
					System.out.println("after");
				}
		   });
    	   thread.start();
    	   
    	   Thread.sleep(5000L);
    	   
//    	   System.out.println(Thread.activeCount());
    	   System.out.format("thread:%s%n",thread.toString());
    	   
    	   Object blocker = LockSupport.getBlocker(thread);
    	   System.out.format("out-blocker:%s%n",blocker==null?"null":blocker.toString());
    	   
    	   LockSupport.unpark(thread);
    	   
	   }
}
