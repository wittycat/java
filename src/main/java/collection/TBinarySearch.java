package collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/** 
 * @author:chenxun
 * @2016年12月28日 下午10:33:29 
 * @Theme:
 * @Reference:
 * @Descript:使用非线性检索：
 *     在ArrayList中存大量数据时，
 *     使用验证ArrayList通过使用Collections.binarySearch（二分法）查找比ArrayList.indexOf快
 */
public class TBinarySearch {
	private static List<String> list = new ArrayList<String>(1000000);

	@Before
	public void initData() {
		for (int i = 0; i < 1000000; i++) {
			list.add("element" + i);
		}
		System.out.println("@Before");
	}

	@Test
	public void TestIndexOf() {

		long start = ComporeTime.start();
		/**
		 * 返回此列表中首次出现的指定元素的索引，或如果此列表不包含元素，则返回 -1
		 * 逐个对比寻找
		 */
		int indexOf = list.indexOf("element999999");
		System.out.println("indexOf:" + indexOf);
		long end = ComporeTime.end();
		ComporeTime.minus(end, start);
		
		
		Collections.sort(list);
		long start2 = ComporeTime.start();
		/**
		 * 使用二分搜索法搜索指定列表，以获得指定对象。在进行此调用之前，必须根据列表元素的自然顺序对列表进行升序排序（通过 sort(List)
		 * 方法）。如果没有对列表进行排序，则结果是不确定的。如果列表包含多个等于指定对象的元素，则无法保证找到的是哪一个。
		 */
		int binarySearch = Collections.binarySearch(list, "element999999");
		System.out.println("binarySearch:" + binarySearch);
		long end2 = ComporeTime.end();
		ComporeTime.minus(end2, start2);
	}
}
