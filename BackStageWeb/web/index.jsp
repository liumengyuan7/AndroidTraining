<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<html class="x-admin-sm">
    <head>
        <meta charset="UTF-8">
        <title>后台登录-小鸽快跑</title>
        <meta name="renderer" content="webkit|ie-comp|ie-stand">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
        <meta http-equiv="Cache-Control" content="no-siteapp" />
        <link rel="stylesheet" href="${ctx}/css/font.css">
        <link rel="stylesheet" href="${ctx}/css/xadmin.css">
        <!-- <link rel="stylesheet" href="./css/theme5.css"> -->
        <script src="${ctx}/lib/layui/layui.js" charset="utf-8"></script>
        <script type="text/javascript" src="${ctx}/js/xadmin.js"></script>
        <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
        <!--[if lt IE 9]>
          <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
          <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
        <script>
            // 是否开启刷新记忆tab功能
            // var is_remember = false;
        </script>
    </head>
    <body class="index">
        <!-- 顶部开始 -->
        <div class="container">
            <div class="logo">
                <a href="${ctx}/admin/login">smallpigeon</a></div>
            <div class="left_open">
                <a><i title="展开左侧栏" class="iconfont">&#xe699;</i></a>
            </div>
            <ul class="layui-nav left fast-add" lay-filter="">
                <li class="layui-nav-item">
                    <a href="javascript:;">+新增</a>
                    <dl class="layui-nav-child">
                        <!-- 二级菜单 -->
                        <dd>
                            <a onclick="xadmin.open('最大化','http://www.baidu.com','','',true)">
                                <i class="iconfont">&#xe6a2;</i>弹出最大化</a></dd>
                        <dd>
                            <a onclick="xadmin.open('弹出自动宽高','http://www.baidu.com')">
                                <i class="iconfont">&#xe6a8;</i>弹出自动宽高</a></dd>
                        <dd>
                            <a onclick="xadmin.open('弹出指定宽高','http://www.baidu.com',500,300)">
                                <i class="iconfont">&#xe6a8;</i>弹出指定宽高</a></dd>
                        <dd>
                            <a onclick="xadmin.add_tab('在tab打开','table-user.jsp')">
                                <i class="iconfont">&#xe6b8;</i>在tab打开</a></dd>
                        <dd>
                            <a onclick="xadmin.add_tab('在tab打开刷新','member-del.html',true)">
                                <i class="iconfont">&#xe6b8;</i>在tab打开刷新</a></dd>
                    </dl>
                </li>
            </ul>
            <ul class="layui-nav right" lay-filter="">
                <li class="layui-nav-item">
                    <a href="javascript:;">${sessionScope.admin.nickName}</a>
                    <dl class="layui-nav-child">
                        <!-- 二级菜单 -->
                        <dd>
                            <a href="${ctx}/login_out.jsp">切换帐号</a></dd>
                        <dd>
                            <a href="${ctx}/login_out.jsp">退出</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item to-index">
                    <a href="/">前台首页</a></li>
            </ul>
        </div>
        <!-- 顶部结束 -->
        <!-- 中部开始 -->
        <!-- 左侧菜单开始 -->
        <div class="left-nav">
            <div id="side-nav">
                <ul id="nav">
                    <li>
                        <a href="javascript:;">
                            <i class="iconfont left-nav-li" lay-tips="用户管理">&#xe6b8;</i>
                            <cite>用户管理</cite>
                            <i class="iconfont nav_right">&#xe697;</i></a>
                        <ul class="sub-menu">
                            <li>
                                <a onclick="xadmin.add_tab('统计页面','${ctx}/welcome1.jsp')">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite>统计页面</cite></a>
                            </li>
                            <li>
                                <a onclick="xadmin.add_tab('用户列表(动态表格)','${ctx}/user/getAll',true)">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite>用户列表(动态表格)</cite></a>
                            </li>
                            <li>
                                <a onclick="xadmin.add_tab('用户添加','${ctx}/user/addUsers',true)">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite>用户添加</cite></a>
                            </li>
                            <li>
                                <a href="javascript:;">
                                    <i class="iconfont">&#xe70b;</i>
                                    <cite>兴趣管理</cite>
                                    <i class="iconfont nav_right">&#xe697;</i></a>
                                <ul class="sub-menu">
                                    <li>
                                        <a onclick="xadmin.add_tab('兴趣列表','${ctx}/interest/getAll')">
                                            <i class="iconfont">&#xe6a7;</i>
                                            <cite>兴趣列表</cite></a>
                                    </li>
                                    <li>
                                        <a onclick="xadmin.add_tab('兴趣添加','${ctx}/interest/addInterests')">
                                            <i class="iconfont">&#xe6a7;</i>
                                            <cite>兴趣添加</cite></a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="javascript:;">
                            <i class="iconfont left-nav-li" lay-tips="好友管理">&#xe723;</i>
                            <cite>好友管理</cite>
                            <i class="iconfont nav_right">&#xe697;</i></a>
                        <ul class="sub-menu">
                            <li>
                                <a onclick="xadmin.add_tab('好友列表','${ctx}/friend/getAll')">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite>好友列表</cite></a>
                            </li>
                            <li>
                                <a onclick="xadmin.add_tab('好友添加','${ctx}/friend/addFriends')">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite>好友添加</cite></a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="javascript:;">
                            <i class="iconfont left-nav-li" lay-tips="跑步记录管理">&#xe723;</i>
                            <cite>跑步记录管理</cite>
                            <i class="iconfont nav_right">&#xe697;</i></a>
                        <ul class="sub-menu">
                            <li>
                                <a onclick="xadmin.add_tab('记录列表','${ctx}/record/getAll')">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite>记录列表</cite></a>
                            </li>
                            <li>
                                <a onclick="xadmin.add_tab('记录添加','${ctx}/record/addRecords')">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite>记录添加</cite></a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="javascript:;">
                            <i class="iconfont left-nav-li" lay-tips="跑步计划管理">&#xe723;</i>
                            <cite>跑步计划管理</cite>
                            <i class="iconfont nav_right">&#xe697;</i></a>
                        <ul class="sub-menu">
                            <li>
                                <a onclick="xadmin.add_tab('跑步计划列表','${ctx}/plan/getAll')">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite>跑步计划列表</cite></a>
                            </li>
                            <li>
                                <a onclick="xadmin.add_tab('跑步计划添加','${ctx}/plan/addPlans')">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite>跑步计划添加</cite></a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="javascript:;">
                            <i class="iconfont left-nav-li" lay-tips="管理员管理">&#xe726;</i>
                            <cite>管理员管理</cite>
                            <i class="iconfont nav_right">&#xe697;</i></a>
                        <ul class="sub-menu">
                            <li>
                                <a onclick="xadmin.add_tab('管理员列表','${ctx}/admin/getAll')">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite>管理员列表</cite></a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="javascript:;">
                            <i class="iconfont left-nav-li" lay-tips="动态管理">&#xe6b4;</i>
                            <cite>动态管理</cite>
                            <i class="iconfont nav_right">&#xe697;</i></a>
                        <ul class="sub-menu">
                            <li>
                                <a onclick="xadmin.add_tab('动态列表','${ctx}/dynamics/getAll')">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite>动态列表</cite></a>
                            </li>
                            <li>
                                <a onclick="xadmin.add_tab('动态添加','${ctx}/dynamics/addDynamics')">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite>动态添加</cite></a>
                            </li>
                            <li>
                                <a href="javascript:;">
                                    <i class="iconfont left-nav-li" lay-tips="评论管理">&#xe723;</i>
                                    <cite>评论管理</cite>
                                    <i class="iconfont nav_right">&#xe697;</i></a>
                                <ul class="sub-menu">
                                    <li>
                                        <a onclick="xadmin.add_tab('评论列表','${ctx}/reply/getAll')">
                                            <i class="iconfont">&#xe6a7;</i>
                                            <cite>评论列表</cite></a>
                                    </li>
                                    <li>
                                        <a onclick="xadmin.add_tab('评论添加','${ctx}/reply/addReply')">
                                            <i class="iconfont">&#xe6a7;</i>
                                            <cite>评论添加</cite></a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="javascript:;">
                            <i class="iconfont left-nav-li" lay-tips="其它页面">&#xe6b4;</i>
                            <cite>其它页面</cite>
                            <i class="iconfont nav_right">&#xe697;</i></a>
                        <ul class="sub-menu">
                            <li>
                                <a onclick="xadmin.add_tab('登录页面','${ctx}/')">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite>登录页面</cite></a>
                            </li>
                            <li>
                                <a onclick="xadmin.add_tab('错误页面','${ctx}/error.jsp')">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite>错误页面</cite></a>
                            </li>
                            <li>
                                <a onclick="xadmin.add_tab('注册页面','${ctx}/register.jsp')">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite>注册页面</cite></a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
        <!-- <div class="x-slide_left"></div> -->
        <!-- 左侧菜单结束 -->
        <!-- 右侧主体开始 -->
        <div class="page-content">
            <div class="layui-tab tab" lay-filter="xbs_tab" lay-allowclose="false">
                <ul class="layui-tab-title">
                    <li class="home">
                        <i class="layui-icon">&#xe68e;</i>我的桌面</li></ul>
                <div class="layui-unselect layui-form-select layui-form-selected" id="tab_right">
                    <dl>
                        <dd data-type="this">关闭当前</dd>
                        <dd data-type="other">关闭其它</dd>
                        <dd data-type="all">关闭全部</dd></dl>
                </div>
                <div class="layui-tab-content">
                    <div class="layui-tab-item layui-show">
                        <iframe src='${ctx}/welcome.jsp' frameborder="0" scrolling="yes" class="x-iframe"></iframe>
                    </div>
                </div>
                <div id="tab_show"></div>
            </div>
        </div>
        <div class="page-content-bg"></div>
        <style id="theme_style"></style>
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