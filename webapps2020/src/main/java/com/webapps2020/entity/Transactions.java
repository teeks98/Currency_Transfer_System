package com.webapps2020.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

//All queries used within the transactions Entity.
@Entity
@NamedQueries({
    @NamedQuery(name = "getAllUserTransactions", query = "SELECT e FROM Transactions e WHERE e.fromEmail = :fromEmail OR e.toEmail = :toEmail ORDER BY e.transactionId DESC"),
    @NamedQuery(name = "newReceivedPayement", query = "SELECT e FROM Transactions e WHERE e.toEmail = :toEmail ORDER BY e.transactionId DESC"),
    @NamedQuery(name = "getPendingTransactions", query = "SELECT e FROM Transactions e WHERE e.fromEmail = :fromEmail AND e.status = :status ORDER BY e.transactionId DESC"),
    @NamedQuery(name = "getTransactionByID", query = "SELECT e FROM Transactions e WHERE e.transactionId = :id"),
    @NamedQuery(name = "getAllExistingUserTransactions", query = "SELECT e FROM Transactions e ORDER BY e.transactionId DESC"),
    @NamedQuery(name = "getAllSentPayement", query = "SELECT e FROM Transactions e WHERE e.fromEmail = :fromEmail ORDER BY e.transactionId DESC"),})

/**
 * The Entity class for transactions.
 */
