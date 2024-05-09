package com.esprit.financialhub_desktop;

import com.esprit.financialhub_desktop.services.AgentServiceImpl;
import com.esprit.financialhub_desktop.services.UserService;
import com.esprit.financialhub_desktop.services.UserServiceImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class FinancialHub extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        URL loginFxmlUrl = FinancialHub.class.getResource("/com/esprit/financialhub_desktop/welcome.fxml");


        if (loginFxmlUrl != null) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(loginFxmlUrl));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/esprit/css/welcome.css")).toExternalForm());
            stage.setTitle("FinancialHub!");
            Image image = new Image("https://as2.ftcdn.net/v2/jpg/05/77/65/21/1000_F_577652170_zS9lFJVOGUam2FayctBGt5DXqmSsNebI.jpg");
            double width = 80; // Specify the desired width
            double height = 80; // Specify the desired height
            image = new Image(image.getUrl(), width, height, false, false);
            stage.getIcons().add(image);

            stage.setScene(scene);
            stage.show();
        } else {
            System.err.println("FXML file not found");
        }
    }

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        System.out.println("password hash: "+userService.hashPassword("SecurePwd123!",
                "$2y$13$5/.FeQNcTADrKPI5MtWZyuPOoILBc3F2VN4Bl0O2PrD8f4JUgC6w2"));

        AgentServiceImpl agentService = new AgentServiceImpl();
        System.out.println(agentService.getAgents());

        launch();
    }
}