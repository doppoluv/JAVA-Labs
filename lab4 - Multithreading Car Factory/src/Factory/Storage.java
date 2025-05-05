package Factory;

import java.util.ArrayList;
import java.util.List;

public class Storage<T> {
    private final List<T> items;
    private final int capacity;

    public Storage(int capacity) {
        this.capacity = capacity;
        this.items = new ArrayList<>(capacity);
    }

    public synchronized void put(T item) throws InterruptedException {
        while (items.size() >= capacity) {
            wait(); // ждем пока не освободится место
        }
        items.add(item);
        notifyAll(); // уведомляем потоки ожидающие элементы
    }

    public synchronized T take() throws InterruptedException {
        while (items.isEmpty()) {
            wait(); // ждем пока не появится элемент
        }
        T item = items.removeFirst();
        notifyAll(); // уведомляем потоки ожидающие место
        return item;
    }

    public synchronized int size() {
        return items.size();
    }
}