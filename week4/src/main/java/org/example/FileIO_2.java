package org.example;

import java.io.*;
import java.util.List;

public class FileIO_2 {

    record Student(long id, String name, String major) implements Serializable {}

    private static final List<Student> students = List.of(
            new Student(111, "Alice", "Computer Science"),
            new Student(222, "Bob", "Mathematics"),
            new Student(333, "Charlie", "Physics")
    );

    private static final String PATH = System.getProperty("user.dir");
    private static final String FILE_NAME = "students.bin";

    private static ObjectOutputStream openOutputStream(String path) throws IOException {
        var fos = new FileOutputStream(path);
        return new ObjectOutputStream(fos);
    }

    public static void main(String[] args) {
       try (ObjectOutputStream oos = openOutputStream(PATH + File.separator + FILE_NAME)) {
           for (Student student : students) {
               oos.writeObject(student);
           }
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
       System.out.printf("---- Students saved to %s/%s ----%n", PATH, FILE_NAME);

       try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PATH + File.separator + FILE_NAME))) {
           while (true) {
               try {
                   Student student = (Student) ois.readObject();
                   System.out.println(student);
               } catch (EOFException e) {
                   break; // End of file reached
               }
           }
       } catch (IOException | ClassNotFoundException e) {
           throw new RuntimeException(e);
       }
    }
}