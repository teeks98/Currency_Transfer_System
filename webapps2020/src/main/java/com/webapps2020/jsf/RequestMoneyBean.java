package com.webapps2020.jsf;

import com.webapps2020.ejb.requestMoneyService;
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
 * The JSF backing bean for making a request.
 *
 */
@Named
@ViewScoped
public class RequestMoneyBean implements Serializable {

    @EJB
    requestMoneyService requestMoney;

    private String requestFromEmail;
    private String requestToEmail;
    private double amount;

    /**
     * Call to the EJB to send request.
     *
     * @return Back to user dashboard upon successful request, error page
     * otherwise.
     */
    public String sendTransactionRequest() {
        return requestMoney.sendRequest(requestToEmail, requestFromEmail, amount);
    }

    public String getRequestFromEmail() {
        return requestFromEmail;
    }

    public void setRequestFromEmail(String requestFromEmail) {
        this.requestFromEmail = requestFromEmail;
    }

    public String getRequestToEmail() {
        return requestToEmail;
    }

    public void setRequestToEmail(String requestToEmail) {
        this.requestToEmail = requestToEmail;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.requestMoney);
        hash = 59 * hash + Objects.hashCode(this.requestFromEmail);
        hash = 59 * hash + Objects.hashCode(this.requestToEmail);
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.amount) ^ (Double.doubleToLongBits(this.amount) >>> 32));
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
        final RequestMoneyBean other = (RequestMoneyBean) obj;
        if (Double.doubleToLongBits(this.amount) != Double.doubleToLongBits(other.amount)) {
            return false;
        }
        if (!Objects.equals(this.requestFromEmail, other.requestFromEmail)) {
            return false;
        }
        if (!Objects.equals(this.requestToEmail, other.requestToEmail)) {
            return false;
        }
        if (!Objects.equals(this.requestMoney, other.requestMoney)) {
            return false;
        }
        return true;
    }

    /**
     * Assigning the requestFromEmail to the current user.
     */
    @PostConstruct
    public void postConstruct() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        Principal principal = request.getUserPrincipal();
        requestFromEmail = principal.getName();
    }
}
