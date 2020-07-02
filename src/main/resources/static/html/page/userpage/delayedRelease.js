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
        imprisonmentTime();
        hell();
    });
    function imprisonmentTime() {
        for(var i=0;i<=500;i++){
            $("#addImprisonmentTime").append("<option value='"+i+"'>"+i+"</option>");
        }
    };
    function hell() {
        $.ajax({
            type:"post",
            dataType: "json",
            url: "/selectMingJieEighteen",
            success:function (data) {
                for(var i=0;i<data.length;i++){
                    var info=data[i].info;
                    $("#hell").append("<option value='"+i+"'>"+data[i].eightName+":"+info+"</option>");
                }
                form.render("select");
            }
        })
    }
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        upload = layui.upload,
        laydate = layui.laydate,
        address = layui.address;

    //提交个人资料
    form.on("submit(delayedRelease)",function(){
        var addImprisonmentTime=$("#addImprisonmentTime").val();
        // 将填写的用户信息存到session以便下次调取
        $.ajax({
            type:"post",
            dataType:"json",
            data:$("#delayedRelease").serialize(),
            url: "/addImprisonmentTime?addImprisonmentTime="+addImprisonmentTime,
            success:function (data) {
                if (data==1){
                    layer.msg("修改成功！");
                }else{
                    layer.msg("修改失败！");
                }
            }
        });
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    form.render("select");
});