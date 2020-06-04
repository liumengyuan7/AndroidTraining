<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
	<meta charset="utf-8">
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
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
	<div class="message">小鸽快跑-忘记密码</div>
	<div id="darkbannerwrap"></div>
		<form method="post" class="layui-form" >
			<input name="email" id="email" placeholder="邮箱"  type="text" lay-verify="required" class="layui-input" >
			<hr class="hr15">
			<input value="Reset Password" lay-submit lay-filter="login" style="width:100%;" type="submit">
			<hr class="hr20" >
			<a href="${ctx}/login.jsp" style="float:right">
				登录
			</a>
		</form>
</div>
</body>
</html>