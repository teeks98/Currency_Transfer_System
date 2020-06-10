package com.webapps2020.jsf;

import com.webapps2020.ejb.UserRegistration;
import com.webapps2020.ejb.UserRegistrationService;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * The JSF backing bean for registering a user.
 *
 */
@Named("registrationBean")
@RequestScoped
public class UserRegistrationBean {

    @EJB
    UserRegistrationService usrReg;

    String email;
    String userpassword;
    String currency;

    public UserRegistrationBean() {
    }

    /**
     * Call to the EJB to register a new user.
     *
     * @return Back to index page upon successful registration otherwise error
     * page.
     */
    public String register() {
        return usrReg.registerUser(email, userpassword, currency);
    }

    public UserRegistrationService getUsrReg() {
        return usrReg;
    }

    public void setUsrReg(UserRegistration usrReg) {
        this.usrReg = usrReg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
