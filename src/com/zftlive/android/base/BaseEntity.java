package com.zftlive.android.base;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;

/**
 * 实体基类
 * @author 曾繁添
 * @version 1.0
 *
 */
public abstract class BaseEntity implements Serializable {

	/**
	 */
	private static final long serialVersionUID = 6337104618534280060L;
	
	/**
	 * 主键ID
	 */
	@DatabaseField(id=true)
	private String id;
	
	/**
	 * 备注
	 */
	@DatabaseField
	private String remark;
	
	/**
	 * 版本号
	 */
	@DatabaseField(defaultValue="1")
	private Integer version;
	
	/**
	 * 是否有效
	 */
	@DatabaseField(defaultValue="true")
	private Boolean valid;

}
