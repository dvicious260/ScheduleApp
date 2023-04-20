package bowden.scheduleapp.Helper;

public class CountryStats {
    private final String country;
    private final int customerCount;

    public CountryStats(String country, int customerCount) {
        this.country = country;
        this.customerCount = customerCount;
    }

    public String getCountry() {
        return country;
    }

    public int getCustomerCount() {
        return customerCount;
    }
}
