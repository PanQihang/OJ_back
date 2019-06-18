$(document).ready(function () {
    getComInfo();
});
function getComInfo() {
    console.log($('#competitionName').val());
    $.ajax({
        type: "POST",
        url: "/competition/getcompetitionInfo",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify({
            "experName" : $('#competitionName').val()
        }),
        success:function(result) {
            var dataTable = $('#competitionTable');
            if ($.fn.dataTable.isDataTable(dataTable)) {
                dataTable.DataTable().destroy();
            }
            dataTable.DataTable({
                "searching": false,
                "autoWidth": false,
                "processing": true,
                "data": result,
                "bSort": false,
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
                        debugger
                        var a = "";
                        a += "<button type='button' class='btn btn-primary' onclick='openAddCom(\"" + row.id + "\")' title='编辑' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-pencil'></i>&nbsp;编辑</button>"
                        a += "<button type='button' class='btn btn-primary' onclick='UpdateTheTestResult(\"" + row.id + "\")' title='判分' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-refresh'></i>&nbsp;判分</button>"

                        a += "<button type='button' class='btn btn-primary' onclick='compScore(\""+row.id+"\")' title='成绩' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-search'></i>&nbsp;成绩</button>"
                        //a += "<button type='button' class='btn btn-primary' onclick='similarityUser(\""+row.id+"\")' title='相似' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-eye'></i>&nbsp;相似</button>"

                        a += "<button type='button' class='btn btn-primary' onclick='deleteComp(\"" + row.id + "\")' title='删除' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-trash'></i>&nbsp;删除</button>"

                        return a;
                    },
                    "targets" :4
                }]
            });
        }
    })


}
function openAddCom(id) {
    window.location.href = "/competition/addCom/"+id;
}
function deleteComp(id) {
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
                    url: "/competition/compDelete",
                    dataType: "json",
                    data:{
                        "id" : id
                    },
                    success:function (result){
                        if(result.flag == "1"){
                            getComInfo();
                            swal("删除成功！", "实验已被删除", "success");
                        }else{
                            swal("删除失败！", "实验暂时不能被删除", "error");
                        }

                    }
                })
            }else {
                swal("已取消", "你取消了删除实验操作", "error");
            }
        });
}
function compScore(t){
    window.location.href="/competition/showcompScore/"+t;;
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