<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <% String id= (String)request.getAttribute("uniqueId");
  	String curTime = (String)request.getAttribute("curTime");
  %>
<!DOCTYPE>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<!-- 百度地图 -->
		<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
		<!-- 地图样式 -->
		<style type="text/css">
			body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
			</style> 
			
		<!-- <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=RR6UFDbDRoZQcEGUFTpunGGT"></script>	 -->
		<title>追踪</title>
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta content="width=device-width, initial-scale=1" name="viewport"/>
	    <meta content="" name="description"/>
	    <meta content="" name="author"/>
	    <link rel="stylesheet" href="../../assets/plugins/font-awesome/css/font-awesome.min.css" type="text/css"/><!--字体图标-->
	    <link rel="stylesheet" href="../../asset/plugins/simple-line-icons/simple-line-icons.min.css"><!--字体图标-->
	    <link rel="stylesheet" href="../../css/bootstrap.css">
	    <link rel="stylesheet" href="../../assets/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css" type="text/css"/><!--table-->
	    <link rel="stylesheet" href="../../css/components.css">
	    <link rel="stylesheet" href="../../assets/css/plugins.css"><!--BASE-->
	    <link rel="stylesheet" href="../../css/main.css" >
	    <script src="../../js/jquery.min.js"></script>
	    <script src="../../js/jquery-ui-1.10.4.min.js"></script>
	    <script src="../../js/bootstrap.min.js"></script>
	    <script src="../../js/layer/layer.js"></script>
	    <script src="../../js/check.js"></script>
	    
	    <script src="../../js/normal.js"></script>
	    <script type="text/javascript" src="../../jQuery/maputils.js"></script>
	</head>
