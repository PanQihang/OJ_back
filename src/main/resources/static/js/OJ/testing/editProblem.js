toastr.options = {
    closeButton: false,
    debug: false,
    progressBar: true,
    positionClass: "toast-top-center ",
    onclick: null,
    showDuration: "300",
    hideDuration: "1000",
    timeOut: "2000",
    extendedTimeOut: "1000",
    showEasing: "swing",
    hideEasing: "linear",
    showMethod: "fadeIn",
    hideMethod: "fadeOut"
};
$(document).ready(function () {
    loadSummernote();
    indexSubjectSelect();
    editProblem();

});

var rank = 1;

//题目编辑详情
function editProblem(){
    var id = getUrlParam("id");
    if(id != "0" ){
        $("#dialogTitle").html("编辑题目")
        // $("#dialogProblemId").val(id)
        $.ajax({
            type: "POST",
            url: "/problemsMn/getProblemDetails",
            dataType: "json",
            async:false,
            data:{
                "id" : id
            },success:function (result){
                $("#dialogProblemId").id,
                $("#dialogProblemName").val(result[0].name)
                if(result[0].subjectid == '0')
                    $('#problemSubject').html('<option value="' + 40 + '">' + "其他" + '</option>').trigger("change");
                else
                    $('#problemSubject').html('<option value="' + result[0].subjectid + '">' + result[0].subject + '</option>').trigger("change");
                if(result[0].public == "on"){
                    $("#dialogProblemPublicOn").attr("checked", true);
                }
                else {
                    $("#dialogProblemPublic").attr("checked", true);
                }
                $("#experDesc").code(result[0].description)
                $("#dialogProblemInType").val(delHtml(result[0].intype))
                $("#dialogProblemOutType").val(delHtml(result[0].outtype))
                $("#dialogProblemInSample").val(delHtml(result[0].insample))
                $("#dialogProblemOutSample").val(delHtml(result[0].outsample))
                $("#dialogProblemMaxMemory").val(result[0].maxmemory)
                $("#dialogProblemMaxTime").val(result[0].maxtime)
                rank = dbRankToShowRank(result[0].rank);
                $("#dialogProblemRank").ionRangeSlider({
                    min: 1,
                    max: 5,
                    from: rank,
                    type: 'single',
                    step: 1,
                    prettify: false,
                    hasGrid: true,
                    onFinish: function(obj){        // function-callback, is called once, after slider finished it's work
                        rank = obj.fromNumber;
                    }
                });
                $("#dialogCodeLanguage").val("GCC")
                $("#dialogProblemCode").val(result[0].exam_code);
                if(result[0].is_show_exepl == "")
                    $("#dialogCodePublic").attr("checked", false);
                else
                    $("#dialogCodePublic").attr("checked", true);
            }
        })
        getProblemTestData(id)
    }else{
        $("#dialogTitle").html("新建题目");
        $("#dialogProblemRank").ionRangeSlider({
            min: 1,
            max: 5,
            from: 1,
            type: 'single',
            step: 1,
            prettify: false,
            hasGrid: true,
            onFinish: function(obj){        // function-callback, is called once, after slider finished it's work
                rank = obj.fromNumber;
            }
        });
        $("#dialogProblemTestId").attr("value", 1);
    }

}

function getProblemTestData(id){
    $.ajax({
        type: "POST",
        url: "/caseMn/getCase",
        dataType: "json",
        async:false,
        data:{
            "id" : id
        },success:function (result){
            var i = 1;
            for( ; i < result.length; i++){
                newTest();
            }
            var arr_in=$("textarea[name='dialogProblemInTest']");
            var arr_out=$("textarea[name='dialogProblemOutTest']");
            var arr_id=$("input[name='dialogProblemTestId']");
            for(i=0;i<result.length;i++){
               $(arr_in[i]).val(result[i].in_put);
               $(arr_out[i]).val(result[i].out_put);
               $(arr_id[i]).attr("value",result[i].id)
            }
        }
    })
}

