/**
 * Created by 曾博晖 on 2016/9/8.
 * 个人信息的映射类
 * @date 2016年9月8日22:08:59
 * @verson 1
 * @author 曾博晖
 */

package com.ac.alumnuscircle.auth;


public class MyInfo  {

    public static MyInfo myInfo=new MyInfo();
    /**
     * last_update_time : 2016-08-28 17:11:37
     * major : 1
     * uid : 14
     * admission_year : 2014
     * instroduction : {}
     * create_circle_list : _
     * city : 123
     * icon_url : default
     * my_circle_list : _
     * state :
     * protect_contact_list : {}
     * detail_id : 14
     * company_publicity_level : 0
     * public_contact_list : {}
     * admin_circle_list : _
     * tags : {}
     * company : google China
     * job : worker
     * faculty : 71
     * name : 陈雄辉
     * gender : 0
     * job_list_level : 0
     * country :
     * adlevel : 0
     * job_list : {}
     */
    private String last_update_time;
    private String major;
    private String uid;
    private String admission_year;
    private String instroduction;
    private String create_circle_list;
    private String city;
    private String icon_url;
    private String my_circle_list;
    private String state;
    private String protect_contact_list;
    private String detail_id;
    private String company_publicity_level;
    private String public_contact_list;
    private String admin_circle_list;
    private String tags;
    private String company;
    private MyInfo(){}
    public MyInfo(String last_update_time,
                  String major, String uid,
                  String admission_year, String instroduction,
                  String create_circle_list, String city,
                  String icon_url, String my_circle_list,
                  String state, String protect_contact_list,
                  String detail_id, String company_publicity_level,
                  String public_contact_list, String admin_circle_list,
                  String tags, String company, String job, String faculty,
                  String name, String gender, String job_list_level,
                  String country, String adlevel, String job_list) {
        this.last_update_time = last_update_time;
        this.major = major;
        this.uid = uid;
        this.admission_year = admission_year;
        this.instroduction = instroduction;
        this.create_circle_list = create_circle_list;
        this.city = city;
        this.icon_url = icon_url;
        this.my_circle_list = my_circle_list;
        this.state = state;
        this.protect_contact_list = protect_contact_list;
        this.detail_id = detail_id;
        this.company_publicity_level = company_publicity_level;
        this.public_contact_list = public_contact_list;
        this.admin_circle_list = admin_circle_list;
        this.tags = tags;
        this.company = company;
        this.job = job;
        this.faculty = faculty;
        this.name = name;
        this.gender = gender;
        this.job_list_level = job_list_level;
        this.country = country;
        this.adlevel = adlevel;
        this.job_list = job_list;
    }

    private String job;
    private String faculty;
    private String name;
    private String gender;
    private String job_list_level;
    private String country;
    private String adlevel;
    private String job_list;

    public String getLast_update_time() {
        return last_update_time;
    }

    public void setLast_update_time(String last_update_time) {
        this.last_update_time = last_update_time;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAdmission_year() {
        return admission_year;
    }

    public void setAdmission_year(String admission_year) {
        this.admission_year = admission_year;
    }

    public String getInstroduction() {
        return instroduction;
    }

    public void setInstroduction(String instroduction) {
        this.instroduction = instroduction;
    }

    public String getCreate_circle_list() {
        return create_circle_list;
    }

    public void setCreate_circle_list(String create_circle_list) {
        this.create_circle_list = create_circle_list;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getMy_circle_list() {
        return my_circle_list;
    }

    public void setMy_circle_list(String my_circle_list) {
        this.my_circle_list = my_circle_list;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProtect_contact_list() {
        return protect_contact_list;
    }

    public void setProtect_contact_list(String protect_contact_list) {
        this.protect_contact_list = protect_contact_list;
    }

    public String getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(String detail_id) {
        this.detail_id = detail_id;
    }

    public String getCompany_publicity_level() {
        return company_publicity_level;
    }

    public void setCompany_publicity_level(String company_publicity_level) {
        this.company_publicity_level = company_publicity_level;
    }

    public String getPublic_contact_list() {
        return public_contact_list;
    }

    public void setPublic_contact_list(String public_contact_list) {
        this.public_contact_list = public_contact_list;
    }

    public String getAdmin_circle_list() {
        return admin_circle_list;
    }

    public void setAdmin_circle_list(String admin_circle_list) {
        this.admin_circle_list = admin_circle_list;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getJob_list_level() {
        return job_list_level;
    }

    public void setJob_list_level(String job_list_level) {
        this.job_list_level = job_list_level;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAdlevel() {
        return adlevel;
    }

    public void setAdlevel(String adlevel) {
        this.adlevel = adlevel;
    }

    public String getJob_list() {
        return job_list;
    }

    public void setJob_list(String job_list) {
        this.job_list = job_list;
    }
}
