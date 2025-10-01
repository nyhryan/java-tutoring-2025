package org.example;

public class Inheritance01 {

    static void print(Book book) {
        System.out.println(book);
    }

    public static void main(String[] args) {
        var textBook = new TextBook("001", "명품 Java", "황기태", "IT개발");
        print(textBook);
    }
}

class Book {
    private final String isbn;
    private final String title;
    private final String author;

    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    @Override
    public String toString() {
        return "[%s] %s by %s".formatted(isbn, title, author);
    }
}

class TextBook extends Book {
    private final String category;

    public TextBook(String isbn, String title, String author, String category) {
        super(isbn, title, author);
        this.category = category;
    }

    @Override
    public String toString() {
        String basicInfoString = super.toString();
        return basicInfoString + " (Category: %s)".formatted(category);
    }
}