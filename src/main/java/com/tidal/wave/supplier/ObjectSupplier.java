package com.tidal.wave.supplier;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Create and maintains objects
 */
public class ObjectSupplier {

    private static final Logger logger = LoggerFactory.getLogger(ObjectSupplier.class);

    private static final ThreadLocal<Map<String, Object>> objectMap = ThreadLocal.withInitial(() -> new ConcurrentHashMap<>(200));

    //So as not to instantiate the class
    private ObjectSupplier() {
    }

    /**
     * Supplies the instance of the class. For each thread, a single instance of the class will be created
     *
     * @param klass the input class information that can used to create class or get an already created class
     * @param <T>   generic type specification
     * @return the class instance stored/created by the supplier
     */
    public static <T> Object instanceOf(Class<T> klass) {
        String className = klass.getSimpleName();
        Object objectStored = objectMap.get().get(className);
        if (objectStored != null) {
            return objectStored;
        } else {
            try {
                Constructor<T> constructor = klass.getConstructor();
                Object newObject = constructor.newInstance();
                objectMap.get().put(className, newObject);
                return newObject;
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                logger.error(e.getMessage());
            }
        }
        throw new ExceptionInInitializerError(String.format("Failed to initialize class %s", className));
    }

    /**
     * Method to add an instance to the object map
     *
     * @param object the instance of the object to be added
     */
    public static void addInstance(Object object) {
        String className = object.getClass().getSimpleName();
        objectMap.get().put(className, object);
    }

    public static void addSuperInstance(Object object) {
        String superClassName = object.getClass().getSuperclass().getSimpleName();
        objectMap.get().put(superClassName, object);
    }

    /**
     * Method flushes all the object instances
     */
    public static void flushInstances() {
        objectMap.get().forEach((k, v) -> v = null);
        objectMap.remove();
    }

    public static void flushInstance(Object object) {
        objectMap.get().remove(object.getClass().getSimpleName());
    }
}
