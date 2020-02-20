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
		, width: 1250
		, limit: 5
		, limits: [5, 10, 15, 20, 25, 30]
		, cols: [[
			{field: 'drugcode', width: 150, title: '药品编号'}
			, {field: 'commoname', width: 250, title: '常用名称',sort: true}
			, {field: 'unitpricesold', width: 100, title: '售出单价',sort:true}
			, {field: 'quantitysold', width: 100, title: '售出数量',sort:true}
			, {field: 'totalprice', width: 100, title: '售出总价',sort:true}
			, {field: 'salestime', width: 150, title: '销售时间',sort:true}
			, {field: 'specialmedicine', width: 100, title: '是否特殊药品'}
			, {field: 'idcard', width: 100, title: '购买人员身份证'}
			, {field: 'consumername', width: 100, title: '购买人员姓名'}
			, {field: 'salesperson', width: 100, title: '营业员'}
		]]
		, url:'drugController/selectsale'
		, id: "drugTable"
	});

	var name1 = $('#drug_name').val();
	var start = $('#date1').val();
	var end = $('#date2').val();
	// <%--用于带条件查询--%>
	$("#query_bt").click(function () {
		table.reload('drugTable', {
			url: "drugController/selectsale"
			, where: { //设定异步数据接口的额外参数，任意设
				drugcode:$('#drugcode').val(),
				commoname: $('#commoname').val(),
				specialmedicine:$('#specialmedicine').val(),
				idcard:$('#idcard').val(),
				consumername:$('#consumername').val(),
				salesperson:$('#salesperson').val(),
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
