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
	protected String id;
	
	/**
	 * 备注
	 */
	@DatabaseField
	protected String remark;
	
	/**
	 * 版本号
	 */
	@DatabaseField(defaultValue="1")
	protected Integer version;
	
	/**
	 * 是否有效
	 */
	@DatabaseField(defaultValue="true")
	protected Boolean valid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

}
