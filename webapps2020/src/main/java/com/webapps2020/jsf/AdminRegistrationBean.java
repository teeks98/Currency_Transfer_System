package com.webapps2020.jsf;

import com.webapps2020.ejb.AdminRegistrationService;
import java.util.Objects;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * The JSF backing bean for registering a new admin.
 *
 */
@Named
@RequestScoped
public class AdminRegistrationBean {

    @EJB
    AdminRegistrationService adminReg;

    String email;
    String userpassword;

    /**
     * Call to the EJB to register an admin.
     *
     * @return Back to admin dashboard upon successful registration, error
     * otherwise.
     */
    public String register() {
        return adminReg.registerAdmin(email, userpassword);
    }

    public AdminRegistrationBean() {
    }

    public String getUsername() {
        return email;
    }

    public void setUsername(String username) {
        this.email = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.email);
        hash = 97 * hash + Objects.hashCode(this.userpassword);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AdminRegistrationBean other = (AdminRegistrationBean) obj;
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.userpassword, other.userpassword)) {
            return false;
        }
        return true;
    }
}
