<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>系统设置--基本信息维护</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <meta content="" name="description"/>
    <meta content="" name="author"/>
    <link rel="stylesheet" type="text/css" href="../../assets/plugins/font-awesome/css/font-awesome.min.css"/><!--字体图标-->
    <link rel="stylesheet" type="text/css"  href="../../assets/plugins/ElegantIcons/style.css"><!--字体图标-->
    <link rel="stylesheet" type="text/css"  href="../../assets/plugins/simple-line-icons/simple-line-icons.min.css"><!--字体图标-->
    <link rel="stylesheet" type="text/css" href="../../css/bootstrap.css"><!--base-->
    <link rel="stylesheet" type="text/css" href="../../assets/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"/><!--table-->
    <link rel="stylesheet" type="text/css" href="../../assets/css/layout.css"/>
    <link rel="stylesheet" type="text/css" href="../../assets/css/components.css"><!--BASE-->
    <link rel="stylesheet" href="../../assets/css/plugins.css"><!--BASE-->
    <link rel="stylesheet" type="text/css"  href="../../css/main.css"/><!--自定义-->
    <link rel="stylesheet" type="text/css"  href="../../css/jquery.mCustomScrollbar.css"/><!--滚动条-->
</head>
<body onload="mapOnload();">
	<input type="hidden" id="forOpCode" >
	
<div class="o_warpper">
<!-- BEGIN HEADER -->
<div class="o_header">
    <a href="javascript:void(0);" onclick="window.location.href='../../web/pageController/toIndex'" class="top_back"><span class="icon-arrow-left"></span>系统设置</a>
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
                    <li class="active">
                        <a href="javascript:" id="czyManageBtn"><em class="icon-lock"></em>操作员管理</a>
                    </li>
                    <li>
                        <a href="javascript:" id="baseMsgWeihuBtn"><em class="icon-lock"></em>基本信息维护</a>
                    </li>
                    <li>
                        <a href="javascript:" id="pwdModifyBtn"><em class="icon-lock"></em> 修改操作员密码</a>
                    </li>
                    <li>
                        <a href="javascript:" id="compPwdModifyBtn"><em class="icon-lock"></em> 修改集团密码</a>
                    </li>
                    <li>
                        <a href="javascript:" id="serviceConfBtn"><em class="icon-lock"></em> 服务器设置</a>
                    </li>
                </ul>
            </div>
            <!-- end left -->
<!---------------------------------------------- start right   -->

