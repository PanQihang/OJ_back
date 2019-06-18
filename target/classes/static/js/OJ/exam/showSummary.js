var selectedresult = [];
$(document).ready(function () {
    init();

});

//初始化创建
function init(){
    getTestResultList();
    getQuestionList();
    getPieChartData();
    document.getElementById('testName').innerText = testInfo.name;
}
// 绘制图表
function getPieChartData(){
    var temp = $('#classId').val();
    $.ajax({
        type: "POST",
        url: "/summaryMn/getTheStatisticalResult",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify({
            "testId" : testInfo.tid,
        }),
        success:function (result){
            pieChartCreate(result.key, result.data);
        }
    });
}
function pieChartCreate(key, t){
    var pieChart = echarts.init(document.getElementById("echarts-pie-chart"));
    echarts.init(document.getElementById('echarts-pie-chart')).setOption({
        title: {
            text: '考试成绩分布',
            subtext: '',
            x: 'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient : 'vertical',
            x : 'left',
            data:key
        },
        series: {
            name: '考试情况',
            type: 'pie',
            radius: '60%', //可以影响饼的半径
            center: ['60%', '50%'], //可以影响饼的位置
            data: t  //饼图中的数据来源
        }
    });
}
//加载考试题目列表
function getQuestionList(){
    $.ajax({
        type: "POST",
        url: "/summaryMn/getTestList",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify({
            "testId" : testInfo.tid
        }),
        success:function (result) {
            for(var i=0; i<result.length;i++)
            {
                result[i].start = formatDate(result[i].start);
                result[i].end = formatDate(result[i].end);
            }
            loadQuestionList(result);
            pieChartCreate(result.result,result.all);
        }
    });
}
function loadQuestionList(t){
    var dataTable = $('#questionTable');
    if ($.fn.dataTable.isDataTable(dataTable)) {
        dataTable.DataTable().destroy();
    }
    dataTable.DataTable({
        "searching":false,
        "paging": false,
        "serverSide": false,
        "autoWidth" : false,
        "bSort": false,
        "scrollY": "150px",
        "scrollCollapse": "true",
        "data" : t,
        "columns" : [{
            "data" : "name"
        },{
            "data" : "start"
        },{
            "data" : "end"
        }]});
}
//加载考试结果
function getTestResultList(){
    $.ajax({
        type: "POST",
        url: "/summaryMn/getTestScoreResultList",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify({
            "testId" : testInfo.tid,
            "classId": $('#classId').val(),
            "majorId": ''//$('#majorId').val()
        }),
        success:function (result) {
            selectedresult = result;
            loadTestResultList(result);
        }
    });
}
function loadTestResultList(t){
    var dataTable = $('#resultTable');
    if ($.fn.dataTable.isDataTable(dataTable)) {
        dataTable.DataTable().destroy();
    }
    dataTable.DataTable({
        //"searching":false,
        "serverSide": false,
        "autoWidth" : false,
        "bSort": false,
        "data" : t,
        "columns" : [{
            "data" : "account"
        },{
            "data" : "name"
        },{
            "data" : "class_name"
        },{
            "data" : "result"
        },{
            "data" : "all"
        }]});
}

//**功能函数
function formatTime(time) {
    time = time.split(".")[0];
    time = time.replace("T", " ")
    return time;
}

function formatDate (date) {
    date*=1000;
    var tt = new Date(date)
    var date= new Date(Date.parse(tt));
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    var hour = date.getHours();
    var min = date.getMinutes();
    var time = y+'-'+m+'-'+d+' '+hour+':'+min;
    return time;
}

function daochu() {
    var str = '<tr><td>学号</td><td>姓名</td><td>班级</td><td>试题对应分数</td><td>总分</td></tr>';
    for(var i = 0 ; i < selectedresult.length ; i++ ){
        str+='<tr>';
            //增加\t为了不让表格显示科学计数法或者其他格式
        str+=`<td>${ selectedresult[i]['account'] + '\t'}</td>`;
        str+=`<td>${ selectedresult[i]['name'] + '\t'}</td>`;
        str+=`<td>${ selectedresult[i]['class_name'] + '\t'}</td>`;
        str+=`<td>${ selectedresult[i]['result'] + '\t'}</td>`;
        str+=`<td>${ selectedresult[i]['all'] + '\t'}</td>`;
        str+='</tr>';
    }
    var worksheet = 'Sheet1'
    var uri = 'data:application/vnd.ms-excel;base64,';

    //下载的表格模板数据
    var template = `<html xmlns:o="urn:schemas-microsoft-com:office:office" 
      xmlns:x="urn:schemas-microsoft-com:office:excel" 
      xmlns="http://www.w3.org/TR/REC-html40">
      <head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet>
        <x:Name>${worksheet}</x:Name>
        <x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet>
        </x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]-->
        </head><body><table>${str}</table></body></html>`;
    //下载模板
    window.location.href = uri + base64(template)
}

function base64 (s)
{ return window.btoa(unescape(encodeURIComponent(s))) }


function daochu2() {
    var str = `学号,姓名,班级,试题对应分数,总分\n`;
    for(var i = 0 ; i < selectedresult.length ; i++ ){
        //增加\t为了不让表格显示科学计数法或者其他格式
        str+=`${ selectedresult[i]['account'] + '\t'},`;
        str+=`${ selectedresult[i]['name'] + '\t'},`;
        str+=`${ selectedresult[i]['class_name'] + '\t'},`;
        str+=`${ selectedresult[i]['result'] + '\t'},`;
        str+=`${ selectedresult[i]['all'] + '\t'},`;
        str+='\n';
    }
    let uri = 'data:text/csv;charset=utf-8,\ufeff' + encodeURIComponent(str);
    //通过创建a标签实现
    let link = document.createElement("a");
    link.href = uri;
    //对下载的文件命名
    link.download =  testInfo.name+'.xls';
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}
