
layui.use('table', function(){
	var table = layui.table;
	var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句
	var drugDate=[];
	var drugDate1;
	table.render({
		elem: '#test'//指定表格第一张查询表
		,url:'/drugController/selectclaim'
		,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
			layout: ['limit','count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
			//,curr: 5 //设定初始在第 5 页
			,groups: 1 //只显示 1 个连续页码
			,first: false //不显示首页
			,last: false //不显示尾页

		}
		,cols: [[

			{field:'diid', width:80, title: 'ID', sort: true}
			,{field:'drugcode', width:100, title: '药品编码'}
			// ,{field:'productname', width:100, title: '药品名称', sort: true}
			,{field:'commoname', width:100, title: '常用名称', sort: true}
			,{field:'lotnumber', width:100, title: '批号', sort: true}
			,{field:'productiondate', width:100, title: '生产日期', sort: true}
			,{field:'specification', width:60, title: '规格', sort: true}
			,{field:'dosageform', width:60, title: '剂型', sort: true}
			// ,{field:'drugdepotunit', width:60, title: '药库单位', sort: true}
			,{field:'pharmacyunit', width:60, title: '药房单位', sort: true}
			// ,{field:'reductionformula', width:60, title: '换算公式', sort: true}
			,{field:'supplier', width:60, title: '供应商', sort: true}
			,{field:'antibiotic', width:60, title: '是否抗生素', sort: true}
			,{field:'specialmedicine', width:60, title: '是否特殊药品', sort: true}
			,{field:'drugstatus', width:60, title: '药品状态', sort: true}
			,{field:'price', width:60, title: '价格', sort: true}
			,{field:'druginventory', width:60, title: '药库数量', sort: true}
			,{field:'number', width:60, title: '请领数量', sort: true, edit: 'text'}
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
			,{field:'diid', width:80, title: 'ID', sort: true}
			,{field:'drugcode', width:100, title: '药品编码'}
			// ,{field:'productname', width:100, title: '药品名称', sort: true}
			,{field:'commoname', width:100, title: '常用名称', sort: true}
			,{field:'lotnumber', width:100, title: '批号', sort: true}
			,{field:'productiondate', width:100, title: '生产日期', sort: true}
			,{field:'specification', width:60, title: '规格', sort: true}
			,{field:'dosageform', width:60, title: '剂型', sort: true}
			// ,{field:'drugdepotunit', width:60, title: '药库单位', sort: true}
			,{field:'pharmacyunit', width:60, title: '药房单位', sort: true}
			// ,{field:'reductionformula', width:60, title: '换算公式', sort: true}
			,{field:'supplier', width:60, title: '供应商', sort: true}
			,{field:'antibiotic', width:60, title: '是否抗生素', sort: true}
			,{field:'specialmedicine', width:60, title: '是否特殊药品', sort: true}
			,{field:'drugstatus', width:60, title: '药品状态', sort: true}
			,{field:'price', width:60, title: '价格', sort: true}
			,{field:'druginventory', width:60, title: '药库数量', sort: true}
			,{field:'number', width:60, title: '请领数量', sort: true, edit: 'text'}
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
			// ,fixed:"left"
			{field:'diid', width:80, title: 'ID', sort: true}
			,{field:'drugcode', width:100, title: '药品编码'}
			// ,{field:'productname', width:100, title: '药品名称', sort: true}
			,{field:'commoname', width:100, title: '常用名称', sort: true}
			,{field:'lotnumber', width:100, title: '批号', sort: true}
			,{field:'productiondate', width:100, title: '生产日期', sort: true}
			,{field:'specification', width:60, title: '规格', sort: true}
			,{field:'dosageform', width:60, title: '剂型', sort: true}
			// ,{field:'drugdepotunit', width:60, title: '药库单位', sort: true}
			,{field:'pharmacyunit', width:60, title: '药房单位', sort: true}
			// ,{field:'reductionformula', width:60, title: '换算公式', sort: true}
			,{field:'supplier', width:60, title: '供应商', sort: true}
			,{field:'antibiotic', width:60, title: '是否抗生素', sort: true}
			,{field:'specialmedicine', width:60, title: '是否特殊药品', sort: true}
			,{field:'drugstatus', width:60, title: '药品状态', sort: true}
			,{field:'price', width:60, title: '价格', sort: true}
			,{field:'druginventory', width:60, title: '药库数量', sort: true}
			,{field:'number', width:60, title: '请领数量', sort: true, edit: 'text'}
		]]
		,limit: 10
		,limits:[10,20,30,40,50]
		,data:drugDate
	});
	// 用于带条件查询
	$("#query_bt").click(function () {
		table.reload('condition',{
			url:"/drugController/selectclaim"
			,where: { //设定异步数据接口的额外参数，任意设
				commoname: $('#drugn1').val(),
				pincode: $('#drugn2').val()
			}
			,page: {
				curr: 1 //重新从第 1 页开始
			}
		});
	});
	// 最终确认请领
	$("#confirmation").click(function () {
		drugDate=layui.table.cache['test1']
		table.reload("test2", {
			data: drugDate
		});
		layui.use(['table', 'form'], function() {
			var form = layui.form;
			layer.open({
				type: 1
				, title: '请确认请领药品'
				, area: ['390px', '260px']
				, shade: 0
				, maxmin: true
				, btnAlign: 'c' //按钮居中
				, content: $('#tb')
				, btn: ['确认请领', '暂不请领'] //只是为了演示
				, yes: function () {
					$.ajax({
						url:'/vacationController/startProcess',
						method:'post',
						// contentType : "application/json;charsetset=UTF-8",//必须
						data:{
							'gsonList':JSON.stringify(drugDate),
							// 'list':drugDate,
							'processkey':'drugclaim'
						},
						success:function(res){
							if(res){
								layer.alert('请领成功，等待审核');
							}
							else{
								layer.alert('请领失败，请重新尝试');
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

		if(layEvent === 'info'){

			// layer.confirm('确定重置'+data.account+'密码？',function (index)
			// {
			// 	layer.close(index);
			// 	$.ajax({
			// 		url:"UserServlet?methodName=updatCode" , // 请求路径
			// 		type:"POST" , //请求方式
			// 		data:{"uid":data.userid},
			// 		success:function (d) {
			// 			if (d==='1'){
			// 				layer.alert("密码重置成功");
			// 				table.reload();
			// 			} else{
			// 				layer.alert("密码重置失败，请重新尝试");
			// 			}
			// 		},//响应成功后的回调函数
			// 		error:function () {
			// 			alert("服务器繁忙")
			// 		},//表示如果请求响应出现错误，会执行的回调函数
			// 		dataType:"text"//设置接受到的响应数据的格式
			// 	});
			// });
		}
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
	// table.on('edit(drug)', function(obj){
	// 	var value = obj.value //得到修改后的值
	// 		,data = obj.data //得到所在行所有键值
	// 		,field = obj.field; //得到字段
	// 	layer.msg('[ID: '+ data.id +'] ' + field + ' 字段更改为：'+ value);
	// });

});


