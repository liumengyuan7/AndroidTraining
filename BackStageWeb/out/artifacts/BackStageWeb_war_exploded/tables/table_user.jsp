<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>后台登录-用户显示</title>
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
</style>
<body>
     <h2 style="text-align: center;color: #009f95">table_user</h2>
     <button onclick="window.location.href='${ctx}/user/addUsers'" style="background-color: lightblue;height: 30px;width:100px;border-radius: 236px 236px 236px 236px">
      Add
      </button>
     <div style="width: 1050px">
     <table>
         <thead>
            <tr>
               <th>
                 #
               </th>
               <th>
                 user_nickname
               </th>
              <th>
                user_sex
              </th>
              <th>
                user_email
              </th>
              <th>
                user_register_time
              </th>
              <th>
                user_sno
              </th>
              <th>
                user_points
              </th>
              <th>
                is_accreditation
              </th>
              <th>
                operate
              </th>
            </tr>
         </thead>
     <tbody>
     <c:forEach items="${users}" var="user" >
       <tr>
         <td>
             ${user.id}
         </td>
         <td>
             ${user.userNickname}
         </td>
         <td>
             ${user.userSex}
         </td>
         <td>
             ${user.userEmail}
         </td>
         <td>
             ${user.userRegisterTime}
         </td>
         <td>
             ${user.userSno}
         </td>
         <td>
             ${user.userPoints}
         </td>
         <td>
             ${user.isAcc}
         </td>
         <td>
           <button onclick="window.location.href='${ctx}/user/editUsers/'+${user.id}" style="background-color: lightblue;height: 30px;width: 80px;border-radius: 236px 236px 236px 236px;text-align: center">
             Edit
           </button>
           <button onclick="window.location.href='${ctx}/user/deleteUsers/'+${user.id}" style="background-color: lightblue;height: 30px;width: 80px;border-radius: 236px 236px 236px 236px;text-align: center">
             Delete
           </button>
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
