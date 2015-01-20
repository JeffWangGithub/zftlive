package com.zftlive.android.sample.db.entity;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zftlive.android.base.BaseEntity;

/**
 * 首页[菜单]数据
 * 
 * @author 曾繁添
 */
@DatabaseTable(tableName = "User")
public class User extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1270221627651225756L;

	/**
	 * 序号
	 */
	@DatabaseField
	private String orderNo;
	
	/**
	 * 用户名
	 */
	@DatabaseField
	private String username;

	/**
	 * 电子邮箱
	 */
	@DatabaseField
	private String email;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
