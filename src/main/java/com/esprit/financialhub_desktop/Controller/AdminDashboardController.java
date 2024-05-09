package com.esprit.financialhub_desktop.Controller;

import com.esprit.financialhub_desktop.services.GPTAPI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import org.json.JSONObject;

import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {

    public TextArea pieChartLabel;
    public TextArea barChartLabel;
    @FXML
    private AreaChart<Number, Number> areaChart;
    @FXML
    private PieChart pieChart;
    @FXML
    private BarChart<String,Number> barChart;
    @FXML
    private Label percentageLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        feadAreaChart(null);
        feadPieChart(null);
        feadBarChart();
    }

    @FXML
    public void feadAreaChart(MouseEvent mouseEvent) {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        // Set labels for the axes
        xAxis.setLabel("X");
        yAxis.setLabel("Y");

        // Create the area chart
        areaChart.setTitle("Area Chart Example");

        // Define the data for the chart

        List<String> categories = List.of("STUDENT_ACCOUNT", "SAVINGS_ACCOUNT", "JOINT_ACCOUNT", "CHECKING_ACCOUNT", "BUSINESS_ACCOUNT");
        Random random = new Random();

        for (String cat:categories) {
            XYChart.Series<Number,Number> series = new XYChart.Series<>();
            series.setName(cat);
            series.getData().add(new XYChart.Data<>(0, random.nextInt(1000)));
            series.getData().add(new XYChart.Data<>(0.5, random.nextInt(1000)));
            series.getData().add(new XYChart.Data<>(1, random.nextInt(1000)));
            series.getData().add(new XYChart.Data<>(1.5, random.nextInt(1000)));
            series.getData().add(new XYChart.Data<>(2, random.nextInt(1000)));
            series.getData().add(new XYChart.Data<>(2.5, random.nextInt(1000)));
            series.getData().add(new XYChart.Data<>(3, random.nextInt(1000)));
            series.getData().add(new XYChart.Data<>(3.5, random.nextInt(1000)));
            series.getData().add(new XYChart.Data<>(4, random.nextInt(1000)));
            series.getData().add(new XYChart.Data<>(4.5, random.nextInt(1000)));
            series.getData().add(new XYChart.Data<>(5, random.nextInt(1000)));


            areaChart.getData().add(series);

        }



    }

    @FXML
    public void feadPieChart(MouseEvent mouseEvent) {
        // Create the pie chart
        pieChart.setTitle("Customers by Account Type");

        // Define the data for the chart
        PieChart.Data slice1 = new PieChart.Data("STUDENT_ACCOUNT", 213);
        PieChart.Data slice2 = new PieChart.Data("SAVINGS_ACCOUNT", 67);
        PieChart.Data slice3 = new PieChart.Data("JOINT_ACCOUNT", 36);
        PieChart.Data slice4 = new PieChart.Data("CHECKING_ACCOUNT", 152);
        PieChart.Data slice5 = new PieChart.Data("BUSINESS_ACCOUNT", 210);


        pieChart.getData().addAll(slice1, slice2, slice3, slice4, slice5);

        // Event handling for hover effect
        for (PieChart.Data data : pieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent event) -> {
                Platform.runLater(() -> {
                    data.getNode().setScaleX(1.1);
                    data.getNode().setScaleY(1.1);
                    data.getNode().setCursor(Cursor.HAND);
                    String sliceName = data.getName();
                    String sliceValue = String.valueOf(data.getPieValue());
                    percentageLabel.setVisible(true);
                    percentageLabel.setText(String.format("we have %s customers with %s account \n which is %.2f%%", sliceValue, sliceName, (data.getPieValue() / getTotal()) * 100));
                });
            });

            data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent event) -> {
                Platform.runLater(() -> {
                    data.getNode().setScaleX(1);
                    data.getNode().setScaleY(1);
                    data.getNode().setCursor(Cursor.DEFAULT);
                    percentageLabel.setVisible(false);
                });
            });
        }

        String text = execPromptGpt("You are a helpful assistant for bank admin." +
                "i will give you some diagram bank data and you should generate me an interpretation" +
                "this data is for the variation of number of customers oussna bank by account type" +
                "STUDENT_ACCOUNT: 213 clients,SAVINGS_ACCOUNT: 67 clients,JOINT_ACCOUNT: 36 clients,CHECKING_ACCOUNT: 152 clients,BUSINESS_ACCOUNT: 210 clients");
        System.out.println(text);
        pieChartLabel.setText(text);
        pieChartLabel.setEditable(false);
    }

    private double getTotal() {
        double total = 0;
        for (PieChart.Data data : pieChart.getData()) {
            total += data.getPieValue();
        }
        return total;
    }

    @FXML
    private void feadBarChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Categories");
        yAxis.setLabel("Values");

        // Clear existing data
        barChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Data");
        series.getData().add(new XYChart.Data<>("STUDENT_ACCOUNT", 213));
        series.getData().add(new XYChart.Data<>("SAVINGS_ACCOUNT", 67));
        series.getData().add(new XYChart.Data<>("JOINT_ACCOUNT", 36));
        series.getData().add(new XYChart.Data<>("CHECKING_ACCOUNT", 152));
        series.getData().add(new XYChart.Data<>("BUSINESS_ACCOUNT", 210));

        barChart.getData().add(series);

        Platform.runLater(() -> {
            series.getData().get(0).getNode().setStyle("-fx-bar-fill: rgba(0,0,255,0.9);");
            series.getData().get(1).getNode().setStyle("-fx-bar-fill: rgba(255,0,0,0.9);");
            series.getData().get(2).getNode().setStyle("-fx-bar-fill: rgba(0,128,0,0.9);");
            series.getData().get(3).getNode().setStyle("-fx-bar-fill: rgba(255,165,0,0.9);");
            series.getData().get(4).getNode().setStyle("-fx-bar-fill: rgba(128,0,128,0.9);");
        });

        for (XYChart.Data<String, Number> data : series.getData()) {
            StackPane stackPane = (StackPane) data.getNode();
            stackPane.setOnMouseEntered(event -> {
                stackPane.setCursor(Cursor.HAND);
                stackPane.setScaleX(1.1);
                stackPane.setScaleY(1.1);

            });
            stackPane.setOnMouseExited(event -> {
                stackPane.setCursor(Cursor.DEFAULT);
                stackPane.setScaleX(1);
                stackPane.setScaleY(1);
            });
        }

        String text = execPromptGpt("You are a helpful assistant for bank admin." +
                "i will give you some diagram bank data and you should generate me an interpretation" +
                "this data represents a bar chart illustrating the variation of money flow (in millions) by different account types." +
                "STUDENT_ACCOUNT: $213 million\n" +
                "SAVINGS_ACCOUNT: $67 million\n" +
                "JOINT_ACCOUNT: $36 million\n" +
                "CHECKING_ACCOUNT: $152 million\n" +
                "BUSINESS_ACCOUNT: $210 million");

        barChartLabel.setText(text);
        barChartLabel.setEditable(false);

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

}
