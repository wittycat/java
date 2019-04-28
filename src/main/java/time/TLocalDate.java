package time;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * Created by chenxun.
 * Date: 2019/1/11 下午5:56
 * Description:
 *
 */

public class TLocalDate {

    /**
     *
     * 设置获取
     *
     * LocalDate
     *
     * LocalTime
     *
     * LocalDateTime ，LocalDateTime代替 Calendar，
     */
    @Test
    public  void method1() {

        System.out.println(LocalDate.now());
        System.out.println(LocalDate.of(2018,12,12));

        System.out.println(LocalTime.now());
        System.out.println(LocalTime.of(5,5,5,5));

        System.out.println(LocalDateTime.now());
        System.out.println(LocalDateTime.of(2018,12,12,12,12,12,111));

        System.out.println(Instant.now());
    }

    /**
     *
     * Instant ，Instant代替 Date，
     * Instant 类代表的是某个时间（有点像 java.util.Date），它是精确到纳秒的（而不是象旧版本的Date精确到毫秒）。
     * 如果使用纳秒去表示一个时间则原来使用一位Long类型是不够的，需要占用更多一点的存储空间，实际上其内部是由两个Long字段组成。
     * 第一个部分保存的是自标准Java计算时代（就是1970年1月1日开始）到现在的秒数，第二部分保存的是纳秒数（永远不会超过999,999,999）
     */
    @Test
    public  void method2() {
        //过这种方式获取的时间戳与北京时间相差8个时区，需要修正为北京时间
        Instant now = Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8));;
        System.out.println(now);
    }

    /**
     *
     * 常用的获取耗时
     * @throws InterruptedException
     */
    @Test
    public  void method3() throws InterruptedException {
        Instant start = Instant.now();

        TimeUnit.SECONDS.sleep(1);

        //耗时秒
        System.out.println(Instant.now().getEpochSecond()-start.getEpochSecond());
        //耗时毫秒
        System.out.println(Instant.now().toEpochMilli()-start.toEpochMilli());
    }

    /**
     *
     * DateTimeFormatter 代替 SimpleDateFormat
     * DateTimeFormatter 格式化
     *
     */
    @Test
    public  void method4()  {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //当天零点
        System.out.println( dateTimeFormatter.format(LocalDateTime.of(LocalDate.now(), LocalTime.MIN)));
        // 毫秒值  使用的时区都是东8区，也就是北京时间
        System.out.println( LocalDateTime.of(LocalDate.now(), LocalTime.MIN).toInstant(ZoneOffset.of("+8")).toEpochMilli());
        //当天 23:59:59
        System.out.println( dateTimeFormatter.format(LocalDateTime.of(LocalDate.now(), LocalTime.MAX)));
        // 毫秒值
        System.out.println( LocalDateTime.of(LocalDate.now(), LocalTime.MAX).toInstant(ZoneOffset.of("+8")).toEpochMilli());
    }

    /**
     *
     *  毫秒值转化为 特定格式
     */
    @Test
    public  void method5()  {
        Instant instant = Instant.ofEpochMilli(System.currentTimeMillis());
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.ofInstant(instant, ZoneId.systemDefault())));
    }
}