package com.example.red.magazine.agents.companyAgent.models;

/**
 * Created by Meseret on 11/7/2017.
 */

public class AgentModel {
    private String first_name;
    private String last_name;
    private String role_type;

    public AgentModel(String first_name, String last_name, String role_type) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.role_type = role_type;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getRole_type() {
        return role_type;
    }

    public void setRole_type(String role_type) {
        this.role_type = role_type;
    }
}
