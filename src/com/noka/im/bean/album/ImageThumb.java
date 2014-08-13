/**
 * 
 */
package com.noka.im.bean.album;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

/**
 * @author shenbai
 *
 */
@Table(name = "imageThumb")
public class ImageThumb  {
	
	@Id
	private int id;
	private String img;
	private String thumb;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
}
