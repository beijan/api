package com.api.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.api.system.entity.Appoitment;
import com.api.system.entity.XcxAppoitment;

import spark.db.ConnectionPoolFactory;

public class XcxAppointmentDao {
	/**
	 * Info
	 * @param uid
	 * @return
	 */
	public static int getInfoMycount(int uid){
		int i = 0;
		String strSql = "select count(id) as id from xcx_info info,xcx_appointment appointment where info.id=appointment.iid and uid = "+uid +" and appointment.status = 0 order by id desc";
		
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
	/**
	 * 查询是否已预定
	 * @param uid
	 * @param iid
	 * @return
	 */
	public static int getAppointment(int uid,String iid){
		int i = 0;
		String strSql = "select id as id from xcx_appointment appointment where  uid = "+uid +" and iid = "+iid;
		
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
	
	public static int add(XcxAppoitment xa){
		XcxDao<?> xcxdao = new XcxDao<XcxAppoitment>();
		return xcxdao.insertAppoitmen(xa);
	}
	
	public static Vector<Appoitment> getMyAppoitmen(int uid){
		Vector<Appoitment> v = new Vector<Appoitment>();
		
		String strSql = "select info.id as infoid,info.departure,info.destination,info.time itime,appointment.* from xcx_info info,xcx_appointment appointment where info.id=appointment.iid and info.uid = "+uid +" order by appointment.time desc";
		
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try{
	    	
		 conn = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt = conn.prepareStatement(strSql);
	     rs = pstmt.executeQuery();
	     while (rs.next()) {
	    	 Appoitment xa = new Appoitment();
	    	xa.setId(rs.getInt("id"));
	    	xa.setInfoid(rs.getInt("infoid"));
	    	xa.setIid(rs.getInt("iid"));
	    	xa.setName(rs.getString("name"));
	    	xa.setPhone(rs.getString("phone"));
	    	xa.setStatus(rs.getInt("status"));
	    	xa.setSurplus(rs.getInt("surplus"));
	    	xa.setTime(rs.getInt("itime"));
	    	xa.setUid(rs.getInt("uid"));
	    	xa.setDeparture(rs.getString("departure"));
	    	xa.setDestination(rs.getString("destination"));
	    	v.add(xa); 
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
	
	public static Appoitment getAppoitmen(int uid){
		Appoitment xa = new Appoitment();
		String strSql = "select info.id as infoid,info.departure,info.destination,info.time itime,appointment.* from xcx_info info,xcx_appointment appointment where info.id=appointment.iid and info.uid = "+uid +" order by appointment.time desc";
		
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try{
	    	
		 conn = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt = conn.prepareStatement(strSql);
	     rs = pstmt.executeQuery();
	     while (rs.next()) {
	    	
	    	xa.setId(rs.getInt("infoid"));
	    	xa.setIid(rs.getInt("iid"));
	    	xa.setName(rs.getString("name"));
	    	xa.setPhone(rs.getString("phone"));
	    	xa.setStatus(rs.getInt("status"));
	    	xa.setSurplus(rs.getInt("surplus"));
	    	xa.setTime(rs.getInt("itime"));
	    	xa.setUid(rs.getInt("uid"));
	    	xa.setDeparture(rs.getString("departure"));
	    	xa.setDestination(rs.getString("destination"));
	    	
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
		
		
		return xa;
	}
	
	public static Appoitment getAppoitmen(int uid,String id){
		Appoitment xa = new Appoitment();
		String strSql = "select info.id as infoid,info.departure,info.destination,info.time itime,appointment.* from xcx_info info,xcx_appointment appointment where info.id=appointment.iid and info.uid = "+uid +" and appointment.id = "+id+" order by appointment.time desc";
		System.out.println("查询详细SQL"+strSql);
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try{
	    	
		 conn = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt = conn.prepareStatement(strSql);
	     rs = pstmt.executeQuery();
	     while (rs.next()) {
	    	
	    	xa.setId(rs.getInt("infoid"));
	    	xa.setIid(rs.getInt("iid"));
	    	xa.setName(rs.getString("name"));
	    	xa.setPhone(rs.getString("phone"));
	    	xa.setStatus(rs.getInt("status"));
	    	xa.setSurplus(rs.getInt("surplus"));
	    	xa.setTime(rs.getInt("itime"));
	    	xa.setUid(rs.getInt("uid"));
	    	xa.setDeparture(rs.getString("departure"));
	    	xa.setDestination(rs.getString("destination"));
	    	
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
		
		
		return xa;
	}
	
	public static int updateType(String id, String type) {
		int i = 0;
		String strSql = "update xcx_appointment set status=? where id=?";
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    try{
	    	 conn = ConnectionPoolFactory.getInstance().getConnection();
	    	 pstmt = conn.prepareStatement(strSql);
	    	 pstmt.setInt(1, Integer.parseInt(type));
	    	 pstmt.setInt(2, Integer.parseInt(id));
	    	 
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
	/**
	 * 通过UID,和IID查询预定情况
	 * @param uid 用户ID
	 * @param iid 发布信息ID
	 * @return
	 */
	public static Appoitment Appoitmen(int uid, long iid) {
		int status = -1;
		Appoitment xa = new Appoitment();
		xa.setStatus(status);
		String strSql = "select appointment.* from xcx_appointment appointment where uid="+uid+" and iid="+iid;
		
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try{
	    	
		 conn = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt = conn.prepareStatement(strSql);
	     rs = pstmt.executeQuery();
	     while (rs.next()) {
	    	xa.setId(rs.getInt("id"));
	    	xa.setIid(rs.getInt("iid"));
	    	xa.setName(rs.getString("name"));
	    	xa.setPhone(rs.getString("phone"));
	    	xa.setStatus(rs.getInt("status"));
	    	xa.setSurplus(rs.getInt("surplus"));
	    	xa.setTime(rs.getLong("time"));
	    	xa.setUid(rs.getInt("uid"));
	    	//status = rs.getInt("status");
	    	
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
		
		
		return xa;
	}
	public static int updateNoFee(String total_fee, String out_trade_no, String id) {
		int i = 0;
		String strSql = "update xcx_appointment set out_trade_no=?,total_fee=? where id=?";
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    try{
	    	 conn = ConnectionPoolFactory.getInstance().getConnection();
	    	 pstmt = conn.prepareStatement(strSql);
	    	 pstmt.setString(1, out_trade_no);
	    	 pstmt.setFloat(2, (Float.parseFloat(total_fee)/100));
	    	 pstmt.setInt(3, Integer.parseInt(id));
	    	 
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
	/**
	 * 修改状态为已支付 5
	 * @param out_trade_no
	 * @return
	 */
	public static int updateType(String out_trade_no) {
		int i = 0;
		String strSql = "update xcx_appointment set status=? where out_trade_no=?";
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    try{
	    	 conn = ConnectionPoolFactory.getInstance().getConnection();
	    	 pstmt = conn.prepareStatement(strSql);
	    	 pstmt.setInt(1, 5);
	    	 pstmt.setString(2, out_trade_no);
	    	 
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
	public static String getFormId(String id) {
		String formId = "";
		
		String strSql = "select formId from xcx_appointment appointment where  id="+id;
		
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try{
	    	
		 conn = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt = conn.prepareStatement(strSql);
	     rs = pstmt.executeQuery();
	     while (rs.next()) {
	    	
	    	 formId = rs.getString("formId");
	    	
	    	
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
		
		
		
		return formId;
	}
	/**
	 * 根据IID查询是否有人预定
	 * @param id
	 * @return
	 */
	public static int Appoitmen(String id) {
		int queryId = 0;
			String strSql = "select id from xcx_appointment appointment where  iid="+id;
		
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try{
	    	
		 conn = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt = conn.prepareStatement(strSql);
	     rs = pstmt.executeQuery();
	     while (rs.next()) {
	    	
	    	 queryId = rs.getInt("id");
	    	
	    	
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
		
		return queryId;
	}
	

}
