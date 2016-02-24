package ctsibip.wise.survey.sciprod2014;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Strings;

public class Invitee {

    private final String id;
    private final String firstname;
    private final String lastname;
    private final String salutation;
    private final String email;
    private final String phone;
    private final String irbId;
    private final int y1_voucher;
    private final int y2_voucher;
    private final int y1_salary;
    private final int y2_salary;
    private final int y1_pilot;
    private final int y2_pilot;
    private final int y1_biostats;
    private final int y2_biostats;
    private final int ctrc_support;
    private final String supportText;
    private final int pubs_prepopulated;
    private final int grants_prepopulated;
    private final int tech_xfers;

    public Invitee(String[] arrayFromCsv) {
        this.id = arrayFromCsv[0].trim();
        this.firstname = arrayFromCsv[1].trim();
        this.lastname = arrayFromCsv[2].trim();
        this.salutation = arrayFromCsv[3].trim();
        this.email = arrayFromCsv[4].trim();
        this.phone = arrayFromCsv[5].trim();
        this.irbId = arrayFromCsv[6].trim();
        this.y1_voucher = Strings.isNullOrEmpty(arrayFromCsv[7]) ? 0 : Integer.parseInt(arrayFromCsv[7]);
        this.y2_voucher = Strings.isNullOrEmpty(arrayFromCsv[8]) ? 0 : Integer.parseInt(arrayFromCsv[8]);
        this.y1_salary = Strings.isNullOrEmpty(arrayFromCsv[9]) ? 0 : Integer.parseInt(arrayFromCsv[9]);
        this.y2_salary = Strings.isNullOrEmpty(arrayFromCsv[10]) ? 0 : Integer.parseInt(arrayFromCsv[10]);
        this.y1_pilot = Strings.isNullOrEmpty(arrayFromCsv[11]) ? 0 : Integer.parseInt(arrayFromCsv[11]);
        this.y2_pilot = Strings.isNullOrEmpty(arrayFromCsv[12]) ? 0 : Integer.parseInt(arrayFromCsv[12]);
        this.y1_biostats = Strings.isNullOrEmpty(arrayFromCsv[13]) ? 0 : Integer.parseInt(arrayFromCsv[13]);
        this.y2_biostats = Strings.isNullOrEmpty(arrayFromCsv[14]) ? 0 : Integer.parseInt(arrayFromCsv[14]);
        this.ctrc_support = Strings.isNullOrEmpty(arrayFromCsv[15]) ? 0 : Integer.parseInt(arrayFromCsv[15]);
        this.supportText = arrayFromCsv[16].trim();
        this.pubs_prepopulated = Strings.isNullOrEmpty(arrayFromCsv[17]) ? 0 : Integer.parseInt(arrayFromCsv[17]);
        this.grants_prepopulated = Strings.isNullOrEmpty(arrayFromCsv[18]) ? 0 : Integer.parseInt(arrayFromCsv[18]);
        this.tech_xfers = Strings.isNullOrEmpty(arrayFromCsv[19]) ? 0 : Integer.parseInt(arrayFromCsv[19]);

        if (Strings.isNullOrEmpty(this.email)) {
            throw new IllegalArgumentException("Email is null for " + this.firstname + " " + this.lastname);
        }
    }

    public Invitee(ResultSet rs) throws SQLException {
        this.id = rs.getString(1);
        this.firstname = rs.getString(2);
        this.lastname = rs.getString(3);
        this.salutation = rs.getString(4);
        this.email = rs.getString(5);
        this.phone = rs.getString(6);
        this.irbId = rs.getString(7);
        this.y1_voucher = rs.getInt(8);
        this.y2_voucher = rs.getInt(9);
        this.y1_salary = rs.getInt(10);
        this.y2_salary = rs.getInt(11);
        this.y1_pilot = rs.getInt(12);
        this.y2_pilot = rs.getInt(13);
        this.y1_biostats = rs.getInt(14);
        this.y2_biostats = rs.getInt(15);
        this.ctrc_support = rs.getInt(16);
        this.supportText = rs.getString(17);
        this.pubs_prepopulated = rs.getInt(18);
        this.grants_prepopulated = rs.getInt(19);
        this.tech_xfers = rs.getInt(20);
    }

    public String getId() {
        return this.id;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public String getSalutation() {
        return this.salutation;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getIrbId() {
        return this.irbId;
    }

    public int getY1_voucher() {
        return this.y1_voucher;
    }

    public int getY2_voucher() {
        return this.y2_voucher;
    }

    public int getY1_salary() {
        return this.y1_salary;
    }

    public int getY2_salary() {
        return this.y2_salary;
    }

    public int getY1_pilot() {
        return this.y1_pilot;
    }

    public int getY2_pilot() {
        return this.y2_pilot;
    }

    public int getY1_biostats() {
        return this.y1_biostats;
    }

    public int getY2_biostats() {
        return this.y2_biostats;
    }

    public int getCtrc_support() {
        return this.ctrc_support;
    }

    public String getSupportText() {
        return this.supportText;
    }

    public int getPubs_prepopulated() {
        return this.pubs_prepopulated;
    }

    public int getGrants_prepopulated() {
        return this.grants_prepopulated;
    }

    public int getTech_xfers() {
        return this.tech_xfers;
    }

    public String constructSupportText() {

        List<String> supports = new ArrayList<String>();

        if (this.getY1_voucher() == 1) {
            supports.add("2011 voucher award");
        }

        if (this.getY2_voucher() == 1) {
            supports.add("2012 voucher award");
        }

        if (this.getY1_salary() == 1) {
            supports.add("2011 salary support");
        }

        if (this.getY2_salary() == 1) {
            supports.add("2012 salary support");
        }

        if (this.getY1_pilot() == 1) {
            supports.add("2011 pilot award");
        }

        if (this.getY2_pilot() == 1) {
            supports.add("2012 pilot award");
        }

        if (this.getY1_biostats() == 1) {
            supports.add("2011 biostatistics support");
        }

        if (this.getY2_biostats() == 1) {
            supports.add("2012 biostatistics support");
        }

        if (this.getCtrc_support() == 1) {
            supports.add("CTRC support between 2011 and 2013");
        }

        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (String support : supports) {
            i++;
            if ((i == supports.size()) && (supports.size() > 1)) {
                sb.append(" and ");
            }

            sb.append(support);

            if (i < (supports.size() - 1)) {
                sb.append(", ");
            }
        }

        return sb.toString();
    }
}
