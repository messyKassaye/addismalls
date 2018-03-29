package com.example.red.magazine.model;

/**
 * Created by Meseret on 11/12/2017.
 */

public class OuterVacancyModel {
    private String id;
    private String job_title;
    private String name;
    private String salary;
    private String employment_type;
    private String qualifications;
    private String years_exp;
    private String description;
    private String deadline;

    public OuterVacancyModel(String id, String job_title, String name, String salary, String employment_type, String qualifications, String years_exp, String description, String deadline) {
        this.id = id;
        this.job_title = job_title;
        this.name = name;
        this.salary = salary;
        this.employment_type = employment_type;
        this.qualifications = qualifications;
        this.years_exp = years_exp;
        this.description = description;
        this.deadline = deadline;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getEmployment_type() {
        return employment_type;
    }

    public void setEmployment_type(String employment_type) {
        this.employment_type = employment_type;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public String getYears_exp() {
        return years_exp;
    }

    public void setYears_exp(String years_exp) {
        this.years_exp = years_exp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
