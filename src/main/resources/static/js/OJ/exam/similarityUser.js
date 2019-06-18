$(document).ready(function () {

    getSimilarityList();

});

function testFunction() {
    var JData = [
        {
            result: 90,
            Saccount: '2016081523',
            Sname: '潘通',
            SclassName: '大佬165',
            Taccount: '2016081523',
            Tname: '郑祈航',
            TclassName: '大佬166',

        },
    ]
    loadResultList(JData);
    // JData={name:'测试样例002'}
    // loadResultTitle(JData);
}

//加载考试名称
// function loadResultTitle(t) {
//     $('#resultTitle').text(t.name+"-判重结果");
// }
//获取相似结果列表
function getSimilarityList(){
    $.ajax({
        type: "POST",
        url: "/testMn/getSimilarityResult",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify({
            "testId" : testInfo.tid
        }),
        success:function (result) {
            loadResultList(result);
        }
    });
}
//加载结果列表
function loadResultList(t) {
    var dataTable = $('#resultTable');
    if ($.fn.dataTable.isDataTable(dataTable)) {
        dataTable.DataTable().destroy();
    }
    dataTable.DataTable({
        // "searching":false,
        //"paging": false,
        "serverSide": false,
        "autoWidth": false,
        "bSort": false,
        //"scrollY": "150px",
        //"scrollCollapse": "true",
        "data": t,
        "columns": [{
            "data": "problem_id"
        }, {
            "data": "fAccount"
        }, {
            "data": "fName"
        }, {
            "data": "fClassName"
        }, {
            "data": "sAccount"
        }, {
            "data": "sName"
        }, {
            "data": "sClassName"
        }, {
            "data": "similarity"
        } ],
        "columnDefs": [{
            "render" : function(data, type, row) {
                var a = "";
                //console.log(row);
                a += "<button type='button' class='btn btn-primary' onclick=\"showSimilarityInf("+row.problem_id+", "+row.fAccount+", '"+row.fName+"', '"+row.fClassName+"', "+row.sAccount+", '"+row.sName+"', '"+row.sClassName+"', "+row.f_sid+", "+row.s_sid+")\" title='show' data-toggle='modal' data-target='#courseListDialog' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-eject'></i>&nbsp;查看</button>"
                return a;
            },
            "targets" :8
        }]
    });
}
//获取指定两个小朋友的提交代码
function getSubmitCode(fsid, ssid){
    $.ajax({
        type: "POST",
        url: "/testMn/getTargetSubmitCode",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify({
            "f_sid" : fsid,
            "s_sid" : ssid
        }),
        success:function (result) {
            loadSubmitCode(result);
        }
    });
}
function loadSubmitCode(t){
    $("#sCode").text(t.codeB);
    $("#fCode").text(t.codeA);
}
function showSimilarityInf(problem_id, fAccount, fName, fClassName, sAccount, sName, sClassName, f_sid, s_sid) {

        $("#dialogTitle").html("代码比较")
        //$("#dialogUserAccount").attr("readonly",true)
        $("#fClassName").text(fClassName)
        $("#sClassName").text(sClassName)
        $("#fName").text(fName)
        $("#sName").text(sName)
        $("#fAccount").text(fAccount)
        $("#sAccount").text(sAccount)
        $("#fProId").text(problem_id)
        $("#sProId").text(problem_id)
        getSubmitCode(f_sid, s_sid);

}