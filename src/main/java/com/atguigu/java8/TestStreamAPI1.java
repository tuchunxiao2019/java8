package com.atguigu.java8;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 一、Stream API 的操作步骤：
 * <p>
 * 1. 创建 Stream
 * <p>
 * 2. 中间操作
 * <p>
 * 3. 终止操作(终端操作)
 */
public class TestStreamAPI1 {
    //创建 Stream
    @Test
    public void test1() {
        //1. 可以通过Collection系列集合提供的stream() 或 parallelStream()
        List<String> list = new ArrayList<>();
        Stream<String> stream1 = list.stream();

        //2. 通过Arrays中的静态方法stream()获取数组流
        Employee[] emps = new Employee[10];
        Stream<Employee> stream2 = Arrays.stream(emps);

        //3. 通过Stream类中的静态方法of()
        Stream<String> stream3 = Stream.of("aa", "bb", "cc");

        //4. 创建无限流
        //迭代
        Stream<Integer> stream4 = Stream.iterate(0, (x) -> x + 2);
        stream4.limit(10).forEach(System.out::println);

        //生成
        Stream.generate(() -> Math.random())
                .limit(5)
                .forEach(System.out::println);
    }

    List<Employee> emps = Arrays.asList(
            new Employee(102, "李四", 59, 6666.66),
            new Employee(101, "张三", 18, 9999.99),
            new Employee(103, "王五", 28, 3333.33),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(105, "田七", 38, 5555.55)
    );

    //中间操作
    /*
	  筛选与切片
		filter——接收 Lambda ， 从流中排除某些元素。
		limit——截断流，使其元素不超过给定数量。
		skip(n) —— 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补
		distinct——筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素
	 */
    //内部迭代：迭代操作由Stream API 完成的
    @Test
    public void test2() {
        //中间操作：不会执行任何操作
        Stream<Employee> stream = emps.stream().filter((e) -> {
            System.out.println("Stream API的中间操作");
            return e.getAge() > 35;
        });

        //终止操作：一次性执行全部内容，即"惰性求值"
        stream.forEach(System.out::println);
    }

    //外部迭代：
    @Test
    public void test3() {
        Iterator<Employee> iterator = emps.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    //截断流
    @Test
    public void test4() {
        emps.stream()
                .filter((e) -> {
                    System.out.println("短路！"); // && ||
                    return e.getSalary() > 5000;
                })
                .limit(2)
                .forEach(System.out::println);
    }

    //跳过元素
    @Test
    public void test5() {
        emps.stream()
                .filter((e) -> e.getSalary() > 5000)
                .skip(2)
                .forEach(System.out::println);
    }

    //筛选
    @Test
    public void test6() {
        emps.stream()
                .filter((e) -> e.getSalary() > 5000)
                .skip(2)
                .distinct()
                .forEach(System.out::println);
    }

    /*
        映射
        map--接收Lambda，将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上，
            并将其映射成一个新的元素。
        flatMap--接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
     */
    @Test
    public void test7() {
        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");
        list.stream()
                .map((str) -> str.toUpperCase())
                .forEach(System.out::println);

        System.out.println("-----------------------------");

        emps.stream()
                .map((e) -> e.getName())
                .forEach(System.out::println);

        System.out.println("----------------------------");

        emps.stream()
                .map(Employee::getName)
                .forEach(System.out::println);

        System.out.println("-----------------------------");

        Stream<Stream<Character>> stream = list.stream()
                .map(TestStreamAPI1::filterCharacter);// {{a,a,a},{b,b,b}}
        stream.forEach((sm) -> {
            sm.forEach(System.out::println);
        });

        System.out.println("------------------------------");

        Stream<Character> stream1 = list.stream()
                .flatMap(TestStreamAPI1::filterCharacter);//{a,a,a,b,b,b}
        stream1.forEach(System.out::println);
    }

    public static Stream<Character> filterCharacter(String str) {
        List<Character> list = new ArrayList<>();

        for (Character ch :
                str.toCharArray()) {
            list.add(ch);
        }
        return list.stream();
    }

    /*
        排序
        sorted--自然排序(Comparable)
        sorted(Comparator com)--定制排序(Comparator)
     */
    @Test
    public void test8() {
        List<String> list = Arrays.asList("ccc", "bbb", "aaa", "ddd", "eee");
        list.stream()
                .sorted()
                .forEach(System.out::println);

        System.out.println("----------------------------");

        emps.stream()
                .sorted((e1, e2) -> {
                    if (e1.getAge() == e2.getAge()) {
                        return e1.getName().compareTo(e2.getName());
                    } else {
                        return Integer.compare(e1.getAge(), e2.getAge());
                    }
                }).forEach(System.out::println);
    }

    List<Employee2> employee2s = Arrays.asList(
            new Employee2("张三", 18, 9999.99, Employee2.Status.FREE),
            new Employee2("李四", 58, 5555.55, Employee2.Status.BUSY),
            new Employee2("王五", 26, 3333.33, Employee2.Status.VOCATION),
            new Employee2("赵六", 36, 6666.66, Employee2.Status.FREE),
            new Employee2("田七", 12, 8888.88, Employee2.Status.BUSY),
            new Employee2("田七", 12, 8888.88, Employee2.Status.BUSY)
    );

    //终止操作
    /*
        查找与匹配
		allMatch——检查是否匹配所有元素
		anyMatch——检查是否至少匹配一个元素
		noneMatch——检查是否没有匹配所有元素
		findFirst——返回第一个元素
		findAny——返回当前流中的任意元素
		count——返回流中元素的总个数
		max——返回流中最大值
		min——返回流中最小值
	 */
    @Test
    public void test9() {
        boolean b1 = employee2s.stream()
                .allMatch((e) -> e.getStatus().equals(Employee2.Status.BUSY));
        System.out.println(b1);

        System.out.println("----------------------------");

        boolean b2 = employee2s.stream()
                .anyMatch((e) -> e.getStatus().equals(Employee2.Status.BUSY));
        System.out.println(b2);

        System.out.println("----------------------------");

        boolean b3 = employee2s.stream()
                .noneMatch((e) -> e.getStatus().equals(Employee2.Status.BUSY));
        System.out.println(b3);

        System.out.println("----------------------------");

        Optional<Employee2> first = employee2s.stream()
                .sorted((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()))
                .findFirst();
        System.out.println(first.get());

        System.out.println("----------------------------");

        Optional<Employee2> any = employee2s.parallelStream() //并行流
                .filter((e) -> e.getStatus().equals(Employee2.Status.FREE))
                .findAny();
        System.out.println(any.get());

        System.out.println("----------------------------");

        long count = employee2s.stream()
                .count();
        System.out.println(count);

        System.out.println("----------------------------");

        Optional<Employee2> max = employee2s.stream()
                .max((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));
        System.out.println(max.get());

        System.out.println("----------------------------");

        Optional<Double> min = employee2s.stream()
                .map(Employee2::getSalary)
                .min(Double::compare);
        System.out.println(min.get());
    }

    /*
		归约
		reduce(T identity, BinaryOperator) / reduce(BinaryOperator) ——可以将流中元素反复结合起来，得到一个值。
	 */
    @Test
    public void test10() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Integer reduce = list.stream()
                .reduce(0, (x, y) -> x + y);
        System.out.println(reduce);

        System.out.println("----------------------------");

        Optional<Double> reduce1 = employee2s.stream()
                .map(Employee2::getSalary)
                .reduce(Double::sum);
        System.out.println(reduce1.get());
    }

