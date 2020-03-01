layui.use(['form', 'layer', 'jquery', 'table', 'laydate'], function () {
	var table = layui.table;
	var form = layui.form;
	var $ = layui.jquery;
	var layer = layui.layer;
	var laydate = layui.laydate;
	//日期
	laydate.render({
		elem: '#date1'
	});
	laydate.render({
		elem: '#date2'
	});

	table.render({
		elem: '#test'
		, even: true
		, page: true
		, width: 1250
		, limit: 5
		, limits: [5, 10, 15, 20, 25, 30]
		, cols: [[
			{field: 'lid', width: 40, title: 'id'}
			, {field: 'operationdate', width: 200, title: '操作时间',sort: true}
			, {field: 'account', width: 80, title: '账号',sort:true}
			, {field: 'username', width: 100, title: '用户名',sort:true}
			, {field: 'ip', width: 120, title: 'ip',sort:true}
			, {field: 'url', width: 200, title: 'url',sort:true}
			, {field: 'duration', width: 90, title: '执行时间'}
			, {field: 'method', width: 420, title: '执行方法'}
		]]
		, url:'logController/queryAll'
		, id: "logTable"
	});

	// <%--用于带条件查询--%>
	$("#query_bt").click(function () {
		table.reload('logTable', {
			url: "logController/queryAll"
			, where: { //设定异步数据接口的额外参数，任意设
				username:$('#username').val(),
				url: $('#url').val(),
				start: $('#date1').val(),
				end: $('#date2').val()
			}
			, page: {
				curr: 1 //重新从第 1 页开始
			}
		});
		// Layui表格,刷新当前分页数据
		// $(".layui-laypage-btn").click()
	});
	// //将表单转为js对象数据
	// function serializeObject($, array){
	// 	var obj=new Object();
	// 	$.each(array, function(index,param){
	// 		if(!(param.name in obj)){
	// 			obj[param.name]=param.value;
	// 		}
	// 	});
	// 	return obj;
	// };

});
