package com.esprit.financialhub_desktop.services;

import com.esprit.financialhub_desktop.entities.Account;
import com.esprit.financialhub_desktop.entities.ClientAccount;

import java.util.List;

public interface AccountServiceI {
    public List<Account> getAgentsAccounts();
    List<ClientAccount> getClientAccounts();
}
