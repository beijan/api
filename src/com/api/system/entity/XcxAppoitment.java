package com.api.system.entity;

import spark.annotation.Table;

@Table(name="xcx_appointment")
public class XcxAppoitment {
	private int id;
	private int uid;
	private int iid;
	private long time;
	private String name;
	private String phone;
	private int surplus;
	private int status;
	private String formId;
	public long getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public long getIid() {
		return iid;
	}
	public void setIid(int iid) {
		this.iid = iid;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getSurplus() {
		return surplus;
	}
	public void setSurplus(int surplus) {
		this.surplus = surplus;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}

	
}
