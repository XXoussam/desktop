package com.esprit.financialhub_desktop.entities;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AgentSalDTO {
    private final StringProperty agentType;
    private final StringProperty description;
    private final DoubleProperty amount;

    public AgentSalDTO(String agentType, String description, double amount) {
        this.agentType = new SimpleStringProperty(agentType);
        this.description = new SimpleStringProperty(description);
        this.amount = new SimpleDoubleProperty(amount);
    }

    // Getters and setters for agentType, description, and amount properties
    public String getAgentType() {
        return agentType.get();
    }

    public StringProperty agentTypeProperty() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType.set(agentType);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public double getAmount() {
        return amount.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }
}
