package com.esprit.financialhub_desktop.Controller;

import com.esprit.financialhub_desktop.entities.Agent;
import com.esprit.financialhub_desktop.entities.UserType;
import com.esprit.financialhub_desktop.services.AgentService;
import com.esprit.financialhub_desktop.services.AgentServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SaveAgentController implements Initializable {
    public TextField first_name;
    public TextField last_name;
    public TextField cin;
    public TextField phone;
    public TextField email;
    public TextField password;
    public ComboBox role;
    public Label err_mssg;
    private AgentServiceImpl agentService = new AgentServiceImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        role.setValue(UserType.RESPONSIBLE_ACCOUNT);
    }

    public void close(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void save(ActionEvent actionEvent) {
        String firstName = first_name.getText();
        String lastName = last_name.getText();
        String cinNumber = cin.getText();
        String phoneNumber = phone.getText();
        String emailAddr = email.getText();
        String pwd = password.getText();
        String roleValue = role.getValue().toString();

        // Clear any previous error messages and reset style
        err_mssg.setText("");

        if (firstName.isEmpty() || lastName.isEmpty() || cinNumber.isEmpty() || phoneNumber.isEmpty() || emailAddr.isEmpty() || pwd.isEmpty() || roleValue.isEmpty()) {
            // Display error message in red with font size
            err_mssg.setText("Please fill in all fields.");
            err_mssg.setStyle("-fx-text-fill: red; -fx-font-size: 12px; -fx-font-weight: normal;"); // Set text color to red and font size
            return; // Exit method if validation fails
        }


        if (!isValidEmail(emailAddr)) {
            err_mssg.setText("Please enter a valid email address.");
            err_mssg.setStyle("-fx-text-fill: red; -fx-font-size: 12px; -fx-font-weight: normal;"); // Set text color to red
            return;
        }

        if (!isStrongPassword(pwd)) {
            err_mssg.setText("Please enter a strong password (at least 8 characters).");
            err_mssg.setStyle("-fx-text-fill: red; -fx-font-size: 12px; -fx-font-weight: normal;"); // Set text color to red
            return;
        }

        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);


        Agent agent = new Agent();
        agent.setFirst_name(firstName);
        agent.setLast_name(lastName);
        agent.setCin(cinNumber);
        agent.setPhone(phoneNumber);
        agent.setEmail(emailAddr);
        agent.setPassword(pwd);
        agent.setUserType(UserType.valueOf(roleValue));
        System.out.println(agent);

        agentService.addAgent(agent);

        close(actionEvent);
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    private boolean isStrongPassword(String password) {
        return password.length() >= 8;
    }


}
