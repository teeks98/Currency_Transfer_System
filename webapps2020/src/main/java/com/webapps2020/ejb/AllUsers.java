package com.webapps2020.ejb;

import com.webapps2020.entity.SystemUser;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * EJB for retrieving all existing users.
 *
 */
@DeclareRoles({"admins"})
@RolesAllowed({"admins"})
@Stateless
public class AllUsers implements AllUsersService {

    @PersistenceContext
    EntityManager em;

    public AllUsers() {
    }

    /**
     * Method that retrieves all existing users.
     *
     * @return List of all users.
     */
    @Override
    public List<SystemUser> retriveAllUsers() {
        String groupName = "users";
        Query query = em.createNamedQuery("getAllUsers", SystemUser.class);
        query.setParameter("groupname", groupName);
        return query.getResultList();
    }
}
