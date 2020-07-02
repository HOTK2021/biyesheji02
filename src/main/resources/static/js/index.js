var user=null;
var config=null;
var roles=null;
$(function(){
	initData();
});
function initData(){
	$.ajax({
		url:"/loginSuccess",
		dataType:"json",
		type:"get",
		async:false,
		success:function(data){
			user=data[0];
			config=data[1];
			roles=data[2];
		}
	});
	if(user!=null){
		$('.userName').html(user.username);
		$('.adminName').html(user.username);
		//alert(roles.length);
		//var ro=$(".topLevelMenus .menu").val();
		var ro1 = {};
		$(".topLevelMenus .menu").each(function(index, el) {
			// alert(el.value);
			ro1[index] = el.value;
		});
		var arr = Object.keys(ro1);
		// alert(roles[1].menu_id);
		// alert(arr);
		for (var i=0;i<roles.length;i++) {
			// alert(ro1[i]);
			for (var j = 0; j < arr.length; j++) {
				if (roles[i].menu_id == ro1[j]) {
					$('#'+roles[i].menu_id).css("pointer-events", "");
				}
			}
		}




		// $.ajax({
		// 	type:'get',
		// 	dataType: 'json',
		// 	url:'/html/json/navs.json',
		// 	success:function (data) {
		// 		for(var i=0;i<data.userInfo.length;i++){
		// 			for (var j=0;j<roles.length;j++){
		// 				if (data.userInfo[i].id==roles[j].menu_id){
		// 					$('#'+data.userInfo[i]).css("pointer-events", "");
		//
		// 				}
		// 			}
		// 		}
		// 	}
		// });


	}else{
		layui.use('layer',function () {
			var layer = layui.layer;
			layer.open({
				type:1
				,closeBtn: false
				,anim: 6
				,resize: false
				,maxWidth: 200
				,content: '<div style="margin-top: 20px; margin-left: 10%">还没有登陆，快去登陆吧！</div>'
				,btn: '去登陆' //关闭按钮
				,btnAlign: 'c' //按钮居中
				,yes: function(){
					layer.closeAll();
					location.href='/login.html'
				}
			})
		})
	}
	// alert("11111"+user)
}

// var ro2 = {};
// $(".topLevelMenus .menu").each(function(index, el) {
// 	ro1[index] = el.value;
// });
// var arr = Object.keys(ro1);
// for (var i=0;i<arr.length;i++){
// 	$("#"+ro2[i]).click(function () {
//
// 		$.ajax({
// 			type:'get',
// 			dataType: 'json',
// 			url:'/html/json/navs.json',
// 			success:function (data) {
// 				for(var k=0;k<data.userInfo.length;k++){
// 					for (var j=0;j<roles.length;j++){
// 						if (data.userInfo[k].id==roles[j].menu_id){
// 							$('#'+data.userInfo[k]).css("pointer-events", "");
// 						}
// 					}
// 				}
// 			}
// 		});
// 	});
// }










// var user=null;
// var config=null;
// var roles=null;
// $(function(){
// 	initData();
// //	alert(user);
// //	alert(config);
// //	alert(roles);
// });
// function initData(){
// 	$.ajax({
// 		url:"../loginSuccess",
// 		dataType:"json",
// 		type:"get",
// 		async:false,
// 		success:function(data){
// 			user=data[0];
// 			$('.userName').html(user.username);
// 			$('.adminName').html(user.username);
// 			// config=data[1];
// 			// roles=data[2];
// 		}
// 	});
// }












