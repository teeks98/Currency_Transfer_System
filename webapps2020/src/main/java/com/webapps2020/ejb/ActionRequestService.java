package com.webapps2020.ejb;

import com.webapps2020.entity.Transactions;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ActionRequestService {

    public long retrivePendingCount(String email);

    public List<Transactions> pendingTransactions(String email);

    public String acceptAction(Transactions transaction);

    public String rejectAction(Transactions transaction);

    public Transactions retriveTransaction(Transactions transaction);

    public boolean sufficientBalance(String email, double amount);

    public void increaseBalance(String toEmail, double amount, String fromEmail);

    public void decreaseBalance(String fromEmail, double amount, String toEmail);

    public boolean toEmailExists(String email);

    public boolean isPositive(double amount);
}
