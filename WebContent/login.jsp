<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
	session.invalidate();
%>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5shiv.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<![endif]-->
<link href="static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="static/h-ui.admin/css/H-ui.login.css" rel="stylesheet" type="text/css" />
<link href="static/h-ui.admin/css/style.css" rel="stylesheet" type="text/css" />
<link href="lib/Hui-iconfont/1.0.8/iconfont.css" rel="stylesheet" type="text/css" />
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>后台登录 - 云拼车</title>
<meta name="keywords" content="">
<meta name="description" content="">
</head>
<body>
<input type="hidden" id="TenantId" name="TenantId" value="" />

<div class="loginWraper">
  <div id="loginform" class="loginBox">
    <form id="ff"  class="form form-horizontal"  method="post">
      <div class="row cl">
        <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60d;</i></label>
        <div class="formControls col-xs-8">
          <input id="uname" name="uname" type="text" placeholder="账户" class="input-text size-L">
        </div>
      </div>
      <div class="row cl">
        <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60e;</i></label>
        <div class="formControls col-xs-8">
          <input id="upassword" name="upassword" type="password" placeholder="密码" class="input-text size-L">
        </div>
      </div>
      <div class="row cl">
        <div class="formControls col-xs-8 col-xs-offset-3">
          <input class="input-text size-L" id="code" name = "code" type="text" placeholder="验证码" onblur="if(this.value==''){this.value='验证码:'}" onclick="if(this.value=='验证码:'){this.value='';}" value="验证码:" style="width:150px;">
          <img id="image" src="<%=request.getContextPath() %>/app/image"> <a id="kanbuq" href="javascript:reload();">看不清，换一张</a> </div>
      </div>
      <div class="row cl">
        <div class="formControls col-xs-8 col-xs-offset-3 c-red">
          <label id="lable">
          </label>
        </div>
      </div>
      <div class="row cl">
        <div class="formControls col-xs-8 col-xs-offset-3">
          <input name="" type="submit"  class="btn btn-success radius size-L" value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;">
          <input name="" type="reset" class="btn btn-default radius size-L" value="&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;">
        </div>
      </div>
    </form>
  </div>
</div>
<div class="footer">Copyright 云喇叭 </div>
<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript">  
     function reload(){  
        document.getElementById("image").src="<%=request.getContextPath() %>/app/image?date="+new Date().getTime();  
        $("#checkcode").val("");   // 将验证码清空  
    } 
     
     $(document).ready(function(){
    	  $("form").submit(function(e){
    	    var name = $('#uname').val();
    	    var upassword = $('#upassword').val();
    	    var code = $('#code').val();
    	    if(name.length==0){
    	    	$("#lable").text("用户名不能为空!");
    	    	return false;
    	    }
    	    if(upassword.length==0){
    	    	$("#lable").text("密码不能为空!");
    	    	return false;
    	    }
    	    if(code.length==0 ||code == '验证码:'){
    	    	$("#lable").text("验证码不能为空!");
    	    	return false;
    	    }
    	    var params = $("form").serialize();
    	    $.ajax( {  
                type : "POST",  
                url : "app/login",  
                data : params,  
                success : function(msg) {  
              
                   if(msg == "-1"){
                	   $("#lable").text("验证码错误!");
                   }else if(msg == "2"){
                	   $("#lable").text("用户名错误!");
                   }else if(msg == "3"){
                	   $("#lable").text("密码错误!");
                   }else if(msg == "1"){
                	   window.location.href = "index.jsp";
                   }else{
                	   $("#lable").text("其它错误，请联系管理员!");
                   }
                }  
            });
    	    return false;
        });
    });
    	
  	
</script> 
</body>
</html>