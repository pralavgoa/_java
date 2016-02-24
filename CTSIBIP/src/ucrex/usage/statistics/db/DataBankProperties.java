package ucrex.usage.statistics.db;

public interface DataBankProperties {

    static final String DATABASE_HOST_URL = "database.host.url";
    static final String DATABASE_HOST_PORT = "database.port";
    static final String DATABASE_HOST_SID = "database.sid";
    static final String DATABASE_USER = "database.user";
    static final String DATABASE_PASSWORD = "database.password";

    String getDatabaseHostUrl();

    String getDatabaseHostPort();

    String getDatabaseSid();

    String getDatabaseUser();

    String getDatabasePassword();
}