<body onload="mapOnload();">
<div class="o_warpper">
    <!-- BEGIN HEADER -->
    <div class="o_header" style="z-index: 10">
        <a href="javascript:void(0);" class="top_back" onclick="window.location.href='../../web/pageController/toIndex'"><span class="icon-arrow-left"></span>追踪</a>
        <ul class="o_topnav" id="allMsg">
            <li><!-- class="active" -->
                <a href="javascript:void(0);" onclick="window.location.href='../../web/pageController/toAllMsg'"><i class="icon-list"></i>全部<span class="badge badge-warning"></span></a>
            </li>
            <li>
                <a href="javascript:void(0);" onclick="window.location.href='../../web/pageController/toAlarm'"><i class="icon-bell"></i>报警<span class="badge badge-danger"></span></a>
            </li>
            <li>
                <a href="javascript:void(0);" onclick="window.location.href='../../web/pageController/toThing'"><i class="icon-note"></i>事件<span class="badge badge-success"></span></a>
            </li>
            <li>
                <a href="javascript:void(0);" onclick="window.location.href='../../web/pageController/toFault'"><i class="icon-ban"></i>故障<span class="badge badge-primary"></span></a>
            </li>
        </ul>
        <div class="top_right">
            <a class="btn_lock"><span class="icon-lock"></span></a>
            <a class="top_user">Andy</a>
             <a class="top_logout" onclick="opLogout();">注销</a>
        </div>
    </div>
    <!-- END HEADER -->
    <!-- start main map -->
    <div class="main_box" style="height: 76%;">
 <!--------------- 新添加 -->
       <!--  <div class="map_search">
                        <form class="form-inline">
                            <div class="input-icon right">
                                <i class="icon-magnifier" data-toggle="modal" href="#history"></i>
                                <input type="text" class="form-control input-large" placeholder="search...">
                            </div>
                        </form>
                    </div> -->
                    <input type="hidden" name="uniqueId" id="uniqueId" value="<%=id%>"/>
                    <input type="hidden" name="beginTime" id="beginTime" value="<%=curTime%>"/>
                    <input type="hidden" name="endTime" id="endTime"/>
                    <div class="map_tips" style="margin-top: 40px;">
                        <span id="power">电量：未知</span>
                        <span>在线<i class="fa fa-check-circle"></i></span>
                   		 
                    </div>
                    <div class="map_right" style="margin-top: 40px;">
                        <div class="map_top">
                            <a class="pull-left" href="javascript:" onclick="saveHistoryTrace();"><i class="glyphicon glyphicon-star"></i></a>
                            <a id="historyCheckBtn" class="pull-right" data-toggle="modal" href="#history" data-backdrop="static">
                            	<i class="glyphicon glyphicon-th-list"></i>
                            </a>
                        </div>
                        <div class="map_dev_list">
                            <ul class="bxslider map_dev" id="trackPageATag">
                            	<li><a onclick="showTime();">查看最新时间</a><i class="glyphicon glyphicon-remove"></i> </li>
                              	<!-- <li><a>二区设备1号</a><i class="glyphicon glyphicon-remove"></i> </li>
                                <li><a>二区设备1号</a><i class="glyphicon glyphicon-remove"></i> </li>
                                <li><a>二区设备1号</a><i class="glyphicon glyphicon-remove"></i> </li>
                                <li><a>二区设备1号</a><i class="glyphicon glyphicon-remove"></i> </li>
                                <li><a>二区设备1号</a><i class="glyphicon glyphicon-remove"></i> </li>
                                <li><a>二区设备1号</a><i class="glyphicon glyphicon-remove"></i> </li> -->
                            </ul>
                        </div>
                    </div>
 <!----------------------- 新添加 END -->
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
<!-- 弹出层--历史查询-->
	<div class="modal fade" id="history" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
	    <div class="modal-dialog modal-mdmd">
	        <div class="modal-content" style="height: 500px">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" onclick="clearTb()"></button>
	                <h3>历史查询</h3>
	            </div>
	            <div class="modal-body" style="height: 87%;">
	                <form class="form-horizontal">
	                    <div class="form-group">
	                        <div class="col-md-4">
	                            <label>设备编号</label>
	                        </div>
	                       <div class="col-md-8">
	                            <div class="input-icon">
	                                <!-- <i onclick="selHistroy()" class="icon-magnifier" data-toggle="modal"></i>
	                                <input id="histroyCheckUniqueId" type="text" class="form-control input-large" placeholder="请输入设备号"> -->
	                                <select id="histroyCheckUniqueId" style="width: 60%;" class="form-control select-small">
	                                	<option value="">--请选择--</option>
	                                </select>
	                            </div>
	                        </div>
	                        
	                    </div>
	                </form>
	                <div style="height:80%;overflow: auto">
		                <table border="1" style="text-align: center;" id="historyTb">
		                	<thead>
		                		<tr>
		                			<th style="text-align: center;">时间段</th>
		                			<th style="text-align: center;">操作</th>
		                		</tr>
		                	</thead>
		                	<tbody id="historyTrace">
		                	</tbody>
		                	
		                </table>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>
<!-- END 弹出层--历史查询---->
</body>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.3"></script>
<script type="text/javascript"
	src="http://api.map.baidu.com/library/GeoUtils/1.2/src/GeoUtils.js"></script>
<script type="text/javascript"
	src="http://api.map.baidu.com/library/CurveLine/1.5/src/CurveLine.js"></script>
<script type="text/javascript" 
	src="http://api.map.baidu.com/library/TextIconOverlay/1.2/src/TextIconOverlay_min.js"></script>
<script type="text/javascript" 
	src="http://api.map.baidu.com/library/MarkerClusterer/1.2/src/MarkerClusterer_min.js"></script>



<!-- 地图展示 -->
<script type="text/javascript">
	/* // 百度地图API功能
	var map = new BMap.Map("allmap");    // 创建Map实例
	map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);  // 初始化地图,设置中心点坐标和地图级别
	map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
	map.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
	map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放 */
</script>
<script type="text/javascript" src= "../../js/map.js"></script>
 <script type="text/javascript" src="../../js/map/map.js"></script>
 <script type="text/javascript" src="../../js/trackpage.js"></script>
 <script src="../../js/global.js"></script>
</html>
<!-- 地图 -->
