/**
 * @Description  查看药房报损明细
 * @author xlx
 * @Date 上午 11:09 2020/2/28 0028
 * @Param
 * @return
 **/
layui.use(['form', 'layer', 'jquery', 'table', 'laydate'], function () {
	var table = layui.table;
	var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句
	var form = layui.form;
	var layer = layui.layer;
	var laydate = layui.laydate;
	laydate.render({
		elem: '#date1'
	});
	laydate.render({
		elem: '#date2'
	});
	table.render({
		elem: '#test'//指定表格第一张查询表
		,url:'/drugController/selectBreakdownOfDrugs'
		,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
			layout: ['limit','count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
			//,curr: 5 //设定初始在第 5 页
			,groups: 1 //只显示 1 个连续页码
			,first: false //不显示首页
			,last: false //不显示尾页

		}
		,cols: [[

			{field:'bodid', width:80, title: 'ID', sort: true}
			,{field:'commoname', width:100, title: '药品名称', sort: true}
			,{field:'drugcode', width:100, title: '药品编码'}
			,{field:'damagedtype', width:100, title: '报损类型', sort: true}
			,{field:'lossreporter', width:100, title: '报损员', sort: true}
			,{field:'lossreport', width:60, title: '报损说明', sort: true}
			,{field:'losstime', width:60, title: '报损时间', sort: true}
			,{field:'losscount', width:60, title: '报损数量', sort: true}
			,{field:'lossamount', width:60, title: '报损总金额', sort: true}
			,{field:'lotnumber', width:60, title: '批号', sort: true}
			,{field:'drugstate', width:60, title: '状态', sort: true}
			,{field:'provepath', width:60, title: '证据路径', sort: true}
			,{field:'pharmacycode', width:60, title: '药房编号', sort: true}
			,{field:'auditor', width:60, title: '审核人', sort: true}
			,{field:'reviewtime', width:60, title: '审核时间', sort: true}
		]]
		,limit: 10
		,limits:[10,20,30,40,50]
		,id:'condition'
	});
	// 用于带条件查询
	$("#query_bt").click(function () {
		table.reload('condition',{
			url:"/drugController/selectBreakdownOfDrugs"
			,where: { //设定异步数据接口的额外参数，任意设
				commoname: $('#drugn1').val(),
				damagedtype: $('#drugn2').val(),
				start: $('#date1').val(),
				end: $('#date2').val()
			}
			,page: {
				curr: 1 //重新从第 1 页开始
			}
		});
	});

});


