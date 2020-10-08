package util.function;

import org.junit.Test;

/**
 * Created by chenxun.
 * Date: 2020/10/1 下午10:30
 * Description: https://www.cnblogs.com/noteless/p/9505098.html#8
 */
public class TFunctionInterface {

    @FunctionalInterface
    public interface TestFunctionInterface<T> {
        void print(T t);
    }

    @Test
    public  void test1() {
        TestFunctionInterface tFunctionInterface = o -> System.out.println(o);
        tFunctionInterface.print("函数式接口");
    }
}