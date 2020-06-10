package com.webapps2020.jsf;

import com.webapps2020.ejb.ReceivedPaymentNotificationService;
import com.webapps2020.entity.Transactions;
import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 * The JSF backing bean for showing the newly received payments.
 *
 */
@Named
@ViewScoped
public class ReceivedPaymentBean implements Serializable {

    @EJB
    ReceivedPaymentNotificationService notificationService;

    String email;
    int count;

    /**
     * Call to the EJB to get all new received payments.
     *
     * @return List of all new received payments.
     */
    public List<Transactions> generatePaymentsReceived() {
        return notificationService.receivedPayments(email);
    }

    /**
     * Clear the new payments notifications by resetting the count.
     */
    public void clearNotifications() {
        notificationService.resetCount(email);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCount() {
        return notificationService.retriveCount(email);
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.email);
        hash = 59 * hash + this.count;
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
        final ReceivedPaymentBean other = (ReceivedPaymentBean) obj;
        if (this.count != other.count) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }

    /**
     * Method to assign email to the current user.
     */
    @PostConstruct
    public void postConstruct() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        Principal principal = request.getUserPrincipal();
        email = principal.getName();
    }
}
