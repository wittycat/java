package concurrent.collection.beginconcurrent;

import java.util.concurrent.ConcurrentHashMap;

/** 
 * @Author:chenxun
 * @2017年1月14日 下午6:34:35 
 * @Theme:
 * @Reference:http://www.cnblogs.com/everSeeker/p/5601861.html
 * @Descript:
 * 1.代码体积约6000行
 * 2.java1.8 ConcurrentHashMap 和  HashMap 结构一样 ，在put操作时，当数组索引位置大于1时进行加锁操作
 * 3.size()(或 mappingCount()) 使用无锁操作，返回并非准确值, 因为此时可能有线程对集合进行删除或增加操作
 * 4.比较：
 *   java1.7
 *   1.版本采用Segment<K,V>[] segments数组，Segment继承ReentrantLock，实现锁分段，进行安全操作
 *   2.每个Segment相当于一个老版本的hashMap(数据结构为：table数组＋单向链表的数据结构)
 *   3.get时  取不到值最后在加锁取一次
 *   java1.8
 *   1.取消segments字段，直接采用 HashEntry<K,V>[] table保存数据
 *   2.数据结构改为：变更为table数组＋单向链表(或红黑树)
 *   3.get() 使用无锁操作，取不到直接返回空
 * 5.1.8中的锁力度更小，数据结构更简单
 * 6.CounterCell何时使用 没有理解透彻
 */
public class TConcurrentHashMap {
	static class Person{
		private int age;
		
		public Person(int age) {
			super();
			this.age = age;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		/**
		 * 使元素处于同一个索引位置
		 */
		@Override
		public int hashCode() {
			return age;
		}
	}
	public static void main(String[] args) {
		
		ConcurrentHashMap<Person, String> map = new ConcurrentHashMap<Person, String>();
		map.put(new Person(1), String.valueOf(1));
		map.put(new Person(1), String.valueOf(1));
		map.put(new Person(1), String.valueOf(1));
		map.put(new Person(1), String.valueOf(1));
		map.put(new Person(1), String.valueOf(1));
		map.put(new Person(1), String.valueOf(1));
		map.put(new Person(1), String.valueOf(1));
		map.put(new Person(1), String.valueOf(1));
		map.put(new Person(1), String.valueOf(1));
		map.put(new Person(1), String.valueOf(1));
		
		map.put(new Person(2), String.valueOf(1));
		map.put(new Person(2), String.valueOf(1));
		
		System.out.println(map.size());
	}
}
