/**
 * @Description  查看药房滞销药品
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
	var drugDate=[];
	table.render({
		elem: '#test'//指定表格第一张查询表
		,url:'/drugController/doDrugInventoryUnsalable'
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
			,{field:'shelflife', width:60, title: '保质期', sort: true}
			,{field:'drugstatus', width:60, title: '药品状态', sort: true}
			,{field:'pharmacynumber', width:60, title: '药房编号', sort: true}
			,{field:'number', width:60, title: '退库数量', sort: true, edit: 'text'}
			,{field:'start', width:60, title: '操作',align:'center', toolbar: '#toolbar1'}
		]]
		,limit: 10
		,limits:[10,20,30,40,50]
		,id:'condition'
	});
	table.render({
		elem: '#test1'//指定表格第二张请领表
		,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
			layout: ['limit','count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
			//,curr: 5 //设定初始在第 5 页
			,groups: 1 //只显示 1 个连续页码
			,first: false //不显示首页
			,last: false //不显示尾页

		}
		,cols: [[
			// ,fixed:"left"
			{type:'checkbox'}
			,{field:'ditid', width:80, title: 'ID', sort: true}
			,{field:'commoname', width:100, title: '药品名称'}
			,{field:'drugcode', width:100, title: '药品编码'}
			,{field:'druginventorynumber', width:100, title: '药品库存数量', sort: true}
			,{field:'drugminimums', width:100, title: '药品最低限量', sort: true}
			,{field:'drugunit', width:60, title: '药品单位', sort: true}
			,{field:'lotnumber', width:60, title: '批号', sort: true}
			,{field:'specialmedicine', width:60, title: '是否特殊药品', sort: true}
			,{field:'productiondate', width:60, title: '生产日期', sort: true}
			,{field:'shelflife', width:60, title: '保质期', sort: true}
			,{field:'drugstatus', width:60, title: '药品状态', sort: true}
			,{field:'pharmacynumber', width:60, title: '药房编号', sort: true}
			,{field:'number', width:60, title: '退库数量', sort: true, edit: 'text'}
			,{field:'start', width:60, title: '操作',align:'center', toolbar: '#toolbar2'}
		]]
		,limit: 10
		,limits:[10,20,30,40,50]
		,data:drugDate
	});
	table.render({
		elem: '#test2'//指定表格第三张确认表
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
			,{field:'number', width:60, title: '退库数量', sort: true, edit: 'text'}
		]]
		,limit: 10
		,limits:[10,20,30,40,50]
		,data:drugDate
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

	// 最终确认
	$("#confirmation").click(function () {
		drugDate=layui.table.cache['test1']
		table.reload("test2", {
			data: drugDate
		});
		layui.use(['table', 'form'], function() {
			var form = layui.form;
			layer.open({
				type: 1
				, title: '请确认退库药品'
				, area: ['390px', '260px']
				, shade: 0
				, maxmin: true
				, btnAlign: 'c' //按钮居中
				, content: $('#tb')
				, btn: ['确认退库', '暂不退库'] //
				, yes: function () {
					$.ajax({
						url:'/vacationController/startWithdrawal',
						method:'post',
						// contentType : "application/json;charsetset=UTF-8",//必须
						data:{
							'gsonList':JSON.stringify(drugDate),
							// 'list':drugDate,
							'processkey':'pharmacywithdrawal'
						},
						success:function(res){
							if(res){
								layer.alert('提交成功，等待审核');
							}
							else{
								layer.alert('提交失败，请重新尝试');
							}
						},
						error:function (data) {
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
	table.on('tool(drug)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
		var data = obj.data //获得当前行数据
			,layEvent = obj.event; //获得 lay-event 对应的值
		if(layEvent === 'add'){
			drugDate.push(data);
			table.reload("test1", {
				data: drugDate,
			});

		}

	});
	table.on('tool(drug1)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"

		var data = obj.data //获得当前行数据
			,layEvent = obj.event; //获得 lay-event 对应的值

		if(layEvent === 'delete') {
			layer.confirm("你确定要删除么？",{btn:['确定','取消']},
				function(dex){
					var checkStatus = table.checkStatus('test1');
					// list.push(checkStatus.data);
					// alert(list.length);
					// list.push(data);
					var index;
					if (checkStatus.data.length===0){
						layer.msg('请先勾选数据', {
							time: 1500, //1500ms后自动关闭
						});
					} else {
						for (var i = 0; i <checkStatus.data.length ; i++) {
							index = JSON.stringify(drugDate).indexOf(JSON.stringify(checkStatus.data[i]));
							if (index > -1) {
								drugDate.splice(index, 1);
							}
						}
						table.reload("test1", {
							data: drugDate,
						});
					}
					layer.close(dex);

					// var oldData = table.cache["test2"];
					// oldData.splice(obj.tr.data('index'),1);
					// table.reload('test2',{data : oldData});
				}
			)
		}
	});
});


