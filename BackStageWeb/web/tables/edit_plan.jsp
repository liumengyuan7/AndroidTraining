<%--
  Created by IntelliJ IDEA.
  User: 吴东枚
  Date: 2020/4/28/028
  Time: 15:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>SmallPigeon EditPlan</title>
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
    <div class="message">小鸽快跑-修改用户兴趣</div>
    <div id="darkbannerwrap"></div>
    <form action="${ctx}/plan/editPlans" method="POST"  class="layui-form" >
        ID
        <input name="id" id="id" type="text" readonly="readonly" class="layui-input" value="${plans.id}">
        <hr class="hr15">
        用户ID
        <input name="userId" id="userId"  type="text" lay-verify="required" class="layui-input"  value="${plans.userId}">
        <hr class="hr15">
        匹配同伴ID
        <input name="companionId" id="companionId" type="text" lay-verify="required" class="layui-input" value="${plans.companionId}">
        <hr class="hr15">
        计划设定时间
        <input name="planTime" id="planTime" lay-verify="required"  type="date" class="layui-input" value="${plans.planTime}">
        <hr class="hr15">
        计划地址
        <input name="planAddress" id="planAddress"  type="text" lay-verify="required" class="layui-input" value="${plans.planAddress}">
        <hr class="hr15">
        计划状态(是否完成)
        <input name="planStatus" id="planStatus"  type="text" lay-verify="required" class="layui-input" value="${plans.planStatus}">
        <hr class="hr15">
        <input value="修改跑步计划" lay-submit lay-filter="edit" style="width:100%;" type="submit">
        <hr class="hr20" >
    </form>
</body>
</html>
