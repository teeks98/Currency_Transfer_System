package com.webapps2020.ejb;

import javax.ejb.Local;

@Local
public interface UserRegistrationService {

    public String registerUser(String email, String userpassword, String currency);

    public boolean emailExists(String email);
}
