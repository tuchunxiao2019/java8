package com.atguigu.java8;

import org.junit.Test;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.*;

/**
 * 一、方法引用：若 Lambda 体中的功能，已经有方法提供了实现，可以使用方法引用
 * （可以将方法引用理解为 Lambda 表达式的另外一种表现形式）
 * <p>
 * 1. 对象的引用 :: 实例方法名
 * <p>
 * 2. 类名 :: 静态方法名
 * <p>
 * 3. 类名 :: 实例方法名
 * <p>
 * 注意：
 * ①方法引用所引用的方法的参数列表与返回值类型，需要与函数式接口中抽象方法的参数列表和返回值类型保持一致！
 * ②若Lambda 的参数列表的第一个参数，是实例方法的调用者，第二个参数(或无参)是实例方法的参数时，格式： ClassName::MethodName
 * <p>
 * 二、构造器引用 :构造器的参数列表，需要与函数式接口中参数列表保持一致！
 * <p>
 * 1. 类名 :: new
 * <p>
 * 三、数组引用
 * <p>
 * 类型[] :: new;
 */
public class TestMethodRef {
    //对象::实例方法名
    @Test
    public void test1() {
        PrintStream ps1 = System.out;
        Consumer<String> con = (x) -> ps1.println(x);

        PrintStream ps = System.out;
        Consumer<String> con1 = ps::println;

        Consumer<String> con2 = System.out::println;
        con2.accept("test");
    }

    @Test
    public void test2() {
        Employee emp = new Employee();
        Supplier<String> sup = () -> emp.getName();
        String str = sup.get();
        System.out.println(str);

        Supplier<Integer> sup2 = emp::getAge;
        Integer integer = sup2.get();
        System.out.println(integer);
    }

    //类::静态方法名
    @Test
    public void test3() {
        Comparator<Integer> com = (x, y) -> Integer.compare(x, y);

        Comparator<Integer> com1 = Integer::compare;
    }

    //类::实例方法名
    @Test
    public void test4() {
        BiPredicate<String, String> bp = (x, y) -> x.equals(y);

        BiPredicate<String, String> bp2 = String::equals;
    }

    //构造器引用
    @Test
    public void test5() {
        Supplier<Employee> sup = () -> new Employee();

        Supplier<Employee> sup2 = Employee::new;
        Employee employee = sup2.get();
        System.out.println(employee);
    }

    @Test
    public void test6() {
        Function<Integer, Employee> fun = (x) -> new Employee(x);
        Function<Integer, Employee> fun2 = Employee::new;
        Employee employee = fun2.apply(1);
        System.out.println(employee);

        BiFunction<Integer, Integer, Employee> bf = Employee::new;
        Employee employee1 = bf.apply(2, 18);
        System.out.println(employee1);
    }

    //数组引用
    @Test
    public void test7() {
        Function<Integer, String[]> fun = (x) -> new String[x];
        String[] strings = fun.apply(10);
        System.out.println(strings.length);

        Function<Integer, String[]> fun2 = String[]::new;
        String[] strings1 = fun2.apply(20);
        System.out.println(strings1.length);
    }
}
