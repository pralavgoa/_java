package ucrex.usage.statistics;

import ucrex.usage.statistics.db.DataBank;
import ucrex.usage.statistics.properties.AbstractUCRexStatsProperties;
import ucrex.usage.statistics.properties.UCRexStatsProperties;

public class UCRexStats {

    private static UCRexStats ucRexStats;

    private final UCRexStatsProperties properties;
    private final DataBank databank;

    private UCRexStats(UCRexStatsProperties properties, String rootFolderPath) {
        this.properties = properties;
        this.databank = new DataBank(rootFolderPath + "/sql", properties);
    }

    public static void initialize(UCRexStatsProperties properties, String rootFolderPath) {
        if (ucRexStats == null) {
            ucRexStats = new UCRexStats(properties, rootFolderPath);
        }
    }

    public static UCRexStats getInstance() {
        if (ucRexStats == null) {
            throw new IllegalStateException("The application is not initialized properly");
        }
        return ucRexStats;
    }

    public AbstractUCRexStatsProperties getProperties() {
        return this.properties;
    }

    public DataBank getDatabank() {
        return this.databank;
    }
}
