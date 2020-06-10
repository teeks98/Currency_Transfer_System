package com.webapps2020.ejb;

import com.webapps2020.entity.Transactions;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * EJB that gets transactions of a user.
 *
 */
@DeclareRoles({"users"})
@RolesAllowed({"users"})
@Stateless
public class UserTransactions implements UserTransactionsService {

    @PersistenceContext
    EntityManager em;

    /**
     * Method that gets all transactions of a user.
     *
     * @param fromEmail The email the transaction was from.
     * @param toEmail The email the transaction was going to.
     * @return List of all transactions.
     */
    @Override
    public List<Transactions> getTransactions(String fromEmail, String toEmail) {
        Query query = em.createNamedQuery("getAllUserTransactions", Transactions.class);
        query.setParameter("fromEmail", fromEmail);
        query.setParameter("toEmail", toEmail);
        return query.getResultList();
    }

    /**
     * Method that gets all transactions that a user has received.
     *
     * @param toEmail The email that received the transactions.
     * @return List of all received transactions.
     */
    @Override
    public List<Transactions> getReceivedTransactions(String toEmail) {
        Query query = em.createNamedQuery("newReceivedPayement", Transactions.class);
        query.setParameter("toEmail", toEmail);
        return query.getResultList();
    }

    /**
     * Method that gets all transactions that the user has sent.
     *
     * @param fromEmail Email of the transactions being sent from.
     * @return List of all transactions that the user sent.
     */
    @Override
    public List<Transactions> getSentTransactions(String fromEmail) {
        Query query = em.createNamedQuery("getAllSentPayement", Transactions.class);
        query.setParameter("fromEmail", fromEmail);
        return query.getResultList();
    }
}
