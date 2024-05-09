package com.esprit.financialhub_desktop.Controller;

import com.esprit.financialhub_desktop.services.AgentService;
import com.esprit.financialhub_desktop.services.AgentServiceImpl;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class NewPasswordController {
    public TextField new_password;
    public TextField confirm_new_password;
    public Label err_mssg;
    private AgentServiceImpl agentService = new AgentServiceImpl();
    private String email;

    public void setEmail(String email) {
        this.email = email;
    }

    public void saveNewPassword(ActionEvent actionEvent) {
        String password = new_password.getText();
        String confirm_password = confirm_new_password.getText();
        if (password.equals(confirm_password)) {
            System.out.println("Password saved");
            System.out.println(email);
            agentService.updatePassword(password, email);
        } else {
            System.out.println("Passwords don't match");
            err_mssg.setText("Passwords don't match");
            err_mssg.setVisible(true);
        }
    }
}
