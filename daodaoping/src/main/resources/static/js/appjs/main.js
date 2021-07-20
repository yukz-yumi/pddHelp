
var prefix = "/gotoo/warehouseInfo"
$(function() {
	//load();
});

function load() {
	$('#waitUpkeepTable')
			.bootstrapTable(
					{
						method : 'get', // 服务器数据的请求方式 get or post
						url : prefix + "/waitUpkeepList", // 服务器数据的加载地址
                    //  showExport: true,//是否支持导出表格
					//	showRefresh : true,
					//	showToggle : true,
					//	showColumns : true,
						iconSize : 'outline',
						toolbar : '#exampleToolbar',
						striped : true, // 设置为true会有隔行变色效果
                        rowStyle : rowStyle,//设置行样式
						dataType : "json", // 服务器返回的数据类型
						pagination : true, // 设置为true会在底部显示分页条
						// queryParamsType : "limit",
						// //设置为limit则会发送符合RESTFull格式的参数
						singleSelect : false, // 设置为true将禁止多选
						// contentType : "application/x-www-form-urlencoded",
						// //发送到服务器的数据编码类型
						pageSize : 10, // 如果设置了分页，每页数据条数
						pageNumber : 1, // 如果设置了分布，首页页码
						//search : true, // 是否显示搜索框
						showColumns : false, // 是否显示内容下拉框（选择显示的列）
						sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
						queryParams : function(params) {
							return {
								//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
								limit: params.limit,
								offset:params.offset
					           // name:$('#searchName').val(),
					           // username:$('#searchName').val()
							};
						},
						// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
						// queryParamsType = 'limit' ,返回参数必须包含
						// limit, offset, search, sort, order 否则, 需要包含:
						// pageSize, pageNumber, searchText, sortName,
						// sortOrder.
						// 返回false将会终止请求
						columns : [
								/*{
									title : '序号',
                                    formatter: function(value, row, index) {
                                        return index + 1;
                                    }
								},*/
																{
									field : 'f1',
									title : '设备名称'
								},
																{
									field : 'f3',
									title : '设备编号'
								},
																{
									field : 'f13',
									title : '保养周期（天）'
								},
                                {
                                    field : 'leftDays',
                                    title : '剩余天数'
                            }]
					});

    $('#waitMeasureTable')
        .bootstrapTable(
            {
                method : 'get', // 服务器数据的请求方式 get or post
                url : prefix + "/waitMeasureList", // 服务器数据的加载地址
                //  showExport: true,//是否支持导出表格
                //	showRefresh : true,
                //	showToggle : true,
                //	showColumns : true,
                iconSize : 'outline',
                toolbar : '#exampleToolbar',
                striped : true, // 设置为true会有隔行变色效果
                rowStyle : rowStyle,//设置行样式
                dataType : "json", // 服务器返回的数据类型
                pagination : true, // 设置为true会在底部显示分页条
                // queryParamsType : "limit",
                // //设置为limit则会发送符合RESTFull格式的参数
                singleSelect : false, // 设置为true将禁止多选
                // contentType : "application/x-www-form-urlencoded",
                // //发送到服务器的数据编码类型
                pageSize : 10, // 如果设置了分页，每页数据条数
                pageNumber : 1, // 如果设置了分布，首页页码
                //search : true, // 是否显示搜索框
                showColumns : false, // 是否显示内容下拉框（选择显示的列）
                sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
                queryParams : function(params) {
                    return {
                        //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                        limit: params.limit,
                        offset:params.offset
                        // name:$('#searchName').val(),
                        // username:$('#searchName').val()
                    };
                },
                // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
                // queryParamsType = 'limit' ,返回参数必须包含
                // limit, offset, search, sort, order 否则, 需要包含:
                // pageSize, pageNumber, searchText, sortName,
                // sortOrder.
                // 返回false将会终止请求
                columns : [
                    /*{
                        title : '序号',
                        formatter: function(value, row, index) {
                            return index + 1;
                        }
                    },*/
                    {
                        field : 'f1',
                        title : '设备名称'
                    },
                    {
                        field : 'f3',
                        title : '设备编号'
                    },
                    {
                        field : 'f15',
                        title : '计量周期（天）'
                    },
                    {
                        field : 'leftDays',
                        title : '剩余天数'
                    }]
            });
}
function reLoad() {
	$('#waitUpkeepTable').bootstrapTable('refresh');
}

//行样式设置方法
function rowStyle(row, index) {
    var style = {};
    if (row.leftDays <= 10 && row.leftDays >=7) {
        style={css:{'background-color':'#1b9907','color':'#000000'}};
    } else if (row.leftDays <= 6 && row.leftDays >=3){
        style={css:{'background-color':'#ffff00','color':'#000000'}};
    } else if (row.leftDays <= 2){
        style={css:{'background-color':'#ff0000','color':'#000000'}};
	}
    return style;
}

//步骤详情行样式设置方法
function detailRowStyle(row, index) {
    var style = {};
    if (row.status == 0) {
        style={css:{'background-color':'#ff0000','color':'#000000','font-weight':'bold'}};
    } else {
        style={css:{'background-color':'#1b9907','color':'#000000'}};
    }
    return style;
}



function resetPwd(id) {
}