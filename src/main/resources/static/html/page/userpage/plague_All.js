layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    //用户列表
    var tableIns = table.render({
        elem: '#userList',
        url : "/findPlagueInfo",
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 20,
        id : "userListTable",
        cols : [[
            // {type: "checkbox", fixed:"left", width:50},
            {field: 'pid', title: '记录编号',  align:"center"},
            {field: 'plagueid', title: '瘟疫种类',  align:"center",templet: function (d) {
                    if(d.plagueid=="1"){
                        return "黑死病";
                    }else if(d.plagueid=="2"){
                        return "鼠疫";
                    }else if(d.plagueid=="3"){
                        return "雅典瘟疫";
                    }else if(d.plagueid=="4"){
                        return "流感";
                    }else if(d.plagueid=="5"){
                        return "狂犬病";
                    }else if(d.plagueid=="6"){
                        return "天花";
                    }else if(d.plagueid=="7"){
                        return "结核病";
                    }else if(d.plagueid=="8"){
                        return "霍乱";
                    }else if(d.plagueid=="9"){
                        return "登革热病毒";
                    }
                }},
            {field: 'diecount', title: '死亡人数',  align:"center"},
            {field: 'username', title: '瘟疫源',  align:"center"},
            {field: 'plagueTime', title: '发布时间',  align:"center"},
            {title: '操作', minWidth:175, templet:'#userListBar',fixed:"right",align:"center"}
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){

        // alert($(".searchVal").val());

        if($(".searchVal").val() != ''){
            table.reload("userListTable",{

                // page: {
                //     curr: 1 //重新从第 1 页开始
                // },
                where: {
                    username:$(".searchVal").val()
                },
            })
        }else{
            layer.msg("请输入搜索的内容");
        }
    });

    //添加用户
    function addUser(edit){
        var index = layui.layer.open({
            title : "瘟疫死亡详情",
            type : 2,
            content : "plague_all_details.html",
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find("#pid").val(edit.pid);  //用户id

                    form.render();
                }
                setTimeout(function(){
                    layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {

                        tips: 3
                    });
                },500)
            }
        })
        layui.layer.full(index);
        window.sessionStorage.setItem("index",index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(window.sessionStorage.getItem("index"));
        })
    }
    $(".addNews_btn").click(function(){
        addUser();
    })

    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('userListTable'),
            data = checkStatus.data,
            newsId = [];
        if(data.length > 0) {
            for (var i in data) {
                newsId.push(data[i].newsId);
            }
            layer.confirm('确定删除选中的用户？', {icon: 3, title: '提示信息'}, function (index) {
                // $.get("删除文章接口",{
                //     newsId : newsId  //将需要删除的newsId作为参数传入
                // },function(data){
                tableIns.reload();
                layer.close(index);
                // })
            })
        }else{
            layer.msg("请选择需要删除的用户");
        }
    })

    //列表操作
    table.on('tool(userList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            addUser(data);
        }else if(layEvent === 'usable'){ //启用禁用
            var _this = $(this),
                usableText = "是否确定禁用此用户？",
                btnText = "已禁用";
            if(_this.text()=="已禁用"){
                usableText = "是否确定启用此用户？",
                    btnText = "已启用";
            }
            layer.confirm(usableText,{
                icon: 3,
                title:'系统提示',
                cancel : function(index){
                    layer.close(index);
                }
            },function(index){
                _this.text(btnText);
                layer.close(index);
            },function(index){
                layer.close(index);
            });
        }else if(layEvent === 'del'){ //删除
            // layer.confirm('您确定轮回么,轮回以后将脱离地狱?',{icon:3, title:'提示信息'},function(index){
            $.ajax({
                url: "/",
                type:"get",
                dataType:"json",
                data:{"pid":data.pid},
                success:function (data) {
                    if (data==1){
                        layer.msg("轮回成功！");
                        location.href="againList_no.html";
                    }else{
                        layer.msg("轮回失败！");
                    }
                }
            })
            // });
        }
    });

})
