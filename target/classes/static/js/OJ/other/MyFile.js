$(document).ready(function () {
    queryMyFileInfo();
    indexAdminSelect();
});
var icon = "<i class='fa fa-times-circle'></i>";
//重置form内的标签
function resetForm() {
    $(".form-horizontal input").val("");
    $(".form-horizontal select").val("");
    queryMyFileInfo();
}
function resetMyFileInfoDialog() {
    $("#myModal5 input").val("");
    $("#myModal5 select").val("");
    $("#myModal5 input").removeClass("error");
    $("#myModal5 select").removeClass("error");
    $("#myModal5 label.error").remove();
}
function queryMyFileInfo() {
    //alert("come in here");
    $.ajax({
        type: "POST",
        url: "/myFile/getFileMapList",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify({
            "name" : $('#fileName').val(),
            "uploader_id" : $('#fileUploader').val(),
        }),
        success:function (result) {
            //var ans = JSON.stringify(result);
            //alert(ans);
            for(var i = 0; i < result.length; i++)
            {
                var date = new Date(result[i].upload_time*1000);
                result[i].upload_time = date.getFullYear() + '-' + (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-' + date.getDate();
                var fileSize = getSize(result[i].size);
                result[i].size = fileSize;
                if(result[i].flag==0)result[i].flag = "教师文件";
                else result[i].flag = "公共文件";
            }
            var dataTable = $('#fileInfoTable');
            if ($.fn.dataTable.isDataTable(dataTable)) {
                dataTable.DataTable().destroy();
            }
            dataTable.DataTable({
                "serverSide": false,
                "autoWidth" : false,
                "bSort": false,
                "data" : result,
                "columns" : [{
                    "data" : "id"
                },{
                    "data" : "name"
                },{
                    "data" : "uploader_name"
                },{
                    "data" : "upload_time"
                },{
                    "data" : "size"
                },{
                    "data" : "flag"
                }],
                "columnDefs": [{
                    "render" : function(data, type, row) {
                        var a = "";
                        a += "<button type='button' class='btn btn-primary' onclick='showEditFile(\""+row.id+"\")' data-toggle='modal' data-target='#Edit' title='编辑' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-pencil-square-o'></i>&nbsp;编辑</button>"
                        a += "<button type='button' class='btn btn-primary' onclick='downloadFile(\""+row.id+"\")' data-toggle='modal' title='下载' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-list'></i>&nbsp;下载</button>"
                        a += "<button type='button' class='btn btn-primary' onclick='deleteFile(\""+row.id+"\")' data-toggle='modal' data-target='dropdown' title='删除' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-pencil-square-o'></i>&nbsp;删除</button>"
                        return a;
                    },
                    "targets" :6
                }]
            });
        }
    })
}

function showEditFile(id)
{
    resetMyFileInfoDialog();
    $("#Title").html("状态编辑");
    $("#dialogFileName").attr("readonly",true);
    $("#dialogFileId").val(id);
    //alert(id);
    $.ajax({
        type: "POST",
        url: "/myFile/fileFlag",
        dataType: "json",
        data:{
            "id" : id
        },
        success:function (result){
            //alert(result[0].name);
            //alert(result[0].flag);
            $("#dialogFileName").val(result[0].name);
            $("#fileFlag").empty();
            var roleSelectInfo = "";
            roleSelectInfo += "<option value='0'>"+"教师文件"+"</option>"
            roleSelectInfo += "<option value='1'>"+"公共文件"+"</option>"
            $("#fileFlag").append(roleSelectInfo);
            if(result[0].flag == 0)
            {
                $("#fileFlag").val(0);
            }
            else
            {
                $("#fileFlag").val(1);
            }
        }
    })
}

function saveFileFlag()
{
    var id = $("#dialogFileId").val();
    var flag = $("#fileFlag").val();
    $.ajax({
        type: "POST",
        url: "/myFile/saveFileFlag",
        dataType: "json",
        data:{
            "id" : id,
            "flag" : flag,
        },
        success:function (result){
            if(result.flag == 1){
                queryMyFileInfo();
                //关闭模态窗口
                $('#Edit').modal('hide');
                swal("保存成功！","", "success");
            }else{
                swal("保存失败！", result.message, "error");
            }
        }
    })
}

function showFileToUpload() {
    resetMyFileInfoDialog();
    $("#dialogTitle").html("上传");
    setProgress(0);

}

var ot;//上传开始时间
var oloaded;//已上传文件大小
var xhr;

//上传文件类
function uploadFile(id) {
    //alert($('#uploadFile').val());
    if($('#uploadFile').val()=='')
    {
        swal('未选择文件','请选择文件');
        return ;
    }
    var flag = $('#dialogFlag').val();
    if(flag == ''){
        swal('未选择状态','请先选择状态');
        return ;
    }
    var formData = new FormData();
    formData.append('file', $('#uploadFile')[0].files[0]);
    var length = getSize($('#uploadFile')[0].files[0].size);
    if($('#uploadFile')[0].files[0].size >= 1073741824*5)//后面的5表示1G*5=5G 上限为5G 可修改
    {
        swal("上传失败！", "上传文件过大！最大不能超过1GB", "error");
        return ;
    }

    var FileName = $('#uploadFile')[0].files[0].name;
    $.ajax({
        type: "POST",
        url: "/myFile/checkFileName",
        dataType: "json",
        data: {
            name: FileName,
        },
        success:function (result){
            //if(result.flag == "1"){
                showProgress();
                var uploadGo = "/myFile/uploadMyFile?flag="+flag;
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
                queryMyFileInfo();
                //swal("上传成功！", "", "success");
            //}else{
                //swal("上传失败！", result.message, "error");
            //}
        }
    })

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


//上传者列表
function indexAdminSelect() {
    //alert("come in select");
    $.ajax({
        type: "POST",
        url: "/myFile/getAdminSelectInfo",
        dataType: "json",
        success:function (result){
            //alert(JSON.stringify(result));
            var adminSelectInfo = "";
            for (var i=0; i<result.length; i++){
                adminSelectInfo += "<option value='"+result[i].id+"'>"+result[i].name+"</option>"
            }
            $("#fileUploader").append(adminSelectInfo);
        }
    })
}
//删除文件
function deleteFile(id) {
    swal({
            title: "确认删除?",
            text: "",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "确认",
            cancelButtonText: "取消",
            closeOnConfirm: false,
            closeOnCancel: false
        },
        function (isConfirm) {
            if (isConfirm) {
                $.ajax({
                    type: "POST",
                    url: "/myFile/fileDelete",
                    // contentType: "application/json;charset=UTF-8",
                    dataType: "json",
                    data:{
                        "id" : id
                    },
                    success:function (result){
                        if(result.flag == "1"){
                            queryMyFileInfo();
                            swal("删除成功！", "文件已被删除", "success");
                        }else{
                            swal("删除失败！", "文件暂时不能被删除", "error");
                        }

                    }
                })
            }else {
                swal("已取消", "你取消了删除文件操作", "error");
            }
        });
}

function downloadFile(id) {
    $.ajax({
        type: "POST",
        url: "/myFile/checkFileExistence",
        dataType: "json",
        data: {
            id: id,
        },
        success:function (result){
            if(result.flag == 1)
            {
                window.location.href="/myFile/downloadFile?id="+id;
            }
            else
            {
                swal("下载失败！", "文件不存在，请联系教师或管理员", "error");
            }
        }
    })
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

function getSize(size) {
    var fileSize = '0KB';
    if (size > 1024 * 1024) {
        fileSize = (Math.round(size / (1024 * 1024))).toString() + 'MB';
    } else {
        fileSize = (Math.round(size / 1024)).toString() + 'KB';
    }
    return fileSize;
}

//上传成功后回调
function uploadComplete(evt) {
    $('#myModal5').modal('hide');
    resetForm();
    swal("上传成功！","", "success");
    setProgress(0);
};

//上传失败回调
function uploadFailed(evt) {
    swal("上传失败！", evt.target.responseText, "error");
}

//终止上传
function cancelUpload() {
    xhr.abort();
}

//上传取消后回调
function uploadCanceled(evt) {
    swal("上传失败！", '上传取消,上传被用户取消或者浏览器断开连接:' + evt.target.responseText, "error");
}