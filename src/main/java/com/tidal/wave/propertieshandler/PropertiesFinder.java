package com.tidal.wave.propertieshandler;


import com.tidal.wave.encryptor.Decryptor;
import com.tidal.wave.exceptions.PropertyHandlerException;

import java.util.Locale;
import java.util.Properties;

import static com.google.common.base.Strings.isNullOrEmpty;

public class PropertiesFinder {
    private static final String SECRET_KEY_NAME = "secretKeyName";
    private static Properties propertiesController;
    private static String environment;
    private static Properties envProperties;
    private static Properties configProperties;

    //So as not to instantiate the class
    private PropertiesFinder() {
    }

    public static String getEnvironment() {
        if (environment == null) {
            propertiesController = new PropertiesHandler("properties/controller.properties").getProperties();
            environment = setEnvironment();
        }
        return environment;
    }

    public static String getProperty(String key) {
        if (!isNullOrEmpty(System.getenv(key))) {
            return System.getenv(key);
        }

        if (!isNullOrEmpty(System.getProperty(key))) {
            return System.getProperty(key);
        }
        configProperties = getConfigProperties();

        String propertyValue = configProperties.getProperty(key);

        if (!isNullOrEmpty(propertyValue) && propertyValue.startsWith("ENC(")) {
            return Decryptor.decrypt(getSecretKey(), propertyValue);
        } else if (!isNullOrEmpty(propertyValue)) {
            return propertyValue;
        }

        envProperties = getEnvProperties();

        String envProperty = envProperties.getProperty(key);

        if (!isNullOrEmpty(envProperty) && envProperty.startsWith("ENC(")) {
            return Decryptor.decrypt(getSecretKey(), envProperty);
        }

        return envProperty;
    }

    private static String getSecretKey() {
        if (isNullOrEmpty(configProperties.getProperty(SECRET_KEY_NAME))) {
            throw new PropertyHandlerException("secretKeyName is null or cannot be found from 'configuration.properties' file.");
        }
        String secretKeyName = configProperties.getProperty(SECRET_KEY_NAME);

        String secretKey = System.getenv(secretKeyName) == null ? System.getProperty(secretKeyName) : System.getenv(secretKeyName);

        if (isNullOrEmpty(secretKey)) {
            throw new PropertyHandlerException(String.format("You do not have a secret value stored in your env variables with key '%s'", configProperties.getProperty(SECRET_KEY_NAME)));
        }

        return secretKey;
    }

    private static Properties getEnvProperties() {
        if (envProperties == null) {
            envProperties = new PropertiesHandler("properties/application-" + getEnvironment().toLowerCase(Locale.ROOT) + ".properties").getProperties();
        }
        return envProperties;
    }

    private static Properties getConfigProperties() {
        if (configProperties == null) {
            configProperties = new PropertiesHandler("configuration.properties").getProperties();
        }
        return configProperties;
    }


    private static String setEnvironment() {
        String env = System.getenv("env");
        if (isNullOrEmpty(env)) {
            env = System.getProperty("env");
        }
        if (isNullOrEmpty(env)) {
            env = propertiesController.getProperty("environment");
        }

        return env;
    }

}
