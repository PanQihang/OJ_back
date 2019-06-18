
//全局变量
var NewStudentNum =0; //批量增加学生时，学生的总数
var NewStudentList; //批量添加学生的数据

$(document).ready(function () {
    queryStudentInfo();
    indexClassNameSelect();

});
var icon = "<i class='fa fa-times-circle'></i>";
//重置form内的标签
function resetForm() {
    $(".form-horizontal input").val("");
    $(".form-horizontal select").val("");
    queryStudentInfo();
}
function resetStudentInfoDialog() {
    $("#myModal5 input").val("");
    $("#myModal5 select").val("");
    $("#myModal5 input").removeClass("error");
    $("#myModal5 select").removeClass("error");
    $("#myModal5 label.error").remove()
}
function queryStudentInfo() {
    $.ajax({
        type: "POST",
        url: "/studentMn/getTargetStudentList",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify({
            "account" : $('#Account').val(),
            "student_name" : $('#studentName').val(),
            "class_id" : $('#classId').val()
        }),
        success:function (result) {
            var dataTable = $('#stuInfoTable');
            if ($.fn.dataTable.isDataTable(dataTable)) {
                dataTable.DataTable().destroy();
            }
            dataTable.DataTable({
                "serverSide": false,
                "autoWidth" : false,
                "bSort": false,
                "data" : result,
                "columns" : [ {
                    "data" : "account"
                },{
                    "data" : "student_name"
                },{
                    "data" : "class_name"
                }],
                "columnDefs": [{
                    "render" : function(data, type, row) {
                        var a = "";
                        a += "<button type='button' class='btn btn-primary' onclick='showEditStudent(\""+row.id+"\")' data-toggle='modal' data-target='#myModal5' title='编辑用户' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-pencil-square-o'></i>&nbsp;编辑</button>"
                        a += "<button type='button' class='btn btn-primary' onclick='deleteStudent(\""+row.id+"\")' title='删除用户' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-user-times'></i>&nbsp;删除</button>"
                        a += "<button type='button' class='btn btn-primary' onclick='reSetPassord(\""+row.id+"\")' data-toggle='modal' data-target='#resetPassword' title='重置密码' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-refresh'></i>&nbsp;重置密码</button>"
                        //a += "<button type='button' class='btn btn-primary' onclick='courseList(\""+row.id+"\")' data-toggle='modal' data-target='#courseListDialog' title='课程列表' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-list'></i>&nbsp;课程列表</button>"
                        return a;
                    },
                    "targets" :3
                }]
            });
        }
    })
}
function deleteStudent(id) {
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
                    url: "/studentMn/deleteTheStudent",
                    contentType: "application/json;charset=UTF-8",
                    dataType: "json",
                    data:JSON.stringify({
                        "id" : id
                    }),
                    success:function (result){
                        if(result.result == "succeed"){
                            queryStudentInfo();
                            swal("删除成功！", "用户已被删除", "success");
                        }else{
                            swal("删除失败！", "用户暂时不能被删除", "error");
                        }

                    }
                })
            }else {
                swal("已取消", "你取消了删除用户操作", "error");
            }
        });
}
//展示用户编辑详情模态窗口
function showEditStudent(id) {
    resetStudentInfoDialog();
    if(id!=''){
        $("#dialogTitle").html("编辑用户")
        $("#dialogStudentId").attr("readonly",true)
        $("#dialogStudentId").val(id)
        $.ajax({
            type: "POST",
            url: "/studentMn/getTargetStudentList",
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            data:JSON.stringify({
                "id" : id
            }),
            success:function (result){
                $("#dialogStuAccount").val(result[0].account)
                $("#dialogStudentName").val(result[0].student_name)
                $("#dialogClassName").val(result[0].class_id)
            }
        })
    }else{
        $("#dialogStudentId").attr("readonly",true)
        $("#dialogTitle").html("添加用户")
    }
}
function resetAddMoreStudentDialog(){
    $("#myModal5B select").val("");
    $('#upFileX').val('');
}
function showAddMoreStudent(){
    resetAddMoreStudentDialog();
    $("#dialogTitleB").html("批量添加用户");

}
//新增或更新用户信息
function saveOrUpdateStudentInfo() {
    /*if($("#dialogStudentInfo").validate({
        rules: {
            dialogStuAccount: {
                required: true,
                length: 10
            },
            dialogStudentName: {
                required: true,
                maxlength: 5
            },
            dialogClassName: {
                required: true
            }
        },
        messages: {
            dialogUserAccount: {
                required: icon + "学号不能为空",
                length: icon + "学号应为10位的整数"
            },
            dialogUserName: {
                required: icon + "姓名不能为空",
                equalTo: icon + "姓名最长为32"
            },
            dialogClassName: {
                required: icon + "班级不能为空"
            }
        }
    }).form()){*/
    var newStudentInf = {
        "id" : $("#dialogStudentId").val(),
        "account" : $("#dialogStuAccount").val(),
        "name" : $("#dialogStudentName").val(),
        "class_id" : $("#dialogClassName").val()
    }
    if(false==checkStudentInf(newStudentInf, 'checkClassId')) return;
        $.ajax({
            type: "POST",
            url: ($("#dialogStudentId").val()==''?"/studentMn/addNewStudent":"/studentMn/changeTheStudent"),
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            data:JSON.stringify(newStudentInf),
            success:function (result){
                if(result.result == "succeed"){
                    queryStudentInfo();
                    //关闭模态窗口
                    $('#myModal5').modal('hide');
                    swal("保存成功！", "", "success");
                }else{
                    swal("保存失败！", result.message, "error");
                }
            }
        });
    /*}*/
}
//获取所有班级信息
function indexClassNameSelect() {
    $.ajax({
        type: "POST",
        url: "/studentMn/getClassList",
        dataType: "json",
        success:function (result){
            var roleSelectInfo = "";
            for (var i=0; i<result.length; i++){
                roleSelectInfo += "<option value='"+result[i].id+"'>"+result[i].name+"</option>"
            }
            $("#classId").append(roleSelectInfo);
            $("#dialogClassName").append(roleSelectInfo);
            $("#dialogClassNameB").append(roleSelectInfo);
        }
    })
}

