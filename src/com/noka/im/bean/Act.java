/**
 * 
 */
package com.noka.im.bean;

import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * @author zzq
 * 活动（约会）的基类
 */
public abstract class Act extends BmobObject{
	private static final long serialVersionUID = -3196282030503026681L;
	/**
	 * 约会类型
	 */
	private ActType type = ActType.movie;
	/**
	 * 发起人
	 */
	private User sponsor;
	/**
	 * 发布时间
	 */
	private BmobDate postTime = new BmobDate(new Date());
	/**
	 * 活动时间
	 */
	private BmobDate actTime;
	/**
	 * 约会活动状态
	 */
	private ActStatus status = ActStatus.ing;
	/**
	 * 活动地址
	 */
	private String address;
	
	
	public ActType getType() {
		return type;
	}
	public void setType(ActType type) {
		this.type = type;
	}
	public User getSponsor() {
		return sponsor;
	}
	public void setSponsor(User sponsor) {
		this.sponsor = sponsor;
	}
	public BmobDate getPostTime() {
		return postTime;
	}
	public void setPostTime(BmobDate postTime) {
		this.postTime = postTime;
	}
	public BmobDate getActTime() {
		return actTime;
	}
	public void setActTime(BmobDate actTime) {
		this.actTime = actTime;
	}
	public ActStatus getStatus() {
		return status;
	}
	public void setStatus(ActStatus status) {
		this.status = status;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
