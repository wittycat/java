package io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.junit.Test;

/**
 * @author：chenxun 
 * createDate：2016年12月11日 下午8:53:34 
 * Theme: 
 * reference: descript:
 */
public class TObjectInputStreamAndOutPut {
	
	@Test
	public void read() throws FileNotFoundException, IOException, ClassNotFoundException{
		ObjectInputStream iInputStream = new ObjectInputStream(new FileInputStream("d:/io/classs.tmp"));
		Person p =(Person)iInputStream.readObject();
		System.out.println(p);
		System.out.println(iInputStream.readObject());
	    p =(Person)iInputStream.readObject();
		System.out.println(p);
		iInputStream.close();
	}
	/**
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * 1.一个读对应一个写（不管什么类型当做一个整体看待）
	 * 2.transient 和static 不支持写入文件
	 * 3.序列的对象不支持内部类的写法
	 * 
	 */
	@Test
	public void write() throws FileNotFoundException, IOException{
	     ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("d:/io/classs.tmp"));
	     Person person = new Person("cx1", 26,180,"hobby");
	     System.out.println(person);
	     outputStream.writeObject(person);
		 outputStream.writeObject("stop");
		 person = new Person("cx2", 26,180,"hobby");
		 System.out.println(person);
		 outputStream.writeObject(person);
		 outputStream.close();
	}

	class Person implements Serializable {

		private static final long serialVersionUID = 1L;

		private String name;
		private Integer age;
		private Integer height;
		private transient String hobby;

		public Person() {
			super();
		}

		public Person(String name, Integer age,Integer height , String hobby) {
			super();
			this.name = name;
			this.age = age;
			this.hobby = hobby;
			this.height = height;
		}


		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getAge() {
			return age;
		}

		public void setAge(Integer age) {
			this.age = age;
		}

		public  Integer getHeight() {
			return height;
		}

		public  void setHeight(Integer height) {
			this.height = height;
		}

		public String getHobby() {
			return hobby;
		}

		public void setHobby(String hobby) {
			this.hobby = hobby;
		}

		@Override
		public String toString() {
			return "Person [name=" + name + ", age=" + age +",height="+height+ ", hobby=" + hobby
					+ "]";
		}
	}
}

