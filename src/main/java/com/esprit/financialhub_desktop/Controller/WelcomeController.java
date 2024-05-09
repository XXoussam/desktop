package com.esprit.financialhub_desktop.Controller;

import com.esprit.financialhub_desktop.FinancialHub;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {
    public TextFlow perspective_nav;
    @FXML
    private Pane pnlOverview;
    private String currentView;
    @FXML
    private TextFlow dash_nav;
    @FXML
    private TextFlow agents_nav;
    @FXML
    private TextFlow accounts_nav;
    @FXML
    private TextFlow setting_nav;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void getDashboard(MouseEvent mouseEvent) {
        pnlOverview.getChildren().clear();
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(FinancialHub.class.getResource("/com/esprit/financialhub_desktop/adminDash.fxml")));
            Parent menu = loader.load();
            pnlOverview.getChildren().add(menu);

            currentView = "dashboard";
            resetNav();
            dash_nav.getStyleClass().add("selectedText");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void getAgents(MouseEvent mouseEvent) {
        pnlOverview.getChildren().clear();
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(FinancialHub.class.getResource("/com/esprit/financialhub_desktop/agents.fxml")));
            Parent menu = loader.load();
            pnlOverview.getChildren().add(menu);

            currentView = "agents";
            resetNav();
            agents_nav.getStyleClass().add("selectedText");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void getAccounts(MouseEvent mouseEvent) {
        pnlOverview.getChildren().clear();
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(FinancialHub.class.getResource("/com/esprit/financialhub_desktop/accounts.fxml")));
            Parent menu = loader.load();
            pnlOverview.getChildren().add(menu);

            currentView = "accounts";
            resetNav();
            accounts_nav.getStyleClass().add("selectedText");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void getSetting(MouseEvent mouseEvent) {
        pnlOverview.getChildren().clear();
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(FinancialHub.class.getResource("/com/esprit/financialhub_desktop/settings.fxml")));
            Parent menu = loader.load();
            pnlOverview.getChildren().add(menu);

            currentView = "settings";
            resetNav();
            setting_nav.getStyleClass().add("selectedText");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void logout(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(FinancialHub.class.getResource("/com/esprit/financialhub_desktop/Login.fxml")));
        Parent menu = loader.load();

        Window currentWindow = pnlOverview.getScene().getWindow();
        Stage dashboardStage = new Stage();
        dashboardStage.setScene(new Scene(menu, 800, 520));
        currentWindow.hide();
        Image image = new Image("https://as2.ftcdn.net/v2/jpg/05/77/65/21/1000_F_577652170_zS9lFJVOGUam2FayctBGt5DXqmSsNebI.jpg");
        double width = 80; // Specify the desired width
        double height = 80; // Specify the desired height
        image = new Image(image.getUrl(), width, height, false, false);
        dashboardStage.getIcons().add(image);
        dashboardStage.show();
    }

    private void resetNav() {
        if (currentView != null) {
            switch (currentView) {
                case "dashboard":
                    agents_nav.getStyleClass().remove("selectedText");
                    accounts_nav.getStyleClass().remove("selectedText");
                    perspective_nav.getStyleClass().remove("selectedText");
                    break;
                case "agents":
                    dash_nav.getStyleClass().remove("selectedText");
                    accounts_nav.getStyleClass().remove("selectedText");
                    perspective_nav.getStyleClass().remove("selectedText");
                    break;
                case "accounts":
                    dash_nav.getStyleClass().remove("selectedText");
                    agents_nav.getStyleClass().remove("selectedText");
                    perspective_nav.getStyleClass().remove("selectedText");
                    break;
                case "perspective":
                    dash_nav.getStyleClass().remove("selectedText");
                    agents_nav.getStyleClass().remove("selectedText");
                    accounts_nav.getStyleClass().remove("selectedText");
                    break;
            }
        }
    }


    public void getPERSPECTIVE(MouseEvent mouseEvent) {
        pnlOverview.getChildren().clear();
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(FinancialHub.class.getResource("/com/esprit/financialhub_desktop/perspective.fxml")));
            Parent menu = loader.load();
            pnlOverview.getChildren().add(menu);

            currentView = "perspective";
            resetNav();
            perspective_nav.getStyleClass().add("selectedText");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
