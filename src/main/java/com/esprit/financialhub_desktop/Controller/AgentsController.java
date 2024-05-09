package com.esprit.financialhub_desktop.Controller;

import com.esprit.financialhub_desktop.entities.Agent;
import com.esprit.financialhub_desktop.entities.AgentSalDTO;
import com.esprit.financialhub_desktop.services.AgentServiceImpl;
import com.esprit.financialhub_desktop.services.EmailSender;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class AgentsController implements Initializable{

    public Button btn_add;
    @FXML
    private VBox agentsVBox;

    private AgentServiceImpl agentService = new AgentServiceImpl();

    private static AgentsController instance;


    @FXML
    private TableView<AgentSalDTO> agentsTableView;

    @FXML
    private TableColumn<AgentSalDTO, String> agentTypeColumn;

    @FXML
    private TableColumn<AgentSalDTO, String> descriptionColumn;

    @FXML
    private TableColumn<AgentSalDTO, Double> amountColumn;

    @FXML
    private CheckBox automateDepositCheckBox;


    // Using AtomicBoolean instead of a simple boolean provides thread safety when accessing and modifying the boolean value across multiple threads.
    private AtomicBoolean depositTaskRunning = new AtomicBoolean(false);


    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        loadAgentLayout();

        // Set cell factory for amount column to TextFieldTableCell with a DoubleStringConverter
        amountColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        // Set the cell editing policy to enable editing
        agentsTableView.setEditable(true); // Enable editing for the entire TableView

        // Initialize the list with initial data
        ObservableList<AgentSalDTO> agentsList = FXCollections.observableArrayList(
                new AgentSalDTO("RESPONSIBLE_CLIENTELE", "Description 1 for RESPONSIBLE_CLIENTELE", 1800.0),
                new AgentSalDTO("RESPONSIBLE_ACCOUNT", "Description 2 for RESPONSIBLE_ACCOUNT", 2700.0),
                new AgentSalDTO("RESPONSIBLE_INVESTMENT", "Description 3 for RESPONSIBLE_INVESTMENT", 3000.0),
                new AgentSalDTO("RESPONSIBLE_LOAN", "Description 4 for RESPONSIBLE_LOAN", 5600.0)
        );

        // Set the items of the TableView to the observable list
        agentsTableView.setItems(agentsList);

        // Configure column cell value factories (assuming you have appropriate getters in AgentSalDTO)
        agentTypeColumn.setCellValueFactory(cellData -> cellData.getValue().agentTypeProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());

        // Enable editing of the amount column
        amountColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        amountColumn.setOnEditCommit(event -> {
            // Get the edited value
            Double newValue = event.getNewValue();
            // Get the index of the edited row
            int rowIndex = event.getTablePosition().getRow();
            // Get the AgentSalDTO object at the edited row
            AgentSalDTO agent = agentsList.get(rowIndex);
            // Set the new amount value
            agent.setAmount(newValue);
            // Commit the edit to the TableView
            agentsTableView.getItems().set(rowIndex, agent);
        });

        TableColumn<AgentSalDTO, Void> refreshColumn = new TableColumn<>("Refresh");
        refreshColumn.setCellFactory(param -> new TableCell<>() {
            private final Button refreshButton = new Button("Deposit");

            {
                refreshButton.setOnAction(event -> {
                    AgentSalDTO agent = getTableView().getItems().get(getIndex());
                    // Perform the action you want when the button is clicked, for example, deposit action
                    depositAction(agent);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(refreshButton);
                }
            }
        });
        agentsTableView.getColumns().add(refreshColumn);

        automateDepositCheckBox.setOnAction(event -> {
            if (automateDepositCheckBox.isSelected()) {
                scheduleMonthlyDeposit();
                System.out.println("Scheduled deposit task started");
            } else {
                stopScheduledDepositTask();
                System.out.println("Scheduled deposit task stopped");

            }
        });
    }


    private void scheduleMonthlyDeposit() {
        depositTaskRunning.set(true);
        Thread depositThread = new Thread(() -> {
            while (depositTaskRunning.get()) {
                LocalDate currentDate = LocalDate.now();
                if (currentDate.getDayOfMonth() == 30) {
                    List<AgentSalDTO> agents = agentsTableView.getItems();
                    for (AgentSalDTO agent : agents) {
                        depositAction(agent);
                    }
                    try {
                        Thread.sleep(86400000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Thread.sleep(3600000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        depositThread.start();
    }

    private void stopScheduledDepositTask() {
        // Set the flag to indicate that the deposit task should stop
        depositTaskRunning.set(false);
    }



    // Define the deposit action
    private void depositAction(AgentSalDTO agent) {
        System.out.println("Deposit action for agent: " + agent.getAgentType());
        System.out.println("Amount: " + agent.getAmount());

        agentService.depositSalary(agent.getAgentType(), agent.getAmount());
        EmailSender.sendEmail("ooussnasaoucila@gmail.com", "Salary Deposit", "Your salary has been deposited successfully!");
    }



    public static void notifyAgentUpdated() {
        instance.loadAgentLayout();
    }

    void loadAgentLayout() {
        try {
            // Clear the current list of agents
            agentsVBox.getChildren().clear();

            // Load the updated list of agents
            List<Agent> agents = agentService.getAgents();
            for (Agent agent : agents) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/financialhub_desktop/components/agent_details.fxml"));
                BorderPane agentLayout = loader.load();
                agentsVBox.getChildren().add(agentLayout);
                AgentController agentController = loader.getController();
                agentController.setAgent(agent); // Pass the agent to the controller

            }
            agentsVBox.setSpacing(10);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Event handler for the "Details" button in the agent details layout
    @FXML
    private void handleDetails(ActionEvent event) {
        // Get the source of the event (the button that was clicked)
        Object source = event.getSource();

        // Check if the source is a BorderPane (assuming each agent details layout is a BorderPane)
        if (source instanceof BorderPane) {
            // Retrieve the controller associated with the BorderPane
            AgentController agentDetailsController = (AgentController) ((BorderPane) source).getProperties().get("controller");

            // Retrieve the agent associated with the BorderPane
            Agent selectedAgent = agentDetailsController.getAgent();

            // Now you have the selected agent, you can pass it to the showDetails method in the AgentController
            AgentController agentController = new AgentController();
            try {
                agentController.showDetails(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void add_new(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/financialhub_desktop/components/agent_save.fxml"));
            Parent addAgentLayout = loader.load();

            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL); // Ensure this stage blocks interactions with other windows
            newStage.setTitle("Add New Agent");

            Image image = new Image("https://as2.ftcdn.net/v2/jpg/05/77/65/21/1000_F_577652170_zS9lFJVOGUam2FayctBGt5DXqmSsNebI.jpg");
            double width = 80;
            double height = 80;
            image = new Image(image.getUrl(), width, height, false, false);

            Scene scene = new Scene(addAgentLayout);
            newStage.setScene(scene);

            newStage.getIcons().add(image);

            newStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void depositSalary(ActionEvent actionEvent) {

    }


}
