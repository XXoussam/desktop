package com.esprit.financialhub_desktop.Controller;

import com.esprit.financialhub_desktop.entities.Account;
import com.esprit.financialhub_desktop.entities.ClientAccount;
import com.esprit.financialhub_desktop.services.AccountServiceI;
import com.esprit.financialhub_desktop.services.AccountServiceImpl;
import com.esprit.financialhub_desktop.services.ExchangeMoney;
import com.esprit.financialhub_desktop.services.ExportExcelService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AccountsController implements Initializable {
    public TableColumn createdAt;
    public ChoiceBox from;
    public ChoiceBox to;
    public TextField amount;
    public Label result;
    private AccountServiceI accountService = new AccountServiceImpl();

    @FXML
    private VBox vbox;

    @FXML
    private TableView<Account> accountsTable;

    @FXML
    private TableColumn<Account, Integer> idColumn;

    @FXML
    private TableColumn<Account, String> accountNumberColumn;

    @FXML
    private TableColumn<Account, Double> balanceColumn;

    @FXML
    private TableColumn<Account, String> currencyColumn;

    @FXML
    private TableView<ClientAccount> clientsTable;

    @FXML
    private TableColumn<ClientAccount, Integer> accountClientNumberColumn;

    @FXML
    private TableColumn<ClientAccount, String> accountTypeClientColumn;

    @FXML
    private TableColumn<ClientAccount, Double> balanceClientColumn;

    @FXML
    private TableColumn<ClientAccount, String> currencyClientColumn;

    private ExportExcelService exportExcelService = new ExportExcelService();

    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the columns for accountsTable
        accountNumberColumn.setCellValueFactory(new PropertyValueFactory<>("account_number"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currency"));

        // Initialize the columns for clientsTable

        accountClientNumberColumn.setCellValueFactory(new PropertyValueFactory<>("account_number"));
        accountTypeClientColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        balanceClientColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        currencyClientColumn.setCellValueFactory(new PropertyValueFactory<>("currency"));
        createdAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));


        // Populate data for accountsTable
        List<Account> accounts = accountService.getAgentsAccounts();
        ObservableList<Account> accountsData = FXCollections.observableArrayList(
                accounts
        );
        accountsTable.setItems(accountsData);

        // Populate data for clientsTable
        List<ClientAccount> clientAccounts = accountService.getClientAccounts();
        ObservableList<ClientAccount> clientsData = FXCollections.observableArrayList(
                clientAccounts);
        clientsTable.setItems(clientsData);
    }

    public void exportAgents(ActionEvent actionEvent) throws IOException {
        String directoryPath = "C:\\Users\\saoud\\Downloads";
        String fileName = "agents_data.xlsx";
        File file = new File(directoryPath, fileName);
        exportExcelService.exportToExcel(accountsTable, file);
    }

    public void exportClients(ActionEvent actionEvent) throws IOException {
        String directoryPath = "C:\\Users\\saoud\\Downloads";
        String fileName = "clients_data.xlsx";
        File file = new File(directoryPath, fileName);
        exportExcelService.exportToExcel(clientsTable, file);
    }

    public void convert(ActionEvent actionEvent) throws IOException {
        String fromCurrency = from.getValue().toString();
        String toCurrency = to.getValue().toString();
        double amountToConvert = Double.parseDouble(amount.getText());

        double convertedAmount = ExchangeMoney.convertCurrency(fromCurrency, toCurrency, amountToConvert);
        System.out.println("Converted amount: " + convertedAmount);
        switch (toCurrency) {
            case "USD":
                result.setText("Converted amount: " +"$" + convertedAmount);
                break;
            case "EUR":
                result.setText("Converted amount: " +"€" + convertedAmount);
                break;
            case "TND":
                result.setText("Converted amount: " +convertedAmount + " DT");
                break;
        }

    }
}
