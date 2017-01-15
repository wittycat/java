package concurrent.collection.cow;
/** 
 *
 * @author chenxun
 * @date 2017年1月13日 上午11:40:36
 * @Theme:
 * @Reference:
 * @Descript :System.arraycopy(包括Arrays.copyOf) 都是拷贝引用
 *
 */
public class TSystemArrayCopy {
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
	}
	/**
	 * 
	 * System.arraycopy(包括Arrays.copyOf) 都是拷贝引用
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		Person[] p = new Person[2];
		Person p0 = new Person(0);
		Person p1 = new Person(1);
		p[0] = p0;
		p[1] = p1;
		
		Person[] dest = new Person[3];
		
		System.arraycopy(p, 0, dest, 0, p.length);
		
		System.out.format("src ->e1:%s,e2:%s%n", p[0].hashCode(),p[1].hashCode());
		System.out.format("dest->e1:%s,e2:%s%n", dest[0].hashCode(),dest[1].hashCode());
	}
}
