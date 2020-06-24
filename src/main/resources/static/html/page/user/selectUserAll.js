var falg=true;
layui.use(['jquery','layer'],function () {
    var $=layui.$,layer=layui.layer
    table = layui.table;
    $(function () {
        selectUserInfo("/userAll")
    })    //添加用户
})
//根据字段模糊查询或ID查询的搜索框功能
function searCH() {
    layui.use(['jquery','layer'],function () {
        var $=layui.$,layer=layui.layer;
        var key=$("#keyWord").val();
        if(/^[0-9]+$/.test(key)){
            selectUserInfo("/userAll?user_id="+key);
        }else {
            selectUserInfo("/userAll?keyWord="+key);
        }

    })
}
function selectUser() {
    var url="/userAll"
    selectUserInfo(url)
}
function selectUserDie() {
    var url="/userDieAll"
    falg=false;
    selectUserInfo(url)
}

function selectUserInfo(url){
    layui.use('table', function(){
        var table = layui.table;
        table.render({
            elem: '#userInfoAll'
            ,height: 'auto'
            ,url: url //数据接口
            ,page: true //开启分页
            ,limits : [10,15,20,25]
            ,limit :10
            ,cols : [[
                {type: "checkbox", fixed:"left", width:50},
                {field: 'user_id', title: '用户ID', minWidth:100, align:"center"},
                {field: 'username', title: '用户名', minWidth:200, align:'center'},
                {field: 'dept_id', title: '部门ID', minWidth:200, align:'center'},
                {field: 'email', title: '邮箱', minWidth:200, align:'center'},
                {field: 'mobile', title: '电话', minWidth:200, align:'center'},
                {field: 'ssex', title: '性别', minWidth:200, align:'center'},
                {field: 'description', title: '描述', minWidth:200, align:'center'},
                {field: 'age', title: '年龄', minWidth:200, align:'center'},
                {title: '操作', width:170, templet:'#UserListBar',align:"center"}
            ]]
        });

    });
}
layui.use(['form','layer','laydate','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table;
//添加文章
    function addNews(edit){
        var index = layui.layer.open({
            title : "修改",
            type : 2,
            content : "/html/page/userpage/modifyUserInfo.html",
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find("#modifyName").val(edit.username);
                    body.find("#modifylifetime").val(edit.age);
                    body.find("#modifydescription").val(edit.description);
                    body.find("#modifyID").val(edit.user_id);
                    form.render();
                }
                setTimeout(function(){
                    layui.layer.tips('点击此处返回文章列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        })
        layui.layer.full(index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(index);
        })
    }
    $(".addNews_btn").click(function(){
        addNews();
    })
//列表操作
    table.on('tool(userInfoAll)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            addNews(data);
        } else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此文章？',{icon:3, title:'提示信息'},function(index){
                // $.get("删除文章接口",{
                //     newsId : data.newsId  //将需要删除的newsId作为参数传入
                // },function(data){
                tableIns.reload();
                layer.close(index);
                // })
            });
        } else if(layEvent === 'look'){ //预览
            layer.alert("此功能需要前台展示，实际开发中传入对应的必要参数进行文章内容页面访问")
        }
    });
})






