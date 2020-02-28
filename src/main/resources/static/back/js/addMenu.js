layui.use(['form', 'layer', 'jquery', 'table'], function () {
	var table = layui.table;
	var form = layui.form;
	var $ = layui.jquery;
	var layer = layui.layer;
	var chooseMenu = '';
	var flag = false;
	form.on('select(chooseMenu)', function (data) {
		chooseMenu = data.value;
	});

	$(function () {
		$('#chooseMenu').empty();
		$('#chooseMenu').append('<option value="">请选择父级菜单</option>');
		//ajax
		$.ajax({
			type: "POST",
			url: "sysController/queryParentMenu",
			dataType: "text",
			data: {},
			success: function (msg) {
				var list = JSON.parse(msg);
				for (var i = 0; i < list.length; i++) {
					$('#chooseMenu').append('<option value="' + list[i].menucode + '">' + list[i].menu   + '</option>');
				}
				form.render();
			},
			error: function () {
				layer.msg('服务器繁忙');
			}
		});
	});
	$('#menucode').blur(function () {
		var menucode = $(this).val().trim();
		var regExp=/^\d+(\.\d+)?$/;
		if (menucode.length>0&&regExp.test(menucode)) {
			$.ajax({
				type: "POST",
				url: "sysController/checkMenu",
				//发送的数据（同时也将数据发送出去）
				data: {menucode: menucode},
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
			layer.msg('请输入正确的菜单编号！');
			flag = false;
		}

	});
	$('#confirm_bt').click(function () {
		if (chooseMenu.length > 0) {
			if ($('#menucode').val().length>0&&$('#menuname').val().length>0&&$('#url').val().length>0) {
				if (flag){
					//ajax
					$.ajax({
						type: "POST",
						url: "sysController/addMenu",
						data: {
							parentcode: chooseMenu,
							menucode: $('#menucode').val(),
							menuname: $('#menuname').val(),
							url: $('#url').val(),
						},
						success: function (data) {
							if (data.code==200) {

								var index = parent.layer.getFrameIndex(window.name);

								parent.layer.close(index);
								parent.layer.msg('菜单添加成功', {icon: 6});
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
					layer.msg("请输入合规的菜单编号");
				}
			}else{
				layer.msg("请将菜单信息填写完整");
			}
		} else {
			layer.msg("请先选择父级菜单");
		}

	});
});