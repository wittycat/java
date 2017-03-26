package collection;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.junit.Test;

/** 
 * @author：chenxun
 * createDate：2016年11月24日 下午9:10:20 
 * Theme:
 * reference:
 * descript:
 */
public class THashMap {
	
	@Test
    public  void yiwei() {
		//被移位操作的底数为1，切记不是数学中的2的多少次方，
		System.out.println(1 << 1);//等于2的1次方   ，相当于把1移1位到2
		System.out.println(1 << 3);//等于2的3次方   ，相当于把1移1位到2，在移一位为4，在移为8
		System.out.println(1 << 30);
	}
	
	@Test
    public  void createHashMap() {
    	HashMap<String, String> map = new HashMap<String, String>();
    	String k = null;
    	for (int i = 0; i < 10; i++) {
    		k = UUID.randomUUID().toString();
    		map.put(k, "value");
		}
    	map.get(k);
    	map.remove(k);
    	
    	Set<Entry<String, String>> entrySet = map.entrySet();
    	for (Entry<String, String> entry : entrySet) {
			System.out.println(entry.toString());
		}
	}
	
	@Test
    public  void intiCreateHashMap() {
    	HashMap<String, String> map = new HashMap<String, String>(32);
    	String k = null;
    	for (int i = 0; i < 100000; i++) {
    		k = i+"";
    		map.put(k, "value");
		}
	}
	
	@Test
	public  void keyHash() {
		HashMap<MyObject,Integer> map = new HashMap<MyObject,Integer>();
		MyObject myobj = null;
		for (int i = 0; i < 200; i++) {
		     myobj = new MyObject();
//			int h = myobj.hashCode();
//			String s= h+":"+(h^ (h >>> 16));
//			System.out.println(s);
			map.put(myobj, 0);
		}
		map.remove(myobj);
		System.out.println(map.size());;
	}
	/**
	 * 参考：http://www.w2bc.com/article/155537
	 * 情况一：通过断点调节测试多次：只出现了丢失元素的情况;
	 * 原因是线程1把原table1表指向了新建的table2表，还没有来得及添加
	 * 线程2在扩容时使用了table2，没有可以挪动的元素导致线程2丢失了执行扩容方法之前添加的元素
	 * 
	 * 情况二：没有出现扩容形成的闭合回路，应该不会出现
	 * 
	 */
	@Test
	public  void dropDeadHalt() {
		final HashMap<Integer,String> map = new HashMap<Integer,String>(2,0.75f);
		map.put(5, "C");
		System.out.println(map);
		new Thread("Thread1"){
			@Override
			public void run() {
				map.put(3, "A");
				System.out.println(map);
			}
		}.start();
		new Thread("Thread2"){
			@Override
			public void run() {
				map.put(7, "B");
				System.out.println(map);
			}
		}.start();
	}
	
	class MyObject implements Comparable<MyObject>{
		@Override
		public int hashCode() {
			return 1;
		}
		@Override
		public boolean equals(Object obj) {
			// TODO Auto-generated method stub
			return super.equals(obj);
		}
		@Override
		public int compareTo(MyObject o) {
			return 0;
		}
	}
	
	@Test
	public  void tableSizeFor() {
		    int MAXIMUM_CAPACITY = 1 << 30;
		    String r=null;
		    for (int i = 0; i < 1000; i++) {
		    	int cap = i;
		    	r = i+"==";
		        int n = cap - 1;
		        r += n+"==";
		        n |= n >>> 1;
		        r += n+"==";
		        n |= n >>> 2;
		        r += n+"==";
		        n |= n >>> 4;
		        r += n+"==";
		        n |= n >>> 8;
		        r += n+"==";
		        n |= n >>> 16;
		        r += n+"==";
		        cap =  (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
		        r += cap;
		        System.out.println(r);
			}
	 }
	/**
	 * 引用地址传递
	 * 始终数组中的一个对象
	 */
	@Test
	public  void referenceTransfer () {
		Person[] parr = new Person[5];
		Person person = new Person();
		person.setName("P1"); 
		parr[4] = person;
		System.out.println(parr[4].toString());//Person [name=P1]
		Person person2 = parr[4];
		Person person3 =  person2;
		person3.setName("P3");
		System.out.println(parr[4].toString());//Person [name=P3]
	}
	class Person{
		private String name ;
		public String getName() {return name;}
		public void setName(String name) {this.name = name;}
		@Override
		public String toString() {return "Person [name=" + name + "]";}
		
	}
    
}
