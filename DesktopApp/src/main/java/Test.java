import java.io.*;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        InputStream stream = Test.class.getResourceAsStream("/log4j2.xml");
        Scanner scanner = new Scanner(stream);
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine()) {
            sb.append(scanner.nextLine());
            sb.append("\n");
        }
        System.out.println(sb);
    }
}
