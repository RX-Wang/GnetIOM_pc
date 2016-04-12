<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<!-- 百度地图 -->
		<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
		<!-- 地图样式 -->
		<style type="text/css">
			body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
			</style> 
			
		<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=RR6UFDbDRoZQcEGUFTpunGGT"></script>	
		
		<title>地图</title>
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta content="width=device-width, initial-scale=1" name="viewport"/>
	    <meta content="" name="description"/>
	    <meta content="" name="author"/>
	    <link rel="stylesheet" href="../../assets/plugins/font-awesome/css/font-awesome.min.css" type="text/css"/><!--字体图标-->
	  <!--  <link rel="stylesheet" href="../../asset/plugins/ElegantIcons/style.css">字体图标-->
	    <link rel="stylesheet" href="../../asset/plugins/simple-line-icons/simple-line-icons.min.css"><!--字体图标-->
	    <link rel="stylesheet" href="../../css/bootstrap.css">
	    <link rel="stylesheet" href="../../assets/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css" type="text/css"/><!--table-->
	    <link rel="stylesheet" href="../../css/components.css">
	    <link rel="stylesheet" href="../../assets/css/plugins.css"><!--BASE-->
	    <link rel="stylesheet" href="../../css/main.css" >
	    <script src="../../js/jquery.min.js"></script>
	    <script src="../../js/check.js"></script>
	    <script src="../../js/global.js"></script>
	    <script src="../../js/bootstrap.min.js"></script>
	    <script src="../../js/normal.js"></script>
	    <!-- <script type="text/javascript" src="../../js/alarm/alarm.js"></script> -->
	    <script type="text/javascript" src="../../jQuery/maputils.js"></script>
	    <script src="../../js/layer/layer.js"></script>
	</head>

<body onload="mapOnload();">
<div class="o_warpper">
    <!-- BEGIN HEADER -->
    <div class="o_header" style="z-index: 10">
        <a href="javascript:void(0)" class="top_back" onclick="window.location.href='../../web/pageController/toIndex'"><span class="icon-arrow-left"></span>地图</a>
        <ul class="o_topnav" id="allMsg">
            <li><!-- class="active" -->
                <a href="javascript:void(0)" onclick="window.location.href='../../web/pageController/toAllMsg'"><i class="icon-list"></i>全部<span class="badge badge-warning"></span></a>
            </li>
            <li>
                <a href="javascript:void(0)" onclick="window.location.href='../../web/pageController/toAlarm'"><i class="icon-bell"></i>报警<span class="badge badge-danger"></span></a>
            </li>
            <li>
                <a href="javascript:void(0)" onclick="window.location.href='../../web/pageController/toThing'"><i class="icon-note"></i>事件<span class="badge badge-success"></span></a>
            </li>
            <li>
                <a href="javascript:void(0)" onclick="window.location.href='../../web/pageController/toFault'"><i class="icon-ban"></i>故障<span class="badge badge-primary"></span></a>
            </li>

        </ul>
        <div class="top_right">
            <a class="btn_lock"><span class="icon-lock"></span></a>
            <a class="top_user"></a>
             <a class="top_logout" onclick="opLogout();">注销</a>
        </div>
    </div>
    <!-- END HEADER -->
    <!-- start main map -->
    <div class="main_box" style="height: 76%;">
        <!-- <div class="main_map">
        
        </div> -->
        
 <!--------------- 新添加 -->
        <div class="map_search" style="margin-top: 40px;">
              <form class="form-inline">
                  <div class="input-icon right">
                      <i class="icon-magnifier" data-toggle="modal" href="#history" onclick="selPosition();"></i>
                      <input type="text" id="forSearchUniqueId" class="form-control input-large" placeholder="请输入设备号">
                  </div>
              </form>
        </div>
                    <!-- <div class="map_tips">
                        <span>电量：70%</span>
                        <span>在线<i class="fa fa-check-circle"></i></span>
                    </div> 
           <div class="map_right">
               <div class="map_top">
                   <a class="pull-left"><i class="glyphicon glyphicon-star"></i></a>
                   <a class="pull-right"><i class="glyphicon glyphicon-th-list"></i></a>
               </div>
               <div class="map_dev_list">
                   <span class="map_pre"></span>
                   <ul class="">
                       <li><a>二区设备1号</a><em class=""></em> </li>
                   </ul>
                   <span class="map_next"></span>
               </div>
           </div>-->
 <!----------------------- 新添加 -->
       <div id="allmap"></div>
    </div>
    <!-- end main map -->
    <!-- start foot -->
    <div class="main_foot" style="height:27%">
        <table class="table_one table table-bordered table-hover table-striped dataTable"><!-- foot_table -->
	        <thead>   
	            <tr>
	                <th style="width:3%"></th>
	                <th style="width:20%">设备名称</th>
	                <th style="width:20%">设备编号</th>
	                <th style="width:20%">报警类型</th>
	                <th style="width:20%">地址</th>
	                <th style="width:20%">时间</th>
	            </tr>
	         </thead>
        </table>
        <div style="overflow:auto;height:70%">
            <table class="table_one table table-bordered table-hover table-striped dataTable"  id="table1">
		            <tbody  id="tbAllMsg">
		            		
		            </tbody>
            </table>
        </div>

    </div>
    <!-- END foot -->
</div>
</body>
<!-- 地图展示 -->
<script type="text/javascript">
$(function(){
	// 百度地图API功能
	var map = new BMap.Map("allmap");    // 创建Map实例
	map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);  // 初始化地图,设置中心点坐标和地图级别
	map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
	map.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
	map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
});
</script>
<!-- GPS转百度 -->
<!-- <script type="text/javascript">
    // 百度地图API功能
    //GPS坐标
    var x = 116.32715863448607;
    var y = 39.990912172420714;
    var ggPoint = new BMap.Point(x,y);

    //地图初始化
    var bm = new BMap.Map("allmap");
    bm.centerAndZoom(ggPoint, 15);
    bm.addControl(new BMap.NavigationControl());

    //添加gps marker和label
    var markergg = new BMap.Marker(ggPoint);
    bm.addOverlay(markergg); //添加GPS marker
    var labelgg = new BMap.Label("未转换的GPS坐标（错误）",{offset:new BMap.Size(20,-10)});
    markergg.setLabel(labelgg); //添加GPS label

    //坐标转换完之后的回调函数
    translateCallback = function (data){
      if(data.status === 0) {
        var marker = new BMap.Marker(data.points[0]);
        bm.addOverlay(marker);
        var label = new BMap.Label("转换后的百度坐标（正确）",{offset:new BMap.Size(20,-10)});
        marker.setLabel(label); //添加百度label
        bm.setCenter(data.points[0]);
      }
    }

    setTimeout(function(){
        var convertor = new BMap.Convertor();
        var pointArr = [];
        pointArr.push(ggPoint);
        convertor.translate(pointArr, 1, 5, translateCallback)
    }, 1000);
</script>  -->


 <script type="text/javascript" src="../../js/map/map.js"></script>
 <script type="text/javascript" src="../../js/devicecontrol.js"></script>
<script src="../../js/global.js"></script>
<!-- <script src="../../js/table.js"></script> -->
<!-- <script type="text/javascript" src="../../assets/plugins/datatables/media/js/jquery.dataTables.min.js"></script> --><!-- 表格插件 -->
<!-- <script type="text/javascript" src="../../assets/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js"></script> --><!-- 表格插件 -->
</html>
<!-- 地图 -->
