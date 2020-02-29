/**
 * @program: Mybatis
 * @ClassName userTable
 * @description: 用户表管理（禁用，启用，重置密码）
 * @author: lj
 * @create: 2019-12-28 16:10
 * @Version 1.0
 **/
layui.use(['table', 'jquery', 'form'], function () {
	var table = layui.table
		, $ = layui.jquery
		, form = layui.form;

	table.render({
		elem: '#demo'//指定表格
		, id :'idTest'
		, url: 'userController/manageUsers'//指定对应servlet
		, limit: 5//要传向后台的每页显示条数
		//,page: true //开启分页
		, page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
			layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip']//自定义分页布局
			, limits: [5, 10]
			// ,first: false //不显示首页
			// ,last: false //不显示尾页
		}
		, cols: [[
			{field: 'account', width: 80, title: '账号', sort: true, fixed: "left"}
			, {field: 'username', width: 100, title: '名称'}
			, {field: 'phone', width: 150, title: '电话', sort: true}
			, {field: 'sex', width: 100, title: '性别', sort: true}
			, {field: 'age', width: 100, title: '年龄', sort: true}
			, {field: 'department', width: 200, title: '部门', sort: true}
			, {field: 'position', width: 200, title: '职位', sort: true}
			, {field: 'rolecode', width: 100, title: '角色编码', sort: true}
			, {field: 'state', width: 150, title: '状态', sort: true}
			, {title: '操作', fixed: 'right', width: 400, align: 'center', toolbar: '#barDemo'}
		 ]]

	});
	table.on('tool(test)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
		var data = obj.data //获得当前行数据
			, layEvent = obj.event; //获得 lay-event 对应的值

		if (layEvent === 'open') {
			// layer.msg('启用操作');
			$.ajax({
				type: "POST", //请求方式
				url: 'userController/enable', // 请求路径
				data: {account: data.account},
				success: function (msg) {
					if (msg === '1') {
						layer.alert("启用成功");
						table.reload('idTest');
					} else {
						layer.alert("启用失败，请重新尝试");
					}
				},//响应成功后的回调函数
				error: function () {
					alert("服务器繁忙")
				},//表示如果请求响应出现错误，会执行的回调函数
				dataType: "text"//设置接受到的响应数据的格式
			});

		} else if (layEvent === 'close') {
			// layer.msg('禁用操作');
			$.ajax({
				type: "POST", //请求方式
				url: 'userController/disable', // 请求路径
				data: {account: data.account},
				success: function (msg) {
					if (msg === '1') {
						layer.alert("禁用成功");
						table.reload('idTest');
					} else {
						layer.alert("禁用失败，请重新尝试");
					}
				},//响应成功后的回调函数
				error: function () {
					alert("服务器繁忙")
				},//表示如果请求响应出现错误，会执行的回调函数
				dataType: "text"//设置接受到的响应数据的格式
			});

		} else if (layEvent === 'info') {
			// layer.msg('重置密码操作');
			layer.confirm('确定重置' + data.account + '密码？', function (index) {
				layer.close(index);
				$.ajax({
					type: "POST", //请求方式
					url: 'userController/resetPassword', // 请求路径
					data: {uaccount: data.account},
					success: function (msg) {
						if (msg === '1') {
							layer.alert("密码重置成功");
							// table.reload();
						} else {
							layer.alert("密码重置失败，请重新尝试");
						}
					},//响应成功后的回调函数
					error: function () {
						alert("服务器繁忙")
					},//表示如果请求响应出现错误，会执行的回调函数
					dataType: "text"//设置接受到的响应数据的格式
				});
			});
		}


		//药品调价 注意!!! 大于成本价 小于政府指导价？修改的价格可以是带小数点？修改的是哪个药品的价格？
		if (layEvent === 'adjustment') {
			layer.prompt(function (val, index) {
				// layer.msg('得到了'+val);
				layer.close(index);
				$.ajax({
					type: "POST", //请求方式
					url: 'userController/adjustmentPrice', // 请求路径
					data: {price: val},
					success: function (msg) {
						if (msg === '1') {
							layer.alert("调价成功");
							table.reload();
						} else {
							layer.alert("调价失败，请重新尝试");
						}
					},//响应成功后的回调函数
					error: function () {
						alert("服务器繁忙")
					},//表示如果请求响应出现错误，会执行的回调函数
					dataType: "text"//设置接受到的响应数据的格式
				});


			});
		}

		//药品停用
		if (layEvent === 'disable') {
			layer.msg('停用操作');
			$.ajax({
				type: "POST", //请求方式
				url: 'userController/drugDiscontinuation', // 请求路径
				data: {account: data.account},
				success: function (msg) {
					if (msg === '1') {
						layer.alert("停用成功");
						 table.reload();
					} else {
						layer.alert("停用失败，请重新尝试");
					}
				},//响应成功后的回调函数
				error: function () {
					alert("服务器繁忙")
				},//表示如果请求响应出现错误，会执行的回调函数
				dataType: "text"//设置接受到的响应数据的格式
			});

		}


	});

	//新增人员
	$("#distribution").click(function () {

		layer.open({
			type: 2, //设置类型 默认为0， 1：页面层  2：iframe层
			title: "新增人员界面",
			content: "jump/back/addStaff",
			skin: 'layui-layer-molv',
			area: ['700px', '500px'],
			offset: 'auto',
			icon: 1,//只对type=0的有效
			shadeClose: true
			, anim: 4
			, maxmin: true //是否显示最大化和最小化的按钮 对type=1 type=2有效
			, success: function () {

			}
		});

	});

});
