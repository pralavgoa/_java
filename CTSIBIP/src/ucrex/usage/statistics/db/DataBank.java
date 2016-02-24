package ucrex.usage.statistics.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ucrex.usage.statistics.Constants;
import ucrex.usage.statistics.model.UserAndQueryStats;
import ucrex.usage.statistics.model.UserAndQueryStats.StatType;
import ucrex.usage.statistics.properties.UCRexStatsProperties;
import ctsibip.shared.utils.SQLTemplateUtils;

public class DataBank {

    private final String sqlFolderPath;
    private final DataBankProperties properties;
    private Connection connection;

    public DataBank(String sqlFolderPath, DataBankProperties properties) {
        this.sqlFolderPath = sqlFolderPath;
        this.properties = properties;
    }

    public void open() throws SQLException {
        this.connection = ConnectionPool.getConnection(this.properties);
    }

    public void close() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public UserAndQueryStats getUserAndQueryStats() throws SQLException {
        UserAndQueryStats stats = new UserAndQueryStats();
        this.chartQueryResults(StatType.QUERIES, this.connection,
                SQLTemplateUtils.getSQLFromTemplate(this.sqlFolderPath, "queries.sql"), stats);
        this.chartQueryResults(StatType.USERS, this.connection,
                SQLTemplateUtils.getSQLFromTemplate(this.sqlFolderPath, "users.sql"), stats);
        return stats;
    }

    public UserAndQueryStats getSuccessAndFailureStats() throws SQLException {
        UserAndQueryStats stats = new UserAndQueryStats();
        this.chartQueryResults(StatType.TOTAL_QUERIES, this.connection,
                SQLTemplateUtils.getSQLFromTemplate(this.sqlFolderPath, "total_queries.sql"), stats);
        this.chartQueryResults(StatType.FAILED_QUERIES, this.connection,
                SQLTemplateUtils.getSQLFromTemplate(this.sqlFolderPath, "failed_queries.sql"), stats);
        return stats;
    }

    public String getAllNumberOfObservations() throws SQLException {
        String sql = SQLTemplateUtils.getSQLFromTemplate(this.sqlFolderPath, "number_of_observations.sql");
        return this.getNumberOfObservations(sql);
    }

    private String getNumberOfObservations(String sql) throws SQLException {
        StringBuilder sb = new StringBuilder();

        PreparedStatement stmt = this.connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            sb.append(rs.getString(1) + ",");
            sb.append(rs.getString(2)).append(Constants.NEWLINE);
        }
        return sb.toString();
    }

    private String getNumberOfObservationsJavascript(String sql) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("['1', '2']");
        PreparedStatement stmt = this.connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            sb.append(",").append(Constants.NEWLINE);
            sb.append("['" + rs.getString(1) + "',");
            sb.append("'" + rs.getString(2) + "']");
        }
        return sb.toString();
    }

    public String getNumberOfObservationsTotal() throws SQLException {
        String sql = SQLTemplateUtils.getSQLFromTemplate(this.sqlFolderPath, "number_of_observations_total.sql");
        return this.getNumberOfObservationsJavascript(sql);
    }

    public void query4(String sql) throws SQLException {

        PreparedStatement stmt = this.connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            System.out.print(rs.getString(1) + ",");
            System.out.println(rs.getString(2));
        }
    }

    private void chartQueryResults(StatType statType, Connection con, String sql, UserAndQueryStats stats)
            throws SQLException {
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            String yearMonth = rs.getString(1);
            String number = rs.getString(2);
            stats.put(statType, yearMonth, number);
        }
    }

    public static void main(String[] args) throws SQLException, IOException {
        UCRexStatsProperties properties = new UCRexStatsProperties("conf/dev/ucrexstats.properties", "UCRexStats");

        DataBank db = new DataBank("WebContent/sql", properties);
        db.open();
        // System.out.println(db.getUserAndQueryStats().usersAndQueriesAsString());
        // System.out.println(db.getSuccessAndFailureStats().successAndFailureQueriesAsString());
        // System.out.println(db.getSuccessAndFailureStats().percentageOfFailedQueriesAsString());

        System.out.println(db.getNumberOfObservationsTotal());
        db.close();
    }
}
