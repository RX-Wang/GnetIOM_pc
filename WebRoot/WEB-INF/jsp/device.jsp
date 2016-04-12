<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% 
    	String uniqueId = (String)request.getParameter("uniqueId");
    
    /* 	String isCheck = (String)request.getAttribute("isCheck");
   		System.out.println("isCheck:"+isCheck); */
    %> 
<!DOCTYPE html >
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>设备</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <meta content="" name="description"/>
    <meta content="" name="author"/>
    <link href="../../assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/><!--字体图标-->
    <link rel="stylesheet" type="text/css"  href="../../assets/plugins/ElegantIcons/style.css"><!--字体图标-->
    <link rel="stylesheet" type="text/css"  href="../../assets/plugins/simple-line-icons/simple-line-icons.min.css"><!--字体图标-->
    <link rel="stylesheet"  type="text/css" href="../../css/bootstrap.css"><!--base-->
    <link rel="stylesheet" type="text/css" href="../../assets/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"/>
    <link href="../../assets/css/layout.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="../../assets/css/components.css"><!--BASE-->
    <link rel="stylesheet" href="../../assets/css/plugins.css"><!--BASE-->
    <link rel="stylesheet" type="text/css"  href="../../css/main.css"/><!--自定义-->
    <link rel="stylesheet" type="text/css"  href="../../css/jquery.mCustomScrollbar.css"/><!--滚动条-->
</head>
<body onload="mapOnload();">
	<input type="hidden" id="forPencilModifyName">
	<input type="hidden" id="forSettingSaveOrUpdate">
	<input type="hidden" id="fortypeID">
	 <input type="hidden" id="uniqueId" value="<%=uniqueId%>"/> 
	<%-- <input type="hidden" id="isCheck" value="<%=isCheck%>"/> --%>
<%-- 	<input type="hidden" id="foruniqueId" value="<%=   %>">
	<input type="hidden" id="fortypeID" value="<%=   %>"> --%>
<div class="o_warpper">
<!-- BEGIN HEADER -->
<div class="o_header">
    <a href="javascript:void(0)" class="top_back" onclick="window.location.href='../../web/pageController/toIndex'"><span class="icon-arrow-left"></span>设备</a>
    <ul class="o_topnav">
        <li>
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
        <div class="main_box" style="height:73%">
            <!-- start left -->
            <div class="left_box  scroll">
				 <ul class="menu">
                    <li class="left_top">
                        <!-- <em class="top_search pull-left"><input id="groupSearchText" type="text" class="top_input form-control" placeholder="search..."></em>
                       <em id="groupSearch" class="top_edit pull-right">搜索</em> -->
                        <!-- <a id="groupSearch" class="top_edit pull-right">搜索</a> -->
                    </li>
                </ul>
            </div>
            <!-- end left -->
            <!-- start right -->
            <div class="rig_box">
                <div class="rig_top">
                    <a class="rig_back_btn"><i class="glyphicon glyphicon-chevron-left"></i></a>
                    <span class="o_deliver">|</span>
                    <span class="o_rignum">共有0个设备</span>
                    <div class="pull-left clearfix top_btnlist">
                        <a class="device_aut" data-toggle="modal" href="#" id="shouquan"><i class="fa fa-user"></i>授权</a>
                        <a class="device_add" data-toggle="modal" href="#" id="addDevice"><i class="glyphicon glyphicon-plus"></i>添加</a>
                        <a class="device_edit" data-toggle="modal" href="#" id="modifyDevice"><i class="glyphicon glyphicon-pencil"></i>修改</a>
                        <a class="device_del cur" data-toggle="modal" href="#" id="removeDevice"><i class="glyphicon glyphicon-remove"></i>删除</a>
                    </div>
                    <div class="pull-right rig_top_rig">
                        <form class="form-inline pull-left">
                            <div class="input-icon right">
                                <a style="margin-left: 86%;">
                                	<i id="searchDevBtn" class="icon-magnifier"></i>
                                </a>
                                <input type="text" id="searchDevText" class="form-control input-medium" placeholder="请输入设备名称或设备号">
                            </div>
                        </form>
                        <!-- <i class="glyphicon glyphicon-list"></i>点击显示列表样式  -->
                        <!-- <i class="glyphicon glyphicon-th"></i>点击显示图标样式  -->
                        <a id="deviceShowType" class="top_list_dot pull-right defaultType">
                        	<i class="glyphicon glyphicon-th"></i>
                        </a>
                    </div>
                </div>
                <div class="rig_con">
