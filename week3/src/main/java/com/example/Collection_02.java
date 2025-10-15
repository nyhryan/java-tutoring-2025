package com.example;

import java.util.*;
import java.util.stream.Collectors;

public class Collection_02 {
    public static void main(String[] args) {
        Map<String, String> students = new HashMap<>();
        students.put("홍길동", "컴퓨터공학부");
        students.put("고길동", "사회학부");
        students.put("김길동", "물리학부");

        Set<String> studentNames = students.keySet();
        System.out.print("학생 이름들: ");
        studentNames.forEach(name -> System.out.print(name + ", "));
        System.out.println();

        var prettyList = students.entrySet().stream()
                .map(entry -> {
                    var name = entry.getKey();
                    var dept =  entry.getValue();
                    return "** %s -- %s **".formatted(name, dept);
                })
                .collect(Collectors.joining("\n"));

        System.out.println("   이름   -- 학부");
        System.out.println(prettyList);
    }
}
