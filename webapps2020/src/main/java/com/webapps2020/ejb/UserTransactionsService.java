package com.webapps2020.ejb;

import com.webapps2020.entity.Transactions;
import java.util.List;
import javax.ejb.Local;

@Local
public interface UserTransactionsService {

    public List<Transactions> getTransactions(String fromEmail, String toEmail);

    public List<Transactions> getReceivedTransactions(String toEmail);

    public List<Transactions> getSentTransactions(String fromEmail);
}
