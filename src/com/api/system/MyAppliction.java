package com.api.system;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.dom4j.*;
import org.dom4j.io.*;

import com.api.ip.Util;
import com.api.platform.common.JSONUtils;
import com.api.platform.common.StringUtil;
import com.api.platform.pay.MessageUtil;
import com.api.platform.pay.PayUtil;
import com.api.platform.pay.UUIDHexGenerator;
import com.api.system.dao.Log;
import com.api.system.dao.UserInfoDao;
import com.api.system.dao.UsersDao;
import com.api.system.dao.XcxAppointmentDao;
import com.api.system.dao.XcxCommentDao;
import com.api.system.dao.XcxDynamicDao;
import com.api.system.dao.XcxFavDao;
import com.api.system.dao.XcxImportDao;
import com.api.system.dao.XcxInfoDao;
import com.api.system.dao.XcxMsgDao;
import com.api.system.dao.XcxZanDao;
import com.api.system.entity.Dynamic;
import com.api.system.entity.Info;
import com.api.system.entity.Msg;
import com.api.system.entity.PaymentPo;
import com.api.system.entity.XcxAppoitment;
import com.api.system.entity.XcxComment;
import com.api.system.entity.XcxDynamic;
import com.api.system.entity.XcxFav;
import com.api.system.entity.XcxImport;
import com.api.system.entity.XcxInfo;
import com.api.system.entity.XcxMsg;
import com.api.system.entity.XcxUser;
import com.api.system.entity.XcxZan;
import com.api.system.service.AppService;
import com.api.system.service.IAppService;

import net.sf.json.JSONObject;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.Spark;
import spark.annotation.Auto;
import spark.page.easyui.Page;
import spark.render.JsonRender;
import spark.servlet.ISparkApplication;

public class MyAppliction implements ISparkApplication {
	//推荐以自动注入的方式创建对象
	@Auto(name = AppService.class)
	private IAppService appService;
	
	private Log log ;

