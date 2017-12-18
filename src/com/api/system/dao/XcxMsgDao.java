package com.api.system.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.api.platform.common.HttpRequest;
import com.api.platform.common.JSONUtils;
import com.api.platform.common.StringUtil;
import com.api.system.entity.Appoitment;
import com.api.system.entity.Msg;
import com.api.system.entity.XcxMsg;

import net.sf.json.JSONObject;
import spark.db.ConnectionPoolFactory;

public class XcxMsgDao {
	/**
	 * 增加消息
	 * @param xm
	 * @return
	 */
	public static int addMsg(XcxMsg xm){
		XcxDao<?> xcxdao = new XcxDao<XcxMsg>();
		return xcxdao.insertMsg(xm);
	}
	
	/**
	 * 给用户发送消息
	 * @param openId 接收消息用户OPENID
	 * @param page 	 处理消息页面
	 * @param form_id前台传入的Form_id
	 * @param name	用户名
	 * @param phone	电话
	 * @param destination 出发地
	 * @param departure	  目的地
	 * @param time		  时间
	 * @param msg         消息内容
	 */
	public static String sendMessage(String openId,String page,String form_id,String name,String phone,String destination,String departure,String time,String msg,String template_id){
		String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token="+getToken();
		
		Map<Object,Object> m = new HashMap<Object,Object>();
		Map<Object,Object> data = new HashMap<Object,Object>();
		Map<Object,Object> mkeyword1 = new HashMap<Object,Object>();
		Map<Object,Object> mkeyword2 = new HashMap<Object,Object>();
		Map<Object,Object> mkeyword3 = new HashMap<Object,Object>();
		Map<Object,Object> mkeyword4 = new HashMap<Object,Object>();
		Map<Object,Object> mkeyword5 = new HashMap<Object,Object>();
		Map<Object,Object> mkeyword6 = new HashMap<Object,Object>();
		mkeyword1.put("value", name);
		mkeyword1.put("color", "#173177");
		
		mkeyword2.put("value", phone);
		mkeyword2.put("color", "#173177");
		
		mkeyword3.put("value", destination);
		mkeyword3.put("color", "#173177");
		
		mkeyword4.put("value", departure);
		mkeyword4.put("color", "#173177");
		
		mkeyword5.put("value", time);
		mkeyword5.put("color", "#173177");
		
		mkeyword6.put("value", msg);
		mkeyword6.put("color", "#173177");
		data.put("keyword1", mkeyword1);
		data.put("keyword2", mkeyword2);
		data.put("keyword3", mkeyword3);
		data.put("keyword4", mkeyword4);
		data.put("keyword5", mkeyword5);
		data.put("keyword6", mkeyword6);
		m.put("touser", openId);
		m.put("template_id", template_id);
		m.put("page", page);
		m.put("form_id", form_id);
		m.put("data", data);
		
	//	m.put("emphasis_keyword", "keyword6.DATA" );
		
		String params = JSONUtils.getJson(m);
				System.out.println(params);
		//开始调用发送消息
		 String sr = HttpRequest.sendPost(url, params);
		 
		return sr;
		
	}
	
	
	public static String getToken(){
		 //小程序唯一标识   (在配置文件中)
        String wxspAppid = StringUtil.getProp("", "xcx.appid");
        //小程序的 app secret (在微信小程序管理后台获取)
        String wxspSecret = StringUtil.getProp("", "xcx.secret");
        //https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx743f39a7d2cdd028&secret=83a686c85cb5ad0f3d208c9ab751935d
        String params = "grant_type=client_credential&appid=" + wxspAppid + "&secret=" + wxspSecret;
		String url = "https://api.weixin.qq.com/cgi-bin/token";
		  //发送请求
        String sr = HttpRequest.sendGet(url, params);
        System.out.println("Token===="+sr);
        JSONObject json = JSONObject.fromObject(sr);
        return json.get("access_token").toString();
		
	}
	
