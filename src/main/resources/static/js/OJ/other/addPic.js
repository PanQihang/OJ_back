var FileName = "";
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
$(document).ready(function () {
    //加载富文本编辑器
    setProgress(0);
    loadSummernote();
    if (PicInfo.id != 'add'){
        //加载考试信息
        loadPic();
    }
});

function loadSummernote() {
    $("#picDesc").summernote({
        height: 200,
        minHeight: 200,
        maxHeight: 200,
        lang: 'zh-CN',
        toolbar: [
            ['style', ['style']],
            ['font', ['bold', 'underline', 'clear']],
            ['fontname', ['fontname']],
            ['color', ['color']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['table', ['table']],
            ['view', ['fullscreen', 'codeview']]
        ]

    })
}

function loadPic()
{
    //alert(PicInfo.id);
    var id = PicInfo.id;
    $.ajax({
        type: "POST",
        url: "/pic/loadPic",
        datatype: "json",
        data:{
            "id": id
        },
        success:function(result)
        {
            //console.log(result);
            $('#PicId').val(result.id);
            $('#PicName').val(result.name);
            $('#PicDescribes').val(result.describes);
            $('#picDesc').code(result.description);
            $('#checkHave').css('display', 'block');
            $('#checkHaveImg').attr('src', result.route);
        }
    })
}

/*
//更新或新增图片轮播信息
function saveOrUpdatePic() {
    //alert($('#uploadFile').val());
    if($("#PicName").val() == ""){
        toastr.error("","文章名不能为空");
        return;
    }
    var id = PicInfo.id;
    console.log(id);
    if($('#uploadPic').val()==''){//如果是新增，则直接判断错误，因为新增必须要图片，如果是修改且没有上传新的图片，则直接修改该修改的字段。
        if (PicInfo.id == 'add') {
            swal("上传失败！", "新增请上传图片", "error");
            return ;
        }
        else
        {
            var name = $('#PicName').val();
            var describes = $('#PicDescribes').val();
            var picDesc = $('#picDesc').code();
            $.ajax({
                type: "POST",
                url: "/pic/saveEditPic",
                datatype: "json",
                contentType: "application/json;charset=UTF-8",
                data:JSON.stringify({
                    "id": id,
                    "name": name,
                    "describes": describes,
                    "description": picDesc,
                }),
                success:function(result)
                {
                    if(result.flag==1)
                    {
                        swal("修改成功！", "", "success");
                        $(".confirm").click(function(){window.location.href = "/pic/addPic/"+id;})
                    }
                    else
                    {
                        swal("修改失败！", "请重试", "error");
                    }
                }
            })
        }
    }
    else//如果是新增，则save+保存图片，如果是修改且更改图片，则update+删除之前的图片且上传新的图片。
    {
        var formData = new FormData();
        formData.append('file', $('#uploadPic')[0].files[0]);
        console.log(($('#uploadPic')[0].files[0].type));
        console.log(($('#uploadPic')[0].files[0].name));
        if(($('#uploadPic')[0].files[0].type).indexOf("image/")==-1)
        {
            swal("上传失败！", "请上传图片", "error");
            return ;
        }
        if($('#uploadPic')[0].files[0].size >= 1073741824)//后面的5表示1G*5=5G 上限为5G 可修改
        {
            swal("上传失败！", "上传图片过大！最大不能超过1GB", "error");
            return ;
        }

        var FileName = $('#uploadPic')[0].files[0].name;
        showProgress();
        //var reqStr = 'requestData=' + (JSON.stringify({"url":url,"describes":describes,})).toString();
        var name = $('#PicName').val();
        var describes = $('#PicDescribes').val();
        var picDesc = $('#picDesc').code();
        formData.append('id', id);
        formData.append('name', name);
        formData.append('describes', describes);
        formData.append('picDesc', picDesc);
        var uploadGo = "/pic/uploadPic";
        xhr = new XMLHttpRequest();
        xhr.open("post", uploadGo, true);
        xhr.onloadstart = function() {
            ot = new Date().getTime();   //设置上传开始时间
            oloaded = 0;//已上传的文件大小为0
        };
        xhr.upload.addEventListener("progress", progressFunction, false);
        xhr.addEventListener("load", uploadComplete, false);
        xhr.addEventListener("error", uploadFailed, false);
        xhr.addEventListener("abort", uploadCanceled, false);
        xhr.send(formData);
    }
    if (PicInfo.id == 'add')
    {
        swal("上传成功！", "", "success");
        $(".confirm").click(function(){history.back(-1)})
    }
    else
    {
        swal("修改成功！", "", "success");
        $(".confirm").click(function(){window.location.href = "/pic/addPic/"+id;})
    }
}
*/

//更新或者新增图片信息 （添加图片或者更换图片）
function addOrUpdatePic()
{
    var id = PicInfo.id;
    var name = $('#PicName').val();
    var describes = $('#PicDescribes').val();
    var picDesc = $('#picDesc').code();
    $.ajax({
        type: "POST",
        url: "/pic/savePicMsg",
        datatype: "json",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify({
            "id": id,
            "name": name,
            "describes": describes,
            "description": picDesc,
            "fileName": FileName,
        }),
        success:function(result)
        {
            if(result.flag==1)
            {
                if(PicInfo.id=='add')
                {
                    swal("保存成功！", "", "success");
                    $(".confirm").click(function(){history.back(-1)})
                }
                else
                {
                    swal("修改成功！", "", "success");
                    $(".confirm").click(function(){window.location.href = "/pic/addPic/"+id;})
                }
            }
            else
            {
                if(PicInfo.id=='add')
                {
                    swal("保存失败！", "请重试", "error");
                }
                else
                {
                    swal("修改失败！", "请重试", "error");
                }
            }
        }
    })
}
//更新或新增图片信息 难受啊飞飞 因为需求函数要重写 我炸了呀 zhengtong bu ku
function saveOrUpdatePic() {
    if($("#PicName").val() == ""){
        toastr.error("","文章名不能为空");
        return;
    }
    var id = PicInfo.id;
    if (id == 'add')//新增
    {
        if($('#uploadPic').val()=='')
        {
            swal("上传失败！", "新增请上传图片", "error");
            return ;
        }
        else
        {
            addOrUpdatePic();
        }
    }
    else
    {
        if($('#uploadPic').val()=='')
        {
            var name = $('#PicName').val();
            var describes = $('#PicDescribes').val();
            var picDesc = $('#picDesc').code();
            $.ajax({
                type: "POST",
                url: "/pic/saveEditPic",
                datatype: "json",
                contentType: "application/json;charset=UTF-8",
                data:JSON.stringify({
                    "id": id,
                    "name": name,
                    "describes": describes,
                    "description": picDesc,
                }),
                success:function(result)
                {
                    if(result.flag==1)
                    {
                        swal("修改成功！", "", "success");
                        $(".confirm").click(function(){window.location.href = "/pic/addPic/"+id;})
                    }
                    else
                    {
                        swal("修改失败！", "请重试", "error");
                    }
                }
            })
        }
        else
        {
            addOrUpdatePic();
        }
    }

}

function uploadPicToShow()
{
    console.log("come in here to upload pic");
    var formData = new FormData();
    formData.append('file', $('#uploadPic')[0].files[0]);
    console.log(($('#uploadPic')[0].files[0].type));
    console.log(($('#uploadPic')[0].files[0].name));
    if(($('#uploadPic')[0].files[0].type).indexOf("image/")==-1)
    {
        swal("上传失败！", "请上传图片", "error");
        return ;
    }
    if($('#uploadPic')[0].files[0].size >= 1073741824)//后面的5表示1G*5=5G 上限为5G 可修改
    {
        swal("上传失败！", "上传图片过大！最大不能超过1GB", "error");
        return ;
    }
    var FileName = $('#uploadPic')[0].files[0].name;
    showProgress();
    var uploadGo = "/pic/uploadPicToShow";
    xhr = new XMLHttpRequest();
    xhr.open("post", uploadGo, true);
    xhr.onloadstart = function() {
        ot = new Date().getTime();   //设置上传开始时间
        oloaded = 0;//已上传的文件大小为0
    };
    xhr.upload.addEventListener("progress", progressFunction, false);
    xhr.addEventListener("load", uploadComplete, false);
    xhr.addEventListener("error", uploadFailed, false);
    xhr.addEventListener("abort", uploadCanceled, false);
    xhr.send(formData);
}

function progressFunction(evt) {
    var progressBar = document.getElementById("progressBar");
    var percentageDiv = document.getElementById("percentage");
    if (evt.lengthComputable) {
        var completePercent = Math.round(evt.loaded / evt.total * 100)
            + '%';
        $('#progressBar').width(completePercent);
        $('#progressBar').text(completePercent);

        var time = $("#time");
        var nt = new Date().getTime();     //获取当前时间
        var pertime = (nt-ot)/1000;        //计算出上次调用该方法时到现在的时间差，单位为s
        ot = new Date().getTime();          //重新赋值时间，用于下次计算

        var perload = evt.loaded - oloaded; //计算该分段上传的文件大小，单位b
        oloaded = evt.loaded;               //重新赋值已上传文件大小

        //上传速度计算
        var speed = perload/pertime;//单位b/s
        var bspeed = speed;
        var units = 'b/s';//单位名称
        if(speed/1024>1){
            speed = speed/1024;
            units = 'k/s';
        }
        if(speed/1024>1){
            speed = speed/1024;
            units = 'M/s';
        }
        speed = speed.toFixed(1);
        //剩余时间
        var resttime = ((evt.total-evt.loaded)/bspeed).toFixed(1);
        $("#showInfo").html(speed+units+'，剩余时间：'+resttime+'s');
    }
}

function setProgress(w) {
    $('#progressBar').width(w + '%');
    $('#progressBar').text(w + '%');
    $("#showInfo").html("");
}
function showProgress() {
    $('#progressBar').parent().show();
}
function hideProgress() {
    $('#progressBar').parent().hide();
}
//上传成功后回调
function uploadComplete(evt) {
    $.ajax({
        type: "POST",
        url: "/pic/getSessionId",
        datatype: "json",
        success:function(id)
        {
            var id = id;
            $('#checkHave').css('display', 'block');
            $('#checkHaveImg').attr('src', "/upload/img/tmp/"+id+"/"+$('#uploadPic')[0].files[0].name);
            FileName = $('#uploadPic')[0].files[0].name;
        }
    })
    //var id = window.location.href = "/pic/getSessionId";
    //$('#checkHave').css('display', 'block');
    //$('#checkHaveImg').attr('src', "upload/img/tmp"+id+"/"+$('#uploadPic')[0].files[0].name);
    /*
    if (PicInfo.id == 'add') {
        swal("上传成功！", "", "success");
        $(".confirm").click(function(){history.back(-1)})
    }
    else {
        swal("修改成功！", "", "success");
        $(".confirm").click(function(){window.location.href = "/pic/addPic/"+id;})
    }*/
}

//上传失败回调
function uploadFailed(evt) {
    if (PicInfo.id == 'add') {
        swal("上传失败！", evt.target.responseText, "error");
    }
    else {
        swal("修改失败！", evt.target.responseText, "error");
    }
}

//终止上传
function cancelUpload() {
    xhr.abort();
}

//上传取消后回调
function uploadCanceled(evt) {
    swal("上传失败！", '上传取消,上传被用户取消或者浏览器断开连接:' + evt.target.responseText, "error");
}


