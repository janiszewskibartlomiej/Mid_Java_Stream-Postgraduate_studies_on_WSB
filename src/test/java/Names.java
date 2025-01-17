import org.junit.jupiter.api.Test;

import javax.lang.model.element.Name;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Names {

    List<NameInfo> names = Files.lines(Paths.get(getClass().getResource("imiona_nadane_w_polsce_w_latach_2000-2019.csv").toURI()))
            .skip(1)
            .map(line -> line.split(","))
            .map(fields -> new NameInfo(Integer.parseInt(fields[0]), fields[1], Integer.parseInt(fields[2]), fields[3]))
            .collect(Collectors.toList());

    public Names() throws IOException, URISyntaxException {
    }

    /**
     * Podaj ilość imion żeńskich nie kończących się na "a".
     */
    @Test
    void task1() {
        long count = names.stream()
                .filter(nameInfo -> nameInfo.getSex().equals("K"))
                .filter(nameInfo -> !nameInfo.getName().endsWith("A"))
                .map(nameInfo -> nameInfo.getName())
                .distinct()
                .count();


        // TODO Napisz implementację

        System.out.println("Ilość imion żeńskich nie kończących się na 'a':");
        System.out.println(count);

        assertEquals(572, count);
    }

    /**
     * Podaj sumę nadań imienia Jan.
     */
    @Test
    void task2() {
        int sum = names.stream()
                .filter(nameInfo -> nameInfo.getName().equals("JAN"))
                .mapToInt(names -> names.getCount())
                .sum();

        // TODO Napisz implementację

        System.out.println("Liczba nadanych imion 'Jan':");
        System.out.println(sum);

        assertEquals(103151, sum);
    }

    /**
     * Podaj najmniejszą, największą i średnią ilośc nadań imienia Pola na przestrzeni lat.
     */
    @Test
    void task3() {
        IntSummaryStatistics statistics = names.stream()
                .filter(nameInfo -> nameInfo.getName().equals("POLA"))
                .mapToInt(names -> names.getCount())
                .summaryStatistics();
        // TODO Napisz implementację

        int min = statistics.getMin();
        int max = statistics.getMax();
        double avg = statistics.getAverage();

        System.out.println("Nadane imiona 'POLA' wg lat:");
        System.out.println("najmniej: " + min);
        System.out.println("najwięcej: " + max);
        System.out.println("średnio: " + avg);

        assertEquals(97, min);
        assertEquals(3657, max);
        assertEquals(1465.85, avg);
    }

    /**
     * Podaj 5 najpopularniejszych imion żeńskich w 2019 roku jako tekst rozdzielony przecinkami
     * (na przykład: "ANIA, KASIA, BASIA").
     */
    @Test
    void task4() {
        String mostPopular2019 = names.stream()
                .filter(nameInfo -> nameInfo.getYear() == 2019)
                .filter(nameInfo -> nameInfo.getSex().equals("K"))
                .limit(5)
                .map(nameInfo -> nameInfo.getName())
                .collect(Collectors.joining(", "));

        System.out.println("Najpopularniejsze imiona żeńskie w 2019 roku:");
        System.out.println(mostPopular2019);

        assertEquals("ZUZANNA, JULIA, ZOFIA, MAJA, HANNA", mostPopular2019);
    }

    /**
     * Podaj, w kolejności od najdłuższego do najkrótszego, 4 najdłuższe imiona.
     */
    @Test
    void task5() {
        List<String> longestNames = names.stream()
                .map(nameInfo -> nameInfo.getName())
                .sorted((a,b) -> b.length() - a.length())
                .distinct()
                .limit(4)
                .collect(Collectors.toList());

        // TODO Napisz implementację

        System.out.println("Najdłuższe imiona:");
        System.out.println(longestNames);

        List<String> expected = Arrays.asList("MARIA-MAGDALENA", "HANNAH-SOPHIE", "MASSIMILIANO", "KONSTANTINOS");
        assertEquals(expected, longestNames);
    }

}
