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
var accountMsg;
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
		,where: { //设定异步数据接口的额外参数，任意设
			rolecode: $('#rolecode').val(),
			username: $('#rolename').val(),
			department:$('#department').val()
		}
	});


	// 用于带条件查询
	$("#query_bt").click(function () {
		table.reload('idTest',{
			url:"userController/userManage"
			,where: { //设定异步数据接口的额外参数，任意设
				rolecode: $('#rolecode').val(),
				username: $('#rolename').val(),
				department:$('#department').val()
			}
			,page: {
				curr: 1 //重新从第 1 页开始
			}
		});
		// Layui表格,刷新当前分页数据
		// $(".layui-laypage-btn").click()
	});

	table.on('tool(test)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
		var data = obj.data //获得当前行数据
			, layEvent = obj.event; //获得 lay-event 对应的值
		accountMsg=data.account;
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
				data: {account: data.account,},
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

		}else if (layEvent === 'update') {
			// layer.msg('修改操作');

			layer.open({
				type: 1, //设置类型 默认为0， 1：页面层  2：iframe层
				title: "用户信息修改",
				content: $("#updateStaffDiv"),
				skin: 'layui-layer-molv',
				area: ['700px', '500px'],
				offset: 'auto',
				icon: 1,//只对type=0的有效
				shadeClose: true
				, anim:1
				, maxmin: true //是否显示最大化和最小化的按钮 对type=1 type=2有效
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



	//确认修改
	$("#updateSave").click(function () {

		// alert("点击修改了");
		var username = $('#username').val();
		var phone = $('#phone').val();
		var sex = $('input[name="sex"]:checked').val();
		var age = $('#age').val();
		var role = $('#role').val();
		// alert("账号："+accountMsg+"名称:"+username + ",电话:" + phone + ",性别:" + sex
		// 	+ ",年龄:" + age + ",角色编号:" + role + ",职位编号：" + title);

	$.ajax({
		type: "POST", //请求方式
		url: 'userController/updateStaff', // 请求路径
		data: {account:accountMsg,username:username,phone:phone,sex:sex,age:age,role:role,titles:title},
		success: function (msg) {
			if (msg === '1') {
				layer.alert("修改成功");
				table.reload('idTest');
			} else
			{
				layer.alert("修改失败，请重新尝试");
			}
		},//响应成功后的回调函数
		error: function () {
			alert("服务器繁忙")
		},//表示如果请求响应出现错误，会执行的回调函数
		dataType: "text"//设置接受到的响应数据的格式
	});


	});


});