<!--------------------操作员管理start -->  
			<div class="rig_box" id="czyManage">
                <div class="rig_top">
                    <div class="pull-left clearfix top_btnlist">
                        <a id="opAdd" class="device_add" data-backdrop="static" data-toggle="modal" href="javascript:void(0);"><i class="glyphicon glyphicon-plus"></i>添加</a>
                        <a class="device_edit" data-backdrop="static" id="menagerModify"><i class="glyphicon glyphicon-pencil"></i>修改</a>
                        <a class="device_del cur" id="menagerDel"><i class="glyphicon glyphicon-remove"></i>删除</a>
                    </div>
                    <div class="pull-left rig_top_rig">
                        <form class="form-inline pull-left">
                            <div class="input-icon right">
                                <a style="position: absolute;margin-left: 86%;">
                                	<i class="icon-magnifier" onclick="selectOperatorByCondition();"></i>
                                </a>
                                <input id="opNameOrCode" type="text" class="form-control input-medium" placeholder="请输入操作员账号或名称">
                            </div>
                        </form>
                    </div>
                </div>
                <div class="rig_con no-footer">
                    <div class="rig_table scroll">
                        <table class="table table-bordered table-hover dataTable table_two"  id="table3">
                            <thead>
	                            <tr>
	                                <th width="30"></th>
	                                <th>编号</th>
	                                <th>代码</th>
	                                <th>姓名</th>
	                                <th>角色</th>
	                                <th>创建时间</th>
	                                <th>状态</th>
	                            </tr>
                            </thead>
                            <tbody id="tBody">
	                           
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
<!--------------------操作员管理end -->
<!--------------------基本信息维护start -->
            <div class="rig_box" id="baseMsgWeihu" hidden="true">
                <div class="system_box scroll">
                    <div class="content">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="portlet light ">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <span class="caption-subject bold uppercase">基本信息</span>
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                    <input type="hidden" id="companyId" name="companyId"/>
                                        <div class="form-group">
                                            <label>公司名称</label>
                                            <i class="fa tooltips" style="color: red">*</i>
                                            <input id="companyName" type="text" class="form-control input-inline input-large" placeholder="" maxlength="50">
                                       		<input type="hidden" id="oldCompanyName" name="oldCompanyName"/>
                                        </div>
                                        <div class="form-group">
                                            <label>公司网址</label>
                                            <i class="fa tooltips" style="color: red">*</i>
                                            <input id="companyInterAddr" type="text" class="form-control input-inline input-large" placeholder="">
                                        </div>
                                        <div class="form-group">
                                            <label>公司电话</label>
                                            <i class="fa tooltips" style="color: red">*</i>
                                            <input id="companyPhone" type="text" class="form-control input-inline input-large" placeholder="">
                                        </div>
                                        <div class="form-group">
                                            <label>软件标题</label>
                                            <i class="fa tooltips" style="color: red">*</i>
                                            <input id="softName" type="text" class="form-control input-inline input-large" placeholder="">
                                        </div>
                                        <div class="form-group">
                                            <label>公司地址</label>
                                            <i class="fa tooltips" style="color: red">*</i>
                                            <textarea id="companyAddr" class="form-control  input-inline input-large" rows="3"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="portlet light ">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <span class="caption-subject bold uppercase">描述</span>
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <div class="form-group">
                                            <label>公司描述</label>
                                            <i class="fa tooltips" style="color: red">*</i>
                                            <textarea id="companyDescription" class="form-control  input-inline input-large" rows="3"></textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="portlet light ">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <span class="caption-subject bold uppercase">图片上传</span>
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <div class="form-group clearfix">
                                            <label>公司图片</label>
                                           <!--  <i class="fa tooltips" style="color: red">*</i> -->
                                            <a href="javascript:;" class="a-upload form-control input-inline input-large">
                                                <form id="companyPicForm" enctype="multipart/form-data">
	                                                <input id="companyFileCtx" type="file" class="" name="companyPic" accept="image/jpeg,image/png" >点击这里上传文件
                                                </form>
                                            </a>
                                            <div class="up_load_img1">
                                                <img id="companyPicShow" src="../../images/logo_img.png"/>
                                                <p>（最佳尺寸310*70,图片格式:.jpg、.png）</p>
                                            </div>
                                        </div>
                                        <div class="form-group clearfix">
                                            <label>软件LOGO</label>
                                           <!--  <i class="fa tooltips" style="color: red">*</i> -->
                                            <a href="javascript:;" class="a-upload form-control input-inline input-large">
                                            	<form id="softLogoForm" enctype="multipart/form-data">
	                                                <input id="softLogoFileCtx" type="file" class=""  name="softLogo" accept="image/jpeg,image/png" >点击这里上传文件
                                            	</form>
                                            </a>
                                            <div class="up_load_img2">
                                                <img id="softLogoShow" src="../../images/logo_litimg.png"/>
                                                <p>（最佳尺寸24*24,图片格式:.jpg、.png）</p>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                                <div class="portlet text-center">
                                    <button id="baseMsgBtn" type="button" class="btn red " data-toggle="button" aria-pressed="true">确定</button>
                                    <button type="button" class="btn btn-default" data-toggle="button" aria-pressed="true">取消 </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
<!--------------------基本信息维护end -->

