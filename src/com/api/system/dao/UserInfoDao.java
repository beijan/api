package com.api.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.api.platform.common.AesCbcUtil;
import com.api.platform.common.CityUtils;
import com.api.platform.common.HttpRequest;
import com.api.platform.common.StringUtil;
import com.api.system.entity.XcxImport;
import com.api.system.entity.XcxUser;

import net.sf.json.JSONObject;
import spark.annotation.Auto;
import spark.db.ConnectionPoolFactory;

public class UserInfoDao {
	
    
	/**
	 * 通过小程序得到登录信息
	 * @param sessionKey
	 * @param encryptedData
	 * @param iv
	 * @param code
	 * @return Map对象
	 */
	public static Map<Object,Object> getUserInfo(String encryptedData, String iv,String code){
		Map<Object,Object> map = new HashMap<Object,Object>();
        //登录凭证不能为空
        if (code == null || code.length() == 0) {
            map.put("status", 0);
            map.put("msg", "code 不能为空");
            return map;
        }

        //小程序唯一标识   (在配置文件中)
        String wxspAppid = StringUtil.getProp("", "xcx.appid");
        //小程序的 app secret (在微信小程序管理后台获取)
        String wxspSecret = StringUtil.getProp("", "xcx.secret");
        //授权（必填）
        String grant_type = StringUtil.getProp("", "xcx.grant_type");


        //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
        //请求参数
        String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type=" + grant_type;
        //发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        //解析相应内容（转换成json对象）
        JSONObject json = JSONObject.fromObject(sr);
        //获取会话密钥（session_key）
        String session_key = json.get("session_key").toString();
        System.out.println("session中的session_key="+session_key);
        //用户的唯一标识（openid）
        String openid = (String) json.get("openid");
        
        //////////////// 2、对encryptedData加密数据进行AES解密 ////////////////
        try {
            String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
            if (null != result && result.length() > 0) {
                map.put("status", 1);
                map.put("msg", "解密成功");
                
              
                JSONObject userInfoJSON = JSONObject.fromObject(result);
                Map<Object,Object> userInfo = new HashMap<Object,Object>();
                String OpenId = userInfoJSON.get("openId").toString();
                map.put("sk", userInfoJSON.get("openId"));//把OpenId，做为用户唯一属性
                userInfo.put("openId", userInfoJSON.get("openId"));
                userInfo.put("nickName", userInfoJSON.get("nickName"));
                userInfo.put("gender", userInfoJSON.get("gender"));
                userInfo.put("city", CityUtils.getCity(userInfoJSON.get("city")));
                userInfo.put("province", CityUtils.getCity(userInfoJSON.get("province")));
                userInfo.put("country", userInfoJSON.get("country"));
                userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));
                userInfo.put("unionId", userInfoJSON.get("unionId"));
                
                userInfo.put("id", UserInfoDao.sk(userInfoJSON.get("openId").toString()));
                userInfo.put("name", UserInfoDao.getUserKey("name", OpenId));
                userInfo.put("phone", UserInfoDao.getUserKey("phone", OpenId));
                userInfo.put("vehicle", UserInfoDao.getUserKey("vehicle", OpenId));
                userInfo.put("county", UserInfoDao.getUserKey("county", OpenId));
                userInfo.put("start", UserInfoDao.getUserKey("start", OpenId));
                userInfo.put("over", UserInfoDao.getUserKey("over", OpenId));
                userInfo.put("remark", UserInfoDao.getUserKey("remark", OpenId));
                map.put("userInfo", userInfo);
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("status", 0);
        map.put("msg", "解密失败");
        return map;
	}
	
	
	/**
	 * 通过小程序得到登录信息
	 * @param sessionKey
	 * @param encryptedData
	 * @param iv
	 * @param code
	 * @return Map对象
	 */
	public static Map<Object,Object> getUserPhone(String encryptedData, String iv,String code){
		Map<Object,Object> map = new HashMap<Object,Object>();
        //登录凭证不能为空
        if (code == null || code.length() == 0) {
            map.put("status", 0);
            map.put("msg", "code 不能为空");
            return map;
        }

        //小程序唯一标识   (在配置文件中)
        String wxspAppid = StringUtil.getProp("", "xcx.appid");
        //小程序的 app secret (在微信小程序管理后台获取)
        String wxspSecret = StringUtil.getProp("", "xcx.secret");
        //授权（必填）
        String grant_type = StringUtil.getProp("", "xcx.grant_type");


        //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
        //请求参数
        String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type=" + grant_type;
        //发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        //解析相应内容（转换成json对象）
       JSONObject json = JSONObject.fromObject(sr);
        //获取会话密钥（session_key）
       String session_key = json.get("session_key").toString();
        System.out.println("session中的session_key="+session_key);
        //用户的电话号码（purePhoneNumber）
      //  String purePhoneNumber = (String) json.get("purePhoneNumber");
      //  System.out.println("用户的电话号码解密前："+purePhoneNumber);
        //////////////// 2、对encryptedData加密数据进行AES解密 ////////////////
        try {
        	System.out.println(encryptedData);
        	System.out.println(session_key);
        	System.out.println("iv===:"+iv);
            String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
            if (null != result && result.length() > 0) {
                map.put("status", 1);
                map.put("msg", "解密成功");
                
              
                JSONObject userInfoJSON = JSONObject.fromObject(result);
                Map<Object,Object> userInfo = new HashMap<Object,Object>();
               
             
                userInfo.put("phoneNumber", userInfoJSON.get("phoneNumber"));
                userInfo.put("purePhoneNumber", userInfoJSON.get("purePhoneNumber"));
                userInfo.put("countryCode", userInfoJSON.get("countryCode"));
                
                
                map.put("userInfo", userInfo);
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("status", 0);
        map.put("msg", "解密失败");
        return map;
	}
	/**
	 * 通过OpenId判断用户是否存在数据库中
	 * @param openId
	 * @return
	 */
	public static int sk(String openId){
		int id = -1;
		String strSql = "select id from xcx_user where openid = '"+StringUtil.sql_inj(openId)+"'";
		System.out.println("判断用户是否是第一次登录"+strSql);
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try{
	    	
		 conn = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt = conn.prepareStatement(strSql);
	     rs = pstmt.executeQuery();
	     while (rs.next()) {
	    	id = rs.getInt("id");
	    	 
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
			
	   
		return id;
	}
	
	/**
	 * 通过OpenId判断用户是否存在数据库中
	 * @param openId
	 * @return
	 */
	public static int getUserId(String openId){
		int userId = -1;
		String strSql = "select id from xcx_user where openid = '"+StringUtil.sql_inj(openId)+"'";
		
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try{
	    	
		 conn = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt = conn.prepareStatement(strSql);
	     rs = pstmt.executeQuery();
	     while (rs.next()) {
	    	userId = rs.getInt("id");
	    	 
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
		System.out.println("查询用户ID ="+userId);	
	   
		return userId;
	}
	
	/**
	 * 通过OpenId判断用户是否存在数据库中
	 * @param openId
	 * @return
	 */
	public static String  getUserKey(String str,String openId){
		 String key = "";
		String strSql = "select "+str+" from xcx_user where openid = '"+StringUtil.sql_inj(openId)+"'";
		
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try{
	    	
		 conn = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt = conn.prepareStatement(strSql);
	     rs = pstmt.executeQuery();
	     while (rs.next()) {
	    	key = StringUtil.getString(rs.getString(str));
	    	 
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
			
	   
		return key;
	}
	
	public static int addUserInfo(XcxUser xu){
		int i = 0;
		XcxDao<?> xcxdao = new XcxDao<XcxUser>(); 
		i = xcxdao.insert(xu);
		return i;
	}
	
	public static int  UpdataUserInfo(XcxUser xu){
		int i = 0;
		String strSql = "update xcx_user set phone=?,vehicle=?,name=? where id=?";
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    try{
	    	 conn = ConnectionPoolFactory.getInstance().getConnection();
	    	 pstmt = conn.prepareStatement(strSql);
	    	 pstmt.setString(1, xu.getPhone());
	    	 pstmt.setString(2, xu.getVehicle());
	    	 pstmt.setString(3, xu.getName());
	    	 pstmt.setLong(4, xu.getId());
	    	 
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
	public static XcxUser getUserInfo(int aid) {
		XcxUser xu = new XcxUser();
		String strSql = "select * from xcx_user where id = "+aid;
		
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try{
	    	
		 conn = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt = conn.prepareStatement(strSql);
	     rs = pstmt.executeQuery();
	     while (rs.next()) {
	    	xu.setPhone(rs.getString("phone"));
	    	xu.setName(rs.getString("name"));
	    	xu.setOpenId(rs.getString("openId"));
	    	xu.setUnionId(rs.getString("unionId"));
	    	 
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
			return xu;
	}
	
	public static XcxUser getUserInfoOepnid(String openid) {
		XcxUser xu = new XcxUser();
		String strSql = "select * from xcx_user where openid = '"+openid+"'";
		
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try{
	    	
		 conn = ConnectionPoolFactory.getInstance().getConnection();
	     pstmt = conn.prepareStatement(strSql);
	     rs = pstmt.executeQuery();
	     while (rs.next()) {
	    	xu.setPhone(rs.getString("phone"));
	    	xu.setName(rs.getString("name"));
	    	xu.setOpenId(rs.getString("openId"));
	    	xu.setAvatarUrl(rs.getString("avatarUrl"));
	    	xu.setCity(rs.getString("city"));
	    	xu.setCountry(rs.getString("country"));
	    	xu.setCounty(rs.getString("county"));
	    	xu.setGender(rs.getString("gender"));
	    	xu.setId(rs.getLong("id"));
	    	xu.setLanguage(rs.getString("language"));
	    	xu.setNickName(rs.getString("nickName"));
	    	xu.setProvince(rs.getString("province"));
	    	xu.setUnionId(rs.getString("unionId"));
	    	xu.setVehicle(rs.getString("vehicle"));
	    	xu.setStart(rs.getString("start"));
	    	xu.setOver(rs.getString("over"));
	    	xu.setRemark(rs.getString("remark"));
	    	 
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
			return xu;
	}



	public static int updatePhone(XcxImport xi,String openId) {
		int i = 0;
		String strSql = "update xcx_user set phone='"+xi.getPhone()+"'";
		if(StringUtil.isEmpty(xi.getVehicle())){
			strSql +=",vehicle='"+xi.getVehicle()+"'";
		}
		if(StringUtil.isEmpty(xi.getStar())){
			strSql +=",start='"+xi.getStar()+"'";
		}
		if(StringUtil.isEmpty(xi.getOver())){
			strSql +=",over='"+xi.getOver()+"'";
		}
		if(StringUtil.isEmpty(xi.getRemark())){
			strSql +=",remark='"+xi.getRemark()+"'";
		}
		strSql +=" where openId='"+openId+"'";
		System.out.println("更新个人电话号码和基本信息："+strSql);
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


	public static int UpdataUserInfo(String openId, String city, String county, String gender, String name, String over,
			String phone, String province, String remark, String start, String vehicle) {
		int i = 0;
		String strSql = "update xcx_user set city='"+city+"',county='"+county+"',gender='"+gender+"',nickName='"+name+"',over='"+over+"',phone='"+phone+"',province='"+province+"',remark='"+remark+"',start='"+start+"',vehicle='"+vehicle+"' where openId='"+openId+"'";
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
	

}
