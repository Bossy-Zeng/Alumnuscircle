/**
 * Created by 曾博晖 on 2016/9/6.
 * 表示注册用户信息的类
 * 包括手机号、密码
 * 每次验证通过之后进行赋值，最后再进行传送
 * @date 2016年9月6日14:51:22
 * @verson 1
 */
package com.ac.alumnuscircle.auth.register;



public class RegisterUser {
    /**
     * "city": 城市 字符串
     "state": 省份 字符串
     “country”：国家 字符串

     "faculty":院 字符串
     "name": "陈雄辉", 姓名，2~20位数，可以是中文
     "major": 专业，字符串

     "company": "the SEU",公司名字，可以中文英文，空格，2~25
     "admission_year": 2014, 入学年份，4位整形
     "telephone": 15105861442,手机号

     "job": "student",工作，可中文，2~20位的中文或者英文，可以空格
     "gender": 1,性别 0 女，1 男
     "password": "XXXXX" 密码，客户端需要进行md5加密
     * */
    public static String telephone;
    public static String password;
    public static String name;
    public static String gender;
    public static String faculty;
    public static String major;
    public static String city;
    public static String state;
    public static String country;
    public static String admission_year;
    public static String job;

    public static String getIcon_url() {
        return icon_url;
    }

    public static void setIcon_url(String icon_url) {
        RegisterUser.icon_url = icon_url;
    }

    public static String company;
    public static String icon_url;
    public static String getMajor() {
        return major;
    }

    public static void setMajor(String major) {
        RegisterUser.major = major;
    }

    public static String getTelephone() {
        return telephone;
    }

    public static void setTelephone(String telephone) {
        RegisterUser.telephone = telephone;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        RegisterUser.password = password;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        RegisterUser.name = name;
    }

    public static String getGender() {
        return gender;
    }

    public static void setGender(String gender) {
        RegisterUser.gender = gender;
    }

    public static String getFaculty() {
        return faculty;
    }

    public static void setFaculty(String faculty) {
        RegisterUser.faculty = faculty;
    }

    public static String getCity() {
        return city;
    }

    public static void setCity(String city) {
        RegisterUser.city = city;
    }

    public static String getState() {
        return state;
    }

    public static void setState(String state) {
        RegisterUser.state = state;
    }

    public static String getCountry() {
        return country;
    }

    public static void setCountry(String country) {
        RegisterUser.country = country;
    }

    public static String getAdmission_year() {
        return admission_year;
    }

    public static void setAdmission_year(String admission_year) {
        RegisterUser.admission_year = admission_year;
    }

    public static String getJob() {
        return job;
    }

    public static void setJob(String job) {
        RegisterUser.job = job;
    }

    public static String getCompany() {
        return company;
    }

    public static void setCompany(String company) {
        RegisterUser.company = company;
    }





}
