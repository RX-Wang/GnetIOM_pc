<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <meta content="" name="description"/>
    <meta content="" name="author"/>
    <link href="../../assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/><!--字体图标-->
    <link rel="stylesheet" type="text/css"  href="../../assets/plugins/simple-line-icons/simple-line-icons.min.css"><!--字体图标-->
    <link rel="stylesheet"  type="text/css" href="../../css/bootstrap.css"><!--base-->

    <link rel="stylesheet" type="text/css" href="../../assets/css/components.css"><!--BASE-->
    <link rel="stylesheet" href="../../assets/css/plugins.css"><!--BASE-->
    <link rel="stylesheet" type="text/css"  href="../../css/main.css"/><!--自定义-->

</head>
<body>

<div class="o_warpper">
    <div class="index_box">
        <div class="index_left">
            <a class="index_alarm color_1" onclick="toPage('../../web/pageController/toAllMsg')">
                <i class="glyphicon glyphicon-warning-sign"></i>
                <span>报警</span>
            </a>
            <a class="index_device color_2" onclick="toPage('../../web/pageController/toDevice')">
                 <i class="glyphicon glyphicon-phone"></i>
                <span>设备</span>
            </a>
            <a class="index_trace color_3" onclick="toPage('../../web/devicesController/toDevTrack')">
                 <i class="glyphicon glyphicon-plane"></i>
                <span>追踪</span>
            </a>
        </div>
        <div class="index_right">
            <a class="index_statistics color_4" onclick="toPage('../../web/pageController/toStatistics')">
                 <i class="glyphicon glyphicon-stats"></i>
                <span>统计</span>
            </a>
            <a class="index_map color_5" onclick="toPage('../../web/pageController/toMap')">
                <i class="glyphicon glyphicon-map-marker"></i>
                <span>地图</span>
            </a>
            <a class="index_system color_6" onclick="toPage('../../web/pageController/toSystem')">
                <i class="glyphicon glyphicon-cog"></i>
                <span>系统</span>
            </a>
        </div>

    </div>
    <div class="index_bg"></div>
</div>

<script src="../../js/jquery.min.js"></script>
<script src="../../js/bootstrap.min.js"></script>
<script src="../../js/normal.js"></script>
<script type="text/javascript" src="../../js/check.js"></script>
<script type="text/javascript" src="../../js/topage.js"></script>
<script src="../../js/layer/layer.js"></script>
</body>
</html>