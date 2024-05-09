package com.esprit.financialhub_desktop.services;

import com.esprit.financialhub_desktop.entities.User;


public interface UserService {
        User login(String username, String password);
        String getId(User s);
        String getUserNameById(int userId);
        boolean hashPassword(String password,String hashedPassword);
}