//重置弹出框的内容
function rePwdformReset() {
    $("#resetPassword input").val("");
    $("#resetPassword input").removeClass("error");
    $("#resetPassword label.error").remove()
}

function reSetPassord(id) {
    rePwdformReset();
    $("#resetPasswordUserId").val(id)
}

//发送重置密码的请求
function saveNewPassword() {
    if($('#newPassword').val()==''){
        swal('请输入新密码', '', 'error');
        return;
    }else if($('#verifyPassword').val()==''){
        swal('请再次输入密码', '', 'error');
        return;
    }else if($('#newPassword').val() != $('#verifyPassword').val()){
        swal('两次输入密码不相同', '请确保两次输入的密码一样', 'error');
        return;
    }
    var newStudentInf = {
        "id" : $("#resetPasswordUserId").val(),
        "password" : $("#newPassword").val()
    }
    if(false==checkStudentInf(newStudentInf, 'checkPassword')){
        return;
    }
        $.ajax({
            type: "POST",
            url: "/studentMn/changeTheStudentPW",
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            data:JSON.stringify(newStudentInf),
            success:function (result){
                if(result.result=='succeed'){
                    //关闭模态窗口
                    $('#resetPassword').modal('hide');
                    swal("修改成功！", "密码已成功修改", "success");
                }else{
                    swal("修改失败！", "密码修改失败", "error");
                }

            }
        })
}
/*
    方案一：前端进行多次创建请求，并显示每次请求的结果   -------------------------------------- 缺乏规范性检测
    （未使用方案）
 */
//批量添加学生时，展示预览信息(方案一)
function showStudentInfList() {
    $('#CLDtbody').html("");
    var className = $('#dialogClassNameB').find('option:selected').text();
    var obj = document.getElementById('upFileX')//$('#upFileX');
    if($('#upFileX').val()==''){
        swal('未选择文件','请选择要读取的excel文件');
        return ;
    }
    //console.log($('#upFileX').val());

    $('#CLDuserId').val( className );

    var str='';
    str="<thead>" + "<tr>" + "<th width=\"30%\">学号</th>\n" + "<th width=\"30%\">姓名</th>\n" + "<th width=\"30%\">班级</th>\n" + "<th width=\"10%\">状态</th>\n" + "</tr>\n" + "</thead>";
    $('#CLDtbody').append(str);
    if(!obj.files) {
        return;
    }
    var f = obj.files[0];
    var reader = new FileReader();
    var wb;
    reader.onload = function(e) {

        var data = e.target.result;
        wb = XLSX.read(data, {
            type: 'binary'
        });
        //wb.SheetNames[a]表示读取的是excel中的第几张表(下标从0开始)
        var JData = XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]]);
        NewStudentNum = JData.length; //全局变量NewStudentNum 保存着当前学生集的数量
        var Taccount, Tname;
        for(var i=0; i<JData.length; i++){
            Taccount = (JData[i]['account']==undefined?'':JData[i]['account']);
            Tname = (JData[i]['name']==undefined?'':JData[i]['name']);
            str="<tr>"
            str+="<td><input type='text' id='nStuAccount"+i+"' value='"+Taccount+"' /></td>";
            str+="<td><input type='text' id='nStuName"+i+"' value='"+Tname+"' /></td>";
            str+="<td><span id='nStuClass"+i+"' >"+className+" </span></td>";
            str+="<td><span id='nStuState"+i+"' >"+'待确定'+"</span></td>";
            str+="</tr>"
            //console.log(str);
            $('#CLDtbody').append(str);
        }
    }
    reader.readAsBinaryString(f);
}
//批量添加学生账号(方案一)
function saveStudentList(){
    var classId = $('#dialogClassNameB').val();
    if(classId == ''){
        swal('未选择班级','请先选择班级');
        return ;
    }
    var i, cell, strName, strAccount;
    cell = [];
    for(i=0; i<NewStudentNum; i++){
        strAccount='#nStuAccount'+ i; strName = '#nStuName'+i;
        cell[i]={
            'account' : $(strAccount).val(),
            'name' : $(strName).val(),
            'class_id' : classId,
            'elementId' : i
        }
        console.log(JSON.stringify(cell[i]));
        $.ajax({
            type: "POST",
            url: "/studentMn/addNewStudent",
            async:false,
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            data:JSON.stringify(cell[i]),
            success:function (result){
                var strState;
                strState = '#nStuState'+result.elementId;
                if(result.result == "succeed"){
                    $(strState).text("添加成功");
                    //关闭模态窗口
                    //$('#myModal5').modal('hide');
                }else{
                    $(strState).text(result.message); //显示后台对错误的判断结果
                    //swal("保存失败！", result.message);
                }
            }
        });
    }
}
/*
    方案二：前端一次性将要新增的学生账号发送给后台并保持连接，后台执行多次创建请求，最后将结果返回给当前连接
    （当前使用方案）
 */
