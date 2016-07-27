package org.zyq.swing.console;

import java.util.Collection;
import java.util.LinkedList;

public class LimiteList<E> extends LinkedList<E> {

    private int initialCapacity;

    public LimiteList(int initialCapacity) {
        this.initialCapacity = initialCapacity;
    }

    public int getInitialCapacity() {
        return initialCapacity;
    }

    public boolean add(E e) {
        boolean result = false;
        synchronized (this) {
            if (this.size() + 1 > initialCapacity) {
                this.remove(0);
            }
            super.add(e);
        }
        return result;
    }

    public void add(int index, E element) {
    }

    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }
}
