layui.use(['form', 'layer', 'jquery', 'table'], function () {
	var table = layui.table;
	var form = layui.form;
	var $ = layui.jquery;
	var layer = layui.layer;

	$(function () {
		$('#choosedrugcodeA').empty();
		$('#choosedrugcodeB').empty();
		$('#choosedrugcodeA').append('<option value="请选择药品编号A">请选择药品编号A</option>');
		//ajax
		$.ajax({
			type: "POST",
			url: "drugController/queryDrugcode",
			dataType: "text",
			data: {},
			success: function (msg) {
				var list = JSON.parse(msg);
				for (var i = 0; i < list.length; i++) {
					$('#choosedrugcodeA').append('<option value="' + list[i].drugcode + '">' + list[i].drugcode + '</option>');
				}
				form.render();
			},
			error: function () {
				layer.msg('服务器繁忙');
			}
		});
	});
	form.on('select(choosedrugcodeA)', function () {
		$('#choosedrugcodeB').empty();
		var choosedrugcodeA = $("#choosedrugcodeA option:checked").text();
		if ($("#choosedrugcodeA option:checked").text() === "请选择药品编号A") {
			$('#choosedrugcodeB').empty();
			form.render();
		} else {
			$.ajax({
				type: "POST",
				url: "drugController/queryDrugcodeIf",
				dataType: "text",
				//发送的数据（同时也将数据发送出去）
				data: {choosedrugcodeA: choosedrugcodeA},
				success: function (msg) {
					var list = JSON.parse(msg);
					$("#choosedrugcodeB").empty();
					$("#choosedrugcodeB").append('<option value="">请选择药品编号B</option>');
					for (var i = 0; i < list.length; i++) {
						$('#choosedrugcodeB').append('<option value="' + list[i].drugcode + '">' + list[i].drugcode + '</option>');
					}
					form.render();
				},
				error: function (msg) {
					layer.msg('服务器繁忙');
				}
			});
		}
	});
	$('#confirm_bt').click(function () {
		if ($("#choosedrugcodeA option:checked").text() !== "请选择药品编号A") {
			if ($("#choosedrugcodeB option:checked").text() !== "请选择药品编号B") {
				if ($("#msg").val().length > 0) {
					//ajax
					$.ajax({
						type: "POST",
						url: "drugController/insertcompatibility",
						data: {
							choosedrugcodeA: $("#choosedrugcodeA option:checked").text(),
							choosedrugcodeB: $("#choosedrugcodeB option:checked").text(),
							msg: $('#msg').val()
						},
						success: function (res) {
							if (res === "success") {
								alert("添加成功！");
								var index = parent.layer.getFrameIndex(window.name);
								parent.layer.close(index);
							} else if (res === "exist") {
								layer.alert('配伍禁忌已存在，请重新填写');
							}
							layer.close();
						},
						error: function () {
							layer.msg('服务器繁忙');
						}
					});
				} else {
					layer.msg("请填写禁忌说明");
				}
			} else {
				layer.msg("请选择药品编号B");
			}
		} else {
			layer.msg("请先选择药品编号A");
		}

	});
});