	public static Vector<Map<Object,Object>> getAll(int uid){
		Vector<Map<Object,Object>> v = new Vector<Map<Object,Object>>();
		StringBuffer sql = new StringBuffer();
		sql.append("select type,count(*) as count from xcx_msg  where see=0 and uid = "+uid+" group by type");
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try{
	    	
		 conn = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt = conn.prepareStatement(sql.toString());
	     rs = pstmt.executeQuery();
	     int zan = 0;
	     int comment = 0;
	     int notice = 0;
	     while (rs.next()) {
	    	 if("zan".equals(rs.getString("type")))
	    		 zan = rs.getInt("count");
	    	 if("comment".equals(rs.getString("type")))
	    		 comment = rs.getInt("count");
	    	 if("notice".equals(rs.getString("type")))
	    		 notice = rs.getInt("count");
	     }
	     Map<Object,Object> zanm = new HashMap<Object,Object>();
	     Map<Object,Object> commentm = new HashMap<Object,Object>();
	     Map<Object,Object> noticem = new HashMap<Object,Object>();
	     zanm.put("type", "zan");
	     zanm.put("count", zan);
	     commentm.put("type", "comment");
	     commentm.put("count", comment);
	     noticem.put("type", "notice");
	     noticem.put("count", notice);
	     
	     v.add(zanm);
	     v.add(commentm);
	     v.add(noticem);
	     
	     
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

	public static Vector<Msg> getMsg(int uid, String type,int limit, int page_count) {
		Vector<Msg> v = new Vector<Msg>();
		
		String strSql = "select  msg.*,user.avatarUrl,user.nickName from xcx_msg msg,xcx_user user where msg.fid=user.id and msg.uid="+uid+" and type = '"+type+"' limit "+ limit+","+page_count;
		System.out.println("我的消息："+strSql);
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try{
	    	
		 conn = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt = conn.prepareStatement(strSql);
	     rs = pstmt.executeQuery();
	     while (rs.next()) {
	    	 Msg msg = new Msg();
	    	msg.setId(rs.getInt("id"));
	    	msg.setTime(rs.getLong("time"));
	    	msg.setUid(rs.getInt("uid"));
	    	msg.setAvatarUrl(rs.getString("avatarUrl"));
	    	msg.setContent(rs.getString("content"));
	    	msg.setFid(rs.getInt("fid"));
	    	msg.setNickName(rs.getString("nickName"));
	    	msg.setSee(rs.getInt("see"));
	    	msg.setType(rs.getString("type"));
	    	msg.setUrl(rs.getString("url"));
	    	v.add(msg); 
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

	public static int updateSee(String mid) {
		int i = 0;
		String strSql = "update xcx_msg set see=see+1 where id in ("+mid+")";
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
	 * 给公从号传参数，调用公众号发消息
	 * @param first_remark 消息头
	 * @param user_name		姓名
	 * @param user_mobile	电话
	 * @param origin_station	出发地
	 * @param destination	目的地
	 * @param departure_time 出发时间
	 * @param comment	消息体
	 * @param applet_url PageUrl
	 * @param unionid	
	 * @return
	 */
	public static String sendGzHMsg(String first_remark,String user_name,String user_mobile,String origin_station,String destination,String departure_time,String comment,String applet_url,String unionid){
		String url = "http://wechat2.shenbianvip.com/rpc/open/send_template";
		
		Map<Object,Object> m = new HashMap<Object,Object>();
		m.put("first_remark", first_remark );
		m.put("user_name", user_name );
		m.put("user_mobile", user_mobile );
		m.put("origin_station", origin_station );
		m.put("destination", destination );
		m.put("departure_time", departure_time );
		m.put("comment", comment );
		m.put("applet_url", "");//applet_url );//小程序未上线。暂传一个空值进来
		m.put("unionid", unionid );
		
		String params = JSONUtils.getJson(m);
		System.out.println("url==="+url);		
		System.out.println("params==="+params);
		//开始调用发送消息
		 String sr = HttpRequest.sendPost(url, params);
		 
		return sr;
		
	}
	

}
