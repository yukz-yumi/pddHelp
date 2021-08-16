$().ready(function() {
	validateRule();
	initSwitch();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/task/taskTypeInfo/save",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			name : {
				required : true
			}
		},
		messages : {
			name : {
				required : icon + "请输入姓名"
			}
		}
	})
}

function initSwitch(){
	$('#allowed').bootstrapSwitch({
		onText : "启用",      // 设置ON文本
		offText : "禁用",    // 设置OFF文本
		onColor : "success",// 设置ON文本颜色(info/success/warning/danger/primary)
		offColor : "warning",  // 设置OFF文本颜色 (info/success/warning/danger/primary)
		size : "small",    // 设置控件大小,从小到大  (mini/small/normal/large)
		// 当开关状态改变时触发
		onSwitchChange : function(event, state) {
		}
	})
}