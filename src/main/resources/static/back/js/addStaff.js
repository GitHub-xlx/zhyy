layui.use(['table', 'jquery', 'form'], function () {
	var table = layui.table
		, $ = layui.jquery
		, form = layui.form;


//获取下拉框的值
	var role;
	form.on('select(role)', function (data) {
		role = data.value;
		if (role === '01') {
			$('#docInfo2').css('display', 'none');
			$('#docInfo').css('display', 'block');
		} else if (role === '02') {
			$('#docInfo2').css('display', 'block');
			$('#docInfo').css('display', 'none');
		}

	});
//获取下拉框职称的值
	var title = '';
	form.on('select(title)', function (data) {
		title = data.value;
	});

// //获取下拉框职称的值
// var title2 = '';
	form.on('select(title2)', function (data) {
		title = data.value;
	});

//重新渲染
	form.render();


	$('#account').blur(function () {
		var account = $(this).val().trim();
		$.ajax({
			type: "POST",
			url: "userController/checkUser",
			//发送的数据（同时也将数据发送出去）
			data: {account: account},
			success: function (msg) {
				if (msg === '2') {
					$('#ri').removeAttr('hidden');
					$('#wr').attr('hidden', 'hidden');
					flag = true;
				} else {
					$('#wr').removeAttr('hidden');
					$('#ri').attr('hidden', 'hidden');
					layer.msg('当前账号已存在!');
					flag = false;
				}
			},
			error: function (msg) {
				alert("服务器正忙。。。。" + msg);
			}
			, dataType: "text"//设置接受到的响应数据的格式
		})
	});

//新增人员确定提交
	$("#submit").click(function () {

		var account = $('#account').val();
		var password = $('#password').val();
		var confirmPassword = $('#confirmPassword').val();
		var username = $('#username').val();
		var phone = $('#phone').val();
		var sex = $('input[name="sex"]:checked').val();
		var age = $('#age').val();
		var role = $('#role').val();
		// var title = $('#title').val();
		// var title2 = $('#title2').val();

		// alert(account + "," + password + "," + confirmPassword + "," + username + "," + phone + "," + sex
		// 	+ "," + age + "," + role + ",职位为：" + title);

		if (account.length >= 6 && account.length <= 12) {

			if (password.length >= 6 && password.length <= 12) {

				if (password === confirmPassword) {

					if (phone.length === 11) {

						$.ajax({
							type: "POST", //请求方式
							url: 'userController/regStaff', // 请求路径
							data: {
								account2: account, password2: password, username2: username
								, phone2: phone, sex2: sex, age2: age, roles: role, titles: title
							},
							success: function (msg) {
								if (msg === '1') {
									layer.alert("注册成功");
									window.location.reload();
								} else {
									layer.alert("注册失败，请重新尝试");
								}
							},//响应成功后的回调函数
							error: function () {
								alert("服务器繁忙")
							},//表示如果请求响应出现错误，会执行的回调函数
							dataType: "text"//设置接受到的响应数据的格式
						});

					} else {
						alert("电话号码长度为11位")
					}

				} else {
					alert("两次密码输入不一致")
				}
			} else {
				alert("密码的长度大于6位且不超过12位")
			}

		} else {
			alert("账号的长度大于6位且不超过12位")
		}

	});
});