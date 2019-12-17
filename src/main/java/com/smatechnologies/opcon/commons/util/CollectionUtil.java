package com.smatechnologies.opcon.commons.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;


/**
 * @author Pierre PINON
 */
public class CollectionUtil {

    private CollectionUtil() {
    }

    public static <T> int getInsertIndexToKeepSorting(List<T> list, T item) {
        if (list == null || item == null) {
            throw new IllegalArgumentException("List and Item cannot be null");
        }

        if (item instanceof Comparable) {
            return getInsertIndexToKeepSorting(list, item, (o1, o2) -> {
                @SuppressWarnings("unchecked")
                Comparable<T> comparable1 = (Comparable) o1;

                return comparable1.compareTo(o2);
            });
        } else {
            throw new IllegalArgumentException("Item should implement Comparable interface");
        }
    }

    public static <T> int getInsertIndexToKeepSorting(List<T> list, T item, Comparator<? super T> comparator) {
        if (list == null || item == null || comparator == null) {
            throw new IllegalArgumentException("List, Item and Comparator cannot be null");
        }

        for (int i = 0; i < list.size(); i++) {
            T currentItem = list.get(i);

            if (comparator.compare(currentItem, item) > 0) {
                return i;
            }
        }

        return list.size();
    }

    public static <T, U> List<T> excludeElements(List<T> list, Collection<U> elementsToExclude) {
        return excludeElements(list, elementsToExclude, Object::equals);
    }

    public static <T, U> List<T> excludeElements(List<T> list, Collection<U> elementsToExclude, ItemComparator<T, U> itemComparator) {
        return excludeElements(list, elementsToExclude, itemComparator, Collectors.toList());
    }

    public static <T, U> Set<T> excludeElements(Set<T> set, Collection<U> elementsToExclude) {
        return excludeElements(set, elementsToExclude, Object::equals);
    }

    public static <T, U> Set<T> excludeElements(Set<T> set, Collection<U> elementsToExclude, ItemComparator<T, U> itemComparator) {
        return excludeElements(set, elementsToExclude, itemComparator, Collectors.toSet());
    }

    private static <T, U, R> R excludeElements(Collection<T> collection, Collection<U> elementsToExclude, ItemComparator<T, U> itemComparator, Collector<T, ?, R> collector) {
        Objects.requireNonNull(collector, "Collector cannot be null");

        if (collection == null || collection.isEmpty() || elementsToExclude == null || elementsToExclude.isEmpty()) {
            return collection == null ? null : collection.stream().collect(collector);
        }

        Objects.requireNonNull(itemComparator, "ItemComparator cannot be null");

        return collection
                .stream()
                .filter(element -> elementsToExclude
                        .stream()
                        .filter(elementToExclude -> itemComparator.equals(element, elementToExclude))
                        .count() == 0)
                .collect(collector);
    }

    public static <T> Map<T, T> retainAll(Collection<T> collection, Collection<T> elementsToRetain) {
        return retainAll(collection, elementsToRetain, Objects::equals);
    }

    public static <T, U> Map<T, U> retainAll(Collection<T> collection, Collection<U> elementsToRetain, ItemComparator<T, U> itemComparator) {
        if (collection == null || elementsToRetain == null) {
            return null;
        }

        Map<T, U> map = new LinkedHashMap<>();

        if (collection.isEmpty() || elementsToRetain.isEmpty()) {
            return map;
        }

        Objects.requireNonNull(itemComparator, "ItemComparator cannot be null");

        collection.forEach(element -> {
            U element2 = elementsToRetain
                    .stream()
                    .filter(elementToRetain -> itemComparator.equals(element, elementToRetain))
                    .findFirst()
                    .orElse(null);

            if (element2 != null) {
                map.put(element, element2);
            }
        });

        return map;
    }

    public interface ItemComparator<T, U> {

        boolean equals(T o1, U o2);
    }

    public interface ItemCounter<T> {

        int count(T item);
    }

    public static <T> Set<Set<T>> splitSet(Set<T> items, final int maxSize, ItemCounter<T> itemCounter) {
        if (items == null) {
            return null;
        }
        Objects.requireNonNull(itemCounter, "ItemCounter cannot be null");

        items = new LinkedHashSet<>(items); //Avoid change initial set to avoid affect caller

        final Set<Set<T>> newItems = new LinkedHashSet<>();

        while (!items.isEmpty()) {
            int totalSize = 0;

            final Set<T> partialItems = new LinkedHashSet<>();

            Iterator<T> itemsIterator = items.iterator();

            while (itemsIterator.hasNext()) {
                T string = itemsIterator.next();

                int itemSize = itemCounter.count(string);
                if (itemSize > maxSize) {
                    throw new IllegalArgumentException("An item is bigger than maxSize");
                }
                totalSize += itemSize;

                if (totalSize <= maxSize) {
                    itemsIterator.remove();
                    partialItems.add(string);
                } else {
                    break;
                }
            }

            newItems.add(partialItems);
        }

        return newItems;
    }

    /**
     * @return <code>true</code> if the collection is <code>null</code> or is an empty Collection
     */
    public static boolean isNullOrEmpty(final Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> List<T> nullToEmpty(final List<T> collection) {
        if (collection == null) {
            return Collections.emptyList();
        } else {
            return collection;
        }
    }

    public static <T> Set<T> nullToEmpty(final Set<T> collection) {
        if (collection == null) {
            return Collections.emptySet();
        } else {
            return collection;
        }
    }

    public static <K, V> Map<K, V> nullToEmpty(final Map<K, V> collection) {
        if (collection == null) {
            return Collections.emptyMap();
        } else {
            return collection;
        }
    }

    public static <T> Enumeration<T> nullToEmpty(final Enumeration<T> collection) {
        if (collection == null) {
            return Collections.emptyEnumeration();
        } else {
            return collection;
        }
    }

    public static <T> Iterator<T> nullToEmpty(final Iterator<T> collection) {
        if (collection == null) {
            return Collections.emptyIterator();
        } else {
            return collection;
        }
    }

    public static <T> ListIterator<T> nullToEmpty(final ListIterator<T> collection) {
        if (collection == null) {
            return Collections.emptyListIterator();
        } else {
            return collection;
        }
    }

    public static <K, V> NavigableMap<K, V> nullToEmpty(final NavigableMap<K, V> collection) {
        if (collection == null) {
            return Collections.emptyNavigableMap();
        } else {
            return collection;
        }
    }

    public static <K, V> SortedMap<K, V> nullToEmpty(final SortedMap<K, V> collection) {
        if (collection == null) {
            return Collections.emptySortedMap();
        } else {
            return collection;
        }
    }

    public static <T> NavigableSet<T> nullToEmpty(final NavigableSet<T> collection) {
        if (collection == null) {
            return Collections.emptyNavigableSet();
        } else {
            return collection;
        }
    }

    public static <T> SortedSet<T> nullToEmpty(final SortedSet<T> collection) {
        if (collection == null) {
            return Collections.emptySortedSet();
        } else {
            return collection;
        }
    }

    public static <T extends Collection> T emptyToNull(final T collection) {
        if (collection != null && !collection.isEmpty()) {
            return collection;
        } else {
            return null;
        }
    }
}
