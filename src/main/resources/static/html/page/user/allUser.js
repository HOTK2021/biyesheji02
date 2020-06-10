layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    //用户列表
    var tableIns = table.render({
        elem: '#allUser',
        url : '/userAll',
        cellMinWidth : 95,
        page : false,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 20,
        id : "userALlTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: 'user_id', title: '用户ID', minWidth:100, align:"center"},
            {field: 'username', title: '用户名', minWidth:200, align:'center'},
            {field: 'dept_id', title: '部门ID', minWidth:200, align:'center'},
            {field: 'email', title: '邮箱', minWidth:200, align:'center'},
            {field: 'mobile', title: '电话', minWidth:200, align:'center'},
            {field: 'status', title: '权限', minWidth:200, align:'center'},
            {field: 'create_time', title: '创建时间', minWidth:200, align:'center'},
            {field: 'modify_time', title: '修改时间', minWidth:200, align:'center'},
            {field: 'last_login_time', title: '最后登录时间', minWidth:200, align:'center'},
            {field: 'ssex', title: '性别', minWidth:200, align:'center'},
            {field: 'description', title: '描述', minWidth:200, align:'center'},
            {field: 'avatar', title: '头像', minWidth:200, align:'center'},
            {field: 'age', title: '年龄', minWidth:200, align:'center'}
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        if($(".searchVal").val() != ''){
            table.reload("newsListTable",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    key: $(".searchVal").val()  //搜索的关键字
                }
            })
        }else{
            layer.msg("请输入搜索的内容");
        }
    });

    //添加用户
    function addUser(edit){
        var index = layui.layer.open({
            title : "添加用户",
            type : 2,
            content : "userAdd.html",
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".userName").val(edit.userName);  //登录名
                    body.find(".userEmail").val(edit.userEmail);  //邮箱
                    body.find(".userSex input[value="+edit.userSex+"]").prop("checked","checked");  //性别
                    body.find(".userGrade").val(edit.userGrade);  //会员等级
                    body.find(".userStatus").val(edit.userStatus);    //用户状态
                    body.find(".userDesc").text(edit.userDesc);    //用户简介
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
            layer.confirm('确定删除此用户？',{icon:3, title:'提示信息'},function(index){
                // $.get("删除文章接口",{
                //     newsId : data.newsId  //将需要删除的newsId作为参数传入
                // },function(data){
                tableIns.reload();
                layer.close(index);
                // })
            });
        }
    });

})

// layui.use('table', function(){
//     var table = layui.table;
//
//     //第一个实例
//     table.render({
//         elem: '#allUser'
//         ,height:321
//         ,url: '/userAll' //数据接口
//         ,page: false //开启分页
//         ,cols: [[ //表头
//             {field: 'user_id', title: '用户ID', minWidth:100, align:"center"},
//             {field: 'username', title: '用户名', minWidth:200, align:'center'},
//             {field: 'password', title: '1', minWidth:200, align:'center'},
//             {field: 'dept_id', title: '2', minWidth:200, align:'center'},
//             {field: 'email', title: '3', minWidth:200, align:'center'},
//             {field: 'mobile', title: '4', minWidth:200, align:'center'},
//             {field: 'status', title: '5', minWidth:200, align:'center'},
//             {field: 'create_time', title: '6', minWidth:200, align:'center'},
//             {field: 'modify_time', title: '7', minWidth:200, align:'center'},
//             {field: 'last_login_time', title: '8', minWidth:200, align:'center'},
//             {field: 'ssex', title: '9', minWidth:200, align:'center'},
//             {field: 'description', title: '10', minWidth:200, align:'center'},
//             {field: 'avatar', title: '11', minWidth:200, align:'center'},
//             {field: 'age', title: '11', minWidth:200, align:'center'}
//         ]]
//     });
//
// // console.log(11);
// });

