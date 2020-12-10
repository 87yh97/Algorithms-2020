package lesson5;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class OpenAddressingSet<T> extends AbstractSet<T> {

    private final int bits;

    private final int capacity;

    private final Object[] storage;

    private int size = 0;

    private int startingIndex(Object element) {
        return element.hashCode() & (0x7FFFFFFF >> (31 - bits));
    }

    public OpenAddressingSet(int bits) {
        if (bits < 2 || bits > 31) {
            throw new IllegalArgumentException();
        }
        this.bits = bits;
        capacity = 1 << bits;
        storage = new Object[capacity];
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Проверка, входит ли данный элемент в таблицу
     */
    @Override
    public boolean contains(Object o) {
        int index = startingIndex(o);
        Object current = storage[index];
        while (current != null) {
            if (current.equals(o)) {
                return true;
            }
            index = (index + 1) % capacity;
            current = storage[index];
        }
        return false;
    }

    /**
     * Добавление элемента в таблицу.
     *
     * Не делает ничего и возвращает false, если такой же элемент уже есть в таблице.
     * В противном случае вставляет элемент в таблицу и возвращает true.
     *
     * Бросает исключение (IllegalStateException) в случае переполнения таблицы.
     * Обычно Set не предполагает ограничения на размер и подобных контрактов,
     * но в данном случае это было введено для упрощения кода.
     */
    @Override
    public boolean add(T t) {
        int startingIndex = startingIndex(t);
        int index = startingIndex;
        Object current = storage[index];
        while (current != null) {
            if (current.equals(t)) {
                return false;
            }
            index = (index + 1) % capacity;
            if (index == startingIndex) {
                throw new IllegalStateException("Table is full");
            }
            current = storage[index];
        }
        storage[index] = t;
        size++;
        return true;
    }

    /**
     * Удаление элемента из таблицы
     *
     * Если элемент есть в таблица, функция удаляет его из дерева и возвращает true.
     * В ином случае функция оставляет множество нетронутым и возвращает false.
     * Высота дерева не должна увеличиться в результате удаления.
     *
     * Спецификация: {@link Set#remove(Object)} (Ctrl+Click по remove)
     *
     * Средняя
     */
    @Override
    public boolean remove(Object o) {
        return super.remove(o);
    }

    /**
     * Создание итератора для обхода таблицы
     *
     * Не забываем, что итератор должен поддерживать функции next(), hasNext(),
     * и опционально функцию remove()
     *
     * Спецификация: {@link Iterator} (Ctrl+Click по Iterator)
     *
     * Средняя (сложная, если поддержан и remove тоже)
     * @return
     */
    @NotNull
    @Override
    public Iterator<T> iterator() {
       return new OpenAddressingSetIterator();
    }

    private class OpenAddressingSetIterator implements Iterator<T> {

        int index = 0;
        int lastReturnedElementIndex = 0;
        T lastReturnedElement = null;
        boolean wasRemoved = true;

        @Override
        public boolean hasNext() {
            while (index < capacity) {
                if (storage[index] == null) index++;
                else return true;
            }
            return false;
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
//                if (storage[index] != null) return (T) storage[index];
//                else {
//                    while (hasNext())
//                }
            lastReturnedElementIndex = index;
            lastReturnedElement = (T) storage[lastReturnedElementIndex];
            index++;
            wasRemoved = false;
            return lastReturnedElement; //?????????????
        }

        @Override
        public void remove() {
            if (wasRemoved) throw new IllegalStateException();
            //storage[lastReturnedElementIndex] = null;
//            for (int i = lastReturnedElementIndex; i < (size - 1); i++) {
//                 storage[i] = storage[i + 1];
//            }

//            int index = lastReturnedElementIndex;
//            int elStartingIndex = startingIndex(lastReturnedElement);
//            while (index < (capacity - 1) + elStartingIndex
//                    && (index + 1) % capacity != elStartingIndex
//                    && storage[(index + 1) % capacity] != null
//                    && startingIndex(storage[(index + 1) % capacity]) >= elStartingIndex) {
//                storage[index % capacity] = storage[(index + 1) % capacity];
//                index++;
//            }
            int index = lastReturnedElementIndex + 1;
            int lastReplaced = lastReturnedElementIndex;
            int elStartingIndex = startingIndex(lastReturnedElement);
            while (index < (capacity - 1) + elStartingIndex
                    //&& (index + 1) % capacity != elStartingIndex
                    && storage[index % capacity] != null) {
                if (startingIndex(storage[(index + 1) % capacity]) != elStartingIndex) {

                }
                storage[index % capacity] = storage[(index + 1) % capacity];
                index++;
            }
            storage[index % capacity] = null;
            //storage[size - 1] = null;
            this.index--;
            wasRemoved = true;
            size--;
        }

    }
}
