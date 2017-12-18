package com.api.system.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.api.platform.common.StringUtil;
import com.api.system.entity.Info;
import com.api.system.entity.XcxComment;
import com.api.system.entity.XcxInfo;
import com.api.system.entity.XcxUser;

import spark.db.ConnectionPoolFactory;


public class XcxInfoDao {
	public static Vector<Info> getInfoList(String start,String over,String data,int limit,int page_count){
		Vector<Info> v = new Vector<Info>();
		StringBuffer sql = new StringBuffer();
		sql.append("select info.*,user.avatarUrl as avatarUrl from xcx_info info,xcx_user user where user.id = info.uid and info.time >= "+new Date().getTime()/1000+" ");
		//判断是否有出发地
		if(!"".equals(StringUtil.strXcxnull(start)))
			sql.append(" and departure like '%"+start+"%' ");
		//判断是否有目的地
		if(!"".equals(StringUtil.strXcxnull(over)))
			sql.append(" and destination like '%"+over+"%' ");
		sql.append("order by date asc limit "+limit+","+ page_count);
		System.out.println("信息相关Sql；="+sql.toString());
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
	    	 info.setAvatarUrl(StringUtil.strnull(rs.getString("avatarUrl")));
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
	
	
	
	public static int addInfo(XcxInfo info){
		int i = 0;
		XcxDao<?> xcxdao = new XcxDao<Info>(); 
		i = xcxdao.insertInfo(info);
		return i;
	}
	
	public static int getInfoMaxId(int uid){
		int i = 0;
		String strSql = "select id from xcx_info where uid = "+uid +" order by id desc";
		System.out.println("收藏相关Sq；="+strSql);
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
	 * 我的预约
	 * @param uid
	 * @return
	 */
	public static int getInfoMycount(int uid){
		int i = 0;
		String strSql = "select count(info.id) as id from xcx_info info,xcx_appointment appointment where info.id=appointment.iid and info.uid = "+uid +" and appointment.status = 0 order by id desc";
		System.out.println("收藏相关Sq；="+strSql);
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
	 * 我的发布记录
	 * @param uid
	 * @return
	 */
	public static int getInfoMycount1(int uid){
		int i = 0;
		String strSql = "select count(info.id) as id from xcx_info info where info.uid = "+uid +" order by id desc";
		System.out.println("收藏相关Sq；="+strSql);
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
	
	public static Info getInfo(String iid){
		String strSql = "select info.*,user.openId openId,user.id uuid,user.unionid unionid from xcx_info info,xcx_user user where info.uid=user.id and info.id = "+iid ;
		System.out.println("信息相关Sq；="+strSql);
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    Info info = new Info();
	    try{
	    	
		 conn = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt = conn.prepareStatement(strSql);
	     rs = pstmt.executeQuery();
	    
	     while (rs.next()) {
	    	info.setOpenId(rs.getString("openId"));
	    	info.setDeparture(rs.getString("departure"));
	    	info.setDestination(rs.getString("destination"));
	    	info.setTime(rs.getLong("time"));
	    	info.setFormId(rs.getString("formId"));
	    	info.setUuid(rs.getInt("uuid"));//用来发消息通知
	    	info.setUnionid(rs.getString("unionid"));
	    	 
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
		
		return info;
	}
	
	
	public static Vector<Map<Object,Object>> getUserInfo(int uid){
		Vector<Map<Object,Object>> v = new Vector<Map<Object,Object>>();
		String strSql = "select info.id,info.phone,info.departure,info.destination,info.time,appointment.status from xcx_info info,xcx_appointment appointment where info.id=appointment.iid and appointment.uid = "+uid +" order by appointment.time desc";
		
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try{
	    	
		 conn = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt = conn.prepareStatement(strSql);
	     rs = pstmt.executeQuery();
	     while (rs.next()) {
	    	 Map<Object,Object> info = new HashMap<Object,Object>();
	    	info.put("id", rs.getInt("id"));
	    	info.put("phone", rs.getString("phone"));
	    	info.put("departure", rs.getString("departure"));
	    	info.put("destination", rs.getString("destination"));
	    	info.put("time", rs.getInt("time"));
	    	info.put("status", rs.getInt("status"));
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
	
	/**
	 * 
	 * 得到我的列表
	 * @param uri
	 * @param limit
	 * @param page_count
	 * @return
	 */

	public static Vector<Info> getMyList(int uid,int limit,int page_count){
		Vector<Info> v = new Vector<Info>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from xcx_info info where  info.uid = "+uid+"  ");
		
		sql.append("order by addtime desc limit "+limit+","+ page_count);
		System.out.println("我的列表Sql；="+sql.toString());
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


	/*
	 * 查询信息表中的拼车申请表中是否有记录
	 */
	public static Info getXcxInfo( int uid,String id) {
		Info info = new Info();
		String strSql = "select info.*,appointment.uid as aid from xcx_info info,xcx_appointment appointment where info.id=appointment.iid and appointment.id = "+id+"  and info.uid = "+uid;
		System.out.println("拼车申请表Sq；="+strSql);
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try{
	    	
		 conn = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt = conn.prepareStatement(strSql);
	     rs = pstmt.executeQuery();
	     while (rs.next()) {
	    	info.setAid(rs.getInt("aid"));
	    	info.setId(rs.getLong("id"));
	    	info.setTime(rs.getLong("time"));
	    	info.setDestination(rs.getString("destination"));
	    	info.setDeparture(rs.getString("departure"));
	    	info.setName(rs.getString("name"));
	    	info.setPhone(rs.getString("phone"));
	    	info.setFormId(rs.getString("formId"));
	    	info.setUnionid(rs.getString("unionid"));
	    	 
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
		
		
		return info;
		
	}



	public static int updateInfo(XcxInfo info, String id) {
		int i = 0;
		String strSql = "update xcx_info set name='"+info.getName()+"',gender="+info.getGender()+",phone='"+info.getPhone()+"',date='"+info.getDate()+"',time='"+info.getTime()+"',price="+info.getPrice()+",vehicle='"+info.getVehicle()+"',surplus='"+info.getSurplus()+"',remark='"+info.getRemark()+"' where id="+id;
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


	/**
	 * 删除发布的拼车信息
	 * @param id
	 * @param uid
	 * @return
	 */
	public static int deleteInfo(String id, int uid) {
		int i = 0;
		String strSql = "delete from xcx_info  where id="+id +" and uid="+uid;
		System.out.println("删除发布的拼车信息:"+strSql);
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



	public static Vector<Map<Object,Object>> getInfoList(String iDisplayStart,String iDisplayLength, String gender) {
		Vector<Map<Object,Object>> v = new Vector<Map<Object,Object>>();
		String strSql = "select * from xcx_info info ";
		if(!"0".equals(gender))
			strSql +=" where gender = "+gender;
		strSql += " limit "+iDisplayStart+","+iDisplayLength;
		System.out.println(strSql);
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try{
	    	
		 conn = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt = conn.prepareStatement(strSql);
	     rs = pstmt.executeQuery();
	     while (rs.next()) {
	    	 Map<Object,Object> info = new HashMap<Object,Object>();
	    	info.put("id", rs.getInt("id"));
	    	info.put("name", rs.getString("name"));
	    	info.put("phone", rs.getString("phone"));
	    	info.put("departure", rs.getString("departure"));
	    	info.put("destination", rs.getString("destination"));
	    	info.put("time", StringUtil.TimeStamp2Date(rs.getLong("time"), "yyyy-MM-dd HH:mm:ss"));
	    	info.put("status", rs.getInt("status"));
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
	
	public static int getInfoListNum(String gender) {
		int i = 0;
		String strSql = "select count(id) as id from xcx_info info ";
		if(!"0".equals(gender))
			strSql +=" where gender = "+gender;
		
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try{
	    	
		 conn = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt = conn.prepareStatement(strSql);
	     rs = pstmt.executeQuery();
	     while (rs.next()) {
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
	

}
