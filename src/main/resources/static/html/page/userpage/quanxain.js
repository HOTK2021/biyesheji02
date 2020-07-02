$(function () {
    selectQuanXian()
    selectRole()
    selectUser()
})

function selectQuanXian() {
    layui.use('table',function () {
        var table = layui.table;
        table.render({
            elem: '#quanxian'
            ,height: 'auto'
            ,url: '/selectUserRole' //数据接口
            ,page: true //开启分页
            ,toolbar : '#barDemo'
            ,cols: [[
                {field: 'user_id', title: '用户ID',sort: true}
                ,{field: 'username', title: '用户名'}
                ,{field: 'role_name', title: '角色名称'}
                ,{field: 'btn', title: '操作',templet:function (d) {
                    var user_id = d.user_id;
                        return '<button class="layui-btn" onclick="bianji('+user_id+')">编辑</button>'
                }}
            ]]
        })
    })
}

function addRole() {
    layui.use(['layer','form'],function () {
        var layer = layui.layer,form = layui.form;
        layer.open({
            type: 1
            ,area: ['400px','240px']
            ,title: '新增'
            ,resize:false
            ,content: $('#addRole')
        })

        form.on('submit(formDemo)',function () {
            var userId=$('#userId').val()
            var role_id=$('#role_id').val()
            $.ajax({
                type: 'post',
                dataType: 'json',
                url: '/addgongzhi?userId='+userId+'&role_id='+role_id,
                success: function (data) {
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
                        $('#userId').val('')
                        $('#role_id').val('')
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
                        $('#userId').val('')
                        $('#role_id').val('')
                    }
                }
            })
            return false
        })

    })
}

function bianji(user_id) {
    layui.use(['layer','form','table'],function () {
        var layer = layui.layer,form = layui.form,table = layui.table;
        layer.open({
            type: 1
            ,area: ['400px','220px']
            ,title: '新增'
            ,resize:false
            ,content: $('#bianjiDiv')
        })
        form.on('submit(bianjiBtn)',function () {
            var role_id = $('#role_id2').val()
            $.ajax({
                type: 'post',
                dataType: 'json',
                url: '/updateRole?user_id='+user_id+'&role_id='+role_id,
                success: function (data) {
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
                        $('#role_id2').val('')
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
                        $('#role_id2').val('')
                    }
                }
            })
            return false
        })
    })
}


function selectUser() {
    $.ajax({
        type: 'get',
        dataType: 'json',
        url: '/selectnotGZ',
        async: false,
        success:function (data) {
            for (var i=0;i<data.length;i++){
                $('#userId').append('<option value="'+data[i].user_id+'">'+data[i].username+'</option>')
            }
        }
    })
}

function selectRole() {
    $.ajax({
        type: 'get',
        dataType: 'json',
        url: '/findRole',
        async: false,
        success:function (data) {
            for (var i=0;i<data.length;i++){
                $('#role_id').append('<option value="'+data[i].role_id+'">'+data[i].role_name+'</option>')
                $('#role_id2').append('<option value="'+data[i].role_id+'">'+data[i].role_name+'</option>')
            }
        }
    })
}