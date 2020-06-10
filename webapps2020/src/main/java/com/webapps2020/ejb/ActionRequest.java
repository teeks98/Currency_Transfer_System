package com.webapps2020.ejb;

import com.webapps2020.entity.SystemUser;
import com.webapps2020.entity.Transactions;
import com.webapps2020.rsConverter.Accessor;
import java.util.Collections;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * This is the EJB for dealing with how a user wants to handle a request
 * (Accept/Reject).
 *
 */
@DeclareRoles({"users"})
@RolesAllowed({"users"})
@Stateless
public class ActionRequest implements ActionRequestService {

    @PersistenceContext
    EntityManager em;

    @EJB
    UserDetailsService userDetails;

    @Inject
    Accessor accessor;

    public ActionRequest() {
    }

    /**
     * Method that returns a count of the amount of Pending transactions that
     * the user needs to respond to.
     *
     * @param email Email of the count being retrieved from.
     * @return Number of pending transactions.
     */
    @Override
    public long retrivePendingCount(String email) {
        String status = "PENDING";
        Query query = em.createNamedQuery("getPendingTransactions", Transactions.class);
        query.setParameter("fromEmail", email);
        query.setParameter("status", status);
        return query.getResultList().size();
    }

    /**
     * Method that gets all pending transactions that the user needs to respond
     * to.
     *
     * @param email Email of the pending transactions.
     * @return All pending transactions that the user needs to respond to, empty
     * list if no pending transactions.
     */
    @Override
    public List<Transactions> pendingTransactions(String email) {
        String status = "PENDING";
        Query query = em.createNamedQuery("getPendingTransactions", Transactions.class);
        query.setParameter("fromEmail", email);
        query.setParameter("status", status);
        long count = retrivePendingCount(email);
        if (count > 0) {
            return query.getResultList();
        }
        return Collections.emptyList();
    }

    /**
     * Method for when the user accepts the requested transaction.
     *
     * @param transaction The transaction that is being accepted.
     * @return Back to actionRequest page when successful, error page if unable
     * to proceed with transaction.
     */
    @Override
    public String acceptAction(Transactions transaction) {
        //Checks for whether the transaction can take place.
        if (sufficientBalance(transaction.getFromEmail(), transaction.getSendersCurrencyAmount()) && toEmailExists(transaction.getToEmail()) && isPositive(transaction.getSendersCurrencyAmount())) {
            try {
                Transactions replaceTransaction = em.find(Transactions.class, retriveTransaction(transaction).getTransactionId());
                increaseBalance(transaction.getToEmail(), transaction.getReceiversCurrencyAmount(), transaction.getFromEmail());
                decreaseBalance(transaction.getFromEmail(), transaction.getSendersCurrencyAmount(), transaction.getToEmail());
                double fromEmailNewBalance = userDetails.getUserDetails(transaction.getFromEmail()).getBalance();
                double toEmailNewBalance = userDetails.getUserDetails(transaction.getToEmail()).getBalance();
                replaceTransaction.setStatus("COMPLETE");
                replaceTransaction.setFromEmailNewBalance(fromEmailNewBalance);
                replaceTransaction.setToEmailNewBalance(toEmailNewBalance);
                em.merge(replaceTransaction);
                return "actionRequest";
            } catch (Exception e) {
                System.out.println("ERROR: " + e);
            }
        }

        return "cannotRequest";
    }

    /**
     * Method for rejecting request.
     *
     * @param transaction The transaction that is being rejected.
     * @return Back to actionRequest page when successful
     */
    @Override
    public String rejectAction(Transactions transaction) {
        em.remove(retriveTransaction(transaction));
        return "actionRequest";
    }

    /**
     * Method for identifying a transaction by ID.
     *
     * @param transaction The transactions.
     * @return The single transaction.
     */
    @Override
    public Transactions retriveTransaction(Transactions transaction) {
        Query query = em.createNamedQuery("getTransactionByID", Transactions.class);
        query.setParameter("id", transaction.getTransactionId());
        return (Transactions) query.getSingleResult();
    }

    /**
     * Method to check whether the user has sufficient balance to carry out the
     * transaction.
     *
     * @param email Users email.
     * @param amount Amount to be sent.
     * @return True or False if their balance is greater than or equal the
     * amount for the transaction.
     */
    @Override
    public boolean sufficientBalance(String email, double amount) {
        double currentBal = userDetails.getUserDetails(email).getBalance();
        return currentBal - amount >= 0;
    }

    /**
     * Method that increases the balance of the user who made the request.
     *
     * @param toEmail The email the money is being sent to.
     * @param amount Amount being sent.
     * @param fromEmail The email the money is being sent from.
     */
    @Override
    public void increaseBalance(String toEmail, double amount, String fromEmail) {
        double currentBalance = userDetails.getUserDetails(toEmail).getBalance();
        double newBalance = currentBalance + amount;
        int count = userDetails.getUserDetails(toEmail).getReceivedPaymentCount();
        int newCount = count + 1;
        SystemUser toUser = em.find(SystemUser.class, userDetails.getUserDetails(toEmail).getId());
        toUser.setBalance(newBalance);
        toUser.setReceivedPaymentCount(newCount);
        em.merge(toUser);
    }

    /**
     * Method that decreases the balance of the user sending the money by their
     * currency amount.
     *
     * @param fromEmail The email the money is being sent from.
     * @param amount The amount being sent.
     * @param toEmail The email the money is being sent to.
     */
    @Override
    public void decreaseBalance(String fromEmail, double amount, String toEmail) {
        double currentBalance = userDetails.getUserDetails(fromEmail).getBalance();
        double newBalance = currentBalance - amount;
        SystemUser fromUser = em.find(SystemUser.class, userDetails.getUserDetails(fromEmail).getId());
        fromUser.setBalance(newBalance);
        em.merge(fromUser);
    }

    /**
     * Method that checks whether the email exists or not.
     *
     * @param email The email being checked.
     * @return True if number is 1 False if 0 (can only be one or 0).
     */
    @Override
    public boolean toEmailExists(String email) {
        Query query = em.createNamedQuery("doesEmailExist", Long.class);
        query.setParameter("email", email);
        Long count = (Long) query.getSingleResult();
        return count.equals(1L);
    }

    /**
     * Method that checks whether the amount being send is a positive amount.
     *
     * @param amount Amount being sent.
     * @return True if positive and False if negative.
     */
    @Override
    public boolean isPositive(double amount) {
        return amount > 0;
    }
}
