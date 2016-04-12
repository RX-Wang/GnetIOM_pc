<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>统计与查询</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <meta content="" name="description"/>
    <meta content="" name="author"/>
    <link href="../../assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/><!--字体图标-->
    <link rel="stylesheet" type="text/css"  href="../../assets/plugins/ElegantIcons/style.css"><!--字体图标-->
    <link rel="stylesheet" type="text/css"  href="../../assets/plugins/simple-line-icons/simple-line-icons.min.css"><!--字体图标-->
    <link rel="stylesheet"  type="text/css" href="../../css/bootstrap.css"><!--base-->
    <link rel="stylesheet" type="text/css" href="../../assets/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"/><!--table-->
    <link rel="stylesheet" type="text/css" href="../../assets/plugins/calendar/bootstrapDatepickr-1.0.0.min.css"/><!--日期插件-->
    <link href="../../assets/css/layout.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="../../assets/css/components.css"><!--BASE-->
    <link rel="stylesheet" href="../../assets/css/plugins.css"><!--BASE-->
    <link rel="stylesheet" type="text/css"  href="../../css/main.css"/><!--自定义-->
    <link rel="stylesheet" type="text/css"  href="../../css/jquery.mCustomScrollbar.css"/><!--滚动条-->

</head>
<body onload="mapOnload();">
<div class="o_warpper">
<!-- BEGIN HEADER -->
 <div class="o_header" style="z-index: 10">
        <a href="javascript:void(0)" class="top_back" onclick="window.location.href='../../web/pageController/toIndex'"><span class="icon-arrow-left"></span>统计与查询</a>
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
            <a class="top_user">Andy</a>
            <a class="top_logout" onclick="opLogout();">注销</a>
        </div>
    </div>
<!-- END HEADER -->
<div class="o_main">
    <div class="o_maincon">
    <!-- start main -->
        <div class="main_box">
            <!-- start left -->
            <div class="left_box ">
                <ul class="menu leftnav">
<!----------------------------------------- 系统日志START -->                
                    <li class="dropdown	active open" forDiv="systemLog" >
                        <a role="button" data-toggle="dropdown" aria-expanded="true"><em class="icon-speech"></em>系统日志</a>
                        <div class="dropdown-menu query_box" role="menu" aria-labelledby="dropdownMenu" >
                            <h4>日期</h4>
                            <form >
                                <div class="checkbox-list">
                                    <label><input type="checkbox" id="sysLogAllDate">全部日期</label>
                                </div>
                                <div class="date_box">
                                    <div class="date_li">
                                        <div class="input-group">
                                            <input type="text" id="calendar1" placeholder="开始日期" class="form-control calendar">
                                            <span class="input-group-addon addonclick_id-calendar" id="basic-addon1"><i class="fa fa-calendar"></i></span>
                                        </div>
                                    </div>
                                    <div class="date_li">
                                        <div class="input-group">
                                            <input type="text" id="calendar2" placeholder="结束日期" class="form-control calendar">
                                            <span class="input-group-addon addonclick_id-calendar" id="basic-addon2"><i class="fa fa-calendar"></i></span>
                                        </div>
                                    </div>
                                </div>
                            </form>
                            <h4>操作员</h4>
                            <form>
                                <div class="checkbox-list">
                                    <label><input type="checkbox" id="sysLogAllUsers">全部操作员</label>
                                </div>
                                <div class="input-group">
                                    <input id="sysLogUser" type="text" placeholder="操作员名称" class="form-control">
                                </div>
                            </form>
                            <button id="sysLogBtn" type="button" class="btn red btn-block margin-top-20">查询</button>
                        </div>
                    </li>
