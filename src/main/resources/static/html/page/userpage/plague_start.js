var form, $,areaData;
layui.config({
    base : "../../js/"
}).extend({
    "address" : "address"
})
layui.use(['form','layer','upload','laydate',"address"],function(){


    form = layui.form;
    $ = layui.jquery;
    $(function () {

    });

    var layer = parent.layer === undefined ? layui.layer : top.layer,
        upload = layui.upload,
        laydate = layui.laydate,
        address = layui.address;

    //显示出全部的病种
    $(function () {
        $.ajax({
            url: "/findPlagueAll",
            type: "get",
            dataType: "json",
            success: function (data) {
                $.each(data, function (index, item) {
                    // alert("xxx");
                    console.log(item);
                    // console.log(item.info);
                    //new Option方法是新建一个option，第一个参数是需要显示的值，
                    // 第二个参数是option的value值,然后append到下拉框中。
                    $('#plagueId').append(new Option(item.plague_name,item.plague_id));// 下拉菜单里添加元素
                });
                layui.form.render("select");
            }
            ,
            error: function () {
                // alert("查询地狱失败!!!!!")
                layui.msg("查询病种失败!!!!!");
            }
        })
    })

    //提交个人资料
    form.on("submit(addUser)",function(res){
        // var index = layer.msg('提交中，请稍候',{icon: 16,time:false,shade:0.8});
        //将填写的用户信息存到session以便下次调取
        // layer.confirm('确定发起瘟疫吗，发起瘟疫人间将受进折磨?',{icon:3, title:'提示信息'},function(index){
        $.ajax({
            type:"post",
            dataType:"json",
            // data:$("#addUser").serialize(),
            data:res.field,
            url: "/startPlague",
            success:function (data) {
                if (data==1){
                    layer.msg("瘟疫发起成功！");
                    // location.href=".html";
                }else{
                    layer.msg("瘟疫发起失败！");
                }
            }
        });
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        // });
    });

    form.render("select");
});