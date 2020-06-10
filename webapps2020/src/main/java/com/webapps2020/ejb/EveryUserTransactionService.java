package com.webapps2020.ejb;

import com.webapps2020.entity.Transactions;
import java.util.List;
import javax.ejb.Local;

@Local
public interface EveryUserTransactionService {

    List<Transactions> retriveAllUserTransactions();
}