<!----------------------------------------- 系统日志END -->
<!----------------------------------------- 事件START -->
                    <li class="dropdown" forDiv="things">
                        <a role="button" data-toggle="dropdown">
                        	<em class="icon-note"></em>事件</a>
                        <div class="dropdown-menu query_box" role="menu" aria-labelledby="dropdownMenu" >
                            <h4>创建时间</h4>
                            <form >
                                <div class="checkbox-list">
                                    <label><input type="checkbox" id="thingsAllDate">全部日期</label>
                                </div>
                                <div class="date_box">
                                    <div class="date_li">
                                        <div class="input-group">
                                            <input type="text" id="calendar3" placeholder="开始日期" class="form-control calendar">
                                            <span class="input-group-addon addonclick_id-calendar" id="basic-addon3"><i class="fa fa-calendar"></i></span>
                                        </div>
                                    </div>
                                    <div class="date_li">
                                        <div class="input-group">
                                            <input type="text" id="calendar4" placeholder="结束日期" class="form-control calendar">
                                            <span class="input-group-addon addonclick_id-calendar" id="basic-addon4"><i class="fa fa-calendar"></i></span>
                                        </div>
                                    </div>
                                </div>
                            </form>
                            <h4>使用者名称</h4>
                            <form>
                                <div class="input-group">
                                    <input id="thingsDeviceName" type="text" placeholder="如不填写，则为全部" class="form-control">
                                </div>
                            </form>
                            <h4>类型选择</h4>
                            <form>
                                <div class="input-group">
                                    <input id="thingsDeviceType" type="text" placeholder="报警类型" class="form-control">
                                </div>
                            </form>
                            <button  id="thingsBtn" type="button" class="btn red btn-block margin-top-20">查询</button>
                        </div>
                    </li>
<!----------------------------------------- 事件END -->
<!----------------------------------------- 用户清单START -->
                    <li class="dropdown" forDiv="userMenu">
                        <a role="button" data-toggle="dropdown"><em class="icon-list"></em> 用户清单</a>
                        <div class="dropdown-menu query_box" role="menu" aria-labelledby="dropdownMenu" >
                            <h4>创建时间</h4>
                            <form >
                                <div class="checkbox-list">
                                    <label><input type="checkbox" id="userMenuAllDate">全部日期</label>
                                </div>
                                <div class="date_box">
                                    <div class="date_li">
                                        <div class="input-group">
                                            <input type="text" id="calendar5" placeholder="开始日期" class="form-control calendar">
                                            <span class="input-group-addon addonclick_id-calendar" id="basic-addon5"><i class="fa fa-calendar"></i></span>
                                        </div>
                                    </div>
                                    <div class="date_li">
                                        <div class="input-group">
                                            <input type="text" id="calendar6" placeholder="结束日期" class="form-control calendar">
                                            <span class="input-group-addon addonclick_id-calendar" id="basic-addon6"><i class="fa fa-calendar"></i></span>
                                        </div>
                                    </div>
                                </div>
                            </form>
                            <h4>用户信息</h4>
                            <form>
                                <div class="input-group">
                                    <input id="userMenuName" type="text" placeholder="姓名" class="form-control">
                                    <input id="userMenuType" type="text" placeholder="用户类型" class="form-control">
                                    <input id="userMenuTel" type="text" placeholder="电话" class="form-control">
                                    <input id="userMenuAdd" type="text" placeholder="地址" class="form-control">
                                </div>
                            </form>
                            <button  id="userMenuBtn" type="button" class="btn red btn-block margin-top-20">查询</button>
                        </div>
                    </li>
                </ul>
            </div>
            <!-- end left -->
            <!-- start right -->
