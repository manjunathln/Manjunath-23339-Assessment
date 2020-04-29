package com.tel.crudoperations.models;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer {

    @Id
    private  int customerId;
    private String customerFName;
    private String customerLName;
    private boolean customerStatus;

    public Customer(int customerId, String customerFName, String customerLName,boolean customerStatus) {
        this.customerId = customerId;
        this.customerFName = customerFName;
        this.customerLName = customerLName;
        this.customerStatus=customerStatus;
    }

    public Customer() {
        super();
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerFName() {
        return customerFName;
    }

    public void setCustomerFName(String customerFName) {
        this.customerFName = customerFName;
    }

    public String getCustomerLName() {
        return customerLName;
    }

    public void setCustomerLName(String customerLName) {
        this.customerLName = customerLName;
    }

    public boolean isCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(boolean customerStatus) {
        this.customerStatus = customerStatus;
    }
}
