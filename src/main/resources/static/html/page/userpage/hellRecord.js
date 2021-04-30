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
                {field: 'eighteenId', title: '门诊科室', minWidth:200, align:'center',templet: function (d) {
                        if (d.eighteenId==1){
                            return " 皮肤科";
                        }else if (d.eighteenId==2){
                            return " 眼科";
                        }else if (d.eighteenId==3){
                            return " 儿科";
                        }else if (d.eighteenId==4){
                            return " 口腔科";
                        }else if (d.eighteenId==5){
                            return " 肿瘤科";
                        }else if (d.eighteenId==6){
                            return " 疼痛科";
                        }else if (d.eighteenId==7){
                            return " 耳鼻咽喉科";
                        }else if (d.eighteenId==8){
                            return " 生殖医学科";
                        }else if (d.eighteenId==9){
                            return " 胸外科";
                        }else if (d.eighteenId==10){
                            return " 泌尿科";
                        }else if (d.eighteenId==11){
                            return " 骨科";
                        }else if (d.eighteenId==12){
                            return " 肝胆胰外科";
                        }else if (d.eighteenId==13){
                            return " 妇科";
                        }else if (d.eighteenId==14){
                            return " 产科";
                        }else if (d.eighteenId==15){
                            return " 神经外科";
                        }else if (d.eighteenId==16){
                            return " 皮肤科";
                        }else if (d.eighteenId==17){
                            return " 康复医学科";
                        }else if (d.eighteenId==18){
                            return " 心血管内科";
                        }
                    }},
                {field: 'inOrOutTime', title: '诊断时间', minWidth:200, align:'center'},
                {field: 'status', title: '执行状态', minWidth:200, align:'center',templet:function (d) {
                        if(d.status==1){
                            return "回家调养";
                        }else if(d.status==2){
                            return "住院";
                        }
                    }},
                {field: 'info', title: '执行描述', minWidth:200, align:'center'},
                {field: 'imprisonmentTime', title: '诊断费用', minWidth:200, align:'center'}
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
            title : "添加诊断信息",
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






