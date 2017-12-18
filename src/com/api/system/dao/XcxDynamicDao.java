package com.api.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.api.platform.common.StringUtil;
import com.api.system.entity.Dynamic;
import com.api.system.entity.XcxComment;
import com.api.system.entity.XcxDynamic;

import spark.db.ConnectionPoolFactory;

public class XcxDynamicDao {
	public static Vector<Dynamic> getDynamicList(int uid){
		Vector<Dynamic> v = new Vector<Dynamic>();
		StringBuffer sql = new StringBuffer();
		sql.append("select dynamic.*,user.avatarUrl,user.nickName from xcx_dynamic dynamic,xcx_user user where user.id = dynamic.uid and dynamic.uid = "+uid+" order by dynamic.time asc ");
		
		System.out.println("我的动态Sql；="+sql.toString());
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try{
	    	
		 conn = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt = conn.prepareStatement(sql.toString());
	     rs = pstmt.executeQuery();
	     while (rs.next()) {
	    	 Dynamic info = new Dynamic();
	    	 info.setId(rs.getInt("id"));
	    	 info.setAvatarUrl(rs.getString("avatarUrl"));
	    	 info.setContent(rs.getString("content"));
	    	 info.setImg(rs.getString("img"));
	    	 info.setNickName(rs.getString("nickName"));
	    	 info.setTime(rs.getLong("time"));
	    	 info.setUid(rs.getInt("uid"));
	    	 info.setZan(rs.getInt("zan"));
	    	 
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

	public static int add(XcxDynamic xd) {
		XcxDao<?> xcxdao = new XcxDao<XcxDynamic>();
		return xcxdao.insertDynamic(xd);
	}
	
	/**
	 * 得到动态信息
	 * @param limit
	 * @param page_count
	 * @return
	 */
	public static Vector<Map<Object,Object>> getDynamic(int limit,int page_count){
		Vector<Map<Object,Object>> v = new Vector<Map<Object,Object>>();
		String strSql = "select dynamic.*,user.avatarUrl,user.nickName from xcx_dynamic dynamic,xcx_user user where user.id = dynamic.uid order by dynamic.time desc limit "+limit+","+page_count;
		System.out.println("动态相关Sq；="+strSql);
		Connection conn1 = null;
	    PreparedStatement pstmt1 = null;
	    ResultSet rs1 = null;
	    try{
	    	
		 conn1 = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt1 = conn1.prepareStatement(strSql);
	     rs1 = pstmt1.executeQuery();
	     
	     while (rs1.next()) {
	    	 Map<Object,Object> xc = new HashMap<Object,Object>();
	    	 xc.put("content", StringUtil.strnull(rs1.getString("content")));
	    	
	    	 xc.put("id",rs1.getInt("id"));
	    	 xc.put("time",rs1.getLong("time"));
	    	 xc.put("uid",rs1.getInt("uid"));
	    	 xc.put("zan",rs1.getInt("zan"));
	    	 xc.put("avatarUrl",StringUtil.strnull(rs1.getString("avatarUrl")));
	    	 xc.put("nickName",StringUtil.strnull(rs1.getString("nickName")));
	    	
	    	 v.add(xc);
	    	 
	     }rs1.last();
	    }catch(Exception e){
	    	
	    	e.printStackTrace();
	    }finally
	    {
		      try
		      {
		        rs1.close();
		      }
		      catch (Exception e) {
		       // e.printStackTrace();
		      }
		      try {
		        pstmt1.close();
		      }
		      catch (Exception e) {
		        e.printStackTrace();
		      }
		      try {
		    	  ConnectionPoolFactory.getInstance().close(conn1);
			      }
			      catch (Exception e) {
			        e.printStackTrace();
			      }
		      
		    }
			
		
		
		return v;
	}
	/**
	 * 得到动态信息
	 * @param limit
	 * @param page_count
	 * @return
	 */
	public static Vector<Map<Object,Object>> getDynamic(int uid,int limit,int page_count){
		Vector<Map<Object,Object>> v = new Vector<Map<Object,Object>>();
		String strSql = "select dynamic.*,user.avatarUrl,user.nickName from xcx_dynamic dynamic,xcx_user user where user.id = dynamic.uid and user.id = "+uid+" order by dynamic.time desc limit "+limit+","+page_count;
		System.out.println("动态相关Sq；="+strSql);
		Connection conn1 = null;
	    PreparedStatement pstmt1 = null;
	    ResultSet rs1 = null;
	    try{
	    	
		 conn1 = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt1 = conn1.prepareStatement(strSql);
	     rs1 = pstmt1.executeQuery();
	     
	     while (rs1.next()) {
	    	 Map<Object,Object> xc = new HashMap<Object,Object>();
	    	 xc.put("content", StringUtil.strnull(rs1.getString("content")));
	    	
	    	 xc.put("id",rs1.getInt("id"));
	    	 xc.put("time",rs1.getLong("time"));
	    	 xc.put("uid",rs1.getInt("uid"));
	    	 xc.put("zan",rs1.getInt("zan"));
	    	 xc.put("avatarUrl",StringUtil.strnull(rs1.getString("avatarUrl")));
	    	 xc.put("nickName",StringUtil.strnull(rs1.getString("nickName")));
	    	 
	    	 v.add(xc);
	    	 
	     }rs1.last();
	    }catch(Exception e){
	    	
	    	e.printStackTrace();
	    }finally
	    {
		      try
		      {
		        rs1.close();
		      }
		      catch (Exception e) {
		       // e.printStackTrace();
		      }
		      try {
		        pstmt1.close();
		      }
		      catch (Exception e) {
		        e.printStackTrace();
		      }
		      try {
		    	  ConnectionPoolFactory.getInstance().close(conn1);
			      }
			      catch (Exception e) {
			        e.printStackTrace();
			      }
		      
		    }
			
		
		
		return v;
	}
	/**
	 * 传入动态ID，相关用户信息
	 * @param cid
	 * @return
	 */
	public static XcxDynamic getUid(String cid) {
		XcxDynamic xc = new XcxDynamic();
		String strSql = "select id,uid,content,zan from xcx_dynamic where id = "+cid +" order by id desc";
		
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try{
	    	
		 conn = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt = conn.prepareStatement(strSql);
	     rs = pstmt.executeQuery();
	     while (rs.next()) {
	    	 xc.setId(rs.getInt("id")); 
	    	 xc.setUid(rs.getInt("uid"));
	    	 xc.setZan(rs.getInt("zan"));
	    	 xc.setContent(rs.getString("content"));
	    	 
	     }
	     rs.last();
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
		
		
		return xc;
	}
	
	
}
