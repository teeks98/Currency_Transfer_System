package com.webapps2020.ejb;

import com.webapps2020.entity.SystemUser;
import javax.ejb.Local;

@Local
public interface UserDetailsService {

    public SystemUser getUserDetails(String email);

}
