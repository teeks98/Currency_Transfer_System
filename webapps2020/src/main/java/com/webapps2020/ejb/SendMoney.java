package com.webapps2020.ejb;

import com.webapps2020.entity.SystemUser;
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
 * EJB for sending money.
 *
 */
@DeclareRoles({"users"})
@RolesAllowed({"users"})
@Stateless
public class SendMoney implements SendMoneyService {

    @PersistenceContext
    EntityManager em;

    @EJB
    UserDetailsService userDetails;

    @Inject
    Accessor accessor;

    public SendMoney() {
    }

    /**
     * Check to see if user is sending to self.
     *
     * @param toEmail Email money is being sent to.
     * @param fromEmail Email money is being sent from.
     * @return True if senders email is equal to receivers email, False
     * otherwise.
     */
    @Override
    public boolean isSelf(String toEmail, String fromEmail) {
        return toEmail.equals(fromEmail);
    }

    /**
     * Method that checks whether the sender has a sufficient balance to send
     * money.
     *
     * @param email Email of the balance is being checked.
     * @param amount Amount wanting to be sent.
     * @return True if balance is greater than or equal to amount, False
     * otherwise.
     */
    @Override
    public boolean sufficientBalance(String email, double amount) {
        double currentBal = userDetails.getUserDetails(email).getBalance();
        return currentBal - amount >= 0;
    }

    /**
     * Method that checks whether you are trying to send money to an admin.
     *
     * @param email Email that is being checked.
     * @return True if it is an admin, False otherwise.
     */
    @Override
    public boolean isAdmin(String email) {
        String admin = "admins";
        Query query = em.createNamedQuery("isAdmin", Long.class);
        query.setParameter("email", email);
        query.setParameter("groupName", admin);
        Long count = (Long) query.getSingleResult();
        return count.equals(1L);
    }

    /**
     * Method that checks if the email you are sending to exists.
     *
     * @param email Email that is being checked.
     * @return True is it does exist, False otherwise.
     */
    @Override
    public boolean toEmailExists(String email) {
        Query query = em.createNamedQuery("doesEmailExist", Long.class);
        query.setParameter("email", email);
        Long count = (Long) query.getSingleResult();
        return count.equals(1L);
    }

    /**
     * Method that checks whether the amount being sent is positive.
     *
     * @param amount Amount wanting to be sent.
     * @return True if greater than 0 False otherwise.
     */
    @Override
    public boolean isPositive(double amount) {
        return amount > 0;
    }

    /**
     * Method for sending money to another user.
     *
     * @param fromEmail Email being sent from.
     * @param toEmail Email being sent to.
     * @param amount Amount being sent.
     * @return Back to the users dashboard if send is successful, error page if
     * send cannot be carried out.
     */
    @Override
    public String send(String fromEmail, String toEmail, double amount) {
        //Checks being carried out.
        if (sufficientBalance(fromEmail, amount) && toEmailExists(toEmail) && isPositive(amount) && !isAdmin(toEmail) && !isSelf(toEmail, fromEmail)) {
            try {
                Transactions transaction;
                //Getting the old balances of both the sender and receiver
                double fromEmailOldBalance = userDetails.getUserDetails(fromEmail).getBalance();
                double toEmailOldBalance = userDetails.getUserDetails(toEmail).getBalance();
                //Updating the senders and receivers balance.
                increaseBalance(toEmail, amount, fromEmail);
                decreaseBalance(fromEmail, amount);
                double fromEmailNewBalance = userDetails.getUserDetails(fromEmail).getBalance();
                double toEmailNewBalance = userDetails.getUserDetails(toEmail).getBalance();
                String status = "COMPLETE";
                String toEmailCurrency = userDetails.getUserDetails(toEmail).getCurrency();
                String fromEmailCurrency = userDetails.getUserDetails(fromEmail).getCurrency();
                double receiversCurrencyAmount = accessor.newAmount(fromEmailCurrency, toEmailCurrency, String.valueOf(amount));
                //Recording the transaction in the transactions entity.
                transaction = new Transactions(fromEmail, fromEmailOldBalance, fromEmailNewBalance, toEmail,
                        toEmailOldBalance, toEmailNewBalance, status, amount,
                        receiversCurrencyAmount, fromEmailCurrency, toEmailCurrency);
                em.persist(transaction);
                return "user";
            } catch (Exception e) {
                System.out.println("ERROR" + e.getMessage());
            }
        }
        return "cannotSend";
    }

    /**
     * Method for increasing the the receivers balance by their currency amount.
     *
     * @param toEmail Receivers email.
     * @param amount Amount being sent in the senders currency.
     * @param fromEmail Senders email.
     */
    @Override
    public void increaseBalance(String toEmail, double amount, String fromEmail) {
        String toEmailCurrency = userDetails.getUserDetails(toEmail).getCurrency();
        String fromEmailCurrency = userDetails.getUserDetails(fromEmail).getCurrency();
        double currentBalance = userDetails.getUserDetails(toEmail).getBalance();
        double newBalance = currentBalance + accessor.newAmount(fromEmailCurrency, toEmailCurrency, String.valueOf(amount));
        int count = userDetails.getUserDetails(toEmail).getReceivedPaymentCount();
        int newCount = count + 1;
        SystemUser toUser = em.find(SystemUser.class, userDetails.getUserDetails(toEmail).getId());

        toUser.setBalance(newBalance);
        toUser.setReceivedPaymentCount(newCount);
        em.merge(toUser);
    }

    /**
     * Method for decreasing the balance of the sender.
     *
     * @param fromEmail The senders email.
     * @param amount Amount being sent.
     */
    @Override
    public void decreaseBalance(String fromEmail, double amount) {
        double currentBalance = userDetails.getUserDetails(fromEmail).getBalance();
        double newBalance = currentBalance - amount;
        SystemUser fromUser = em.find(SystemUser.class, userDetails.getUserDetails(fromEmail).getId());
        fromUser.setBalance(newBalance);
        em.merge(fromUser);
    }
}
