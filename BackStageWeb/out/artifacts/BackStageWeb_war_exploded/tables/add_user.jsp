<%--
  Created by IntelliJ IDEA.
  User: 吴东枚
  Date: 2020/4/14/014
  Time: 17:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <title>SmallPigeon Admin</title>
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
<body>

<div class="login layui-anim layui-anim-up">
    <div class="message">小鸽快跑-添加用户</div>
    <div id="darkbannerwrap"></div>
        <form method="post" action="${ctx}/user/addUsers" class="layui-form" >
            <input name="userName" id="userName" placeholder="用户姓名"  type="text" lay-verify="required" class="layui-input" >
            <hr class="hr15">
            <input name="userNickname" id="userNickname" placeholder="用户昵称"  type="text" lay-verify="required" class="layui-input" >
            <hr class="hr15">
            <input name="password" id="password" lay-verify="required" placeholder="密码"  type="password" class="layui-input">
            <hr class="hr15">
            <input name="userSex" id="userSex" placeholder="性别"  type="text" lay-verify="required" class="layui-input" >
            <hr class="hr15">
            <input name="email" id="email" placeholder="邮箱"  type="text" lay-verify="required" class="layui-input" >
            <hr class="hr15">
            <input name="userRegisterTime" id="userRegisterTime" lay-verify="required" placeholder="注册时间"  type="date" class="layui-input">
            <hr class="hr15">
            <input name="userSno" id="userSno" placeholder="学号"  type="text" lay-verify="required" class="layui-input" >
            <hr class="hr15">
            <input name="userPoints" id="userPoints" placeholder="用户积分"  type="text" lay-verify="required" class="layui-input" >
            <hr class="hr15">
            <input name="matcher" id="matcher" lay-verify="required" placeholder="匹配人姓名"  type="text" class="layui-input">
            <hr class="hr15">
            <input name="isAcc" id="isAcc" lay-verify="required" placeholder="是否达到标准（没有达到0，达到标准1）"  type="text" class="layui-input">
            <hr class="hr15">
            <input value="添加用户" lay-submit lay-filter="login" style="width:100%;" type="submit">
            <hr class="hr20" >
        </form>
</div>

</body>
</html>
