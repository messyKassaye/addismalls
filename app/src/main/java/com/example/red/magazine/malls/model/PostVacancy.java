package com.example.red.magazine.malls.model;

/**
 * Created by ezedin on 10/30/17.
 */

public class PostVacancy {
    private String job_title;
    private String cate_name;
    private String salary;
    private String employment_type;
    private String experience;
    private String educational_level;
    private String how_to_apply;
    private String deadline;


    public PostVacancy(String job_title, String cate_name, String salary, String employment_type, String educational_level, String experience, String how_to_apply, String deadline) {
        this.job_title = job_title;
        this.cate_name = cate_name;
        this.salary = salary;
        this.employment_type = employment_type;
        this.educational_level = educational_level;
        this.experience = experience;
        this.how_to_apply = how_to_apply;
        this.deadline = deadline;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getEducational_level() {
        return educational_level;
    }

    public void setEducational_level(String educational_level) {
        this.educational_level = educational_level;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getHow_to_apply() {
        return how_to_apply;
    }

    public void setHow_to_apply(String how_to_apply) {
        this.how_to_apply = how_to_apply;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getEmployment_type() {
        return employment_type;
    }

    public void setEmployment_type(String employment_type) {
        this.employment_type = employment_type;
    }
}
