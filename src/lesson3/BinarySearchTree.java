package lesson3;

import java.util.*;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// attention: Comparable is supported but Comparator is not
public class BinarySearchTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        final T value;
        Node<T> left = null;
        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        } else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        } else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    /**
     * Добавление элемента в дерево
     * <p>
     * Если элемента нет в множестве, функция добавляет его в дерево и возвращает true.
     * В ином случае функция оставляет множество нетронутым и возвращает false.
     * <p>
     * Спецификация: {@link Set#add(Object)} (Ctrl+Click по add)
     * <p>
     * Пример
     */
    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        } else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        } else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    /**
     * Удаление элемента из дерева
     * <p>
     * Если элемент есть в множестве, функция удаляет его из дерева и возвращает true.
     * В ином случае функция оставляет множество нетронутым и возвращает false.
     * Высота дерева не должна увеличиться в результате удаления.
     * <p>
     * Спецификация: {@link Set#remove(Object)} (Ctrl+Click по remove)
     * <p>
     * Средняя
     */
    //Ресурсоемкость: O(1)
    //Трудоемкость: log(N) - найти родителя + log(N) - найти следующий по значению после удаляемого, значит
    //O(2log(N)) = O(log(N))
    //До того как уже доделал задание не знал, что можно изменять класс Node<T> и использовать
    //другие готовые структуры данных как стеки и списки, иначе бы возможно сделал задание эффективнее
    @Override
    public boolean remove(Object o) {
        if (!contains(o)) return false;

        @SuppressWarnings("unchecked")
        T t = (T) o;

        Node<T> node = find(t);
        Node<T> parent = root;
        if (parent != node) {
            while (parent.left != node && parent.right != node) { //log(N)
                if (parent.value.compareTo(t) > 0) parent = parent.left;
                else if (parent.value.compareTo(t) < 0) parent = parent.right;
            }
        }

        assert node != null;
        if (node.right != null) {

            Node<T> current = node.right;
            if (current.left != null) { //log(N)
                while (current.left != null) {
                    current = current.left;
                }
                Node<T> newNode = new Node<>(current.value);
                newNode.left = node.left;
                newNode.right = node.right;

                current = node.right;               //убираем тот элемент, значение которого взяли на позицию удаленного
                while (current.left.left != null) {
                    current = current.left;
                }
                System.out.println(current.left.value);
                current.left = current.left.right;

                replace(parent, node, newNode);

            } else {
                current.left = node.left;
                replace(parent, node, current);
            }

        } else if (node.left != null) {
            replace(parent, node, node.left);
        } else {
            replace(parent, node, null);
        }

        size--;
        return true;
    }

    private void replace(Node<T> parent, Node<T> node, Node<T> newValue) {
        if (parent == node) {
            //then it is root
            root = newValue;
        } else {
            if (parent.left == node) parent.left = newValue;
            else parent.right = newValue;
        }
    }

    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinarySearchTreeIterator();
    }

    public class BinarySearchTreeIterator implements Iterator<T> {
        int index = 0;
        final int initialSize = size;
        boolean isFirst = true;
        T lastValue;
        boolean wasDeleted = true;

        private BinarySearchTreeIterator() {
            // Добавьте сюда инициализацию, если она необходима.
        }

        /**
         * Проверка наличия следующего элемента
         * <p>
         * Функция возвращает true, если итерация по множеству ещё не окончена (то есть, если вызов next() вернёт
         * следующий элемент множества, а не бросит исключение); иначе возвращает false.
         * <p>
         * Спецификация: {@link Iterator#hasNext()} (Ctrl+Click по hasNext)
         * <p>
         * Средняя
         */
        @Override
        public boolean hasNext() {
            return index < initialSize;
        }

        /**
         * Получение следующего элемента
         * <p>
         * Функция возвращает следующий элемент множества.
         * Так как BinarySearchTree реализует интерфейс SortedSet, последовательные
         * вызовы next() должны возвращать элементы в порядке возрастания.
         * <p>
         * Бросает NoSuchElementException, если все элементы уже были возвращены.
         * <p>
         * Спецификация: {@link Iterator#next()} (Ctrl+Click по next)
         * <p>
         * Средняя
         */

        //Ресурсоемкость: O(1)
        //Трудоемкость: Найти следующий элемент - log(N), пройти по всему дереву - N*log(N)
        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            if (isFirst) {
                lastValue = first();
                isFirst = false;
                wasDeleted = false;
                index++;
                return lastValue;
            }

            lastValue = findNext(root);
            index++;
            wasDeleted = false;
            return lastValue;
        }


        //До того как уже доделал задание не знал, что можно изменять класс Node<T> и использовать
        //другие готовые структуры данных как стеки и списки, иначе бы возможно сделал задание эффективнее
        private T findNext(Node<T> node) {
            if (node.left == null && node.right == null) return node.value;
            else {
                if (node.value.compareTo(lastValue) > 0) {
                    if (node.left == null) return node.value;
                    else if (node.left.value.compareTo(lastValue) == 0 && node.left.right == null) return node.value;
                    else {
                        if (findNext(node.left) == lastValue) return node.value;
                        else return findNext(node.left);
                    }
                } else if (node.value.compareTo(lastValue) == 0) {
                    if (node.right == null) return node.value;
                    return findNext(node.right);
                } else {
                    return findNext(node.right);
                }
            }
        }

        /**
         * Удаление предыдущего элемента
         * <p>
         * Функция удаляет из множества элемент, возвращённый крайним вызовом функции next().
         * <p>
         * Бросает IllegalStateException, если функция была вызвана до первого вызова next() или же была вызвана
         * более одного раза после любого вызова next().
         * <p>
         * Спецификация: {@link Iterator#remove()} (Ctrl+Click по remove)
         * <p>
         * Сложная
         */
        //Ресурсоемкость: O(1)
        //Трудоемкость: O(log(N))
        @Override
        public void remove() {
            if (wasDeleted) throw new IllegalStateException();
            BinarySearchTree.this.remove(lastValue);
            wasDeleted = true;
        }
    }

    /**
     * Подмножество всех элементов в диапазоне [fromElement, toElement)
     * <p>
     * Функция возвращает множество, содержащее в себе все элементы дерева, которые
     * больше или равны fromElement и строго меньше toElement.
     * При равенстве fromElement и toElement возвращается пустое множество.
     * Изменения в дереве должны отображаться в полученном подмножестве, и наоборот.
     * <p>
     * При попытке добавить в подмножество элемент за пределами указанного диапазона
     * должен быть брошен IllegalArgumentException.
     * <p>
     * Спецификация: {@link SortedSet#subSet(Object, Object)} (Ctrl+Click по subSet)
     * (настоятельно рекомендуется прочитать и понять спецификацию перед выполнением задачи)
     * <p>
     * Очень сложная (в том случае, если спецификация реализуется в полном объёме)
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
//        return new SubSet<T>(fromElement, toElement);
//    }
//
//    private final class SubSet<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {
//        final T fromElement;
//        final T toElement;
//        private int size = 0;
//
//        SubSet(T fromElement, T toElement) {
//            if (fromElement == null || toElement == null || fromElement.compareTo(toElement) > 0 ) {
//                throw new IllegalArgumentException();
//            }
//            this.fromElement = fromElement;
//            this.toElement = toElement;
//        }
//
//        public boolean contains(Object o) {
//            @SuppressWarnings("unchecked")
//            T t = (T) o;
//            Node<T> closest = find();
//            return closest != null && t.compareTo(closest.value) == 0;
//        }
//
//        public boolean add(T t) {
//            return true;
//        }
//
//        public boolean remove(Object o) {
//            return true;
//        }
//
//        @Override
//        public Iterator<T> iterator() {
//            return null;
//        }
//
//        @Override
//        public int size() {
//            return size;
//        }
//
//        @Override
//        public boolean checkInvariant() {
//            return false;
//        }
//
//        @Override
//        public int height() {
//            return 0;
//        }
//
//        @Nullable
//        @Override
//        public Comparator<? super T> comparator() {
//            return null;
//        }
//
//        @NotNull
//        @Override
//        public SortedSet<T> subSet(T fromElement, T toElement) {
//            return null;
//        }
//
//        @NotNull
//        @Override
//        public SortedSet<T> headSet(T toElement) {
//            return null;
//        }
//
//        @NotNull
//        @Override
//        public SortedSet<T> tailSet(T fromElement) {
//            return null;
//        }
//
//        @Override
//        public T first() {
//            return null;
//        }
//
//        @Override
//        public T last() {
//            return null;
//        }
        throw new NotImplementedError();
    }

    /**
     * Подмножество всех элементов строго меньше заданного
     * <p>
     * Функция возвращает множество, содержащее в себе все элементы дерева строго меньше toElement.
     * Изменения в дереве должны отображаться в полученном подмножестве, и наоборот.
     * <p>
     * При попытке добавить в подмножество элемент за пределами указанного диапазона
     * должен быть брошен IllegalArgumentException.
     * <p>
     * Спецификация: {@link SortedSet#headSet(Object)} (Ctrl+Click по headSet)
     * (настоятельно рекомендуется прочитать и понять спецификацию перед выполнением задачи)
     * <p>
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Подмножество всех элементов нестрого больше заданного
     * <p>
     * Функция возвращает множество, содержащее в себе все элементы дерева нестрого больше toElement.
     * Изменения в дереве должны отображаться в полученном подмножестве, и наоборот.
     * <p>
     * При попытке добавить в подмножество элемент за пределами указанного диапазона
     * должен быть брошен IllegalArgumentException.
     * <p>
     * Спецификация: {@link SortedSet#tailSet(Object)} (Ctrl+Click по tailSet)
     * (настоятельно рекомендуется прочитать и понять спецификацию перед выполнением задачи)
     * <p>
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        // TODO
        throw new NotImplementedError();
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }

    public int height() {
        return height(root);
    }

    private int height(Node<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

}