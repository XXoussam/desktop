package com.esprit.financialhub_desktop.Controller;

import com.esprit.financialhub_desktop.entities.Agent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ShowAgentController {
    @FXML
    public Label passwordLabel;
    @FXML
    public Label phoneLabel;
    @FXML
    public Label cinLabel;
    @FXML
    private Label nameLabel;

    @FXML
    private Label roleLabel;

    @FXML
    private Label emailLabel;

    public void setAgentDetails(Agent agent) {
        nameLabel.setText(agent.getFirst_name() + " " + agent.getLast_name());
        roleLabel.setText(agent.getUserType().name());
        emailLabel.setText(agent.getEmail());
        cinLabel.setText(agent.getCin());
        phoneLabel.setText(agent.getPhone());
        passwordLabel.setText(agent.getPassword());

        // Set other details as needed
    }
}
