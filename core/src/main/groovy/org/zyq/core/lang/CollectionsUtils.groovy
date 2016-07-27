package org.zyq.core.lang

class CollectionsUtils {
    public static void main(String[] args) {
        List<Integer> s = new ArrayList<>();
        s.add(1);
        s.add(2);
        List<Integer> b = new ArrayList<>();
        b.add(3);
        b.add(4);
        s.each {
            if (it == 1) s.set(0, 23);
        }
        copy(b, s);
        println s
        println b
    }

    public static void copy(List<? extends T> dest, List<? extends T> src) {
        dest = src.clone();
        println src.hashCode()
        println src.clone().hashCode()
    }
}
