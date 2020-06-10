package com.webapps2020.jsf;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * The JSF backing bean to logout a user.
 *
 */
@Named
@RequestScoped
public class LoginBean implements Serializable {

    /**
     * Method to log a user out.
     *
     * @return Back to index page upon successful logout.
     */
    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.logout();
            context.getExternalContext().invalidateSession();
            context.addMessage(null, new FacesMessage("Logged out"));
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage("Logout failed."));
        }
        return "index";
    }
}
