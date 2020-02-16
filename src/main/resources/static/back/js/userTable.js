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
	table.render({
		elem: '#test'//指定表格
		,url:'/user/find'//指定对应servlet
		,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
			layout: ['limit','count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
			//,curr: 5 //设定初始在第 5 页
			,groups: 1 //只显示 1 个连续页码
			,first: false //不显示首页
			,last: false //不显示尾页

		}
		,cols: [[
			{field:'userid', width:80, title: 'ID', sort: true,fixed:"left"}
			,{field:'account', width:100, title: '账户'}
			,{field:'phone', width:150, title: '电话', sort: true}
			,{field:'scroe', width:100, title: '积分', sort: true}
			,{field:'usertime', width:260, title: '注册时间', sort: true}
			,{field:'phone', width:80, title: '状态', sort: true
				,templet:function (d) {
					return d.start==1? '启用':'禁用';
				}
			}
			,{field:'start', width:350, title: '操作',align:'center', toolbar: '#toolbar2'}
		]]
		,limit: 5
		,limits:[5,10,15,20,25]
	});
	table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
		var data = obj.data //获得当前行数据
			,layEvent = obj.event; //获得 lay-event 对应的值
		var username = data.userid;
		var time = data.atime

		if(layEvent === 'info'){
			layer.confirm('确定重置'+data.account+'密码？',function (index)
			{
				layer.close(index);
				$.ajax({
					url:"UserServlet?methodName=updatCode" , // 请求路径
					type:"POST" , //请求方式
					data:{"uid":data.userid},
					success:function (d) {
						if (d==='1'){
							layer.alert("密码重置成功");
							table.reload();
						} else{
							layer.alert("密码重置失败，请重新尝试");
						}
					},//响应成功后的回调函数
					error:function () {
						alert("服务器繁忙")
					},//表示如果请求响应出现错误，会执行的回调函数
					dataType:"text"//设置接受到的响应数据的格式
				});
			});
		}
		if(layEvent === 'open'){
			layer.confirm('是否启用'+data.account+'该账户',function (index)
			{
				layer.close(index);
				$.ajax({
					url:"UserServlet?methodName=updatStart" , // 请求路径
					type:"POST" , //请求方式
					data:{"uid":data.userid,"start":1},
					success:function (d) {
						if (d==='1'){
							layer.alert("状态修改成功");
							table.reload("test");
						} else{
							layer.alert("状态修改失败，请重新尝试");
						}
					},//响应成功后的回调函数
					error:function () {
						alert("服务器繁忙")
					},//表示如果请求响应出现错误，会执行的回调函数
					dataType:"text"//设置接受到的响应数据的格式
				});
				// window.location.href="JumpServlet?methodName=godoctorinfo&username="+username;

			});
		}
		if(layEvent === 'close'){
			layer.confirm('是否禁用'+data.account+'该账户',function (index)
			{
				layer.close(index);
				$.ajax({
					url:"UserServlet?methodName=updatStart" , // 请求路径
					type:"POST" , //请求方式
					data:{"uid":data.userid,"start":0},
					success:function (d) {
						if (d==='1'){
							layer.alert("状态修改成功");
							table.reload();
						} else{
							layer.alert("状态修改失败，请重新尝试");
						}
					},//响应成功后的回调函数
					error:function () {
						alert("服务器繁忙")
					},//表示如果请求响应出现错误，会执行的回调函数
					dataType:"text"//设置接受到的响应数据的格式
				});
			});
		}
	});

});


