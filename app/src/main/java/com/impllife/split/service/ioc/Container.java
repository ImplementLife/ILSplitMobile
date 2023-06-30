package com.impllife.split.service.ioc;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public class Container {
    private Container() {}
    private static final Map<Class<?>, Object> beans = new HashMap<>();

    public static <T> void putBean(T bean) {
        requireNonNull(bean);
        beans.put(bean.getClass(), bean);
    }
    public static <T> void putBean(Class<T> type, T bean) {
        requireNonNull(type);
        requireNonNull(bean);
        beans.put(type, bean);
    }

    public static <P, T> void putBean(Class<P> type, Function<P, T> bean) {
        requireNonNull(bean);
        T t = requireNonNull(bean.apply(inject(type)));
        beans.put(t.getClass(), t);
    }
    public static <T> void putBean(Supplier<T> bean) {
        requireNonNull(bean);
        T t = requireNonNull(bean.get());
        beans.put(t.getClass(), t);
    }

    /**
     * Be careful, it can create StackOverFlow in {@link #inject(Class type)}
     * in time init object one Supplier via Constructor can invoke other Supplier and so on in a cycle
     */
    public static <T> void putBeanSupplier(Class<T> type, Supplier<T> bean) {
        requireNonNull(type);
        requireNonNull(bean);
        beans.put(type, bean);
    }

    public static <T> T inject(Class<T> type) {
        requireNonNull(type);
        T t;
        try {
            Object o = beans.get(type);
            if (Objects.equals(o, type)) {
                throw new IllegalStateException("the bean was try created earlier, but don't complete success");
            }
            if (o instanceof Supplier) {
                o = ((Supplier<?>) o).get();
            }
            t = (T) o;
        } catch (ClassCastException e) {
            throw new IllegalStateException("ClassCastException " + type.getSimpleName(), e);
        }
        if (t == null) {
            try {
                Constructor<T> declaredConstructor = type.getDeclaredConstructor();
                t = declaredConstructor.newInstance();
                putBean(t);
            } catch (Throwable e) {
                beans.put(type, type);
                throw new IllegalStateException(type.getSimpleName() + " is not exist in container, and don't have public constructor without arguments", e);
            }
        }
        return t;
    }

}
