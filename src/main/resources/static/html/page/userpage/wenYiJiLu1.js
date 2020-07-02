var falg=true;
layui.use(['jquery','layer'],function () {
    var $=layui.$,layer=layui.layer
    table = layui.table;
    $(function () {
        selectUserInfo("/selectPlaugeInfo")
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
    layui.use(['form','layer','laydate','table','laytpl'],function(){
        var table = layui.table;
        var form = layui.form,
            layer = parent.layer === undefined ? layui.layer : top.layer,
            $ = layui.jquery,
            laydate = layui.laydate,
            laytpl = layui.laytpl,
            table = layui.table;
        table.render({
            elem: '#wenyitable'
            ,height: 'auto'
            ,url: url //数据接口
            ,page: true //开启分页
            ,limits : [10,15,20,25]
            ,limit :10
            ,cols : [[
                {field: 'plague_info_id', title: 'ID'}
                ,{field: 'address', title: '散播地区',templet:function (d) {
                        var code = (d.area_id).toString();
                        var address = "";
                        $.ajax({
                            url:'../../json/address.json',
                            type:'get',
                            dataType:'json',
                            async:false,
                            success:function (data) {
                                for (var i=0;i<data.length;i++){
                                    if(data[i].code==code.slice(0,2)){
                                        address+=data[i].name
                                    }
                                    for (var j=0;j<data[i].childs.length;j++){
                                        if(data[i].childs[j].code==code.slice(0,4)){
                                            address+=data[i].childs[j].name
                                        }
                                        for (var k=0;k<data[i].childs[j].childs.length;k++){
                                            if(data[i].childs[j].childs[k].code == code){
                                                address+=data[i].childs[j].childs[k].name
                                            }

                                        }
                                    }
                                }
                            }
                        })
                        return '<span>'+address+'</span>'
                    }}
                ,{field: 'die_count', title: '死亡人数'}
                ,{field: 'happen_time', title: '散播时间'}
                ,{title: '操作', width:170, templet:'#dieUserInfoListBar',align:"center"}
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
            title : "添加审判信息",
            type : 2,
            content : "/html/page/userpage/dieUserInfo.html",
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find("#dieUserInfoTable").val(edit.plague_info_id);
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
    table.on('tool(wenyitable)', function(obj){
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






