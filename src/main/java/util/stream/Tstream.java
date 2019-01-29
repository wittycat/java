package util.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Created by chenxun.
 * Date: 2018/12/2 下午11:44
 * Description:
 */
public class Tstream {

    /**
     *
     */
    @Test
    public  void findAnyAndFindFirst() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        //第一个执行完成的
        Optional<Integer> findAny1 = numbers.parallelStream().map(integer -> integer + 3).findAny();
        System.out.println(findAny1.get());
        //严格描述流的第一个元素
        Optional<Integer> first = numbers.parallelStream().map(integer -> integer + 3).findFirst();
        System.out.println(first.get());

        Optional<Integer> findAny2 = numbers.stream().map(integer -> integer + 3).findAny();
        System.out.println(findAny2.get());
        Optional<Integer> first1 = numbers.stream().map(integer -> integer + 3).findFirst();
        System.out.println(first1.get());
    }

    class Employee{
        private String name;
        private Integer age;
        private Integer salary;

        Employee(String name,Integer age,Integer salary){
            this.name = name;
            this.age  = age;
            this.salary = salary;
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

        public Integer getSalary() {
            return salary;
        }

        public void setSalary(Integer salary) {
            this.salary = salary;
        }

        @Override
        public String toString() {
            return "Employee{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", salary=" + salary +
                    '}';
        }
    }

    /**
     * 1.两次排序
     * 2.排序反转
     *
     */
    @Test
    public void comparing() {
        List<Employee> list= Arrays.asList(
                new Employee("小明1", 12, 8000),
                new Employee("小明2", 84, 7000),
                new Employee("小明3", 36, 7500),
                new Employee("小明4", 36, 5000),
                new Employee("小明5", 12, 6000)
        );
        list.sort(Comparator.comparingInt(Employee::getAge).reversed().thenComparingDouble(Employee::getSalary));
        list.forEach(System.out::println);
    }


    @Test
    public void comparing2() {
        System.out.println(Integer.toBinaryString(Integer.MAX_VALUE));
        System.out.println(Integer.toBinaryString(Integer.MAX_VALUE+1));
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.toBinaryString(Integer.MIN_VALUE));
        System.out.println(Integer.toBinaryString(Integer.MIN_VALUE+1));
        System.out.println(Integer.MIN_VALUE);
    }
}