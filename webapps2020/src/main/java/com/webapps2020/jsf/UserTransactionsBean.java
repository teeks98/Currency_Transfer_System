package com.webapps2020.jsf;

import com.webapps2020.ejb.UserTransactionsService;
import com.webapps2020.entity.Transactions;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 * The JSF backing bean for getting a user's transactions.
 *
 */
@Named
@RequestScoped
public class UserTransactionsBean {

    @EJB
    UserTransactionsService userTS;

    private String fromEmail;
    private double fromEmailOldBalance;
    private double fromEmailNewBalance;
    private String toEmail;
    private String status;
    private double amount;

    /**
     * Call to the EJB returning all user transactions.
     *
     * @return List of all transactions.
     */
    public List<Transactions> generateUserTS() {
        return userTS.getTransactions(fromEmail, toEmail);
    }

    /**
     * Call to the EJB returning all transactions that were received.
     *
     * @return List of transactions received.
     */
    public List<Transactions> generateReceievedTransaction() {
        return userTS.getReceivedTransactions(toEmail);
    }

    /**
     * Call to the EJB returning all transactions that the user sent.
     *
     * @return List of all transactions sent.
     */
    public List<Transactions> generateSentTransaction() {
        return userTS.getSentTransactions(fromEmail);
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.fromEmail);
        hash = 23 * hash + (int) (Double.doubleToLongBits(this.fromEmailOldBalance) ^ (Double.doubleToLongBits(this.fromEmailOldBalance) >>> 32));
        hash = 23 * hash + (int) (Double.doubleToLongBits(this.fromEmailNewBalance) ^ (Double.doubleToLongBits(this.fromEmailNewBalance) >>> 32));
        hash = 23 * hash + Objects.hashCode(this.toEmail);
        hash = 23 * hash + Objects.hashCode(this.status);
        hash = 23 * hash + (int) (Double.doubleToLongBits(this.amount) ^ (Double.doubleToLongBits(this.amount) >>> 32));
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
        final UserTransactionsBean other = (UserTransactionsBean) obj;
        if (Double.doubleToLongBits(this.fromEmailOldBalance) != Double.doubleToLongBits(other.fromEmailOldBalance)) {
            return false;
        }
        if (Double.doubleToLongBits(this.fromEmailNewBalance) != Double.doubleToLongBits(other.fromEmailNewBalance)) {
            return false;
        }
        if (Double.doubleToLongBits(this.amount) != Double.doubleToLongBits(other.amount)) {
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
        return true;
    }

    /**
     * Assigning the fromEmail and toEmail to the current user.
     */
    @PostConstruct
    public void postConstruct() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        Principal principal = request.getUserPrincipal();
        fromEmail = principal.getName();
        toEmail = principal.getName();
    }
}
