/**
 * @author 曾博晖
 * @date 2016年8月28日
 * @verson 1
 * 人脉界面RecyclerView每一个条目
 * 各项数据成员类型为String
 * 即为
 * 头像Url地址，用户名，用户地址
 * 用户学院，用户年级，用户班级，
 * 以及用户的职业
 *
 *  /**
 * 班级条目删去
 * 需求更改
 * @verson 2
 * @date 2016年9月3日09:22:43
 * */
package com.ac.alumnuscircle.main.ctc.ctc_rv;





public class ContactFgtItem {
    private String headImgUrl;
    private String userName;
    private String userLocation;
    private String userFaculty;
    private String userGrade;
//    private String userClass;
    private String userJob;

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public void setUserFaculty(String userFaculty) {
        this.userFaculty = userFaculty;
    }

    public void setUserGrade(String userGrade) {
        this.userGrade = userGrade;
    }

    public void setUserJob(String userJob) {
        this.userJob = userJob;
    }

    /**
     * 重构构造函数，可以先不传入任何参数
     * 便于对数据的处理
     * 2016年9月7日22:59:11
     * 曾博晖
     * 创建
     * */
    public ContactFgtItem(){
        
    }

    /**
     * 构造函数，传入每一个Item的各项数据
     * 曾博晖
     * 各项数据成员类型为String
     * 依次代表
     * 头像Url地址，用户名，用户地址
     * 用户学院，用户年级，用户班级，
     * 以及用户的职业
     * @author 曾博晖
     * 2016年8月23日15:51:02
     * 创建*/
    /**
     * 班级条目删去
     * 需求更改
     * @verson 2
     * */
    public ContactFgtItem(String url,String name,String location,String faculty,
                        String grade,
                        String job){
        this.headImgUrl=url;
        this.userName=name;
        this.userLocation=location;
        this.userFaculty=faculty;
        this.userGrade=grade;
//        this.userClass=userClass;
        this.userJob=job;
    }
    public String getHeadImgUrl(){return headImgUrl;}
    public String getUserName(){return userName;}
    public String getUserLocation(){return userLocation;}
    public String getUserFaculty(){return userFaculty;}
    public String getUserGrade(){return userGrade;}
//    public String getUserClass(){return userClass;}
    public String getUserJob(){return userJob;}
}