//批量添加学生时，展示预览信息(方案二)
function showStudentInfListB(JsonData) {
    var classId = $('#dialogClassNameB').val();
    if(classId == ''){
        swal('未选择班级','请先选择班级');
        $('.confirm').one("click", function(){ //一次性监听器
            $("#testId").trigger("click"); //模拟点击关闭按钮
        });
        return ;
    }
    var className = $('#dialogClassNameB').find('option:selected').text();
    $('#CLDuserId').val( className );
    var JData = JsonData.data;
    var dataTable = $('#CLDtbody');
    dataTable.html("");
    for(var i=0; i<JData.length; i++){
        var Taccount = (JData[i]['account']==undefined?'':JData[i]['account']);
        var Tname = (JData[i]['name']==undefined?'':JData[i]['name']);
        str="<tr>"
        str+="<td width='33%'><span id='nStuAccount"+i+"' >"+Taccount+"</span></td>";
        str+="<td width='34%'><span id='nStuName"+i+"' >"+Tname+"</span></td>";
        str+="<td width='33%'><span id='nStuClass"+i+"' >"+className+" </span></td>";
        str+="</tr>"
        $('#CLDtbody').append(str);
    }
    $('#AmountNewStudent').text('新增账号总数:'+JData.length)
}
//批量添加学生账号(方案二)
function saveStudentListB(){
    if(!checkStudentList(NewStudentList)) //检查学生账号集的数据（学号、姓名）是否符合规范
        return ;
    NewStudentList.classId=$('#dialogClassNameB').find('option:selected').val(); //临时将学生账号集的班级所属添加上
        $.ajax({
            type: "POST",
            url: "/studentMn/bulkAddNewStudent",
            async:false,
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            data:JSON.stringify(NewStudentList),
            success:function (result){
                var strState;
                strState = '#nStuState'+result.elementId;
                if(result.result == "succeed"){
                    swal("添加成功！", "批量添加成功", "success");
                    //关闭模态窗口
                    //$('#myModal5').modal('hide');
                }else{
                    swal("添加失败！", result.message, "error")
                    //swal("保存失败！", result.message);
                }
            }
        });
}
//检查批量新增的学生集合的数据格式是否规范：批量添加时的规范性检测
function checkStudentList(t){
    var XTypeRule = /^[0-9]*$/;
    var i=0; t=t.data;
    for(;i<t.length;i++){
        //console.log(t[i].account.length+'>'+t[i].account);
        //console.log(t[i].name.length+'>'+t[i].name);
        /*if(!XTypeRule.test(t[i].account)  || t[i].account.length!=10) {
            swal('学号' + t[i].account + '格式不正确', '请确保为10位全数字格式', 'error');
            return false;
        }else if(t[i].name.length>50 || t[i].name.length<1){
            swal('姓名' + t[i].name + '格式不正确', '请确保不大于50个字符长度', 'error');
            return false;
        }*/
        if(false==checkStudentInf(t[i], '')) return false;
    }
    return true;
}
function checkStudentInf(t, acType){
    /*
    idsmallint(6) unsigned NOT NULL
    accountvarchar(64) NOT NULL
    passwordchar(32) NOT NULL
    namevarchar(50) NULL
    class_idsmallint(4) NOT NULL
     */
    if('checkPassword'==acType){
        if(t.password.length>32 || t.password.length<6){
            swal('新密码('+t.password+')格式不正确', '请确保其为长度不大于32且不小于6的非空字符串', 'error');
            return false;
        }
        return true;
    }
    var XTypeRule = /^[0-9]*$/;
    if(!XTypeRule.test(t.account)  || t.account.length!=10) {
        swal('学号(' + t.account + ')格式不正确', '请确保为非空的10位数字字符串', 'error');
        return false;
    }else if(t.name.length>50 || t.name.length<1){
        swal('姓名(' + t.name + ')格式不正确', '请确保为长度不大于50的非空字符串', 'error');
        return false;
    }else if('checkClassId'==acType){
        if(t.class_id==''){
            swal('未指定班级', '请指定所属班级', 'error');
            return false;
        }
    }
    return true;
}



