
$(function() {
    console.log(ipInfo.tid);
    queryIpInfo();
});

function resetIpInfoDialog() {
    $("#myModal5 input").val("");
    $("#myModal5 textarea").val("");
    $("#myModal5 input").removeClass("error");
    $("#myModal5 select").removeClass("error");
    $("#myModal5 textarea").removeClass("error");
    $("#myModal5 label.error").remove()
}
function queryIpInfo() {
    $.ajax({
        type: "POST",
        url: "/experimentMn/getIpInfoList",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify({
            "tid":ipInfo.tid,
            "account" : $('#userAccount').val(),
            "name":$('#userName').val(),
        }),
        success:function (result) {
            $.each(result, function (index, value) {

                value.submit_date=format(value.submit_date);
            })
            var dataTable = $('#ipInfoTable');
            if ($.fn.dataTable.isDataTable(dataTable)) {
                dataTable.DataTable().destroy();
            }
            dataTable.DataTable({
                "serverSide": false,
                "autoWidth" : false,
                "bSort": false,
                "data" : result,
                "columns" : [{
                    "data" : "account"
                },{
                    "data" : "name"
                },{
                    "data" : "class"
                },{
                    "data" : "sip"
                },{
                    "data":"submit_date"
                }],
                // "columnDefs": [{
                //     "render" : function(data, type, row) {
                //         var a = "";
                //        // a += "<button type='button' class='btn btn-primary' onclick='showEditIP(\""+row.id+"\")' data-toggle='modal' data-target='#myModal5' title='编辑IP' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-pencil-square-o'></i>&nbsp;编辑</button>"
                //         a += "<button type='button' class='btn btn-primary' onclick='deleteIP(\""+row.id+"\")' title='删除IP' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-user-times'></i>&nbsp;删除</button>"
                //         return a;
                //     },
                //     "targets" :4
                // }]
            });
        }
    })
}
//重置输入框
function resetForm() {
    $(".form-horizontal input").val("");
    queryIpInfo();
}

function format(time) {
    time = time.split(".")[0];
    time = time.replace("T", " ")
    return time;
}