public class Transactions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transactionId; //Auto generated ID.
    private String fromEmail;   //Senders email.
    private double fromEmailOldBalance; //Senders old balance.
    private double fromEmailNewBalance; //Senders new balance.
    private String toEmail;     //Receivers email.
    private double toEmailOldBalance;   //Receivers old balance.
    private double toEmailNewBalance;   //Receivers new balance.
    private String status;  //The status of the transaction.
    private double sendersCurrencyAmount;   //The amount being sent in the senders currency.
    private double receiversCurrencyAmount; //The amount being sent in the receivers currency.
    private String sendersCurrency; //The senders currency.
    private String receiversCurrency;   //The receivers currency.

    public Transactions() {
    }

    /**
     * Constructor for transactions.
     *
     * @param fromEmail Senders email.
     * @param fromEmailOldBalance Senders old balance.
     * @param fromEmailNewBalance Senders new balance
     * @param toEmail Receivers email.
     * @param toEmailOldBalance Receivers old balance.
     * @param toEmailNewBalance Receivers new balance.
     * @param status The status of the transaction.
     * @param sendersCurrencyAmount The amount being sent in the senders
     * currency.
     * @param receiversCurrencyAmount The amount being sent in the receivers
     * currency.
     * @param sendersCurrency The senders currency.
     * @param receiversCurrency The receivers currency.
     */
    public Transactions(String fromEmail, double fromEmailOldBalance, double fromEmailNewBalance,
            String toEmail, double toEmailOldBalance, double toEmailNewBalance,
            String status, double sendersCurrencyAmount, double receiversCurrencyAmount,
            String sendersCurrency, String receiversCurrency) {
        this.fromEmail = fromEmail;
        this.fromEmailOldBalance = fromEmailOldBalance;
        this.fromEmailNewBalance = fromEmailNewBalance;
        this.toEmail = toEmail;
        this.toEmailOldBalance = toEmailOldBalance;
        this.toEmailNewBalance = toEmailNewBalance;
        this.status = status;
        this.sendersCurrencyAmount = sendersCurrencyAmount;
        this.receiversCurrencyAmount = receiversCurrencyAmount;
        this.sendersCurrency = sendersCurrency;
        this.receiversCurrency = receiversCurrency;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public double getFromEmailOldBalance() {
        return fromEmailOldBalance;
    }

    public void setFromEmailOldBalance(double fromEmailOldBalance) {
        this.fromEmailOldBalance = fromEmailOldBalance;
    }

    public double getFromEmailNewBalance() {
        return fromEmailNewBalance;
    }

    public void setFromEmailNewBalance(double fromEmailNewBalance) {
        this.fromEmailNewBalance = fromEmailNewBalance;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public double getToEmailOldBalance() {
        return toEmailOldBalance;
    }

    public void setToEmailOldBalance(double toEmailOldBalance) {
        this.toEmailOldBalance = toEmailOldBalance;
    }

    public double getToEmailNewBalance() {
        return toEmailNewBalance;
    }

    public void setToEmailNewBalance(double toEmailNewBalance) {
        this.toEmailNewBalance = toEmailNewBalance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getSendersCurrencyAmount() {
        return sendersCurrencyAmount;
    }

    public void setSendersCurrencyAmount(double sendersCurrencyAmount) {
        this.sendersCurrencyAmount = sendersCurrencyAmount;
    }

    public double getReceiversCurrencyAmount() {
        return receiversCurrencyAmount;
    }

    public String getSendersCurrency() {
        return sendersCurrency;
    }

    public void setSendersCurrency(String sendersCurrency) {
        this.sendersCurrency = sendersCurrency;
    }

    public String getReceiversCurrency() {
        return receiversCurrency;
    }

    public void setReceiversCurrency(String receiversCurrency) {
        this.receiversCurrency = receiversCurrency;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.transactionId);
        hash = 59 * hash + Objects.hashCode(this.fromEmail);
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.fromEmailOldBalance) ^ (Double.doubleToLongBits(this.fromEmailOldBalance) >>> 32));
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.fromEmailNewBalance) ^ (Double.doubleToLongBits(this.fromEmailNewBalance) >>> 32));
        hash = 59 * hash + Objects.hashCode(this.toEmail);
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.toEmailOldBalance) ^ (Double.doubleToLongBits(this.toEmailOldBalance) >>> 32));
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.toEmailNewBalance) ^ (Double.doubleToLongBits(this.toEmailNewBalance) >>> 32));
        hash = 59 * hash + Objects.hashCode(this.status);
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.sendersCurrencyAmount) ^ (Double.doubleToLongBits(this.sendersCurrencyAmount) >>> 32));
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.receiversCurrencyAmount) ^ (Double.doubleToLongBits(this.receiversCurrencyAmount) >>> 32));
        hash = 59 * hash + Objects.hashCode(this.sendersCurrency);
        hash = 59 * hash + Objects.hashCode(this.receiversCurrency);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Transactions other = (Transactions) obj;
        if (Double.doubleToLongBits(this.fromEmailOldBalance) != Double.doubleToLongBits(other.fromEmailOldBalance)) {
            return false;
        }
        if (Double.doubleToLongBits(this.fromEmailNewBalance) != Double.doubleToLongBits(other.fromEmailNewBalance)) {
            return false;
        }
        if (Double.doubleToLongBits(this.toEmailOldBalance) != Double.doubleToLongBits(other.toEmailOldBalance)) {
            return false;
        }
        if (Double.doubleToLongBits(this.toEmailNewBalance) != Double.doubleToLongBits(other.toEmailNewBalance)) {
            return false;
        }
        if (Double.doubleToLongBits(this.sendersCurrencyAmount) != Double.doubleToLongBits(other.sendersCurrencyAmount)) {
            return false;
        }
        if (Double.doubleToLongBits(this.receiversCurrencyAmount) != Double.doubleToLongBits(other.receiversCurrencyAmount)) {
            return false;
        }
        if (!Objects.equals(this.fromEmail, other.fromEmail)) {
            return false;
        }
        if (!Objects.equals(this.toEmail, other.toEmail)) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        if (!Objects.equals(this.sendersCurrency, other.sendersCurrency)) {
            return false;
        }
        if (!Objects.equals(this.receiversCurrency, other.receiversCurrency)) {
            return false;
        }
        if (!Objects.equals(this.transactionId, other.transactionId)) {
            return false;
        }
        return true;
    }

}
