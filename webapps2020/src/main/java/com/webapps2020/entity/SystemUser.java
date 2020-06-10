package com.webapps2020.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

//All queries used  within SystemUser.
@Entity
@NamedQueries({
    @NamedQuery(name = "doesEmailExist", query = "SELECT count(e) from SystemUser e where e.email = :email"),
    @NamedQuery(name = "getUserDetails", query = "SELECT e from SystemUser e where e.email = :email"),
    @NamedQuery(name = "getAllUsers", query = "SELECT e FROM SystemUser e, SystemUserGroup g WHERE g.groupname = :groupname AND e.email = g.email"),})

/**
 * The Entity class for System Users.
 */
public class SystemUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;    //Auto generated ID.

    private String email;   //Email of the user.
    private String userPassword;    //Password of the user.
    @Column(nullable = true)
    private double balance;     //Balance of the user.
    @Column(nullable = true)
    private int receivedPaymentCount;   //New Received payment count of the user.
    @Column(nullable = true)
    private String currency;    //The currency used for the user.

    public SystemUser() {
    }

    /**
     * Constructor for users.
     *
     * @param email Email of the user.
     * @param paswdToStoreInDB Password of the user.
     * @param currency The currency used for the user.
     * @param balance The balance of the user.
     */
    public SystemUser(String email, String paswdToStoreInDB, String currency, double balance) {
        this.email = email;
        this.userPassword = paswdToStoreInDB;
        this.currency = currency;
        this.balance = balance;
    }

    /**
     * Constructor for admins.
     *
     * @param email Email/username of the admin.
     * @param userPassword Password of the admin.
     */
    public SystemUser(String email, String userPassword) {
        this.email = email;
        this.userPassword = userPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return userPassword;
    }

    public void setPassword(String password) {
        this.userPassword = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getReceivedPaymentCount() {
        return receivedPaymentCount;
    }

    public void setReceivedPaymentCount(int receivedPaymentCount) {
        this.receivedPaymentCount = receivedPaymentCount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SystemUser)) {
            return false;
        }
        SystemUser other = (SystemUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.webapps2020.entity.SystemUser[ id=" + id + " ]";
    }

}
