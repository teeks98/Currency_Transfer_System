package com.webapps2020.jsf;

import com.webapps2020.ejb.AllUsersService;
import com.webapps2020.entity.SystemUser;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 * The JSF backing bean get all users.
 *
 */
@Named
@ViewScoped
public class AllUsersBean implements Serializable {

    @EJB
    AllUsersService allUsers;

    /**
     * Call to the EJB to get all users.
     *
     * @return List of all users.
     */
    public List<SystemUser> allUsers() {
        return allUsers.retriveAllUsers();
    }
}
