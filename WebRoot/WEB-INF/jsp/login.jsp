<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录首页</title>
    <title></title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <meta content="" name="description"/>
    <meta content="" name="author"/>
    <link rel="stylesheet" type="text/css"  href="../../assets/plugins/font-awesome/css/font-awesome.min.css"><!--字体图标-->
    <link rel="stylesheet" type="text/css"  href="../../assets/plugins/ElegantIcons/style.css"><!--字体图标-->
    <link rel="stylesheet" href="../../asset/plugins/simple-line-icons/simple-line-icons.min.css"><!--字体图标-->
    <link rel="stylesheet" href="../../css/bootstrap.css">
    <link rel="stylesheet" href="../../assets/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css">
    <link rel="stylesheet" type="text/css" href="../../assets/css/components.css"><!--BASE-->
    <link rel="stylesheet" href="../../assets/css/plugins.css"><!--BASE-->
    <link rel="stylesheet" type="text/css" href="../../css/main.css">
    <link rel="stylesheet" type="text/css" href="../../css/jquery.mCustomScrollbar.css">
    <script src="../../js/jquery.min.js"></script>
    <script src="../../js/layer/layer.js"></script>
    <script src="../../js/bootstrap.min.js"></script>
    <script src="../../js/check.js"></script>
    <script src="../../js/normal.js"></script>
    <script src="../../js/login/login.js" type="text/javascript"></script>
    <script type="text/javascript">
    </script>
</head>

<body>
	<div class="o_warpper" id="loginDiv">
	    <div class="login_box">
	        <span class="o_user_img"><img src="../../images/user_img.png"/></span>
	        <ul class="o_login_tab" id="tablist">
	            <li class="active first-child"><a>用户账号</a></li>
	            <li><a>集团帐号</a></li>
	        </ul>
	        <div id="tabcon">
	            <div class="item loginUser">
	                <h4><a></a></h4>
	                <div class="o_login_con">
	                    <div class="user_login"><span class="icon-user"></span><input type="text" id="userName" placeholder="用户账号" class="first-child" value=""/></div>
	                    <div class="user_login"><span class="icon-lock"></span><input type="password" id="userPwd" placeholder="密码"  value=""/></div>
	                </div>
	                <a class="login_btn" href="javascript:checUserkLogin();" >登录</a>
	            </div>
	            <div class="item loginCompinany" style="display: none">
	                <h4><a onclick="toRegFun()">注册集团账号</a></h4>
	                <div class="o_login_con">
	                    <div class="user_login"><span class="icon-user"></span><input type="text" id="companyId" placeholder="集团帐号" class="first-child" value=""/></div>
	                    <div class="user_login"><span class="icon-lock"></span><input type="password" id="companyPwd" placeholder="密码" value=""/></div>
	                </div>
	                <a class="login_btn" href="javascript:checkCompanyLogin();" >登录</a>
	            </div>
	        </div>
	    </div>
	    <div class="login_bg"></div>
	</div>
	<div class="o_warpper" id="regDiv" hidden="true">
	    <div class="login_box login_box2">
	        <h3>集团信息注册</h3>
	        <form class="form-horizontal" role="form">
	            <div class="form-body">
	                <div class="form-group ">
	                    <label class="col-sm-3">集团ID</label>
	                    <div class="col-sm-8">
	                        <div class="input-icon right">
	                            <i class="fa tooltips">*</i>
	                            <input id="regCompId" type="text" class="form-control form-inline">
	                        </div>
	                    </div>
	                </div>
	                <div class="form-group ">
	                    <label class="col-md-3 ">名称</label>
	                    <div class="col-md-8">
	                        <div class="input-icon right">
	                            <i class="fa tooltips">*</i>
	                            <input id="regCompName" type="text" class="form-control">
	                        </div>
	                    </div>
	                </div>
	                <div class="form-group ">
	                    <label class="col-md-3 ">密码</label>
	                    <div class="col-md-8">
	                        <div class="input-icon right">
	                            <i class="fa tooltips">*</i>
	                            <input id="regCompPwd1" type="password" class="form-control" maxlength="18">
	                        </div>
	                    </div>
	                </div>
	                <div class="form-group ">
	                    <label class="col-md-3 ">确认密码</label>
	                    <div class="col-md-8">
	                        <div class="input-icon right">
	                            <i class="fa tooltips">*</i>
	                            <input id="regCompPwd2" type="password" class="form-control" maxlength="18">
	                        </div>
	                    </div>
	                </div>
	                <div class="form-group ">
	                    <label class="col-md-3 ">网址</label>
	                    <div class="col-md-8">
	                        <div class="input-icon right">
	                            <i class="fa tooltips">*</i>
	                            <input id="regCompEmail" type="text" class="form-control">
	                        </div>
	                    </div>
	                </div>
	                <div class="form-group ">
	                    <label class="col-md-3 ">电话</label>
	                    <div class="col-md-8">
	                        <div class="input-icon right">
	                            <i class="fa tooltips">*</i>
	                            <input id="regCompTel" type="text" class="form-control">
	                        </div>
	                    </div>
	                </div>
	                <div class="form-group ">
	                    <label class="col-md-3 ">地址</label>
	                    <div class="col-md-8">
	                        <div class="input-icon right">
	                            <i class="fa tooltips">*</i>
	                            <textarea id="regCompAdd" class="form-control  " rows="3"></textarea>
	                        </div>
	                    </div>
	                </div>
	                <div class="text-center footer_btn margin-top-50">
	                    <a class="active" id="regBtn" onclick="regFun();">注册</a>
	                    <a class="active" id="regBtn" onclick="window.location.href='../../web/pageController/loginPage'">返回</a>
	                </div>
	            </div>
	        </form>
	    </div>
	    <div class="login_bg"></div>
	</div>
</body>
</html>