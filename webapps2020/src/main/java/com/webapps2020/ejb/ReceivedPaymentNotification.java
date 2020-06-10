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
 * EJB for retrieving new received payments.
 *
 */
@DeclareRoles({"users"})
@RolesAllowed({"users"})
@Stateless
public class ReceivedPaymentNotification implements ReceivedPaymentNotificationService {

    @PersistenceContext
    EntityManager em;

    @EJB
    UserDetailsService userDetails;

    @Inject
    Accessor accessor;

    public ReceivedPaymentNotification() {
    }

    /**
     * Method that gets the receivedPaymentCount of a user.
     *
     * @param email Email of the count being retrieved from.
     * @return The count of receivedPaymentCount.
     */
    @Override
    public int retriveCount(String email) {
        return userDetails.getUserDetails(email).getReceivedPaymentCount();
    }

    /**
     * Method for resetting the count after viewing.
     *
     * @param email Email of the count that is being reset.
     * @return Back to user dashboard after resetting the count.
     */
    @Override
    public String resetCount(String email) {
        SystemUser user = em.find(SystemUser.class, userDetails.getUserDetails(email).getId());
        user.setReceivedPaymentCount(0);
        em.merge(user);
        return "user";
    }

    /**
     * Method that gets the list of the n most new received payments.
     *
     * @param toEmail The email of which received the transactions.
     * @return List of all the newly received transactions.
     */
    @Override
    public List<Transactions> receivedPayments(String toEmail) {
        Query query = em.createNamedQuery("newReceivedPayement", Transactions.class);
        query.setParameter("toEmail", toEmail);
        int count = userDetails.getUserDetails(toEmail).getReceivedPaymentCount();
        if (count > 0) {
            return query.setMaxResults(count).getResultList();
        }
        return Collections.emptyList();
    }
}
