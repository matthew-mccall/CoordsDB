package dev.mmccall.coordsdb;

import java.util.HashMap;

public class HashTable<T, U, V> extends HashMap<T, HashMap<U, V>> {

    public V get(T col, U row) {
        return getOrCreateCol(col).get(row);
    }

    public void set(T col, U row, V val) {
        getOrCreateCol(col).put(row, val);
    }

    public HashMap<U, V> getOrCreateCol(T col) {
        if (!containsKey(col)) {
            put(col, new HashMap<U, V>());
        }

        return super.get(col);
    }

}
