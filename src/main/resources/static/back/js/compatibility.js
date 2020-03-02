layui.use(['form', 'layer', 'jquery', 'table'], function () {
	var table = layui.table;
	var form = layui.form;
	var $ = layui.jquery;
	var layer = layui.layer;
	table.render({
		elem: '#test'
		, even: true
		, page: true
		, width: 650
		, limit: 5
		, limits: [5, 10, 15, 20, 25, 30]
		, cols: [[
			{field: 'drugcodeA', width: 200, title: '药品编号A'}
			, {field: 'drugcodeB', width: 200, title: '药品编号B'}
			, {field: 'contraindications', width: 250, title: '说明'}
		]]
		, url: 'drugController/selectcompatibility'
		, id: "drugTable"
	});
	$("#query_bt").click(function () {
		table.reload('drugTable', {
			url: "drugController/selectcompatibility"
			, where: { //设定异步数据接口的额外参数，任意设
				drugcode: $('#drugcode').val(),
			}
			, page: {
				curr: 1 //重新从第 1 页开始
			}
		});
	});
	// 新增配伍禁忌
	$("#add_bt").click(function () {
		var that = this;
		layer.open({
			type: 2
			, title: '新增配伍禁忌'
			, area: ['500px', '600px']
			, shade: 0
			, maxmin: true
			, offset: [ //为了演示，随机坐标
				($(window).height() * 0.01)
				, ($(window).width() * 0.35)
			]
			, content: 'jump/back/addcompatibility'
			, yes: function () {
				$(that).click();
			}
			, btn2: function () {
				layer.closeAll();
			}
			, zIndex: layer.zIndex //重点1
			, success: function (layero) {
				layer.setTop(layero); //重点2
			}, end: function () {
				window.location.reload();
			}
		});
	});
});




