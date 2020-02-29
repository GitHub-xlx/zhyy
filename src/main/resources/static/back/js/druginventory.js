/**
 * @program: Mybatis
 * @ClassName userTable
 * @description: 药房药品库存
 * @author: linjin
 * @create: 2019-12-28 16:10
 * @Version 1.0
 **/
layui.use(['table', 'jquery','form'], function () {
	var table = layui.table
		, $ = layui.jquery
		, form = layui.form;

	table.render({
		elem: '#demo'//指定表格
		, url: 'drugController/doDrugInventory'//指定对应servlet
		, page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
			layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
			//,curr: 5 //设定初始在第 5 页
			, groups: 1 //只显示 1 个连续页码
			, first: false //不显示首页
			, last: false //不显示尾页

		}
		, cols: [[
			{field: 'drugcode', width: 240, title: '药品编号', sort: true, fixed: "left"}
			, {field: 'commoname', width: 250, title: '药品名称'}
			, {field: 'druginventorynumber', width: 150, title: '药品库存数量', sort: true}
			, {field: 'drugminimums', width: 150, title: '药品最低限量', sort: true}
			, {field: 'drugunit', width: 130, title: '药品单位', sort: true}
			, {field: 'lotnumber', width: 200, title: '批号', sort: true}
			, {field: 'specialmedicine', width: 150, title: '是否特殊药品', sort: true}
			, {field: 'productiondate', width: 200, title: '生产日期', sort: true}
			, {field: 'drugstatus', width: 130, title: '药品状态', sort: true}
			, {field: 'pharmacynumber', width: 130, title: '药房编号', sort: true}
			// , {title: '操作', fixed: 'right', width: 400, align: 'center', toolbar: '#barDemo'}
		]]
		, limit: 5
		, limits: [5, 10, 15, 20, 25]
	});
	// table.on('tool(test)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
	// 	var data = obj.data //获得当前行数据
	// 		, layEvent = obj.event; //获得 lay-event 对应的值
	//
	// 	if (layEvent === 'open') {
	// 		// layer.msg('启用操作');
	// 		$.ajax({
	// 			type: "POST", //请求方式
	// 			url: 'userController/enable', // 请求路径
	// 			data: {account: data.account},
	// 			success: function (msg) {
	// 				if (msg === '1') {
	// 					layer.alert("启用成功");
	// 					table.reload();
	// 				} else {
	// 					layer.alert("启用失败，请重新尝试");
	// 				}
	// 			},//响应成功后的回调函数
	// 			error: function () {
	// 				alert("服务器繁忙")
	// 			},//表示如果请求响应出现错误，会执行的回调函数
	// 			dataType: "text"//设置接受到的响应数据的格式
	// 		});
	//
	// 	} else if (layEvent === 'close') {
	// 		// layer.msg('禁用操作');
	// 		$.ajax({
	// 			type: "POST", //请求方式
	// 			url: 'userController/disable', // 请求路径
	// 			data: {account: data.account},
	// 			success: function (msg) {
	// 				if (msg === '1') {
	// 					layer.alert("禁用成功");
	// 					table.reload();
	// 				} else {
	// 					layer.alert("禁用失败，请重新尝试");
	// 				}
	// 			},//响应成功后的回调函数
	// 			error: function () {
	// 				alert("服务器繁忙")
	// 			},//表示如果请求响应出现错误，会执行的回调函数
	// 			dataType: "text"//设置接受到的响应数据的格式
	// 		});
	//
	// 	} else if (layEvent === 'info') {
	// 		// layer.msg('重置密码操作');
	// 		layer.confirm('确定重置' + data.account + '密码？', function (index) {
	// 			layer.close(index);
	// 			$.ajax({
	// 				type: "POST", //请求方式
	// 				url: 'userController/resetPassword', // 请求路径
	// 				data: {uaccount: data.account},
	// 				success: function (msg) {
	// 					if (msg === '1') {
	// 						layer.alert("密码重置成功");
	// 						table.reload();
	// 					} else {
	// 						layer.alert("密码重置失败，请重新尝试");
	// 					}
	// 				},//响应成功后的回调函数
	// 				error: function () {
	// 					alert("服务器繁忙")
	// 				},//表示如果请求响应出现错误，会执行的回调函数
	// 				dataType: "text"//设置接受到的响应数据的格式
	// 			});
	//  		});
	//  	}
	//
	// })

});