	@Override
	public void run() {
        /**
         * 登陆有效性验证
         */
		Spark.before(new Filter() {
			
			@SuppressWarnings("static-access")
			@Override
			public void handle(Request request, Response response) {
				
				/*
				Session session = request.session(false);
				if (session == null) {
					response.redirect(request.raw().getContextPath()+"/login.jsp");
				} else {
					if (session.attribute("name") == null) {
						response.redirect(request.raw().getContextPath()+"/login.jsp");
				}
				}
				
				//写日志
				log.writeLog(request, response);
				*/
			}
		});

		Spark.post(new Route("/app/login") {
			@Override
			public Object handle(Request request, Response response) {
				String uname = request.queryParams("uname");
				String pwd = request.queryParams("upassword");
				String code = request.queryParams("code").toUpperCase();
				Session session = request.session();
				String verificationCode = session.attribute("verificationCode").toString();
				String ip = Log.getIpAddr(request);
				if(!code.equals(verificationCode))
					return -1;
				int flag = appService.checkLogin(uname, pwd);
				if (flag == 1) {
					session.attribute("name", uname);
					//写入数据库表
					UsersDao.updateUsers(uname, ip, new Date().getTime()/1000);
				}
				//此处可选择是否使用
				response.type("application/json;charset=utf-8");
				return flag ;
				
			}

		});
		
		
		Spark.get(new Route("/app/getUsers") {
			@Override
			public Object handle(Request request, Response response) {
				//在需要分页显示的时候使用此段代码
				Page p = new Page();
				int pageindex=0; 
			//	int offset = Integer.parseInt(request.raw().getParameter("offset")); 
			//	int limit = Integer.parseInt(request.raw().getParameter("limit"));     

			//	if(offset !=0){ 
			//	    pageindex = offset/limit; 
			//	} 
				pageindex+= 1;//第几页<BR>...
			//	p.setMaxNum(limit);//每页多少条记录
				p.setPage(pageindex);
				String personCode = request.raw().getParameter("personCode");
				//写日志
				//log.writeLog(request, response);
				return new JsonRender().render(appService.getUsers()).toString();
				
			}

		});
		
		/**
		 * 登录用验证码
		 */
		Spark.get(new Route("/app/image") {
			@Override
			public Object handle(Request request, Response response) {
				BufferedImage bfi = new BufferedImage(80,25,BufferedImage.TYPE_INT_RGB);  
		        Graphics g = bfi.getGraphics();  
		        g.fillRect(0, 0, 80, 25);  
		  
		        //验证码字符范围  
		        char[] ch = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789".toCharArray();  
		        Random r = new Random();   
		        int index;    
		        StringBuffer sb = new StringBuffer(); //保存字符串  
		        for(int i=0; i<4; i++){  
		            index = r.nextInt(ch.length);  
		            g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));  
		            Font font = new Font("宋体", 30, 20);  
		            g.setFont(font);  
		            g.drawString(ch[index]+"", (i*20)+2, 23);  
		            sb.append(ch[index]);  
		        }  
		          
		        // 添加噪点  
		        int area = (int) (0.02 * 80 * 25);  
		        for(int i=0; i<area; ++i){  
		            int x = (int)(Math.random() * 80);  
		            int y = (int)(Math.random() * 25);  
		            bfi.setRGB(x, y, (int)(Math.random() * 255));  
		        }  
		          
		      //设置验证码中的干扰线  
		        for (int i = 0; i < 6; i++) {    
		              //随机获取干扰线的起点和终点  
		              int xstart = (int)(Math.random() * 80);  
		              int ystart = (int)(Math.random() * 25);  
		              int xend = (int)(Math.random() * 80);  
		              int yend = (int)(Math.random() * 25);  
		              g.setColor(Util.interLine(1, 255));  
		              g.drawLine(xstart, ystart, xend, yend);  
		            }  
		        HttpSession session = request.raw().getSession();  //保存到session  
		        session.setAttribute("verificationCode", sb.toString());  
		      //  System.out.println("session 中的verificationCode="+sb);
		        
		        try {
					ImageIO.write(bfi, "JPG", response.raw().getOutputStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  //写到输出流  
				
				
				return "";
			}

		});
		
		
		
		/**
		 * 小程序验证是否登录
		 */
		Spark.post(new Route("/user/vaild_sk") {
			@Override
			public Object handle(Request request, Response response) {
				System.out.println("/user/vaild_sk");
				String sk = request.raw().getParameter("sk");
				System.out.println("小程序传来的sk="+sk);
				
	             Map<Object,Object> map = new HashMap<Object,Object>();  
				if(!"".equals(StringUtil.strXcxnull(sk))){
					map.put("status", 1);
					map.put("msg", "已登录！");
					map.put("phone", UserInfoDao.getUserKey("phone", sk));
				}else{
					map.put("status", -1);
					map.put("msg", "未登录！");
				}
				System.out.println(JSONUtils.getJson(map));
				return JSONUtils.getJson(map);
			}
		});
		
		/**
		 * 小程序使用登录
		 */
		Spark.post(new Route("/user/login") {
			@Override
			public Object handle(Request request, Response response) {
				String code = request.raw().getParameter("code");
				String encryptedData = request.raw().getParameter("encryptedData");
				String iv = request.raw().getParameter("iv");
				
				Map<Object,Object> map = new HashMap<Object,Object>();
				 map = UserInfoDao.getUserInfo(encryptedData, iv, code);
				
				 String OpenId = map.get("sk").toString();
				 int id = UserInfoDao.sk(OpenId);
				
				 //判断是否是第一次登录这个小程序
				 if(id == -1){
					 //把用户信息写入xcx_user表
					 XcxUser xu = new XcxUser();
					 Map<Object,Object> userInfo = (Map)map.get("userInfo");
					 xu.setAvatarUrl(userInfo.get("avatarUrl").toString());
					 xu.setCity(userInfo.get("city").toString());
					 xu.setCountry(userInfo.get("country").toString());
					 xu.setGender(userInfo.get("gender").toString());
					 xu.setLanguage("zh_CN");//小程序未得到这个值，设置默认
					 xu.setNickName(userInfo.get("nickName").toString());
					 xu.setOpenId(userInfo.get("openId").toString());
					 xu.setProvince(userInfo.get("province").toString());
					 xu.setCounty("");
					 xu.setPhone("");
					 xu.setVehicle("");
					 xu.setName(userInfo.get("nickName").toString());
					 xu.setUnionId(userInfo.get("unionId").toString());
					 UserInfoDao.addUserInfo(xu);
				 }

			     return JSONUtils.getJson(map);
			}
		});
		
		
		/**
		 * 小程序验证手机号码
		 */
		Spark.post(new Route("/user/phone") {
			@Override
			public Object handle(Request request, Response response) {
				String code = request.raw().getParameter("code");
				String encryptedData = request.raw().getParameter("encryptedData");
				String iv = request.raw().getParameter("iv");
				String sk =  request.raw().getParameter("sk");
				System.out.println("OpenId="+sk);
				System.out.println("iv===:"+iv);
				Map<Object,Object> map = new HashMap<Object,Object>();
				 map = UserInfoDao.getUserPhone(encryptedData, iv, code);
				 Map<Object,Object> userInfo = (Map<Object,Object>)map.get("userInfo");
				 String purePhoneNumber = userInfo.get("purePhoneNumber").toString();
				 System.out.println("用户的电话号码解密后："+purePhoneNumber);
				 if(purePhoneNumber!=null){
					 //去xcx_import中取数据
					 XcxImport xi = XcxImportDao.getImport(purePhoneNumber);
					 xi.setPhone(purePhoneNumber);
					 //更新用户表
					int i =  UserInfoDao.updatePhone(xi,sk);
				 }
			     return JSONUtils.getJson(map);
			}
		});
		
		
		
		/**
		 * 小程序使用编辑个人信息
		 */
		Spark.post(new Route("/user/editUser") {
			@Override
			public Object handle(Request request, Response response) {
				String openId = request.raw().getParameter("sk");
				String city = request.raw().getParameter("city");
				String county = request.raw().getParameter("county");
				String gender = request.raw().getParameter("gender");
				String name = request.raw().getParameter("name");
				String over = request.raw().getParameter("over");
				String phone = request.raw().getParameter("phone");
				String province = request.raw().getParameter("province");
				String remark = request.raw().getParameter("remark");
				String start = request.raw().getParameter("start");
				String vehicle = request.raw().getParameter("vehicle");
				//System.out.println("前端传来的SK:"+openId);
				int i = UserInfoDao.UpdataUserInfo(openId,city,county,gender,name,over,phone,province,remark,start,vehicle);
				
				Map<Object,Object> map = new HashMap<Object,Object>();
				map.put("msg", "修改成功");
				map.put("status", 1);
				map.put("user", UserInfoDao.getUserInfoOepnid(openId));
				
			     return JSONUtils.getJson(map);
			}
		});
		
		/**
		 * 小程序得到拼车列表
		 */
		Spark.post(new Route("/info/lists") {
			@Override
			public Object handle(Request request, Response response) {
				String start = request.raw().getParameter("start");//出发地
				String over = request.raw().getParameter("over");//目的地
				String date = request.raw().getParameter("date");//日期
				String page = request.raw().getParameter("page");//页码
				int page_count = 20;
				int limit = (Integer.parseInt(page)-1)*page_count;
				
				
				
				//此处可选择是否使用
				response.type("application/json;charset=utf-8");
					
				Vector<Info> v = new Vector<Info>();
				v = XcxInfoDao.getInfoList(start,over,date,limit,page_count);
				Map<Object,Object> m = new HashMap<Object,Object>();
				m.put("msg", "获取成功");
				m.put("status", 1);
				m.put("list", v);
				
				return JSONUtils.getJson(m);
				
			}
		});
		
		
		/**
		 * 小程序得到拼车详细信息
		 */
		Spark.post(new Route("/info/index") {
			@Override
			public Object handle(Request request, Response response) {
				String id = request.raw().getParameter("id");//出发地
				String sk = request.raw().getParameter("sk");
				System.out.println("id==="+id);
				int uid = UserInfoDao.getUserId(sk);
				//此处可选择是否使用
				response.type("application/json;charset=utf-8");
					
				
				Map<Object,Object> m = new HashMap<Object,Object>();
				Info o = appService.getInfo(id);
				
				
				if(o != null){
				m.put("msg", "获取成功");
				m.put("status", 1);
				m.put("data",o );
				
				m.put("astatus",XcxAppointmentDao.Appoitmen(uid,o.getId()) );
				}else{
					m.put("msg", "获取失败");
					m.put("status", -1);
					
				}
				return JSONUtils.getJson(m);
				
			}
		});
		
		/**
		 * 小程序得到/comment/zan
		 * 赞
		 */
		Spark.post(new Route("/comment/zan") {
			@Override
			public Object handle(Request request, Response response) {
				Map<Object,Object> m = new HashMap<Object,Object>();
				String sk = request.raw().getParameter("sk");
				String cid = request.raw().getParameter("cid");
				int uid = UserInfoDao.getUserId(sk);
				//查询是否已点赞
				if(XcxZanDao.isZan(uid,cid)>0){
					m.put("msg", "你已经赞过了");
					m.put("status", 0);
				}else{
				//写入赞的数据表
					XcxZan xz = new XcxZan();
					xz.setUid(uid);
					xz.setCid(Integer.parseInt(cid));
					xz.setTime(new Date().getTime());
					XcxZanDao.addZan(xz);
					//评论表赞加1
					XcxCommentDao.updateZan(cid);
					//判断赞是不是本人，如果不是，小程序写入通知表
				
					if(uid != (int)XcxCommentDao.getUid(cid).getUid()){
						//msg('zan',$data['uid'],$user['id'],'赞了你的评论:'.$data['content'],'/pages/info/index?id='.$data['iid']);
						XcxMsg xm = new XcxMsg();
						xm.setType("zan");
						xm.setUid((int)XcxCommentDao.getUid(cid).getUid());
						xm.setFid(uid);
						xm.setContent("赞了你的评论:"+XcxCommentDao.getUid(cid).getContent());
						xm.setUrl("/pages/info/index?id="+cid);
						xm.setTime(new Date().getTime());
						XcxMsgDao.addMsg(xm);
					}
					m.put("msg", "点赞成功");
					m.put("status", 1);
					m.put("zan", XcxCommentDao.getUid(cid).getZan());
				}
				return JSONUtils.getJson(m);
			}
		});
		
		/**
		 * 小程序得到fav/addfav
		 * 收藏成功
		 */
		Spark.post(new Route("/fav/addfav") {
			@Override
			public Object handle(Request request, Response response) {
				Map<Object,Object> m = new HashMap<Object,Object>();
				String sk = request.raw().getParameter("sk");
				String iid = request.raw().getParameter("iid");
				int uid = UserInfoDao.getUserId(sk);
				XcxFav xf = new XcxFav();
				xf.setIid(Integer.parseInt(iid));
				xf.setUid(uid);
				xf.setTime(new Date().getTime());
				
				if(XcxFavDao.add(xf)>0){
					m.put("msg", "收藏成功");
					m.put("status", 1);
				}else{
					m.put("msg", "收藏失败");
					m.put("status", 0);
				}
				return JSONUtils.getJson(m);
			}
		});
		
		/**
		 * 小程序得到fav/delfav
		 * 取消收藏成功
		 */
		Spark.post(new Route("/fav/delfav") {
			@Override
			public Object handle(Request request, Response response) {
				
				Map<Object,Object> m = new HashMap<Object,Object>();
				String sk = request.raw().getParameter("sk");
				String iid = request.raw().getParameter("iid");
				int uid = UserInfoDao.getUserId(sk);
				
				
				
				if(XcxFavDao.del(uid,Integer.parseInt(iid))>0){
					m.put("msg", "取消收藏成功");
					m.put("status", 1);
				}else{
					m.put("msg", "取消收藏失败");
					m.put("status", 0);
				}
				return JSONUtils.getJson(m);
			}
		});
		
		
		/**
		 * 小程序得到/fav/myFav
		 * 我的收藏
		 */
		Spark.post(new Route("/fav/myFav") {
			@Override
			public Object handle(Request request, Response response) {
				String sk = request.raw().getParameter("sk");
				String page = request.raw().getParameter("page");
				int page_count = 20;
				int limit = (Integer.parseInt(page)-1)*page_count;
				Map<Object,Object> m = new HashMap<Object,Object>();
				int uid = UserInfoDao.getUserId(sk);
				
				m.put("status", 1);
				m.put("msg", "获取成功！");
				m.put("data", XcxFavDao.getInfoList(uid,limit,page_count));
				
				return JSONUtils.getJson(m);
				
			}
		});
		
		/**
		 * 小程序得到/dynamic/getList
		 * 我的动态
		 */
		Spark.post(new Route("/dynamic/getList") {
			@Override
			public Object handle(Request request, Response response) {
				String sk = request.raw().getParameter("sk");
				String page = request.raw().getParameter("page");
				int page_count = 20;
				int limit = (Integer.parseInt(page)-1)*page_count;
				Map<Object,Object> m = new HashMap<Object,Object>();
				int uid = UserInfoDao.getUserId(sk);
				Vector<Dynamic> v = XcxDynamicDao.getDynamicList(uid);
				String iid = "";
				for(int i=0;i<v.size();i++){
					Dynamic dynamic = (Dynamic)v.get(i);
					iid += dynamic.getId()+",";
				}
				if(iid.length()>2)
					iid = iid.substring(0,iid.length()-1);
				System.out.println("dynamic id is :"+iid);
				
				m.put("status", 1);
				m.put("msg", "获取成功！");
				// xc.put("comment",XcxCommentDao.getIid(rs1.getString("id")));
				Vector<Map<Object,Object>> vd = new Vector<Map<Object,Object>> ();
				Vector<Map<Object,Object>> newVd = new Vector<Map<Object,Object>> ();		
				if(sk==null){
					vd = XcxDynamicDao.getDynamic(limit, page_count);
				}else{
					vd = XcxDynamicDao.getDynamic( uid, limit, page_count);
				}
				for(int i=0;i<vd.size();i++){
					Map<Object,Object> map = (Map<Object, Object>) vd.get(i);
					map.put("comment",XcxCommentDao.getIid(map.get("id").toString()));
					newVd.add(map);
				}
				m.put("list", newVd);
				System.out.println("json is :"+JSONUtils.getJson(m));
				return JSONUtils.getJson(m);
				
			}
		});
		
		/**
		 * 小程序得到appointment/add
		 * 增加预约
		 */
		Spark.post(new Route("/appointment/add") {
			@Override
			public Object handle(Request request, Response response) {
				Map<Object,Object> m = new HashMap<Object,Object>();
				String form_id = request.raw().getParameter("form_id");
				String iid = request.raw().getParameter("iid");
				String openId = request.raw().getParameter("sk");
				String name = request.raw().getParameter("name");
				String phone = request.raw().getParameter("phone");
				String surplus = request.raw().getParameter("surplus");
				//通过OpenId得到用户Id
				int UserId = UserInfoDao.getUserId(openId);
				long time = new Date().getTime();
				
				XcxAppoitment xa = new XcxAppoitment();
				xa.setIid(Integer.parseInt(iid));
				xa.setUid(UserId);
				xa.setName(name);
				xa.setPhone(phone);
				xa.setStatus(0);
				xa.setSurplus(Integer.parseInt(surplus));
				xa.setTime(time);
				xa.setFormId(form_id);
				
				int insert = 0;
				//先查询是否预定，
				int i = XcxAppointmentDao.getAppointment(UserId,iid);
				if(i==0){
					insert = XcxAppointmentDao.add(xa);
					
					String page = "/pages/appointment/index?id="+XcxAppointmentDao.getAppointment(UserId, iid);
					String msg = name + "预约了您发布的拼车信息,请及时联系"+phone+"处理";
					Info info = XcxInfoDao.getInfo(iid);
					System.out.println("form_id==="+form_id);;
					//发送消息
					String template_id = "3on7B2U9nHGfUHgk-laqE_fZHxVgh7oeB2ozkH11Io8";
					//String str = XcxMsgDao.sendMessage(info.getOpenId(), page, info.getFormId(), name, phone, info.getDestination(), info.getDeparture(), StringUtil.getStringDate(), msg,template_id);
					//采用公众号消息发送方式
					String str = XcxMsgDao.sendGzHMsg("拼车请求通知",name,phone,info.getDestination(),info.getDeparture(),StringUtil.TimeStamp2Date(info.getTime(),"yyyy-MM-dd HH:mm:ss"),msg,page,info.getUnionid());
					System.out.println("发送结果："+str);
					if(insert>0){
						m.put("status", 1);
						m.put("id", iid);
						m.put("msg", "预约成功！");
					}else{
						m.put("status", 0);
						m.put("id", iid);
						m.put("msg", "预约失败！");
					}
					//把信息存放到消息表中
					XcxMsg xm = new XcxMsg();
					xm.setContent(msg);
					xm.setFid(10000);//默认为系统管理员
					xm.setTime(time);
					xm.setType("notice");
					xm.setUid(info.getUuid());
					xm.setUrl(page);
					
					XcxMsgDao.addMsg(xm);
				}else{
					m.put("status", 0);
					m.put("msg", "请不要重复预约！");
				}
				
				
				
				return JSONUtils.getJson(m);
			}
		});
		
		/**
		 * 小程序得到fav/isfav
		 * 预约
		 */
		Spark.post(new Route("/fav/isfav") {
			@Override
			public Object handle(Request request, Response response) {
				String iid = request.raw().getParameter("iid");
				String openId = request.raw().getParameter("sk");
				//通过OpenId得到用户Id
				int UserId = UserInfoDao.getUserId(openId);
				Map<Object,Object> m = new HashMap<Object,Object>();
				if(XcxFavDao.isfav(UserId, iid)){
					m.put("status", 1);
					m.put("msg", "已收藏");
				}else{
					m.put("status", 0);
					m.put("msg", "未收藏");
				}
				
				return JSONUtils.getJson(m);
			}
		});
		
		/**
		 * 小程序得到comment/get
		 * 预约
		 */
		Spark.post(new Route("/comment/get") {
			@Override
			public Object handle(Request request, Response response) {
				String id = request.raw().getParameter("id");
				String type = request.raw().getParameter("type");
				String page = request.raw().getParameter("page");
				int page_count = 20;
				int limit = (Integer.parseInt(page)-1)*page_count;
				
				Vector<XcxComment> v = new Vector<XcxComment>();
				v = XcxCommentDao.getComment(id,type,limit,page_count);
				Map<Object,Object> m = new HashMap<Object,Object>();
				if(v.size()>0){
					
					m.put("status", 1);
					m.put("msg", "评论加载成功!");
					m.put("data", v);
				}else{
					m.put("status", 0);
					m.put("msg", "无评论！");
				}
				
				return JSONUtils.getJson(m);
				
				
			}
		});
		
		/**
		 * 小程序得到comment/get_count
		 * 预约
		 */
		Spark.post(new Route("/comment/get_count") {
			@Override
			public Object handle(Request request, Response response) {
				String id = request.raw().getParameter("id");
				String type = request.raw().getParameter("type");
				int count = XcxCommentDao.getCommentCount(id, type);
				Map<Object,Object> m = new HashMap<Object,Object>();
				if(count > 0){
					
					m.put("status", 1);
					m.put("msg", "评论加载成功!");
					m.put("data", count);
				}else{
					m.put("status", 1);
					m.put("msg", "无评论！");
					m.put("data", count);
				}
				
				return JSONUtils.getJson(m);
				
			}
		});
		
		
		/**
		 * 小程序得到info/mycount
		 * 发布数量
		 */
		Spark.post(new Route("/info/mycount") {
			@Override
			public Object handle(Request request, Response response) {
				String openId = request.raw().getParameter("sk");
				int uid = UserInfoDao.getUserId(openId);
				int count = XcxInfoDao.getInfoMycount1(uid);
				Map<Object,Object> m = new HashMap<Object,Object>();
				if(count > 0){
					
					m.put("status", 1);
					m.put("msg", "获取成功!");
					m.put("data", count);
				}else{
					m.put("status", 0);
					m.put("msg", "无评论！");
				}
				
				return JSONUtils.getJson(m);
				
			}
		});
		/**
		 * 小程序得到appointment/mycount
		 * 我的预约
		 */
		Spark.post(new Route("/appointment/mycount") {
			@Override
			public Object handle(Request request, Response response) {
				String openId = request.raw().getParameter("sk");
				int uid = UserInfoDao.getUserId(openId);
				int count = XcxInfoDao.getInfoMycount(uid);
				Map<Object,Object> m = new HashMap<Object,Object>();
				if(count > 0){
					
					m.put("status", 1);
					m.put("msg", "获取成功!");
					m.put("data", count);
				}else{
					m.put("status", 0);
					m.put("msg", "无评论！");
				}
				
				return JSONUtils.getJson(m);
				
			}
		});
		/**
		 * 小程序得到添加拼车信息
		 * 发布拼车信息
		 */
		Spark.post(new Route("/info/add") {
			@Override
			public Object handle(Request request, Response response) {
				String name = request.raw().getParameter("name");//
				String gender = request.raw().getParameter("gender");//:2
				String phone = request.raw().getParameter("phone");//:14800138000
				String type = request.raw().getParameter("type");//1
				String date = request.raw().getParameter("date");//:2017-09-09
				String time = request.raw().getParameter("time");//:03:00
				String price = request.raw().getParameter("price");//:23
				String vehicle = request.raw().getParameter("vehicle");//:BYD-12345
				String surplus = request.raw().getParameter("surplus");//:4
				String remark = request.raw().getParameter("remark");//:11111111
				String sk = request.raw().getParameter("sk");//:oIYjy0HqaVyFqlBKeXQCLsMa2dGM
				String departure = request.raw().getParameter("departure");//:四川省成都市武侯区锦悦西路2号
				String destination = request.raw().getParameter("destination");//:四川省成都市武侯区天府大道北段1656号
				String formId = request.raw().getParameter("formId");
				
				String id = request.raw().getParameter("id");
				
				int uid = UserInfoDao.getUserId(sk);
				
				
				XcxInfo info = new XcxInfo();
				info.setDate(date);
				info.setAddtime(new Date().getTime()/1000);
				info.setTime(StringUtil.strToDateLong(date+" "+time).getTime()/1000);
				info.setDeparture(departure);
				info.setDestination(destination);
				info.setGender(Integer.parseInt(gender));
				info.setName(name);
				info.setPhone(phone);
				info.setRemark(remark);
				info.setSurplus(Integer.parseInt(surplus));
				if(!StringUtil.isEmpty(type))//判断Type是否为空
					info.setType(Long.parseLong(type));
				info.setVehicle(vehicle);
				info.setUid(uid);
				info.setFormId(formId);
				int i = 0;
				if( price!=null )
				info.setPrice(Long.parseLong(price));
				if(id!=null && id.length()>0){
					i = XcxInfoDao.updateInfo(info,id);
				}else{
					i = XcxInfoDao.addInfo(info);
				}
				
				
				
				
				
				Map<Object,Object> m = new HashMap<Object,Object>();
				if(i>0){
					
					m.put("status", 1);
					m.put("msg", "发布成功！");
					m.put("info", XcxInfoDao.getInfoMaxId(uid));
					//更新电话号码，车牌尾号，姓名
					XcxUser xu = new XcxUser();
					xu.setId(uid);
					xu.setName(name);
					xu.setVehicle(vehicle);
					xu.setPhone(phone);
					
					UserInfoDao.UpdataUserInfo(xu);
				}else{
					m.put("status", 0);
					m.put("msg", "发布失败！");
				}
				
				
				
				
				
				return JSONUtils.getJson(m);
				
			}
		});
		
		/**
		 * 小程序得到comment/add
		 * 发表回复
		 */
		Spark.post(new Route("/comment/add") {
			@Override
			public Object handle(Request request, Response response) {
				String iid = request.raw().getParameter("iid");
				String openId = request.raw().getParameter("sk");
				String reply = request.raw().getParameter("reply");
				String type = request.raw().getParameter("type");
				String content = request.raw().getParameter("content");
				
				int uid = UserInfoDao.getUserId(openId);
				
				XcxComment xc = new XcxComment();
				xc.setIid(Long.parseLong(iid));
				xc.setReply(reply);
				xc.setType(type);
				xc.setContent(content);
				xc.setUid(uid);
				xc.setTime(new Date().getTime()/1000);
				int i = XcxCommentDao.AddComment(xc);
				Map<Object,Object> m = new HashMap<Object,Object>();
				if(i>0){
					m.put("status", 1);
					m.put("msg", "评论成功！");
					m.put("id", XcxCommentDao.getCommentMaxId(uid));
					m.put("list", XcxCommentDao.getIid(iid));
				}else{
					m.put("status", 0);
					m.put("msg", "评论失败！");
				}
				//写入消息
				//查询出INFO表的信息
				if(uid != (int)XcxDynamicDao.getUid(iid).getUid()){
					XcxMsg xm = new XcxMsg();
					xm.setType("comment");
					xm.setUid((int)XcxCommentDao.getUid1(type,iid).getUid());
					xm.setFid(uid);
					xm.setContent("回复了您的信息\""+content+"\"");
					xm.setUrl("/pages/"+type+"/index?id="+iid);
					xm.setTime(new Date().getTime());
					XcxMsgDao.addMsg(xm);
				}
				return JSONUtils.getJson(m);
			}
		});
		
		/**
		 * 小程序得到appointment/my
		 * 我是车主
		 */
		Spark.post(new Route("/appointment/my") {
			@Override
			public Object handle(Request request, Response response) {
				String sk = request.raw().getParameter("sk");
				Map<Object,Object> m = new HashMap<Object,Object>();
				int uid = UserInfoDao.getUserId(sk);
				
				m.put("status", 1);
				m.put("msg", "获取成功！");
				m.put("data", XcxAppointmentDao.getMyAppoitmen(uid));
				
				return JSONUtils.getJson(m);
			}
		});
		
		/**
		 * 小程序得到appointment/getPassenger
		 * 我是乘客
		 */
		Spark.post(new Route("/appointment/getPassenger") {
			@Override
			public Object handle(Request request, Response response) {
				String sk = request.raw().getParameter("sk");
				Map<Object,Object> m = new HashMap<Object,Object>();
				int uid = UserInfoDao.getUserId(sk);
				
				m.put("status", 1);
				m.put("msg", "获取成功！");
				m.put("data", XcxInfoDao.getUserInfo(uid));
				
				return JSONUtils.getJson(m);
			}
		});
		/**
		 * 小程序得到appointment/detail
		 * 预约详情
		 */
		Spark.post(new Route("/appointment/detail") {
			@Override
			public Object handle(Request request, Response response) {
				String sk = request.raw().getParameter("sk");
				String id = request.raw().getParameter("id");
				Map<Object,Object> m = new HashMap<Object,Object>();
				int uid = UserInfoDao.getUserId(sk);
				
				m.put("status", 1);
				m.put("msg", "获取成功！");
				m.put("data", XcxAppointmentDao.getAppoitmen(uid,id));
				
				return JSONUtils.getJson(m);
			}
		});
		
		
		/**
		 * 小程序得到info/mylist
		 * 我的发布
		 */
		Spark.post(new Route("/info/mylist") {
			@Override
			public Object handle(Request request, Response response) {
				String sk = request.raw().getParameter("sk");
				String page = request.raw().getParameter("page");
				int uid = UserInfoDao.getUserId(sk);
				int page_count = 20;
				int limit = (Integer.parseInt(page)-1)*page_count;
				Map<Object,Object> m = new HashMap<Object,Object>();
				
				
				m.put("status", 1);
				m.put("msg", "获取成功！");
				m.put("data", XcxInfoDao.getMyList(uid,limit,page_count));
				
				return JSONUtils.getJson(m);
			}
		});
		
		/**
		 * 小程序得到/msg/getall
		 * 消息列表
		 */
		Spark.post(new Route("/msg/getall") {
			@Override
			public Object handle(Request request, Response response) {
				String sk = request.raw().getParameter("sk");
				
				int uid = UserInfoDao.getUserId(sk);
				
				Map<Object,Object> m = new HashMap<Object,Object>();
				
				
				m.put("status", 1);
				m.put("msg", "获取成功！");
				m.put("data", XcxMsgDao.getAll(uid));
				
				return JSONUtils.getJson(m);
			}
		});
		
		/**
		 * 小程序得到/msg/get
		 * 消息信息
		 */
		Spark.post(new Route("/msg/get") {
			@Override
			public Object handle(Request request, Response response) {
				String sk = request.raw().getParameter("sk");
				String type = request.raw().getParameter("type");
				String page = request.raw().getParameter("page");
				
				int uid = UserInfoDao.getUserId(sk);
				int page_count = 20;
				int limit = (Integer.parseInt(page)-1)*page_count;
				Map<Object,Object> m = new HashMap<Object,Object>();
				Vector<Msg> v = XcxMsgDao.getMsg(uid,type,limit,page_count);
				String mid = "";
				for(int i=0;i<v.size();i++){
					Msg msg = (Msg)v.get(i);
					mid += msg.getId()+",";
				}
				if(mid.length()>2){
					mid = mid.substring(0,mid.length()-1);
					//更新查看次数
					XcxMsgDao.updateSee(mid);
				}
				System.out.println("msg id is :"+mid);
				
				m.put("status", 1);
				m.put("msg", "获取成功！");
				m.put("data", v);
				
				return JSONUtils.getJson(m);
			}
		});
		
		
		/**
		 * 小程序得到/dynamic/add
		 * 发布动态
		 */
		Spark.post(new Route("/dynamic/add") {
			@Override
			public Object handle(Request request, Response response) {
				String sk = request.raw().getParameter("sk");
				String content = request.raw().getParameter("content");
				int uid = UserInfoDao.getUserId(sk);
				
				Map<Object,Object> m = new HashMap<Object,Object>();
				XcxDynamic xd = new XcxDynamic();
				xd.setContent(content);
				xd.setTime(new Date().getTime());
				xd.setUid(uid);
				
				
				if(XcxDynamicDao.add(xd)>0){
					m.put("status", 1);
					m.put("msg", "发布成功！");
				}else{
					m.put("status", 0);
					m.put("msg", "发布失败！");
				}
				
				
				return JSONUtils.getJson(m);
			}
		});
		
		
		/**
		 * 小程序得到/appointment/submit
		 * 处理拼车
		 */
		Spark.post(new Route("/appointment/submit") {
			@Override
			public Object handle(Request request, Response response) {
				String sk = request.raw().getParameter("sk");
				String id = request.raw().getParameter("id");
				String type = request.raw().getParameter("type");
				String form_id = request.raw().getParameter("form_id");
				int uid = UserInfoDao.getUserId(sk);
				
				Map<Object,Object> m = new HashMap<Object,Object>();
				//先查询拼车表里是否有相关信息
				Info info = XcxInfoDao.getXcxInfo(uid,id);
				if(info == null){
					m.put("status", 0);
					m.put("msg", "信息错误");
				}else{
					//更新状态
					if(XcxAppointmentDao.updateType(id,type)>0){
					//user 拼车人的相关信息
					XcxUser user = UserInfoDao.getUserInfo(info.getAid());
					//拼装消息主体
					String openId = user.getOpenId(); //发送给谁？
					String page = "/pages/info/index?id="+info.getId();
					
		
						
						String time = StringUtil.TimeStamp2Date(info.getTime(),"yyyy-MM-dd HH:mm:ss");
						
						String name = info.getName();
						String phone = info.getPhone();
						String msg = "";
						String msg1 = "";
						if("1".equals(type)){
							msg = UserInfoDao.getUserKey("name",sk) + "同意了您的拼车请求,请及时与车主"+UserInfoDao.getUserKey("phone",sk)+"取得联系";
							msg1="拼车结果：成功！司机："+name+" 联系电话："+phone;
						}else{
							msg = UserInfoDao.getUserKey("name",sk) + "拒绝了您的拼车请求,原因是拼车人数已满,建议选择其他时间拼车或其它车辆。";
							msg1="拼车结果：失败！司机："+name+" 联系电话："+phone;
						}
						String formid = XcxAppointmentDao.getFormId(id);
						String template_id = "3on7B2U9nHGfUHgk-laqE_fZHxVgh7oeB2ozkH11Io8";
						//小程序本身推送消息
						String str = XcxMsgDao.sendMessage(openId, page, formid, name, phone,  info.getDeparture(), info.getDestination(),time, msg,template_id);
						//采用公众号消息发送方式
						String str1 = XcxMsgDao.sendGzHMsg(msg1,user.getName(),user.getPhone(),info.getDestination(),info.getDeparture(),StringUtil.TimeStamp2Date(info.getTime(),"yyyy-MM-dd HH:mm:ss"),msg,page,user.getUnionId());
						System.out.println("小程序本身推送消息模板消息发送结果="+str);
						System.out.println("公从号="+str1);
						XcxMsg xm = new XcxMsg();
						xm.setFid(10000);
						xm.setType("notice");
						xm.setUid(info.getAid());
						xm.setContent(msg);
						xm.setUrl(page);
						xm.setTime(new Date().getTime());
						XcxMsgDao.addMsg(xm);
						
						m.put("status", 1);
						m.put("msg", "操作成功");
					
					}else{
						m.put("status", 0);
						m.put("msg", "操作失败");
					}
				}
				return JSONUtils.getJson(m);
			}
		});
		
		
		/**
		 * 小程序得到/pay/pay
		 * 处理支付
		 * 生成订单 
		 */
		Spark.post(new Route("/pay/pay") {
			@Override
			public Object handle(Request request, Response response) {
				String total_fee = request.raw().getParameter("total_fee");
				String body = request.raw().getParameter("body");
				String id = request.raw().getParameter("id");
				String sk = request.raw().getParameter("sk");
				System.out.println("<------开始支付----->");
				
				   
		        String appid = StringUtil.getProp("", "xcx.appid");//"替换为自己的小程序ID";//小程序ID   
		        String mch_id = StringUtil.getProp("", "xcx.mchid");//"替换为自己的商户号";//商户号   
		        String mch_key = StringUtil.getProp("", "xcx.mchkey");//"交易密钥
		        String nonce_str = UUIDHexGenerator.generate();//随机字符串   
		        String today = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());   
		        String code = PayUtil.createCode(8);   
		        String out_trade_no = mch_id+today+code;//商户订单号   
		        String spbill_create_ip = Log.getIpAddr(request);//"替换为自己的终端IP";//终端IP   
		        String notify_url = "https://car.ylb.io/api/wxpay/pay";//通知地址   
		        String trade_type = "JSAPI";//交易类型    
		        String openid=sk;//"替换为用户的openid";//用户标识   
		        /**/   
		        PaymentPo paymentPo = new PaymentPo();   
		        paymentPo.setAppid(appid);   
		        paymentPo.setMch_id(mch_id);   
		        paymentPo.setNonce_str(nonce_str);  
		       
		        //String newbody=StringUtil.StrToUtf8(body);//以utf-8编码放入paymentPo，微信支付要求字符编码统一采用UTF-8字符编码   
		        String newbody=body;//本身为UTF-8 转码后是？
		        
		        paymentPo.setBody(newbody);   
		        paymentPo.setOut_trade_no(out_trade_no);   
		        paymentPo.setTotal_fee(total_fee);   
		        paymentPo.setSpbill_create_ip(spbill_create_ip);   
		        paymentPo.setNotify_url(notify_url);   
		        paymentPo.setTrade_type(trade_type);   
		        paymentPo.setOpenid(openid);   
		        // 把请求参数打包成数组   
		        Map sParaTemp = new HashMap();   
		        sParaTemp.put("appid", paymentPo.getAppid());   
		        sParaTemp.put("mch_id", paymentPo.getMch_id());   
		        sParaTemp.put("nonce_str", paymentPo.getNonce_str());   
		        sParaTemp.put("body",  paymentPo.getBody());   
		        sParaTemp.put("out_trade_no", paymentPo.getOut_trade_no());   
		        sParaTemp.put("total_fee",paymentPo.getTotal_fee());   
		        sParaTemp.put("spbill_create_ip", paymentPo.getSpbill_create_ip());   
		        sParaTemp.put("notify_url",paymentPo.getNotify_url());   
		        sParaTemp.put("trade_type", paymentPo.getTrade_type());   
		        sParaTemp.put("openid", paymentPo.getOpenid());   
		        // 除去数组中的空值和签名参数   
		        Map sPara = PayUtil.paraFilter(sParaTemp);   
		        String prestr = PayUtil.createLinkString(sPara); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串   
		        String key = "&key="+mch_key; // 商户支付密钥   
		        //MD5运算生成签名   
		        String mysign = PayUtil.sign(prestr, key, "utf-8").toUpperCase();   
		        paymentPo.setSign(mysign);   
		        //打包要发送的xml   
		        String respXml = MessageUtil.messageToXML(paymentPo);   
		        // 打印respXml发现，得到的xml中有“__”不对，应该替换成“_” 
		        System.out.println(respXml);
		        respXml = respXml.replace("__", "_");   
		        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";//统一下单API接口链接   
		        String param = respXml;   
		       // System.out.println(param);
		        //String result = SendRequestForUrl.sendRequest(url, param);//发起请求   
		        String result =PayUtil.httpRequest(url, "POST", param); 
		        //System.out.println(result);
		        // 将解析结果存储在HashMap中   
		        Map map = new HashMap();  
		        try{
		         InputStream in=new ByteArrayInputStream(result.getBytes());    
		        // 读取输入流   
		        SAXReader reader = new SAXReader();   
		        Document document = reader.read(in);   
		        // 得到xml根元素   
		        Element root = document.getRootElement();   
		        // 得到根元素的所有子节点   
		        @SuppressWarnings("unchecked")   
		        List<Element> elementList = root.elements();   
		        for (Element element : elementList) {   
		            map.put(element.getName(), element.getText());   
		        }  
		        }catch(Exception e){
		        	e.printStackTrace();
		        }
		        // 返回信息   
		        String return_code = map.get("return_code").toString();//返回状态码   
		        String return_msg = map.get("return_msg").toString();//返回信息   
		        System.out.println("return_msg"+return_msg);   
		        JSONObject JsonObject=new JSONObject() ;   
		        if(return_code=="SUCCESS"||return_code.equals(return_code)){   
		            // 业务结果   
		            String prepay_id = map.get("prepay_id").toString();//返回的预付单信息   
		            String nonceStr=UUIDHexGenerator.generate();   
		            JsonObject.put("nonceStr", nonceStr);   
		            JsonObject.put("package", "prepay_id="+prepay_id);   
		            Long timeStamp= System.currentTimeMillis()/1000;   
		            JsonObject.put("timeStamp", timeStamp+"");   
		            String stringSignTemp = "appId="+appid+"&nonceStr=" + nonceStr + "&package=prepay_id=" + prepay_id+ "&signType=MD5&timeStamp=" + timeStamp;   
		            //再次签名 
		            
		            String paySign=PayUtil.sign(stringSignTemp, "&key="+mch_key, "utf-8").toUpperCase();   
		            JsonObject.put("paySign", paySign);   
		           // jsonArray.add(JsonObject);   
		        }  
		        System.out.println(JsonObject);
		        //把支付内容存入DB  
		        //total_fee  out_trade_no id
		        XcxAppointmentDao.updateNoFee(total_fee, out_trade_no,id);
		        return JsonObject;   
				
				
				
				
			}
		});
		
