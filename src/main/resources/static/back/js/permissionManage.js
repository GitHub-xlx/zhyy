

//左移和右移
layui.use(['tree', 'form', 'util', 'jquery', 'layer'], function () {
	var $ = layui.jquery
		, tree = layui.tree
		, layer = layui.layer
		, util = layui.util
		, form = layui.form;
	//初始加载

	$(function () {
		$('#chooseRole').empty();
		$('#chooseRole').append('<option value="">请选择角色</option>');
		//ajax
		$.ajax({
			type: "POST",
			url: "sysController/queryRole",
			dataType: "text",
			data: {},
			success: function (msg) {
				var list = JSON.parse(msg);
				for (var i = 0; i < list.length; i++) {
					$('#chooseRole').append('<option value="' + list[i].rolecode + '">' + list[i].rolename + '</option>');
				}
				form.render();
				$(getMenu());
			},
			error: function () {
				layer.msg('服务器繁忙');
			}
		});
	});
	$('#save').click(function () {
		if ($('#chooseRole').val()==='') {
			layer.msg("请先选择您要分配的角色")
		}else {
			layer.confirm("您确定要更改该角色所拥有的权限吗？",function () {
				var list = tree.getChecked('permission');

				if (list.length>0){
					$.ajax({
						headers: {
							'Accept': 'application/json',
							'Content-Type': 'application/json'
						},
						// traditional:true,
						// contentType : "application/json;charsetset=UTF-8",
						type: 'post'
						, async: false
						, url: "sysController/savePermission"
						, data: JSON.stringify(list)
						, success: function (res) {
							if (res.code==200){
								layer.msg("权限修改成功");
							}
							if (res.code==500){
								layer.msg("权限修改失败");
							}
							getMenu($('#chooseRole').val());
						}
					});
				} else {

					$.ajax({
						type: 'post'
						, url: "sysController/openPermissionByRoleCode"
						, data: {rolecode:$('#chooseRole').val()}
						, success: function (res) {
							if (res.code==200){
								layer.msg("权限修改成功");
							}
							if (res.code==500){
								layer.msg("权限修改失败");
							}
							getMenu($('#chooseRole').val());
						}
					});
				}

			})
		}


	});


	form.on('select(chooseRole)', function (data) {
		var rolecode = data.value;

		getMenu(rolecode);

	})
});


function getMenu(rolecode) {
	layui.use(['tree', 'util', 'jquery', 'layer'], function () {

		var $ = layui.jquery;
		var tree = layui.tree;
		var layer = layui.layer;
		var util = layui.util;

		$.ajax({
			type: 'post'
			, async: false
			, url: "sysController/permissionManage"
			, data: {rolecode: rolecode}
			, success: function (res) {
				tree.render({
					elem: '#test12',
					data: res,
					id: 'permission',
					showCheckbox: true,     //是否显示复选框
					onlyIconControl: true,
					accordion: true
				})
			}
		});

	})
}