//初始化题目详情编辑器
function loadSummernote() {
    $("#experDesc").summernote({
        height: 200,
        minHeight: 200,
        maxHeight: 200,
        lang: 'zh-CN',
        toolbar: [
            ['style', ['style']],
            ['font', ['bold', 'underline', 'clear']],
            ['fontname', ['fontname']],
            ['color', ['color']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['table', ['table']],
            ['view', ['fullscreen', 'codeview']]
        ]

    })
}


//添加新的测试用例
function newTest(){
    var id = getUrlParam("id") == 0 ? 1 : 0;
    var newTest = '<div class="row">\n' +
        '<input type="hidden" name="dialogProblemTestId" id="dialogProblemTestId" value=' + id + '>' +
        '                                    <div class="col-sm-2"></div>\n' +
        '                                    <div class="col-sm-4">\n' +
        '                                         <textarea id="dialogProblemInTest" name="dialogProblemInTest" placeholder="输入用例" class="form-control" rows="6" style="resize:none;"></textarea>\n' +
        '                                    </div>\n' +
        '                                    <div class="col-sm-4">\n' +
        '                                        <textarea id="dialogProblemOutTest" name="dialogProblemOutTest" placeholder="输出用例" class="form-control" rows="6" style="resize:none;"></textarea>\n' +
        '                                    </div>\n' +
        '                                     <input type="button" class="btn btn-primary" onclick="deleteTest(this)" value="删除">\n' +
        '                                </div>';


  $("#parentTest").append(newTest);
}

//删除测试用例
function deleteTest(Obj) {
     var id =  $(Obj).siblings("input").attr("value")
      if(id != 0)
         deleteProblemTestCase(id,Obj)
    if(id == 0)
       Obj.parentNode.parentNode.removeChild(Obj.parentNode);
}

//从数据库删除测试用例
function deleteProblemTestCase(id,Obj){
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
                    url: "/caseMn/caseDelete",
                    dataType: "json",
                    async:false,
                    data:{
                        "id" : id
                    },
                    success:function (result){
                        if(result.flag == "1"){
                            swal("删除成功！", "测试数据已被删除", "success");
                            Obj.parentNode.parentNode.removeChild(Obj.parentNode);
                        }else{
                            swal("删除失败！", "测试数据暂时不能被删除", "error");
                        }
                    }
                })
            }else {
                swal("已取消", "你取消了删除操作", "error");
            }
        });
}

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

//编辑题目后进行存储
function saveOrUpdateProblem() {
    var id = getUrlParam("id");
    var timestamp = Date.parse( new Date() ).toString();
    timestamp = timestamp.substr(0,10);
    if($("#dialogProblemName").val() == ""){
        toastr.error("","实验名称不能为空");
        debugger;
        return;
    }
    if($("#dialogProblemName").val() == ""){

        toastr.error("","题目名称不能为空");
        return;
    }else if($("#dialogProblemName").val().length >= 40){
        toastr.error("","题目名称最长为40");
        return;
    }else if($("#problemSubject").val() <= 0){
        toastr.error("","请选择题目类别");
        return;
    }else if($("#dialogProblemMaxTime").val() == ""){
        toastr.error("","最大时间不能为空");
        return;
    }else if($("#dialogProblemMaxMemory").val() == ""){
        toastr.error("","最大内存不能为空");
        return;
    }else if(!$("input[name='optionsRadios']:checked").val()){
        toastr.error("","请选择是否公开该题目");
        return;
    }

        $.ajax({
            type: "POST",
            url: "/problemsMn/saveOrUpdateProblem",
            dataType: "json",
            async: false,
            contentType: "application/json;charset=UTF-8",
            data:JSON.stringify({
                "id" : id,
                "name" : $("#dialogProblemName").val(),
                "subjectid" : $("#problemSubject").val(),
                "isPublic" : $("input[name='optionsRadios']:checked").val() == "on" ? "on" : "",
                "description" : $("#experDesc").code(),
                "intype" :$("#dialogProblemInType").val(),
                "outtype": $("#dialogProblemOutType").val(),
                "insample": $("#dialogProblemInSample").val(),
                "outsample": $("#dialogProblemOutSample").val(),
                "maxmemory":  $("#dialogProblemMaxMemory").val(),
                "maxtime" : $("#dialogProblemMaxTime").val(),
                "rank" : showRankToDbRank(rank),
                "time" : timestamp,
                "author" : $("#user").val(),
                "kind" : 1,
                "test_data_id" :0,
                "submit_id" : 1,
                "exam_code" : $("#dialogProblemCode").val(),
                "is_show_exepl" : $("#dialogCodePublic").is(':checked') ? "on" : ""

            }),
            success:function (result){
                if(result.flag == 1 ){
                    saveOrUpdateTestData();
                }else{
                    swal("保存失败！", result.message, "error");
                }
            }
        });
}