<!--------------------修改操作员密码start -->  
			<div class="rig_box" id="pwdModify" hidden="true">
                <div class="system_box scroll">
                    <div class="content">
                        <div class="row">
                            <div class="col-md-8 col-md-offset-2 col-lg-4 col-lg-offset-4" >
                                <div class="portlet light" style="width: 405px;">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <span class="caption-subject bold uppercase" style="color: black;">修改密码</span>
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <form class="form-horizontal" role="form">
                                      
                                       <label class="col-sm-3" style="color: black;">原密码</label>
					                    <div class="col-sm-8">
					                        <div class="input-icon right">
					                            <i class="fa tooltips">*</i>
					                            <input id="oldPwd" type="password" class="form-control form-inline" style="border: 1px solid black;">
					                        </div>
					                    </div>
                                        <label class="col-sm-3" style="color: black;">新密码</label>
					                    <div class="col-sm-8">
					                        <div class="input-icon right">
					                            <i class="fa tooltips">*</i>
					                            <input id="newPwd1" type="password" class="form-control form-inline" style="border: 1px solid black;"  maxlength="18">
					                        </div>
					                    </div>
                                        <label class="col-sm-3" style="color: black;">确认密码</label>
					                    <div class="col-sm-8">
					                        <div class="input-icon right">
					                            <i class="fa tooltips">*</i>
					                            <input id="newPwd2" type="password"  class="form-control form-inline" style="border: 1px solid black;">
					                        </div>
					                    </div>
			                 
			                    
			                    
			                    
			                    
                                        <!-- <div class="form-group">
                                                <label>原密码</label>
                                                原密码<input id="oldPwd" type="password" class="form-control input-inline" placeholder="">
                                            </div>
                                            <div class="form-group">
                                                <label>新密码</label>
                                                新密码<input id="newPwd1" type="password" class="form-control input-inline" placeholder="">
                                            </div>
                                            <div class="form-group">
                                                <label>确认密码</label>
                                               确认密码 <input id="newPwd2" type="password" class="form-control input-inline" placeholder="">
                                            </div>-->
                                            <div class="form-group">
                                                <div class="col-md-offset-4 col-md-8">
                                                    <button type="button" id="modifyPwdBtn" class="btn red">确定</button>
                                                </div>
                                            </div> 
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
<!--------------------修改操作员密码end -->

<!--------------------修改集团密码start --> 

			<div class="rig_box" id="compPwdModify" hidden="true">
				<div class="login_box">
			        <h3 style="color: black;">修改集团账号密码</h3>
			        <form class="form-horizontal" role="form">
			            <div class="form-body">
			                <div class="form-group ">
			                    <label class="col-sm-3" style="color: black;">集团ID</label>
			                    <div class="col-sm-8">
			                        <div class="input-icon right">
			                            <i class="fa tooltips">*</i>
			                            <input id="compId" type="text" class="form-control form-inline" style="border: 1px solid black;">
			                        </div>
			                    </div>
			                </div>
			                <div class="form-group ">
			                    <label class="col-md-3 " style="color: black;">原密码</label>
			                    <div class="col-md-8">
			                        <div class="input-icon right">
			                            <i class="fa tooltips">*</i>
			                            <input id="compOldPwd" type="password" class="form-control" style="border: 1px solid black;">
			                        </div>
			                    </div>
			                </div>
			                <div class="form-group ">
			                    <label class="col-md-3 " style="color: black;">新密码</label>
			                    <div class="col-md-8">
			                        <div class="input-icon right">
			                            <i class="fa tooltips">*</i>
			                            <input id="compPwx1" type="password" class="form-control" style="border: 1px solid black;" maxlength="18">
			                        </div>
			                    </div>
			                </div>
			                <div class="form-group ">
			                    <label class="col-md-3 " style="color: black;">再次输入</label>
			                    <div class="col-md-8">
			                        <div class="input-icon right">
			                            <i class="fa tooltips">*</i>
			                            <input id="compPwx2" type="password" class="form-control" style="border: 1px solid black;">
			                        </div>
			                    </div>
			                </div>
			                <div class="text-center footer_btn margin-top-50">
			                    <a id="compSub" class="sysBBBTN" style="border: 1px solid #cb5a5e">确定</a>
			                    <a id="compCan" class="sysBBBTN" style="border: 1px solid #cb5a5e">取消</a>
			                </div>
			            </div>
			        </form>
	    		</div>
	    	</div>
