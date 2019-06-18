$(function(){
    //绑定回车功能
    $(document).keydown(function(event){
        if(event.keyCode==13){
            userLogin();
        }
    })
});

//用户登陆校验
function userLogin() {
    $.ajax({
        type: "POST",
        url: "/login/UserLogin",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data:JSON.stringify({
            "username" : $("#username").val(),
            "passwords" : $("#passwords").val(),
        }),
        success:function (result){
            if (result){
                window.location.href = "/index"
            } else{
                toastr.options = {
                    "closeButton": true,
                    "debug": false,
                    "progressBar": false,
                    "positionClass": "toast-top-center",
                    "onclick": null,
                    "showDuration": "400",
                    "hideDuration": "1000",
                    "timeOut": "2500",
                    "extendedTimeOut": "1000",
                    "showEasing": "swing",
                    "hideEasing": "linear",
                    "showMethod": "fadeIn",
                    "hideMethod": "fadeOut"
                }
                toastr.error("帐号密码错误","登陆失败！")
            }
        }
    })
}