package ucrex.usage.statistics.db;

import java.sql.Connection;
import java.sql.SQLException;

import oracle.jdbc.pool.OracleDataSource;

public class ConnectionPool {
    public static Connection getConnection(DataBankProperties properties) throws SQLException {
        String hostIpAddress = properties.getDatabaseHostUrl();
        String hostPort = properties.getDatabaseHostPort();
        String hostSid = properties.getDatabaseSid();
        String hostDbUser = properties.getDatabaseUser();
        String hostDbPassword = properties.getDatabasePassword();

        String jdbcUrl = "jdbc:oracle:thin:@" + hostIpAddress + ":" + hostPort + ":" + hostSid;
        OracleDataSource ds = new OracleDataSource();
        ds.setURL(jdbcUrl);
        return ds.getConnection(hostDbUser, hostDbPassword);
    }

}
