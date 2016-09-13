package com.ac.alumnuscircle.beans;

import java.io.Serializable;

/**
 * 
* @ClassName: CommentItem 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 白洋
* @date 2016年9月13日15:36:52
*
 */
public class CommentItem implements Serializable{
    private String liked;
	private String id;
	private User user;
	private User toReplyUser;
	private String content;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getToReplyUser() {
		return toReplyUser;
	}
	public void setToReplyUser(User toReplyUser) {
		this.toReplyUser = toReplyUser;
	}
	public void setLiked(String liked)
	{
		this.liked = liked;
	}

	public String getLiked()
	{
		return this.liked;
	}

}
