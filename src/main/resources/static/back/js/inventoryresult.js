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
		elem: '#test'
		, even: true
		, page: true
		, width: 700
		, limit: 5
		, limits: [5, 10, 15, 20, 25, 30]
		, cols: [[
			{field: 'drugcode', width: 200, title: '药品编号'}
			, {field: 'commoname', width: 250, title: '药品名称'}
			, {field: 'inventoryresults', width: 100, title: '盘点结果'}
			, {field: 'inventorytime', width: 150, title: '盘点日期'}
		]]
		, url: 'drugController/selectinventorylist'
		, id: "drugTable"
	});
	$("#query_bt").click(function () {
		table.reload('drugTable', {
			url: "drugController/selectinventorylist"
			, where: { //设定异步数据接口的额外参数，任意设
				drugcode: $('#drugcode').val(),
				inventoryresults : $("#inventoryresults option:checked").text(),
				commoname :$('#commoname').val(),
				start: $('#date1').val(),
				end: $('#date2').val()
			}
			, page: {
				curr: 1 //重新从第 1 页开始
			}
		});
	});
});




