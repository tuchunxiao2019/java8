package com.atguigu.java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Java8 内置的四大核心函数式接口
 * <p>
 * Consumer<T> : 消费型接口
 * void accept(T t);
 * <p>
 * Supplier<T> : 供给型接口
 * T get();
 * <p>
 * Function<T, R> : 函数型接口
 * R apply(T t);
 * <p>
 * Predicate<T> : 断言型接口
 * boolean test(T t);
 */
public class TestLambda3 {
    public void happy(double money, Consumer<Double> con) {
        con.accept(money);
    }

    //Consumer<T> 消费型接口 :
    @Test
    public void test1() {
        happy(10000, (m) -> System.out.println("test" + m + "\t"));
    }

    //需求：产生指定个数的整数，并放入集合中
    public List<Integer> getNumList(int num, Supplier<Integer> sup) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Integer n = sup.get();
            list.add(n);
        }
        return list;
    }

    //Supplier<T> 供给型接口 :
    @Test
    public void test2() {
        List<Integer> numList = getNumList(10, () -> (int) (Math.random() * 100));
        for (Integer n :
                numList) {
            System.out.println(n);
        }
    }

    //需求：用于处理字符串
    public String strHandler(String str, Function<String, String> fun) {
        return fun.apply(str);
    }

    //Function<T, R> 函数型接口：
    @Test
    public void test3() {
        String trimStr = strHandler("\t\t\t test", (str) -> str.trim());
        System.out.println(trimStr);

        String subStr = strHandler("adsdasdasd", (str) -> str.substring(2, 4));
        System.out.println(subStr);
    }

    //需求：将满足条件的字符串，放入集合中
    public List<String> filterStr(List<String> list, Predicate<String> pre) {
        List<String> strList = new ArrayList<>();

        for (String str :
                list) {
            if (pre.test(str)) {
                strList.add(str);
            }
        }
        return strList;
    }

    //Predicate<T> 断言型接口：
    @Test
    public void test4() {
        List<String> list = Arrays.asList("Hello", "World", "Lambda", "ok");
        List<String> stringList = filterStr(list, (str) -> str.length() > 3);

        for (String str :
                stringList) {
            System.out.println(str);
        }
    }
}
