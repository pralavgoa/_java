package ucrex.usage.statistics.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.google.common.base.Strings;

public abstract class AbstractUCRexStatsProperties extends Properties {

    private static final Logger LOGGER = Logger.getLogger(AbstractUCRexStatsProperties.class);

    private static final long serialVersionUID = 1L;

    private final String fileName;
    private final String applicationName;

    public AbstractUCRexStatsProperties(String fileName, String applicationName) {
        super();
        this.fileName = fileName;
        this.applicationName = applicationName;
        this.loadFile(fileName);
    }

    public AbstractUCRexStatsProperties(InputStream stream, String fileName, String applicationName) {
        super();
        this.fileName = fileName;
        this.applicationName = applicationName;

        try {
            this.populateDefaultValues();
            this.load(stream);
            if (stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    public boolean isDirectory(String path) {
        if (Strings.isNullOrEmpty(path)) {
            return false;
        }
        File xmlDir = new File(path);
        return xmlDir.isDirectory();
    }

    protected boolean isNotNullOrEmpty(String input) {
        LOGGER.info("Checking if null or empty property: '" + input + "'");
        return !Strings.isNullOrEmpty(input);
    }

    private final void loadFile(String fileName) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fileName);
            this.populateDefaultValues();
            this.load(inputStream);
        } catch (IOException ioe) {
            LOGGER.error("Reading file: '" + fileName + "'", ioe);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOGGER.error("Closing file: '" + fileName + "'", e);
                }
            }
        }
    }

    /**
     * redefine to add default properties
     */
    protected void populateDefaultValues() {

    }

    public abstract boolean isValid();

    public String getFileName() {
        return this.fileName;
    }

    public String getApplicationName() {
        return this.applicationName;
    }

    @Override
    @Deprecated
    public String getProperty(String property) {
        return super.getProperty(property);
    }

    protected String getStringProperty(String property) {
        return super.getProperty(property);
    }

}
