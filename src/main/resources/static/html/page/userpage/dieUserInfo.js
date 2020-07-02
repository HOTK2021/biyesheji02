var $;
layui.use(['jquery','layer','address','form','laydate'],function () {
    $=layui.$;
    var layer=layui.layer,address=layui.address,form=layui.form,laydate=layui.laydate;
    var plague_info_id=$("#dieUserInfoTable").val();
    $(function () {
        plague();
        person();
        selectUserInfo(10);
    })

    form.render('select');
})

function plague() {
    $.ajax({
        type:'get',
        dataType:'json',
        url:'/selectPlauge',
        async:false,
        success:function (data) {
            for (var i=0;i<data.length;i++){
                $('#plague').append('<option value="'+data[i].plague_id+'">'+data[i].plague_name+'</option>')
            }
        }
    });
}


function person() {
    $.ajax({
        type:'get',
        dataType:'json',
        url:'/selectUserToPlague',
        async:false,
        success:function (data) {
            for (var i=0;i<data.length;i++){
                $('#person').append('<option value="'+data[i].user_id+'">'+data[i].username+'</option>')
            }
        }
    });
}

function selectUserInfo(plague_info_id){
    layui.use('table', function(){
        var table = layui.table;
        table.render({
            elem: '#dieUserTable'
            ,height: 'auto'
            ,url: '/selectPlagueUser?plague_info_id='+plague_info_id //数据接口
            // ,page: true //开启分页
            // ,limits : [10,15,20,25]
            // ,limit :10
            ,cols : [[
                {field: 'id',title: 'ID',sort: true,width: 200}
                ,{field: 'userid',title: '用户ID',sort: true,width: 200}
                ,{field: 'username',title: '用户名',width: 200}
            ]]
        });
    });
}



// function showDieUser(plague_info_id) {
//     layui.use(['table','jquery'],function () {
//         var $ = layui.$,
//             table = layui.table,
//             layer = layui.layer;
//         table.render({
//             elem:'#dieUserTable'
//             ,height: 'auto'
//             ,url: '/selectPlagueUser?plague_info_id='+plague_info_id //数据接口
//             ,cols: [[
//                 {field: 'id',title: 'ID',sort: true,width: 200}
//                 ,{field: 'userid',title: '用户ID',sort: true,width: 200}
//                 ,{field: 'username',title: '用户名',width: 200}
//             ]]
//         })
//
//         // layer.open({
//         //     type:1,
//         //     title:'死亡名单',
//         //     area:['600px','400px'],
//         //     content: $("#dieUserDiv"),
//         //     offset:'auto',
//         //     end:function () {
//         //         $("#divs").css("display","none");
//         //     }
//         // })
//     })
// }


