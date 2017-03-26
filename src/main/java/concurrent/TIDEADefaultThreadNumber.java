package concurrent;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * Created by chenxun on 2017/3/26.
 *
 */
public class TIDEADefaultThreadNumber {
    
    public static void main(String[] args) {

        /**
         * 关于Thread.activeCount() 在ecplise和idea下的返回值不同
         * idea:下获取的线程数为为2  ，main线程和Monitor Ctrl-Break 线程
         * ecplise:下获取的线程数为1，只有main线程
         *
         * 注意：对于为1是ecplise下的写法 ，在idea下测试修改成2
         * <code>
         * while(Thread.activeCount()<1)
         * Thread.yield();
         * </code>
         */
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        Thread[] threads = new Thread[Thread.activeCount()];
        int enumerate = threadGroup.enumerate(threads);
        for (Thread t : threads) {
            System.out.println(t.getName());
        }

        System.out.println("===========================================");

        /**
         * java程序运行的不仅仅是main方法的运行，而是main线程和多个其他线程的同时运行
         */
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("[" + threadInfo.getThreadId() + "]" + threadInfo.getThreadName());
        }

    }
}
