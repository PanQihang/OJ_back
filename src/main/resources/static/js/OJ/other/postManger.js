
$(document).ready(function () {
    loadSummernote()
    showtopicListinfo();
});

function showtopicListinfo() {
    $.each(info.postlist,function(index,value){
        value.time = formatTime(value.time)

    })
    var dataTable=$('#postTable');
    if ($.fn.dataTable.isDataTable(dataTable)) {
        dataTable.DataTable().destroy();
    }
    dataTable.DataTable({
        "searching": false,
        "autoWidth": false,
        "processing": true,
        "data": info.postlist,
        "bSort": false,
        "columns": [{
            "data": "id"
        }, {
            "data": "title"
        }, {
            "data": "content"
        }, {
            "data": "name"
        },{
            "data": "time"
        }, {
            "data": "view_num"
        }, {
            "data": "zan_num"
        },{
            "data":"reply_num"
        },{
            "data":"flag"
        }],
        "columnDefs": [
            {
                "render": function (data, type, row) {
                    debugger
                    var content = row.content;

                    return content.replace(/<[^>]+>/g,"").substring(0,50);
                },
                "targets": 2
            },{
            "render": function (data, type, row) {
                debugger
                var a = "";
                //a += "<button type='button' class='btn btn-primary' onclick='showIp(\"" + row.id + "\")' title='IP' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-eject'></i>&nbsp;IP</button>";
                a += "<button type='button' onclick='showarticel(\"" + row.title +"\",\"" + escape(row.content) +"\")' class='btn btn-primary'  title='查看'   style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-search'></i>&nbsp;详情</button>"
                if(row.flag=="否") {
                    a += "<button type='button' class='btn btn-primary' onclick='Flag(\"" + row.id +"\", \""+row.flag+ "\")' title='置顶' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-pencil'></i>&nbsp;置顶</button>"
                }else{
                    a += "<button type='button' class='btn btn-primary' onclick='Flag(\"" + row.id +"\", \""+row.flag+ "\")' title='取消置顶' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-pencil'></i>&nbsp;取消置顶</button>"

                }
                return a;
            },
            "targets": 9
        }]
    });

}

function Flag(id,flag) {
    if(flag=="是"){
        flag=0;
    }else{
        flag=1;
    }
    console.log(flag);
    $.ajax({
        type: "POST",
        url: "/blocksMn/updateFlagpost",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify({
            id:id,
            flag:flag
        }),success(result){
            if(result==true){
                swal("操作成功！");

            }else{
                swal("操作失败！")
            }
            setTimeout( function(){
                history.go(0);
            }, 1* 1000 );
        }
    })
}
function showarticel(name,content) {
   $('#postTitle').val(name);
  // $('#topic').val(topic);
    $('#content').code(unescape(content));
    $('#myModal5').modal();
}
function formatTime(time) {
    time = time.split(".")[0];
    time = time.replace("T", " ")
    return time;
}
function loadSummernote() {
    $('#content').summernote('disable');
    $("#content").summernote({
        height: 500,
        minHeight: 200,
        maxHeight: 600,
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