module com.esprit.financialhub_desktop {
    exports com.esprit.financialhub_desktop.entities;

    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires bytes;
    requires bcrypt;
    requires java.net.http;
    requires org.json;
    requires javax.mail.api;
    requires okhttp;
    requires com.fasterxml.jackson.databind;
    requires org.apache.poi.poi;
    requires org.apache.pdfbox;

    opens com.esprit.financialhub_desktop to javafx.fxml;
    exports com.esprit.financialhub_desktop;
    exports com.esprit.financialhub_desktop.Controller;
    opens com.esprit.financialhub_desktop.Controller to javafx.fxml;
}