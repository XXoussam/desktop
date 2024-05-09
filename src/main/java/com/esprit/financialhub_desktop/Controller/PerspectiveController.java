package com.esprit.financialhub_desktop.Controller;

import com.esprit.financialhub_desktop.services.EmailSender;
import com.esprit.financialhub_desktop.services.GPTAPI;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class PerspectiveController implements Initializable {


    public Text mytext;
    public Button export_btn;
    public ProgressIndicator progressIndicator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mytext.setVisible(false);
        export_btn.setVisible(false);
    }

    public void exportButtonAction(ActionEvent actionEvent) {
        EmailSender.sendEmail("ooussnasocial@gmail.com","Rapport",mytext.getText());

    }


    public void writeRapport(){
        String data1 = execPromptGpt("You are a helpful assistant for bank admin." +
                "i will give you some diagram bank data and you should generate me an interpretation" +
                "this data is for the variation of number of customers oussna bank by account type" +
                "STUDENT_ACCOUNT: 213 clients,SAVINGS_ACCOUNT: 67 clients,JOINT_ACCOUNT: 36 clients,CHECKING_ACCOUNT: 152 clients,BUSINESS_ACCOUNT: 210 clients");


        String data2 = execPromptGpt("You are a helpful assistant for bank admin." +
                "i will give you some diagram bank data and you should generate me an interpretation" +
                "this data represents a bar chart illustrating the variation of money flow (in millions) by different account types." +
                "STUDENT_ACCOUNT: $213 million\n" +
                "SAVINGS_ACCOUNT: $67 million\n" +
                "JOINT_ACCOUNT: $36 million\n" +
                "CHECKING_ACCOUNT: $152 million\n" +
                "BUSINESS_ACCOUNT: $210 million");


        String rapport = execPromptGpt("from those two data interpretations, write a report with details on the financial situation of the company" +
                "give me business plans and strategies to improve the company's financial situation" +
                " and give me some perspective for the company" +
                "Data 1: " + data1 + "Data 2: " + data2);

        System.out.println(rapport + "rapport\n"+ data1 + "data1\n" + data2 + "data2\n");
        mytext.setText(rapport );

    }

    public String execPromptGpt (String prompt){
        GPTAPI gptApi = new GPTAPI("api key here");
        String model = "gpt-3.5-turbo-instruct";
        String result = gptApi.queryGPT(prompt, model);
        JSONObject jsonObject = new JSONObject(result);
        String textContent = jsonObject.getJSONArray("choices")
                .getJSONObject(0)
                .getString("text");
        return textContent;
    }

    public void generate(ActionEvent actionEvent) {
        // Create a service for the writeRapport task
        Service<Void> writeRapportService = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        // Execute the writeRapport method here
                        writeRapport();
                        return null;
                    }
                };
            }
        };
        // Update ProgressIndicator as the service progresses
        progressIndicator.progressProperty().bind(writeRapportService.progressProperty());

        // Handle service events
        writeRapportService.setOnRunning(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                // Show ProgressIndicator when the service starts
                progressIndicator.setVisible(true);
            }
        });

        writeRapportService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                // Hide ProgressIndicator when the service completes
                progressIndicator.setVisible(false);
                // Show other components as needed
                mytext.setVisible(true);
                export_btn.setVisible(true);
            }
        });

        // Start the service
        writeRapportService.start();
    }
}
