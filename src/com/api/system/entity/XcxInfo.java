package com.api.system.entity;

import spark.annotation.Table;


@Table(name="xcx_info")
public class XcxInfo {
	private long id;
	private String date;
	private long time;
	private String departure;
	private String destination;
	private int gender;
	private String name;
	private String phone;
	private String remark;
	
	private String vehicle;
	private long surplus;
	private long price;
	
	private long type;
	  private long uid;
	  private long status;
	  private long see;
	  private long addtime;
	  
	  public String formId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getDeparture() {
		return departure;
	}
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getVehicle() {
		return vehicle;
	}
	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}
	public long getSurplus() {
		return surplus;
	}
	public void setSurplus(long surplus) {
		this.surplus = surplus;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public long getType() {
		return type;
	}
	public void setType(long type) {
		this.type = type;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public long getSee() {
		return see;
	}
	public void setSee(long see) {
		this.see = see;
	}
	public long getAddtime() {
		return addtime;
	}
	public void setAddtime(long addtime) {
		this.addtime = addtime;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	
	
	

}
