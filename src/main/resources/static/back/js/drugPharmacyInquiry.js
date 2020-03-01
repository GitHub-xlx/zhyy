/**
 * @Description  查看药房出入库明细
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
		,url:'/drugController/selectPharmacyd'
		,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
			layout: ['limit','count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
			//,curr: 5 //设定初始在第 5 页
			,groups: 1 //只显示 1 个连续页码
			,first: false //不显示首页
			,last: false //不显示尾页

		}
		,cols: [[

			{field:'pdeesid', width:80, title: 'ID', sort: true}
			,{field:'drugcode', width:100, title: '药品编码'}
			,{field:'time', width:100, title: '生产时间', sort: true}
			,{field:'number', width:100, title: '数量', sort: true}
			,{field:'outbound', width:60, title: '出入库', sort: true}
			,{field:'lotnumber', width:60, title: '批号', sort: true}
			,{field:'specialmedicine', width:60, title: '是否特殊药品', sort: true}
			,{field:'outboundtype', width:60, title: '出入库类型', sort: true}
			,{field:'asker', width:60, title: '领药人', sort: true}
			,{field:'auditor', width:60, title: '审核人', sort: true}
			,{field:'asktime', width:60, title: '领药时间', sort: true}
			,{field:'reviewtime', width:60, title: '审核时间', sort: true}
			,{field:'operatingtime', width:60, title: '操作时间', sort: true}
		]]
		,limit: 10
		,limits:[10,20,30,40,50]
		,id:'condition'
	});
	// 用于带条件查询
	$("#query_bt").click(function () {
		table.reload('condition',{
			url:"/drugController/selectPharmacyd"
			,where: { //设定异步数据接口的额外参数，任意设
				drugcode: $('#drugn1').val(),
				lotnumber: $('#drugn2').val(),
				asker: $('#drugn3').val(),
				outbound: $('#drugn4').val(),
				start: $('#date1').val(),
				end: $('#date2').val()
			}
			,page: {
				curr: 1 //重新从第 1 页开始
			}
		});
	});

});


