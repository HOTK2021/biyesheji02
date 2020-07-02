$(function () {
 selectMoney()
})

function selectMoney() {
    layui.use('table',function () {
        var table = layui.table;
        table.render({
            elem: '#money'
            ,id:"money"
            ,height: 'auto'
            ,url: '/selectHad' //数据接口
            ,page: true //开启分页
            ,cols: [[
                {field: 'id', title: 'ID', sort: true}
                ,{field: 'username', title: '用户名'}
                ,{field: 'total', title: '余额',sort: true}
                ,{field: 'btn', title: '操作',templet:function (d) {
                    var userId = d.userId
                    return '<button class="layui-btn" onclick="chongzhi('+userId+')">充值</button>' +
                        '<button class="layui-btn" onclick="xiaofei('+userId+')">消费</button>' +
                        '<button class="layui-btn" onclick="xiangqing('+userId+')">详情</button>'
                }}
            ]]
        })
    })
}

function chongzhi(userId) {
    layui.use(['layer','form','table'],function () {
        var layer = layui.layer,
            form = layui.form,
            table = layui.table;
        layer.open({
            type: 1
            ,area: ['400px','300px']
            ,title: '充值'
            ,content: $('#chongzhiDiv')
        })
        form.on('submit(chongzhiBtn)',function () {
            var total = $('#total').val();
            var info = $('#info').val();
            $.ajax({
                type:'post',
                dataType:'json',
                data:{'userId':userId,'total':total,'info':info,'status':1},
                url: '/modifyTotal',
                success: function (data) {
                    console.log(data);
                    if (data == 0) {
                        layer.open({
                            type: 1,
                            title: '提示',
                            btn: '关闭',
                            btnAlign: 'c',
                            content: '<div style="padding: 20px 100px;"> 操作成功</div>'
                            ,yes:function () {
                                layer.closeAll()
                            }
                        })
                        table.reload('money');
                         $('#total').val('');
                         $('#info').val('');
                    } else {
                        layer.open({
                            type: 1,
                            title: '提示',
                            btn: '关闭',
                            btnAlign: 'c',
                            content: '<div style="padding: 20px 100px;"> 操作失败</div>'
                            ,yes:function () {
                                layer.closeAll()
                            }
                        })
                        table.reload('money');
                        $('#total').val('');
                        $('#info').val('');
                    }
                }
            })
            return false
        })
    })
}

function xiaofei(userId) {
    layui.use(['layer','form','table'],function () {
        var layer = layui.layer,form = layui.form,table = layui.table;
        layer.open({
            type: 1
            ,title: '消费'
            ,area: ['400px','300px']
            ,content: $('#xiaofeDiv')
        })
        form.on('submit(xiaofeiBtn)',function () {
            var total = $('#total2').val();
            var info = $('#info2').val();
            $.ajax({
                type:'post',
                dataType:'json',
                data:{'userId':userId,'total':total,'info':info,'status':2},
                url: '/modifyTotal',
                success: function (data) {
                    console.log(data);
                    if (data == 0) {
                        layer.open({
                            type: 1,
                            title: '提示',
                            btn: '关闭',
                            btnAlign: 'c',
                            content: '<div style="padding: 20px 100px;"> 操作成功</div>'
                            ,yes:function () {
                                layer.closeAll()
                            }
                        })
                        table.reload('money');
                        $('#total2').val('');
                        $('#info2').val('');
                    } else {
                        layer.open({
                            type: 1,
                            title: '提示',
                            btn: '关闭',
                            btnAlign: 'c',
                            content: '<div style="padding: 20px 100px;"> 操作失败</div>'
                            ,yes:function () {
                                layer.closeAll()
                            }
                        })
                        table.reload('money');
                        $('#total').val('');
                        $('#info').val('');
                    }
                }
            })
            return false
        })
    })
}

function xiangqing() {
    layui.use(['layer','form','table'],function () {
        var layer = layui.layer,form = layer.form,table = layui.table;
        layer.open({
            type: 1
            ,area: ['800px','600px']
            ,title: '详情'
            ,content: $('#xiangqingDiv')
        })
        table.render({
            elem: '#xiangqingTable'
            ,height: 'auto'
            ,url: '/selectLog' //数据接口
            ,page: true //开启分页
            ,cols: [[
                {field: 'id', title: 'ID', sort: true}
                ,{field: 'userId', title: '用户名'}
                ,{field: 'info', title: '概述',sort: true}
                ,{field: 'createTime', title: '充值/消费时间',sort: true}
                ,{field: 'status', title: '充值/消费',templet: function (d) {
                     var status = d.status;
                     if(status==1){
                         return '充值'
                     }else{
                         return '消费'
                     }
                    }}
            ]]
        })
    })
}