<!----------------------------------------- 系统日志     DIV  START -->            
            <div class="rig_box" id="systemLog">
                <div class="rig_top">
                    <span id="sysLogStartTime" class="o_rignum margin-left-10"><!-- --/--/-- --></span>
                    <span class="o_rignum margin-left-10">至</span>
                    <span id="sysLogEndTime" class="o_rignum margin-left-10"><!-- --/--/-- --></span>
                    <span class="o_deliver">|</span>
                    <span id="sysLogUName" class="o_rignum">system</span>
                    <div class="pull-right portlet">
                        <button type="button" forTable="table4" class="btn red margin-top-5 margin-right-10 downLoad">下载</button>
                    </div>
                </div>
                <div class="rig_con no-footer">
                    <div class="rig_table scroll">
                        <table class="table table-bordered table-hover dataTable table_two"  id="table4">
                            <thead>
	                            <tr>
	                                <th>编号</th>
	                                <th>日期</th>
	                                <th>时间</th>
	                                <th>事件类型</th>
	                                <th>描述</th>
	                                <th>操作员姓名</th>
	                                <th>操作员代码</th>
	                            </tr>
                            </thead>
                            <tbody id="sysLogList">
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
<!----------------------------------------- 系统日志    DIV   END -->            
<!----------------------------------------- 事件    DIV   START -->            
            <div class="rig_box" id="things" hidden="true">
                <div class="rig_top">
                    <span id="thingsStartTime" class="o_rignum margin-left-10">--/--/--</span>
                    <span class="o_rignum margin-left-10">至</span>
                    <span id="thingsEndTime" class="o_rignum margin-left-10">--/--/--</span>
                    <span class="o_deliver">|</span>
                    <span id="thingsDeviceName1" class="o_rignum">全部</span>
                    <span class="o_deliver">|</span>
                    <span id="thingsAlarmType" class="o_rignum">全部</span>
                    <div class="pull-right portlet">
                        <button type="button" forTable="table5" class="btn red margin-top-5 margin-right-10 downLoad">下载</button>
                    </div>
                </div>
                <div class="rig_con no-footer">
                    <div class="rig_table scroll">
                        <table class="table table-bordered table-hover dataTable table_two"  id="table5">
                            <thead>
	                            <tr>
	                                <th>设备编号</th>
	                                <th>报警类型</th>
	                                <th>地址</th>
	                                <th>时间</th>
	                                <th>经度</th>
	                                <th>纬度</th>
	                                <th>使用者</th>
	                            </tr>
                            </thead>
                            <tbody id="thingsList">
	                            <!-- <tr>
	                                <td>1</td>
	                                <td>2015-8.8</td>
	                                <td>10.10.10</td>
	                                <td>oprater</td>
	                                <td>系统登录</td>
	                                <td>系统默认管理员</td>
	                                <td>system</td>
	                            </tr> -->
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
<!----------------------------------------- 事件 DIV   END --> 
<!----------------------------------------- 用户清单 DIV START -->             
            <div class="rig_box" id="userMenu" hidden="true">
                <div class="rig_top">
                    <span id="userMenuStartTime" class="o_rignum margin-left-10">--/--/--</span>
                    <span class="o_rignum margin-left-10">至</span>
                    <span id="userMenuEndTime" class="o_rignum margin-left-10">--/--/--</span>
                    <span class="o_deliver">|</span>
                    <span id="userMenuUName" class="o_rignum">全部</span>
                    <div class="pull-right portlet">
                        <button type="button" forTable="table3" class="btn red margin-top-5 margin-right-10 downLoad">下载</button>
                    </div>
                </div>
                <div class="rig_con no-footer">
                    <div class="rig_table scroll">
                        <table class="table table-bordered table-hover dataTable table_two"  id="table3">
                            <thead>
	                            <tr>
	                                <th>设备编号</th>
	                                <th>设备类型</th>
	                                <th>sim号</th>
	                                <th>使用者姓名</th>
	                                <th>使用者类型</th>
	                                <th>创建时间</th>
	                                <th>电话</th>
	                                <th>地址</th>
	                                <th>使用者</th>
	                                <th>状态</th>
	                                <th>分组</th>
	                            </tr>
                            </thead>
                            <tbody id="userMenuList">
	                           <!--  <tr>
	                                <td>1</td>
	                                <td>2015-8.8</td>
	                                <td>10.10.10</td>
	                                <td>oprater</td>
	                                <td>系统登录</td>
	                                <td>系统默认管理员</td>
	                                <td>system</td>
	                                <td>system</td>
	                                <td>system</td>
	                                <td>system</td>
	                                <td>system</td>
	                            </tr> -->
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
<!----------------------------------------- 用户清单END -->             
            <!-- end right -->
        </div>
    <!-- end main -->
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
</div>
</div>
<script src="../../js/jquery.min.js"></script>
<script src="../../js/layer/layer.js"></script>
<script src="../../js/map.js"></script>
<script src="../../js/jquery-ui-1.10.4.min.js"></script>
<script src="../../js/bootstrap.min.js"></script>
<script src="../../js/jquery.mousewheel.js"></script><!--提供滚动支持-->
<script src="../../js/jquery.mCustomScrollbar.js"></script><!--滚动插件的主文件-->
<!-- <script src="../../js/table.js"></script> --><!--表格插件-->
<script src="../../assets/plugins/calendar/bootstrapDatepickr-1.0.0.min.js"></script><!--日期插件-->
<script type="text/javascript" src="../../assets/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js"></script><!--表格插件-->
<script src="../../js/check.js"></script>
<script src="../../js/global.js"></script>
<script src="../../js/normal.js"></script>
<script src="../../js/statistics.js"></script>
<script type='text/javascript'>
    (function($){
        $(window).load(function(){
            $(".scroll").mCustomScrollbar();
        });
    })(jQuery);


</script>
<script>
    $(document).ready(function() {
        $(".calendar").bootstrapDatepickr();
    });


</script>
 <script type="text/javascript" src="../../js/map/map.js"></script>
</body>
</html>