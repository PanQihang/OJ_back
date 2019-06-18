$(document).ready(function () {
    getStatusInfo();
});
var stateCNList = ['已提交,请等待…', //0
    '<font color="green">Accepted</font>', //1
    'PresentationError',//2 这个没用了
    'WrongAnswer',//3 这个没用了
    '<font color="red">RuntimeError</font>',//4
    '<font color="orangered">TimeLimitExceed</font>',//5
    '<font color="red">MemoryLimitExceed</font>',//6
    '<font color="red">SystemCallError</font>',//7
    'CompileError',//8
    'SystemError',//9 		以上为数据库提供
    '超时,到查看状态处查看',//10   		以下为异常错误
    '含违规字符',//11
    '无权提交',//12
    '未知错误，联系管理员'//13
    ]
var laguateType = [
    "未知语言类型", //0
    "C++", //1
    'C',//2
    'Java',//3
    'Python'//4
]
//重置form内的标签
function resetForm() {
    $(".form-horizontal input").val("");
    $(".form-horizontal select").val("");
    getStatusInfo();
}
function getStatusInfo() {
    var dataTable = $('#StatusInfoTable');
    if ($.fn.dataTable.isDataTable(dataTable)) {
        dataTable.DataTable().destroy();
    }
    dataTable.DataTable({
        "searching" : false,
        "serverSide": true,
        "autoWidth": false,
        "processing": true,
        "ajax": {
            url: "/submitStatusMn/getSubmitStatusMaplist",
            type: "POST",
            data: {
                "problem_id" : $('#problemId').val(),
                "account" : $('#account').val(),
                "submit_state" : $('#submit_state').val()
            },
        },
        "bSort": false,
        "columns": [{
            "data": "problem_id"
        }, {
            "data": "name"
        }, {
            "data": "submit_state"
        }, {
            "data": "submit_language"
        }, {
            "data": "submit_time"
        }, {
            "data": "submit_memory"
        }, {
            "data": "submit_code_length"
        }, {
            "data": "submit_date"
        }],
        "columnDefs": [{
            "render" : function(data, type, row) {
                debugger
                var a = "";
                if(undefined != stateCNList[row.submit_state]){
                    a = stateCNList[row.submit_state]
                }
                return a;
            },
            "targets" :2
        },{
            "render" : function(data, type, row) {
                var a = "";
                if(undefined != laguateType[row.submit_language]){
                    a = laguateType[row.submit_language]
                }
                return a;
            },
            "targets" :3
        },{
            "render" : function(data, type, row) {
                var a = "<span>"+row.submit_time+" ms</span>";
                return a;
            },
            "targets" :4
        },{
            "render" : function(data, type, row) {
                var a = "<span>"+row.submit_memory+" KB</span>";
                return a;
            },
            "targets" :5
        },{
            "render" : function(data, type, row) {
                var a = "<span>"+row.submit_code_length+" b</span>";
                return a;
            },
            "targets" :6
        }]
    });

}

// function formatTime(time) {
//     time = time.split(".")[0];
//     time = time.replace("T", " ")
//     return time;
// }
//function add0(m){return m<10?'0'+m:m }
function format(time)
{
    return new Date(parseInt(time) * 1000).toLocaleString().replace(/:\d{1,2}$/,' ');
}