/*
    excel文件读取及数据提取调用：
        excel文件依赖于: xlsx.full.min.js
 */
//将input file组件所选择的excel文件的内容读取出来，并以Json对象的形式将数据返回
function ConvertExcelToJsonArray(){
    $('#CLDtbody').html("");
    var file = document.getElementById('upFileX')
    if($('#upFileX').val()==''){
        swal('未选择文件','请选择要读取的excel文件');
        $('.confirm').one("click", function(){ //一次性监听器
            $("#testId").trigger("click"); //模拟点击关闭按钮
        });
        return ;
    }
    if(!file.files) {
        return;
    }
    var reader = new FileReader();
    var sheet_name_list;
    var result = [];
    var data;
    var workbook;
    var ans;
    reader.readAsBinaryString(file.files[0]);
    reader.onloadend = function (evt) {
        var i=0, temp;
        if(evt.target.readyState == FileReader.DONE){
            temp = reader.result;
            workbook = XLSX.read(temp, { type: 'binary' });
        }
        sheet_name_list = workbook.SheetNames;
        while(sheet_name_list[i]!=undefined){
            data = XLSX.utils.sheet_to_json(workbook.Sheets[sheet_name_list[i]], {header:1}); //sheet_name_list所指定的下标，代表的就是excel文件里的第几张表(下标从0开始)
            result[i]=data;
            i++;

        }
        //console.log("Array:\n");
        //console.log(result);
        ans = ConverArrayToJson(result);
        NewStudentList=ans[0];
        showStudentInfListB(NewStudentList);
    }
    return ans;
}
//将数组内容转换为json对象
/*
    参数解读：data数组的格式为[[['account', 'name'], ['学号', '姓名']....],[...]... ]  ，其为一个三维数组，代表一个excel文件里的所有内容（每一张表（每一张表里的内容（每一张表里的每一行内容）））
    返回类型：[{classId:'班级编号', data:[{account:'学号', name:'姓名'},...], Num:'data集合的总数'}, ...]
*/
function ConverArrayToJson(data){
    var result ;
    var i=0, j=0;
    var temp;
    var str;
    result='[';
    while(data[i]!=undefined){
        str='{"classId":"?", "data":[';
        j=1;
        while(data[i][j]!=undefined && data[i][j][0]!=undefined && data[i][j][1]!=undefined){
            //console.log("array data "+j+"->");
            //console.log(data[i][j]);
            temp='{"account":"'+data[i][j][0]+'", "name":"'+data[i][j][1]+'"}';
            j++;
            temp+=(data[i][j]!=undefined && data[i][j][0]!=undefined && data[i][j][1]!=undefined)?', ':'';
            str+=temp;
        }
        if(data[i][j]!=undefined && (data[i][j][0]!=undefined || data[i][j][1]!=undefined)){//表(i+1)的第(j+1)行存在，但数据不完整
            swal("表格数据异常提醒", "表"+(i+1)+"第"+(j+1)+"行存在缺少项，若无错，请忽略！")
        }
        i++;
        str+='], "Num":"'+(j-1)+'" }';
        result+=str;
        if(false && data[i]!=undefined){ //false字段的添加，以此来控制只获取第一张表的数据（原因：一个execle文件里，可以存在多张表！）
            str+=(data[i]!=undefined?', ':'');
        }else break;  //只读第一张表
    }
    result+=']';
    result=JSON.parse(result);//将json格式的字符串转换为json对象
    return result;
}





/*
    excel文件上传的调用(暂废)：
        原方案拟定将excel文件的读取操作放在后台进行，后因故放弃
 */
function upFile() {
    var formData = new FormData();
    formData.append('file', $('#upFileX')[0].files[0]);
    $.ajax({
        url: "/studentMn/upFile",
        type: 'POST',
        cache: false,
        data: formData,
        processData: false,
        contentType: false,
        beforeSend: function () {
        },
        success: function (data) {
            if (data.result == 'succeed') {
                swal("批量添加成功！", "success");
            } else {
                swal("批量添加失败！", data.message);
            }
        }
    });
}


