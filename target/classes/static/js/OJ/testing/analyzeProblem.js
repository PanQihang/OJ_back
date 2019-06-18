$(document).ready(function () {
    analyzeProblem();
});

//对问题进行分析
function analyzeProblem() {
    var id = $('#cid').val();
      var submitCount =0;
       var successCount = 0;
       var formatErrorCount = 0;
       var wrongAnswerCount = 0;
       var compileErrorCount = 0;
    if(id != null && id != ""){
        $.ajax({
            type: "POST",
            url: "/problemsMn/analyzeProblem",
            dataType: "json",
            async:false,
            data:{
                "id" : id
            },
            success:function (result){
                var names=[];
                var nums=[];
                for(var key in result[0]){
                     names.push(key);
                     nums.push(result[0][key])
                }
               submitCount = result[0].submitCount;
              successCount = result[0].successCount;
              formatErrorCount = result[0].formatErrorCount;
              wrongAnswerCount = result[0].wrongAnswerCount;
               compileErrorCount = result[0].compileErrorCount;
                setEcharts(submitCount,successCount,formatErrorCount,wrongAnswerCount,compileErrorCount);
                $('#id').val("");
            }
        })
    }


}

//饼形图设置
function setEcharts(submitCount,successCount,formatErrorCount,wrongAnswerCount,compileErrorCount){
    //$('echarts-pie-chart').removeAttrs('echarts_instance_');
    document.getElementById("echarts-pie-chart")
    var pieChart = echarts.init(document.getElementById("echarts-pie-chart"));
    var pieoption = {
        title : {
            text: '提交状态分析',
            subtext:' 提交状态',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient : 'vertical',
            x : 'left',
            data:['全部通过','格式错误','答案错误','编译错误','其他错误']
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {
                    show: true,
                    type: ['pie', 'funnel'],
                    option: {
                        funnel: {
                            x: '25%',
                            width: '50%',
                            funnelAlign: 'left',
                            max: 1548
                        }
                    }
                },
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        series : [
            {
                name:'提交状态',
                type:'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    {value:successCount, name:'全部通过'},
                    {value:formatErrorCount, name:'格式错误'},
                    {value:wrongAnswerCount, name:'答案错误'},
                    {value:compileErrorCount, name:'编译错误'},
                    {value:submitCount - compileErrorCount - successCount - formatErrorCount -wrongAnswerCount,name:'其他错误'}
                ],
                itemStyle:{
                    normal:{
                        label:{
                            show: true,
                            formatter: '{b} : {c} ({d}%)'
                        },
                        labelLine :{show:true}
                    }
                }
            }
        ]
    };
//if(pieoption && typeof pieoption == "object"){
    pieChart.setOption(pieoption);
    $(window).resize(pieChart.resize);
//}

}


