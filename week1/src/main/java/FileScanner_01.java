import java.io.InputStream;
import java.util.Scanner;

public class FileScanner_01 {
    public static void main(String... args) {
        InputStream is = FileScanner_01.class.getResourceAsStream("/input.txt");
        if (is == null) {
            throw new RuntimeException("input.txt not found");
        }

        Scanner sc = new Scanner(is);
        while (sc.hasNext()) {
            System.out.printf("READ: %s\n", sc.next());
        }
        sc.close();

        System.out.println("--------");

        is = FileScanner_01.class.getResourceAsStream("/input.txt");
        if (is == null) {
            throw new RuntimeException("input.txt not found");
        }

        // try-resource
        try (Scanner sc2 = new Scanner(is)) {
            while (sc2.hasNextLine()) {
                System.out.printf("READ: %s\n", sc2.nextLine());
            }
        } // <-- sc.close() called
    }
}
