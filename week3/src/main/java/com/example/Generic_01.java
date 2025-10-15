package com.example;

public class Generic_01 {
    public static void main(String[] args) {
        var pair1 = new Pair<>("콜라", "사이다");
        Pair<String, Integer> pair2 = new Pair<>("남윤혁", 2291012);

        // --------------------
        Box<Integer> numberBox = new MyBox<>(10);
        System.out.printf("numberBox의 value: %d\n", numberBox.getValue());

        Box<Box<Integer>> boxOfNumberBox = new MyBox<>(numberBox);
        System.out.printf("boxOfNumberBox value: %s\n", boxOfNumberBox.getValue());

        // --------------------
        var bookshelf = new BookShelf();
        bookshelf.addBook(new Novel());
        bookshelf.addBook(new Magazine());
//        bookshelf.addBook(new Pizza()); // X
    }
}

class Pair<T, U> {
    private final T first;
    private final U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }
}

// --------------------
interface Box<T> {
    T getValue();
}

class MyBox<T> implements  Box<T> {
    private final T value;

    public MyBox(T value) {
        this.value = value;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "MyBox<%s>".formatted(value.getClass().getSimpleName());
    }
}

// --------------------
interface Book {}
class Novel implements Book {}
class Magazine implements Book {}
class Pizza {}

class BookShelf {
    public <T extends Book> void addBook(T bookLike) {

    }
}