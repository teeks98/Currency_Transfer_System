package com.webapps2020.jsf;

import com.webapps2020.ejb.SendMoneyService;
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
 * The JSF bean used to send money.
 *
 */
@Named
@ViewScoped
public class SendMoneyBean implements Serializable {

    @EJB
    SendMoneyService sendMoneyService;

    private String sendFromEmail;
    private String sendToEmail;
    private double amount;

    /**
     * Method calling the EJB to send money.
     *
     * @return Back to users dashboard or error page
     */
    public String sendMoney() {
        return sendMoneyService.send(sendFromEmail, sendToEmail, amount);
    }

    public String getSendFromEmail() {
        return sendFromEmail;
    }

    public void setSendFromEmail(String sendFromEmail) {
        this.sendFromEmail = sendFromEmail;
    }

    public String getSendToEmail() {
        return sendToEmail;
    }

    public void setSendToEmail(String sendToEmail) {
        this.sendToEmail = sendToEmail;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.sendFromEmail);
        hash = 47 * hash + Objects.hashCode(this.sendToEmail);
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.amount) ^ (Double.doubleToLongBits(this.amount) >>> 32));
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
        final SendMoneyBean other = (SendMoneyBean) obj;
        if (Double.doubleToLongBits(this.amount) != Double.doubleToLongBits(other.amount)) {
            return false;
        }
        if (!Objects.equals(this.sendFromEmail, other.sendFromEmail)) {
            return false;
        }
        if (!Objects.equals(this.sendToEmail, other.sendToEmail)) {
            return false;
        }
        return true;
    }

    /**
     * Assigning the sendFromEmail to the current user.
     */
    @PostConstruct
    public void postConstruct() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        Principal principal = request.getUserPrincipal();
        sendFromEmail = principal.getName();
    }

}
