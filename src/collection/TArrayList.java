package collection;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/** 
 * @author：chenxun
 * 创建时间：2016年5月29日 下午10:09:34 
 * 参考：
 * 说明：
 */
public class TArrayList {
	
	/**
	 * ArrayList 底层为数组
	 * 1.ArrayList创建无元素时 数组大小为0
	 * 2.ArrayList创建有素时 数组大小为默认的10
	 * 3.ArrayList的elementData大于默认时以elementData>>1 (二分子一的方式扩容)
	 *   注意java1.8 扩容方法在grow() 中为原数组1.5倍  old+old>>1
	 *      java1.6 扩容方法在ensureCapacity() 中为原数组1.5倍+1   计算公式为：old*3/2+1
	 *   为什么是1.5倍？1.5倍时，数组暂时浪费33%的无用内存，过大浪费内存多，过小则需要多次分配内存；经测试1.5配满足性能要求
	 * 4.其他的集合的扩容方式
	 *   Vector和ArrayLsit类似，多了一个安自定义长度（变量capacityIncrement）进行扩容的方式，否则按2倍扩容
	 *   Stack继承Vector，和Vector 类似
	 *   HashMap按倍数扩容
	 */
	@Test
    public void testArrayListSize() {
		// TODO Auto-generated method stub
    	 List<Integer> list = new ArrayList<Integer>();
    	 System.out.println("ArrayList有0个元素大小："+list.size());//elementData = 0
    	 list.add(1);
    	 System.out.println("ArrayList有1个元素大小："+list.size());//elementData =10
    	 for (int i = 1; i < 10; i++) {
    		 list.add(i);
		 }
    	 System.out.println("ArrayList有10个元素大小："+list.size());//elementData =10
    	 list.add(10);
    	 System.out.println("ArrayList有11个元素大小："+list.size());//elementData =15
	}
}
