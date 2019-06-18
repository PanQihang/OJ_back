$(document).ready(function () {
    queryPicInfo();
    //indexAdminSelect();
});
var icon = "<i class='fa fa-times-circle'></i>";
//重置form内的标签
function resetForm() {
    $(".form-horizontal input").val("");
    $(".form-horizontal select").val("");
    queryPicInfo();
}
function resetPicInfoDialog() {
    $("#myModal5 input").val("");
    $("#myModal5 select").val("");
    $("#myModal5 input").removeClass("error");
    $("#myModal5 select").removeClass("error");
    $("#myModal5 label.error").remove();
}
function queryPicInfo() {
    //alert("come in here");
    $.ajax({
        type: "POST",
        url: "/pic/getPicMapList",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify({
            //"name" : $('#fileName').val(),
            //"uploader_id" : $('#fileUploader').val(),
        }),
        success:function (result) {
            var ans = JSON.stringify(result);
            //alert(ans);
            for(var i = 0; i < result.length; i++)
            {
                var date = new Date(result[i].upload_time*1000);
                //alert(date);
                result[i].upload_time = date.getFullYear() + '-' + (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes();
                var dateother = new Date(result[i].update_time*1000);
                result[i].update_time = dateother.getFullYear() + '-' + (dateother.getMonth()+1 < 10 ? '0'+(dateother.getMonth()+1) : dateother.getMonth()+1) + '-' + dateother.getDate() + ' ' + dateother.getHours() + ':' + dateother.getMinutes();
            }
            var dataTable = $('#picInfoTable');
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
                    "data" : "describes"
                },{
                    "data" : "upload_time"
                },{
                    "data" : "update_time"
                }],
                "columnDefs": [{
                    "render" : function(data, type, row) {
                        var a = "";
                        a += "<button type='button' class='btn btn-primary' onclick='openAddPic(\""+row.id+"\")' data-toggle='modal' data-target='#Edit' title='编辑' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-pencil-square-o'></i>&nbsp;编辑</button>"
                        a += "<button type='button' class='btn btn-primary' onclick='deletePic(\""+row.id+"\")' data-toggle='modal' data-target='dropdown' title='删除' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-pencil-square-o'></i>&nbsp;删除</button>"
                        return a;
                    },
                    "targets" :6
                }]
            });
        }
    })
}

//删除文件
function deletePic(id) {
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
                    url: "/pic/picDelete",
                    // contentType: "application/json;charset=UTF-8",
                    dataType: "json",
                    data:{
                        "id" : id
                    },
                    success:function (result){
                        if(result.flag == "1"){
                            queryPicInfo();
                            swal("删除成功！", "此文章已被删除", "success");
                        }else{
                            swal("删除失败！", "文章暂时不能被删除", "error");
                        }

                    }
                })
            }else {
                swal("已取消", "你取消了删除文章操作", "error");
            }
        });
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

function openAddPic(id) {
    window.location.href = "/pic/addPic/"+id;
}
function openAdminPic(id){
    window.location.href = "/pic/adminPic";
}