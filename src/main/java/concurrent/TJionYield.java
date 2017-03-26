package concurrent;
/** 
 *  <br/> author:chenxun
 *  <br/> create：2016年12月6日 下午3:35:19 
 *  <br/> description：
 * 1.join主动抢占执行权限，执行完成后，其他的才能执行
 * 2.yield主动放弃执行权限，其他的执行完成后，自己执行，可以和Thread.activeCount() 结合使用
 */
public class TJionYield {
   public static void main(String[] args) throws InterruptedException {
//	   Thread thread = new Thread(new Runnable() {
//		@Override
//		public void run() {
//			try {
//				Thread.sleep(3000);
//				 System.out.println(Thread.currentThread().getName());
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//	  });
//	  thread.start();
//	  thread.join();
//	  System.out.println("main线程");
	  
	  Thread thread = new Thread(new Runnable() {
		  @Override
		  public void run() {
			  try {
				  Thread.sleep(3000);
				  System.out.println(Thread.currentThread().toString());
			  } catch (InterruptedException e) {
				  e.printStackTrace();
			  }
		  }
	  });
	  thread.start();
	  while (Thread.activeCount()>1)
		  Thread.yield();
	  System.out.println("main线程");
   }
}
