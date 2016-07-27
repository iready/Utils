package org.zyq.core.lang;

import java.util.TreeMap;


public class TMap extends TreeMap<String, Object> {

    public TMap put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
