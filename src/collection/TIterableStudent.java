package collection;

import java.util.Iterator;

/** 
 * @author：chenxun
 * createDate：2016年11月21日 下午11:30:04 
 * Theme:
 * reference:
 * descript:
 */
public class TIterableStudent {
	
	public static void main(String[] args) {
		StudentIterable studentS = new StudentIterable(10);
		for (Student student : studentS) {
			System.out.println(student.toString());
		}
	}
	
    private static class StudentIterable implements Iterable<Student>{
		
        private Student[]  students;
        
		public StudentIterable(int size) {
			this.students = new Student[size];
			for (int i = 0; i < size; i++) {
				students[i] = new Student("student_"+(i+1), 20+i);
			}
		}

		@Override
		public Iterator<Student> iterator() {
			return new StudentIterator();
		}
		
		private class StudentIterator implements Iterator<Student>{
			
            private int index = 0;
            
			@Override
			public boolean hasNext() {
				return index!=students.length;
			}

			@Override
			public Student next() {
				return students[index++];
			}
			
		}
	}
	
	private static class Student {
		private String name;
		private Integer age;

		public Student(String name, Integer age) {
			super();
			this.name = name;
			this.age = age;
		}

		@SuppressWarnings("unused")
		public String getName() {
			return name;
		}

		@SuppressWarnings("unused")
		public void setName(String name) {
			this.name = name;
		}

		@SuppressWarnings("unused")
		public Integer getAge() {
			return age;
		}

		@SuppressWarnings("unused")
		public void setAge(Integer age) {
			this.age = age;
		}

		@Override
		public String toString() {
			return "Student [name=" + name + ", age=" + age + "]";
		}

	}
}
