package com.tidal.wave.propertieshandler;


import com.tidal.wave.exceptions.PropertyHandlerException;
import com.tidal.wave.filehandlers.FileReader;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesHandler {
    private final Properties configurationPropertiesFile;

    public PropertiesHandler(String propertyPathAndFileName) {
        configurationPropertiesFile = setProperties(propertyPathAndFileName);
    }

    public PropertiesHandler(InputStream defaultProperties) {
        configurationPropertiesFile = loadProperties(defaultProperties);
    }

    private Properties setProperties(String propertyPathAndFileName) {
        InputStream fileContentsAsStream = FileReader.getFileContentsAsStream(propertyPathAndFileName);
        if (fileContentsAsStream == null) {
            throw new PropertyHandlerException(String.format("Input file/stream '%s' cannot be found or loaded", propertyPathAndFileName));
        }
        return loadProperties(FileReader.getFileContentsAsStream(propertyPathAndFileName));
    }

    public Properties loadProperties(InputStream input) {
        Properties defaultProps = new Properties();
        try {
            defaultProps.load(input);
            input.close();
        } catch (Exception e) {
            throw new PropertyHandlerException("Failed to load the application properties.", e);
        }
        return defaultProps;
    }

    public Properties getProperties() {
        return configurationPropertiesFile;
    }
}
