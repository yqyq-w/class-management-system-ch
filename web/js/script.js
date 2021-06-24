/*
 * assn 402
 * yiqingw
 */

// 给表格行排版的方法
function colorRows() {
    // 用attr()方法设置class属性,是一个覆盖的过程;如果已经有此属性, 则会覆盖
    // 而addClass()则是一个追加的过程
    $("tbody tr:even").attr("class", "info"); // 偶数行的tbody tr
    $("tbody tr:odd").attr("class", "warning"); // 奇数行的tbody tr
}
// 初次加载先排版表格背景颜色
colorRows();

// 获取框中值的方法 
function getInputValue(input) {
    var str = $(input).val();
    return str;
}

// 获取节点内容的方法 
function getNodeText(node) {
    var str = $(node).text();
    return str;
}

// 关闭模态框的方法
function closeModal(str) {
    // .modal(options)方法
    $(str).modal("hide");
    // bootstrap源码中modal弹出后会加上一个.modal-backdrop的div层，即覆盖在body上的灰色层
    // $(".modal").css("display","none");        
    // $(".modal-backdrop").css("display","none");
}

function selectCheck(){
    var option=$("#selectC option:selected");
    // alert(option.val());
    if("0"==option.val()|| ""==option.val() ||null==option) {
        alert("请选择班级！");
        return false;
    }
}

// 新增功能窗口中，点击确认增加后
$("#addSubmit").on("click", function () {
    // 获取输入内容
    var inputs = $(".mustWrite");
    // alert($("#addModal .modal-body input").length);

    for (var i = 0; i < inputs.length; i++) {
        // if (i==5){
        //     break;
        // }
        // alert($(inputs[i]).val());
        if ($(inputs[i]).val() == "") {
             // 必须全部填写
            alert("还有内容为空！");
            return false;
        }
    }

    var option=$("#selectC option:selected");
    // alert(option.val());
    if("0"==option.val()|| ""==option.val() ||null==option) {
        alert("请选择班级！");
        return false;
    }

    // 新增好了之后关闭addModal模态框
    closeModal("#addModal");
    return true;
});



// 删除选中学生信息，先判断是否勾选；再确认是否要删除
$("#delete").click(function () {
    // 获取选中项
    var checkedList = $("tbody input:checkbox:checked");
    // 未勾选则进行相应提示
    if (checkedList.length == 0) {
        alert("请先选择要删除的内容！");
        return false;
    }
    // 点击确认按钮进行删除，删除<tr>节点
    $("#deleteSubmit").click(function () {
        // 确定要删除后关闭deleteModal模态框
        closeModal("#deleteModal");
    });
    return true;
});

// 编辑选中学生的个人信息，目前版本仅支持选一个修改
$("#edit").click(function () {
    // 获取选中项
    var checkedList = $("tbody input:checkbox:checked");
    // 未勾选则进行相应提示
    if (checkedList.length == 0) {
        alert("请先选择要修改的内容！");
        return false;
    } else if (checkedList.length != 1) {
        alert("只能选择一个学生，请逐次修改！");
        return false;
    }

    for (var i = 0; i < checkedList.length; i++) {
        // 获取需要编辑的节点
        var editNodes = $(checkedList[i]).parent().siblings();
        // 获取新输入信息，注意只包括text类型
        var inputContent = $("#editModal input:text");

        for (var j = 0; j < editNodes.length; j++) {
            // 获取需要编辑的节点中的当前信息
            // value属性: 在输入框加载的时候输入框中的值; 提交时可直接获取
            var nodeValue = getNodeText(editNodes[j]);
            // 先将输入框加载时，输入框中的值设置为当前信息
            $(inputContent[j]).attr("value", nodeValue);
            // 性别选项特殊处理
            if (j == 2) {
                if (nodeValue == "男") {
                    $("input[name=sex]:eq(0)").attr("checked", "checked");
                } else {
                    $("input[name=sex]:eq(1)").attr("checked", "checked");
                }
            } if (j > 2) {
                // 性别选项后面的输入框editContent.length=5，而editNodes.length=6，所以inputContent[j - 1]
                $(inputContent[j - 1]).attr("value", nodeValue);
            }
        }
        // 下拉班级选项默认目前所属班级??
            // 1、设置value为pxx的项选中 $(".selector").val("pxx");
            // 2、设置text为pxx的项选中 $(".selector").find("option[text='pxx']").attr("selected",true);
        // var selectValue = getNodeText(editNodes[6]);
        // alert(selectValue);


        // 点击确定编辑后，将学生信息更新为新输入/选择的值
        $("#editSubmit").click(function () {
            var option=$("#selectC2 option:selected");
            // alert(option.val());
            if("0"==option.val() || ""==option.val() || null==option) {
                alert("请选择班级！");
                return false;
            }
            // 确定编辑后关闭editModal模态框
            closeModal("#editModal");
            return true;
        });
    }
    return true;
});


// 编辑选中班级的信息，目前版本仅支持选一个修改
$("#editC").click(function () {
    // 获取选中项
    var checkedList = $("tbody input:checkbox:checked");
    // 未勾选则进行相应提示
    if (checkedList.length == 0) {
        alert("请先选择要修改的内容！");
        return false;
    } else if (checkedList.length != 1) {
        alert("只能选择一个班级，请逐次修改！");
        return false;
    }

    for (var i = 0; i < checkedList.length; i++) {
        // 获取需要编辑的节点
        var editNodes = $(checkedList[i]).parent().siblings();
        // 获取新输入信息，注意只包括text类型
        var inputContent = $("#editModal input:text");

        for (var j = 0; j < editNodes.length; j++) {
            // 获取需要编辑的节点中的当前信息
            // value属性: 在输入框加载的时候输入框中的值; 提交时可直接获取
            var nodeValue = getNodeText(editNodes[j]);
            // 先将输入框加载时，输入框中的值设置为当前信息
            $(inputContent[j]).attr("value", nodeValue);
        }

        $("#editSubmit").click(function () {
            // 确定编辑后关闭editModal模态框
            closeModal("#editModal");
        });
    }
    return true;
});



// // 删除选中班级信息，先判断是否勾选、再判断有否关联学生；最后确认是否要删除
// $("#deleteC").click(function () {
//     // 获取选中项
//     var checkedList = $("tbody input:checkbox:checked");
//     // 未勾选则进行相应提示
//     if (checkedList.length == 0) {
//         alert("请先选择要删除的内容！");
//         return false;
//     }
//
//
//     alert("仍有关联学生，请选择当前班级人数为0的班级！");
//     // 点击确认按钮进行删除，删除<tr>节点
//     $("#deleteSubmit").click(function () {
//         // 确定要删除后关闭deleteModal模态框
//         closeModal("#deleteModal");
//     });
//     return true;
// });

// // 点击查询按钮后
// function queryCheck() {
//     // 获取输入的查询条件的值
//     var idDuery = getInputValue("#queryID");
//     var nameQuery = getInputValue("#queryName");
//     // 查询条件为空则提示
//     if ("" == idDuery && "" == nameQuery) {
//         alert("请先输入查询条件！");
//         return false;
//     } else {
//         return true;
//     }
// }
