package concurrent.threadpool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/** 
 *
 * @Author chenxun
 * @Date 2017年1月20日 下午5:06:05 
 *
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
