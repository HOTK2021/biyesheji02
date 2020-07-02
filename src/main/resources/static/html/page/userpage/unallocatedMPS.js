var falg=true;
layui.use(['jquery','layer'],function () {
    var $=layui.$,layer=layui.layer
    table = layui.table;
    $(function () {
        selectUserInfo("/selectSoulDispensed")
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
            elem: '#unallocatedMPS'
            ,height: 'auto'
            ,url: url //数据接口
            // ,page: true //开启分页
            // ,limits : [10,15,20,25]
            // ,limit :10
            ,cols : [[
                {field: 'userId', title: '用户ID', minWidth:100, align:"center"},
                {field: 'executorId', title: '执行人', minWidth:200, align:'center',templet:function (d) {
                        if(d.executorId==2){
                            return "孟婆";
                        }else {
                            return "判官";
                        }
                    }},
                {field: 'executorInfo', title: '执行描述', minWidth:200, align:'center'},
                {field: 'executorTime', title: '执行时间', minWidth:200, align:'center'},
                {field: 'executorStatus', title: '执行状态', minWidth:200, align:'center',templet:function (d) {
                        if(d.executorStatus==0){
                            return "等待分配孟婆汤";
                        }else {
                            return "已分配孟婆汤";
                        }
                    }},
                {title: '操作', width:170, templet:'#unallocatedMPSListBar',align:"center"}
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
    table.on('tool(unallocatedMPS)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'edit'){ //编辑
            $.ajax({
                type: "post",
                data:{"userId":data.userId},
                dataType:"json",
                url:"/dispensed",
                success:function (data) {
                    if(data==1){
                        layer.msg("分配成功");
                        selectUserInfo("/selectSoulDispensed");
                    }else {
                        layer.msg("分配失败");
                        selectUserInfo("/selectSoulDispensed");
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






