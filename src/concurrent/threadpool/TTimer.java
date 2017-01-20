package concurrent.threadpool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/** 
 *
 *@Author chenxun
 *@Date 2017年1月20日 下午5:06:05 
 *@Reference:http://www.blogjava.net/xylz/archive/2011/01/10/342738.html
 *@Descript:
 * 		由于ScheduledExecutorService拥有Timer/TimerTask的全部特性，并且使用更简单，支持并发，而且更安全，
 * 因此没有理由继续使用Timer/TimerTask，完全可以全部替换。
 */
public class TTimer {
	
   static class TaskItem extends TimerTask{
	@Override
	public void run() {
		  System.out.println("执行---->"+new SimpleDateFormat("hh:mm:ss").format(new Date()));
	}
   }
   
   public static void main(String[] args) {
	   Timer timer = new Timer();
	   timer.schedule(new TaskItem(), 2000, 2000);
   }
}
