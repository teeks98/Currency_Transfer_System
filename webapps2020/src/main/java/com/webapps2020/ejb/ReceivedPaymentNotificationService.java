package com.webapps2020.ejb;

import com.webapps2020.entity.Transactions;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ReceivedPaymentNotificationService {

    public int retriveCount(String email);

    public String resetCount(String email);

    public List<Transactions> receivedPayments(String toEmail);
}
