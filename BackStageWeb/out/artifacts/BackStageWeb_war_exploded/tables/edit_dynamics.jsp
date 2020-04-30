<%--
  Created by IntelliJ IDEA.
  User: 吴东枚
  Date: 2020/4/30/030
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>SmallPigeon EditDynamics</title>
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
    <div class="message">小鸽快跑-修改动态</div>
    <div id="darkbannerwrap"></div>
    <form action="${ctx}/dynamics/editDynamics" method="POST"  class="layui-form" >
        ID
        <input name="id" id="id" type="text" readonly="readonly" class="layui-input" value="${dynamics.id}">
        <hr class="hr15">
        用户ID
        <input name="userId" id="userId"  type="text" lay-verify="required" class="layui-input"  value="${dynamics.userId}">
        <hr class="hr15">
        发送动态时间
        <input name="pushTime" id="pushTime" type="date" lay-verify="required" class="layui-input" value="${dynamics.pushTime}">
        <hr class="hr15">
        发送动态内容
        <input name="pushContent" id="pushContent" type="text" readonly="readonly" class="layui-input" value="${dynamics.pushContent}">
        <hr class="hr15">
        发送动态图片
        <input name="pushImage" id="pushImage"  type="text" lay-verify="required" class="layui-input"  value="${dynamics.pushImage}">
        <hr class="hr15">
        点赞数量
        <input name="zanNum" id="zanNum" type="text" lay-verify="required" class="layui-input" value="${dynamics.zanNum}">
        <hr class="hr15">
        转发ID
        <input name="forwardId" id="forwardId" type="text" lay-verify="required" class="layui-input" value="${dynamics.forwardId}">
        <hr class="hr15">
        <input value="修改好友" lay-submit lay-filter="edit" style="width:100%;" type="submit">
        <hr class="hr20" >
    </form>
</div>
</body>
</html>
