/**
 * @program: Mybatis
 * @ClassName userTable
 * @description: 用户表管理（禁用，启用，重置密码）
 * @author: xlx
 * @create: 2019-12-28 16:10
 * @Version 1.0
 **/
layui.use('table', function(){
	var table = layui.table;
	var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句
	var drugDate;
	table.render({
		elem: '#test'//指定表格
		,url:'/drugController/selectclaim'
		,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
			layout: ['limit','count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
			//,curr: 5 //设定初始在第 5 页
			,groups: 1 //只显示 1 个连续页码
			,first: false //不显示首页
			,last: false //不显示尾页

		}
		,cols: [[
			{type:'checkbox'}
			,{field:'diid', width:80, title: 'ID', sort: true,fixed:"left"}
			,{field:'drugcode', width:100, title: '药品编码'}
			,{field:'productname', width:100, title: '药品名称', sort: true}
			,{field:'commonname', width:100, title: '常用名称', sort: true}
			,{field:'specification', width:60, title: '规格', sort: true}
			,{field:'dosageform', width:60, title: '剂型', sort: true}
			,{field:'drugdepotunit', width:60, title: '药库单位', sort: true}
			,{field:'pharmacyunit', width:60, title: '药房单位', sort: true}
			,{field:'reductionformula', width:60, title: '换算公式', sort: true}
			,{field:'supplier', width:60, title: '供应商', sort: true}
			,{field:'antibiotic', width:60, title: '是否抗生素', sort: true}
			,{field:'specialmedicine', width:60, title: '是否特殊药品', sort: true}
			,{field:'price', width:60, title: '价格', sort: true}
			,{field:'number', width:60, title: '数量', sort: true, edit: 'text'}
			,{field:'start', width:60, title: '操作',align:'center', toolbar: '#toolbar2'}
		]]
		,limit: 10
		,limits:[10,20,30,40,50]
		,id:'condition'
	});
	table.render({
		elem: '#test1'//指定表格
		,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
			layout: ['limit','count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
			//,curr: 5 //设定初始在第 5 页
			,groups: 1 //只显示 1 个连续页码
			,first: false //不显示首页
			,last: false //不显示尾页

		}
		,cols: [[
			{field:'diid', width:80, title: 'ID', sort: true,fixed:"left"}
			,{field:'drugcode', width:100, title: '药品编码'}
			,{field:'commonname', width:100, title: '常用名称', sort: true}
			,{field:'specification', width:60, title: '规格', sort: true}
			,{field:'dosageform', width:60, title: '剂型', sort: true}
			,{field:'drugdepotunit', width:60, title: '药库单位', sort: true}
			,{field:'pharmacyunit', width:60, title: '药房单位', sort: true}
			,{field:'reductionformula', width:60, title: '换算公式', sort: true}
			,{field:'supplier', width:60, title: '供应商', sort: true}
			,{field:'antibiotic', width:60, title: '是否抗生素', sort: true}
			,{field:'specialmedicine', width:60, title: '是否特殊药品', sort: true}
			,{field:'price', width:60, title: '价格', sort: true}
			,{field:'number', width:60, title: '数量', sort: true, edit: 'text'}
		]]
		,limit: 10
		,limits:[10,20,30,40,50]
		,data:drugDate
	});
	// 用于带条件查询
	$("#query_bt").click(function () {
		table.reload('userTable',{
			url:"/drugController/selectclaim"
			,where: { //设定异步数据接口的额外参数，任意设
				commonname: $('#drugn1').val(),
				pincode: $('#drugn2').val()
			}
			,page: {
				curr: 1 //重新从第 1 页开始
			}
		});
	});
	table.on('tool(drug)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
		var data = obj.data //获得当前行数据
			,layEvent = obj.event; //获得 lay-event 对应的值
		var username = data.userid;
		var time = data.atime

		if(layEvent === 'info'){

			layer.open({
				type: 1
				,title: '请确认请领药品'
				,area: ['390px', '260px']
				,shade: 0
				,maxmin: true
				,btnAlign: 'c' //按钮居中
				,content: $('#test1')
				,btn: ['确认请领', '暂不请领'] //只是为了演示
				,yes: function(){
					alert('确认请领')
				}
				,btn2: function(){
					layer.closeAll();
				}

				,zIndex: layer.zIndex //重点1
				// ,success: function(layero){
				// 	layer.setTop(layero); //重点2
				// }
			});
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

	});
	table.on('edit(drug)', function(obj){
		var value = obj.value //得到修改后的值
			,data = obj.data //得到所在行所有键值
			,field = obj.field; //得到字段
		layer.msg('[ID: '+ data.id +'] ' + field + ' 字段更改为：'+ value);
	});

});


