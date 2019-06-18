var selectedPicList = [];
selectedPicList = adminPicInfo.selectedPicList;
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
};

$(document).ready(function () {
    getSelectPic();
    getPicOrder();
});

function getSelectPic()
{
    $.ajax({
        type:"POST",
        url: "/pic/getSelectPic",
        datatype: "json",
        contentType: "application/json;charset=UTF-8",
        success:function(result) {
            var dataTable = $('#preSelectPicTable');
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
                "data" : result,
                "columns" : [{
                    "data" : "id"
                },{
                    "data" : "name"
                },{
                    "data" : "uploader_name"
                },{
                    "data" : "describes",
                },],
                "columnDefs": [{
                    "render" : function(data, type, row) {
                        var a = "";
                        a+="<i style=\"cursor:pointer\" onclick='preSelect(this, \""+row.id+"\", \""+row.name+"\", \""+row.uploader_name+"\", \""+row.describes+"\")' class='fa fa-check'></i>"
                        return a;
                    },
                    "targets" : 4
                }]
            });
        }
    })
}

function preSelect(radio, id, name, uploader_name, describes) {
    var isSelected = false
    for (var i=0; i<selectedPicList.length; i++){
        if(id == selectedPicList[i].id){
            isSelected = true
            toastr.warning("","该文章已经被选择")
            break
        }
    }
    if (!isSelected) {
        var addQue = {};
        addQue.id = id;
        addQue.name = name;
        addQue.uploader_name = uploader_name;
        addQue.describes = describes;
        addQue.order_show = selectedPicList.length+1;
        selectedPicList.push(addQue);
        getPicOrder();
    }
}

function getPicOrder()
{
    var dataTable = $('#selectedPicTable');
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
        "data" : selectedPicList,
        "columns" : [{
            "data" : "order_show",
        },{
            "data" : "id"
        },{
            "data" : "name"
        },{
            "data" : "uploader_name"
        },{
            "data" : "describes",
        },],
        "columnDefs": [{
            "render" : function(data, type, row) {
                var a = "";
                a+="<i style='cursor:pointer' onclick='deleteSelectedPic(\""+row.id+"\")' class='fa fa-close'></i>"
                return a;
            },
            "targets" :5
        }]
    });
}

function deleteSelectedPic(id) {
    for (var i=0; i<selectedPicList.length; i++){
        if(id == selectedPicList[i].id){
            selectedPicList.splice(i, 1);
        }
    }
    for(var i = 1; i <= selectedPicList.length; i++)
    {
        selectedPicList[i-1].order_show = i;
    }
    getPicOrder();
}
function saveAdminPic()
{
    if(selectedPicList.length == 0)
    {
        toastr.error("","至少有一张轮播图片！");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/pic/saveAdminPic",
        datatype: "json",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({
            "selectedPicList": selectedPicList,
        }),
        success: function(result)
        {
            swal("保存成功！", "", "success");
            $(".confirm").click(function(){window.location.href = "/pic/adminPic/"+id;})
        }
    })

}