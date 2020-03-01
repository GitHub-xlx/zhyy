layui.use(['form', 'layer', 'jquery', 'table'], function () {
	var table = layui.table;
	var form = layui.form;
	var $ = layui.jquery;
	var layer = layui.layer;
	var drugDate = [];
	table.render({
		elem: '#test'
		, even: true
		, page: true
		, width: 920
		, limit: 5
		, limits: [5, 10, 15, 20, 25, 30]
		, cols: [[
			{field: 'drugcode', width: 150, title: '药品编号'}
			, {field: 'commoname', width: 180, title: '常用名称'}
			, {field: 'specification', width: 120, title: '规格'}
			, {field: 'druginventorynumber', width: 120, title: '库存数量'}
			, {field: 'drugunit', width: 120, title: '药品单位'}
			, {field: 'specialmedicine', width: 100, title: '是否特殊药品'}
			, {field: 'number', width: 60, title: '发药数量', sort: true, edit: 'text'}
			, {field: 'start', width: 60, title: '操作', align: 'center', toolbar: '#toolbar1'}
		]]
		, url: 'drugController/selectdruginventory'
		, id: "drugTable"
	});
	table.render({
		elem: '#test1'
		, even: true
		, page: true
		, width: 920
		, limit: 5
		, limits: [5, 10, 15, 20, 25, 30]
		, cols: [[
			{field: 'drugcode', width: 150, title: '药品编号'}
			, {field: 'commoname', width: 180, title: '常用名称'}
			, {field: 'specification', width: 120, title: '规格'}
			, {field: 'druginventorynumber', width: 120, title: '库存数量'}
			, {field: 'drugunit', width: 120, title: '药品单位'}
			, {field: 'specialmedicine', width: 100, title: '是否特殊药品'}
			, {field: 'number', width: 60, title: '发药数量', sort: true}
			, {field: 'start', width: 60, title: '操作', align: 'center', toolbar: '#toolbar2'}
		]]
		, data: drugDate
	});
	table.render({
		elem: '#test2'
		, even: true
		, page: true
		, width: 920
		, limit: 5
		, limits: [5, 10, 15, 20, 25, 30]
		, cols: [[
			{field: 'drugcode', width: 150, title: '药品编号'}
			, {field: 'commoname', width: 180, title: '常用名称'}
			, {field: 'specification', width: 120, title: '规格'}
			, {field: 'druginventorynumber', width: 120, title: '库存数量'}
			, {field: 'drugunit', width: 120, title: '药品单位'}
			, {field: 'specialmedicine', width: 100, title: '是否特殊药品'}
			, {field: 'number', width: 60, title: '发药数量', sort: true}
		]]
		, data: drugDate
	});
	$("#query_bt").click(function () {
		table.reload('drugTable', {
			url: "drugController/selectdruginventory"
			, where: { //设定异步数据接口的额外参数，任意设
				classcode: $('#classcode').val(),
				commoname: $('#commoname').val()
			}
			, page: {
				curr: 1 //重新从第 1 页开始
			}
		});
	});
	// 最终确认发药
	$("#confirmation").click(function () {
		table.reload("test2", {
			data: drugDate,
		});
		var drugDateObj = JSON.stringify(drugDate);
		layui.use(['table', 'form'], function () {
			var form = layui.form;
			layer.open({
				type: 1
				, title: '请确认发药的药品'
				, area: ['500px', '400px']
				, shade: 0
				, maxmin: true
				, btnAlign: 'c' //按钮居中
				, content: $('#tb')
				, btn: ['确认发药', '取消发药'] //只是为了演示
				, yes: function () {
					$.ajax({
						url: '/drugController/confirmsendmedicine',
						method: 'post',
						data: {
							'list': drugDateObj
						},
						success: function (res) {
							if (res === "success") {
								alert("发药成功！");
								window.location.reload();
							} else if (res === "failed") {
								layer.alert("发药失败！");
							} else if (res === "conflict") {
								layer.alert("药品有冲突，请重新选择！")
							}
						},
						error: function (res) {
							if (res === "failed") {
								layer.alert("发药失败！");
							} else if (res === "conflict") {
								layer.alert("药品有冲突，请重新选择！")
							}
						}
					});
					layer.closeAll();
				}
				, btn2: function () {
					layer.closeAll();
				}

				, zIndex: layer.zIndex //重点1
				// ,success: function(layero){
				// 	layer.setTop(layero); //重点2
				// }
			});
		});
	});
	table.on('tool(drug)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
		var data = obj.data //获得当前行数据
			, layEvent = obj.event; //获得 lay-event 对应的值
		if (layEvent === 'add') {
			if (parseInt(data.number) > 0 && parseInt(data.number) <= parseInt(data.druginventorynumber)) {
				if (drugDate.length === 0) {
					drugDate.push(data);
					layer.alert('添加成功！');
					table.reload("test1", {
						data: drugDate,
					});
				} else {
					var count = 0;
					for (let i = 0; i < drugDate.length; i++) {
						if (drugDate[i].drugcode === data.drugcode) {
							layer.confirm("表单中已有该药品，是否需要替换", {btn: ['确定', '取消']},
								function (dex) {
									drugDate.splice(i, 1);
									drugDate.push(data);
									layer.alert('替换成功！');
									layer.close(dex);
									table.reload("test1", {
										data: drugDate,
									});
								});
						} else {
							count++;
							if (count === drugDate.length) {
								drugDate.push(data);
								layer.alert('添加成功！');
								table.reload("test1", {
									data: drugDate,
								});
								break;
							}
						}
					}
				}
			} else if (parseInt(data.number) > parseInt(data.druginventorynumber)) {
				layer.alert('库存数量不足,请重新填写数量！');
			} else {
				layer.alert('填写数量必须大于0')
			}
		}
	});
	table.on('tool(drug1)', function (obj) {
		var data = obj.data //获得当前行数据
			, layEvent = obj.event; //获得 lay-event 对应的值

		if (layEvent === 'delete') {
			layer.confirm("你确定要删除么？", {btn: ['确定', '取消']},
				function (dex) {
					var index;
					for (let i = 0; i < drugDate.length; i++) {
						if (drugDate[i].drugcode === data.drugcode) {
							index = i;
							drugDate.splice(index, 1);
							layer.alert('删除成功！');
						}
					}
					layer.close(dex);
					table.reload("test1", {
						data: drugDate,
					});
				}
			)
		}
	});

	//将表单转为js对象数据
	function serializeObject($, array) {
		var obj = new Object();
		$.each(array, function (index, param) {
			if (!(param.name in obj)) {
				obj[param.name] = param.value;
			}
		});
		return obj;
	};

});
