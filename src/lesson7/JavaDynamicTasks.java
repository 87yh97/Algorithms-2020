package lesson7;

import kotlin.NotImplementedError;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     * <p>
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
    //Трудоемкость: O(m*n) где m, n - длины строк
    //Ресурсоемкость: O(m*n)
    public static String longestCommonSubSequence(String first, String second) {
        if (first.equals("") || second.equals("")) return "";

        int[][] commonChars = new int[first.length() + 1][second.length() + 1];

        //for (int i = 0; i < first.length(); i++ )

        for (int i = 1; i <= first.length(); i++) {
            for (int j = 1; j <= second.length(); j++) {
                if (first.charAt(i - 1) == second.charAt(j - 1)) {
                    commonChars[i][j] = commonChars[i - 1][j - 1] + 1;
                } else {
                    commonChars[i][j] = Math.max(commonChars[i - 1][j], commonChars[i][j - 1]);
                }
            }
        }


        int i = first.length();
        int j = second.length();
        StringBuilder commonSequence = new StringBuilder();
        while (j > 0 && i > 0) {
            if (first.charAt(i - 1) == second.charAt(j - 1)) {
                commonSequence.append(first.charAt(i - 1));
                i--;
                j--;
            } else {
                if (commonChars[i - 1][j] >= commonChars[i][j - 1]) i--;
                else j--;
            }
        }
//        for (int k = 1; k <= first.length(); k++) {
//            for (int m = 1; m <= second.length(); m++) {
//                System.out.print(commonChars[k][m] + ", ");
//            }
//            System.out.println();
//        }
        /*commonSequence = */
        commonSequence.reverse();
        System.out.println("CommonSequence " + commonSequence.toString());
        return commonSequence.toString();
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     * <p>
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */
    //Трудоемкость: O(N*log(N))
    //Ресурсоемкость: O(N)

    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        for (int i = 50; i >= 0; i--) {
            System.out.print(i + ", ");
        }
        int size = list.size();

        ArrayList<Integer> reversed = new ArrayList<>();

        for (int i = size - 1; i >= 0; i--) {
            reversed.add(list.get(i));
        }
        int[] end = new int[size + 1];
        int[] prevSeqEnd = new int[size];

        int seqSize = 0;


        for (int i = 0; i < size; i++) {
            int newNum = reversed.get(i);
            int upperBorder = seqSize + 1;
            int lowerBorder = 1;

            while (lowerBorder < upperBorder) {
                int middle = (lowerBorder + upperBorder) / 2;
                if (reversed.get(end[middle]) > newNum) lowerBorder = middle + 1;
                else upperBorder = middle;
            }

            if (lowerBorder > seqSize) {
                seqSize = lowerBorder;
            }
            if (i > 0) prevSeqEnd[i] = end[lowerBorder - 1];
            end[lowerBorder] = reversed.indexOf(newNum);

        }
        ArrayList<Integer> sequence = new ArrayList<>();
        int k = end[seqSize];
        for (int i = seqSize - 1; i >= 0; i--) {
            sequence.add(0, reversed.get(k));
            k = prevSeqEnd[k];
        }
        ArrayList<Integer> reversedSequence = new ArrayList<>();
        for (int i = seqSize - 1; i >= 0; i--) {
            reversedSequence.add(sequence.get(i));
        }
        return reversedSequence;
    }


    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     * <p>
     * В файле с именем inputName задано прямоугольное поле:
     * <p>
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     * <p>
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     * <p>
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    public static int shortestPathOnField(String inputName) {
        throw new NotImplementedError();
    }

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
