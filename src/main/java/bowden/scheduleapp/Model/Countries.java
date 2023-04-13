package bowden.scheduleapp.Model;

public class Countries {
    int countryID;
    int countryName;

    public Countries(int countryID, int countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public int getCountryName() {
        return countryName;
    }

    public void setCountryName(int countryName) {
        this.countryName = countryName;
    }
}
