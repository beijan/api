package com.api.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.api.platform.common.StringUtil;
import com.api.system.entity.XcxComment;
import com.api.system.entity.XcxUser;

import spark.db.ConnectionPoolFactory;

public class XcxCommentDao {
	/**
	 * 得到评论信息
	 * @param uid
	 * @param type
	 * @param limit
	 * @param page_count
	 * @return
	 */
	public static Vector<XcxComment> getComment(String uid,String type,int limit,int page_count){
		Vector<XcxComment> v = new Vector<XcxComment>();
		String strSql = "select xc.*,xu.avatarUrl avatarUrl,xu.name nickName from xcx_comment xc,xcx_user xu where xc.uid = xu.id and iid = "+StringUtil.sql_inj(uid)+" and type = '"+type+"' order by xc.id asc limit "+limit+","+page_count;
		System.out.println("评论相关Sq；="+strSql);
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try{
	    	
		 conn = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt = conn.prepareStatement(strSql);
	     rs = pstmt.executeQuery();
	     while (rs.next()) {
	    	 XcxComment xc = new XcxComment();
	    	 xc.setId(rs.getLong("id"));
	    	 xc.setUid(rs.getLong("uid"));
	    	 xc.setIid(rs.getLong("iid"));
	    	 xc.setTime(rs.getLong("time"));
	    	 xc.setType(StringUtil.strnull(rs.getString("type")));
	    	 xc.setContent(StringUtil.strnull(rs.getString("content")));
	    	
	    	 xc.setZan(StringUtil.strnull(rs.getString("zan")));
	    	 xc.setReply(StringUtil.strnull(rs.getString("reply")));
	    	 
	    	 xc.setAvatarUrl(StringUtil.strnull(rs.getString("avatarUrl")));
	    	 xc.setNickName(StringUtil.strnull(rs.getString("nickName")));
	    	 
	    	 v.add(xc);
	    	 
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
	 * 得到评论信息
	 * @param iid
	 * @param uid
	 * @param type
	 * @param limit
	 * @param page_count
	 * @return
	 */
	public static Vector<XcxComment> getComment(String iid,int uid,String type,int limit,int page_count){
		Vector<XcxComment> v = new Vector<XcxComment>();
		String strSql = "select xc.*,xu.avatarUrl avatarUrl,xu.name nickName from xcx_comment xc,xcx_user xu where xc.uid = xu.id and iid in ('"+iid+"') and type = '"+type+"' order by xc.id asc limit "+limit+","+page_count;
		System.out.println("评论相关Sq；="+strSql);
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try{
	    	
		 conn = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt = conn.prepareStatement(strSql);
	     rs = pstmt.executeQuery();
	     while (rs.next()) {
	    	 XcxComment xc = new XcxComment();
	    	 xc.setId(rs.getLong("id"));
	    	 xc.setUid(rs.getLong("uid"));
	    	 xc.setIid(rs.getLong("iid"));
	    	 xc.setTime(rs.getLong("time"));
	    	 xc.setType(StringUtil.strnull(rs.getString("type")));
	    	 xc.setContent(StringUtil.strnull(rs.getString("content")));
	    	
	    	 xc.setZan(StringUtil.strnull(rs.getString("zan")));
	    	 xc.setReply(StringUtil.strnull(rs.getString("reply")));
	    	 
	    	 xc.setAvatarUrl(StringUtil.strnull(rs.getString("avatarUrl")));
	    	 xc.setNickName(StringUtil.strnull(rs.getString("nickName")));
	    	 
	    	 v.add(xc);
	    	 
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
	 * 得到评论信息
	 * @param iid
	 * @param uid
	 * @param type
	 * @param limit
	 * @param page_count
	 * @return
	 */
	public static Vector<XcxComment> getComment(String type,int limit,int page_count){
		Vector<XcxComment> v = new Vector<XcxComment>();
		String strSql = "select xc.*,xu.avatarUrl avatarUrl,xu.name nickName from xcx_comment xc,xcx_user xu where xc.uid = xu.id  and type = '"+type+"' order by xc.id asc limit "+limit+","+page_count;
		System.out.println("评论相关Sq；="+strSql);
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try{
	    	
		 conn = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt = conn.prepareStatement(strSql);
	     rs = pstmt.executeQuery();
	     while (rs.next()) {
	    	 XcxComment xc = new XcxComment();
	    	 xc.setId(rs.getLong("id"));
	    	 xc.setUid(rs.getLong("uid"));
	    	 xc.setIid(rs.getLong("iid"));
	    	 xc.setTime(rs.getLong("time"));
	    	 xc.setType(StringUtil.strnull(rs.getString("type")));
	    	 xc.setContent(StringUtil.strnull(rs.getString("content")));
	    	
	    	 xc.setZan(StringUtil.strnull(rs.getString("zan")));
	    	 xc.setReply(StringUtil.strnull(rs.getString("reply")));
	    	 
	    	 xc.setAvatarUrl(StringUtil.strnull(rs.getString("avatarUrl")));
	    	 xc.setNickName(StringUtil.strnull(rs.getString("nickName")));
	    	 
	    	 v.add(xc);
	    	 
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
	 * 得到评论数
	 * @param uid
	 * @param type
	 * @return
	 */
	
	public static int getCommentCount(String uid,String type){
		int i = 0;
		String strSql = "select count(id) as id from xcx_comment where iid = "+StringUtil.sql_inj(uid)+" and type = '"+type+"'";
		System.out.println("评论数量Sq；="+strSql);
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try{
	    	
		 conn = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt = conn.prepareStatement(strSql);
	     rs = pstmt.executeQuery();
	     while (rs.next()) {
	    	 i = rs.getInt(1);
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
	
	public static int  AddComment(XcxComment xc){
		int i = 0;
		String strSql = "INSERT INTO xcx_comment (uid,iid,type,content,reply,time) VALUES(?,?,?,?,?,?)";
		Connection conn = null;
	    PreparedStatement pstmt = null;
	   
	    try{
	    	 conn = ConnectionPoolFactory.getInstance().getConnection();
	    	 pstmt = conn.prepareStatement(strSql);
	    	 pstmt.setInt(1, (int)xc.getUid());
	    	 pstmt.setInt(2, (int)xc.getIid());
	    	 pstmt.setString(3, xc.getType());
	    	 pstmt.setString(4, xc.getContent());
	    	 pstmt.setString(5, xc.getReply());
	    	 pstmt.setInt(6, (int)xc.getTime());
	    	 
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

	public static int getCommentMaxId(int uid){
		int i = 0;
		String strSql = "select id from xcx_comment where uid = "+uid +" order by id desc";
		
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
	 * 评论赞+1
	
	 * @param cid
	 * @return
	 */
	public static int updateZan( String cid) {
		// TODO Auto-generated method stub
		int i = 0;
		String strSql = "update xcx_comment set zan=zan+1 where id = ?";
		Connection conn = null;
	    PreparedStatement pstmt = null;
	   
	    try{
	    	 conn = ConnectionPoolFactory.getInstance().getConnection();
	    	 pstmt = conn.prepareStatement(strSql);
	    	 pstmt.setInt(1, Integer.parseInt(cid));
	    	
	    	 
	    	 
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
	 * 传入评论ID，得到用户信息
	 * @param cid
	 * @return
	 */
	public static XcxComment getUid(String cid) {
		XcxComment xc = new XcxComment();
		String strSql = "select id,uid,content,zan from xcx_comment where id = "+cid +" order by id desc";
		
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try{
	    	
		 conn = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt = conn.prepareStatement(strSql);
	     rs = pstmt.executeQuery();
	     if (rs.next()) {
	    	 xc.setId(rs.getLong("id")); 
	    	 xc.setUid(rs.getLong("uid"));
	    	 xc.setZan(rs.getString("zan"));
	    	 xc.setContent(rs.getString("content"));
	    	 
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
		
		
		return xc;
	}
	
	/**
	 * 传入评论ID，得到用户信息
	 * @param cid
	 * @return
	 */
	public static XcxComment getUid1(String type,String cid) {
		XcxComment xc = new XcxComment();
		String strSql = "";
		if(type.equals("info"))
			strSql = "select id,uid,content,zan from xcx_comment where id = "+cid +" order by id desc";
		else
			strSql = "select id,uid from xcx_"+type+" where id = "+cid +" order by id desc";
		System.out.println("回复信息"+strSql);
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try{
	    	
		 conn = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt = conn.prepareStatement(strSql);
	     rs = pstmt.executeQuery();
	     if (rs.next()) {
	    	 xc.setId(rs.getLong("id")); 
	    	 xc.setUid(rs.getLong("uid"));
	    	 if(!type.equals("info")){
	    	 xc.setZan(rs.getString("zan"));
	    	 xc.setContent(rs.getString("content"));
	    	 }
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
		
		
		return xc;
	}
	
	/**
	 * 传入评论ID，得到用户信息
	 * @param cid
	 * @return
	 */
	public static Vector<XcxComment> getIid(String iid) {
		Vector<XcxComment> v = new Vector<XcxComment>();
		String strSql = "select xc.id,xc.uid,xc.content,xc.zan,xc.reply,xu.avatarUrl avatarUrl,xu.name nickName from xcx_comment xc,xcx_user xu where xc.uid = xu.id and iid = "+iid +" order by id desc";
		
		Connection conn2 = null;
	    PreparedStatement pstmt2 = null;
	    ResultSet rs2 = null;
	    try{
	    	
		 conn2 = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt2 = conn2.prepareStatement(strSql);
	     rs2 = pstmt2.executeQuery();
	     
	     while (rs2.next()) {
	    	 XcxComment xc = new XcxComment();
	    	 xc.setId(rs2.getLong("id")); 
	    	 xc.setUid(rs2.getLong("uid"));
	    	 xc.setZan(rs2.getString("zan"));
	    	 xc.setContent(rs2.getString("content"));
	    	 xc.setReply(StringUtil.strnull( rs2.getString("reply")));
	    	 
	    	 xc.setNickName(StringUtil.strnull( rs2.getString("nickName")));
	    	 xc.setAvatarUrl(StringUtil.strnull( rs2.getString("avatarUrl")));
	    	 v.add(xc);
	    	 
	     }
	    }catch(Exception e){
	    	
	    	e.printStackTrace();
	    }finally
	    {
		      try
		      {
		        rs2.close();
		      }
		      catch (Exception e) {
		       // e.printStackTrace();
		      }
		      try {
		        pstmt2.close();
		      }
		      catch (Exception e) {
		        e.printStackTrace();
		      }
		      try {
		    	  ConnectionPoolFactory.getInstance().close(conn2);
			      }
			      catch (Exception e) {
			        e.printStackTrace();
			      }
		      
		    }
		
		
		return v;
	}
	

}
