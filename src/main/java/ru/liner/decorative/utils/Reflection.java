package ru.liner.decorative.utils;

import scala.util.parsing.combinator.testing.Str;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

@SuppressWarnings("unchecked")
public class Reflection {

    public  static Object toObject(Class<?> clazz, String value) {
        if (Boolean.class == clazz) return Boolean.parseBoolean(value);
        if (Byte.class == clazz) return Byte.parseByte(value);
        if (Short.class == clazz) return Short.parseShort(value);
        if (Integer.class == clazz) return Integer.parseInt(value);
        if (Long.class == clazz) return Long.parseLong(value);
        if (Float.class == clazz) return Float.parseFloat(value);
        if (Double.class == clazz) return Double.parseDouble(value);
        return value;
    }

    public static <Value> void set(final Object object, final String fieldName, final Value value) {
        final Field field = Consumer.of(object.getClass())
                .next(new Consumer.FunctionB<Class<?>, Field>() {
                    @Override
                    public Field apply(Class<?> input) throws Throwable {
                        return input.getDeclaredField(fieldName);
                    }
                })
                .orElse(null);
        Consumer.of(field)
                .ifPresent(new Consumer.Function<Field>() {
                    @Override
                    public void apply(Field input) throws Throwable {
                        field.setAccessible(true);
                        field.set(object, value);
                    }
                });
    }

    public static <Value> void set(final Object object, final Field field, final Value value) {
        Consumer.of(field)
                .ifPresent(new Consumer.Function<Field>() {
                    @Override
                    public void apply(Field input) throws Throwable {
                        field.setAccessible(true);
                        field.set(object, value);
                    }
                });
    }

    public static <Value> void setSafety(final Object object, String fieldName, final Value value) {
        Consumer
                .of(findField(object, fieldName))
                .ifPresent(new Consumer.Function<Field>() {
                    @Override
                    public void apply(Field input) throws Throwable {
                        if (!input.isAccessible())
                            input.setAccessible(true);
                        input.set(object, value);
                    }
                });
    }

    public static boolean hasField(final Object object, String fieldName) {
        return findField(object, fieldName) != null;
    }

    public static <Value> Value getSafety(final Object object, String fieldName, Value defaultValue) {
        return Consumer
                .of(findField(object, fieldName))
                .next(new Consumer.FunctionB<Field, Value>() {
                    @Override
                    public Value apply(Field input) throws Throwable {
                        if (!input.isAccessible())
                            input.setAccessible(true);
                        return (Value) input.get(object);
                    }
                }).orElse(defaultValue);
    }

    public static <Value> Value get(final Object object, Field field) {
        return Consumer
                .of(field)
                .next(new Consumer.FunctionB<Field, Value>() {
                    @Override
                    public Value apply(Field input) throws Throwable {
                        if (!input.isAccessible())
                            input.setAccessible(true);
                        return (Value) input.get(object);
                    }
                }).orElse(null);
    }


    public static <I, Result> Result callMethodSafety(final I instance, String methodName, Result defaultValue, final Object... arguments) {
        Class<?>[] classes = Consumer.of(arguments)
                .next(new Consumer.FunctionB<Object[], Class<?>[]>() {
                    @Override
                    public Class<?>[] apply(Object[] input) throws Throwable {
                        Class<?>[] classes = new Class[input.length];
                        for (int i = 0; i < input.length; i++) {
                            classes[i] = input[i].getClass();
                        }
                        return classes;
                    }
                }).orElse(new Class[0]);
        return Consumer
                .of(findMethod(instance, methodName, classes))
                .next(new Consumer.FunctionB<Method, Result>() {
                    @Override
                    public Result apply(Method input) throws Throwable {
                        return (Result) input.invoke(instance, arguments);
                    }
                })
                .orElse(defaultValue);
    }

    public static <I, Result> Result callMethod(final I instance, String methodName, final Object... arguments) {
        Class<?>[] classes = Consumer.of(arguments)
                .next(new Consumer.FunctionB<Object[], Class<?>[]>() {
                    @Override
                    public Class<?>[] apply(Object[] input) throws Throwable {
                        Class<?>[] classes = new Class[input.length];
                        for (int i = 0; i < input.length; i++) {
                            classes[i] = input[i].getClass();
                        }
                        return classes;
                    }
                }).orElse(new Class[0]);
        return Consumer
                .of(findMethod(instance, methodName, classes))
                .next(new Consumer.FunctionB<Method, Result>() {
                    @Override
                    public Result apply(Method input) throws Throwable {
                        return (Result) input.invoke(instance, arguments);
                    }
                })
                .orElse(null);
    }


    public static <I> Method findMethod(I instance, final String methodName, final Class<?>... arguments) {
        final Class<I> clazz = (instance instanceof Class) ? (Class<I>) instance : (Class<I>) instance.getClass();
        try {
            Method method = clazz.getDeclaredMethod(methodName, arguments);
            method.setAccessible(true);
            return method;
        } catch (final NoSuchMethodException e) {
            Method method = Consumer.of(clazz.getDeclaredMethods())
                    .next(new Consumer.FunctionB<Method[], Method>() {
                        @Override
                        public Method apply(Method[] input) throws Throwable {
                            for (Method method : input) {
                                if (arguments.length == 0) {
                                    if (method.getName().equals(methodName))
                                        return method;
                                } else {
                                    if (method.getName().equals(methodName) && method.getParameterTypes().length == arguments.length)
                                        return method;
                                }
                            }
                            return null;
                        }
                    }).orElse(null);
            if (method != null) {
                return method;
            } else if (clazz.getSuperclass() != null) {
                return findMethod(clazz.getSuperclass(), methodName, arguments);
            } else {
                return null;
            }
        }
    }


    public static <I> Field findField(I instance, String fieldName) {
        Class<I> clazz = (instance instanceof Class) ? (Class<I>) instance : (Class<I>) instance.getClass();
        try {
            Field declaredField = clazz.getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            return declaredField;
        } catch (NoSuchFieldException e) {
            if (clazz.getSuperclass() != null) {
                return findField(clazz.getSuperclass(), fieldName);
            } else {
                return null;
            }
        }
    }

    public static <I, C> List<Field> findFieldsByClass(I instance, final Class<C> clazz) {
        Class<I> instanceClass = (instance instanceof Class) ? (Class<I>) instance : (Class<I>) instance.getClass();
        return Streams.of(instanceClass.getDeclaredFields())
                .filter(new Streams.Predicate<Field>() {
                    @Override
                    public boolean accept(Field value) {
                        return value.getType().isAssignableFrom(clazz);
                    }
                })
                .collect();
    }

    public static boolean exists(String clazz) {
        try {
            Class.forName(clazz);
            return true;
        } catch (ClassNotFoundException ignored) {
        }
        return false;
    }
}
