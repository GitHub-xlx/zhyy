


// var path = document.getElementById("path").value;
// reg
layui.use(['form', 'jquery', 'layer'], function () {
	var form = layui.form;
	var $ = layui.jquery;
	var layer = layui.layer;
	//添加表单失焦事件
	//验证表单
	var flag = false;
	$('#oldPassword').blur(function () {
		var password = $(this).val().trim();
		if (password.length>0) {
			$.ajax({
				type: "POST",
				url: "sysController/checkPassword",
				//发送的数据（同时也将数据发送出去）
				data: {
					account:$('#account').text(),
					password: password
				},
				success: function (data) {
					if (data.code==200) {
						$('#ri').removeAttr('hidden');
						$('#wr').attr('hidden', 'hidden');
						flag = true;
					} else {
						$('#wr').removeAttr('hidden');
						$('#ri').attr('hidden', 'hidden');
						layer.msg('原密码输入错误!');
						flag = false;
					}
				},
				error: function (msg) {
					alert("服务器正忙。。。。" + msg);
				}
			})
		}else {
			$('#wr').removeAttr('hidden');
			$('#ri').attr('hidden', 'hidden');
			layer.msg('原密码输入错误！ ');
			flag = false;
		}

	});

	// 为密码添加正则验证,包括下划线的5~16个字符串
	$('#newPassword').blur(function () {
		var reg = /^[\w]{5,16}$/;
		if (!$('#newPassword').val()===$('#oldPassword').val){
			if (!($('#newPassword').val().match(reg))) {
				//layer.msg('请输入合法密码');
				$('#pwr').removeAttr('hidden');
				$('#pri').attr('hidden', 'hidden');
				flag = false;
				layer.msg('请输入合法密码');
			} else {
				$('#pri').removeAttr('hidden');
				$('#pwr').attr('hidden', 'hidden');
				flag = true;
			}
		} else{
			$('#pwr').removeAttr('hidden');
			$('#pri').attr('hidden', 'hidden');
			flag = false;
			layer.msg('新密码不能与旧密码相同');
		}

	});
	$('#confirm').blur(function () {
		var reg = /^[\w]{5,16}$/;
		if (!($('#confirm').val()===$('#newPassword').val())) {
			//layer.msg('请输入合法密码');
			$('#cwr').removeAttr('hidden');
			$('#cri').attr('hidden', 'hidden');
			flag = false;
			layer.msg('两次密码输入不一致');
		} else {
			$('#cri').removeAttr('hidden');
			$('#cwr').attr('hidden', 'hidden');
			flag = true;
		}
	});





//验证所有数据都符合要求
// 		form.on('submit(reg)', function () {
//
// 			if (reg_flag){
//
// 				var index = parent.layer.getFrameIndex(window.name);
// 				parent.layer.close(index);
// 				parent.layer.msg('注册成功,请登录。');
// 			} else{
// 				return false;
// 			}
// 		});
	//工作人员表单提交
	$('#confirm_bt').click(function () {

			if ($('#oldPassword').val().length>0&&$('#newPassword').val().length>0&&$('#confirm').val().length>0) {
				if (flag){
					//ajax
					$.ajax({
						type: "POST",
						url: "sysController/editPassword",
						data: {
							account:$('#account').val(),
							newPassword: $('#newPassword').val(),

						},
						success: function (data) {
							if (data.code==200) {

								var index = parent.layer.getFrameIndex(window.name);

								parent.layer.close(index);
								parent.layer.msg('密码修改成功', {icon: 6});
							}else if (data.code==305) {
								layer.msg('密码修改失败，请稍后再试')
							}
							layer.close();
						},
						error: function () {
							layer.msg('服务器繁忙');
						}
					});
				}else{
					layer.msg("请输入正确的密码信息");
				}
			}else{
				layer.msg("请输入正确的密码信息");
			}


	});

});






