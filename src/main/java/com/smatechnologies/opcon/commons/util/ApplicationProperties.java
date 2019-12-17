package com.smatechnologies.opcon.commons.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;


/**
 * This class handles reading file maven application.properties
 *
 * @author Pierre PINON
 */
public class ApplicationProperties {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationProperties.class);

    private static final String DEFAULT_PROPERTIES_FILE = "/application.properties";

    private static final Map<String, ApplicationProperties> INSTANCE_MAP = new HashMap<>();

    private Properties properties;

    //Singleton
    private ApplicationProperties(String propertiesFile) throws ApplicationPropertiesException {
        InputStream inputStream = ApplicationProperties.class.getResourceAsStream(propertiesFile);

        if (inputStream == null) {
            throw new ApplicationPropertiesException("Application properties file does not exists");
        }

        properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new ApplicationPropertiesException("Can not get properties", e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                LOG.error("Can not close InputStream", e);
            }
        }
    }

    public static ApplicationProperties getInstance() throws ApplicationPropertiesException {
        return getInstance(DEFAULT_PROPERTIES_FILE);
    }

    public static ApplicationProperties getInstance(String propertiesFile) throws ApplicationPropertiesException {
        Objects.requireNonNull(propertiesFile, "PropertiesFile cannot be null");

        ApplicationProperties instance = INSTANCE_MAP.get(propertiesFile);

        if (instance == null) {
            instance = new ApplicationProperties(propertiesFile);
        }

        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(IApplicationProperty applicationProperty) {
        return properties.getProperty(Optional.ofNullable(applicationProperty).map(IApplicationProperty::getKey).orElse(null));
    }

    public interface IApplicationProperty {

        String getKey();
    }

    public static class ApplicationPropertiesException extends Exception {

        private static final long serialVersionUID = 1L;

        public ApplicationPropertiesException() {
            super();
        }

        public ApplicationPropertiesException(String message, Throwable cause) {
            super(message, cause);
        }

        public ApplicationPropertiesException(String message) {
            super(message);
        }

        public ApplicationPropertiesException(Throwable cause) {
            super(cause);
        }
    }
}
