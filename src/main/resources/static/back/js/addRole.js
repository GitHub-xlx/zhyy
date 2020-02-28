layui.use(['form', 'layer', 'jquery', 'table'], function () {
	var table = layui.table;
	var form = layui.form;
	var $ = layui.jquery;
	var layer = layui.layer;
	var chooseDepartment = '';
	var flag = false;
	form.on('select(chooseDepartment)', function (data) {
		chooseDepartment = data.value;
	});

	$(function () {
		$('#chooseDepartment').empty();
		$('#chooseDepartment').append('<option value="">请选择部门</option>');
		//ajax
		$.ajax({
			type: "POST",
			url: "sysController/queryDepartment",
			dataType: "text",
			data: {},
			success: function (msg) {
				var list = JSON.parse(msg);
				for (var i = 0; i < list.length; i++) {
					$('#chooseDepartment').append('<option value="' + list[i].departmentcode + '">' + list[i].departmentname   + '</option>');
				}
				form.render();
			},
			error: function () {
				layer.msg('服务器繁忙');
			}
		});
	});
	$('#rolecode').blur(function () {
		var rolecode = $(this).val().trim();
		var regExp=/^\d+(\.\d+)?$/;
		if (rolecode.length>0&&regExp.test(rolecode)) {
			$.ajax({
				type: "POST",
				url: "sysController/checkRole",
				//发送的数据（同时也将数据发送出去）
				data: {rolecode: rolecode},
				success: function (data) {
					if (data.code==200) {
						$('#ri').removeAttr('hidden');
						$('#wr').attr('hidden', 'hidden');
						flag = true;
					} else {
						$('#wr').removeAttr('hidden');
						$('#ri').attr('hidden', 'hidden');
						layer.msg('当前菜单编号已存在!');
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
			layer.msg('请输入正确的角色编号！');
			flag = false;
		}

	});
	$('#confirm_bt').click(function () {
		if (chooseDepartment.length > 0) {
			if ($('#rolecode').val().length>0&&$('#rolename').val().length>0) {
				if (flag){
					//ajax
					$.ajax({
						type: "POST",
						url: "sysController/addRole",
						data: {
							department: chooseDepartment,
							rolecode: $('#rolecode').val(),
							rolename: $('#rolename').val(),
						},
						success: function (data) {
							if (data.code==200) {

								var index = parent.layer.getFrameIndex(window.name);

								parent.layer.close(index);
								parent.layer.msg('角色添加成功', {icon: 6});
							}else if (data.code==305) {
								layer.msg('新增失败，请稍后再试')
							}
							layer.close();
						},
						error: function () {
							layer.msg('服务器繁忙');
						}
					});
				}else{
					layer.msg("请输入合规的角色编号");
				}
			}else{
				layer.msg("请将角色信息填写完整");
			}
		} else {
			layer.msg("请先选择部门");
		}

	});
});