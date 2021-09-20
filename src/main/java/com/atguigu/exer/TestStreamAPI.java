package com.atguigu.exer;

import com.atguigu.java8.Employee2;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TestStreamAPI {
    /*
	  	1.	给定一个数字列表，如何返回一个由每个数的平方构成的列表呢？
		，给定【1，2，3，4，5】， 应该返回【1，4，9，16，25】。
	 */
    @Test
    public void test1() {
        Integer[] nums = new Integer[]{1, 2, 3, 4, 5};
        Arrays.stream(nums)
                .map((x) -> x * x)
                .forEach(System.out::println);
    }

    List<Employee2> employee2s = Arrays.asList(
            new Employee2("张三", 18, 9999.99, Employee2.Status.FREE),
            new Employee2("李四", 58, 5555.55, Employee2.Status.BUSY),
            new Employee2("王五", 26, 3333.33, Employee2.Status.VOCATION),
            new Employee2("赵六", 36, 6666.66, Employee2.Status.FREE),
            new Employee2("田七", 12, 8888.88, Employee2.Status.BUSY),
            new Employee2("田七", 12, 8888.88, Employee2.Status.BUSY)
    );

    /*
	 2.	怎样用 map 和 reduce 方法数一数流中有多少个Employee呢？
	 */
    @Test
    public void test2() {
        Optional<Integer> sum = employee2s.stream()
                .map((e) -> 1)
                .reduce(Integer::sum);
        System.out.println(sum.get());
    }
}
