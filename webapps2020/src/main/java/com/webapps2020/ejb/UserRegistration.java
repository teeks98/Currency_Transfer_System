package com.webapps2020.ejb;

import com.webapps2020.entity.SystemUser;
import com.webapps2020.entity.SystemUserGroup;
import com.webapps2020.rsConverter.Accessor;
import com.webapps2020.rsConverter.Conversion;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * EJB for registering a new user.
 *
 */
@Stateless
public class UserRegistration implements UserRegistrationService {

    @PersistenceContext
    EntityManager em;

    @Inject
    Accessor accessor;

    public UserRegistration() {
    }

    /**
     * Method that registers a new user.
     *
     * @param email Email of the new user.
     * @param userpassword Password of the user.
     * @param currency Currency of the user.
     * @return Back to index upon successful registration, error page if cannot
     * register a user.
     */
    @Override
    public String registerUser(String email, String userpassword, String currency) {
        //Check to see if the email has been taken.
        if (!emailExists(email)) {
            return "error";
        }
        try {
            SystemUser sys_user;
            SystemUserGroup sys_user_group;
            String startingBalance = "1000"; // This is the default balance GBP
            //Encryption algorithm used.
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwd = userpassword;
            md.update(passwd.getBytes("UTF-8"));
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
            }
            String paswdToStoreInDB = sb.toString();
            double newBalance = accessor.newAmount("GBP", currency, startingBalance);
            sys_user = new SystemUser(email, paswdToStoreInDB, currency, newBalance);
            sys_user_group = new SystemUserGroup(email, "users");
            //Adding the new user into both SystemUser and SystemUserGroup entity
            em.persist(sys_user);
            em.persist(sys_user_group);

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(UserRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "index";
    }

    /**
     * Method to check whether the email entered has been taken or not.
     *
     * @param email Email that is being checked.
     * @return True is the email has been taken False if not.
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
