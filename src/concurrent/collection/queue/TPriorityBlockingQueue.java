package concurrent.collection.queue;

import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

/** 
 * @Author:chenxun
 * @2017年1月9日 上午12:35:05 
 * @Theme:
 * @Reference:
 * @Descript:
 */
public class TPriorityBlockingQueue {
	public static void main(String[] args) throws InterruptedException {
		tPriorityBlockingQueue();
//		tPriorityQueue();
		
//		/**
//		 * 移位不管是有无符号移位都是往下取舍
//		 */
//		for (int i = 0; i < 20; i++) {
//			System.out.print(i+":"+(i>>>1));
//			System.out.println("####"+i+":"+(i>>1));
//		}
	}
	/**
	 * 最小堆：线程不安全无界队列
	 * 1.此实现不是同步的。如果多个线程中的任意线程修改了队列，则这些线程不应同时访问 PriorityQueue 实例。
	 * 相反，请使用线程安全的 PriorityBlockingQueue 类。 
	 * 2.PriorityQueue存储元素为数组，数组的序列按照最小堆的（根元素最小，孩子始终大于父元素）序列
	 *   公式又最小堆元素和数组索引关系得出
	 *   parentIndex = （当前元素E的索引号-1）>>>1;【减1是因为数组索引从0开始】
	 * 3.添加元素时，并自下而上把新元素调整到合适位置
	 *   堆顶移除元素时（堆顶元素最小，就是数组的0索引的位置），根据公式找到孩子，逐渐自下而上使堆顶为最小元素，下面小孩子逐渐上移
	 */
	public static void tPriorityQueue() {
		PriorityQueue<Integer> queue = new PriorityQueue<Integer>();
		
		queue.add(1);
		System.out.println(queue.toString());
		queue.add(1);
		System.out.println(queue.toString());
		queue.add(3);
		System.out.println(queue.toString());
		queue.add(9);
		System.out.println(queue.toString());
		queue.add(2);
		System.out.println(queue.toString());
		
		while(queue.size()>0){
			System.out.println(queue.poll());
		}
	}
    /**
     * 最小堆：线程安全无界阻塞队列
     * 1.支持排序的无界阻塞队列，它使用与类 PriorityQueue 相同的顺序规则，内部都采用数组
     * 2.入队相同（值相等）元素不会覆盖，出对从队列头出队
     * @throws InterruptedException
     */
	public static void tPriorityBlockingQueue() throws InterruptedException {
		PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<Integer>();
		queue.put(1);
		queue.put(1);
		queue.put(3);
		queue.add(9);
		queue.put(2);
		System.out.println(queue.toString());
		while(queue.size()>0){
			System.out.println(queue.take());
		}
	}
}
