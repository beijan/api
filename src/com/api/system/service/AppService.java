package com.api.system.service;


import com.api.platform.security.MD5;
import com.api.platform.web.InfoBean;
import com.api.system.dao.AppDao;
import com.api.system.entity.Info;
import com.api.system.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;








import spark.annotation.Auto;
import spark.page.easyui.MenuBean;
import spark.page.easyui.Page;
import spark.page.easyui.TreeBean;
import javolution.util.FastTable;

/**
 * 服务实现类
 * @author freedo.xie
 *
 */
public class AppService implements IAppService {
	//推荐以自动注入的方式创建对象
    @Auto(name = AppDao.class)
	private AppDao<?> dao;
    
	
    /**
     * 检查登陆,自行实现
     */
	@Override
	public int checkLogin(String account,String pwd) {
		InfoBean info = new InfoBean();
		String sql = "select userName,password from Users where account = '"+account+"' and sign = 1";
		System.out.println(sql);
		Map m = dao.findMapBysql(sql, new Object[]{});
		
		String pwdmd5 = MD5.toMd5(pwd);
		System.out.println("数据库值："+m.get("password"));
		System.out.println("输入值："+pwdmd5);
		if(m == null)
			return 2;
		if ( !pwdmd5.equals(m.get("password"))) {
			
			return 3;
		} 
		return 1;
		
	}
	
	public int updateLogin(String account,String pwd1,String pwd2,String pwd3) {
		
		String pwdmd5 = MD5.toMd5(pwd1);
		String sql = "select userName,password from Users where account = '"+account+"' and password = '"+pwdmd5+"'";
		Map m = dao.findMapBysql(sql, new Object[]{});
		if(m == null)
			return 2;
		if(!pwd2.equals(pwd3))
			return 3;
		String pwdmd51 = MD5.toMd5(pwd3);
		System.out.println("修改后的新密码："+pwd3+"===="+pwdmd51);
		 sql = "update Users set password='"+pwdmd51+"' where account='"+account+"'";
		 dao.update(sql, null);
		return 1;
		
	}
    /**
     * 分页查询数据
     */
    @Override
	public Page getAllUsers(Page p) {
    	return dao.getAllUser(User.class,p);
	}

    /**
     * 插入或者修改用户信息
     */
    @Override
	public int updateUser(User u) {
		if (u.getId() == 0) {
			return dao.insert(u);
		} else {
			return dao.update(u);
		}
		
	}
    
   /**
    * 删除用户
    */
	@Override
	public int delUser(long id) {
		return this.dao.delete(User.class,id);
	}

	/**
	 * 初始化左侧导航菜单实例
	 */
	@Override
	public List<TreeBean> initLeftMenu(long userId) {
		 List<TreeBean> menus = new FastTable<TreeBean>();
    	 TreeBean tree1 = new TreeBean();
    	 tree1.setId("4");
    	 tree1.setState("open");
    	 tree1.setText("权限管理");
    	 tree1.setIconCls("icon-inner-1");
    	 Map<String,String>attr1 = new HashMap();
    	 attr1.put("url", "");
    	 tree1.setAttributes(attr1);
    	 
    	 TreeBean tree2 = new TreeBean();
    	 tree2.setId("2");
    	 tree2.setState("open");
    	 tree2.setText("律师查询");
    	 tree2.setIconCls("icon-inner-3");
    	 Map<String,String>attr2 = new HashMap();
    	 attr2.put("url", "base.html");
    	 
    	 tree2.setAttributes(attr2);
    	 
    	 TreeBean tree3 = new TreeBean();
    	 tree3.setId("3");
    	 tree3.setState("open");
    	 tree3.setText("律所查询");
    	 tree3.setIconCls("icon-inner-4");
    	 Map<String,String>attr3 = new HashMap();
    	 attr3.put("url", "lawyerfirm.html");
    	 tree3.setAttributes(attr3);
    	 
    	 TreeBean tree4 = new TreeBean();
    	 tree4.setId("4");
    	 tree4.setState("open");
    	 tree4.setText("律师查询高级");
    	 tree4.setIconCls("icon-inner-3");
    	 Map<String,String>attr4 = new HashMap();
    	 attr4.put("url", "basegrade.jsp");
    	 tree4.setAttributes(attr4);
    	 
    	 TreeBean tree5 = new TreeBean();
    	 tree5.setId("5");
    	 tree5.setState("open");
    	 tree5.setText("律所高级查询");
    	 tree5.setIconCls("icon-inner-4");
    	 Map<String,String>attr5 = new HashMap();
    	 attr5.put("url", "lawyerfirmgrade.jsp");
    	 tree5.setAttributes(attr5);
    	 if(userId == 1){
    		 menus.add(tree2);
        	 menus.add(tree3);
        	 menus.add(tree4);
        	 menus.add(tree5);
    	 }else{
    		 menus.add(tree1);
    	 }
    	 System.out.println(userId);
    	
    	 return menus;
	}

	/**
	 * 初始化顶部导航菜单实例
	 */
	@Override
	public List<MenuBean> initTopMenu(long userId) {
		 List<MenuBean> menus = new FastTable<MenuBean>();
		 MenuBean sysMenu = new MenuBean();
		 sysMenu.setName("系统功能");
		 sysMenu.setIcon("icon-inner-5");
		 sysMenu.setId(1);
		 
		
		 
		 /*
		 MenuBean cdMenu = new MenuBean();
		 cdMenu.setName("菜单管理");
		 cdMenu.setIcon("icon-inner-6");
		 MenuBean b2 = new MenuBean();
		 b2.setName("菜单管理1");
		 b2.setIcon("icon-inner-5");
		 b2.setUrl("query.jsp");
		 cdMenu.getSubMenu().add(b2);
		 */
		 
		 MenuBean persionMenu = new MenuBean();
		 persionMenu.setName("系统管理");
		 persionMenu.setIcon("icon-inner-7");
		 persionMenu.setId(2);
		
		
		 
		 /*
		 MenuBean reportMenu = new MenuBean();
		 reportMenu.setName("报表管理");
		 reportMenu.setIcon("icon-inner-8");
		 MenuBean b4 = new MenuBean();
		 b4.setName("报表管理1");
		 b4.setIcon("icon-inner-5");
		 b4.setUrl("query.jsp");
		 reportMenu.getSubMenu().add(b4);
		 */
		 menus.add(sysMenu);
		 //menus.add(cdMenu);
		 menus.add(persionMenu);
		 //menus.add(reportMenu);
		 return menus;
	}
	
	/**
	 * 接口测试，得到用户数组
	 */
	public List<Map<String, Object>> getUsers(){
		String sql =  " select * from Users ";
		
		System.out.println("查询SQL " + sql);
		
		return dao.getListObject(sql);
		
	}
	
	
	/**
	 * 小程序用，拼车详细信息
	 */
	public  Info getInfo(String id){
		
		String sql = "select info.*,user.avatarUrl,user.openid from xcx_info info,xcx_user user where user.id = info.uid and info.id = "+id;// where departure like '%"+start+"%' and destination";

		//拼装SQL
		
		System.out.println("查询详细信息SQL:"+sql);
		return (Info)dao.findEntityBySql(sql, Info.class,new Object[]{});
	

	}
	
	
	
	
}
