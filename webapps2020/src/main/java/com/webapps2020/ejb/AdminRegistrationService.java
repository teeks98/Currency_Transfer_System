package com.webapps2020.ejb;

import javax.ejb.Local;

@Local
public interface AdminRegistrationService {

    public String registerAdmin(String email, String userpassword);

    public boolean emailExists(String email);
}
