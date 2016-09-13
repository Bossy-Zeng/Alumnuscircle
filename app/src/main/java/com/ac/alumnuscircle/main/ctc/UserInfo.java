/**
 * Created by 曾博晖 on 2016/9/7.
 * 代表着用户全部信息的一个映射类
 *
 * @author 曾博晖
 * @date 2016年9月7日20:02:59
 * @verson 1
 */
package com.ac.alumnuscircle.main.ctc;

/**
 * Created by 曾博晖 on 2016/9/7.
 * 代表着用户全部信息的一个映射类
 * @author 曾博晖
 * @date 2016年9月7日20:02:59
 * @verson 1
 */
public class UserInfo {

    /**
     * city : 漳州
     * major : 经济管理
     * name : 陈雄辉
     * icon_url : default
     * company : the seu
     * admission_year : 2014
     * register_time : 2016-09-05 17:15:59
     * job : student
     * state : 福建
     * instroduction : google China
     * faculty : 金融
     * country : 中国
     * job_list :
     */


    private String city;
    private String major;
    private String name;
    private String icon_url;
    private String company;
    private String admission_year;
    private String register_time;
    private String job;
    private String state;
    private String instroduction;
    private String faculty;
    private String country;
    private String job_list;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    private String user_id;

    public UserInfo() {
    }

    public UserInfo(String city, String major,
                    String name, String icon_url,
                    String company, String admission_year,
                    String register_time, String job,
                    String state, String instroduction,
                    String faculty, String country,
                    String job_list, String user_id) {
        this.user_id = user_id;
        this.city = city;
        this.major = major;
        this.name = name;
        this.icon_url = icon_url;
        this.company = company;
        this.admission_year = admission_year;
        this.register_time = register_time;
        this.job = job;
        this.state = state;
        this.instroduction = instroduction;
        this.faculty = faculty;
        this.country = country;
        this.job_list = job_list;
    }


    public String getCity() {

        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRegister_time() {
        return register_time;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getInstroduction() {
        return instroduction;
    }

    public void setInstroduction(String instroduction) {
        this.instroduction = instroduction;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getJob_list() {
        return job_list;
    }

    public void setJob_list(String job_list) {
        this.job_list = job_list;
    }
}
