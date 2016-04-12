<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<% String id= (String)request.getAttribute("devId"); %>
<!DOCTYPE html>
<html>
<head>
    <title>授权</title>
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
	<!-- 弹出层--授权  tabindex="-1" role="dialog"aria-hidden="true" style="display: none;"-->
	<input type="hidden" id="devId" value="<%=id%>">
	<div class="">  <!-- modal fade -->
		<div class=""><!-- modal-dialog modal_wid420 modal_hei200 -->
			<div class=""><!-- modal-content -->
				<!-- <div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true"></button>
					<h3>设备授权维护</h3>
				</div> -->
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
						<tbody id="tbBindUser">
							
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<!-- <button type="button" data-dismiss="modal" class="btn red btn-lg" href="#addShouquan">添加授权</button> -->
					 <a data-toggle="modal" class="btn red btn-lg" href="#addShouquan" >添加授权</a><!--  data-dismiss="modal"  -->
				</div>
			</div>
		</div>
	</div>
	<!-- END 弹出层--授权---->
<!-- 弹出层--解除授权-->
	<div id="jiechuDIV"  class="modal fade"  tabindex="-1" role="dialog"aria-hidden="true" style="display: none;">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true"></button>
				</div>
				<div class="modal-body">
					<h4 class="text-center">您确认要解除授权关系吗？</h4>
				</div>
				<div class="modal-footer clearfix">
					<button id="cancelSQ" type="button" data-dismiss="modal"
						class="btn red pull-right">确定</button>
					<button type="button" data-dismiss="modal"
						class="btn default pull-left">取消</button>
				</div>
			</div>
		</div>
	</div>
<!-- 弹出层--解除授权-->
	<div id="addShouquan"  class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
		<!-- 弹出层--添加授权账户-->
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true"></button>
					<h4 class="text-center">添加授权帐号</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal">
						<div class="form-group">
							<label>账号类型</label> <select id="accountType"
								class="form-control input-medium input-inline">
								<option value="个人账号">个人账号</option>
								<option value="集团账号">集团账号</option>
							</select>
						</div>
						<div class="form-group">
							<label>授权账号</label> <input type="text" id="accountNum"
								class="form-control input-medium input-inline" placeholder="" onblur="javascript:checkAccount();">
						</div>
					</form>
				</div>
				<div class="modal-footer clearfix">
					<button id="addSQ" type="button" data-toggle="modal" class="btn red">添加授权</button>
				</div>
			</div>
		</div>
		<!-- end 弹出层--添加授权账户-->
	
	</div>
	<script src="../../js/jquery.min.js"></script>
	<script src="../../js/bootstrap.min.js"></script>
	<script type="text/javascript" src="../../js/shouquan.js"></script>
	<script src="../../js/check.js"></script>
	<script src="../../js/layer/layer.js"></script>
</body>
</html>