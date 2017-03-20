package concurrent.collection.beginconcurrent;

import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Random;
import java.util.concurrent.ConcurrentSkipListMap;

/** 
 * @Author:chenxun
 * @2017年1月15日 上午4:23:09 
 * @Theme:
 * @Reference:http://blog.csdn.net/sunxianghuang/article/details/52221913
 * @Descript:
 * 1.代码体积约3500行
 * 2.ConcurrentSkipListMap 是TreeMap的多线的安全版本
 * 3.数据结构使用跳表保存数据，实质为一种链表
 * 4.使用CAS保证安全性
 * 
 **********************************************************************************
 *                理解<无锁编程（lock free）>概念
 * 	     常见的lock free编程一般是基于CAS(Compare And Swap)操作：CAS(void *ptr, Any oldValue, 
 * Any newValue);即查看内存地址ptr处的值，如果为oldValue则将其改为newValue，并返回true，否则返回false。
 * X86平台上的CAS操作一般是通过CPU的CMPXCHG指令来完成的。CPU在执行此指令时会首先锁住CPU总线，禁止其它核心对内
 * 存的访问，然后再查看或修改*ptr的值。
 *    简单的说CAS利用了CPU的硬件锁来实现对共享资源的串行使用。
 * 优点：
 * 		a、开销较小：不需要进入内核，不需要切换线程；
 * 		b、没有死锁：总线锁最长持续为一次read+write的时间；
 * 		c、只有写操作需要使用CAS，读操作与串行代码完全相同，可实现读写不互斥。
 * 缺点：
 * 		a、编程非常复杂，两行代码之间可能发生任何事，很多常识性的假设都不成立。
 *		b、CAS模型覆盖的情况非常少，无法用CAS实现原子的复数操作。
 **********************************************************************************
 *									Key-Value数据结构
 *    目前常用的key-value数据结构有三种：Hash表、红黑树、SkipList，它们各自有着不同的优缺点（不考虑删除操作）：
 *		a、Hash表，即数组：例如：Hashmap（Hash表+链表+红黑树），HashTbale（Hash表+链表） 插入、查找最快，为O(1)；
 *             如使用链表实现则可实现无锁；数据有序化需要显式的排序操作。
 *		b、红黑树：例如：TreeMap 插入、查找为O(logn)，但常数项较小；无锁实现的复杂性很高，一般需要加锁；数据天然有序。
 *		c、SkipList：例如：ConcurrentSkipListMap,插入、查找为O(logn)，但常数项比红黑树要大；底层结构为链表，可无锁实现；数据天然有序。
 */
public class TConcurrentSkipListMap {
	public static void main(String[] args) {
		ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<String, String>();
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			map.put(String.valueOf(random.nextInt(100)), String.valueOf(i));
		}
		NavigableSet<String> descendingKeySet = map.descendingKeySet();
		Iterator<String> iterator = descendingKeySet.iterator();
		while(iterator.hasNext()){
			String next = iterator.next();
			System.out.format("%s=%s%n", next,map.get(next));
		}
	}
}
