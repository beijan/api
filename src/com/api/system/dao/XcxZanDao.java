package com.api.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.api.system.entity.XcxAppoitment;
import com.api.system.entity.XcxZan;

import spark.db.ConnectionPoolFactory;

public class XcxZanDao {
	public static int isZan(int uid,String cid){
		int i = 0;
		String strSql = "select id from xcx_zan where uid = "+uid +" and cid = "+cid;
		
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try{
	    	
		 conn = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt = conn.prepareStatement(strSql);
	     rs = pstmt.executeQuery();
	     if (rs.next()) {
	    	i = rs.getInt("id");
	    	 
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
		
		
		return i;
	}
	
	public static int addZan(XcxZan xz){
		XcxDao<?> xcxdao = new XcxDao<XcxZan>();
		return xcxdao.insertZan(xz);
	}

}
