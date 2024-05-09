package com.esprit.financialhub_desktop.services;


import com.esprit.financialhub_desktop.entities.User;
import com.esprit.financialhub_desktop.entities.UserType;
import com.esprit.financialhub_desktop.tools.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import at.favre.lib.crypto.bcrypt.BCrypt;

public class UserServiceImpl implements UserService {


    @Override
    public User login(String username, String password) {
        System.out.println(cryptPassword(password));
            String selectQuery = "SELECT * FROM user WHERE email = ?";
            try (PreparedStatement preparedStatement = MyConnection.getInstance().getConnection().prepareStatement(selectQuery)) {
                preparedStatement.setString(1, username);


                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        if (!verifyPassword(password, resultSet.getString("password"))) {
                            return null;
                        }

                        int userId = resultSet.getInt("id");
                        String url = resultSet.getString("url");
                        UserType userType = UserType.valueOf(resultSet.getString("userType"));
                        return new User(userId, username, password, url,userType);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        return null;
    }


    private static String generateRandomCVV() {
        Random random = new Random();
        return String.format("%03d", random.nextInt(1000));
    }

    private static String generateRandomExpirationDate() {
        Random random = new Random();
        int currentYear = 2023;
        int randomYear = currentYear + random.nextInt(5);
        int randomMonth = random.nextInt(12) + 1;

        return String.format("%02d/%d", randomMonth, randomYear);
    }

    @Override
    public String getId(User s) {
        String selectQuery = "SELECT * FROM stripeusers WHERE id = ?";
        try (PreparedStatement preparedStatement = MyConnection.getInstance().getConnection().prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, s.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String identifier = resultSet.getString("identifier");
                    return identifier;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    private boolean userExists(String username) {

            String selectQuery = "SELECT * FROM user WHERE username = ?";
            try (PreparedStatement preparedStatement = MyConnection.getInstance().getConnection().prepareStatement(selectQuery)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return true;
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        return false;
    }

    @Override
    public String getUserNameById(int userId){
        String username = null;
        String sql = "SELECT username FROM user WHERE id = ?";

        try (PreparedStatement statement = MyConnection.getInstance().getConnection().prepareStatement(sql)) {
            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    username = resultSet.getString("username");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return username;
    }

    

    @Override
    public boolean hashPassword(String password, String hashedPassword) {
        return BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified;
    }

    private String cryptPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    private boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified;
    }

}


