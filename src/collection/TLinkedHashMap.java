package collection;

import java.util.Iterator;
import java.util.LinkedHashMap;

/** 
 * @author：chenxun
 * createDate：2016年12月5日 下午11:37:06 
 * Theme:
 * reference:
 * descript:
 */
public class TLinkedHashMap {
   /**
    * 按元素放入顺序 遍历输出
    * @param args
    */
   public static void main(String[] args) {
	  LinkedHashMap<Integer, Integer> linkedHashMap = new LinkedHashMap<Integer, Integer>();
	  for (int i = 0; i < 10; i++) {
		  linkedHashMap.put(i, i);
		  linkedHashMap.put(1000, 1000);
	  }
	  
	  Iterator<Integer> iterator = linkedHashMap.keySet().iterator();
	  while (iterator.hasNext()) {
		  Integer key = iterator.next();
		  System.out.println(linkedHashMap.get(key));
	  }
   }
}
