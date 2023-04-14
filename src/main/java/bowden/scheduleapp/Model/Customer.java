package bowden.scheduleapp.Model;

import bowden.scheduleapp.Helper.CountryHelper;
import bowden.scheduleapp.Helper.DivisionsHelper;

import java.sql.SQLException;

public class Customer {
    int id;
    String name;
    String address;
    String postalCode;
    String phone;
    int divisionID;
    String divisionName;
    public Customer() {
        // Blank constructor
    }
    public Customer(int id, String name, String address, String postalCode, String phone, int divisionID) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionID = divisionID;
        //this.divisionName = divisionName;
    }
    public Countries getCountry() throws SQLException {
        return CountryHelper.getCountryById(this.getDivision().getCountryID());
    }
    public FirstLevelDivisions getDivision() throws SQLException {
        return DivisionsHelper.getDivisionById(this.divisionID);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }
}
