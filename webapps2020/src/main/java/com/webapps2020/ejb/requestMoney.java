package com.webapps2020.ejb;

import com.webapps2020.entity.Transactions;
import com.webapps2020.rsConverter.Accessor;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * EJB for requesting money.
 *
 */
@DeclareRoles({"users"})
@RolesAllowed({"users"})
@Stateless
public class requestMoney implements requestMoneyService {

    @PersistenceContext
    EntityManager em;

    @EJB
    UserDetailsService userDetails;

    @Inject
    Accessor accessor;

    public requestMoney() {
    }

    /**
     * Method for sending a transaction request.
     *
     * @param requestFromEmail Email the request is being sent from.
     * @param requestToEmail Email the request is being sent to.
     * @param amount Amount being requested.
     * @return Back to user dashboard if send request was successful, error page
     * otherwise.
     */
    @Override
    public String sendRequest(String requestFromEmail, String requestToEmail, double amount) {
        //Checks performed to see if request can be sent.
        if (emailExist(requestFromEmail) && isPositive(amount) && !isAdmin(requestFromEmail) && !isSelf(requestToEmail, requestFromEmail)) {
            try {
                Transactions transaction;
                String fromEmailCurreny = userDetails.getUserDetails(requestFromEmail).getCurrency();
                String toEmailCurrency = userDetails.getUserDetails(requestToEmail).getCurrency();
                double requestFromOldBalance = userDetails.getUserDetails(requestFromEmail).getBalance();
                //As transaction is pending, old and new balance is the same, needed for transaction entity
                double requestFromNewBalance = userDetails.getUserDetails(requestFromEmail).getBalance();
                double requestToOldBalance = userDetails.getUserDetails(requestToEmail).getBalance();
                //As transaction is pending, old and new balance is the same, needed for transaction entity
                double requestToNewBalance = userDetails.getUserDetails(requestToEmail).getBalance();
                String status = "PENDING";//down
                double receiverCurrencyAmount = accessor.newAmount(toEmailCurrency, fromEmailCurreny, String.valueOf(amount));
                transaction = new Transactions(requestFromEmail, requestFromOldBalance, requestFromNewBalance, requestToEmail, requestToOldBalance, requestToNewBalance,
                        status, receiverCurrencyAmount, amount, fromEmailCurreny, toEmailCurrency);
                //Add the new transaction into the transaction entity.
                em.persist(transaction);
                return "user";
            } catch (Exception e) {
                System.out.println("ERROR" + e.getMessage());
            }
        }
        return "cannotRequest";
    }

    /**
     * Method to check if request is being sent to an admin.
     *
     * @param requestFromEmail Email the request is being sent to.
     * @return True if it is an admin, False otherwise.
     */
    @Override
    public boolean isAdmin(String requestFromEmail) {
        String admin = "admins";
        Query query = em.createNamedQuery("isAdmin", Long.class);
        query.setParameter("email", requestFromEmail);
        query.setParameter("groupName", admin);
        Long count = (Long) query.getSingleResult();
        return count.equals(1L);
    }

    /**
     * Method to check to see if the email requesting to exists.
     *
     * @param requestToEmail The email the request is being sent to.
     * @return True if the email exists, False otherwise.
     */
    @Override
    public boolean emailExist(String requestToEmail) {
        Query query = em.createNamedQuery("doesEmailExist", Long.class);
        query.setParameter("email", requestToEmail);
        Long count = (Long) query.getSingleResult();
        return count.equals(1L);
    }

    /**
     * Method that checks whether the amount being requested is a positive
     * amount.
     *
     * @param amount Amount that is being requested.
     * @return True if amount is greater than 0, False otherwise.
     */
    @Override
    public boolean isPositive(double amount) {
        return amount > 0;
    }

    /**
     * Method to check if the person the request is being sent to is themselves.
     *
     * @param toEmail The email the request is being sent to.
     * @param fromEmail The email the request is being sent from.
     * @return True if they are requesting to themselves, False otherwise.
     */
    @Override
    public boolean isSelf(String toEmail, String fromEmail) {
        return toEmail.equals(fromEmail);
    }
}