function saveOrUpdateTestData(){
    var problem_id = getUrlParam("id");
    var timestamp = Date.parse( new Date() ).toString();
    timestamp = timestamp.substr(0,10);
    var cases = [];
    var arr_in=$("textarea[name='dialogProblemInTest']");
    var arr_out=$("textarea[name='dialogProblemOutTest']");
    var arr_id=$("input[name='dialogProblemTestId']");
    for(var i=0; i<arr_id.length; i++){
        var c = new Object();
        c.problem_id = problem_id
        c.id = $(arr_id[i]).attr("value")
        c.in_put= $(arr_in[i]).val()
        c.out_put = $(arr_out[i]).val()
        c.description = " "
        c.time =  timestamp
        cases.push(c);
    }
    $.ajax({
        type: "POST",
        url: "/caseMn/saveOrUpdateCase",
        dataType: "json",
        async: false,
        data: JSON.stringify(cases)  ,
        traditional:true,
        contentType: "application/json;charset=UTF-8",
        success:function (result){
            if(result.flag == 1 ){
                swal({
                    title: "保存成功！",
                    type: "success",
                    confirmButtonText:"确认",
                },function (isConfirm) {
                    if(isConfirm)
                        window.location.href="/problemsMn/";
                })
            }else{
                swal("保存失败！", result.message, "error");
            }
        }
    });
}

//初始化题目类型下拉框
function indexSubjectSelect() {
    SubjectTree('problemSubject');

}

//过滤文本中的html标签
function delHtml(msg) {

        var msg = msg.replace(/<\/?[^>]*>/g, ''); //去除HTML Tag
        // msg = msg.replace(/[|]*\n/, '') //去除行尾空格
        msg = msg.replace(/&npsp;/ig, ' '); //去掉npsp
        return msg;

}

//为方便用户操作，对rank的值进行换算后存入数据库
//数据库：81 -- 100  显示：1
//数据库：61 -- 80  显示：2
//数据库：41 -- 60  显示：3
//数据库：11 -- 40  显示：4
//数据库：0 -- 10  显示：5
function showRankToDbRank(rank){
    if(rank == 5){
        rank = 5;
    } else if(rank == 4){
        rank = 25;
    } else if(rank == 3){
        rank = 45;
    } else if(rank == 2){
        rank = 65;
    } else if(rank == 1){
        rank = 85;
    }
    return rank;
}

function dbRankToShowRank(rank){
    if(rank >= 0 && rank <= 10){
        rank = 5;
    } else if(rank >= 11 && rank <= 40){
        rank = 4;
    } else if(rank >= 41 && rank <= 60){
        rank = 3;
    } else if(rank >= 61 && rank <= 80){
        rank = 2;
    } else if(rank >= 81 && rank <= 100){
        rank = 1;
    }
    return rank;
}

