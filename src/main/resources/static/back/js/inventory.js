/**
 * @program: Mybatis
 * @ClassName
 * @description: 盘点
 * @author: linjin
 * @create:
 * @Version 1.0
 **/
layui.use(['table', 'jquery','form'], function () {
	var table = layui.table
		, $ = layui.jquery
		, form = layui.form;

	table.render({
		elem: '#demo'//指定表格
		, id:  'idTest'
		, url: 'drugController/doInventory'//指定对应servlet
		, page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
			layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
			//,curr: 5 //设定初始在第 5 页
			, groups: 1 //只显示 1 个连续页码
			, first: false //不显示首页
			, last: false //不显示尾页

		}
		,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
		,defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
			title: '提示'
			,layEvent: 'LAYTABLE_TIPS'
			,icon: 'layui-icon-tips'
		}]

		, cols: [[
			{field: 'drugcode', width: 240, title: '药品编号', sort: true, fixed: "left"}
			, {field: 'commoname', width: 250, title: '药品名称'}
			, {field: 'specification', width: 150, title: '规格', sort: true}
			, {field: 'drugunit', width: 150, title: '单位', sort: true}
			, {field: 'lotnumber', width: 200, title: '批号', sort: true}
			, {field: 'druginventorynumber', width: 130, title: '盘点前数量', sort: true}
			, {field: 'relativequantity', width: 140, title: '盘点相对数量', sort: true}
			, {field: 'finishedquantity', width: 150, title: '盘点数量(手填)', sort: true, edit: 'text'}
			, {field: 'wholesaleprice', width: 130, title: '成本单价(元)', sort: true}
			, {field: 'relativeamount', width: 150, title: '盘点相对金额(元)', sort: true}
			// , {title: '操作', fixed: 'right', width: 400, align: 'center', toolbar: '#barDemo'}
		]]
		, limit: 5
		, limits: [5, 10, 15, 20, 25]
	});

	//头工具栏事件
	table.on('toolbar(test)', function(obj){

		switch(obj.event){
			//自定义头工具栏右侧图标 - 提示
			case 'LAYTABLE_TIPS':
				layer.alert('这是工具栏右侧自定义的一个图标按钮');
				break;
		};
	});


	//表单监听提交
	form.on('submit(formDemo)', function () {
		var checkStatus = table.checkStatus('idTest')
			,data = checkStatus.data;
		layer.alert(JSON.stringify(data));

		// var username=$("#username").val();
		// table.reload('idTest',{//对应表格刷新
		// 	method:'post',
		// 	where :{
		// 		username :username  //发送数据至后台
		// 	},
		// 	page :{
		// 		curr:1 //回到第1页
		// 	}
		// });

		return false;

	});





});