		/**
		 * 小程序得到wxpay/pay
		 * 微信异步接口
		 */
		Spark.post(new Route("/wxpay/pay") {
			@Override
			public Object handle(Request request, Response response) {
				DataInputStream in;  
				 // 将解析结果存储在HashMap中   
		        Map map = new HashMap(); 
			      try {  
			      in = new DataInputStream(request.raw().getInputStream());  
			   // 读取输入流   
			        SAXReader reader = new SAXReader();   
			        Document document = reader.read(in);   
			        // 得到xml根元素   
			        Element root = document.getRootElement();   
			        // 得到根元素的所有子节点   
			        @SuppressWarnings("unchecked")   
			        List<Element> elementList = root.elements();   
			        for (Element element : elementList) {   
			            map.put(element.getName(), element.getText());   
			        }  
			      String out_trade_no =  map.get("out_trade_no").toString();//商户单号
			      String result_code = map.get("result_code").toString();//是否支付成功  SUCCESS
			      if("SUCCESS".equals(result_code)){
			    	  System.out.println("商户单号支"+out_trade_no+"付成功！");
			    	  //修改状态
			    	  XcxAppointmentDao.updateType(out_trade_no);
			      }else{
			    	  System.out.println("商户单号支"+out_trade_no+"付失败！");
			      }
			      } catch (Exception e) {  
			    	  e.printStackTrace();  
			      }  
			      
			    
			      
			      return "";
			}
		});
		///info/del
		/**
		 * 小程序得到///info/del
		 * 删除发布的信息
		 */
		Spark.post(new Route("/info/del") {
			@Override
			public Object handle(Request request, Response response) {
				String sk = request.raw().getParameter("sk");
				String id = request.raw().getParameter("id");
				int uid = UserInfoDao.getUserId(sk);
				Map<Object,Object> m = new HashMap<Object,Object>();
				//首先查询一下是否有人支付或预定
				int query = XcxAppointmentDao.Appoitmen(id);
				if(query == 0){
					if(XcxInfoDao.deleteInfo(id,uid)>0){
						m.put("status", 1);
						m.put("msg", "删除成功");
					}else{
						m.put("status", 0);
						m.put("msg", "删除失败");
					}
				}else{
					m.put("status", 0);
					m.put("msg", "已有人预定，不允许删除！");
				}
				
				
				return JSONUtils.getJson(m);
			}
		});
		
		
		
