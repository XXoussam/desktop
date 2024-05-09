package com.esprit.financialhub_desktop.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.esprit.financialhub_desktop.entities.Agent;
import com.esprit.financialhub_desktop.entities.UserType;
import com.esprit.financialhub_desktop.tools.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AgentServiceImpl implements AgentService{


    @Override
    public List<Agent> getAgents() {
        String sql = "SELECT * FROM responsible_loan\n" +
                "UNION ALL\n" +
                "SELECT * FROM responsible_investment\n" +
                "UNION ALL\n" +
                "SELECT * FROM responsible_account\n" +
                "UNION ALL\n" +
                "SELECT * FROM responsible_clientele;\n";

        try {
            PreparedStatement preparedStatement = MyConnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Agent> agents = new ArrayList<>();
            while (resultSet.next()) {
                Agent agent = new Agent();
                agent.setFirst_name(resultSet.getString("first_name"));
                agent.setLast_name(resultSet.getString("last_name"));
                agent.setPhone(resultSet.getString("phone_number"));
                agent.setEmail(resultSet.getString("email"));
                agent.setUserType(UserType.valueOf(resultSet.getString("roles")
                        .replace("ROLE_", "")
                        .replace("[\"", "")
                        .replace("\"]", "")));
                agent.setCin(resultSet.getString("cin"));
                agent.setPassword(resultSet.getString("password"));

                agents.add(agent);
            }
            return agents;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // update email and password of an agent
    @Override
    public void updateAgent(Agent agent, String email, String password) {
        switch (agent.getUserType()) {
            case RESPONSIBLE_CLIENTELE:
                String sql = "UPDATE responsible_clientele SET email = ?, password = ? WHERE cin = ?";
                updateByRole(agent, email, password, sql);
                break;
            case RESPONSIBLE_LOAN:
                String sql2 = "UPDATE responsible_loan SET email = ?, password = ? WHERE cin = ?";
                updateByRole(agent, email, password, sql2);
                break;
            case RESPONSIBLE_INVESTMENT:
                String sql3 = "UPDATE responsible_investment SET email = ?, password = ? WHERE cin = ?";
                updateByRole(agent, email, password, sql3);
                break;
            case RESPONSIBLE_ACCOUNT:
                String sql4 = "UPDATE responsible_account SET email = ?, password = ? WHERE cin = ?";
                updateByRole(agent, email, password, sql4);
                break;
        }
    }

    @Override
    public void deleteAgent(Agent agent) {
        switch (agent.getUserType()) {
            case RESPONSIBLE_CLIENTELE:
                String sql = "DELETE FROM responsible_clientele WHERE cin = ?";
                deleteByRole(agent, sql);
                break;
            case RESPONSIBLE_LOAN:
                String sql2 = "DELETE FROM responsible_loan WHERE cin = ?";
                deleteByRole(agent, sql2);
                break;
            case RESPONSIBLE_INVESTMENT:
                String sql3 = "DELETE FROM responsible_investment WHERE cin = ?";
                deleteByRole(agent, sql3);
                break;
            case RESPONSIBLE_ACCOUNT:
                String sql4 = "DELETE FROM responsible_account WHERE cin = ?";
                deleteByRole(agent, sql4);
                break;
        }
    }

    @Override
    public void addAgent(Agent agent) {
        switch (agent.getUserType()) {
            case RESPONSIBLE_CLIENTELE:
                String sql = "INSERT INTO responsible_clientele (first_name, last_name, cin, phone_number, email, password,roles) VALUES (?, ?, ?, ?, ?, ?, ?)";
                addByRole(agent, sql);
                break;
            case RESPONSIBLE_LOAN:
                String sql2 = "INSERT INTO responsible_loan (first_name, last_name, cin, phone_number, email, password,roles) VALUES (?, ?, ?, ?, ?, ?, ?)";
                addByRole(agent, sql2);
                break;
            case RESPONSIBLE_INVESTMENT:
                String sql3 = "INSERT INTO responsible_investment (first_name, last_name, cin, phone_number, email, password,roles) VALUES (?, ?, ?, ?, ?, ?, ?)";
                addByRole(agent, sql3);
                break;
            case RESPONSIBLE_ACCOUNT:
                String sql4 = "INSERT INTO responsible_account (first_name, last_name, cin, phone_number, email, password,roles) VALUES (?, ?, ?, ?, ?, ?, ?)";
                addByRole(agent, sql4);
                break;
        }
    }

    @Override
    public void depositSalary(String agentType, double amount) {
        switch (agentType) {
            case "RESPONSIBLE_CLIENTELE":
                String sql = "UPDATE agent_accounts SET balance = balance + ? WHERE account_number IN (SELECT cin FROM responsible_clientele)";
                depositByRole(amount, sql);
                break;
            case "RESPONSIBLE_LOAN":
                String sql2 = "UPDATE agent_accounts SET balance = balance + ? WHERE account_number IN (SELECT cin FROM responsible_loan)";
                depositByRole(amount, sql2);
                break;
            case "RESPONSIBLE_INVESTMENT":
                String sql3 = "UPDATE agent_accounts SET balance = balance + ? WHERE account_number IN (SELECT cin FROM responsible_investment)";
                depositByRole(amount, sql3);
                break;
            case "RESPONSIBLE_ACCOUNT":
                String sql4 = "UPDATE agent_accounts SET balance = balance + ? WHERE account_number IN (SELECT cin FROM responsible_account)";
                depositByRole(amount, sql4);
                break;
        }
    }

    @Override
    public void updatePassword(String password,String email) {
        String sql = "SELECT * FROM responsible_loan\n" +
                "UNION ALL\n" +
                "SELECT * FROM responsible_investment\n" +
                "UNION ALL\n" +
                "SELECT * FROM responsible_account\n" +
                "UNION ALL\n" +
                "SELECT * FROM responsible_clientele;\n";

        boolean userFound = false; // Flag to track if any user is found

        try {
            PreparedStatement preparedStatement = MyConnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String userEmail = resultSet.getString("email");
                if (userEmail.equals(email)) {
                    userFound = true;
                    String cin = resultSet.getString("cin");
                    String role = resultSet.getString("roles")
                            .replace("ROLE_", "")
                            .replace("[\"", "")
                            .replace("\"]", "");
                    String sql2 = "UPDATE " + role + " SET password = ? WHERE cin = ?";
                    PreparedStatement preparedStatement2 = MyConnection.getInstance().getConnection().prepareStatement(sql2);
                    preparedStatement2.setString(1, cryptPassword(password));
                    preparedStatement2.setString(2, cin);
                    preparedStatement2.executeUpdate();
                }
            }
            if (!userFound) {
                System.out.println("agent not found search in admin");
                String sql3 = "SELECT * FROM user WHERE email = ?";
                PreparedStatement preparedStatement3 = MyConnection.getInstance().getConnection().prepareStatement(sql3);
                preparedStatement3.setString(1, email);
                ResultSet resultSet2 = preparedStatement3.executeQuery();
                if (resultSet2.next()) {
                    String sql4 = "UPDATE user SET password = ? WHERE email = ?";
                    PreparedStatement preparedStatement4 = MyConnection.getInstance().getConnection().prepareStatement(sql4);
                    preparedStatement4.setString(1, cryptPassword(password));
                    preparedStatement4.setString(2, email);
                    preparedStatement4.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void depositByRole(double amount, String sql3) {
        try {
            PreparedStatement preparedStatement = MyConnection.getInstance().getConnection().prepareStatement(sql3);
            preparedStatement.setDouble(1, amount);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addByRole(Agent agent, String sql) {
        try {
            System.out.println("agent" + agent);
            System.out.println("sql" + sql);
            // Assuming agent.getRoles() returns a string representing a single role
            String role = agent.getUserType().toString();

            // Constructing the JSON string with the specified structure
            String rolesJson = "[\""+"ROLE_" + role + "\"]";
            PreparedStatement preparedStatement = MyConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setString(1, agent.getFirst_name());
            preparedStatement.setString(2, agent.getLast_name());
            preparedStatement.setString(3, agent.getCin());
            preparedStatement.setString(4, agent.getPhone());
            preparedStatement.setString(5, agent.getEmail());
            preparedStatement.setString(6, cryptPassword(agent.getPassword()));
            preparedStatement.setString(7, rolesJson);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteByRole(Agent agent, String sql) {
        try {
            PreparedStatement preparedStatement = MyConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setString(1, agent.getCin());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateByRole(Agent agent, String email, String password, String sql) {
        try {
            PreparedStatement preparedStatement = MyConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, agent.getCin());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String cryptPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

}
