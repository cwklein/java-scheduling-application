package klein.helper_controllers;

import java.sql.Timestamp;

public class CustomerObj {
    private final int customerID;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhone;
    private Timestamp customerDateCreated;
    private String customerCreatedBy;
    private Timestamp customerDateUpdated;
    private String customerUpdatedBy ;
    private int customerDivisionID;

    public CustomerObj(int customerID, String customerName, String customerAddress, String customerPostalCode, String customerPhone, Timestamp customerDateCreated, String customerCreatedBy, Timestamp customerDateUpdated, String customerUpdatedBy, int customerDivisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhone = customerPhone;
        this.customerDateCreated = customerDateCreated;
        this.customerCreatedBy = customerCreatedBy;
        this.customerDateUpdated = customerDateUpdated;
        this.customerUpdatedBy = customerUpdatedBy;
        this.customerDivisionID = customerDivisionID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public Timestamp getCustomerDateCreated() {
        return customerDateCreated;
    }

    public void setCustomerDateCreated(Timestamp customerDateCreated) {
        this.customerDateCreated = customerDateCreated;
    }

    public String getCustomerCreatedBy() {
        return customerCreatedBy;
    }

    public void setCustomerCreatedBy(String customerCreatedBy) {
        this.customerCreatedBy = customerCreatedBy;
    }

    public Timestamp getCustomerDateUpdated() {
        return customerDateUpdated;
    }

    public void setCustomerDateUpdated(Timestamp customerDateUpdated) {
        this.customerDateUpdated = customerDateUpdated;
    }

    public String getCustomerUpdatedBy() {
        return customerUpdatedBy;
    }

    public void setCustomerUpdatedBy(String customerUpdatedBy) {
        this.customerUpdatedBy = customerUpdatedBy;
    }

    public int getCustomerDivisionID() {
        return customerDivisionID;
    }

    public void setCustomerDivisionID(int customerDivisionID) {
        this.customerDivisionID = customerDivisionID;
    }
}




