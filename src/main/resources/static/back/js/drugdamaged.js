/**
 * @Description  药房药品报损
 * @author xlx
 * @Date 上午 11:09 2020/2/28 0028
 * @Param
 * @return
 **/
layui.use(['form', 'layer','upload','jquery', 'table'], function(){
	var table = layui.table;
	var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句
	var form = layui.form;
	var layer = layui.layer;
	var laydate = layui.laydate;
	var upload = layui.upload;
	var drugDate=[];
	var img;
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
			,{field:'price', width:60, title: '药品价格', sort: true}
			,{field:'losscount', width:60, title: '报损数量', sort: true, edit: 'text'}
			,{field:'lossamount', width:60, title: '报损总金额', sort: true }
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
			,{field:'drugstatus', width:60, title: '药品状态', sort: true}
			,{field:'pharmacynumber', width:60, title: '药房编号', sort: true}
			,{field:'price', width:60, title: '药品价格', sort: true}
			,{field:'losscount', width:60, title: '报损数量', sort: true, edit: 'text'}
			,{field:'lossamount', width:60, title: '报损总金额', sort: true }
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

		},parseData: function(res){ //res 即为原始返回的数据
				layui.each(res,function(i,d){
					d.lossamount =d.price * d.losscount;//sum 是自定义的属性
				});
				return res;
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
			,{field:'price', width:60, title: '药品价格', sort: true}
			,{field:'losscount', width:60, title: '报损数量', sort: true}
			,{field:'lossamount', width:60, title: '报损总金额', sort: true
				// ,templet:function(obj){
				// 	var editData=obj.data;
				// 	var Money = editData.losscount *editData.price;
				// 	obj.update({
				// 		lossamount: Money
				//
				// 	});}
			}
		]]
		,limit: 10
		,limits:[10,20,30,40,50]
		,data: layui.table.cache.test1
		// ,done:function(res){
		// 	layui.each(res.data,function(index,d){
		// 		var checkStatus = table.checkStatus(index);
		// 		alert(checkStatus);
		// 		checkStatus.update({
		// 			lossamount:Number(d.losscount) * Number(d.price)
		// 		});
		// 	})
		// 	//修改 结算后余额 统计单元格文本
		// 	this.elem.next().find(’.layui-table-total td[data-field=“onwance”] .layui-table-cell’).text(onwanceTotal)
		// }

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
		drugDate=layui.table.cache['test1'];
		table.reload("test2", {
			data: drugDate
		});
		layui.use(['table', 'form'], function() {
			var form = layui.form;
			layer.open({
				type: 1
				, title: '请确认报损药品'
				, area: ['390px', '260px']
				, shade: 0
				, maxmin: true
				, btnAlign: 'c' //按钮居中
				, content: $('#tb')
				, btn: ['确认报损', '暂不报损'] //
				, yes: function () {
					if ($('#text1').val()===''||img===''){
						layer.tips(
							'报损原因或图片不能为空',
							time=1500
						);
					} else{
						$.ajax({
							url:'/vacationController/startDamaged',
							method:'post',
							// contentType : "application/json;charsetset=UTF-8",//必须
							data:{
								'gsonList':JSON.stringify(drugDate),
								// 'list':drugDate,
								'provepath':img,
								'reason':$('#text1').val(),
								'damagedtype':$('#drugn3').val()
							},
							success:function(res){
								if(res){
									layer.alert('提交成功，等待审核');
									img='';
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
	table.on('edit(drug)', function (obj) {
		var editData = obj.data;
		var A = $.trim(editData.losscount);
		if (A == "") {
			$(this).val(0);
			layer.msg("数量输入的格式有误，只能输入数字", { icon: 0, title: "提示", skin: "layui-layer-molv", time: 1500 });
			return;
		}
		else if (!isNaN(editData.losscount)&& editData.losscount < 1 && editData.losscount != 0)
		{
			$(this).val(0);

			layer.msg("数量输入的格式有误，只能输入数字", { icon: 0, title: "提示", skin: "layui-layer-molv", time: 1500 });
			return;
		}
		else {

			var Money = editData.losscount *editData.price;

			obj.update({
				lossamount: Money
			});

		}

	});
	table.on('edit(drug1)', function (obj) {
		var editData = obj.data;
		var A = $.trim(editData.losscount);
		if (A == "") {
			$(this).val(0);
			layer.msg("数量输入的格式有误，只能输入数字", { icon: 0, title: "提示", skin: "layui-layer-molv", time: 1500 });
			return;
		}
		else if (!isNaN(editData.losscount)&& editData.losscount < 1 && editData.losscount != 0)
		{
			$(this).val(0);

			layer.msg("数量输入的格式有误，只能输入数字", { icon: 0, title: "提示", skin: "layui-layer-molv", time: 1500 });
			return;
		}
		else {

			var Money = editData.losscount *editData.price;

			obj.update({
				lossamount: Money
			});

		}

	});
	var uploadInst = upload.render({
		elem: '#uploadbtn'
		, url: '/statisticsController/projectPictureUpload'
		,method:'post'
		,accept:'images'
		,acceptMime:'image/*'
		,field:'projectImg'
		,choose: function (obj) {
			//预读本地文件示例，不支持ie8
			obj.preview(function (index, file, result) {
				//图片预览
				$('#demo1').attr('src', result); //图片链接（base64）

			});
		}
		,done: function (msg) {
			//如果上传失败
			// var res =JSON.parse(msg);
			var res = msg;
			// layer.msg(res);
			// layer.msg(res.code);
			// alert(res);
			// alert(res.code);
			// console.log(msg);
			if (res.code==205) {
				return layer.msg('上传项目图片失败',{icon: 5});
			}else if (res.code ==0) {
				layer.msg('上传图片成功');
				// 上传成功,获得图片地址
				$('#demo1').attr('src',"/statisticsController/getImage?id="+res.data); //图片链接（base64）
				img=res.data;
			}

		}
		,error: function () {
			alert("上传失败");
			// //演示失败状态，并实现重传
			// var demoText = $('#demoText');
			// demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
			// demoText.find('.demo-reload').on('click', function () {
			// 	uploadInst.upload();
			// });
		}
	});

});


