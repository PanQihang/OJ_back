
$(document).ready(function () {
 topicList();
});


function topicList() {
    $.ajax({
        type: "POST",
        url: "/blocksMn/getTopic",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var dataTable=$('#topicTable');
            if ($.fn.dataTable.isDataTable(dataTable)) {
                dataTable.DataTable().destroy();
            }
            dataTable.DataTable({
                "searching": false,
                "autoWidth": false,
                "processing": true,
                "data": result,
                "bSort": false,
                "columns": [{
                    "data": "id"
                }, {
                    "data": "name"
                }, {
                    "data": "content"
                }, {
                    "data": "admin_name"
                }, {
                    "data": "view"
                }, {
                    "data": "sum"
                }],
                "columnDefs": [{
                    "render": function (data, type, row) {
                        debugger
                        var a = "";
                        //a += "<button type='button' class='btn btn-primary' onclick='showIp(\"" + row.id + "\")' title='IP' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-eject'></i>&nbsp;IP</button>";
                        a += "<button type='button' class='btn btn-primary' onclick='showarticel(\""+row.id+"\")' title='查看所有帖子' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-search'></i>&nbsp;查看</button>"
                        if(row.admin_id==user_id) {
                            a += "<button type='button' class='btn btn-primary' onclick='PostID(\"" + row.id + "\", \""+row.name+"\", \""+row.content+"\")' title='编辑' data-toggle='modal' data-target='#myModal6' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-pencil'></i>&nbsp;编辑</button>"
                            a += "<button type='button' class='btn btn-primary' onclick='delTopic(\"" + row.id + "\")' title='删除' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-trash'></i>&nbsp;删除</button>"
                        }
                        return a;
                    },
                    "targets": 6
                }]
            });
        }
    })
}
function resetAuthInfoDialog() {
    $("#myModal5 input").val("");
    $('#dialogAuthGroup').css("display", "block");
    $("#myModal5 input").removeClass("error");
    $("#myModal5 label.error").remove()
}
function addTopic() {
    if($('#topicName').val()==""){
        toastr.error("","标题不能为空");
        return;
    }
    if($('#contentName').val()==""){
        toastr.error("","内容不能为空");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/blocksMn/addTopic",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify({
            name:$('#topicName').val(),
            content:$('#contentName').val()
        }),success(result){
            if(result == true){
                topicList();
                //关闭模态窗口
                $('#myModal5').modal('hide');
                swal("保存成功！");
            }else{
                swal("保存失败！");
            }
        }
    })
}
function delTopic(id){
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
                    url: "/blocksMn/delTopic",
                    dataType: "json",
                    contentType: "application/json;charset=UTF-8",
                    data: JSON.stringify({
                        id: id
                    }), success(result) {
                        if (result == true) {
                            topicList();
                            //关闭模态窗口
                            $('#myModal5').modal('hide');
                            swal("删除成功！", "实验已被删除", "success");
                        } else {
                            swal("删除失败！", "实验暂时不能被删除", "error");
                        }
                    }

                })
            } else {
                swal("已取消", "你取消了删除实验操作", "error");
            }
        });
}
function showarticel(id){
    window.location.href="/blocksMn/showarticelList/"+id;
}

//重置form内的标签
function resetForm() {
    $(".form-horizontal input").val("");
    $(".form-horizontal select").val("");
    getStatusInfo();
}
function PostID(id,name,content) {
    $('#PostId').val(id);
    $('#topic').val(name);
    $('#content').val(content)
}
function saveTopic(){
    if($('#topic').val()==""){
        toastr.error("","标题不能为空");
        return;
    }
    if($('#content').val()==""){
        toastr.error("","内容不能为空");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/blocksMn/saveTopic",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify({
            id:$('#PostId').val(),
            name:$('#topic').val(),
            content:$('#content').val()
        }),success(result){
            if(result == true){
                topicList();
                $('#myModal6').modal('hide');
                swal("保存成功！");
            }else{
                swal("保存失败！");
            }
        }
    })

}