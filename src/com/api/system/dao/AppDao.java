package com.api.system.dao;



import java.util.List;
import java.util.Map;
import com.api.system.entity.User;
import spark.db.ObjectDao;
import spark.page.easyui.Page;

/**
 * 处理数据的DAO对象,例子中以对象化的方式进行处理,对于复杂查询,的以SQL的方式处理
 * @author freedom.xie
 */
public class AppDao<T> extends ObjectDao<T> {
	
	public Page getAllUser(Class<?>c,Page p) {
		
		return (Page) super.queryPagination(c, p);
	}
	
	public int insert(User u) {
		return super.insert(u);
	}
	
	public int update(User u) {
		return super.update(u);
	}
	
	public int delete(Class<?>c,long id) {
		return super.delete(c,id);
	}
	
	public Map<String, Object> getObject(String sql){

		return super.findMapBysql(sql,  new Object[]{});
	}
	
	public List<Map<String, Object>> getListObject(String sql){

		return super.findMapListBysql(sql,  new Object[]{});
	}
}
