package com.example.red.magazine.adaptermodels;

/**
 * Created by Meseret on 11/12/2017.
 */

public class OuterVacancyAdapterModel {
    private String id;
    private String job_title;
    private String name;
    private String salary;
    private String employment_type;
    private String qualifications;
    private String years_exp;
    private String description;
    private String deadline;

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
