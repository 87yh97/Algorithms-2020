package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;

import java.util.BitSet;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     * <p>
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     * <p>
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     * <p>
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) {
        throw new NotImplementedError();
    }

    /**
     * Задача Иосифа Флафия.
     * Простая
     * <p>
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 5
     * <p>
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 х
     * <p>
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 х 3
     * 8   4
     * 7 6 Х
     * <p>
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     * <p>
     * 1 Х 3
     * х   4
     * 7 6 Х
     * <p>
     * 1 Х 3
     * Х   4
     * х 6 Х
     * <p>
     * х Х 3
     * Х   4
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   х
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   Х
     * Х х Х
     * <p>
     * Общий комментарий: решение из Википедии для этой задачи принимается,
     * но приветствуется попытка решить её самостоятельно.
     */
    static public int josephTask(int menNumber, int choiceInterval) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     * <p>
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     */
    //Ресурсоемкость: O(N^2)
    //Трудоемкость: O(N^2)
    static public String longestCommonSubstring(String firs, String second) {
        if (firs.equals("") || second.equals("")) return "";

        int firsLen = firs.length();
        int secondLen = second.length();
        int[][] chars = new int[firsLen][secondLen];
        int maxSubLen = 0;
        int maxSubEndInd = 0;

        for (int i = 0; i < firsLen; i++) {
            for (int j = 0; j < secondLen; j++) {

                if (firs.charAt(i) == second.charAt(j)) {

                    if (i > 0 && j > 0) {
                        chars[i][j] = chars[i - 1][j - 1] + 1;
                        if (chars[i][j] > maxSubLen) {
                            maxSubLen = chars[i][j];
                            maxSubEndInd = i;
                        }
                    } else chars[i][j] = 1;

                } else {
                    chars[i][j] = 0;
                }

            }
        }

        String subStr = firs.substring(maxSubEndInd - maxSubLen + 1, maxSubEndInd + 1);

        if (maxSubLen == 0) return "";
        else return subStr;
    }

    /**
     * Число простых чисел в интервале
     * Простая
     * <p>
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     * <p>
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     */
    //
    //Ресурсоемкость: O(N)
    //Трудоемкость: O(N*log(log(N)))
    static public int calcPrimesNumber(int limit) {

        if (limit <= 1) return 0;

        int primesNum = 0;
        BitSet primes = new BitSet(limit);

        for (int i = 1; i < limit / 2; i++) {
            if (!primes.get(i)) {
                for (int j = 2 * (i + 1) - 1; j < limit && j > 0; j += (i + 1)) {
                    primes.set(j, true);
                }
            }
        }
        for (int i = 1; i < limit; i++) {
            if (!primes.get(i)) primesNum++;
        }

        return primesNum;
    }
}
