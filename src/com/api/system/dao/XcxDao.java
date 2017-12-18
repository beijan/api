package com.api.system.dao;



import com.api.system.entity.XcxAppoitment;
import com.api.system.entity.XcxDynamic;
import com.api.system.entity.XcxFav;
import com.api.system.entity.XcxInfo;
import com.api.system.entity.XcxMsg;
import com.api.system.entity.XcxUser;
import com.api.system.entity.XcxZan;

import spark.db.ObjectDao;

public class XcxDao <T> extends ObjectDao<T> {
	public int insert(XcxUser u) {
		return super.insert(u);
	}
	
	public int insertInfo(XcxInfo u) {
		return super.insert(u);
	}
	
	public int updateUserInfo(XcxUser u) {
		return super.update(u);
	}
	
	public int insertMsg(XcxMsg u) {
		return super.insert(u);
	}
	
	public int insertAppoitmen(XcxAppoitment xa){
		return super.insert(xa);
	}
	
	public int insertZan(XcxZan xz){
		return super.insert(xz);
	}
	
	public int insertFav(XcxFav xz){
		return super.insert(xz);
	}

	public int insertDynamic(XcxDynamic xd) {
		return super.insert(xd);
	}


}
