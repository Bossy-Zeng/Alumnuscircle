/**
 * Created by Administrator on 2016/9/4.
 * 代表用户的详细信息的类
 * 2016年9月4日21:44:55
 * 曾博晖创建
 */
package com.ac.alumnuscircle.auth.loginJson;



public class Data {
    /**
     * {"city": 321, "faculty_id": 71, "gender": 0,
     * "uid": 14, "tags": "{}", "icon_url": "default",
     * "company": "another company", "admission_year": 2014,
     * "my_circle_list": "{}", "job_list_level": 0, "job": "worker110",
     * "protect_contact_list": "{}", "detail_id": 14,
     * "company_publicity_level": 0,
     * "public_contact_list": "{}", "major_id": 1,
     * "adlevel": 0, "instroduction": "{}",
     * "job_list": "{}",
     * "name": "\u9648\u96c4\u8f89"}
     * */
    private String city;

    public String getFaculty_id() {
        return faculty_id;
    }

    public void setFaculty_id(String faculty_id) {
        this.faculty_id = faculty_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAdmission_year() {
        return admission_year;
    }

    public void setAdmission_year(String admission_year) {
        this.admission_year = admission_year;
    }

    public String getJob_list_level() {
        return job_list_level;
    }

    public void setJob_list_level(String job_list_level) {
        this.job_list_level = job_list_level;
    }

    public String getMy_circle_list() {
        return my_circle_list;
    }

    public void setMy_circle_list(String my_circle_list) {
        this.my_circle_list = my_circle_list;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getProtect_contact_list() {
        return protect_contact_list;
    }

    public void setProtect_contact_list(String protect_contact_list) {
        this.protect_contact_list = protect_contact_list;
    }

    public String getMajor_id() {
        return major_id;
    }

    public void setMajor_id(String major_id) {
        this.major_id = major_id;
    }

    public String getAdlevel() {
        return adlevel;
    }

    public void setAdlevel(String adlevel) {
        this.adlevel = adlevel;
    }

    public String getInstroduction() {
        return instroduction;
    }

    public void setInstroduction(String instroduction) {
        this.instroduction = instroduction;
    }

    public String getJob_list() {
        return job_list;
    }

    public void setJob_list(String job_list) {
        this.job_list = job_list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String faculty_id;
    private String gender;
    private String uid;
    private String tags;
    private String icon_url;
    private String company;
    private String admission_year;

    private String job_list_level;
    private String my_circle_list;
    private String job;

    public Data(String city, String faculty_id,
                String gender, String uid,
                String tags, String icon_url,
                String company, String admission_year,
                String job_list_level, String my_circle_list,
                String job, String protect_contact_list,
                String major_id, String adlevel,
                String instroduction, String job_list,
                String name) {
        this.city = city;
        this.faculty_id = faculty_id;
        this.gender = gender;
        this.uid = uid;
        this.tags = tags;
        this.icon_url = icon_url;
        this.company = company;
        this.admission_year = admission_year;
        this.job_list_level = job_list_level;
        this.my_circle_list = my_circle_list;
        this.job = job;
        this.protect_contact_list = protect_contact_list;
        this.major_id = major_id;
        this.adlevel = adlevel;
        this.instroduction = instroduction;
        this.job_list = job_list;
        this.name = name;
    }

    private String protect_contact_list;
    private String major_id;
    private String adlevel;
    private String instroduction;
    private String job_list;
    private String name;



}