		/****************PAGE*************************/
		/**
		 * 页面查询 全部拼车列表
		 * 
		 */
		Spark.post(new Route("/pages/GetCarList") {
			@Override
			public Object handle(Request request, Response response) {
				String sEcho = request.raw().getParameter("sEcho");
				if(sEcho == null)
					sEcho = "0";
				String gender = request.raw().getParameter("gender");
				String iDisplayStart =request.raw().getParameter("iDisplayStart");
				String iDisplayLength = request.raw().getParameter("iDisplayLength");
				 Vector<Map<Object,Object>> v =XcxInfoDao.getInfoList(iDisplayStart,iDisplayLength,gender);
				 int i = XcxInfoDao.getInfoListNum(gender);
				Map<Object,Object> m = new HashMap<Object,Object>();
				m.put("recordsFiltered",i );
				m.put("draw", sEcho);
				m.put("recordsTotal", i);
				m.put("data", v);
				//String m = "{ \"recordsFiltered\":2512, \"data\":[{\"firstName\":\"Bzf\",\"id\":1,\"lastName\":\"Hazkm\"},{\"firstName\":\"Imxi\",\"id\":2,\"lastName\":\"Oieb\"},{\"firstName\":\"Glyag\",\"id\":3,\"lastName\":\"Gvqlf\"},{\"firstName\":\"Lwbhl\",\"id\":4,\"lastName\":\"Fvvf\"},{\"firstName\":\"Audds\",\"id\":5,\"lastName\":\"Seunp\"},{\"firstName\":\"Otbrq\",\"id\":6,\"lastName\":\"Hnal\"},{\"firstName\":\"Loji\",\"id\":7,\"lastName\":\"Qicn\"},{\"firstName\":\"Rjy\",\"id\":8,\"lastName\":\"Emrygr\"},{\"firstName\":\"Gcglkd\",\"id\":9,\"lastName\":\"Ldgrs\"},{\"firstName\":\"Txh\",\"id\":10,\"lastName\":\"Qwe\"}],  \"draw\":\"1\",  \"recordsTotal\":2512}  ";
				
				//response.type("application/json;charset=utf-8");
				//return m;
				response.type("application/json;charset=utf-8");
				return JSONUtils.getJson(m);
			}
		});
	}
}