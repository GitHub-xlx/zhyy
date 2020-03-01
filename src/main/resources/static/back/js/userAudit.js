/**
 * @Description  用户审核的js
 * @author xlx
 * @Date 上午 6:21 2020/2/26 0026
 * @Param
 * @return
 **/
layui.use('table', function(){
	var table = layui.table;
	var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句
	var drugDate=[];
	var opinionText='';
	table.render({
		elem: '#test'//正在申请流程
		,url:'/vacationController/myVac'
		,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
			layout: ['limit','count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
			//,curr: 5 //设定初始在第 5 页
			,groups: 1 //只显示 1 个连续页码
			,first: false //不显示首页
			,last: false //不显示尾页

		}
		,cols: [[

			{field:'id', width:80, title: '实例ID', sort: true}
			,{field:'name', width:80, title: '流程名称', sort: true}
			,{field:'applyUser', width:100, title: '申请人'}
			,{field:'list', width:80, title: '药品集合', sort: true}
			,{field:'applyTime', width:100, title: '申请时间', sort: true}
			,{field:'applyStatus', width:100, title: '申请状态', sort: true}
			,{field:'start', width:60, title: '操作',align:'center', toolbar: '#toolbar1'}
		]]
		,limit: 10
		,limits:[10,20,30,40,50]
		,id:'condition'
	});
	table.render({
		elem: '#test1'//历史流程
		,url:'/vacationController/myVacRecord'
		,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
			layout: ['limit','count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
			//,curr: 5 //设定初始在第 5 页
			,groups: 1 //只显示 1 个连续页码
			,first: false //不显示首页
			,last: false //不显示尾页 ,hide:false
		}
		,cols: [[
			{field:'id', width:80, title: '实例ID', sort: true}
			,{field:'reason', width:80, title: '流程名称', sort: true}
			,{field:'applyUser', width:100, title: '申请人'}
			,{field:'list', width:80, title: '药品集合', sort: true}
			,{field:'applyTime', width:120, title: '申请时间', sort: true}
			,{field:'applyStatus', width:100, title: '申请状态', sort: true}
			,{field:'auditor', width:100, title: '审核人', sort: true}
			,{field:'result', width:100, title: '审核结果', sort: true}
			,{field:'auditTime', width:120, title: '审核时间', sort: true}
			,{field:'dispenser', width:100, title: '发药人', sort: true}
			,{field:'durgResult', width:100, title: '发药结果', sort: true}
			,{field:'medicineTime', width:120, title: '发药时间', sort: true}
			,{field:'name', width:80, title: '流程名称', sort: true}
		]]
		,limit: 10
		,limits:[10,20,30,40,50]
	});
	table.render({
		elem: '#test2'//待审核流程
		,url:'/vacationController/myAudit'
		,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
			layout: ['limit','count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
			//,curr: 5 //设定初始在第 5 页
			,groups: 1 //只显示 1 个连续页码
			,first: false //不显示首页
			,last: false //不显示尾页
		}
		,cols: [[
			{field:'id', width:80, title: '实例ID', sort: true}
			,{field:'name', width:80, title: '流程名称', sort: true}
			,{field:'applyUser', width:100, title: '申请人',templet: '<div>{{d.vac.applyUser}}</div>'}
			,{field:'list', width:80, title: '药品集合', sort: true,templet: '<div>{{d.vac.list}}</div>'}
			,{field:'applyTime', width:100, title: '申请时间', sort: true,templet: '<div>{{d.vac.applyTime}}</div>'}
			,{field:'applyStatus', width:100, title: '申请状态', sort: true,templet: '<div>{{d.vac.applyStatus}}</div>'}
			,{field:'auditor', width:100, title: '审核人', sort: true,templet: '<div>{{d.vac.auditor}}</div>'}
			,{field:'result', width:100, title: '审核结果', sort: true,templet: '<div>{{d.vac.result}}</div>'}
			,{field:'auditTime', width:100, title: '审核时间', sort: true,templet: '<div>{{d.vac.auditTime}}</div>'}
			,{field:'start', width:60, title: '操作',align:'center', templet: '#titleTpl'}
		]]
		,limit: 10
		,limits:[10,20,30,40,50]
	});
	table.render({
		elem: '#test3'//审核记录
		,url:'/vacationController/myAuditRecord'
		,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
			layout: ['limit','count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
			//,curr: 5 //设定初始在第 5 页
			,groups: 1 //只显示 1 个连续页码
			,first: false //不显示首页
			,last: false //不显示尾页
		}
		,cols: [[
			{field:'id', width:80, title: '实例ID', sort: true}
			,{field:'reason', width:80, title: '流程名称', sort: true}
			,{field:'applyUser', width:100, title: '申请人'}
			,{field:'list', width:80, title: '药品集合', sort: true}
			,{field:'applyTime', width:120, title: '申请时间', sort: true}
			,{field:'applyStatus', width:100, title: '申请状态', sort: true}
			,{field:'auditor', width:100, title: '审核人', sort: true}
			,{field:'result', width:100, title: '审核结果', sort: true}
			,{field:'auditTime', width:120, title: '审核时间', sort: true}
			,{field:'dispenser', width:100, title: '发药人', sort: true}
			,{field:'durgResult', width:100, title: '发药结果', sort: true}
			,{field:'medicineTime', width:120, title: '发药时间', sort: true}

		]]
		,limit: 10
		,limits:[10,20,30,40,50]
	});
	table.render({
		elem: '#test4'//弹出层表格，用于显示具体的表格
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
			,{field:'number', width:60, title: '请领数量', sort: true}
		]]
		,limit: 10
		,limits:[10,20,30,40,50]
		,data:drugDate
	});
	table.render({
		elem: '#test5'//弹出层表格，用于显示具体的表格
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
		]]
		,limit: 10
		,limits:[10,20,30,40,50]
		,data:drugDate
	});
	table.on('tool(drug)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
		var data = obj.data //获得当前行数据
			,layEvent = obj.event; //获得 lay-event 对应的值
		alert(JSON.stringify(data));
		if(layEvent === 'schedule'){
			var src1=document.getElementById('imgProcess');
			src1.src = "/vacationController/getFlowChart?id="+data.id;
			layer.open({
				type: 1
				, title: '流程审核图'
				, area: ['800px', '600px']
				, shade: 0
				, maxmin: true
				, btnAlign: 'c' //按钮居中
				, content: $('#img')
				, btn: ['关闭']
				, yes: function () {
					layer.closeAll();
				}
			});
		}

	});
	table.on('tool(drug2)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
		var data = obj.data //获得当前行数据
			,layEvent = obj.event; //获得 lay-event 对应的值
		if(layEvent === 'modify'){
			drugDate=data.vac.list;
			alert(drugDate);
			table.reload("test5", {
				data: drugDate,
			});
			layui.use(['table', 'form'], function() {
				var form = layui.form;
				layer.open({
					type: 1
					, title: '修改请领数量'
					, area: ['800px', '600px']
					, shade: 0
					, maxmin: true
					, btnAlign: 'c' //按钮居中
					, content: $('#tb1')
					, btn: ['确认', '取消']
					, yes: function () {
						obj.update({
							list:layui.table.cache['test5']
						});
					}
					, btn2: function () {
						layer.closeAll();
					}
				});
			});

		}
		if(layEvent === 'details'){
			drugDate=data.vac.list;
			alert(drugDate);
			table.reload("test4", {
				data: drugDate,
			});
			layui.use(['table', 'form'], function() {
				var form = layui.form;
				layer.open({
					type: 1
					, title: '审核信息'
					, area: ['800px', '600px']
					, shade: 0
					, maxmin: true
					, btnAlign: 'c' //按钮居中
					, content: $('#tb')
					, btn: ['同意', '不同意'] //都是完成任务
					, yes: function () {
						opinionText=$("#text1").val();
						if (opinionText===''){
							layer.tips(
								'审核意见不能为空',
								time=1500
								);
						}else {
							var data1={
								'id':data.id
								,'vac.list':data.vac.list
								,'vac.applyUser':data.vac.applyUser
								,'vac.reason':data.vac.reason
								,'vac.nowResult':'同意'
								,'vac.result':opinionText
							};
							review(data1);
							$("#text1").innerText='';
							layer.closeAll();
						}
					}
					, btn2: function () {
						opinionText=$("#text1").val();
						if (opinionText===''){
							layer.tips(
								'审核意见不能为空',
								time=1500
							);
						}else {
							var data1={
								'id':data.id
								,'vac.nowResult':'不同意'
								,'vac.result':opinionText
							};
							review(data1);
							$("#text1").innerText='';
							layer.closeAll();
						}
					}
					// ,success: function(layero){
					// 	layer.setTop(layero); //重点2
					// }
				});
			});
		}
	});
	//审核确认的ajax
	function review(data1){
		$.ajax({
			url:'/vacationController/passAudit',
			method:'post',
			data:data1,
			success:function(res){
				if(res.val()){
					layer.msg('审核成功', {
						time: 1500, //1500ms后自动关闭
					});
				}
				else
					layer.msg('审核失败，请重新尝试', {
						time: 1500, //1500ms后自动关闭
					});
			},
			error:function (data) {
			}
		});
	}

});


