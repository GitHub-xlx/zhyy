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
		, width: 1000
		, limit: 5
		, limits: [5, 10, 15, 20, 25, 30]
		, cols: [[
			{field: 'drugcode', width: 250, title: '药品编号'}
			, {field: 'productname', width: 250, title: '商品名称', sort: true}
			, {field: 'commoname', width: 250, title: '常用名称',sort: true}
			, {field: 'currentprice', width: 250, title: '当前价格',sort: true}
			, {field: 'previousprice', width: 250, title: '上次价格',sort: true}
			, {field: 'governmentpricing', width: 250, title: '政府指导价',sort: true}
			, {field: 'priceadjustmentdate', width: 150, title: '上次调价日期'}
			, {field: 'operator', width: 100, title: '操作员'}
			, {title: '操作', fixed: 'right', width: 400, align: 'center', toolbar: '#barDemo'}

		]]
		, url:'drugController/selectprice'
		, id: "drugTable"
	});

	table.on('tool(test)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
		var data = obj.data //获得当前行数据
			, layEvent = obj.event; //获得 lay-event 对应的值

		//药品调价 注意!!! 大于成本价 小于政府指导价？修改的价格可以是带小数点？修改的是哪个药品的价格？
		if (layEvent === 'adjustment') {
			layer.prompt(function (val, index) {
				layer.msg('得到了'+val+"当前价格："+data.currentprice+",政府指导价:"+data.governmentpricing);
				layer.close(index);

				if(parseFloat(val)>data.governmentpricing){
					alert("当前调价价格不能大于政府指导价")
				}else{
					$.ajax({
						type: "POST", //请求方式
						url: 'userController/adjustmentPrice', // 请求路径
						data: {price:val,beforePrice:data.currentprice,drugCode:data.drugcode},
						success: function (msg) {
							if (msg === '1') {
								layer.alert("调价成功");
								table.reload('drugTable');
							} else if(msg === '3'){
								layer.alert("当前调价价格不能小于成本价");
							} else {
								layer.alert("调价失败，请重新尝试");
							}
						},//响应成功后的回调函数
						error: function () {
							alert("服务器繁忙")
						},//表示如果请求响应出现错误，会执行的回调函数
						dataType: "text"//设置接受到的响应数据的格式
					});
				}

			});
		}

	});

	var name1 = $('#drug_name').val();
	var start = $('#date1').val();
	var end = $('#date2').val();
	// <%--用于带条件查询--%>
	$("#query_bt").click(function () {
		table.reload('drugTable', {
			url: "drugController/selectprice"
			, where: { //设定异步数据接口的额外参数，任意设
				drugcode:$('#drugcode').val(),
				commoname: $('#commoname').val(),
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
