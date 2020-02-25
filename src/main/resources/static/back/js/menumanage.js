layui.use(['form','layer','jquery','table'], function(){
	var table = layui.table;
	var form = layui.form;
	var $ = layui.jquery;
	var layer  = layui.layer;


	// // 获取下拉框数据
	// form.on('select(user_state)', function (data) {
	// 	category = data.value;
	// });

	table.render({
		elem: '#test'
		,even: true
		,page: true
		,width: 800
		,limit:5
		,limits:[5,10,15,20,25,30]
		,cols: [[
			{field:'menucode', width:100, title: '菜单编号',sort: true,align:'center'}
			,{field:'parentcode', width:120, title: '上级菜单编号',sort: true,align:'center'}
			,{field:'menu', width:100, title: '菜单名称',align:'center'}
			,{field:'url', width:230, title: '跳转路径',align:'center'}
			,{field:'state', width:100, title: '状态',align:'center',toolbar: '#toolbar1'}
			,{field:'operate', width:200, title: '操作',align:'center', toolbar: '#toolbar'}

		]]
		,url:"sysController/menuManage"
		,where: { //设定异步数据接口的额外参数，任意设
			menucode: $('#menucode').val(),
			menu: $('#menu').val()
		}
		,id:'userTable'
	});

// 用于带条件查询
	$("#query_bt").click(function () {
		table.reload('userTable',{
			url:"sysController/menuManage"
			,where: { //设定异步数据接口的额外参数，任意设
				menucode: $('#menucode').val(),
				menu: $('#menu').val()
			}
			,page: {
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
			, area: ['600px', '800px']
			, shade: 0
			, maxmin: true


			, offset: [ //为了演示，随机坐标
				($(window).height() * 0.01)
				, ($(window).width() * 0.35)
			]
			, content: 'HtmlServlet?methodName=showAddWorker'
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
			},end:function () {
				window.location.reload();
			}
		});

	});
	table.on('tool(table-user)',function (obj) {
		var data = obj.data;
		var event = obj.event;
		var account = data.user_Account;
		var state = data.state;

		if (event==='able'){
			layer.confirm('您确定要进行此操作吗？',function (index) {

				myAjax('ManageServlet?methodName=ableUser&tableName=users',{account:account,state:state},function (msg) {


					if (msg=='true'){

						table.reload('userTable');
						layer.msg("状态修改成功");
					}else{
						layer.msg("状态修改失败");
					}
				});
				layer.close(index);
			});
		}else if (event==='delete'){
			layer.confirm('您确定要删除此用户吗？',function (index) {
				myAjax('ManageServlet?methodName=ableUser&tableName=users',{account:account,state:null},function (msg) {


					if (msg==='true'){
						table.reload('userTable');
						layer.msg("状态修改成功");

					}else{
						layer.msg("状态修改失败");
					}

				});
				layer.close(index);});

		}
		else if (event==='reset'){
			layer.confirm('您确定要重置该用户的密码为123456吗？',function (index) {
				myAjax('ManageServlet?methodName=resetUser&tableName=users',{account:account},function (msg) {


					if (msg==='true'){
						layer.msg("密码重置成功");

					}else{
						layer.msg("密码重置失败");
					}

				});
				layer.close(index);});

		}
	})
});