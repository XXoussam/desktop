package com.esprit.financialhub_desktop.Controller;

import com.esprit.financialhub_desktop.entities.Agent;
import com.esprit.financialhub_desktop.services.AgentService;
import com.esprit.financialhub_desktop.services.AgentServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class AgentController{
    @FXML
    public Label agent_email;
    @FXML
    private Label agent_name;
    @FXML
    private Label agent_role;

    private Agent agent; // Added agent field

    private AgentServiceImpl agentService = new AgentServiceImpl();

    public void editAgent(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/financialhub_desktop/components/edit_agent.fxml"));
        Parent root = loader.load();

        EditAgentController editAgentController = loader.getController();
        editAgentController.setAgent(agent);

        Stage stage = new Stage();
        stage.setTitle("Agent Edit");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void deleteAgent(ActionEvent actionEvent) {
        agentService.deleteAgent(agent);
        AgentsController.notifyAgentUpdated();
    }

    // Modified showDetails method to accept the agent parameter
    public void showDetails(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/financialhub_desktop/components/show_agent.fxml"));
        Parent root = loader.load();

        ShowAgentController showAgentController = loader.getController();
        showAgentController.setAgentDetails(agent);

        Stage stage = new Stage();
        stage.setTitle("Agent Details");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Added method to set the agent
    public void setAgent(Agent agent) {
        this.agent = agent;
        agent_name.setText(agent.getFirst_name() + " " + agent.getLast_name());
        agent_role.setText(agent.getUserType().name());
        agent_email.setText(agent.getEmail());
    }

    // Added method to get the agent
    public Agent getAgent() {
        return agent;
    }

}
