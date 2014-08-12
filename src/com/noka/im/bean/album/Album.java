/**
 * 
 */
package com.noka.im.bean.album;

import java.util.Date;

import cn.bmob.v3.BmobObject;

import com.noka.im.util.TimeUtil;

/**
 * @author shenbai
 *  相册，以日期做相册名。
 */
public class Album extends BmobObject {

	private static final long serialVersionUID = 8195634484518195803L;
	
	/**
	 * yyyy-MM-dd
	 */
	private String date = TimeUtil.dateToString(new Date(), TimeUtil.FORMAT_DATE);
	/**
	 * 描述
	 */
	private String desc = "";
	
	private String userId = "";
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
