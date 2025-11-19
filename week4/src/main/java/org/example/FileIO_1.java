package org.example;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FileIO_1 {

    private static BufferedReader openResource(String resourceName, Charset encoding) {
        InputStream is = FileIO_1.class.getResourceAsStream(resourceName);
        if (is == null) {
            throw new RuntimeException("Resource %s not found".formatted(resourceName));
        }

        Reader isr = new InputStreamReader(is, encoding);
        return new BufferedReader(isr);
    }

    private static BufferedReader openResource(String resourceName) {
        return openResource(resourceName, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        try (BufferedReader reader = openResource("/utf8.txt")) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("----<EOF>----");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader reader = openResource("/EUC-KR.txt", Charset.forName("EUC-KR"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("----<EOF>----");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
