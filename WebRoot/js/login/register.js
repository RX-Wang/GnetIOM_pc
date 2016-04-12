var param = param || {};
param.GMSG_HEAD = {};
param.GMSG_QM = {};
$(function() {
	// 初始化ajax
	$.ajaxSetup({
		timeout : 30000,
		type : "post",
		dataType : 'json',
		error : function() {
		}
	});

	// 光标默认为用户名
	// 初始化，光标默认在用户名文本框
	// $("input[name='userName']").focus();
	$("#userName").focusout(function() {
		if ($(this).val().trim() != "") {
			var regUserName = /^[a-zA-Z_][a-zA-Z0-9_]{4,18}$/;
			if (!regUserName.test($(this).val())) {
				$('#u_style').html('只能输入5-18个以字母开头、可带数字、下划线的字串 ');
				return false;
			} else {
				$.ajax({					
					type : "POST",
					url : "web/user/validateUserName",
					data : {
						"userName" : $(this).val()
					},
					dataType : "json",
					success : function(retJs) {
						var result = retJs.DataResult;
						if (result == 'fail') {
							$('#u_style').html('该用户名已被注册!');
						} else {
							$('#u_style').html('恭喜您，该用户名可以注册!');
						}
					}
				});
			}
		}
	});

	// 密码
	$("input[type='password']").focusout(function() {
		var passWord1 = $('#passWord1').val();
		var passWord2 = $('#passWord2').val();

		if (passWord1.trim() != "" && passWord2.trim() != "") {
			if (passWord1 != passWord2) {
				$('#c_style').html('两次输入密码不一样!');
			} else {
				$('#c_style').html('');
			}

		}

	})

	// 注册
	$("#r_button").click(function() {

		if (validateUserName()) {
			//layer.alert("用户名已被注册");
			$('#u_style').html('该用户名已被注册!');
			return false;
		}

		var regUserName = /^[a-zA-Z_][a-zA-Z0-9_]{4,18}$/;
		if (!regUserName.test($('#userName').val())) {
			//layer.alert('只能输入5-18个以字母开头、可带数字、下划线的字串 ');
			$('#u_style').html('只能输入5-18个以字母开头、可带数字、下划线的字串 ');
			return false;
		}
		var passWord1 = $('#passWord1').val();
		var passWord2 = $('#passWord2').val();
		if (passWord1 != passWord2) {
			//layer.alert('两次输入密码不一样');
			$('#c_style').html('两次输入密码不一样!');
			return false;
		}
		// 传值
		$
		.ajax({
			type : "POST",
			url : "web/user/register",
			data : {
				"userName" : $("#userName").val(),
				"passWord" : $("#passWord1").val()				
			},
			dataType : "json",
			success : function(retJs) {
				var result = retJs.DataResult;
				if (result == 'pass') {
					window.location.href = "web/pageController/loginPage";
				}
				else
				{
					layer.alert("注册失败！");
				}
				
			}});
	});

})

// 验证用户名
function validateUserName() {
	var validateUserName = false;
	$.ajax({
		type : "POST",
		dataType : "text",
		url : "web/user/validateUserName",
		data : {
			userName : $("#userName").val()
		},
		async : false,
		success : function(data) {
			if (data == 'false') {
				validateUserName = true;
			}
		}
	});
	return validateUserName;
}


