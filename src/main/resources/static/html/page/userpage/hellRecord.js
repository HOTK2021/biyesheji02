var falg=true;
layui.use(['jquery','layer'],function () {
    var $=layui.$,layer=layui.layer
    table = layui.table;
    $(function () {
        selectUserInfo("/hellRecord")
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
            elem: '#hellRecord'
            ,height: 'auto'
            ,url: url //数据接口
            // ,page: true //开启分页
            // ,limits : [10,15,20,25]
            // ,limit :10
            ,cols : [[
                {field: 'userId', title: '用户ID', minWidth:100, align:"center"},
                {field: 'eighteenId', title: '受刑地狱', minWidth:200, align:'center',templet: function (d) {
                        if (d.eighteenId==1){
                            return "第一层：拔舌地狱";
                        }else if (d.eighteenId==2){
                            return "第二层：剪刀地狱";
                        }else if (d.eighteenId==3){
                            return "第三层：铁树地狱";
                        }else if (d.eighteenId==4){
                            return "第四层：孽镜地狱";
                        }else if (d.eighteenId==5){
                            return "第五层：蒸笼地狱";
                        }else if (d.eighteenId==6){
                            return "第六层：铜柱地狱";
                        }else if (d.eighteenId==7){
                            return "第七层：刀山地狱";
                        }else if (d.eighteenId==8){
                            return "第八层：冰山地狱";
                        }else if (d.eighteenId==9){
                            return "第九层：油锅地狱";
                        }else if (d.eighteenId==10){
                            return "第十层：牛坑地狱";
                        }else if (d.eighteenId==11){
                            return "第十一层：石压地狱";
                        }else if (d.eighteenId==12){
                            return "第十二层：舂臼地狱";
                        }else if (d.eighteenId==13){
                            return "第十三层：血池地狱";
                        }else if (d.eighteenId==14){
                            return "第十四层：枉死地狱";
                        }else if (d.eighteenId==15){
                            return "第十五层：磔刑地狱";
                        }else if (d.eighteenId==16){
                            return "第十六层：火山地狱";
                        }else if (d.eighteenId==17){
                            return "第十七层：石磨地狱";
                        }else if (d.eighteenId==18){
                            return "第十八层：刀锯地狱";
                        }
                    }},
                {field: 'inOrOutTime', title: '入狱时间', minWidth:200, align:'center'},
                {field: 'status', title: '执行状态', minWidth:200, align:'center',templet:function (d) {
                        if(d.status==1){
                            return "受刑中";
                        }else if(d.status==2){
                            return "出狱";
                        }
                    }},
                {field: 'info', title: '执行描述', minWidth:200, align:'center'},
                {field: 'imprisonmentTime', title: '受刑年数', minWidth:200, align:'center'}
                // {title: '操作', width:170, templet:'#TorturedListBar',align:"center"}
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
    function addNews(delayedRelease){
        var index = layui.layer.open({
            title : "添加审判信息",
            type : 2,
            content : "/html/page/userpage/delayedRelease.html",
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(delayedRelease){
                    body.find("#userId3").val(delayedRelease.userId);
                    body.find("#imprisonmentTime").val(delayedRelease.imprisonmentTime);
                    form.render();
                }
                setTimeout(function(){
                    layui.layer.tips('点击此处返回文章列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                    location.href("Tortured.html");
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
    table.on('tool(hellRecord)', function(obj){
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
        } else if(layEvent === 'delayedRelease'){ //删除
            addNews(data);
        } else if(layEvent === 'look'){ //预览
            layer.alert("此功能需要前台展示，实际开发中传入对应的必要参数进行文章内容页面访问")
        }
    });
})






