package klein.helper_controllers;

import java.time.LocalDateTime;

public class CustomerObject {
    private final int customerID;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private LocalDateTime dateCreated;
    private String createdBy;
    private LocalDateTime dateUpdated;
    private String updatedBy;
    private int divisionID;
    private String country;
    private String region;

    public CustomerObject(Integer customerID, String name, String address, String postalCode, String phone, LocalDateTime dateCreated, String createdBy, LocalDateTime dateUpdated, String updatedBy, int divisionID, String country, String region) {
        this.customerID = customerID;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.dateCreated = dateCreated;
        this.createdBy = createdBy;
        this.dateUpdated = dateUpdated;
        this.updatedBy = updatedBy;
        this.divisionID = divisionID;
        this.country = country;
        this.region = region;
    }

    /**
     * Basic getter function for the private attribute 'customerID'.
     * @return integer-type private attribute 'customerID'.
     * */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Basic getter function for the private attribute 'name'.
     * @return string-type private attribute 'name'.
     * */
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    /**
     * Basic getter function for the private attribute 'address'.
     * @return string-type private attribute 'address'.
     * */
    public String getAddress() {
        return address;
    }

    /**
     * Basic getter function for the private attribute 'postalCode'.
     * @return string-type private attribute 'postalCode'.
     * */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Basic getter function for the private attribute 'phone'.
     * @return string-type private attribute 'phone'.
     * */
    public String getPhone() {
        return phone;
    }

    /**
     * Basic getter function for the private attribute 'dateCreated'.
     * @return LocalDateTime-type private attribute 'dateCreated'.
     * */
    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    /**
     * Basic getter function for the private attribute 'createdBy'.
     * @return string-type private attribute 'createdBy'.
     * */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Basic getter function for the private attribute 'dateUpdated'.
     * @return LocalDateTime-type private attribute 'dateUpdate'.
     * */
    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    /**
     * Basic getter function for the private attribute 'updatedBy'.
     * @return string-type private attribute 'updatedBy'.
     * */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * Basic getter function for the private attribute 'divisionID'.
     * @return integer-type private attribute 'divisionID'.
     * */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Basic getter function for the private attribute 'country'.
     * @return string-type private attribute 'country'.
     * */
    public String getCountry() {
        return country;
    }

    /**
     * Basic getter function for the private attribute 'region'.
     * @return string-type private attribute 'region'.
     * */
    public String getRegion() {
        return region;
    }

    /**
     * Basic setter function for the private attribute 'region'.
     * @param region string to be assigned to private attribute 'region'.
     * */
    public void setRegion(String region) {
        this.region = region;
    }
}




