package ctsibip.shared.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractDataBank implements DataBankInterface {
    private final IDataBankProperties properties;

    public AbstractDataBank(IDataBankProperties properties) {
        this.properties = properties;
    }

    public IDataBankProperties getProperties() {
        return this.properties;
    }

    public static String printResultSet(ResultSet rs, int columns) throws SQLException {
        StringBuilder sb = new StringBuilder();
        while (rs.next()) {
            for (int i = 1; i <= columns; i++) {
                sb.append(rs.getString(i)).append(",");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
