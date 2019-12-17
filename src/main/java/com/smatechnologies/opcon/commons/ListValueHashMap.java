package com.smatechnologies.opcon.commons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


/**
 * @author Pierre PINON
 */
public class ListValueHashMap<K, V> extends HashMap<K, List<V>> {

    /**
     * Add a value to the List. Create the list if it doesn't exists anymore
     *
     * @param key
     * @param value
     * @return values
     */
    public List<V> putListItem(K key, V value) {
        List<V> values = get(key);

        if (values == null) {
            values = new ArrayList<>();

            values.add(value);
            return put(key, values);
        } else {
            values.add(value);
        }

        return values;
    }

    /**
     * Remove value from the List. Remove the list if it is empty
     *
     * @param key
     * @param value
     *
     * @return <code>true</code> if this list contained the specified element
     */
    public boolean removeListItem(K key, V value) {
        List<V> values = get(key);

        if (values != null && values.contains(value)) {
            if (values.size() == 1) {
                remove(key);
                return true;
            } else {
                return values.remove(value);
            }
        }

        return false;
    }

    /**
     * Return the list associated to the key
     *
     * @param key
     * @return the list associated to the key or emptyList if list is null
     */
    public List<V> getListItems(K key) {
        List<V> values = get(key);

        return values == null ? Collections.emptyList() : values;
    }
}