<!--------------------修改集团密码end -->
<!--------------------服务器设置start --> 
			<div id="serviceConf" class="rig_box" hidden="true">
                <div class="system_box scroll">
                    <div class="content">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="portlet light ">
                                    <div class="portlet-title">
                                        <div class="note note-warning">
                                            <h4 class="block"><strong>提示</strong> 这些操作请相关专业人员进行操作，请勿乱操作！如果更改数据库，请保证数据库操作驱动存在！</h4>
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <div class="panel panel-default">
                                            <div class="panel-heading">
                                                通讯服务器
                                            </div>
                                            <div class="panel-body">
                                                <div class="form-group">
                                                    <label class="col-md-3">服务器地址</label>
                                                    <div class="input-group input-large col-md-9">
                                                        <input type="text" class="form-control" placeholder="">
                                                        <span class="input-group-btn"><a class="btn red" type="button">设置</a></span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="panel panel-default">
                                            <div class="panel-heading">
                                                接警服务器
                                            </div>
                                            <div class="panel-body">
                                                <div class="form-group">
                                                    <label class="col-md-3">服务器地址</label>
                                                    <div class="input-group input-large col-md-9">
                                                        <input type="text" class="form-control" placeholder="">
                                                        <span class="input-group-btn"><a class="btn red" type="button">设置</a></span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="panel panel-default">
                                            <div class="panel-heading">
                                                数据库
                                            </div>
                                            <div class="panel-body">
                                                <div class="form-group">
                                                    <label>公司名称</label>
                                                    <select class="form-control input-large input-inline">
                                                        <option value="top">人员调动</option>
                                                        <option value="bottom">财务管理</option>
                                                    </select>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3">服务器地址</label>
                                                    <div class="input-group input-large col-md-9">
                                                        <input type="text" class="form-control" placeholder="">
                                                        <span class="input-group-btn"><a class="btn red" type="button">设置</a></span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
<!--------------------服务器设置end -->
            <!-- end right -->
        </div>
    <!-- end main -->
    <!-- start foot -->
    <div class="main_foot">
        <table class="table_one table table-bordered table-hover table-striped dataTable" id="table1">
            <thead>
            <!-- <tr>
                <th width="14"></th>
                <th>设备名称</th>
                <th>设备编号</th>
                <th>报警类型</th>
                <th>地址</th>
                <th>时间</th>
            </tr> -->
            <tr>
	                <th style="width:3%"></th>
	                <th style="width:20%">设备名称</th>
	                <th style="width:20%">设备编号</th>
	                <th style="width:20%">报警类型</th>
	                <th style="width:20%">地址</th>
	                <th style="width:20%">时间</th>
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

<!--用于添加的   弹出层--系统增加-->
<div class="modal fade" id="systemAdd" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-mdmd">
        <div class="modal-content">
            <div class="modal-header">
                <!-- <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button> -->
                <h4>添加操作员</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form">
                    <div class="form-body">
                        <div class="form-group has-error">
                            <label class="col-sm-3">操作员账号</label>
                            <div class="col-sm-8">
                                <div class="input-icon right">
                                    <i class="fa tooltips" >*</i>
                                    <input id="opCodeAdd" onblur="opCodeIsOnly()" type="text" class="form-control" maxlength="20">
                                </div>
                            </div>
                        </div>
                        <div class="form-group has-error">
                            <label class="col-md-3 ">操作员密码</label>
                            <div class="col-md-8">
                                <div class="input-icon right">
                                    <i class="fa tooltips">*</i>
                                    <input id="opPwdAdd" type="password" class="form-control" maxlength="18">
                                </div>
                            </div>
                        </div>
                        <div class="form-group has-error">
                            <label class="col-md-3 ">操作员姓名</label>
                            <div class="col-md-8">
                                <div class="input-icon right">
                                    <i class="fa tooltips">*</i>
                                    <input id="opNameAdd" type="text" class="form-control" maxlength="20">
                                </div>
                            </div>
                        </div>
                        <div class="form-group has-error">
                            <label class="col-md-3 ">操作员角色</label>
                            <div class="col-md-8">
                                <div class="input-icon right">
                                    <i class="fa tooltips">*</i>
                                    <select id="opRuleAdd" class="form-control">
                                        <option value="普通操作员">普通操作员</option>
                                        <option value="普通用户">普通用户</option>
                                        <option value="系统操作员">系统操作员</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button id="managerSubAdd" type="button" class="btn red" data-toggle="button" aria-pressed="true">确定</button>
                <button id="managerCansAdd" type="button" class="btn default" data-toggle="button" aria-pressed="true">取消</button>
            </div>
        </div>
    </div>
