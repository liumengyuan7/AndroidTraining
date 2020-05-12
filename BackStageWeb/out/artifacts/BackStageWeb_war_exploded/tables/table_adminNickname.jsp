<%--
  Created by IntelliJ IDEA.
  User: 吴东枚
  Date: 2020/5/1/001
  Time: 12:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>后台登录-个人信息显示</title>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script>
        // 是否开启刷新记忆tab功能
        // var is_remember = false;
    </script>
    <style>
        table{
            width: 1050px; min-height: 25px; line-height: 25px; text-align: center; border-collapse: collapse;

        }
        table,table tr th, table tr td {border: 1px solid black;}
        td{
            font-size: 13px;
        }
    </style>
</head>
<body>
<h2 style="text-align: center;color: #009f95">table_admin</h2>
<div style="width: 1050px">
    <table>
        <thead>
        <tr>
            <th>
                #
            </th>
            <th>
                邮箱地址
            </th>
            <th>
                密码
            </th>
            <th>
                昵称
            </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${admin}" var="admin" >
            <tr>
                <td>
                        ${admin.id}
                </td>
                <td>
                        ${admin.email}
                </td>
                <td>
                        ${admin.password}
                </td>
                <td>
                        ${admin.nickName}
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<!-- 右侧主体结束 -->
<!-- 中部结束 -->
<script>//百度统计可去掉
var _hmt = _hmt || []; (function() {
    var hm = document.createElement("script");
    hm.src = "https://hm.baidu.com/hm.js?b393d153aeb26b46e9431fabaf0f6190";
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(hm, s);
})();</script>
</body>
</html>
