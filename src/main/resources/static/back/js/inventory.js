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
		, form = layui.form
		, $2 = layui.jquery;

	table.render({
		elem: '#demo2'//指定表格
		, id:  'idTest2'
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
			  {type:'checkbox'}
			, {field: 'drugcode', width: 240, title: '药品编号', sort: true}
			, {field: 'commoname', width: 250, title: '药品名称'}
			, {field: 'specification', width: 150, title: '规格'}
			, {field: 'drugunit', width: 150, title: '单位'}
			, {field: 'lotnumber', width: 200, title: '批号', sort: true}
			, {field: 'druginventorynumber', width: 130, title: '盘点前数量', sort: true}
			, {field: 'relativequantity', width: 140, title: '盘点相对数量'}
			, {field: 'finishedquantity', width: 150, title: '盘点后数量(手填)', sort: true, edit: 'text'}
			, {field: 'wholesaleprice', width: 130, title: '成本单价(元)', sort: true}
			, {field: 'relativeamount', width: 150, title: '盘点相对金额(元)'}
			// , {title: '操作', fixed: 'right', width: 400, align: 'center', toolbar: '#barDemo'}
		]]
		, limit: 5
		, limits: [5, 10, 15, 20, 25]
	});
    // 用于带条件查询
	$2("#query_bt").click(function () {
		table.reload('idTest2', {
			url: "drugController/inventoryQuery"
			, where: { //设定异步数据接口的额外参数，任意设
				drugcode: $2('#drugcode').val(),
				commoname: $2('#commoname').val()
			}
			, page: {
				curr: 1 //重新从第 1 页开始
			}
		});
		// Layui表格,刷新当前分页数据
		// $(".layui-laypage-btn").click()
	});
	//头工具栏事件
	table.on('toolbar(test2)', function(obj){

		switch(obj.event){
			//自定义头工具栏右侧图标 - 提示
			case 'LAYTABLE_TIPS':
				layer.alert('这是工具栏右侧自定义的一个图标按钮');
				break;
		};
	});


	var $ = layui.$, active = {
		getCheckData: function(){ //获取选中数据
			var checkStatus = table.checkStatus('idTest2')
				,data = checkStatus.data;
			// layer.alert(JSON.stringify(data));

			$.ajax({
				type: "POST", //请求方式
				url: 'drugController/adjustmentQuantity', // 请求路径
				async:true,
				data: {data:JSON.stringify(data)},
				success: function (msg) {
					if (msg === '1') {
						layer.alert("盘点完成,库存调整成功");
						// table.reload('idTest2');

						table.render({
							elem: '#demo'//指定表格
							, id:  'idTest'
							, url: 'drugController/afterInventory'//指定对应servlet
							, page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
								layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
								//,curr: 5 //设定初始在第 5 页
								, groups: 1 //只显示 1 个连续页码
								, first: false //不显示首页
								, last: false //不显示尾页

							}
							, cols: [[
								 {field: 'drugcode', width: 240, title: '药品编号', sort: true}
								// , {field: 'commoname', width: 250, title: '药品名称'}
								, {field: 'specification', width: 150, title: '规格'}
								, {field: 'drugunit', width: 150, title: '单位'}
								, {field: 'lotnumber', width: 200, title: '批号', sort: true}
								, {field: 'druginventorynumber', width: 130, title: '盘点前数量', sort: true}
								, {field: 'relativequantity', width: 140, title: '盘点相对数量',templet: '#Top1'}
								, {field: 'finishedquantity', width: 150, title: '盘点后数量(手填)', sort: true, edit: 'text'}
								, {field: 'wholesaleprice', width: 130, title: '成本单价(元)', sort: true}
								, {field: 'relativeamount', width: 150, title: '盘点相对金额(元)',templet: '#Top2'}
								// , {title: '操作', fixed: 'right', width: 400, align: 'center', toolbar: '#barDemo'}
							]]
							, limit: 5
							, limits: [5, 10, 15, 20, 25]
						});



					} else {
						layer.alert("盘点失败，请重新尝试");
					}
				},//响应成功后的回调函数
				error: function () {
					alert("服务器繁忙")
				},//表示如果请求响应出现错误，会执行的回调函数
				dataType: "json"//设置接受到的响应数据的格式
			});


		}

	};
	$('.demoTable .layui-btn').on('click', function(){
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
	});


});
