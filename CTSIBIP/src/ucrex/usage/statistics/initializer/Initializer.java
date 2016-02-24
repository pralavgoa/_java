package ucrex.usage.statistics.initializer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import ucrex.usage.statistics.UCRexStats;
import ucrex.usage.statistics.properties.UCRexStatsProperties;

public class Initializer implements ServletContextListener {

    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String PROPERTIES_FILENAME = "ucrexstats.properties";

    private static final Logger LOGGER = Logger.getLogger(Initializer.class);

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // do nothing

    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            String rootFolderPath = servletContextEvent.getServletContext().getRealPath("/");

            UCRexStatsProperties properties = new UCRexStatsProperties(rootFolderPath + FILE_SEPARATOR
                    + PROPERTIES_FILENAME, "UCRexStats");

            UCRexStats.initialize(properties, rootFolderPath);
            LOGGER.info("UCRexStats initialized");
        } catch (Exception e) {
            LOGGER.error("UCRexStats not initialized properly", e);
        }
    }
}