<%--
  Created by IntelliJ IDEA.
  User: 吴东枚
  Date: 2020/4/22/022
  Time: 16:26
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
    <div class="message">小鸽快跑-添加用户兴趣</div>
    <div id="darkbannerwrap"></div>
    <form action="${ctx}/interest/addInterests" method="POST"  class="layui-form" >
        <input name="user_id" id="user_id" placeholder="用户ID" type="text" lay-verify="required" class="layui-input"  value="${interests.user_id}">
        <hr class="hr15">
        <input name="outdoor" id="outdoor" placeholder="户外（喜欢1，不喜欢0）" type="text" lay-verify="required" class="layui-input" value="${interests.outdoor}">
        <hr class="hr15">
        <input name="music" id="music" placeholder="音乐（喜欢1，不喜欢0）" lay-verify="required"  type="text" class="layui-input" value="${interests.music}">
        <hr class="hr15">
        <input name="film" id="film" placeholder="电影（喜欢1，不喜欢0）" type="text" lay-verify="required" class="layui-input" value="${interests.film}">
        <hr class="hr15">
        <input name="society" id="society"  placeholder="社会活动（喜欢1，不喜欢0）" type="text" lay-verify="required" class="layui-input" value="${interests.society}">
        <hr class="hr15">
        <input name="delicacy" id="delicacy" placeholder="美食（喜欢1，不喜欢0）" placeholder="" lay-verify="required"  type="text" class="layui-input" value="${interests.delicacy}">
        <hr class="hr15">
        <input name="science" id="science" placeholder="科学活动（喜欢1，不喜欢0）"  type="text" lay-verify="required" class="layui-input" value="${interests.science}">
        <hr class="hr15">
        <input name="star" id="star" placeholder="追星（喜欢1，不喜欢0）" lay-verify="required"   type="text" class="layui-input" value="${interests.star}">
        <hr class="hr15">
        <input name="comic" id="comic" placeholder="喜剧（喜欢1，不喜欢0）" lay-verify="required"   type="text" class="layui-input" value="${interests.comic}">
        <hr class="hr15">
        <input value="添加用户兴趣" lay-submit lay-filter="add" style="width:100%;" type="submit">
        <hr class="hr20" >
    </form>
</div>

</body>
</html>
