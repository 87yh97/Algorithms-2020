package lesson4;

import java.util.*;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Префиксное дерево для строк
 */
public class Trie extends AbstractSet<String> implements Set<String> {

    private static class Node {
        Map<Character, Node> children = new LinkedHashMap<>();
    }

    private Node root = new Node();

    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root.children.clear();
        size = 0;
    }

    private String withZero(String initial) {
        return initial + (char) 0;
    }

    @Nullable
    private Node findNode(String element) {
        Node current = root;
        for (char character : element.toCharArray()) {
            if (current == null) return null;
            current = current.children.get(character);
        }
        return current;
    }

    @Override
    public boolean contains(Object o) {
        String element = (String) o;
        return findNode(withZero(element)) != null;
    }

    @Override
    public boolean add(String element) {
        Node current = root;
        boolean modified = false;
        for (char character : withZero(element).toCharArray()) {
            Node child = current.children.get(character);
            if (child != null) {
                current = child;
            } else {
                modified = true;
                Node newChild = new Node();
                current.children.put(character, newChild);
                current = newChild;
            }
        }
        if (modified) {
            size++;
        }
        return modified;
    }

    @Override
    public boolean remove(Object o) {
        String element = (String) o;
        Node current = findNode(element);
        if (current == null) return false;
        if (current.children.remove((char) 0) != null) {
            size--;
            return true;
        }
        return false;
    }

    /**
     * Итератор для префиксного дерева
     *
     * Спецификация: {@link Iterator} (Ctrl+Click по Iterator)
     *
     * Сложная
     *
     */
    //Трудоемкость нахождения слова O(K) где К - среднее число букв в слове,
    //значит общая трудоемкость итерации по всему дереву равна O(N*K) = O(N)
    //Ресурсоемкость - O(N)
    @NotNull
    @Override
    public Iterator<String> iterator() {
        return new TrieIterator();
    }

    private class TrieIterator implements Iterator<String> {
        private int index = 0;
        private final int initialSize = size;
        private final ArrayList<Integer> iterIndexes = new ArrayList<>();
        private boolean wasRemoved = true;
        private Node zeroNode;

        @Override
        public boolean hasNext() {
            return index < initialSize;
        }

        @Override
        public String next() {
            if(!hasNext()) throw new NoSuchElementException();
            index++;
            wasRemoved = false;
            return layerIterator(root, "", (char) 0, 0);
        }

        private void moveToNextChar(int layerIndex) {
            iterIndexes.set(layerIndex - 1, iterIndexes.get(layerIndex - 1) + 1);
            iterIndexes.set(layerIndex, 0);
        }

        private String layerIterator (Node node, String word, char thisNodeChar, int layerIndex) {
            if (layerIndex >= iterIndexes.size()) iterIndexes.add(0);

            Set<Character> childrenKeys = node.children.keySet();

            if (node != root && thisNodeChar == (char) 0) {
                moveToNextChar(layerIndex);
                return word;
            }

            if (childrenKeys.isEmpty()) {
                moveToNextChar(layerIndex);
                return layerIterator(root, "", (char) 0, 0);
            }

            int index = 0;
            for (char character : childrenKeys) {
                if (character == (char) 0) zeroNode = node;

                if (index < iterIndexes.get(layerIndex)) {
                    index++;
                    continue;
                }

                if (node == root)
                    return layerIterator(node.children.get(character), word, character, layerIndex + 1);
                else
                    return layerIterator(node.children.get(character), word + thisNodeChar, character, layerIndex + 1);
            }
            moveToNextChar(layerIndex);

            return layerIterator(root, "", (char) 0, 0);
        }

        //Трудоемкость: O(1)
        @Override
        public void remove() {
            if (wasRemoved) throw new IllegalStateException();
            zeroNode.children.remove((char) 0);
            size--;
            wasRemoved = true;
            for (int i = 1; i < iterIndexes.size(); i++) {
                if (iterIndexes.get(i) == 0 && iterIndexes.get(i - 1) != 0) {
                    iterIndexes.set(i - 1, iterIndexes.get(i - 1) - 1);
                }
            }
        }
    }
}