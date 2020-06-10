package com.webapps2020.ejb;

import javax.ejb.Local;

@Local
public interface requestMoneyService {

    public String sendRequest(String requestFromEmail, String requestToEmail, double amount);

    public boolean isAdmin(String requestToEmail);

    public boolean emailExist(String requestToEmail);

    public boolean isPositive(double amount);
    
    public boolean isSelf(String toEmail, String fromEmail);
}
