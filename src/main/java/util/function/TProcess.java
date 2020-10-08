package util.function;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by chenxun.
 * Date: 2020/10/2 上午12:05
 * Description:
 */
public class TProcess {

    public static void main(String[] args) {
        String[] stringArr = {"you", "me", "she", "he"};
        Stream<String> stream1 = Arrays.stream(stringArr);
        Stream<String> stream2 = stream1.map(s -> s + "11");
        Stream<String> stream3 = stream2.limit(2);
        List<String> collect = stream3.collect(Collectors.toList());
        collect.forEach(System.out::println);
    }
}