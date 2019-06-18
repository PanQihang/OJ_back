$(document).ready(function () {
    getSummary();
});

function resetForm() {
    $(".form-horizontal input").val("");
    $(".form-horizontal select").val("");
    getSummary();
}

function timeFormat(time) {
    var d = new Date(time);

    var year = d.getFullYear();       //年
    var month = d.getMonth() + 1;     //月
    var day = d.getDate();            //日

    var hh = d.getHours();            //时
    var mm = d.getMinutes();          //分
    var ss = d.getSeconds();           //秒

    var clock = year + "/";

    if (month < 10)
        clock += "0";

    clock += month + "/";

    if (day < 10)
        clock += "0";

    clock += day + " ";

    if (hh < 10)
        clock += "0";

    clock += hh + ":";
    if (mm < 10) clock += '0';
    clock += mm + ":";

    if (ss < 10) clock += '0';
    clock += ss;
    return (clock);
}
function getSummary(){
    $.ajax({
        type: "POST",
        url: "/summaryMn/getSummary",
        dataType: "json",
        success:function (result) {
            var len = result.length;
            for(var i=0; i<len; i++)
            {
                result[i].time = timeFormat(result[i].time);
            }
            var dataTable = $('#summaryTable');
            if ($.fn.dataTable.isDataTable(dataTable)) {
                dataTable.DataTable().destroy();
            }
            dataTable.DataTable({
                "serverSide": false,
                "autoWidth" : false,
                "bSort": false,
                "data" : result,
                "columns" : [{
                    "data" : "name"
                },{
                    "data" : "time"
                }],
                "columnDefs": [{
                    "render" : function(data, type, row) {
                        var a = "";
                        a += "<button type='button' class='btn btn-primary' onclick='show(\""+row.id+"\")'  title='查看报表' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-pencil-square-o'></i>&nbsp;查看</button>"
                        a += "<button type='button' class='btn btn-primary' onclick='deleteSummary(\""+row.id+"\")' title='删除报表' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-user-times'></i>&nbsp;删除</button>"
                        return a;
                    },
                    "targets" :2
                }]
            });
        }
    })
}

function deleteSummary(id)
{
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
                    url: "/summaryMn/deleteSummary",
                    dataType: "json",
                    data:{
                        "id" : id
                    },
                    success:function (result){
                        if(result == 1){
                            getSummary();
                            swal("删除成功！", "成绩报表已被删除", "success");
                        }else{
                            swal("删除失败！", "成绩报表暂时不能被删除", "error");
                        }

                    }
                })
            }else {
                swal("已取消", "你取消了删除报表操作", "error");
            }
        });
}

function show(id) {
    window.location.href="/summaryMn/showSummary/"+id;
}

function addSummary(){
    window.location.href = "/summaryMn/addSummaryHTML/";
}


