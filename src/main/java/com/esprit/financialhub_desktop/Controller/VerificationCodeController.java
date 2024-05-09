package com.esprit.financialhub_desktop.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class VerificationCodeController {
    public TextField verification_code;
    public Label err_mssg;
    private int code;
    private String email;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void verify(ActionEvent actionEvent) {
        String input = verification_code.getText();
        if (input.matches("\\d{6}")) {
            int enteredCode = Integer.parseInt(input);
            if (enteredCode == code) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/financialhub_desktop/components/newPassword.fxml"));
                    Parent root = loader.load();
                    NewPasswordController newPasswordController = loader.getController();
                    newPasswordController.setEmail(email);
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setWidth(600);
                    stage.setHeight(400);
                    stage.show();

                    ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("Wrong code");
                err_mssg.setText("Wrong code");
                err_mssg.setVisible(true);
            }
        } else {
            System.out.println("Invalid input");
            err_mssg.setVisible(true);
            err_mssg.setText("Invalid input");
        }

    }

}
