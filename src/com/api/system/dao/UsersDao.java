package com.api.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.api.platform.common.StringUtil;

import spark.db.ConnectionPoolFactory;

public class UsersDao {
	
	/**
	 * 登录后，写入相关用户信息
	 * @param name
	 * @param num
	 * @param ip
	 * @param time
	 * @return
	 */
	public static int updateUsers(String name,String ip,Long time){
		int i = 0;
		String strSql = "update Users set num=num+1,ip='"+ip+"',time="+time+"  where account='"+name+"'";
		System.out.println("更新数据sql::"+strSql);
		
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
	
	public static Map<Object,Object> getUserInfo(String account){
		Map<Object,Object> m = new HashMap<Object,Object>();
		String strSql = "select * from Users where account = '"+account+"'";
		Connection conn1 = null;
	    PreparedStatement pstmt1 = null;
	    ResultSet rs1 = null;
	    try{
	    	
		 conn1 = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt1 = conn1.prepareStatement(strSql);
	     rs1 = pstmt1.executeQuery();
	     
	     while (rs1.next()) {
	    	m.put("num", rs1.getInt("num"));
	    	m.put("ip", rs1.getString("ip"));
	    	m.put("time", StringUtil.TimeStamp2Date(rs1.getLong("time"),"yyyy-MM-dd HH:mm:ss"));
	    	 
	    	 
	     }
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
			
		
		return m;
	}

}
