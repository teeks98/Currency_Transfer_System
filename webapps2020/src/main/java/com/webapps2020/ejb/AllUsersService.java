package com.webapps2020.ejb;

import com.webapps2020.entity.SystemUser;
import java.util.List;
import javax.ejb.Local;

@Local
public interface AllUsersService {

     public List<SystemUser> retriveAllUsers();
}
