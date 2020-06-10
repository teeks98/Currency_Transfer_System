package com.webapps2020.jsf;

import com.webapps2020.ejb.EveryUserTransactionService;
import com.webapps2020.entity.Transactions;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 * The JSF backing bean get all transactions.
 *
 */
@Named
@ViewScoped
public class EveryUserTransactionBean implements Serializable {

    @EJB
    EveryUserTransactionService everyTransaction;

    /**
     * Call to the EJB to get all transactions.
     *
     * @return List of all user transactions.
     */
    public List<Transactions> generateAllTransactions() {
        return everyTransaction.retriveAllUserTransactions();
    }

}
