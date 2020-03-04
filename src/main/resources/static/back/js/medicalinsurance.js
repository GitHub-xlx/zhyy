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
		, width: 580
		, limit: 5
		, limits: [5, 10, 15, 20, 25, 30]
		, cols: [[
			{field: 'drugcode', width: 200, title: '药品编号'}
			, {field: 'commoname', width: 180, title: '常用名称'}
			, {field: 'healthinsurance', width: 100, title: '是否为医保药品'}
			, {field: 'operated', width: 100, title: '操作', align: 'center', toolbar: '#toolbar'}
		]]
		, url: 'drugController/selectdrugstore'
		, id: "drugTable"
	});
	$("#query_bt").click(function () {
		table.reload('drugTable', {
			url: "drugController/selectdrugstore"
			, where: { //设定异步数据接口的额外参数，任意设
				drugcode: $('#drugcode').val(),
				commoname: $('#commoname').val()
			}
			, page: {
				curr: 1 //重新从第 1 页开始
			}
		});
	});

	table.on('tool(table-user)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
			var data = obj.data //获得当前行数据
				, layEvent = obj.event; //获得 lay-event 对应的值
			if (layEvent === 'able') {
				var status;
				var ope;
				if (data.healthinsurance === "是") {
					status = "否";
					ope="禁用";
					alert(1)
				} else {
					status = "是";
					ope="启用";
					alert(2)
				}
				layer.confirm("您确定要" + ope + "该药品吗？", {btn: ['确定', '取消']},
					function () {
						$.ajax({
							url: '/drugController/updatehealthinsurance',
							method: 'post',
							data: {
								status: status,
								drugcode: data.drugcode,
								commoname: data.commoname
							},
							success: function (res) {
								if (res === "success") {
									alert("更改状态成功！");
									window.location.reload();
								} else if (res === "failed") {
									layer.alert("更改状态失败！");
								}
							},
							error: function (res) {
								layer.alert("服务器繁忙");
							}
						});
						layer.closeAll();
					});
			}
		}
	);
});
