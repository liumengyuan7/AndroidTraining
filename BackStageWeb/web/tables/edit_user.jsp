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
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
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
    <div class="message">小鸽快跑-修改用户</div>
    <div id="darkbannerwrap"></div>
       <form action="${ctx}/user/editUsers" method="POST"  class="layui-form" >
           ID
           <input name="id" id="id" type="text" readonly="readonly" class="layui-input" value="${users.id}">
           <hr class="hr15">
           用户姓名
           <input name="userName" id="userName"  type="text" lay-verify="required" class="layui-input"  value="${users.userName}">
           <hr class="hr15">
           用户昵称
           <input name="userNickname" id="userNickname" type="text" lay-verify="required" class="layui-input" value="${users.userNickname}">
           <hr class="hr15">
           密码
           <input name="password" id="password" lay-verify="required"  type="password" class="layui-input" value="${users.password}">
           <hr class="hr15">
           性别
           <input name="userSex" id="userSex"  type="text" lay-verify="required" class="layui-input" value="${users.userSex}">
           <hr class="hr15">
           邮箱
           <input name="email" id="email"  type="text" lay-verify="required" class="layui-input" value="${users.userEmail}">
           <hr class="hr15">
           注册时间
           <input name="userRegisterTime" id="userRegisterTime" lay-verify="required"  type="date" class="layui-input" value="${users.userRegisterTime}">
           <hr class="hr15">
           学号
           <input name="userSno" id="userSno"   type="text" lay-verify="required" class="layui-input" value="${users.userSno}">
           <hr class="hr15">
           用户积分
           <input name="userPoints" id="userPoints"   type="text" lay-verify="required" class="layui-input" value="${users.userPoints}">
           <hr class="hr15">
           匹配
           <input name="matcher" id="matcher" lay-verify="required"   type="text" class="layui-input" value="${users.matcher}">
           <hr class="hr15">
           是否达到标准
           <input name="isAcc" id="isAcc" lay-verify="required"   type="text" class="layui-input" value="${users.isAcc}">
           <hr class="hr15">
           <input value="修改用户" lay-submit lay-filter="edit" style="width:100%;" type="submit">
           <hr class="hr20" >
       </form>
</div>
</body>
</html>
