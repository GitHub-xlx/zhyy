//保存验证码
var code = "";
var login_flag = false;

layui.use(['form','layer','jquery'], function () {
	// 操作对象
	var form = layui.form;
	var $ = layui.jquery;
	var layer  = layui.layer;

	var offsetX=0;

//一、定义一个获取DOM元素的方法
var select = function(selector){
		return  document.querySelector(selector);
	},

			box = select(".drag"),//容器
			bg = select(".bg"),//背景
			text = select(".text"),//文字
			btn = select(".btn"),//滑块
			success = false,//是否通过验证的标志
			distance = box.offsetWidth - btn.offsetWidth;//滑动成功的宽度（距离）


//二、给滑块注册鼠标按下事件
btn.onmousedown = function mousedown(e){

	//1.鼠标按下之前必须清除掉后面设置的过渡属性
	btn.style.transition = "";
	bg.style.transition ="";

	//说明：clientX 事件属性会返回当事件被触发时，鼠标指针向对于浏览器页面(或客户区)的水平坐标。

	//2.当滑块位于初始位置时，得到鼠标按下时的水平位置
	var e = e || window.event;
	var downX = e.clientX;

	//三、给文档注册鼠标移动事件
	document.onmousemove = function mousemove(e){

		var e = e || window.event;
		//1.获取鼠标移动后的水平位置
		var moveX = e.clientX;

		//2.得到鼠标水平位置的偏移量（鼠标移动时的位置 - 鼠标按下时的位置）
		offsetX = moveX - downX;
		distance = box.offsetWidth - btn.offsetWidth;
		//3.在这里判断一下：鼠标水平移动的距离 与 滑动成功的距离 之间的关系
		if( offsetX > distance){
			var account = $('#account').val();
			var password = $('#password').val();

			if (account.length>0&&password.length>0){
				offsetX = distance;//如果滑过了终点，就将它停留在终点位置
			} else {
				layer.msg("请输入账号密码再进行验证");
				document.onmousemove = null;
			}

		}else if( offsetX < 0){
			offsetX = 0;//如果滑到了起点的左侧，就将它重置为起点位置
		}

		//4.根据鼠标移动的距离来动态设置滑块的偏移量和背景颜色的宽度
		btn.style.left = offsetX + "px";
		bg.style.width = offsetX + "px";

		//如果鼠标的水平移动距离 = 滑动成功的宽度
		if( offsetX == distance){

			//1.设置滑动成功后的样式
			text.innerHTML = "验证通过";
			text.style.color = "#fff";
			btn.innerHTML = "&radic;";
			btn.style.color = "green";
			bg.style.backgroundColor = "lightgreen";

			//2.设置滑动成功后的状态
			success = true;
			//成功后，清除掉鼠标按下事件和移动事件（因为移动时并不会涉及到鼠标松开事件）
			btn.onmousedown = null;
			document.onmousemove = null;

			//3.成功解锁后的回调函数
			setTimeout(function(){

				login_flag = true;
			},100);
		}
	};


	//四、给文档注册鼠标松开事件
	document.onmouseup = function(e){

		//如果鼠标松开时，滑到了终点，则验证通过
		if(success){
			return;
		}else{
			//反之，则将滑块复位（设置了1s的属性过渡效果）
			btn.style.left = 0;
			bg.style.width = 0;
			btn.style.transition = "left 1s ease";
			bg.style.transition = "width 1s ease";
		}
		//只要鼠标松开了，说明此时不需要拖动滑块了，那么就清除鼠标移动和松开事件。
		document.onmousemove = null;
		document.onmouseup = null;
	};


	function init(){
		text.innerHTML = "请拖动滑块解锁";
		text.style.color = "#040404";
		btn.innerHTML = "&gt;&gt;";
		btn.style.color = "#666";
		bg.style.backgroundColor = "#75CDF9";

		success=false;
		login_flag=false;
		offsetX=0;
		distance=0;

		btn.style.left = 0;
		bg.style.width = 0;
		btn.style.transition = "left 1s ease";
		bg.style.transition = "width 1s ease";
	}

	form.on('submit(login_B)', function () {



			if (login_flag) {

				var account = $('#account').val();
				var password = $('#password').val();

				$.ajax({
					type: "POST",
					url: "userController/doLogin",

					//发送的数据（同时也将数据发送出去）
					data: {account: account, password: password},
					success: function (data) {

						if (data.code == 200) {
							layer.msg('登录成功');
							// location.href ="jump/back/index";
							location.href = "userController/showMain";
						}
						if (data.code == 404) {
							layer.msg('密码错误');
							init();
							btn.onmousedown = mousedown;
						}
						if (data.code == 405) {
							layer.msg('账户不存在，请联系管理员');
							init();
							btn.onmousedown = mousedown;
						}
						if (data.code == 403) {
							layer.msg('账号密码不能为空');
							init();
							btn.onmousedown = mousedown;
						}
					},
					error: function (msg) {
						alert("服务器正忙。。。。" + msg);
						init();
						btn.onmousedown = mousedown;
					}
				});
				return false;
			} else {
				layer.msg('请滑动滑块解锁验证码');
				return false;
			}

	});
};
});
