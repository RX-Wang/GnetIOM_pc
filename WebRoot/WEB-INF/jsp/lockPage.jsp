<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <meta content="" name="description"/>
    <meta content="" name="author"/>
    <link href="../../assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/><!--字体图标-->
    <link rel="stylesheet" type="text/css"  href="../../assets/plugins/ElegantIcons/style.css"><!--字体图标-->
    <link rel="stylesheet" type="text/css"  href="../../assets/plugins/simple-line-icons/simple-line-icons.min.css"><!--字体图标-->
    <link rel="stylesheet" type="text/css"  href="../../css/bootstrap.css"><!--base-->
    <link rel="stylesheet" type="text/css"  href="../../assets/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"/><!--table-->
    <link rel="stylesheet" type="text/css"  href="../../css/main.css"/><!--自定义-->
<title>Insert title here</title>
</head>
<body onload="killF5()">
	<!-- 弹出层--锁屏-->
	<!-- <div class="modal fade" id="lock"> --><!--  tabindex="-1" role="dialog" aria-hidden="true" style="display: none;" -->
	    <!-- <div class="modal-dialog modal-md"> -->
	        <!-- <div class="modal-content"> -->
	            <div class="modal-header">
	                <!-- <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button> -->
	                <h4>锁屏模式</h4>
	            </div>
	            <div class="modal-body">
	                <form class="form-horizontal">
	                    <div class="form-group">
	                        <div class="col-md-8" style="float: left">
	                            <label style="float: left">用户帐号</label>
	                            <input id="lockCode" type="text" class="form-control input-inline" style="width: 300px;">
	                        </div>
	                        <!-- <div class="col-md-4" style="float: left;"><a href="javascript:void(0)" class="btn blue">切换账号</a></div> -->
	                    </div>
	                    <div class="form-group" style="margin-top: 20px;">
	                        <div class="col-md-8" style="float: left">
	                            <label style="float: left">密码</label>
	                            <input id="lockPwd" type="password" class="form-control input-inline" style="width: 300px;">
	                        </div>
	                    </div>
	                </form>
	            </div>
	            <div class="modal-footer clearfix">
	                <button type="button" onclick="openLock(this)" data-toggle="modal" class="btn red " style="background-color:#c23f44;color:#fff">确定</button>
	                <button type="button" onclick="cleanLock()" data-toggle="modal" class="btn default ">重置</button>
	            </div>
	        <!-- </div> -->
	        <!-- /.modal-content -->
	   <!--  </div> -->
	    <!-- /.modal-dialog -->
	<!-- </div> --><!-- END 弹出层--锁屏---->
	<script src="../../js/jquery.min.js"></script>
	<script type="text/javascript" src="../../js/layer/layer.js"></script>
	<script type="text/javascript" src="../../js/check.js"></script>
	<script type="text/javascript" src="../../js/global.js"></script>
</body>
</html>