</div>
<!--用于添加的  弹出层 END --增加---->
<!--用于修改的  弹出层--系统增加-->
<div class="modal fade" id="systemModify" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-mdmd">
        <div class="modal-content">
            <div class="modal-header">
                <!-- <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button> -->
                <h4>修改操作员</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form">
                    <div class="form-body">
                        <div class="form-group has-error">
                            <label class="col-sm-3">操作员账号</label>
                            <div class="col-sm-8">
                                <div class="input-icon right">
                                    <i class="fa tooltips" >*</i>
                                    <input id="opId" type="hidden"/>
                                    <input readonly="readonly" id="opCodeModify" type="text" class="form-control">
                                </div>
                            </div>
                        </div>
                       <!--  <div class="form-group has-error">
                            <label class="col-md-3 ">操作员密码</label>
                            <div class="col-md-8">
                                <div class="input-icon right">
                                    <i class="fa tooltips">*</i>
                                    <input id="opPwdModify" type="password" class="form-control">
                                </div>
                            </div>
                        </div> -->
                        <div class="form-group has-error">
                            <label class="col-md-3 ">操作员姓名</label>
                            <div class="col-md-8">
                                <div class="input-icon right">
                                    <i class="fa tooltips">*</i>
                                    <input id="opNameModify" type="text" class="form-control">
                                </div>
                            </div>
                        </div>
                        <div class="form-group has-error">
                            <label class="col-md-3 ">操作员角色</label>
                            <div class="col-md-8">
                                <div class="input-icon right">
                                    <i class="fa tooltips">*</i>
                                    <select id="opRuleModify" class="form-control">
                                        <option value="普通操作员">普通操作员</option>
                                        <option value="普通用户">普通用户</option>
                                        <option value="系统操作员">系统操作员</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button id="managerSubModify" type="button" class="btn red" data-toggle="button" aria-pressed="true">确定</button>
                <button id="managerCansModify" type="button" class="btn default" data-toggle="button" aria-pressed="true">取消</button>
            </div>
        </div>
    </div>
</div>
<!-- END 用于修改的  弹出层--增加---->
<script src="../../js/jquery.min.js"></script>
<script src="../../js/jquery-ui-1.10.4.min.js"></script>
<script src="../../js/bootstrap.min.js"></script>
<script src="../../js/jquery.mousewheel.js"></script><!--提供滚动支持-->
<script src="../../js/jquery.mCustomScrollbar.js"></script><!--滚动插件的主文件-->
<script src="../../js/table.js"></script><!--表格插件-->
<script src="../../js/jquery-form.js"></script><!--表格插件-->
<script src="../../js/layer/layer.js"></script>
<script type="text/javascript" src="../../assets/plugins/datatables/media/js/jquery.dataTables.min.js"></script><!--表格插件-->
<script type="text/javascript" src="../../assets/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js"></script><!--表格插件-->
<script src="../../js/check.js"></script>
<script src="../../js/global.js"></script>
<script src="../../js/normal.js"></script>
<script src="../../js/system.js"></script>
 <script type="text/javascript" src="../../js/map/map.js"></script>
<script type='text/javascript'>
    (function($){
        $(window).load(function(){
            $(".scroll").mCustomScrollbar();
        });
    })(jQuery);
</script>

</body>
</html>