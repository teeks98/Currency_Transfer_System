package com.webapps2020.ejb;

import com.webapps2020.entity.SystemUser;
import com.webapps2020.entity.SystemUserGroup;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * The EJB for registering a new admin.
 *
 */
@DeclareRoles({"admins"})
@RolesAllowed({"admins"})
@Stateless
public class AdminRegistration implements AdminRegistrationService {

    @PersistenceContext
    EntityManager em;

    public AdminRegistration() {
    }

    /**
     * Method for registering a new admin.
     *
     * @param email Email/Username of the admin.
     * @param userpassword Password of the admin.
     * @return Back to the admin dashboard upon successful registration, error
     * page if unable to register the admin.
     */
    @Override
    public String registerAdmin(String email, String userpassword) {
        //Check to see if admin usernae has been taken.
        if (!emailExists(email)) {
            return "adminError";
        }
        try {
            SystemUser sys_user;
            SystemUserGroup sys_user_group;
            //Encryption algorithm used for the password.
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwd = userpassword;
            md.update(passwd.getBytes("UTF-8"));
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
            }
            String paswdToStoreInDB = sb.toString();
            sys_user = new SystemUser(email, paswdToStoreInDB);
            sys_user_group = new SystemUserGroup(email, "admins");
            //Stored the new admin in both SystemUser and SystemUserGroup.
            em.persist(sys_user);
            em.persist(sys_user_group);

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(UserRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "admin";
    }

    /**
     * Method to check whether the admin name has been taken.
     *
     * @param email Email that is being checked.
     * @return True if the name exist and False otherwise.
     */
    @Override
    public boolean emailExists(String email) {
        // you will always get a single result
        Query query = em.createNamedQuery("doesEmailExist", Long.class);
        query.setParameter("email", email);
        Long count = (Long) query.getSingleResult();
        return count.equals(0L);
    }
}
