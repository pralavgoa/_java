package ucrex.usage.statistics.model;

import java.util.Map;
import java.util.Set;

public class MonthlyNumbers {
    private final Map<String, MonthlyNumber> monthlyNumbers = new java.util.TreeMap<String, MonthlyNumber>();

    public MonthlyNumber get(String key) {
        return this.monthlyNumbers.get(key);
    }

    public void put(String key, MonthlyNumber value) {
        this.monthlyNumbers.put(key, value);
    }

    public Set<String> keySet() {
        return this.monthlyNumbers.keySet();
    }
}
