
//保存验证码
var code = "";
var login_flag = false;

// login
layui.use(['form','layer','jquery'], function () {

	// 操作对象
	var form = layui.form;
	var $ = layui.jquery;
	var layer  = layui.layer;
	// 页面出现的时候就加载
	$(drawPic());
	//验证码验证
	// $('#login_code').blur(function() {
	// 	if($('#login_code').val() == code){
	// 		layer.msg('验证通过');
	// 		login_flag = true;
	// 	}else {
	// 		layer.msg('验证码错误');
	// 		login_flag = false;
	// 		drawPic();
	// 	}
	// });

	form.on('submit(login_B)', function () {

		// if (login_flag ) {
		if (true ) {
			if (true) {

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
							location.href = "jump/back/index";
						}
						if (data.code == 404) {
							layer.msg('密码错误');
						}
						if (data.code == 405) {
							layer.msg('账户不存在，请联系管理员');
						}
						if (data.code == 403) {
							layer.msg('账号密码不能为空');
						}
					},
					error: function (msg) {
						alert("服务器正忙。。。。" + msg);
					}
				});
				return false;
			} else {
				return false;
			}
		}
	});

});

// 验证码
//生成随机数
function randomNum(min,max){
	return Math.floor(Math.random()*(max-min)+min);
}
//生成随机颜色RGB分量
function randomColor(min,max){
	var _r = randomNum(min,max);
	var _g = randomNum(min,max);
	var _b = randomNum(min,max);
	return "rgb("+_r+","+_g+","+_b+")";
}
//先阻止画布默认点击发生的行为再执行drawPic()方法
document.getElementById("canvas").onclick = function(e){
	e.preventDefault();
	drawPic();
};
function drawPic(){
	//获取到元素canvas
	var $canvas = document.getElementById("canvas");
	var code_str = "QWERTYPADFGHJLBNMqwertyipadfghjbnm123456789";//设置随机数库
	var codeStr = "";//最后得到的字符串
	var rd_num = 4;//4个随机数字
	var code_width = $canvas.width;
	var code_height = $canvas.height;
	var ctx = $canvas.getContext("2d");//获取 context 对象
	ctx.textBaseline = "bottom";//文字上下对齐方式--底部对齐
	ctx.fillStyle = randomColor(180,240);//填充画布颜色
	ctx.fillRect(0,0,code_width,code_height);//填充矩形--画画
	for(var i=0; i<rd_num; i++){
		var x = (code_width-10)/rd_num*i+10;
		var y = randomNum(code_height/2,code_height);
		var deg = randomNum(-45,45);
		var txt = code_str[randomNum(0,code_str.length)];
		codeStr += txt;//获取一个随机数
		ctx.fillStyle = randomColor(10,100);//填充随机颜色
		ctx.font = randomNum(16,35)+"px SimHei";//设置随机数大小，字体为SimHei
		ctx.translate(x,y);//将当前xy坐标作为原始坐标
		ctx.rotate(deg*Math.PI/180);//旋转随机角度
		ctx.fillText(txt, 0,0);//绘制填色的文本
		ctx.rotate(-deg*Math.PI/180);
		ctx.translate(-x,-y);
	}
	for(var i=0; i<rd_num; i++){
		//定义笔触颜色
		ctx.strokeStyle = randomColor(90,180);
		ctx.beginPath();
		//随机划线--4条路径
		ctx.moveTo(randomNum(0,code_width), randomNum(0,code_height));
		ctx.lineTo(randomNum(0,code_width), randomNum(0,code_height));
		ctx.stroke();
	}
	for(var i=0; i<rd_num*10; i++){
		ctx.fillStyle = randomColor(0,255);
		ctx.beginPath();
		//随机画原，填充颜色
		ctx.arc(randomNum(0,code_width),randomNum(0,code_height), 1, 0, 2*Math.PI);
		ctx.fill();
	}

	code = codeStr;
	return codeStr;//返回随机数字符串
}

