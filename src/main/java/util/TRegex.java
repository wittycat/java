package util;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * Created by chenxun on 2017/9/23 22:32
 * http://www.runoob.com/java/java-regular-expressions.html
 */
public class TRegex {

    @Test
    public  void method1() {
        String content = "I am noob from runoob.com.";
        String pattern = ".*runoob.*";

        boolean isMatch = Pattern.matches(pattern, content);
        System.out.println("字符串中是否包含了 'runoob' 子字符串? " + isMatch);
    }

    /**
     * \s : 应该是space 表示一个空格
     * + ：表示可以有多个>=1
     */
    @Test
    public  void method3() {
        String content = "I am boy";
        String pattern = "I\\s+am\\s+boy";
        boolean isMatch = Pattern.matches(pattern, content);
        System.out.println(isMatch);
    }

    /**
     * 找去其中的数字 第一位为0 去掉  仅有一位是0保留
     * todo
     */
    @Test
    public  void method3_1() {
        String content = "0sv123SSECve10awev1aewf";
        char[] chars = content.toCharArray();
        String nums = "";
        for (int i = 0; i < chars.length; i++) {
            int t = (int)chars[i];
            if( 48<=t && t<=57){
                nums+=chars[i];
            }else {
                if(nums.length()>0){
                    System.out.println(nums);
                    nums = "";
                }
            }
        }
    }

    /**
     * 过滤其中的字母
     * 过滤其中的字符
     */
    @Test
    public  void method3_2() {
        String content = "sv123SSECve10awev1aewf00";
        String pattern = "\\d+";
        System.out.println(content.replaceAll("[(0-9)]","").toString());//svSSECveawevaewf
        System.out.println(content.replaceAll("[^0-9]","").toString());//12310100  替换非数字
    }

    /**
     * 去除所有空格
     */
    @Test
    public  void method3_3() {
        String content = " I am boy ";
        System.out.println(content.replaceAll("\\s","").toString());
    }


    /**
     * ^ :以什么开始
     * \d+: 一个或多个数字
     * ? 表示可选
     * \. 匹配 .
     * (pattern) :匹配pattern 这个子表达式  [] 里面是字符 数字等 也可以是表达式 其实也是特殊的表达式
     */
    @Test
    public  void method4() {
        String content = "5.5";
        String pattern = "^\\d+(\\.\\d+)?";
        boolean isMatch = Pattern.matches(pattern, content);
        System.out.println(isMatch);
    }

    /**
     * 其他的语言中，一个反斜杠\就足以具有转义的作用，
     * 而在正则表达式中则需要有两个反斜杠才能被解析为其他语言中的转义作用
     *
     *   \ \                   \\
     *   转义符        本身就得2个存在
     * 好比空格正则匹配：
     *  \ \                     s
     */
    @Test
    public  void method5() {
        String content = "\\";
        String pattern = "\\\\";
        boolean isMatch = Pattern.matches(pattern, content);
        System.out.println(isMatch);
    }
}
