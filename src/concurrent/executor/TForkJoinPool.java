package concurrent.executor;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author chenxun
 * @date 2017年1月19日 下午3:22:45
 * @参考 http://www.infoq.com/cn/articles/fork-join-introduction
 * 
 * 1.Fork/Join实现了“工作窃取算法”，ForkJoinTask需要通过ForkJoinPool来执行，任务分割出的子任务会添加到当前工作线程所维护的双端队列中，
 *   进入队列的头部。当一个工作线程的队列里暂时没有任务时，它会随机从其他工作线程的队列的尾部获取一个任务。
 * 2.Fork/Join框架的设计主要2步任务
 *   第一步分割任务。首先我们需要有一个fork类来把大任务分割成子任务，有可能子任务还是很大，所以还需要不停的分割，直到分割出的子任务足够小。
 *   第二步执行任务并合并结果。分割的子任务分别放在双端队列里，然后几个启动线程分别从双端队列里获取任务执行。子任务执行完的结果都统一放在一
 *   个队列里，启动一个线程从队列里拿数据，然后合并这些数据。
 * 3.应用
 *   第一步：创建任务job，继承ForkJoinTask的子类:
 *   		-----RecursiveAction：用于没有返回结果的任务。
 *   		-----RecursiveTask ：用于有返回结果的任务。
 *   第二步：提交job到ForkJoinPool(获取结果如果有)
 *
 */
public class TForkJoinPool {

	@SuppressWarnings("serial")
	static class Job extends RecursiveTask<Integer> {
		
		public	static ConcurrentSkipListMap<String, AtomicInteger> map = new ConcurrentSkipListMap<String, AtomicInteger>();
		
		int i;

		public Job(int i) {
			/**
			 * 统计最终分解到了多少个线程去执行
			 */
			AtomicInteger atomicInteger = map.putIfAbsent(Thread.currentThread().getName(),new AtomicInteger(1));
			if(atomicInteger!=null)
					atomicInteger.incrementAndGet();
			this.i = i;
		}

		@Override
		protected Integer compute() {
			if (i >= 500) {
				return taskItem(i);
			}

			Job job = new Job(++i);
			job.fork();

			return taskItem(i) + job.join();
		}
		
		private Integer taskItem(int i){
			if(i==50){
//				throw new RuntimeException("异常：i="+i);
			}
			return i*i;
		}
	}
	
	

	public static void main(String[] args) {
		
		Job mt = new Job(1);
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		
		Future<Integer> result = forkJoinPool.submit(mt);
		try {
			/**
			 * 获取异常
			 */
//			if(mt.isCompletedAbnormally()){
//				System.out.println("异常："+mt.getException());
//			}
			System.out.println(result.get());
			System.out.println(forkJoinPool.toString());
			System.out.println(Job.map.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		forkJoinPool.shutdown();
	}
}
