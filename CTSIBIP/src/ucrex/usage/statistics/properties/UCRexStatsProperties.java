package ucrex.usage.statistics.properties;

import ucrex.usage.statistics.db.DataBankProperties;

import com.google.common.base.Strings;

public class UCRexStatsProperties extends AbstractUCRexStatsProperties implements DataBankProperties {

    public UCRexStatsProperties(String fileName, String applicationName) {
        super(fileName, applicationName);
        if (!this.isValid()) {
            throw new IllegalStateException("Properties file is not valid.");
        }
    }

    private static final long serialVersionUID = 1L;

    @Override
    public String getDatabaseHostUrl() {
        return this.getStringProperty(DATABASE_HOST_URL);
    }

    @Override
    public String getDatabaseHostPort() {
        return this.getStringProperty(DATABASE_HOST_PORT);
    }

    @Override
    public String getDatabaseSid() {
        return this.getStringProperty(DATABASE_HOST_SID);
    }

    @Override
    public String getDatabaseUser() {
        return this.getStringProperty(DATABASE_USER);
    }

    @Override
    public String getDatabasePassword() {
        return this.getStringProperty(DATABASE_PASSWORD);
    }

    @Override
    public boolean isValid() {

        boolean result = true;

        result = result && !Strings.isNullOrEmpty(this.getDatabaseHostUrl());
        result = result && !Strings.isNullOrEmpty(this.getDatabaseHostPort());
        result = result && !Strings.isNullOrEmpty(this.getDatabaseSid());
        result = result && !Strings.isNullOrEmpty(this.getDatabaseUser());
        result = result && !Strings.isNullOrEmpty(this.getDatabasePassword());

        return result;
    }

}
