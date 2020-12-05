package lesson1;

import kotlin.NotImplementedError;
import lesson1.Sorts;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.*;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     * <p>
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     * <p>
     * Пример:
     * <p>
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     * <p>
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     * <p>
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    // Ресурсоемкость: (X*N*2) - памяти требуется для отдельной сортировки только AM или PM значений(0<X<1),
    // тогда как для оставшейся части требуется ((1-X)*N*2). Алгоритм же хранит N изначальных значений в двух массивах am[] и pm[]
    // в одном экземпляре каждый. Также в алгоритме используются отдельные массивы для сохранения временных значений
    // в 12 am/pm часов, которые могут занимать от 0 до (N-1) значений.
    // Следовательно ресурсоемкость в лучшем случае равна O(X*N*2 + (1-X)*N*2 + N) = O(3N), а в худшем
    // O(X*N*2 + (1-X)*N*2 + N + 2*(N-1)) = O(3N + 2N - 2) = O(5N - 2).
    // Значит в общем случае ресурсоемкость равна O(N)
    // Трудоемкость: Сортировка подсчетом обладает трудоемкостью O(N + K), все остальные действия алгоритма
    // по преобразованию входных данных и их обработке также работают за O(N)
    // Значит трудоемкость алгоритма равна O(N + K) = O(N + 46800) = O(N), где К - количество различных возможных значений моментов времени,
    // передаваемых в качестве аргумента. В данной задаче К = 46800.
    // Итоговая трудоемкость: O(N)
    static public void sortTimes(String inputName, String outputName) throws IOException {

        int amLines = 0;
        int pmLines = 0;

        try (BufferedReader file =
                     Files.newBufferedReader(Paths.get(System.getProperty("user.dir"), inputName), StandardCharsets.UTF_8)) {

            String inp;

            boolean isEmpty = true;
            while ((inp = file.readLine()) != null) {
                isEmpty = false;
                if (!inp.matches("(((0[0-9]|1[0-2]):([0-5][0-9]):([0-5][0-9]))\\s(AM|PM))")) {
                    throw new IllegalArgumentException();
                }
                if (inp.matches(".*(AM)")) {
                    amLines++;
                } else {
                    pmLines++;
                }
            }

            if (isEmpty) throw new IllegalArgumentException();
        }

        int[] am = new int[amLines];
        int[] pm = new int[pmLines];

        int amInd = 0;
        int pmInd = 0;

        try (BufferedReader file =
                     Files.newBufferedReader(Paths.get(System.getProperty("user.dir"), inputName), StandardCharsets.UTF_8)) {

            String inp;

            while ((inp = file.readLine()) != null) {
                String[] inpSplit = inp.split(":|\\s");

                if (inp.matches(".*(AM)")) {
                    am[amInd] = (Integer.parseInt(inpSplit[0]) * 3600 +
                            Integer.parseInt(inpSplit[1]) * 60 +
                            Integer.parseInt(inpSplit[2]));
                    amInd++;
                } else {
                    pm[pmInd] = (Integer.parseInt(inpSplit[0]) * 3600 +
                            Integer.parseInt(inpSplit[1]) * 60 +
                            Integer.parseInt(inpSplit[2]));
                    pmInd++;
                }
            }

            am = Sorts.countingSort(am, 46800);
            pm = Sorts.countingSort(pm, 46800);

        }

        amInd = 0;
        pmInd = 0;

        for (int i = 0; i < am.length; i++) {
            if (am[i] > 43199) {
                amInd = i;
                break;
            }
        }
        for (int i = 0; i < pm.length; i++) {
            if (pm[i] > 43199) {
                pmInd = i;
                break;
            }
        }

        if (amInd > 0) {
            int[] amSwap = new int[am.length - amInd];

            System.arraycopy(am, amInd, amSwap, 0, am.length - amInd);

            System.arraycopy(am, 0, am, am.length - amInd, amInd);

            System.arraycopy(amSwap, 0, am, 0, am.length - amInd);
        }
        if (pmInd > 0) {
            int[] pmSwap = new int[pm.length - pmInd];

            System.arraycopy(pm, pmInd, pmSwap, 0, pm.length - pmInd);

            System.arraycopy(pm, 0, pm, pm.length - pmInd, pmInd);

            System.arraycopy(pmSwap, 0, pm, 0, pm.length - pmInd);
        }

        try (BufferedWriter out = Files.newBufferedWriter(Paths.get(System.getProperty("user.dir"), outputName), StandardCharsets.UTF_8)) {

            for (int value : am) {
                int num = value / 3600;
                writeZero(num, out);
                out.write(num + ":");

                num = (value / 60) % 60;
                writeZero(num, out);
                out.write(num + ":");

                num = value % 60;
                writeZero(num, out);
                out.write(num + " AM");

                out.newLine();
            }

            for (int value : pm) {
                int num = value / 3600;
                writeZero(num, out);
                out.write(num + ":");

                num = (value / 60) % 60;
                writeZero(num, out);
                out.write(num + ":");

                num = value % 60;
                writeZero(num, out);
                out.write(num + " PM");

                out.newLine();
            }
        }
    }

    private static void writeZero(int num, BufferedWriter out) throws IOException {
        if (num < 10) {
            out.write("0");
        }
    }

    /**
     * Сортировка адресов
     * <p>
     * Средняя
     * <p>
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     * <p>
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     * <p>
     * Людей в городе может быть до миллиона.
     * <p>
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     * <p>
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    //
    //Ресурсоемкость: Для хранения данных используется TreeMap с парами (Название улицы - TreeMap с парами (Номер дома - [Множество Имен]))
    //Так как каждое имя относится лишь к одному дому, который относится лишь к одной улице, то имена не повторяются, а значит
    //для их хранения используется N ячеек в сумме со всех множеств. В худшем случае, когда каждый человек проживает на уникальной
    //улице, для хранения его имени требуется также хранение адреса. Значит если средний вес комбинации (Улица + Дом + Имя) Х байт, то
    //ресурсоемкость составит O(N * X) = O(N), так как ресурсоемкость TreeMap и TreeSet составляет O(N). В лучших же случаях, когда несколько людей
    //будут жить в одном доме, для хранения каждого имени из множества "соседей" средний вес комбинации (Улица + Дом + Имя) будет
    // меньше максимально возможного Х, так как не нужно будет повторно хранить название улицы и номер дома. Значит в лучших случаях
    //ресурсоемкость будет равна O(N * K) = O(N), K < X.
    //Ресурсоемкость: O(N)
    //Трудоемкость: Пусть K - количество улиц, S - среднее количество домов на каждой улице, P - среднее количество жителей в каждом доме.
    //Тогда средняя трудоемкость заполнения структур данных будет равна N(log(K/2) + log(S/2) + log(P/2)).
    //Трудоемкость обработки и вывода: N(log(K) + log(S) + log(P)). В краевых случаях трудоемкость N * log(N).
    static public void sortAddresses(String inputName, String outputName) throws IOException {
        String inp;
        TreeMap<String, TreeMap<Integer, TreeSet<String>>> entries = new TreeMap<>();

        try (BufferedReader file = Files.newBufferedReader(Paths.get(System.getProperty("user.dir"), inputName), StandardCharsets.UTF_8)) {

            boolean isEmpty = true;
            while ((inp = file.readLine()) != null) {
                isEmpty = false;
                if (!inp.matches("(\\S*)\\s(\\S*)\\s-\\s(\\S*)\\s(\\d+)")) {
                    throw new IllegalArgumentException();
                }

                String[] inpSplit = inp.split("(\\s-\\s)|(\\s(?=\\d+(\\n|$)))"); // [Селезнев Лили][Прудковский][12]

                String name = inpSplit[0];
                String streetName = inpSplit[1];
                Integer streetNumber = Integer.valueOf(inpSplit[2]);

                if (entries.containsKey(streetName)) {
                    if (entries.get(streetName).containsKey(streetNumber))
                        entries.get(streetName).get(streetNumber).add(name);
                    else {
                        TreeSet<String> tempSet = new TreeSet<>();
                        tempSet.add(name);
                        entries.get(streetName).put(streetNumber, tempSet);
                    }
                } else {
                    TreeSet<String> tempSet = new TreeSet<>();
                    tempSet.add(name);
                    TreeMap<Integer, TreeSet<String>> tempMap = new TreeMap<>();
                    tempMap.put(streetNumber, tempSet);
                    entries.put(streetName, tempMap);
                }
            }
            if (isEmpty) throw new IllegalArgumentException();

        }

        try (BufferedWriter out = Files.newBufferedWriter(Paths.get(System.getProperty("user.dir"), outputName), StandardCharsets.UTF_8)) {

            for (Map.Entry<String, TreeMap<Integer, TreeSet<String>>> street : entries.entrySet()) {

                for (Map.Entry<Integer, TreeSet<String>> building : street.getValue().entrySet()) {

                    out.write(street.getKey() + " " + building.getKey() + " - ");
                    TreeSet<String> names = building.getValue();
                    out.write(names.first());
                    if (names.size() > 1) {
                        boolean toWriteCommas = false;
                        for (String name : names) {
                            if (toWriteCommas) out.write(", " + name);
                            else toWriteCommas = true;
                        }
                    }
                    out.newLine();
                }
            }
        }
    }

    /**
     * Сортировка температур
     * <p>
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     * <p>
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     * <p>
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     * <p>
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    //Трудоемкость: O(N + K) - Обработка данных - O(N), сортировка - O(N + K)
    //Ресурсоемкость: O(N)
    static public void sortTemperatures(String inputName, String outputName) throws IOException {
        ArrayList<Integer> entries = new ArrayList<>();

        try (BufferedReader file = Files.newBufferedReader(Paths.get(System.getProperty("user.dir"), inputName), StandardCharsets.UTF_8)) {
            String inp;
            while ((inp = file.readLine()) != null) {
                entries.add(((int) (Double.parseDouble(inp) * 10)) + 2730);
            }
        }

        int[] entriesArr = new int[entries.size()];
        for (int i = 0; i < entries.size(); i++) {
            entriesArr[i] = entries.get(i);
        }

        entriesArr = Sorts.countingSort(entriesArr, 7730);

        try (BufferedWriter out = Files.newBufferedWriter(Paths.get(System.getProperty("user.dir"), outputName), StandardCharsets.UTF_8)) {

            for (int value : entriesArr) {
                out.write(String.valueOf((value - 2730) / 10.0));
                out.newLine();
            }
        }
    }

    /**
     * Сортировка последовательности
     * <p>
     * Средняя
     * (Задача взята с сайта acmp.ru)
     * <p>
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     * <p>
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     * <p>
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     * <p>
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Соединить два отсортированных массива в один
     * <p>
     * Простая
     * <p>
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     * <p>
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     * <p>
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        throw new NotImplementedError();
    }
}
