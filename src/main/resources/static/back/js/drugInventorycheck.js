/**
 * @Description  查看药房出入库明细
 * @author xlx
 * @Date 上午 11:09 2020/2/28 0028
 * @Param
 * @return
 **/
layui.use('table', function(){
	var table = layui.table;
	var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句
	var form = layui.form;
	var layer = layui.layer;
	var laydate = layui.laydate;
	table.render({
		elem: '#test'//指定表格第一张查询表
		,url:'/drugController/selectInventorycheck'
		,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
			layout: ['limit','count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
			//,curr: 5 //设定初始在第 5 页
			,groups: 1 //只显示 1 个连续页码
			,first: false //不显示首页
			,last: false //不显示尾页

		}
		,cols: [[

			{field:'ditid', width:80, title: 'ID', sort: true}
			,{field:'commoname', width:100, title: '药品名称'}
			,{field:'drugcode', width:100, title: '药品编码'}
			,{field:'druginventorynumber', width:100, title: '药品库存数量', sort: true}
			,{field:'drugminimums', width:100, title: '药品最低限量', sort: true}
			,{field:'drugunit', width:60, title: '药品单位', sort: true}
			,{field:'lotnumber', width:60, title: '批号', sort: true}
			,{field:'specialmedicine', width:60, title: '是否特殊药品', sort: true}
			,{field:'productiondate', width:60, title: '生产日期', sort: true}
			,{field:'drugstatus', width:60, title: '药品状态', sort: true}
			,{field:'pharmacynumber', width:60, title: '药房编号', sort: true}

		]]
		,limit: 10
		,limits:[10,20,30,40,50]
		,id:'condition'
	});
	// 用于带条件查询
	$("#query_bt").click(function () {
		table.reload('condition',{
			url:"/drugController/selectInventorycheck"
			,where: { //设定异步数据接口的额外参数，任意设
				commoname: $('#drugn1').val(),
				specialmedicine: $('#drugn2').val(),
			}
			,page: {
				curr: 1 //重新从第 1 页开始
			}
		});
	});

});


