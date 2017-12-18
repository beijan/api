package com.api.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.api.platform.common.StringUtil;
import com.api.system.entity.Info;
import com.api.system.entity.XcxFav;

import spark.db.ConnectionPoolFactory;

/**
 * 收藏Table
 * @author lijian
 *
 */
public class XcxFavDao {
	/**
	 * 判断用户是否收藏
	 * @param uid
	 * @param iid
	 * @return
	 */
	public static boolean isfav(int uid,String iid){
		boolean b = false;
		String strSql = "select * from xcx_fav where uid = "+uid +" and iid = "+iid;
		System.out.println("收藏相关Sq；="+strSql);
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try{
	    	
		 conn = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt = conn.prepareStatement(strSql);
	     rs = pstmt.executeQuery();
	     while (rs.next()) {
	    	b = true;
	    	 
	     }
	    }catch(Exception e){
	    	
	    	e.printStackTrace();
	    }finally
	    {
		      try
		      {
		        rs.close();
		      }
		      catch (Exception e) {
		       // e.printStackTrace();
		      }
		      try {
		        pstmt.close();
		      }
		      catch (Exception e) {
		        e.printStackTrace();
		      }
		      try {
		    	  ConnectionPoolFactory.getInstance().close(conn);
			      }
			      catch (Exception e) {
			        e.printStackTrace();
			      }
		      
		    }
			
	   
		return b;
	}
	
	public static Vector<Info> getInfoList(int uid,int limit,int page_count){
		Vector<Info> v = new Vector<Info>();
		StringBuffer sql = new StringBuffer();
		sql.append("select info.*,fav.id as fad from xcx_info info,xcx_fav fav where info.id = fav.iid and info.uid = "+uid+" order by fav.time asc limit "+limit+","+ page_count);
		
		System.out.println("我的收藏Sql；="+sql.toString());
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try{
	    	
		 conn = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt = conn.prepareStatement(sql.toString());
	     rs = pstmt.executeQuery();
	     while (rs.next()) {
	    	 Info info = new Info();
	    	 info.setId(rs.getLong("id"));
	    	 
	    	 info.setDate(StringUtil.strnull(rs.getString("date")));
	    	 info.setTime(rs.getInt("time"));
	    	 info.setDeparture(StringUtil.strnull(rs.getString("departure")));
	    	 info.setDestination(StringUtil.strnull(rs.getString("destination")));
	    	 info.setGender(rs.getInt("gender"));
	    	 info.setName(StringUtil.strnull(rs.getString("name")));
	    	 info.setPhone(StringUtil.strnull(rs.getString("phone")));
	    	 info.setRemark(StringUtil.strnull(rs.getString("remark")));
	    	 info.setType(rs.getInt("type"));
	    	 info.setPrice(rs.getInt("price"));
	    	 info.setSee(rs.getInt("see"));
	    	 info.setStatus(rs.getInt("status"));
	    	 info.setSurplus(rs.getInt("surplus"));
	    	 info.setTime(rs.getInt("time"));
	    	 info.setVehicle(StringUtil.strnull(rs.getString("vehicle")));
	    	 info.setFad(StringUtil.strnull(rs.getString("fad")));
	    	 
	    	 v.add(info);
	    	 
	     }
	    }catch(Exception e){
	    	
	    	e.printStackTrace();
	    }finally
	    {
		      try
		      {
		        rs.close();
		      }
		      catch (Exception e) {
		       // e.printStackTrace();
		      }
		      try {
		        pstmt.close();
		      }
		      catch (Exception e) {
		        e.printStackTrace();
		      }
		      try {
		    	  ConnectionPoolFactory.getInstance().close(conn);
			      }
			      catch (Exception e) {
			        e.printStackTrace();
			      }
		      
		    }
			
		
		
		return v;
	}

	public static int add(XcxFav xf) {
		XcxDao<?> xcxdao = new XcxDao<XcxFav>();
		return xcxdao.insertFav(xf);
	}
	
	public static int del(int uid,int iid) {
		int i = 0;
		String strSql = "delete from xcx_fav where uid ="+uid+" and iid ="+iid;
		Connection conn = null;
	    PreparedStatement pstmt = null;
	   
	    try{
	    	 conn = ConnectionPoolFactory.getInstance().getConnection();
	    	 pstmt = conn.prepareStatement(strSql);
	    	 
	    	 
	    	 
	    	 i = pstmt.executeUpdate();
	    	
	    	 
	    }catch(Exception e){
	    	
	    	e.printStackTrace();
	    }finally
	    {
		      try {
		        pstmt.close();
		      }
		      catch (Exception e) {
		        e.printStackTrace();
		      }
		      try {
		    	  ConnectionPoolFactory.getInstance().close(conn);
			      }
			      catch (Exception e) {
			        e.printStackTrace();
			      }
		      
		    }
			
		
		return i;
		
	}
	

}
