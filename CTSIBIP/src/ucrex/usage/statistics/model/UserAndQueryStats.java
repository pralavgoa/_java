package ucrex.usage.statistics.model;

import ucrex.usage.statistics.Constants;

public class UserAndQueryStats {

    public static enum StatType {
        USERS, QUERIES, TOTAL_QUERIES, FAILED_QUERIES
    };

    private final MonthlyNumbers usersAndQueriesNumbers = new MonthlyNumbers();
    private final MonthlyNumbers successAndFailedQueriesNumbers = new MonthlyNumbers();

    private MonthlyNumber getMonthlyNumber(MonthlyNumbers monthlyNumbers, String key) {
        MonthlyNumber monthlyNumber = monthlyNumbers.get(key);
        if (monthlyNumber == null) {
            monthlyNumber = new MonthlyNumber();
        }
        return monthlyNumber;
    }

    public void put(StatType statType, String key, String value) {
        switch (statType) {
        case USERS: {
            MonthlyNumber monthlyNumber = this.getMonthlyNumber(this.usersAndQueriesNumbers, key);
            monthlyNumber.setNumberOfUsers(Integer.parseInt(value));
            this.usersAndQueriesNumbers.put(key, monthlyNumber);
        }
            break;
        case QUERIES: {
            MonthlyNumber monthlyNumber = this.getMonthlyNumber(this.usersAndQueriesNumbers, key);
            monthlyNumber.setNumberOfQueries(Integer.parseInt(value));
            this.usersAndQueriesNumbers.put(key, monthlyNumber);
        }
            break;
        case TOTAL_QUERIES: {
            MonthlyNumber monthlyNumber = this.getMonthlyNumber(this.successAndFailedQueriesNumbers, key);
            monthlyNumber.setNumberOfTotalQueries(Integer.parseInt(value));
            this.successAndFailedQueriesNumbers.put(key, monthlyNumber);
        }
            break;
        case FAILED_QUERIES: {
            MonthlyNumber monthlyNumber = this.getMonthlyNumber(this.successAndFailedQueriesNumbers, key);
            monthlyNumber.setNumberOfFailedQueries(Integer.parseInt(value));
            this.successAndFailedQueriesNumbers.put(key, monthlyNumber);
        }
            break;
        }
    }

    public String percentageOfFailedQueriesAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append("['Month', 'Percentage Failed']");
        for (String key : this.successAndFailedQueriesNumbers.keySet()) {
            sb.append(",").append(Constants.NEWLINE);
            sb.append("['" + key + "'");
            MonthlyNumber monthlyNumber = this.successAndFailedQueriesNumbers.get(key);
            int total = monthlyNumber.getNumberOfTotalQueries();
            int failed = monthlyNumber.getNumberOfFailedQueries();
            sb.append("," + ((failed * 100.0) / total));
            sb.append("]");
        }
        return sb.toString();
    }

    public String successAndFailureQueriesAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append("['Month', 'Total', 'Failed']");
        for (String key : this.successAndFailedQueriesNumbers.keySet()) {
            sb.append(",").append(Constants.NEWLINE);
            sb.append("['" + key + "'");
            MonthlyNumber monthlyNumber = this.successAndFailedQueriesNumbers.get(key);
            sb.append("," + monthlyNumber.getNumberOfTotalQueries());
            sb.append("," + monthlyNumber.getNumberOfFailedQueries());
            sb.append("]");
        }
        return sb.toString();
    }

    public String usersAndQueriesAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append("['Month', 'Queries', 'Cumulated Users']").append(Constants.NEWLINE);
        int cumulatedUsers = 0;
        for (String key : this.usersAndQueriesNumbers.keySet()) {
            sb.append(",").append(Constants.NEWLINE);
            sb.append("['" + key + "'");
            MonthlyNumber monthlyNumber = this.usersAndQueriesNumbers.get(key);
            int numberOfUsers = monthlyNumber.getNumberOfUsers();
            cumulatedUsers = cumulatedUsers + numberOfUsers;
            sb.append("," + monthlyNumber.getNumberOfQueries());
            sb.append("," + cumulatedUsers);
            sb.append("]");
        }
        return sb.toString();
    }
}
