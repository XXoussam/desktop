package com.esprit.financialhub_desktop.services;

import com.esprit.financialhub_desktop.entities.Agent;

import java.util.List;

public interface AgentService {
    List<Agent> getAgents();

    // update email and password of an agent
    void updateAgent(Agent agent, String email, String password);

    void deleteAgent(Agent agent);

    void addAgent(Agent agent);

    void depositSalary(String agentType, double amount);

    void updatePassword(String password,String email);
}
