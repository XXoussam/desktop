package com.esprit.financialhub_desktop.Controller;

import com.esprit.financialhub_desktop.entities.Agent;
import com.esprit.financialhub_desktop.entities.UserType;
import com.esprit.financialhub_desktop.services.AgentServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditAgentController implements Initializable {
    public TextField emailField;
    public TextField passwordField;
    public ChoiceBox optionChoiceBox;
    public Label emailError;
    public Label passwordError;
    public Button saveButton;
    public Button cancelButton;
    private Agent agent;
    private AgentServiceImpl agentService = new AgentServiceImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void save(ActionEvent actionEvent) throws IOException {

        String email = emailField.getText();
        String password = passwordField.getText();


        // Validate email, option, and password fields
        if (email.isEmpty() || password.isEmpty()) {
            if (email.isEmpty()) {
                emailError.setVisible(true);
            }
            if (password.isEmpty()) {
                passwordError.setVisible(true);
            }
            return;
        }

        // Perform agent update
        agentService.updateAgent(agent, email, password);

        // Notify AgentsController about the update
        AgentsController.notifyAgentUpdated();

        // Close the current scene
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();


    }

    public void cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }



}
