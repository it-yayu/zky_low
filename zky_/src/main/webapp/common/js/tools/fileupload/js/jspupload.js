var aftModeName = $.parseJSON($("[name='allowFileType']").val());
var maxFileSize = $("[name='maxFileSize']").val();
$(function(){
    // 初始化及调用
    var fu = $.SxUpload.init('fine-uploader',{
        autoUpload:false,
        multiple : true,
        button : $('#confBuffon'),

        formatFileName : function(name) {
            //console.log(name);
            return name;
        },
        editFilename: {
            enabled: false
        },
        deleteFile: {
            enabled: true
        },
        validation : {
            itemLimit : 5 //默认上传文件数
        },
        modeName:'testmode',
        allowFileType:aftModeName,
        maxFileSize:maxFileSize
    });
    // 增加上传成功事件
    fu.on('complete',function(event,id,name,responseJSON,xhr){
        $(this).fineUploader('setDeleteFileParams',responseJSON,id);//设置删除文件参数为返回json串
        alert(responseJSON.serverName + "," + responseJSON.clientName);
    });
    // 给上传按钮增加点击上传事件
    $('#uploadButton').click(function(){
        //fu.fineUploader('uploadStoredFiles');//fineuploader原始调用方法的方式
        fu.upload();//fileupload.js中封装方法，方便拼写
    });
});