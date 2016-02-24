package ctsibip.wise.survey.sciprod2014;

import ctsibip.shared.db.IDataBankProperties;

public class DataBankProperties implements IDataBankProperties {

    @Override
    public String getDatabaseHostUrl() {
        return "localhost";
    }

    @Override
    public String getDatabaseHostPort() {
        return "3100";
    }

    @Override
    public String getDatabaseSid() {
        return null;
    }

    @Override
    public String getDatabaseUser() {
        return "root";
    }

    @Override
    public String getDatabasePassword() {
        return "d4t4b4se!";
    }

    @Override
    public String getDatabaseName() {
        return "sciprod2014";
    }

}
