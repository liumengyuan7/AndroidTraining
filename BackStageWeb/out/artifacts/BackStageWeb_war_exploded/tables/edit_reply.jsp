<%--
  Created by IntelliJ IDEA.
  User: 吴东枚
  Date: 2020/5/1/001
  Time: 11:00
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
    <title>SmallPigeon EditReply</title>
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
    <div class="message">小鸽快跑-修改评论回复</div>
    <div id="darkbannerwrap"></div>
    <form action="${ctx}/reply/editReply" method="POST"  class="layui-form" >
        ID
        <input name="id" id="id" type="text" readonly="readonly" class="layui-input" value="${reply.id}">
        <hr class="hr15">
        评论ID
        <input name="commentId" id="commentId"  type="text" lay-verify="required" class="layui-input"  value="${reply.commentId}">
        <hr class="hr15">
        回复人ID
        <input name="fromId" id="fromId" type="text" lay-verify="required" class="layui-input" value="${reply.fromId}">
        <hr class="hr15">
        被回复人ID
        <input name="toId" id="toId" lay-verify="required"  type="text" class="layui-input" value="${reply.toId}">
        <hr class="hr15">
        回复内容
        <input name="replyContent" id="replyContent"  type="text" lay-verify="required" class="layui-input" value="${reply.replyContent}">
        <hr class="hr15">
        <input value="修改用户" lay-submit lay-filter="edit" style="width:100%;" type="submit">
        <hr class="hr20" >
    </form>
</div>
</body>
</html>
