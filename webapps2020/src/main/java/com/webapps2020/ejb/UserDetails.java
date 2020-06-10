package com.webapps2020.ejb;

import com.webapps2020.entity.SystemUser;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * EJB for getting a users details.
 *
 */
@DeclareRoles({"users"})
@RolesAllowed({"users"})
@Stateless
public class UserDetails implements UserDetailsService {

    @PersistenceContext
    EntityManager em;

    public UserDetails() {
    }

    /**
     * Method for getting a users details.
     *
     * @param email Email of the details being retrieved.
     * @return Single result of the users details.
     */
    @Override
    public SystemUser getUserDetails(String email) {
        Query query = em.createNamedQuery("getUserDetails", SystemUser.class);
        query.setParameter("email", email);
        SystemUser su = (SystemUser) query.getSingleResult();
        return su;
    }
}
