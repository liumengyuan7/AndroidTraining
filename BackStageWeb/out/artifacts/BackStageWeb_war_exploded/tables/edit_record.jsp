<%--
  Created by IntelliJ IDEA.
  User: 吴东枚
  Date: 2020/4/28/028
  Time: 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>SmallPigeon EditRecord</title>
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
    <div class="message">小鸽快跑-修改跑步记录</div>
    <div id="darkbannerwrap"></div>
    <form action="${ctx}/record/editRecords" method="POST"  class="layui-form" >
        ID
        <input name="id" id="id" type="text" readonly="readonly" class="layui-input" value="${records.id}">
        <hr class="hr15">
        用户ID
        <input name="user_id" id="user_id"  type="text" lay-verify="required" class="layui-input"  value="${records.user_id}">
        <hr class="hr15">
        此次跑步花的时间
        <input name="recordTime" id="recordTime" type="date" lay-verify="required" class="layui-input" value="${records.recordTime}">
        <hr class="hr15">
        距离
        <input name="recordDistance" id="recordDistance" lay-verify="required"  type="text" class="layui-input" value="${records.recordDistance}">
        <hr class="hr15">
        速度
        <input name="recordSpeed" id="recordSpeed"  type="text" lay-verify="required" class="layui-input" value="${records.recordSpeed}">
        <hr class="hr15">
        此次跑步日期
        <input name="recordDate" id="recordDate"  type="date" lay-verify="required" class="layui-input" value="${records.recordDate}">
        <hr class="hr15">
        此次跑步积分
        <input name="recordPoints" id="recordPoints" lay-verify="required"  type="text" class="layui-input" value="${records.recordPoints}">
        <hr class="hr15">
        <input value="修改跑步记录" lay-submit lay-filter="edit" style="width:100%;" type="submit">
        <hr class="hr20" >
    </form>
</div>
</body>
</html>
