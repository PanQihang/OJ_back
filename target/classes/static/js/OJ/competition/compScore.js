
$(document).ready(function () {

    //testFunction();
    init();

});
//前端测试函数
function testFunction(){
    var tData = [
        {name: '完成0题人数', value: 12},
        {name: '完成1题人数', value: 17},
        {name: '完成3题人数', value: 31},
        {name: '完成4题人数', value: 6},
        {name: '完成2题人数', value: 19}
    ];
    pieChartCreate(['完成0题人数','完成1题人数','完成3题人数','完成4题人数','完成2题人数'], tData);
    tData = [
        {account:'001', name:'jack', className:'软件110', testResult:'096:30/097:30/098:20', testScore:80},
        {account:'001', name:'jack', className:'软件110', testResult:'096:30/097:30/098:20', testScore:80},
        {account:'001', name:'jack', className:'软件110', testResult:'096:30/097:30/098:20', testScore:80},
        {account:'001', name:'jack', className:'软件110', testResult:'096:30/097:30/098:20', testScore:80},
        {account:'001', name:'jack', className:'软件110', testResult:'096:30/097:30/098:20', testScore:80},
        {account:'001', name:'jack', className:'软件110', testResult:'096:30/097:30/098:20', testScore:80},
        {account:'002', name:'tom', className:'软件120', testResult:'096:30/097:30/098:20/099:20', testScore:100},
        {account:'002', name:'tom', className:'软件120', testResult:'096:30/097:30/098:20/099:20', testScore:100},
        {account:'002', name:'tom', className:'软件120', testResult:'096:30/097:30/098:20/099:20', testScore:100},
        {account:'002', name:'tom', className:'软件120', testResult:'096:30/097:30/098:20/099:20', testScore:100},
        {account:'002', name:'tom', className:'软件120', testResult:'096:30/097:30/098:20/099:20', testScore:100},
        {account:'002', name:'tom', className:'软件120', testResult:'096:30/097:30/098:20/099:20', testScore:100},
        {account:'002', name:'tom', className:'软件120', testResult:'096:30/097:30/098:20/099:20', testScore:100},
        {account:'002', name:'tom', className:'软件120', testResult:'096:30/097:30/098:20/099:20', testScore:100},
        {account:'001', name:'jack', className:'软件110', testResult:'096:30/097:30/098:20', testScore:80}
    ]
    loadTestResultList(tData);
    tData = [
        {'name':'jack', 'id':'001'},
        {'name':'jack', 'id':'001'},
        {'name':'jack', 'id':'001'},
        {'name':'jack', 'id':'001'},
        {'name':'jack', 'id':'001'},
        {'name':'jack', 'id':'001'},
        {'name':'jack', 'id':'001'},
        {'name':'jack', 'id':'001'},
        {'name':'jack', 'id':'001'},
        {'name':'jack', 'id':'001'},
        {'name':'jack', 'id':'001'},
        {'name':'jack', 'id':'001'}
    ]
    loadQuestionList(tData);
    showMajorOptionArea();
    JData = [
        {id:0, name:'软件'},
        {id:1, name:'计算机'},
        {id:3, name:'网络'}
    ]
    loadMajorList(JData);
    JData = [
        {id:0, name:'软件110'},
        {id:0, name:'软件110'},
        {id:0, name:'软件110'},
        {id:0, name:'软件110'},
        {id:0, name:'软件110'},
        {id:0, name:'软件110'}
    ]
    loadClassList(JData);
    JData = {
        testName:'测试样例001',
        testSDate:'2019-08-09',
        testEDate:'2020-09-04'
    }
    loadTestInf(JData);
}
//初始化创建
function init(){
    //showMajorOptionArea();
    getTestResultList();
    getQuestionList();
    getTestResultList();
    getTestInf();
    getPieChartData();
}
//改变班级选项后的页面动态变化
function changeClass(){
    //var temp =$('#classId').val();
    //console.log(temp);
    getTestResultList();
    getPieChartData();
}
//控制专业选项的显示
function showMajorOptionArea(){
    var temp = $('#majorOption');
    if('none'!=temp.attr('display'))
        temp.hide();
    else temp.show();
}
// 绘制图表
function getPieChartData(){
    var temp = $('#classId').val();
    $.ajax({
        type: "POST",
        url: "/testMn/getTheStatisticalResult",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify({
            "testId" : testInfo.tid,
            "classId": (undefined==temp?'':temp)
        }),
        success:function (result) {
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
        url: "/testMn/getTestProblemList",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify({
            "testId" : testInfo.tid
        }),
        success:function (result) {
            loadQuestionList(result);
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
            "data" : "id"
        },{
            "data" : "name"
        }]});
}
//加载考试结果
function getTestResultList(){
    $.ajax({
        type: "POST",
        url: "/competition/getcompScoreResultList",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify({
            "testId" : testInfo.tid,
        }),
        success:function (result) {
            console.log(result);
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
            "data" : "className"
        },{
            "data" : "testResult"
        },{
            "data" : "testScore"
        }]});
}
//加载考试信息
function getTestInf(){
    $.ajax({
        type: "POST",
        url: "/testMn/getTestBriefInf",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify({
            "testId" : testInfo.tid
        }),
        success:function (result) {
            loadTestInf(result);
        }
    });
}
function loadTestInf(t){
    $('#testName').text(t.testName);
    $('#testSDate').text("开始时间："+formatTime(t.testSDate));
    $('#testEDate').text("结束时间："+formatTime(t.testEDate));
}

//**功能函数
function formatTime(time) {
    time = time.split(".")[0];
    time = time.replace("T", " ")
    return time;
}
/*
     * @author xielanning
     * @Time 2019年4月24日 10点18分
     */