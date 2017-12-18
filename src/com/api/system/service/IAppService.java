package com.api.system.service;


import com.api.system.entity.Info;
import com.api.system.entity.User;

import java.util.List;
import java.util.Map;

import spark.page.easyui.MenuBean;
import spark.page.easyui.Page;
import spark.page.easyui.TreeBean;

/**
 * 服务类的接口定义
 * @author freedom.xie
 *
 */
public interface IAppService {
	public int checkLogin(String account,String pwd);
	public int updateLogin(String account,String pwd1,String pwd2,String pwd3);
	public Page getAllUsers(Page p);
	public int updateUser(User u);
	public int delUser(long id);
	public List<TreeBean> initLeftMenu(long userId);
	public List<MenuBean> initTopMenu(long userId);
	
	public List<Map<String, Object>> getUsers();
	
	
	
	public Info getInfo(String id);
	
	
	
}
