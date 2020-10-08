package util.function;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Created by chenxun.
 * Date: 2020/10/2 上午12:05
 * Description:
 */
public class TCreateStream {

    public static void main(String[] args) {

        // 1、使用数组
        String[] arr = { "ma", "zhi", "chu", "is", "java", "developer", "family" };
        Stream<String> stream = Stream.of(arr);
        System.out.println(Arrays.toString(stream.toArray(String[]::new)));

        String[] stringArr = {"ma", "zhi", "chu", "love"};
        Stream<String> stream1 = Arrays.stream(stringArr);
        System.out.println(Arrays.toString(stream1.toArray(String[]::new)));


        // 2、使用Collections
        List<String> list = new ArrayList<String>();
        list.add("ma");
        list.add("zhi");
        list.add("chu");
        Stream<String> stream2 = list.stream();
        stream2.forEach(l->{
            System.out.println(l);
        });


        // 3、使用Stream.generate()
        Stream<String> stream3 = Stream.generate(() -> "love").limit(10);
        String[] strArr3 = stream3.toArray(String[]::new);
        System.out.println(Arrays.toString(strArr3));

        // 4、使用Stream.iterate()
        Stream<BigInteger> bigIntStream = Stream.iterate(BigInteger.ZERO, n -> n.add(BigInteger.ONE)).limit(10);
        BigInteger[] bigIntArr = bigIntStream.toArray(BigInteger[]::new);
        System.out.println(Arrays.toString(bigIntArr));

        // 5、使用Popular APIs
        String sentence = "ma zhi chu is a Java wechat official account.";
        Stream<String> wordStream = Pattern.compile("\\W").splitAsStream(sentence);
        String[] wordArr = wordStream.toArray(String[]::new);
        System.out.println(Arrays.toString(wordArr));
    }

}