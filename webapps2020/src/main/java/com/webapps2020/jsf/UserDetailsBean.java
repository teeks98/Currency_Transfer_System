package com.webapps2020.jsf;

import com.webapps2020.ejb.UserDetailsService;
import com.webapps2020.entity.SystemUser;
import java.io.Serializable;
import java.security.Principal;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 * The JSF backing bean for getting user details.
 *
 */
@Named
@ViewScoped
public class UserDetailsBean implements Serializable {

    @EJB
    UserDetailsService userDetails;

    private String email;
    private double balance;
    private String currency;
    private String sign;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Method to get the sign of the currency that is being used.
     *
     * @return Currency sign.
     */
    public String getSign() {
        switch (currency) {
            case "GBP":
                return "£";
            case "EUR":
                return "€";
            case "USD":
                return "$";
            default:
                break;
        }
        return "";
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.userDetails);
        hash = 47 * hash + Objects.hashCode(this.email);
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.balance) ^ (Double.doubleToLongBits(this.balance) >>> 32));
        hash = 47 * hash + Objects.hashCode(this.currency);
        hash = 47 * hash + Objects.hashCode(this.sign);
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
        final UserDetailsBean other = (UserDetailsBean) obj;
        if (Double.doubleToLongBits(this.balance) != Double.doubleToLongBits(other.balance)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.currency, other.currency)) {
            return false;
        }
        if (!Objects.equals(this.sign, other.sign)) {
            return false;
        }
        if (!Objects.equals(this.userDetails, other.userDetails)) {
            return false;
        }
        return true;
    }

    /**
     * Method to get all the details of the current user.
     */
    @PostConstruct
    public void postConstruct() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        Principal principal = request.getUserPrincipal();
        String loginEmail = principal.getName();
        SystemUser su = userDetails.getUserDetails(loginEmail);
        email = su.getEmail();
        balance = su.getBalance();
        currency = su.getCurrency();
    }
}
