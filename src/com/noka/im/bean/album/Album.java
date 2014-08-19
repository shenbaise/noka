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
	 * yyyy-MM-dd HH:mm
	 */
	private String date = TimeUtil.dateToString(new Date(), TimeUtil.YYYY_MM_DD_HH_MM);
	/**
	 * 描述
	 */
	private String desc = "";
	
	private String username = "";
	
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
