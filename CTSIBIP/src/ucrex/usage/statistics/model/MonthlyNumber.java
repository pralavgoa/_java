package ucrex.usage.statistics.model;

public class MonthlyNumber {

    private int numberOfQueries;
    private int numberOfUsers;
    private int numberOfTotalQueries;
    private int numberOfFailedQueries;

    public int getNumberOfQueries() {
        return this.numberOfQueries;
    }

    public void setNumberOfQueries(int numberOfQueries) {
        this.numberOfQueries = numberOfQueries;
    }

    public int getNumberOfUsers() {
        return this.numberOfUsers;
    }

    public void setNumberOfUsers(int numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }

    public int getNumberOfTotalQueries() {
        return this.numberOfTotalQueries;
    }

    public void setNumberOfTotalQueries(int numberOfSuccessfulQueries) {
        this.numberOfTotalQueries = numberOfSuccessfulQueries;
    }

    public int getNumberOfFailedQueries() {
        return this.numberOfFailedQueries;
    }

    public void setNumberOfFailedQueries(int numberOfFailedQueries) {
        this.numberOfFailedQueries = numberOfFailedQueries;
    }

}
