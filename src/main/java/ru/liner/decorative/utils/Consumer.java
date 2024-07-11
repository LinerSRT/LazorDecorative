package ru.liner.decorative.utils;

public class Consumer<T> {

    private final T value;


    public Consumer(T value) {
        this.value = value;
    }

    public Consumer() {
        this.value = null;
    }

    public static <T> Consumer<T> of(T value) {
        return new Consumer<T>(value);
    }

    public static <T> Consumer<T> empty() {
        return new Consumer<T>();
    }


    public <S> Consumer<S> next(FunctionB<? super T, ? extends S> function) {
        try {
            return new Consumer<S>(value == null ? null : function.apply(value));
        } catch (Throwable e) {
            return Consumer.empty();
        }
    }

    public void ifPresent(Function<? super T> consumer) {
        if (value != null) {
            try {
                consumer.apply(value);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isPresent() {
        return value != null;
    }


    public T orElse(T other) {
        return value == null ? other : value;
    }

    public T orElseFun(FunctionC<T> functionC) {
        try {
            return value == null ? functionC.apply() : value;
        } catch (Throwable e) {
            return value;
        }
    }


    public T get() {
        return value;
    }


    public boolean asBoolean() {
        if (value == null)
            return false;
        if (value instanceof Boolean)
            return (Boolean) value;
        return false;
    }

    public int asInt() {
        if (value == null)
            return 0;
        if (value instanceof Integer)
            return (Integer) value;
        return 0;
    }

    public long asLong() {
        if (value == null)
            return 0;
        if (value instanceof Long)
            return (Long) value;
        return 0;
    }


    public double asDouble() {
        if (value == null)
            return 0;
        if (value instanceof Double)
            return (Double) value;
        return 0;
    }


    public float asFloat() {
        if (value == null)
            return 0;
        if (value instanceof Float)
            return (Float) value;
        return 0;
    }


    public interface Function<T> {
        void apply(T input) throws Throwable;
    }

    public interface FunctionB<T, R> {

        R apply(T input) throws Throwable;
    }

    public interface FunctionC<R> {

        R apply() throws Throwable;
    }
}