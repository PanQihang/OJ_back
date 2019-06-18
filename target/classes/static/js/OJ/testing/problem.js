$(document).ready(function () {
    indexSubjectSelect();
    queryProblemsInfo();
});

var icon = "<i class='fa fa-times-circle'></i>";
var caseResult = -1;

//重置form内的标签
function resetForm() {
    $(".form-horizontal input").val("");
    $(".form-horizontal select").val("").trigger("change");
    queryProblemsInfo();
}

function queryProblemsInfo() {
    $.ajax({
        type: "POST",
        url: "/problemsMn/getProblemsMapList",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify({
            "id" : $('#problemId').val(),
            "name" : $('#problemName').val(),
            "subject" : $('#problemSubject').val()
        }),
        success:function (result) {
            var dataTable = $('#problemInfoTable');
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
                    "data" : "subject",
                    "defaultContent" : "其他"
                },{
                    "data" : "public",
                    "defaultContent" : "否"
                }, {
                    "data": "AC_rate",
                    "defaultContent" : "0"
                }, {
                    "data": "AC_number",
                    "defaultContent" : "0"
                }, {
                    "data": "submit_number",
                    "defaultContent" : "0"
                }],
                "columnDefs": [{
                    "render" : function(data, type, row) {
                        var a = "";
                        a += "<button type='button' class='btn btn-primary' onclick='setId(\""+row.id+"\")'data-toggle='modal' data-target='#myModal5' href='problemDetails' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-list'></i>&nbsp;详情</button>"
                        a += "<button type='button' class='btn btn-primary' onclick='setCid(\""+row.id+"\")' data-toggle='modal' data-target='#analyze' title='分析' href='analyze' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-area-chart'></i>&nbsp;分析</button>"
                        a += "<button type='button' class='btn btn-primary' onclick='toEditProblem(\""+row.id+"\")' title='编辑题目' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-edit'></i>&nbsp;编辑</button>"
                        a += "<button type='button' class='btn btn-primary' onclick='deleteProblem(\""+row.id+"\")' data-toggle='dropdown' title='删除'  style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-trash'></i>&nbsp;删除</button>"
                        return a;
                    },
                    "targets" :7
                }]
            });
        }
    })
}

function deleteProblem(id) {

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
                    url: "/problemsMn/problemDelete",
                    async: false,
                    dataType: "json",
                    data:{
                        "id" : id
                    },
                    success:function (result){
                        deleteProblemTest(id);
                        if(result.flag == "1" && caseResult == 1){
                            queryProblemsInfo();
                            swal("删除成功！", "题目已被删除", "success");
                        }else{
                            swal("删除失败！", "题目暂时不能被删除", "error");
                        }
                        caseResult = -1;
                    }
                })
            }else {
                swal("已取消", "你取消了删除题目操作", "error");
            }
        });
}

//删除题目的同时删除其测试用例
function deleteProblemTest(id){
    $.ajax({
        type: "POST",
        url: "/caseMn/caseDeleteByProblem",
        async: false,
        dataType: "json",
        data:{
            "id" : id
        },
        success:function (result){
            caseResult = 1;
        }
    })

}

//跳转题目编辑详情模态窗口
function toEditProblem(id) {
    window.location.href="/problemsMn/editProblem?id=" + id;
}


//设置题目详情页的id，用于获取数据
function setCid(id){
    $('#cid').val(id);

}

//设置题目详情页的id，用于获取数据
function setId(id){
    $('#id').val(id);

}

//初始化题目类型下拉框
function indexSubjectSelect() {
    SubjectTree('problemSubject');

}
