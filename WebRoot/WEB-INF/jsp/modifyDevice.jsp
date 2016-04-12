<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<% String id= (String)request.getAttribute("id"); %>
<!DOCTYPE html>
<html>
<head>
    <title>设备修改</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <meta content="" name="description"/>
    <meta content="" name="author"/>
    <link href="../../assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/><!--字体图标-->
    <link rel="stylesheet" type="text/css"  href="../../assets/plugins/ElegantIcons/style.css"><!--字体图标-->
    <link rel="stylesheet" type="text/css"  href="../../assets/plugins/simple-line-icons/simple-line-icons.min.css"><!--字体图标-->
    <link rel="stylesheet"  type="text/css" href="../../css/bootstrap.css"><!--base-->
    <link rel="stylesheet" type="text/css" href="../../assets/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"/><!--table-->
    <link rel="stylesheet" type="text/css" href="../../assets/css/components.css"><!--BASE-->
    <link rel="stylesheet" href="../../assets/css/plugins.css"><!--BASE-->
    <link rel="stylesheet" type="text/css"  href="../../css/main.css"/><!--自定义-->
</head>
<body>
	<input type="hidden" id="forDeviceId" value="<%=id%>">
	<input type="hidden" id="IMModifyPage">
	<div><!-- class="modal fade"  tabindex="-1" role="dialog" aria-hidden="true" style="display: none;" -->
	    <div  > <!-- class="modal-dialog modal-lg" -->
	        <div class="modal-content"> <!--   class="modal-content"-->
	            <div class="modal-header">
	              <!--   <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
	                <h3>设备管理</h3> -->
	            </div>
	            
	            <form id="deviceForm" action="">
	            <input type="hidden" id="dev_userId" name="dev_userId"/>
	            <div class="modal-body">
	                <div class="content">
	                    <div class="row">
	                        <div class="col-lg-7">
	                            <h4>基本资料</h4>
	                            <!-- <form class="form-horizontal"> -->
	                              <div class="row">
	                                    <div class="col-md-6">
	                                        <div class="form-group">
	                                            <label>使用者名称</label>
	                                            <input type="hidden" id="oldNickName" name="oldNickName" />
	                                            <input type="text" id="nickName" name="nickName" class="form-control input-small input-inline" placeholder="" onblur="checkNickName();">
	                                            <i class="fa tooltips" style="color:red;">*</i>
	                                        </div>
	                                    </div>
	                                    <div class="col-md-6">
	                                        <div class="form-group">
	                                            <label>电话</label>
	                                            <input type="text" id="userPhone" name="userPhone" class="form-control input-small input-inline" placeholder="">
	                                        <i class="fa tooltips" style="color:red;">*</i>
	                                        </div>
	                                    </div>
	                              </div>
	                              <div class="row">
	                                  <div class="col-md-6">
	                                      <div class="form-group">
	                                          <label>使用者类型</label>
	                                          <select id="userType" name="userType" class="form-control input-small input-inline">
	                                              <option value="老人">老人</option>
	                                              <option value="小孩">小孩</option>
	                                              <option value="残疾人">残疾人</option>
	                                              <option value="贵重金属">贵重金属</option>
	                                          </select>
	                                          <i class="fa tooltips" style="color:red;">*</i>
	                                      </div>
	                                  </div>
	                                  <div class="col-md-6">
	                                      <div class="form-group">
	                                          <label>所属分组</label>
	                                          <input id="groupId" name="groupId" type="text" class="form-control input-small input-inline" placeholder="" readonly="readonly">
	                                      	  <input type="hidden" id="gId" name="gId"/>
	                                      	  <i class="fa tooltips" style="color:red;">*</i>
	                                      </div>
	                                  </div>
	                              </div>
	                              <div class="row">
	                                <div class="col-md-12">
	                                    <div class="form-group">
	                                        <label>地址信息</label>
	                                        <input type="text" id="userAddress" name="userAddress" class="form-control input-inline input-large" placeholder="">
	                                    </div>
	                                </div>
	                              </div>
	                              <div class="row">
	                                    <div class="col-md-12">
	                                        <div class="form-group">
	                                            <label>使用者描述</label>
	                                            <textarea id="description" name="description" class="form-control  input-inline input-large" rows="3"></textarea>
	                                        </div>
	                                    </div>
	                              </div>
	                            <!-- </form> -->
	                            <h4>主机资料</h4>
	                           <!--  <form class="form-horizontal"> -->
	                                <div class="row">
	                                    <div class="col-md-6">
	                                        <div class="form-group">
	                                            <label>设备编号</label>
	                                            <input type="text" id="uniqueId" name="uniqueId" class="form-control input-large input-inline" readonly="readonly" placeholder="" style="position: absolute;">
	                                            <i class="fa tooltips" style="color:red;">*</i>
	                                        </div>
	                                    </div>
	                                    <div class="col-md-6">
	                                        <div class="form-group">
	                                            <label>安全码</label>
	                                            <input type="text"  id="securityCode" name="securityCode" class="form-control input-small input-inline" readonly="readonly" placeholder="">
	                                            <i class="fa tooltips" style="color:red;">*</i>
	                                        </div>
	                                    </div>
	                                </div>
	                                <div class="row">
	                                    <div class="col-md-12">
	                                        <div class="form-group">
	                                            <label>SIM卡号</label>
	                                            <input type="text" id="simNumber" name="simNumber" class="form-control input-inline input-large" placeholder="">
	                                            <i class="fa tooltips" style="color:red;">*</i>
	                                        </div>
	                                    </div>
	                                </div>
	                                <div class="row">
	                                    <div class="col-md-6">
	                                        <div class="form-group">
	                                            <label>设备类型</label>
	                                            <select id="deviceType" name="devicesTypeId" class="form-control input-small input-inline">
	                                               <!--  <option value="top">人员调动</option>
	                                                <option value="bottom">财务管理</option> -->
	                                            </select>
	                                            <i class="fa tooltips" style="color:red;">*</i>
	                                        </div>
	                                    </div>
	                                    <div class="col-md-6">
	                                        <a href="javascript:void(0)" class="btn red" id="devicesType">同步设备类型</a>
	                                    </div>
	                                </div>
	                           <!--  </form> -->
	                            <h4>联系人</h4>
	                            <div class="clearfix top_btnlist text-right">
	                                <a class="linkmen_add" data-toggle="modal" href="javascript:void(0)" id="addLinkman"><i class="glyphicon glyphicon-plus"></i>添加</a>
	                                <a class="linkmen_del cur" data-toggle="modal" href="#" id="removeLinkman"><i class="glyphicon glyphicon-remove"></i>删除</a>
	                            </div>
	                            <table class="table-bordered mod_table" id="linkManTable">
	                                <tr>
	                                    <th style="height: 30px;">姓名</th>
	                                    <th style="height: 30px;">电话</th>
	                                    <th style="height: 30px;">手机</th>
	                                    <th style="height: 30px;">描述</th>
	                                </tr>
	                            </table>
	                             <input type="hidden" id="linkmen" name="linkmen"/>
	                            <div class="modal-footer clearfix">
	                                <button type="button" data-dismiss="modal" class="btn btn-lg default pull-left" onclick="updateReset();">重置</button>
	                                <button type="button" data-dismiss="modal" class="btn btn-lg red pull-right" id="update">保存</button>
	                            </div>
	                        </div>
	                        <div class="col-lg-4">
	                            <h4>选择分组</h4>
								<div class="scroll" style="border: 1px solid #000; overflow: hidden; height: 500px; width: 300px;">
										<ul class="menu">
										
										</ul>
								</div>
	                        </div>
	                    </div>
	                </div>
	            </div>
	            </form>
	        </div>
	    </div>
	</div><!-- END 弹出层--增加---->
	<script src="../../js/jquery.min.js"></script>
	<script src="../../js/linkMen.js"></script>
	<script src="../../js/device/device.js"></script>
	<script src="../../js/layer/layer.js"></script>
	<script src="../../js/check.js"></script>
	<script src="../../js/addDeviceZD.js"></script>
</body>
</html>