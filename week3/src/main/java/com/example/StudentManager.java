package com.example;

import java.util.*;

public class StudentManager {
    private static class Student {
        public String name;
        public String major;
        public int id;
        public float averageGrade;

        public Student(String name, String major, int id, float averageGrade) {
            this.name = name;
            this.major = major;
            this.id = id;
            this.averageGrade = averageGrade;
        }
    }

    public static void main(String[] args) {
        Map<String, Student> students = new HashMap<>();
        students.put(
                "김은비",
                new Student("김은비", "모바일SW", 1, 3.19f)
        );

        students.put(
                "하여린",
                new Student("하여린", "웹공학", 5, 4.01f)
        );

        students.put(
                "김여린",
                new Student("김여린", "웹공학 2", 15, 3.01f)
        );

        students.put(
                "박여린",
                new Student("박여린", "웹공학 4", 25, 2.01f)
        );

        System.out.println("------------------------");
        var it = students.values().iterator();
        while (it.hasNext()) {
            Student student = it.next();
            System.out.printf("이름: %s\t전공:%s\n", student.name, student.major);
        }

//        for (Student student : students.values()) {
//            System.out.printf("이름: %s\t전공:%s\n", student.name, student.major);
//        }

        var keyword = "남윤혁";
        var student = students.get(keyword);
        if (student == null) {
            // 찾을 수 없습니다...
        } else {
            // 검색 성공!
        }
    }
}
