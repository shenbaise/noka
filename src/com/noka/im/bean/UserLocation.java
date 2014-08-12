/**
 * 
 */
package com.noka.im.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * @author shenbai
 * 用户经常所在的位置
 */
public class UserLocation extends BmobObject {

	private static final long serialVersionUID = 1155756843555038483L;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 位置信息
	 */
	private BmobGeoPoint location;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public BmobGeoPoint getLocation() {
		return location;
	}
	public void setLocation(BmobGeoPoint location) {
		this.location = location;
	}
}
