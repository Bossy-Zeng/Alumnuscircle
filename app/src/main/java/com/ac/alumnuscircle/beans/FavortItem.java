package com.ac.alumnuscircle.beans;

import java.io.Serializable;

/**
 * 
* @ClassName: FavortItem 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author  白洋
* @date 2016年9月13日15:37:14
*
 */
public class FavortItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private User user;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
