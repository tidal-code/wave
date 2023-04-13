package com.tidal.wave.csv;



import com.tidal.wave.scenario.ScenarioInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static com.tidal.wave.data.GlobalData.addObjectData;


public class DataResolver<T> {

    private static final Logger logger = LogManager.getLogger(DataResolver.class);

    public static synchronized <T> void resolveData(T modelClass, String csvFile) {
        String testCaseName = ScenarioInfo.getScenarioName();
        DataResolver<T> dataResolver = new DataResolver<>();
        dataResolver.resolveData(modelClass, csvFile, testCaseName);
    }

    @SuppressWarnings("unchecked")
    public static synchronized <T> void resolveDataObject(Class<?> modelClass, String csvFile) {
        String testCaseName = ScenarioInfo.getScenarioName();
        DataResolver<T> dataResolver = new DataResolver<>();

        Object classInstance;

        try {
            classInstance = Class.forName(modelClass.getName()).getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        }
        dataResolver.resolveData((T) classInstance, csvFile, testCaseName);
        addObjectData(modelClass.getSimpleName(), classInstance);
    }

    public static synchronized <T> void resolveDataObject(T modelClass, String csvFile) {
        String testCaseName = ScenarioInfo.getScenarioName();
        DataResolver<T> dataResolver = new DataResolver<>();
        dataResolver.resolveData(modelClass, csvFile, testCaseName);
        addObjectData(modelClass.getClass().getSimpleName(), modelClass);
    }

    public static synchronized <T> void copyData(T source, T destination) {
        Objects.requireNonNull(source, "Source class instance should not be null.");
        Objects.requireNonNull(destination, "Destination class instance should not be null.");

        BeanInfo destinationClassBeanInfo = null;
        try {
            destinationClassBeanInfo = Objects.requireNonNull(Introspector.getBeanInfo(destination.getClass()));
        } catch (IntrospectionException e) {
            logger.warn("Introspection error: " + e.getMessage());
        }

        PropertyDescriptor[] destinationDescriptors = Objects.requireNonNull(destinationClassBeanInfo).getPropertyDescriptors();

        Map<String, Object> sourceValues = new HashMap<>();
        for(Field sourceField : source.getClass().getDeclaredFields()){
            try {
                sourceField.setAccessible(true);
                sourceValues.put(sourceField.getName(), sourceField.get(source));
            } catch (IllegalAccessException ignored) {
                //ignored as the fields are set accessible
            } finally {
                sourceField.setAccessible(false);
            }
        }

        for(PropertyDescriptor destinationPropDescriptor : destinationDescriptors){
            String propName = destinationPropDescriptor.getName();
            sourceValues.forEach((k, v) -> {
                if (propName.equalsIgnoreCase(k)) {
                    Method setter = destinationPropDescriptor.getWriteMethod();
                    //One of the property, 'class' does not have a setter. So a null check is required to avoid 'NullPointerException'
                    if (setter != null) {
                        try {
                            setter.invoke(destination, v);
                        } catch (InvocationTargetException | IllegalAccessException e) {
                            logger.error(e.getMessage());
                        }
                    }
                }
            });
        }
        printResolvedData(destination);
    }

    private static synchronized <T> void printResolvedData(T modelClass) {
        StringBuilder fieldValues = new StringBuilder(System.lineSeparator());
        for (Field field : modelClass.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = null;
            try {
                value = field.get(modelClass);
            } catch (IllegalAccessException ignored) {
                logger.info(String.format("Cannot access field '%s'", name));
            } finally {
                field.setAccessible(false);
            }
            fieldValues.append("     ")
                    .append(name).append(" : ").append(value).append(System.lineSeparator());
        }
        logger.info(fieldValues.toString());
    }

    protected synchronized void resolveData(T modelClass, String csvFile, String testCaseName) {
        BeanInfo beaninfo = null;
        Set<String> columnHeaders;

        try {
            beaninfo = Introspector.getBeanInfo(modelClass.getClass());
        } catch (IntrospectionException e) {
            logger.warn("Introspection error: " + e.getMessage());
        }

        PropertyDescriptor[] descriptors;
        descriptors = Objects.requireNonNull(beaninfo).getPropertyDescriptors();
        CsvData csvData = new CsvData();
        LinkedHashMap<String, String> dataMap = csvData.readData(csvFile, testCaseName);
        columnHeaders = dataMap.keySet();

        for (PropertyDescriptor pd : descriptors) {
            String propName = pd.getName();

            for (String header : columnHeaders) {
                if (propName.equalsIgnoreCase(header.replace(" ", ""))) {
                    Method setter = pd.getWriteMethod();
                    if (setter != null) {
                        try {
                            setter.invoke(modelClass, dataMap.get(header));
                        } catch (InvocationTargetException e) {
                            logger.error(e.getMessage());
                        } catch (IllegalAccessException ignored) {
                            //ignored
                        }
                    }
                }
            }
        }
        printResolvedData(modelClass);
    }

    public synchronized List<T> resolveDataAsList(Class<?> modelClass, String csvFile) {
        return resolveDataAsList(modelClass, csvFile, ScenarioInfo.getScenarioName());
    }

    @SuppressWarnings("unchecked")
    public synchronized List<T> resolveDataAsList(Class<?> modelClass, String csvFile, String testCaseName) {
        BeanInfo beaninfo = null;
        Set<String> columnHeaders;
        List<T> classList = new ArrayList<>();

        CsvData csvData = new CsvData();
        List<HashMap<String, String>> dataMapList = csvData.readDataAsMapList(csvFile, testCaseName);

        for (HashMap<String, String> dataMap : dataMapList) {

            Object instance = null;

            try {
                Class<?> clazz = Class.forName(modelClass.getName());
                instance = clazz.getDeclaredConstructor().newInstance();
                beaninfo = Introspector.getBeanInfo(instance.getClass());
            } catch (IntrospectionException | ClassNotFoundException | InstantiationException e) {
                logger.error(e.getMessage());
            } catch (IllegalAccessException ignored) {
                //ignored
            } catch (InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }

            PropertyDescriptor[] descriptors;
            descriptors = Objects.requireNonNull(beaninfo).getPropertyDescriptors();


            columnHeaders = dataMap.keySet();
            for (PropertyDescriptor pd : descriptors) {
                String propName = pd.getName();

                for (String header : columnHeaders) {
                    if (propName.equalsIgnoreCase(header.replace(" ", ""))) {
                        Method setter = pd.getWriteMethod();
                        if (setter != null) {
                            try {
                                setter.invoke(instance, dataMap.get(header));
                            } catch (InvocationTargetException e) {
                                logger.error(e.getMessage());
                            } catch (IllegalAccessException ignored) {
                                //ignored
                            }
                        }
                    }
                }
            }
            classList.add((T) instance);
        }
        return classList;
    }

    private synchronized List<String> getResolvedValues(T modelClass) {
        List<String> resolvedValues = new ArrayList<>();

        for (Field field : modelClass.getClass().getDeclaredFields()) {
            String name = field.getName();
            Object value = null;
            try {
                value = field.get(modelClass);
                if (value != null) {
                    resolvedValues.add(value.toString());
                }
            } catch (IllegalAccessException ignored) {
                //ignored
            }
            logger.info(name + " : " + value);
        }
        return resolvedValues;
    }
}