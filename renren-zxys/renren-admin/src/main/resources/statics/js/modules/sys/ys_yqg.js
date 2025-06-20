$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/ysWater/yqgList',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', index: 'id', width: 60 },
            { label: '项目名称', name: 'name', index: 'name', width: 80 },
            { label: '验收类型', name: 'ysType', index: 'ysType', width: 80 },
            { label: '项目类型', name: 'xmDizhi', index: 'xmDizhi', width: 80 },
            { label: '录入人', name: 'username', index: 'username', width: 80 },
            { label: '设计师', name: 'sjs', index: 'sjs', width: 80 },
            { label: '项目名称', name: 'sgTime', index: 'sgTime', width: 80 },
            { label: '材料品牌', name: 'clpp', index: 'clpp', width: 80 },
            { label: '监理人员', name: 'jlry', index: 'jlry', width: 80 },
            { label: '考核结果', name: 'khjg', index: 'khjg', width: 80 },
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page",
            rows:"limit",
            order: "order",
        },
        gridComplete:function(){
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
});

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            name: null
        },
        showList: true,
        title: null,
        dict: {},
        ysWater: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },


        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.ysWater = {};
        },
        update: function (event) {
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },




        saveOrUpdate: function (event) {
            vm.ysWater.ysType="油漆工装修验收";
            var url = vm.ysWater.id == null ? "sys/ysWater/save" : "sys/ysWater/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.ysWater),
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(index){
                            vm.reload();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        del: function (event) {
            var ids = getSelectedRows();
            if(ids == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/ysWater/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function(r){
                        if(r.code == 0){
                            alert('操作成功', function(index){
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getInfo: function(id){
            $.get(baseURL + "sys/ysWater/info/"+id, function(r){
                vm.ysWater = r.ysWater;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'name': vm.q.name},
                page:page
            }).trigger("reloadGrid");
        }
    }
});