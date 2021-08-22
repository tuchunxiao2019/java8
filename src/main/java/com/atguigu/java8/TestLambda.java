package com.atguigu.java8;

import org.junit.Test;

import java.util.*;

public class TestLambda {

    /**
     * 原来的匿名内部类
     */
    @Test
    public void test1() {
        Comparator<Integer> com = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };

        TreeSet<Integer> ts = new TreeSet<>(com);
    }

    /**
     * Lambda表达式
     */
    @Test
    public void test2() {
        Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
        TreeSet<Integer> ts = new TreeSet<>(com);
    }

    List<Employee> employees = Arrays.asList(
            new Employee("张三", 18, 9999.99),
            new Employee("李四", 38, 5555.55),
            new Employee("王五", 50, 6666.66),
            new Employee("赵六", 16, 3333.33),
            new Employee("田七", 8, 7777.77)
    );

    //需求：获取当前公司中员工年龄大于35的员工信息
    public List<Employee> filterEmployees(List<Employee> list) {
        List<Employee> emps = new ArrayList<>();
        for (Employee emp :
                list) {
            if (emp.getAge() >= 35) {
                emps.add(emp);
            }
        }
        return emps;
    }

    @Test
    public void test3() {
        List<Employee> list = filterEmployees(this.employees);
        for (Employee e :
                list) {
            System.out.println(e);
        }
    }

    //需求：获取当前公司中员工工资大于5000的员工信息
    public List<Employee> filterEmployees2(List<Employee> list) {
        List<Employee> emps = new ArrayList<>();
        for (Employee emp :
                list) {
            if (emp.getSalary() >= 5000) {
                emps.add(emp);
            }
        }
        return emps;
    }

    public List<Employee> filterEmployee(List<Employee> list, MyPredicate<Employee> mp) {
        List<Employee> emps = new ArrayList<>();
        for (Employee e :
                list) {
            if (mp.test(e)) {
                emps.add(e);
            }
        }
        return emps;
    }

    //优化方式一：策略设计模式
    @Test
    public void test4() {
        List<Employee> employees = filterEmployee(this.employees, new FilterEmployeeByAge());
        for (Employee employee : employees) {
            System.out.println(employee);
        }

        System.out.println("----------------------------");
        List<Employee> employees1 = filterEmployee(this.employees, new FilterEmployeeBySalary());
        for (Employee employee : employees1) {
            System.out.println(employee);
        }
    }

    //优化方式二：匿名内部类
    @Test
    public void test5() {
        List<Employee> list = filterEmployee(this.employees, new MyPredicate<Employee>() {
            @Override
            public boolean test(Employee employee) {
                return employee.getSalary() <= 5000;
            }
        });

        for (Employee employee : list) {
            System.out.println(employee);
        }
    }

    //优化方式三：Lambda表达式
    @Test
    public void test6() {
        List<Employee> list = filterEmployee(employees, e -> e.getSalary() <= 5000);
        list.forEach(System.out::println);
    }

    //优化方式四：Stream API
    @Test
    public void test7() {
        employees.stream()
                .filter(e -> e.getSalary() >= 5000)
                .limit(2)
                .forEach(System.out::println);

        System.out.println("------------------------");
        employees.stream()
                .map(Employee::getName)
                .forEach(System.out::println);
    }
}
