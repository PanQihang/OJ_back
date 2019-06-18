$(document).ready(function () {
    //alert("hahha")
    getTestInfo();
});
//重置form内的标签
function resetForm() {
    $(".form-horizontal input").val("");
    $(".form-horizontal select").val("");
    getTestInfo();
}
function getTestInfo() {
    var dataTable = $('#testInfoTable');
    if ($.fn.dataTable.isDataTable(dataTable)) {
        dataTable.DataTable().destroy();
    }
    dataTable.DataTable({
        "serverSide": false,
        "autoWidth" : false,
        "bSort": false,
        "ajax": {
            type: "POST",
            url: "/testMn/getTestInfo",
            dataType: "json",
            data:{
                "testName" : $('#testName').val()
            },
        },
        "columns" : [{
            "data" : "name"
        },{
            "data" : "kind"
        },{
            "data" :"start_time"
        },{
            "data" : "end_time"
        },],
        "columnDefs": [{
            "render" : function(data, type, row) {
                var a = "";
                a += "<button type='button' class='btn btn-primary' onclick='showIp(\""+row.id+"\")' title='IP' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-eject'></i>&nbsp;IP</button>"
                if(row.admin_id==user_id) {
                    a += "<button type='button' class='btn btn-primary' onclick='openAddTest(\"" + row.id + "\")' title='编辑' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-pencil'></i>&nbsp;编辑</button>"
                }
                a += "<button type='button' class='btn btn-primary' onclick='UpdateTheTestResult(\""+row.id+"\")' title='判分' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-refresh'></i>&nbsp;判分</button>"
                a += "<button type='button' class='btn btn-primary' onclick='showTestScore(\""+row.id+"\")' title='成绩' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-search'></i>&nbsp;成绩</button>"
                a += "<button type='button' class='btn btn-primary' onclick='similarityUser(\""+row.id+"\")' title='相似' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-eye'></i>&nbsp;相似</button>"
                a += "<button type='button' class='btn btn-primary' onclick='deleteTest(\""+row.id+"\")' title='删除' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-trash'></i>&nbsp;删除</button>"
                a += "<button type='button' class='btn btn-primary' onclick='copyTest(\""+row.id+"\")' title='复制' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-copy'></i>&nbsp;复制</button>"
                return a;
            },
            "targets" :4
        }]
    });
}

function UpdateTheTestResult(t){
    $.ajax({
        type: "POST",
        url: "/testMn/UpdateTheTestResult",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify({
           "testId" : t
        }),
        success:function (result) {
            if(result.result == "succeed"){
                swal("判分成功！", "考试成绩已更新", "success");
            }else{
                swal("判分失败！", "考试成绩未更新", "error");
            }
        }
    });
}
function showTestScore(t){
    window.location.href="/testMn/showTestScore/"+t;
}
function openAddTest(id) {
    window.location.href = "/testMn/addTest/"+id;
}
function copyTest(id){
    window.location.href="/testMn/copyTest/"+id;
}
function similarityUser(id){
    window.location.href="/testMn/similarityUser/"+id;
}
function deleteTest(id) {
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
                    url: "/testMn/testDelete",
                    dataType: "json",
                    data:{
                        "id" : id
                    },
                    success:function (result){
                        if(result.flag == "1"){
                            getTestInfo();
                            swal("删除成功！", "考试已被删除", "success");
                        }else{
                            swal("删除失败！", "考试暂时不能被删除", "error");
                        }

                    }
                })
            }else {
                swal("已取消", "你取消了删除考试操作", "error");
            }
        });
}
function showIp(tid) {
    //console.log(tid);
    window.location.href = "/testMn/showIp/"+tid;
}