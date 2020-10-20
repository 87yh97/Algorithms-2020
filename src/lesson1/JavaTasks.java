package lesson1;

import kotlin.NotImplementedError;
import lesson1.Sorts;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

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
    //Ресурсоемкость: (X*N*2 + K) - памяти требуется для отдельной сортировки только AM или PM значений(0<X<1),
    // тогда как для оставшейся части требуется ((1-X)*N*2 + K). Из-за формата входных данных
    // так как в памяти одновременно хранятся списки временных значений и их же массивы.
    //
    static public void sortTimes(String inputName, String outputName) throws IOException {

        BufferedReader file = Files.newBufferedReader(Paths.get(System.getProperty("user.dir"), inputName), StandardCharsets.UTF_8);

        /*ArrayList<Integer> amList = new ArrayList<>();
        ArrayList<Integer> pmList = new ArrayList<>();
        String inp;

        while ((inp = file.readLine()) != null) {                                   //O(n)
            if (!inp.matches("(\\d\\d:\\d\\d:\\d\\d)\\s(AM|PM)")) {
                throw new IllegalArgumentException();
            }
            String[] inpSplit = inp.split(":|\\s");
            //System.out.println(inp);
            if (inp.matches(".*(AM)")) {
                amList.add(Integer.parseInt(inpSplit[0]) * 3600 +
                        Integer.parseInt(inpSplit[1]) * 60 +
                        Integer.parseInt(inpSplit[2]));
            } else {
                pmList.add(Integer.parseInt(inpSplit[0]) * 3600 +
                        Integer.parseInt(inpSplit[1]) * 60 +
                        Integer.parseInt(inpSplit[2]));
            }
        }

        file.close();

        int[] am = Sorts.countingSort(amList.stream().mapToInt(Integer::intValue).toArray(), 46800);
        int[] pm = Sorts.countingSort(pmList.stream().mapToInt(Integer::intValue).toArray(), 46800);

        amList = null;
        pmList = null;

         */
        String inp;
        int amLines = 0;
        int pmLines = 0;
        while ((inp = file.readLine()) != null) {                                   //O(n)
            if (!inp.matches("(\\d\\d:\\d\\d:\\d\\d)\\s(AM|PM)")) {
                throw new IllegalArgumentException();
            }
            if (inp.matches(".*(AM)")) {
                amLines++;
            } else {
                pmLines++;
            }
        }
        file.close();

        file = Files.newBufferedReader(Paths.get(System.getProperty("user.dir"), inputName), StandardCharsets.UTF_8);

        int[] am = new int[amLines];
        int[] pm = new int[pmLines];

        int amInd = 0;
        int pmInd = 0;
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

        file.close();

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

//            for (int i = am.length - 1; i >= am.length - amInd; i--) {
//                am[i] = am[i - (am.length - amInd)];
//            }
            System.arraycopy(am, 0, am, am.length - amInd, amInd);

            System.arraycopy(amSwap, 0, am, 0, am.length - amInd);
        }
        if (pmInd > 0) {
            int[] pmSwap = new int[pm.length - pmInd];

            System.arraycopy(pm, pmInd, pmSwap, 0, pm.length - pmInd);

//            for (int i = pm.length - 1; i >= pm.length - pmInd; i--) {
//                pm[i] = pm[i - (pm.length - pmInd)];
//            }
            System.arraycopy(pm, 0, pm, pm.length - pmInd, pmInd);

            System.arraycopy(pmSwap, 0, pm, 0, pm.length - pmInd);
        }

        BufferedWriter out = Files.newBufferedWriter(Paths.get(System.getProperty("user.dir"), outputName), StandardCharsets.UTF_8);

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

        out.close();
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
    static public void sortAddresses(String inputName, String outputName) {
        throw new NotImplementedError();
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
    static public void sortTemperatures(String inputName, String outputName) {
        throw new NotImplementedError();
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
