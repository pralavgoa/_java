package ctsibip.shared.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class MySQLDataBank extends AbstractDataBank {

    private static final Logger LOGGER = Logger.getLogger(MySQLDataBank.class);

    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://";

    public MySQLDataBank(IDataBankProperties properties) {
        super(properties);
        try {
            Class.forName(DRIVER_NAME).newInstance();
            LOGGER.info("*** Driver loaded ***");
        } catch (Exception e) {
            LOGGER.error(e);
            throw new IllegalStateException("Please provide the driver jars");
        }

    }

    public ResultSet executeSelect(String sql, Connection conn) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(sql);
        return statement.executeQuery();
    }

    public void update(String sql, Connection conn) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.executeUpdate();
    }

    public void insert(String sql, Connection conn, String... args) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(sql);
        for (int i = 1; i <= args.length; i++) {
            statement.setString(i, args[i - 1]);
        }
        statement.execute();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL + this.getProperties().getDatabaseHostUrl() + ":"
                + this.getProperties().getDatabaseHostPort() + "/" + this.getProperties().getDatabaseName(), this
                .getProperties().getDatabaseUser(), this.getProperties().getDatabasePassword());
    }
}
