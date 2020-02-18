layui.use(['form', 'layer', 'jquery', 'table', 'laydate'], function () {
	var table = layui.table;
	var form = layui.form;
	var $ = layui.jquery;
	var layer = layui.layer;
	var laydate = layui.laydate;
	//日期
	laydate.render({
		elem: '#date'
	});
	laydate.render({
		elem: '#date1'
	});
	laydate.render({
		elem: '#date2'
	});

	table.render({
		elem: 'test'
		, even: true
		, page: true
		, width: 700
		, marginLeft:350
		, limit: 5
		, limits: [5, 10, 15, 20, 25, 30]
		, cols: [[
			{field: 'drugcode', width: 150, title: '药品编号'}
			, {field: 'currentprice', width: 150, title: '当前价格', sort: true}
			, {field: 'previousprice', width: 150, title: '上次价格',sort: true}
			, {field: 'priceadjustmentdate', width: 150, title: '上次调价日期'}
			, {field: 'operator', width: 100, title: '操作员'}
		]]
		, url:'drugController/selectprice'
		, id: "drugTable"
	});

	var name1 = $('#drug_name').val();
	var start = $('#date1').val();
	var end = $('#date2').val();
	// <%--用于带条件查询--%>
	$("#query_bt").click(function () {
		table.reload('drugTable', {
			url: "drugController/selectprice"
			, where: { //设定异步数据接口的额外参数，任意设
				currentprice:$('#currentprice').val(),
				previousprice: $('#previousprice').val(),
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
	//将表单转为js对象数据
	function serializeObject($, array){
		var obj=new Object();
		$.each(array, function(index,param){
			if(!(param.name in obj)){
				obj[param.name]=param.value;
			}
		});
		return obj;
	};

});
