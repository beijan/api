package com.api.system.dao;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.api.ip.IPSeeker;
import com.api.platform.common.StringUtil;

import spark.Request;
import spark.Response;
import spark.Session;

public class Log {
	/**
	 * 写日志
	 * @param request
	 * @param response
	 */
	public static void writeLog(Request request, Response response){
		Session session = request.session(false);
		String path = request.raw().getRequestURL().toString();
		IPSeeker is = new IPSeeker(null,null);
		String ip = getIpAddr(request);
		String userName = "";
		try{
			userName = session.attribute("name") ;
		
		
		String filePath = request.raw().getSession().getServletContext().getRealPath("")+"/logs/log.txt";
		if((path.indexOf(".jsp") != -1 || path.indexOf(".html") !=-1) || path.indexOf("/gymz/app/") !=-1 && !"".equals(userName)){
			System.out.println(userName + "访问了"+path);
			//写文件
			try {
				Date now = new Date();
				DateFormat d2 = DateFormat.getDateTimeInstance();
				StringUtil.fileOutputStream(d2.format(now) +":"+ userName + "访问了"+path + ",来源IP："+ip+ ",来源位置："+is.getCountry(ip)+ ",来源ISP："+is.getIPLocation(ip).getArea(), filePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		}catch(Exception e){
			
		}
	}
	
	
	public static String getIpAddr(Request request1) { 
		HttpServletRequest request = request1.raw();
        String ip = request.getHeader("X-Forwarded-For");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        } 
        if("0:0:0:0:0:0:0:1".equals(ip))
        	ip ="127.0.0.1";
        return ip;  
    }  
}
