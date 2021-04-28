var falg=true;
layui.use(['jquery','layer'],function () {
    var $=layui.$,layer=layui.layer
    table = layui.table;
    $(function () {
        selectUserInfo("/selectToBeProcessed")
    })    //添加用户
})
//根据字段模糊查询或ID查询的搜索框功能
function searProcess() {
    layui.use(['jquery','layer'],function () {
        var $=layui.$,layer=layui.layer;
        var key=$("#keyWord").val();
        if(/^[0-9]+$/.test(key)){
            selectUserInfo("/selectToBeProcessed?user_id="+key);
        }else {
            selectUserInfo("/selectToBeProcessed?keyWord="+key);
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
            elem: '#tobeprocessed',
            type:"post"
            ,height: 'auto'
            ,url: url //数据接口
            // ,page: true //开启分页
            // ,limits : [10,15,20,25]
            // ,limit :10
            ,cols : [[
                {field: 'user_id', title: 'ID', minWidth:100, align:"center"},
                {field: 'username', title: '姓名', minWidth:200, align:'center'},
                {field: 'description', title: '病因', minWidth:200, align:'center'},
                {field: 'create_time', title: '诊断时间', minWidth:200, align:'center'},
                {field: 'username', title: '诊断医生', minWidth:200, align:'center'},
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
//列表操作
    table.on('tool(tobeprocessed)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            $.ajax({
                type: "post",
                data:{"user_id":data.user_id},
                dataType:"json",
                url:"/process",
                success:function (data) {
                    if(data==1){
                        layer.msg("处理成功");
                        selectUserInfo("/selectToBeProcessed");
                    }else {
                        layer.msg("处理失败");
                        selectUserInfo("/selectToBeProcessed");
                    }
                }
            })
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