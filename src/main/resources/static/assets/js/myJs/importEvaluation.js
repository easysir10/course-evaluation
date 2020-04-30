var wb;//读取
var rABS = false;
var tb;

//开始导入
function importf(obj) {
    if(!obj.files) {
        return;
    }
    var f = obj.files[0];
    var reader = new FileReader();
    reader.onload = function(e) {
        var data = e.target.result;
        if(rABS) {
            wb = XLSX.read(btoa(fixdata(data)), {//手动转化
                type: 'base64'
            });
        } else {
            wb = XLSX.read(data, {
                type: 'binary'
            });
        }
        /**
         * wb.SheetNames[0]是获取Sheets中第一个Sheet的名字
         * wb.Sheets[Sheet名]获取第一个Sheet的数据
         */
        var excelJson = XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]]);

        var res = [];
        for (var i in excelJson) {
            var item = [];
            for (var j in excelJson[i]) {
                if (j==='评价时间') {
                    var num = excelJson[i][j];
                    item.push(formatDate(num,'/'))
                }else {
                    item.push(excelJson[i][j])
                }
            }
            res.push(item)
        }
        if(tb!=null){
            $('#preview-course-table').DataTable().destroy();
        }
        tb=$('#preview-evaluation-table').DataTable({
            data:res,
            "oLanguage": {
                "oPaginate": { "sPrevious": '<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-arrow-left"><line x1="19" y1="12" x2="5" y2="12"></line><polyline points="12 19 5 12 12 5"></polyline></svg>', "sNext": '<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-arrow-right"><line x1="5" y1="12" x2="19" y2="12"></line><polyline points="12 5 19 12 12 19"></polyline></svg>' },
                "sInfo": "Showing page _PAGE_ of _PAGES_",
                "sSearch": '<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-search"><circle cx="11" cy="11" r="8"></circle><line x1="21" y1="21" x2="16.65" y2="16.65"></line></svg>',
                "sSearchPlaceholder": "Search...",
                "sProcessing" : "正在加载数据，请稍后...",
                "sZeroRecords" : "没有数据！",
                "sEmptyTable" : "表中无数据存在！"
            },
            "pageLength": 5,
            "lengthMenu": [5, 10, 20, 50],
            "bAutoWidth":true,
            columns: [
                {
                    title: '评价人工号/学号'
                },
                {
                    title: '评价课程代码'
                },
                {
                    title: '评价时间'
                },
                {
                    title: '评价类型'
                },
                {
                    title: '得分'
                },
                {
                    title: '意见或建议'
                }]
        });
    };
    if(rABS) {
        reader.readAsArrayBuffer(f);
    } else {
        reader.readAsBinaryString(f);
    }
}

//文件流转BinaryString
function fixdata(data) {
    var o = "",
        l = 0,
        w = 10240;
    for(; l < data.byteLength / w; ++l) o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w, l * w +

        w)));
    o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w)));
    return o;
}

function formatDate(numb, format) {
    var time = new Date((numb - 1) * 24 * 3600000 + 1);
    time.setYear(time.getFullYear() - 70);
    var year = time.getFullYear() + '';
    var month = time.getMonth() + 1 + '';
    var date = time.getDate() + '';
    if(format && format.length === 1) {
        return year + format + month + format + date
    }
    return year+(month < 10 ? '0' + month : month)+(date < 10 ? '0' + date : date)
}