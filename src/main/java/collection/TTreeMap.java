package collection;

import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

/** 
 * @author：chenxun
 * createDate：2016年11月30日 下午10:09:56 
 * Theme:
 * reference:
 * descript:
 */
public class TTreeMap {
  
  @Test
  public void testDefaultComparator(){
	  Map<TTreeMap.Person,Integer> treeMap = new TreeMap<TTreeMap.Person,Integer>();
	  for (int i = 0; i < 10; i++) {
		  treeMap.put(new TTreeMap.Person(String.valueOf(i)), i);
	  }
//	  System.out.println(treeMap.size());
//	  Set<Person> keySet = treeMap.keySet();
//	  for (Person person : keySet) {
//		  System.out.println(person.toString());;
//	  }
//	  Set<Person> keySet = treeMap.keySet();
//	  Iterator<Person> iterator = keySet.iterator();
//	  if(iterator.hasNext()){
//		  System.out.println(iterator.next());;
//	  }
  }
  static class Person implements  Comparable<Person>{
	  public Person(String name) {
		  this.name = name;
	  }

	  public Person() {
	  }

	  private String name;

	  public void setName(String name) {
		  this.name = name;
	  }

	  public String getName() {
		  return name;
	  }

	  @Override
	  public int compareTo(Person o) {
		  return o.getName().compareTo(this.getName());
	  }
  }
}
