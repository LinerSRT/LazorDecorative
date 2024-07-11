package ru.liner.decorative.utils;

import java.util.*;

@SuppressWarnings({"ForLoopReplaceableByForEach", "unchecked"})
public class Streams<T> {
    private final List<T> elements;

    private Streams(List<T> elements) {
        this.elements = elements;
    }

    @SafeVarargs
    private Streams(T... elements) {
        this.elements = Arrays.asList(elements);
    }

    @SafeVarargs
    public static <T> Streams<T> of(T... elements) {
        return new Streams<T>(elements);
    }

    public static <K, V> Streams<Map.Entry<K, V>> of(Set<Map.Entry<K, V>> set) {
        return new Streams<>(new ArrayList<>(set));
    }

    public static <T> Streams<T> of(List<T> elements) {
        return new Streams<>(elements);
    }

    public Streams<T> sorted(Comparator<T> comparator) {
        Collections.sort(elements, comparator);
        return of(elements);
    }

    public <K, V> LinkedHashMap<K, V> collectLinkedMap(final MapCollector<T, K, V> collector) {
        final LinkedHashMap<K, V> hashMap = new LinkedHashMap<K, V>();
        forEach(new Iterator<T>() {
            @Override
            public void item(Streams<T> stream, T value) {
                hashMap.put(collector.getKey(value), collector.getValue(value));
            }
        });
        return hashMap;
    }

    public <K, V> HashMap<K, V> collectMap(final MapCollector<T, K, V> collector) {
        final HashMap<K, V> hashMap = new HashMap<K, V>();
        forEach(new Iterator<T>() {
            @Override
            public void item(Streams<T> stream, T value) {
                hashMap.put(collector.getKey(value), collector.getValue(value));
            }
        });
        return hashMap;
    }

    public Streams<T> removeIf(Predicate<T> filter) {
        boolean removed = false;
        final java.util.Iterator<T> each = elements.iterator();
        while (each.hasNext()) {
            if (filter.accept(each.next())) {
                each.remove();
                removed = true;
            }
        }
        return this;
    }

    public Streams<T> forEach(Iterator<T> iterator) {
        try {
            for (java.util.Iterator<T> tIterator = elements.iterator(); tIterator.hasNext(); ) {
                T item = tIterator.next();
                if (item != null)
                    iterator.item(this, item);
            }
        } catch (ArrayIndexOutOfBoundsException e){

        }
        return this;
    }

    public int size(){
        return elements.size();
    }

    public Streams<T> forEachIndexed(IndexedIterator<T> valueIterator) {
        try {
            java.util.Iterator<T> iterator = elements.iterator();
            while (iterator.hasNext()){
                T item = iterator.next();
                valueIterator.item(elements.indexOf(item), item);
            }
        } catch (ArrayIndexOutOfBoundsException e){

        }
        return this;
    }


    public <R> Streams<R> map(final Mapper<T, R> map){
        final List<R> resultList = new ArrayList<>();
        forEach(new Iterator<T>() {
            @Override
            public void item(Streams<T> stream, T value) {
                resultList.add(map.map(value));
            }
        });
        return of(resultList);
    }



    public Streams<T> peek(final Predicate<T> predicate) {
        final List<T> result = new ArrayList<T>();
        forEach(new Iterator<T>() {
            @Override
            public void item(Streams<T> stream, T value) {
                if (!predicate.accept(value))
                    result.add(value);
            }
        });
        return of(result);
    }

    public Streams<T> filter(final Predicate<T> predicate) {
        final List<T> result = new ArrayList<T>();
        forEach(new Iterator<T>() {
            @Override
            public void item(Streams<T> stream, T value) {
                if (predicate.accept(value))
                    result.add(value);
            }
        });
        return of(result);
    }

    public void ifResent(Function<T> function) {
        if (!elements.isEmpty()) {
            function.accept(elements.get(0));
        } else {
            function.orElse();
        }
    }

    public T findFirst() {
        return elements.isEmpty() ? null : elements.get(0);
    }

    public boolean isPresent() {
        return !elements.isEmpty();
    }

    public List<T> collect() {
        return elements;
    }

    public Streams<T> copy(){
        return Streams.of(new ArrayList<T>(elements));
    }

    public Streams<T> reversed() {
        List<T> list = new ArrayList<>(elements);
        Collections.reverse(list);
        return Streams.of(list);
    }

    public interface Iterator<T> {
        void item(Streams<T> stream, T value);
    }

    public interface IndexedIterator<T> {
        void item(int index, T value);
    }

    public interface Predicate<T> {
        boolean accept(T value);
    }

    public interface Function<T> {
        void accept(T value);
        void orElse();
    }

    public interface MapCollector<T, K, V> {
        K getKey(T value);

        V getValue(T value);
    }

    public interface Mapper<V, R>{
        R map(V value);
    }
}