    /*
        收集
        collect——将流转换为其他形式。接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法
     */
    @Test
    public void test11() {
        employee2s.stream()
                .map(Employee2::getName)
                .collect(Collectors.toList())
                .forEach(System.out::println);

        System.out.println("----------------------------");

        employee2s.stream()
                .map(Employee2::getName)
                .collect(Collectors.toSet())
                .forEach(System.out::println);

        System.out.println("----------------------------");

        employee2s.stream()
                .map(Employee2::getName)
                .collect(Collectors.toCollection(HashSet::new))
                .forEach(System.out::println);
    }

    @Test
    public void test12() {
        //总数
        Long count = employee2s.stream()
                .collect(Collectors.counting());
        System.out.println(count);

        System.out.println("----------------------------");

        //平均值
        Double avg = employee2s.stream()
                .collect(Collectors.averagingDouble(Employee2::getSalary));
        System.out.println(avg);

        System.out.println("----------------------------");

        //总和
        Double sum = employee2s.stream()
                .collect(Collectors.summingDouble(Employee2::getSalary));
        System.out.println(sum);

        System.out.println("----------------------------");

        //最大值
        Optional<Employee2> max = employee2s.stream()
                .collect(Collectors.maxBy((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())));
        System.out.println(max.get());

        System.out.println("----------------------------");

        //最小值
        Optional<Double> min = employee2s.stream()
                .map(Employee2::getSalary)
                .collect(Collectors.minBy(Double::compare));
        System.out.println(min.get());
    }

    //分组
    @Test
    public void test13() {
        Map<Employee2.Status, List<Employee2>> collect = employee2s.stream()
                .collect(Collectors.groupingBy(Employee2::getStatus));
        System.out.println(collect);
    }

    //多级分组
    @Test
    public void test14() {
        Map<Employee2.Status, Map<String, List<Employee2>>> collect = employee2s.stream()
                .collect(Collectors.groupingBy(Employee2::getStatus, Collectors.groupingBy((e) -> {
                    if (e.getAge() <= 35) {
                        return "青年";
                    } else if (e.getAge() <= 50) {
                        return "中年";
                    } else {
                        return "老年";
                    }
                })));
        System.out.println(collect);
    }

    //分区
    @Test
    public void test15() {
        Map<Boolean, List<Employee2>> collect = employee2s.stream()
                .collect(Collectors.partitioningBy((e) -> e.getSalary() > 8000));
        System.out.println(collect);
    }

    @Test
    public void test16() {
        DoubleSummaryStatistics collect = employee2s.stream()
                .collect(Collectors.summarizingDouble(Employee2::getSalary));
        System.out.println(collect.getAverage());
        System.out.println(collect.getSum());
        System.out.println(collect.getMax());
    }

    @Test
    public void test17() {
        String str = employee2s.stream()
                .map(Employee2::getName)
                .collect(Collectors.joining(",", "===", "==="));
        System.out.println(str);
    }
}