<!-------------------------------------------列表样式 显示设备 START  -->                
                    <div id="typeList" class="rig_table scroll">
                        <table class="table_three">
                            <tbody id="typeListTb">
                                
                            </tbody>
                        </table>
                    </div>
<!-------------------------------------------列表样式 显示设备 END  -->
<!-------------------------------------------无设备显示样式  START  -->
  					 <div id="typeNone" class="rig_block scroll" hidden="true" >
                        <div class="no_device">
                            <img src="../../images/device_img.png">
                            <h2>此分类下没有设备</h2>
                        </div>
                    </div>
<!-------------------------------------------无设备显示样式 END  -->  
<!-------------------------------------------图标样式 显示设备 START  -->
  					<div id="typePic" class="rig_block scroll" hidden="true">
                        <div id="typePicDiv" class="device_list">
                        
                        </div>
                    </div>
<!-------------------------------------------图标样式 显示设备 END  -->  
                </div>

                <div class="table_pages">
                    <div class="pull-right rig_foot">
                       <!--  <div class="btn-group">
                            <button type="button" class="btn btn-default"><i class="glyphicon glyphicon-map-marker"></i> 当前位置</button>
                            <button type="button" class="btn btn-default"><i class="glyphicon glyphicon-list-alt"></i> 报警信息</button>
                            <button type="button" class="btn btn-default"><i class="glyphicon glyphicon-screenshot"></i> 追踪模式</button>
                            <button type="button" class="btn btn-default"><i class="glyphicon glyphicon-search"></i> 轨迹查询</button>
                        </div> -->
                        <div class="btn-group">
                            <button type="button" class="btn btn-default" id="settingBtn" data-toggle="modal" data-backdrop="static"><i class="glyphicon glyphicon-link"></i> 设备回控</button>
                           <!--  <button type="button" class="btn btn-default" data-toggle="modal" data-backdrop="static"><i class="glyphicon glyphicon-cog"></i> 设置</button> -->
                        	 <button type="button" class="btn btn-default"  id="checkingBtn"><i class="glyphicon glyphicon-list-alt"></i> 抓擢列表</button>
                        </div>
                    </div>


                </div>
            </div>
            <!-- end right -->
        </div>

    <!-- end main -->
    <!-- start foot -->
    <div class="main_foot" style="height:27%">
        <table class="table_one table table-bordered table-hover table-striped dataTable" id="table1">
            <thead>
            <tr>
                <th width="14"></th>
                <th>设备名称</th>
                <th>设备编号</th>
                <th>报警类型</th>
                <th>地址</th>
                <th>时间</th>
            </tr>
            </thead>
	            <tbody id="tbAllMsg">
	             <tr>
	                <td class="tb_bg1"></td>
	                <td>一区设备0001</td>
	                <td>12345678</td>
	                <td>事件</td>
	                <td>北京市海淀区学院里30号</td>
	                <td>2015-7-28</td>
	            </tr>
	            <tr>
	                <td class="tb_bg2"></td>
	                <td>一区设备0002</td>
	                <td>12345678</td>
	                <td>事件</td>
	                <td>北京市海淀区学院里30号</td>
	                <td>2015-7-28</td>
	            </tr>
	            <tr>
	                <td class="tb_bg3"></td>
	                <td>一区设备0003</td>
	                <td>12345678</td>
	                <td>事件</td>
	                <td>北京市海淀区学院里30号</td>
	                <td>2015-7-28</td>
	            </tr>
	            <tr>
	                <td class="tb_bg4"></td>
	                <td>一区设备0004</td>
	                <td>12345678</td>
	                <td>事件</td>
	                <td>北京市海淀区学院里30号</td>
	                <td>2015-7-28</td>
	            </tr>
	            <tr>
	                <td class="tb_bg1"></td>
	                <td>一区设备0005</td>
	                <td>12345678</td>
	                <td>事件</td>
	                <td>北京市海淀区学院里30号</td>
	                <td>2015-7-28</td>
	            </tr>
	            <tr>
	                <td class="tb_bg2"></td>
	                <td>一区设备0006</td>
	                <td>12345678</td>
	                <td>事件</td>
	                <td>北京市海淀区学院里30号</td>
	                <td>2015-7-28</td>
	            </tr>
	            <tr>
	                <td class="tb_bg3"></td>
	                <td>一区设备0007</td>
	                <td>12345678</td>
	                <td>事件</td>
	                <td>北京市海淀区学院里30号</td>
	                <td>2015-7-28</td>
	            </tr>
	            <tr>
	                <td class="tb_bg4"></td>
	                <td>一区设备0001</td>
	                <td>12345678</td>
	                <td>事件</td>
	                <td>北京市海淀区学院里30号</td>
	                <td>2015-7-28</td>
	            </tr>
	            </tbody>
        </table>

    </div>
    <!-- END foot -->
    </div>
