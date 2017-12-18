package com.api.platform.common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * 功能描述：字符串工具类
 * @author      freedom.xie    
 * @version     版本：0.5
 */
public  class StringUtil {	
	
	
	
	/**
	 * 检测字符串是否为空字符串
	 * @param input 待检测字符串
	 * @return
	 * <li>true：字符串为空</li>
	 * <li>false：字符串不为空</li>
	 */
	public static boolean isEmpty(String input) {
		if (input == null) return true;
		return (input.trim().length() == 0);
	}
	
	/**
	 * 将对象转换为字符串对象
	 * @param input 待取得对象
	 * @return 取得的字符串对象
	 * @see #getString(Object, String)
	 */
	public static String getString(Object input) {
		return getString(input, "");
	}
	
	/**
	 * 将对象转换为字符串对象
	 * @param input 待取得对象
	 * @param defVal 对象为空时的默认返回值
	 * @return 取得的字符串对象
	 */
	public static String getString(Object input, String defVal) {
		if (input == null) return defVal;
		String str = input.toString();
		return (str == null) ? defVal : str.trim();
	}
	
	/**
	 * 替换字符串中的指定字符
	 * @param input 待替换字符串
	 * @param oldChar 待替换字符
	 * @param newChar 替换后字符
	 * @return
	 * <li>待替换字符串为null或长度为零时，返回待替换字符串</li>
	 * <li>其它，返回替换后结果</li>
	 */
	public static String replaceAll(String input, char oldChar, char newChar) {
		if (input == null) return input;
		int len = input.length();
		if (input.length() == 0) return input;
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char ch = input.charAt(i);
			if (ch == oldChar) ch = newChar;
			sb.append(ch);
		}
		return sb.toString();
	}
	
	/**
	 * 对请求的url作Encode操作
	 * @param url  访问的url地址
	 * @param code 编码格式
	 * @return 取得的字符串对象
	 */	
	public static String urlEncode(String url,String code) {
		try {
			return URLEncoder.encode(url, code == null ? "gbk" : code);
		} catch (Exception ex) {
			return url;
		}
	}
	
	/**
	 * 对请求的url作Decode操作
	 * @param url  访问的url地址
	 * @param code 编码格式
	 * @return 取得的字符串对象
	 */	
	public static String urlDecode(String url,String code) {
		try {
			return URLDecoder.decode(url, code == null ? "gbk" : code);
		} catch (Exception ex) {
			return url;
		}
	}

	/**
	 * 去掉划线，下划线后一个字母大写
	 * @date         创建日期： 2012-4-17 
	 * @param str
	 * @return 
	 * @description  描述：
	 */
	public static String replaceColumn(String str){
		//String str = "ss_aa_bbb";
		char [] strs = str.toCharArray();
		boolean flag = false;
		StringBuilder sBuilder = new StringBuilder();
		for (char c : strs) {
			if (c == '_') {
				flag = true;
			} else {
				if (flag)
					sBuilder.append((c + "").toUpperCase());
				else
					sBuilder.append(c);
				flag = false;
			}
		}
		//System.out.println("sb== "+sBuilder.toString());
		return sBuilder.toString();
	}
	
	public static String StrToUtf8(String s)
	  {
	    String str = "";
	    try {
	      str = new String(s.getBytes("ISO8859-1"), "UTF-8");
	     // str = s;
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    
	    return (str);//添加方法，防注入
	  }

	  public static String StrToGBK(String s)
	  {
	    String str = "";
	    try {
	      str = new String(s.getBytes("ISO8859-1"), "GBK");
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return str;
	  }
	  
	  /**
	   * 简单防注入代码
	   * @param strText
	   * @return
	   */
	  public static String sql_inj(String str)

	  {
		  if(str == null || "null".equals(str))
			  return "";

	  String inj_str = "'|and|exec|insert|select|delete|update|count|%|chr|mid|master|truncate|char|declare|;|or|-|,";

	  String inj_stra[] = inj_str.split("\\|");

	  for (int i=0 ; i < inj_stra.length ; i++ )

	  {

		  str = str.replaceAll(inj_stra[i], "");

	  }

	  return str;

	  }
	  
	  public static String strnull(String str){
		  if(str == null || "null".equals(str))
			  return "";
		  return str;
	  }
	  
	  public static String strXcxnull(String str){
		  if(str == null || "null".equals(str) ||"undefined".equals(str))
			  return "";
		  return str;
	  }
	  
	  public static void fileOutputStream(String temp,String fileName) throws IOException{  
	        temp +="\n";  
	        FileOutputStream fos = new FileOutputStream(fileName,true);//true表示在文件末尾追加  
	        fos.write(temp.getBytes());  
	        fos.close();//流要及时关闭  
	    }  
	  
	  public static String getProp(String fileName,String Key){
		  if(fileName == null || fileName.length()==0)
			  fileName = "/xcx.properties";
		  InputStream in = null;
		  Properties p = new Properties(); 
		  try{
		  in = StringUtil.class.getResourceAsStream(fileName);   
		   
		  p.load(in); 
		  }catch(Exception e){
			  e.printStackTrace();
		  }finally{
			  if(in != null);
			  try{
				  in.close();
			  }catch(Exception e){
				  e.printStackTrace();
			  }
		  }
		  System.out.println("从属性文件中取得="+Key);
		  return p.getProperty(Key);
	  }
	  
	  
	  public static int getSecondsFromDate(String expireDate){
		  if(expireDate==null||expireDate.trim().equals(""))
		  return 0;
		  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		  Date date=null;
		  try{
		  date=sdf.parse(expireDate);
		  return (int)(date.getTime()/1000);
		  }
		  catch(Exception e)
		  {
		  e.printStackTrace();
		  return 0;
		  }
	  }
	  /**
	   * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm
	   * 
	   * @param strDate
	   * @return
	   */
	  public static Date strToDateLong(String strDate) {
		  System.out.println(strDate);
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	   ParsePosition pos = new ParsePosition(0);
	   Date strtodate = formatter.parse(strDate, pos);
	   return strtodate;
	  }
	  
	  /**
	   * 获取现在时间
	   * 
	   * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	   */
	public static String getStringDate() {
	   Date currentTime = new Date();
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   String dateString = formatter.format(currentTime);
	   return dateString;
	}
	  
	public static String TimeStamp2Date(Long timestampString, String formats){    
		  Long timestamp = timestampString*1000;    
		  String date = new java.text.SimpleDateFormat(formats).format(new java.util.Date(timestamp));    
		  return date;    
		} 
	  
	
	public static void main(String[] args){
		Long l = (long) -2090231390;
		System.out.println(TimeStamp2Date(l,"yyyy-MM-dd HH:mm:ss"));
		long time = new Date().getTime();
		System.out.println(time);
		System.out.println((int)time);
		System.out.println(new Long(time).intValue()); 
		String str = "张三";
		System.out.println(str);
		System.out.println(StrToUtf8(str));
	}

}

