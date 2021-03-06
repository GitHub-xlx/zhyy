layui.use(['form', 'layer', 'jquery', 'table'], function () {
	var table = layui.table;
	var form = layui.form;
	var $ = layui.jquery;
	var layer = layui.layer;


	// // 获取下拉框数据
	// form.on('select(user_state)', function (data) {
	// 	category = data.value;
	// });

	table.render({
		elem: '#test'
		, even: true
		, page: true
		, width: 900
		, limit: 5
		, limits: [5, 10, 15, 20, 25, 30]
		, cols: [[
			{field: 'menucode', width: 120, title: '菜单编号', sort: true, align: 'center'}
			, {field: 'parentcode', width: 140, title: '上级菜单编号', sort: true, align: 'center'}
			, {field: 'menu', width: 130, title: '菜单名称', align: 'center'}
			, {field: 'url', width: 210, title: '跳转路径', align: 'center'}
			, {field: 'state', width: 100, title: '状态', align: 'center', toolbar: '#toolbar1'}
			, {field: 'operate', width: 200, title: '操作', align: 'center', toolbar: '#toolbar'}

		]]
		, url: "sysController/menuManage"
		, where: { //设定异步数据接口的额外参数，任意设
			menucode: $('#menucode').val(),
			menu: $('#menu').val()
		}
		, id: 'userTable'
	});

// 用于带条件查询
	$("#query_bt").click(function () {
		table.reload('userTable', {
			url: "sysController/menuManage"
			, where: { //设定异步数据接口的额外参数，任意设
				menucode: $('#menucode').val(),
				menu: $('#menu').val()
			}
			, page: {
				curr: 1 //重新从第 1 页开始
			}
		});
		// Layui表格,刷新当前分页数据
		// $(".layui-laypage-btn").click()
	});

	$("#add_bt").click(function () {
		var that = this;
		//多窗口模式，层叠置顶
		layer.open({
			type: 2 //此处以iframe举例
			, title: '添加菜单'
			, area: ['500px', '600px']
			, shade: 0
			, maxmin: true
			, offset: [ //为了演示，随机坐标
				($(window).height() * 0.01)
				, ($(window).width() * 0.35)
			]
			, content: 'jump/back/addMenu'
			// ,btn: ['继续弹出', '全部关闭'] //只是为了演示
			, yes: function () {
				$(that).click();
			}
			, btn2: function () {
				layer.closeAll();
			}

			, zIndex: layer.zIndex //重点1
			, success: function (layero) {
				layer.setTop(layero); //重点2
			}, end: function () {
				window.location.reload();
			}
		});

	});
	table.on('tool(table-user)', function (obj) {
		var data = obj.data
		,event = obj.event
		,menucode = data.menucode
		,state = data.state
		,parentcode = data.parentcode;

		if (event === 'able') {
			if (parentcode==0){
				layer.confirm('此菜单为一级菜单，将同时作用于下级菜单，您确定要进行此操作吗？', function (index) {

					$.ajax({
						type: "POST",
						url: "sysController/ableMenu",
						//发送的数据（同时也将数据发送出去）
						data: {
							menucode: menucode,
							state: state,
							parentcode:parentcode
						},
						success: function (data) {

							if (data.code == 200) {
								table.reload('userTable');
								layer.msg("状态修改成功");
							}else{
								layer.msg("状态修改失败");
							}
						},
						error: function (msg) {
							alert("服务器正忙。。。。" + msg);
						}
					});
			})
			}else{
				layer.confirm('您确定要进行此操作吗？', function (index) {

					$.ajax({
						type: "POST",
						url: "sysController/ableMenu",
						//发送的数据（同时也将数据发送出去）
						data: {menucode: menucode, state: state,parentcode:parentcode},
						success: function (data) {

							if (data.code == 200) {
								table.reload('userTable');
								layer.msg("状态修改成功");
							}else{
								layer.msg("状态修改失败");
							}
						},
						error: function (msg) {
							alert("服务器正忙。。。。" + msg);
						}
					});
			});

				// return false;

				layer.close(index);
			}
		} else if (event === 'edit') {
			layer.open({
				type: 2 //此处以iframe举例
				, title: '修改菜单'
				, area: ['500px', '350px']
				, shade: 0
				, maxmin: true
				, offset: [ //为了演示，随机坐标
					($(window).height() * 0.25)
					, ($(window).width() * 0.35)
				]
				, content: 'jump/back/editMenu'
				// ,btn: ['继续弹出', '全部关闭'] //只是为了演示
				, yes: function () {
					$(that).click();
				}
				, btn2: function () {
					layer.closeAll();
				}

				, zIndex: layer.zIndex //重点1
				, success: function (layero,index) {
					layer.setTop(layero); //重点2
					let body =layer.getChildFrame('body',index);
					body.find('#menuname').val(data.menu);
					body.find('#url').val(data.url);
					body.find('#menucode').val(data.menucode);

				}, end: function () {
					window.location.reload();
				}
			});

		}
	})
});