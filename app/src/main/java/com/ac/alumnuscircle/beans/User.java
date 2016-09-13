package com.ac.alumnuscircle.beans;

import java.io.Serializable;

/**
 * 
* @ClassName: User 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 白洋
* @date 2016年9月13日15:37:58
*
 */
public class User implements Serializable{
	private String id;
	private String name;
	private String headUrl;
	public User(String id, String name, String headUrl){
		this.id = id;
		this.name = name;
		this.headUrl = headUrl;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	@Override
	public String toString() {
		return "id = " + id
				+ "; name = " + name
				+ "; headUrl = " + headUrl;
	}
}
