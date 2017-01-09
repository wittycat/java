package concurrent.collection;

import java.util.concurrent.PriorityBlockingQueue;

/** 
 * @Author:chenxun
 * @2017年1月9日 上午12:35:05 
 * @Theme:
 * @Reference:
 * @Descript:支持排序的排序的误解阻塞队列
 *  入队相同（值相等）元素不会覆盖，出对从队列头出队
 */
public class TPriorityBlockingQueue {
	public static void main(String[] args) throws InterruptedException {
		PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<Integer>();
		queue.put(1);
		queue.put(1);
		queue.put(3);
		queue.put(2);
		
		while(queue.size()>0){
			System.out.println(queue.take());
		}
	}
}