</div>
</div>


<!-- -------------------------------------------------     此处为授权相关页面的弹出层的开始，可以删除，因为已经做了新的页面 -->
<!-- 弹出层--授权-->
<div class="modal fade" id="device" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
    <div class="modal-dialog modal_wid420 modal_hei200">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h3>设备授权维护</h3>
            </div>
            <div class="modal-body">
               <table class="table-bordered table_one mod_table">
                   <thead>
                   <tr>
                       <th>被授权账号</th>
                       <th>账号类型</th>
                       <th>授权时间</th>
                       <th>操作</th>
                   </tr>
                   </thead>
                   <tbody>
                   <!-- <tr>
                       <td>system</td>
                       <td>个人账户</td>
                       <td>2015-11-14 12:12:50</td>
                       <td><a data-toggle="modal" href="#device8">解除</a></td>
                   </tr>
                   <tr>
                       <td>system</td>
                       <td>个人账户</td>
                       <td>2015-11-14 12:12:50</td>
                       <td><a data-toggle="modal" href="#device8">解除</a></td>
                   </tr>
                   <tr>
                       <td>system</td>
                       <td>个人账户</td>
                       <td>2015-11-14 12:12:50</td>
                       <td><a data-toggle="modal" href="#device8">解除</a></td>
                   </tr>
                   <tr>
                       <td>system</td>
                       <td>个人账户</td>
                       <td>2015-11-14 12:12:50</td>
                       <td><a data-toggle="modal" href="#device8">解除</a></td>
                   </tr> -->
                   </tbody>
               </table>
            </div>
            <div class="modal-footer">
                <button type="button" data-dismiss="modal" class="btn red btn-lg">添加授权</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- END 弹出层--授权---->

<!-- -------------------------------------------------     此处为授权相关页面的弹出层的结束，可以删除，因为已经做了新的页面 -->
<!-- 弹出层--增加-->
<div class="modal fade" id="device2" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h3>设备管理</h3>
            </div>
            <div class="modal-body">
                <div class="content">
                    <div class="row">
                        <div class="col-lg-7">
                            <h4>基本资料</h4>
                            <form class="form-horizontal">
                              <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>使用者名称</label>
                                            <input type="text" class="form-control input-small input-inline" placeholder="">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>电话</label>
                                            <input type="text" class="form-control input-small input-inline" placeholder="">
                                        </div>
                                    </div>
                              </div>
                              <div class="row">
                                  <div class="col-md-6">
                                      <div class="form-group">
                                          <label>使用者类型</label>
                                          <select class="form-control input-small input-inline">
                                              <option value="top">人员调动</option>
                                              <option value="bottom">财务管理</option>
                                          </select>
                                      </div>
                                  </div>
                                  <div class="col-md-6">
                                      <div class="form-group">
                                          <label>所属分组</label>
                                          <input type="text" class="form-control input-small input-inline" placeholder="">
                                      </div>
                                  </div>
                              </div>
                              <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label>地址信息</label>
                                        <input type="text" class="form-control input-inline input-large" placeholder="">
                                    </div>
                                </div>
                              </div>
                              <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label>使用者描述</label>
                                            <textarea class="form-control  input-inline input-large" rows="3"></textarea>
                                        </div>
                                    </div>
                              </div>
                            </form>
                            <h4>主机资料</h4>
                            <form class="form-horizontal">
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>使用者名称</label>
                                            <input type="text" class="form-control input-small input-inline" placeholder="">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>安全码</label>
                                            <input type="text" class="form-control input-small input-inline" placeholder="">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label>SIM卡号</label>
                                            <input type="text" class="form-control input-inline input-large" placeholder="">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>设备类型</label>
                                            <select class="form-control input-small input-inline">
                                                <option value="top">人员调动</option>
                                                <option value="bottom">财务管理</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <a href="javascript:void(0)" class="btn red">同步设备类型</a>
                                    </div>
                                </div>
                            </form>
                            <h4>联系人</h4>
                            <div class="clearfix top_btnlist text-right">
                                <a class="device_add" data-toggle="modal" href="#" id="addLinkman"><i class="glyphicon glyphicon-plus"></i>添加</a>
                                <a class="device_del cur" data-toggle="modal" href="#" id="removeLinkman"><i class="glyphicon glyphicon-remove"></i>删除</a>
                            </div>
                            <table class="table-bordered mod_table" id="linkManTable">
                                <tr>
                                    <th>姓名</th>
                                    <th>电话</th>
                                    <th>手机</th>
                                    <th>描述</th>
                                </tr>
                            </table>
                            <div class="modal-footer clearfix">
                                <button type="button" data-dismiss="modal" class="btn btn-lg default pull-left">重置</button>
                                <button type="button" data-dismiss="modal" class="btn btn-lg red pull-right" id="saveeeeee">保存</button>
                            </div>
                        </div>
                        <div class="col-lg-4">
                            <h4>选择分组</h4>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div><!-- END 弹出层--增加---->

