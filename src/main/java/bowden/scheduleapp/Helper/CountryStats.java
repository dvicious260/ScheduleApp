package bowden.scheduleapp.Helper;

public class CountryStats {
    private String country;
    private int customerCount;

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
