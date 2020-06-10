package com.webapps2020.ejb;

import javax.ejb.Local;

@Local
public interface SendMoneyService {

    public boolean sufficientBalance(String email, double amount);

    public boolean isAdmin(String email);

    public boolean toEmailExists(String email);

    public String send(String fromEmail, String toEmail, double amount);

    public void increaseBalance(String toEmail, double amount, String fromEmail);

    public void decreaseBalance(String fromEmail, double amount);

    public boolean isPositive(double amount);
    
    public boolean isSelf(String toEmail, String fromEmail);
}
