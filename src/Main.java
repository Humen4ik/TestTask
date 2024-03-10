import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        List<Long> numbers = readFile("10m.txt");
        long start2 = System.currentTimeMillis();
        Math math = new Math();
        math.doMath(numbers);
        System.out.println(math);
        long end2 = System.currentTimeMillis();
        System.out.printf("Execution time: %dms%n", end2 - start2);

    }

    public static List<Long> readFile(String path) {
        List<Long> numbersList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null)
                numbersList.add(Long.valueOf(line));
            return numbersList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

