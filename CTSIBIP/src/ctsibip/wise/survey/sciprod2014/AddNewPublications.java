package ctsibip.wise.survey.sciprod2014;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.base.Charsets;
import com.google.common.collect.Sets;
import com.google.common.io.Files;

import ctsibip.shared.db.MySQLDataBank;

public class AddNewPublications {

    public static final String PATH = "C:\\_Pralav\\projects\\WISE and LOFTS\\Current Surveys\\SCIPROD 2014\\data\\751_pubmed_ids.txt";
    public static final String PATH2 = "C:\\_Pralav\\projects\\WISE and LOFTS\\Current Surveys\\SCIPROD 2014\\data\\257_new_pubmed_ids.txt";

    public static MySQLDataBank db = new MySQLDataBank(new DataBankProperties());

    public static void main(String[] args) throws IOException, SQLException {

        Connection conn = db.getConnection();
        try {
            List<String> newPubmedIds = Files.readLines(new File(PATH), Charsets.UTF_8);
            Set<String> newIdSet = new HashSet<String>();

            for (String id : newPubmedIds) {
                newIdSet.add(id.trim());
            }

            Set<String> oldIdSet = getOldPubmedIdSet(conn);

            Set<String> newlyAdded = Sets.difference(newIdSet, oldIdSet);

            Set<String> oldNotInNew = Sets.difference(oldIdSet, newIdSet);

            Set<String> oldAndNew = Sets.union(newIdSet, oldIdSet);

            System.out.println("There are " + oldAndNew.size() + " total ids");
            System.out.println("There are " + newlyAdded.size() + " new ids");
            System.out.println("There are " + oldNotInNew.size() + " old ids not in new");

            List<String> extraPubmedIds = Files.readLines(new File(PATH2), Charsets.UTF_8);
            Set<String> extraIdSet = new HashSet<String>();

            for (String id : extraPubmedIds) {
                extraIdSet.add(id.trim());
            }

            Set<String> extraIdNotInOldOrNew = Sets.difference(extraIdSet, oldAndNew);

            System.out.println("There are " + extraIdNotInOldOrNew.size() + " extra new ones");
            for (String id : extraIdNotInOldOrNew) {
                System.out.println(id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        conn.close();
    }

    private static Set<String> getOldPubmedIdSet(Connection conn) throws SQLException {
        String sql = "SELECT * FROM sciprod2014.publications";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        Set<String> pubmedIdSet = new HashSet<>();
        while (rs.next()) {
            pubmedIdSet.add(rs.getString(2).trim());
        }
        System.out.println("The pubmed id set has " + pubmedIdSet.size() + " values");
        return pubmedIdSet;
    }
}
