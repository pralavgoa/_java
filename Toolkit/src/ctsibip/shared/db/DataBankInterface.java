package ctsibip.shared.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface DataBankInterface {
    Connection getConnection() throws SQLException;
}
