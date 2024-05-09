package com.esprit.financialhub_desktop.services;

import com.esprit.financialhub_desktop.entities.Account;
import com.esprit.financialhub_desktop.entities.ClientAccount;
import com.esprit.financialhub_desktop.tools.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountServiceImpl implements AccountServiceI {

    public List<Account> getAgentsAccounts() {
        String sql = "SELECT * FROM agent_accounts";
        try {
            PreparedStatement preparedStatement = MyConnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Account> accounts = new ArrayList<>();
            while (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getInt("id"));
                account.setAccount_number(resultSet.getString("account_number"));
                account.setBalance(resultSet.getDouble("balance"));
                account.setCurrency(resultSet.getString("currency"));
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ClientAccount> getClientAccounts() {
        String sql = "SELECT * FROM account";
        try {
            PreparedStatement preparedStatement = MyConnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ClientAccount> accounts = new ArrayList<>();
            while (resultSet.next()) {
                ClientAccount account = new ClientAccount();
                account.setAccount_number(resultSet.getString("account_number"));
                account.setBalance(resultSet.getDouble("balance"));
                account.setCurrency(resultSet.getString("currency"));
                account.setType(resultSet.getString("account_type"));
                account.setCreatedAt(String.valueOf(resultSet.getDate("created_at")));
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
