package com.api.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.api.platform.common.StringUtil;
import com.api.system.entity.XcxImport;

import spark.db.ConnectionPoolFactory;

public class XcxImportDao {

	public static XcxImport getImport(String purePhoneNumber) {
String strSql = "select * from xcx_import where phone = '"+purePhoneNumber +"' ";
		XcxImport xi = new XcxImport();
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try{
	    	
		 conn = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt = conn.prepareStatement(strSql);
	     rs = pstmt.executeQuery();
	     while (rs.next()) {
	    	xi.setId(rs.getInt("id"));
	    	xi.setStar(StringUtil.strnull(rs.getString("star")));
	    	xi.setOver(StringUtil.strnull(rs.getString("over")));
	    	xi.setRemark(StringUtil.strnull(rs.getString("remark")));
	    	xi.setVehicle(StringUtil.strnull(rs.getString("vehicle")));
	    	 
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
		
		return xi;
	}

}
