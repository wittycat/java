package util.function;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by chenxun.
 * Date: 2018/12/2 下午11:02
 * Description:
 *  JDK8函数式接口Function、Consumer、Predicate、Supplier
 *  https://blog.csdn.net/z834410038/article/details/77370785
 *  https://blog.csdn.net/u014470581/article/details/54944384
 */
public interface TFunction<T> {

    /**
     * 有返回结果
     * @param function
     * @return
     */
    T apply(Function function);

    /**
     * 无返回结果
     * @param consumer
     */
    void accept(Consumer consumer);

    /**
     * 无入参，直接获取
     * @param supplier
     * @return
     */
    T get(Supplier supplier);

    /**
     * 断言
     * @param predicate
     * @return
     */
    boolean test(Predicate predicate);
}
