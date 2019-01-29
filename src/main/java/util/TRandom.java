package util;

import org.junit.Test;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by chenxun.
 * Date: 2019/1/28 下午4:29
 * Description:
 */
public class TRandom {

    @Test
    public  void method1() {
        for (int i = 0; i < 30; i++) {
            int j = new Random().nextInt(20);
            System.out.print(j+",");
        }
    }

    @Test
    public  void method2() {
        Random random = new Random();
        List list = new ArrayList();
        for (int i = 0; i < 30; i++) {
            int j = random.nextInt(20);
            list.add(j);
        }
        list.stream().sorted().forEach(i-> System.out.print(i+","));

        System.out.println("");

        SecureRandom secureRandom = new SecureRandom();
        List list2 = new ArrayList();
        for (int i = 0; i < 30; i++) {
            int j = secureRandom.nextInt(20);
            list2.add(j);
        }
        list2.stream().sorted().forEach(i-> System.out.print(i+","));
    }
}