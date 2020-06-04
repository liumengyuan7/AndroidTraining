<%@ page contentType="text/html;charset=UTF-8" language="JAVA" pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<html class="x-admin-sm">
<head>
	<meta charset="UTF-8">
	<title>后台登录-小鸽快跑</title>
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1,text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
	<meta http-equiv="Cache-Control" content="no-siteapp" />
	<link rel="stylesheet" href="${ctx}/css/font.css">
	<link rel="stylesheet" href="${ctx}/css/login.css">
	<link rel="stylesheet" href="${ctx}/css/xadmin.css">
	<script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
	<script src="${ctx}/lib/layui/layui.js" charset="utf-8"></script>
	<!--[if lt IE 9]>
	<script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
	<script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->
</head>
<body class="login-bg">
<div class="login layui-anim layui-anim-up">
	<div class="message">小鸽快跑-管理注册</div>
	<div id="darkbannerwrap"></div>
		<form method="post"  action="${ctx}/admin/register" class="layui-form"  accept-charset="UTF-8">
			<input name="nickName" id="nickName" placeholder="昵称"  type="text" lay-verify="required" class="layui-input" >
			<hr class="hr15">
			<input name="email" id="email" placeholder="邮箱"  type="text" lay-verify="required" class="layui-input" >
			<hr class="hr15">
			<input name="password" id="password" lay-verify="required" placeholder="密码"  type="password" class="layui-input">
			<hr class="hr15">
			<input type="checkbox" name="agree" value="1"> 我同意条款和条件
			<hr class="hr15">
			<input value="register" lay-submit lay-filter="register" style="width:100%;" type="submit">
			<hr class="hr20" >
			<div style="float: right">
				已经有账户了？ <a href="${ctx}/login.jsp">登录</a>
			</div>
		</form>
</div>
</body>
</html>