<!-- 左侧栏，添加新分组 Start -->
<div class="modal fade" id="group" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
    <div class="modal-dialog modal-md">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4>请输入新的分组名称</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label>分组名称</label>
                        <input type="text" id="groupNameText" class="form-control input-medium input-inline" placeholder="" maxlength="40">
                    </div>
                </form>
            </div>
            <div class="modal-footer clearfix">
                <button type="button" id="addGroupBtn"  data-toggle="modal" class="btn red ">确定</button>
                <button type="button" id="consalGroupBtn"  data-toggle="modal" class="btn default ">取消</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- 左侧栏，添加新分组 END -->
<!-- 左侧栏，修改新分组 Start -->
<div class="modal fade" id="modilfyGroup" data-backdrop="static" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
    <div class="modal-dialog modal-md">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4>修改分组名称</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label>分组名称</label>
                        <input type="text" id="modilfygroupNameText" class="form-control input-medium input-inline" placeholder="" maxlength="40">
                    </div>
                </form>
            </div>
            <div class="modal-footer clearfix">
                <button type="button" id="modilfyaddGroupBtn"  data-toggle="modal" class="btn red ">确定</button>
                <button type="button" id="modilfyconsalGroupBtn"  data-toggle="modal" class="btn default ">取消</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- 左侧栏，修改新分组 END -->
<!-- 弹出层--设置-->
<div class="modal fade" id="setting" data-backdrop="static" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
    <div class="modal-dialog modal-med">
        <div class="modal-content">
            <div class="modal-header">
                <button id="settingIframClose" type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4>设置面板：</h4>
            </div>
            <div class="modal-body">
                <form id="settingForm" class="form-horizontal setting"><!--add  setting style-->
                </form>
            </div>
            <div class="modal-footer">
                <button type="button"  data-toggle="modal" href="#" class="btn red btn-lg " id="settingSaveBtn">保存</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- END 弹出层--设置--->
<!-- 弹出层--抓擢START-->
	<div class="modal fade" id="checkBatch" data-backdrop="static" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
	    <div class="modal-dialog modal-mdmd">
	        <div class="modal-content" style="height: 500px">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" onclick="clearTb()"></button>
	                <h3>抓擢列表</h3>
	            </div>
	            <div class="modal-body" style="height: 87%;">
	                <form class="form-horizontal">
	                    <div class="form-group">
	                        <!-- <div class="col-md-4">
	                            <label>设备编号</label>
	                        </div> -->
	                        <div class="col-md-8">
	                            <div class="input-icon">
	                            </div>
	                        </div>
	                    </div>
	                </form>
	                <div style="height:80%;overflow: auto">
		                <table border="1" style="text-align: center;" id="checkBatchTb">
		                	<thead>
		                		<tr>
		                			<th style="text-align: center;">设备编号</th>
		                			<th style="text-align: center;">回控命令类型</th>
		                			<th style="text-align: center;">操作</th>
		                		</tr>
		                	</thead>
		                	<tbody id="checkBatchTbody">
		                	</tbody>
		                	
		                </table>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>
<!-- END 弹出层--抓擢---->
<script src="../../js/jquery.min.js"></script>
<script src="../../js/jquery-ui-1.10.4.min.js"></script>
<script src="../../js/bootstrap.min.js"></script>
<script src="../../js/jquery.mousewheel.js"></script><!--提供滚动支持-->
<script src="../../js/jquery.mCustomScrollbar.js"></script><!--滚动插件的主文件-->
<script src="../../js/layer/layer.js"></script>
<script src="../../js/check.js"></script>
<script src="../../js/global.js"></script>
<script src="../../js/normal01.js"></script>
<!-- <script src="../../js/linkMen.js"></script> -->
<script src="../../js/table.js"></script><!--表格插件-->
<script type="text/javascript" src="../../assets/plugins/datatables/media/js/jquery.dataTables.min.js"></script><!--表格插件-->
<script type="text/javascript" src="../../assets/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js"></script><!--表格插件-->
 <script type="text/javascript" src="../../js/map/map.js"></script>
</body>
</html>