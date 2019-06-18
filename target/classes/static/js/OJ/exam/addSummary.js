var selectedQueList = [];

$(document).ready(function () {
    getTest();
});
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
function getTest() {
    $.ajax({
        type: "POST",
        url: "/summaryMn/getTest",
        dataType: "json",
        success:function (result) {
            var len = result.length;
            for(var i=0; i<len; i++)
            {
                result[i].start_time = timeFormat(result[i].start_time);
                result[i].end_time = timeFormat(result[i].end_time);
            }
            var dataTable = $('#examTable');
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
                    "data" : "kind"
                },{
                    "data" : "start_time"
                },{
                    "data" : "end_time"
                }],
                "columnDefs": [{
                    "render" : function(data, type, row) {
                        var a = "";
                        a+="<i style=\"cursor:pointer\" onclick='preSelect(this, \""+row.id+"\", \""+row.name+"\")' class='fa fa-check'></i>"
                        return a;
                    },
                    "targets" :4
                }]
            });
        }
    })
}

function preSelect(radio, id, name) {
    var isSelected = false
    for (var i=0; i<selectedQueList.length; i++){
        if(id == selectedQueList[i].id){
            isSelected = true
            toastr.warning("","该考试已经被选择")
            break
        }
    }
    if (!isSelected) {
        var addQue = {};
        addQue.id = id;
        addQue.name = name;
        addQue.score = 0;
        refreshScore();
        selectedQueList.push(addQue);
        loadSelectedQuestion();
    }
}

function refreshScore () {
    var inputList = $("#selectedExamTable input")
    for (var i=0; i<inputList.length; i++){
        selectedQueList[i].score = inputList[i].value;
    }
}

function loadSelectedQuestion() {
    var dataTable = $('#selectedExamTable');
    if ($.fn.dataTable.isDataTable(dataTable)) {
        dataTable.DataTable().destroy();
    }
    dataTable.DataTable({
        "paging": false,
        "serverSide": false,
        "autoWidth" : false,
        "bSort": false,
        "scrollY": "300px",
        "scrollCollapse": "true",
        "data" : selectedQueList,
        "columns" : [{
            "data" : "name"
        }],
        "columnDefs": [{
            "render" : function(data, type, row) {
                var a = "";
                a+="<i style='cursor:pointer' onclick='deleteSelectedQue(\""+row.id+"\")' class='fa fa-close'></i>"
                return a;
            },
            "targets" :1
        }]
    });
}

function deleteSelectedQue(id) {
    for (var i=0; i<selectedQueList.length; i++){
        if(id == selectedQueList[i].id){
            selectedQueList.splice(i, 1);
        }
    }
    loadSelectedQuestion();
}

function saveSummary() {
    var postData = [];
    var len = selectedQueList.length;
    for(var i=0; i<len; i++)
    {
        postData[i] = selectedQueList[i].id;
    }
    var name = $('#summaryName').val();
    if(name=='')
    {
        toastr.warning("","名称不能为空")
    }
    else
    {
        $.ajax({
            type: "POST",
            url: "/summaryMn/addSummary",
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            data: JSON.stringify({
                    "name": name,
                    "list" : postData}
                    ),
            success: function (result) {
                if(result==1)
                {
                    swal("保存成功！","", "success");
                    $(".confirm").click(function(){history.back(-1)})
                }
                else
                {
                    swal("保存失败！","", "error");
                }
            }
        });
    }
}
