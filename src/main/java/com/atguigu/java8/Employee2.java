package com.atguigu.java8;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee2 {
    private String name;
    private int age;
    private double salary;
    private Status status;

    public enum Status {
        FREE,
        BUSY,
        VOCATION;
    }
}
