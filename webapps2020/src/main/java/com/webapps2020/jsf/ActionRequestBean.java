package com.webapps2020.jsf;

import com.webapps2020.ejb.ActionRequestService;
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
 * The JSF backing bean for responding to a request.
 *
 */
@Named
@ViewScoped
public class ActionRequestBean implements Serializable {

    @EJB
    ActionRequestService actionRequest;

    private String email;
    private long pendingCount;

    /**
     * Call to the EJB to get all pending payments.
     *
     * @return List of all pending transactions for a user.
     */
    public List<Transactions> generatePendingPayments() {
        return actionRequest.pendingTransactions(email);
    }

    /**
     * Call to the EJB to reject request.
     *
     * @param transaction The transaction to be rejected.
     * @return Back to the request page.
     */
    public String removeTransaction(Transactions transaction) {
        return actionRequest.rejectAction(transaction);
    }

    /**
     * Call to the EJB to accept the request.
     *
     * @param transaction The transaction to be accepted.
     * @return Back to the request page upon successful registration, error
     * otherwise.
     */
    public String acceptRequest(Transactions transaction) {
        return actionRequest.acceptAction(transaction);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPendingCount() {
        return actionRequest.retrivePendingCount(email);
    }

    public void setPendingCount(long pendingCount) {
        this.pendingCount = pendingCount;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.actionRequest);
        hash = 37 * hash + Objects.hashCode(this.email);
        hash = 37 * hash + (int) (this.pendingCount ^ (this.pendingCount >>> 32));
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
        final ActionRequestBean other = (ActionRequestBean) obj;
        if (this.pendingCount != other.pendingCount) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.actionRequest, other.actionRequest)) {
            return false;
        }
        return true;
    }

    /**
     * Assigning email to the user currently logged in.
     */
    @PostConstruct
    public void postConstruct() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        Principal principal = request.getUserPrincipal();
        email = principal.getName();
    }
}
