<%@ page contentType="text/html;charset=UTF-8" language="JAVA" %>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<html class="x-admin-sm">
<head>
	<meta charset="UTF-8">
	<title>后台登录-小鸽快跑</title>
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
        <div class="message">小鸽快跑-管理登录</div>
        <div id="darkbannerwrap"></div>
        <c:if test="${empty admin}">
        <form method="post"  action="${ctx}/admin/login" class="layui-form" >
            <input name="email" id="email" placeholder="邮箱"  type="text" lay-verify="required" class="layui-input" >
            <hr class="hr15">
            <a href="forgot.jsp" style="float:right">
                Forgot Password?
            </a>
            <input name="password" id="password"  placeholder="密码"  type="password" class="layui-input" required >
            <hr class="hr15">
            <input value="登录" lay-submit lay-filter="login" style="width:100%;" type="submit">
            <hr class="hr20" >
            <div style="float: right">
                Don't have an account? <a href="register.jsp">Create One</a>
            </div>
        </form>
        </c:if>
        <c:if test="${not empty admin}">
            <jsp:forward page="index.jsp"></jsp:forward>
        </c:if>
    </div>

</body>
</html>