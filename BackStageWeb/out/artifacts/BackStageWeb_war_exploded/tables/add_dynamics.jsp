<%--
  Created by IntelliJ IDEA.
  User: 吴东枚
  Date: 2020/4/30/030
  Time: 14:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>SmallPigeon Dynamics</title>
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
    <div class="message">小鸽快跑-添加动态</div>
    <div id="darkbannerwrap"></div>
    <form method="post" action="${ctx}/dynamics/addDynamics" class="layui-form" >
        <input name="id" id="id" placeholder="ID"  type="text" lay-verify="required" class="layui-input" >
        <hr class="hr15">
        <input name="userId" id="userId" placeholder="用户ID"  type="text" lay-verify="required" class="layui-input" >
        <hr class="hr15">
        <input name="pushTime" id="pushTime" placeholder="发送动态时间"  type="date" lay-verify="required" class="layui-input" >
        <hr class="hr15">
        <input name="pushContent" id="pushContent" placeholder="发送动态内容"  type="text" lay-verify="required" class="layui-input" >
        <hr class="hr15">
        <input name="pushImage" id="pushImage" placeholder="发送动态照片"  type="text" lay-verify="required" class="layui-input" >
        <hr class="hr15">
        <input name="zanNum" id="zanNum" placeholder="点赞数量"  type="text" lay-verify="required" class="layui-input" >
        <hr class="hr15">
        <input name="forwardId" id="forwardId" placeholder="转发ID"  type="text" lay-verify="required" class="layui-input" >
        <hr class="hr15">
        <input value="添加动态" lay-submit lay-filter="login" style="width:100%;" type="submit">
        <hr class="hr20" >
    </form>
</div>
</body>
</html>
