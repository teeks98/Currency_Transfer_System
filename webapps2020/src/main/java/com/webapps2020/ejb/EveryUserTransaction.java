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
 * EJB that retrieves all users transactions.
 * 
 */
@DeclareRoles({"admins"}) 
@RolesAllowed({"admins"})
@Stateless
public class EveryUserTransaction implements EveryUserTransactionService {

    @PersistenceContext
    EntityManager em;

    public EveryUserTransaction() {
    }

    /**
     * Method that retrieves all users transactions.
     * @return List of all transactions.
     */
    @Override
    public List<Transactions> retriveAllUserTransactions() {
        Query query = em.createNamedQuery("getAllExistingUserTransactions", Transactions.class);
        return query.getResultList();
    }

}
