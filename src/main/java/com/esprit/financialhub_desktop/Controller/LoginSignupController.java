package com.esprit.financialhub_desktop.Controller;


import com.esprit.financialhub_desktop.FinancialHub;
import com.esprit.financialhub_desktop.entities.User;
import com.esprit.financialhub_desktop.entities.UserType;
import com.esprit.financialhub_desktop.services.EmailSender;
import com.esprit.financialhub_desktop.services.UserService;
import com.esprit.financialhub_desktop.services.UserServiceImpl;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class LoginSignupController {
    public Label err_mssg_username;
    @FXML
    private Pane content;
    @FXML
    private AnchorPane childanch;



    @FXML
        private TextField loginUsernameField;

        @FXML
        private PasswordField loginPasswordField;

        @FXML
        private TextField signupUsernameField;

        @FXML
        private TextField urlpic;

        @FXML
        private PasswordField signupPasswordField;

    @FXML
    private PasswordField RepeatsignupPasswordField;

        @FXML
        private TabPane tabPane;

        @FXML
        private Text ResultText;

        private UserService userService = new UserServiceImpl();

    @FXML
    private void handle_login() throws IOException {
        String username = loginUsernameField.getText();
        String password = loginPasswordField.getText();

        User loggedInUser = userService.login(username, password);

        if (loggedInUser != null) {
            if (loggedInUser.getUserType().equals(UserType.ADMIN)) {
                System.out.println("admin logged in");

                URL adminDashboardFxmlUrl = FinancialHub.class.getResource("/com/esprit/financialhub_desktop/Welcome.fxml");
                System.out.println(adminDashboardFxmlUrl);
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(adminDashboardFxmlUrl));
                Parent menu = loader.load();

                WelcomeController welcomeController = loader.getController();
                /*adminDashboardController.setLoggedInUser(loggedInUser);
                adminDashboardController.initData();*/

                Window currentWindow = content.getScene().getWindow();
                Stage dashboardStage = new Stage();
                dashboardStage.setScene(new Scene(menu, 1000, 650));
                currentWindow.hide();
                dashboardStage.getIcons().add(new Image("https://as2.ftcdn.net/v2/jpg/05/77/65/21/1000_F_577652170_zS9lFJVOGUam2FayctBGt5DXqmSsNebI.jpg"));
                dashboardStage.show();
                
            } else if (loggedInUser.getUserType().equals(UserType.RESPONSIBLE_ACCOUNT)) {
                

            } else if (loggedInUser.getUserType().equals(UserType.RESPONSIBLE_CLIENTELE)) {
                
            } else if (loggedInUser.getUserType().equals(UserType.RESPONSIBLE_INVESTMENT)) {
                
            } else if (loggedInUser.getUserType().equals(UserType.RESPONSIBLE_LOAN)) {

            } else {
                System.out.println(loggedInUser.getUserType());
                ResultText.setText("Login failed. Check your username and password.");
            }
        }
    }

    public void sendVerifyCode(MouseEvent mouseEvent) {
        if (loginUsernameField.getText().isEmpty()) {
            err_mssg_username.setVisible(true);
            return;
        }

        // generate code contains 6 digits
        int code = (int) (Math.random() * 900000) + 100000;
        EmailSender.sendEmail("ooussnasocial@gmail.com", "Verification Code", "Your verification code is: " + code);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/financialhub_desktop/components/verifcationCode.fxml"));
        try {
            Parent root = loader.load();
            VerificationCodeController verificationCodeController = loader.getController();
            verificationCodeController.setCode(code);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setWidth(400);
            stage.setHeight(400);
            stage.show();
            verificationCodeController.setEmail(loginUsernameField.getText());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



   /* private void loadCamperDashboard() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(TuniCamp.class.getResource("c-dashboard.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Camper Dashboard");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

   /* @FXML
    private void loginview(ActionEvent event) throws IOException {
        Parent menu = FXMLLoader.load(Objects.requireNonNull(TuniCamp.class.getResource("views/Login.fxml")));
        content.getChildren().removeAll();
        content.getChildren().setAll(menu);

    }*/
    }



