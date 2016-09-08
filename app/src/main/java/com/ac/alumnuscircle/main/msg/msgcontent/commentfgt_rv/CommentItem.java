/**
 * Created by 曾博晖 on 2016/9/8.
 * @author 曾博晖
 * @date 2016年9月8日08:55:02
 * @verson 1
 * 功能：每个评论条目的类
 */

package com.ac.alumnuscircle.main.msg.msgcontent.commentfgt_rv;

/**
 * Created by 曾博晖 on 2016/9/8.
 * @author 曾博晖
 * @date 2016年9月8日08:55:02
 * @verson 1
 * 功能：每个评论条目的类
 */
public class CommentItem {
    private String comment_username;
    private String comment_type;
    private String comment_content;
    private String comment_circlename;
    private String comment_time;
    private String comment_headImgUrl;
    /**
     * 每个评论条目的构造函数
     * @param comment_username 评论人的名字
     * @param comment_type     评论的类型（公告、回复等）
     * @param comment_content  评论的内容
     * @param comment_circlename 评论来自的圈子名
     * @param comment_time       评论发出的时间
     * @param comment_headImgUrl 评论人头像的URL
     * */
    public CommentItem(String comment_username,
                       String comment_type,
                       String comment_content,
                       String comment_circlename,
                       String comment_time,
                       String comment_headImgUrl) {
        this.comment_username = comment_username;
        this.comment_type = comment_type;
        this.comment_content = comment_content;
        this.comment_circlename = comment_circlename;
        this.comment_time = comment_time;
        this.comment_headImgUrl = comment_headImgUrl;
    }

    public String getComment_username() {
        return comment_username;
    }

    public void setComment_username(String comment_username) {
        this.comment_username = comment_username;
    }

    public String getComment_type() {
        return comment_type;
    }

    public void setComment_type(String comment_type) {
        this.comment_type = comment_type;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public String getComment_circlename() {
        return comment_circlename;
    }

    public void setComment_circlename(String comment_circlename) {
        this.comment_circlename = comment_circlename;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }

    public String getComment_headImgUrl() {
        return comment_headImgUrl;
    }

    public void setComment_headImgUrl(String comment_headImgUrl) {
        this.comment_headImgUrl = comment_headImgUrl;
    }
}
