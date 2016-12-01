/**
 * Created by 曾博晖 on 2016/9/7.
 * @date 2016年9月7日14:14:27
 * @verson 1
 * @author 曾博晖
 * 功能：Notify碎片之中每个Item的类
 */

package com.ac.alumnuscircle.main.msg.msgcontent.notifyfgt_rv;


public class NotifyItem {

   public NotifyItem(){

   }


    public String getNotify_content() {
        return notify_content;
    }

    public void setNotify_content(String notify_content) {
        this.notify_content = notify_content;
    }

    public String getNotify_type() {
        return notify_type;
    }

    public void setNotify_type(String notify_type) {
        this.notify_type = notify_type;
    }

    public String getNotify_Name() {
        return notify_Name;
    }

    public void setNotify_Name(String notify_Name) {
        this.notify_Name = notify_Name;
    }

    public String getNotify_time() {
        return notify_time;
    }

    public void setNotify_time(String notify_time) {
        this.notify_time = notify_time;
    }

    public String getNotify_headImgUrl() {
        return notify_headImgUrl;
    }

    public void setNotify_headImgUrl(String notify_headImgUrl) {
        this.notify_headImgUrl = notify_headImgUrl;
    }
    private String notify_type;
    private String notify_content;
    private String notify_Name;
    private String notify_time;
    private String notify_headImgUrl;

    public String getNotify_applyid() {
        return notify_applyid;
    }

    public void setNotify_applyid(String notify_applyid) {
        this.notify_applyid = notify_applyid;
    }

    private String notify_applyid;
    /**
     * @author 曾博晖
     * @param notify_headImgUrl 通知来源的头像
     * @param notify_type 通知的类型，如系统通知和圈子通知等
     * @param notify_content 通知的内容
     * @param notify_Name  发送圈子的来源，如系统或是某个圈子
     * @param notify_time  通知发出的时间
     * */
    public NotifyItem(
                     String notify_headImgUrl,
                     String notify_type,
                     String notify_content,
                     String notify_Name,
                     String notify_time) {
        this.notify_type = notify_type;
        this.notify_content = notify_content;
        this.notify_Name = notify_Name;
        this.notify_time = notify_time;
        this.notify_headImgUrl = notify_headImgUrl;
    }
}
