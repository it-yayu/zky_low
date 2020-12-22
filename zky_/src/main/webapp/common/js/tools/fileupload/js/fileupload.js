// JavaScript Document
/*******************************************************************************
 * NAME			:	文件上传
 * DESCRIPTION	:	文件上传
 * ARGUMENTS	: 	id:上传div id),settings:options
 * RETURN		:	上传组件对象
 * AUTHOR		:	ztw
 * COMPANY		:	www.bksx.cn
 * VERSION		:	V0.9
 ******************************************************************************/

(function($) {
    //初始化组件对象
    var SxUpload = {};
    var _SX_SETTINGS;

    //初始化对象可配置属性
    $.extend(SxUpload,{
        configuration:{
            endpoint			:	'upload',//upload file url
            acceptFiles 		: 	'.mp4,.zip,image/*,.mp3,application/zip',
            allowedExtensions	: 	[],
            sizeLimit			:	100000000,
            takePhotoConfig		:	{
                uploadPath		:	'/sxapp/upload',
                serverName		:	'130.10.7.238',
                port			:	8080
            }
        },
        TimeFn:null
    });

    //初始化上传组件默认参数对象
    var _default_conf = {
        autoUpload: true,
        //button : $('#confBuffon'),// html element Can not be <button>
        debug: true,
        disableCancelForFormUploads : false,
        maxConnections : 3,
        multiple : true,
        takePhoto:false,//
        //template: "qq-template",//default value
        thumbnails : {
            placeholders : {
                // waitingPath: "common/images/waiting-generic.png",
                // notAvailablePath: "common/images/not_available-generic.png"
            }
        },
        formatFileName : function(name) {
            return name;
        },
        camera : {
            ios : true
            // allow to access camera
        },
        request : {
            endpoint : SxUpload.configuration.endpoint,
            filenameParam : 'filename',
            forceMultipart : true,// defaults to true
            paramsInBody : true,// defaults to true,allow to send parameters
            uuidName : 'qquuid',// uuid parameter name ,default is qquuid
            totalFileSizeName : 'qqtotalfilesize',// default value
            params : {
                foo : '中文'
            }
        },
        validation : {
            //acceptFiles : SxUpload.configuration.acceptFiles,
            //allowedExtensions : SxUpload.configuration.allowedExtensions,
            itemLimit : 5,
            minSizeLimit : 0,
            sizeLimit : SxUpload.configuration.sizeLimit,
            stopOnFirstInvalidFile : true
        },
        deleteFile : {
            enabled : true, // defaults to false
            endpoint : SxUpload.configuration.endpoint,
            method : 'POST',
            params : {
                foo : '中文'
            }
        },
        chunking : {
            enabled : false,
            partSize : 102400,
            paramNames : {
                chunkSize : 'chunkSize'
            },
            concurrent : {
                enabled : false
            },
            success : {
                endpoint : SxUpload.configuration.endpoint+'?_method=concatFile'
            }
        },
        messages:{
            emptyError		:	'{file}是空文件,请重新选择文件!',
            noFilesError	:	'没有要上传的文件!',
            onLeave			:	'文件正在上传,离开此页面上传将会取消!',
            sizeError		:	'{file}大小超过最大文件限制{sizeLimit}!',
            typeError		:	'{file}类型不正确. 允许文件类型为: {extensions}!',
            tooManyItemsError:	'文件个数超过限制{itemLimit},已选择{netItems}个文件!',
            retryFailTooManyItemsError:'上传失败,文件个数到达上限!',
            noTakePhotoButtonError:'设置允许拍照上传，请设置拍照按钮!',
            noModeNameError:'模块参数modeName为空，请检查参数!',
            tooManyItemsError1:'文件上传个数超过限制!',
            tooManyTPItemsError:'拍摄图像个数超过限制，请上传后再拍照!',
            noPhotoToUploadError:'请先选择照片或点击拍照，拍摄图像!',
            uploadPathError:'拍照上传路径设置错误，请修改后重新上传!',
            noTakePhotoDeviceError:'未找到拍照设备!'
        }
    }

    //为SxUpload组件增加init方法，返回fineuploader组件对象
    $.extend(SxUpload,{
        init:function(id,settings){
            var _id = id;
            settings = settings || {};
            if(!settings.modeName){
                alert(_default_conf.messages.noModeNameError+'（上传组件id为：'+id+'）');
                return;
            }
            settings = $.extend(true,{},_default_conf, settings,{
                request:{
                    params:{
                        modeName:settings.modeName
                    }
                },
                chunking:{
                    success : {
                        endpoint : SxUpload.configuration.endpoint+'?_method=concatFile&modeName='+settings.modeName
                    }
                },
                validation : {
                    allowedExtensions : settings.allowFileType || SxUpload.configuration.allowedExtensions,
                    sizeLimit:settings.maxFileSize
                },
                callbacks:{
                    onComplete:function(id,name,responseJSON,xhr){
                        $('#'+_id).fineUploader('setDeleteFileParams',responseJSON,id);
                        if(!responseJSON.success){
                            $('#'+_id).fineUploader('getItemByFileId',id).find('.qq-upload-cancel-selector&&.qq-hide').removeClass('qq-hide');
                            if(responseJSON.errmsg)
                                alert(responseJSON.errmsg);
                        }
                    }
                }
            });

            _SX_SETTINGS = settings || {};
            //if allow take photo remove class hide css on button "photoButton"
            if(!settings.takePhoto){
                if(settings.photoButton){
                    settings.photoButton.addClass("hidecss");
                }
            }

            debugger;
            var fu = $('#' + id).fineUploader(settings);

            //initTakePhoto
            if(_SX_SETTINGS.takePhoto){
                $.SxUpload.initTakePhoto();
            }


            return fu;
        },
        addFiles:function(id,files){
            var	fu = $('#' + id),
                _uploadData = fu.fineUploader('getUploadData_sx'),
                _templating = fu.fineUploader('getTemplating_sx');

            //test data
            files = files || [/*{
				 "clientName" : "IMG_2015_08_27_11_35_16_104.jpg",
				 "modeName" : "testmode",
				 "serverName" : "6d093f0b-8723-4618-b7cd-88500bcef483.jpg",
				 "size" : "1261906",
				 "success" : "true",
				 "uuid" : "6d093f0b-8723-4618-b7cd-88500bcef483"
				 }
				 */];

            var values = [];
            $.each(files,function(i,value){
                if(!value.uuid)
                    return true;
                var val = {
                    uuid:value.uuid,
                    name:value.clientName,
                    size:value.size,
                    batchId:qq.getUniqueId(),
                    result:value
                };
                values.push(val);
            });

            $.each(values,function(i,value){
                //store id
                var id = _uploadData.addFile({
                    uuid : value.uuid,//qq.getUniqueId(),
                    name : value.name,
                    size : value.size,
                    batchId : value.batchId //qq.getUniqueId()
                });

                //resopnse xhr
                var xhr = {
                    readyState : 4,
                    //response : value.resultText,
                    //responseText : value.resultText,
                    status : 200,
                    statusText : 200,
                    timeout : 0
                };

                //add file to templating
                _templating.addFile(id,value.name);
                fu.fineUploader('storeForLater_sx', id);
                //delete img preview
                //if(qq.ie())
                //	_templating.generatePreview(id);
                $('.qq-thumbnail-selector').addClass('qq-hide');

                //change status
                fu.fineUploader('onComplete_sx', id, value.name,value.result, xhr);
                //trigger complete event
                fu.trigger('complete', [id, value.name, value.result, xhr]);
                //add delete file params
                //fu.fineUploader('setDeleteFileParams',value.result,id);
                _SX_SETTINGS.callbacks.onComplete.apply(this,[id, value.name, value.result, xhr]);
            });
        },
        jsonToStr:function(json){
            var result = [],
                type,
                flags = '{',
                flage = '}';

            try{
                type = json.constructor;
            }catch(e){}

            if(type === Array){
                flags = '[';
                flage = ']';
            }

            for(var j in json){
                if(/^(string|number)$/.test(typeof(json[j])))
                    result.push("\""+j+"\":\""+json[j]+"\"");
                else if(typeof(json[j]) === 'object')
                    result.push((type===Array?'':"\""+j+"\":")+$.SxUpload.jsonToStr(json[j]));
            }

            return flags+result.join(',')+flage;
        },
        showDiv:function(show_div,bg_div){
            document.getElementById(show_div).style.display='block';
            document.getElementById(bg_div).style.display='block' ;
            var bgdiv = document.getElementById(bg_div);
            bgdiv.style.width = document.body.scrollWidth;
            $("#"+bg_div).height($(document).height());
        },
        closeDiv:function(show_div,bg_div){
            $('#' + show_div + ',#' + bg_div).css('display','none');
        },
        dealPhotos:function(isupload){
            //设置控件上传路径
            var tpConfig = _SX_SETTINGS.takePhotoConfig || $.SxUpload.configuration.takePhotoConfig;
            if(!tpConfig.uploadPath || !tpConfig.serverName || !tpConfig.port){
                alert(_SX_SETTINGS.messages.uploadPathError);
                return;
            }
            camera.GetUrl($.SxUpload.jsonToStr(tpConfig));
            //
            var params = {modeName:_SX_SETTINGS.modeName,paths : [],dels : []},
                str,
                uids = [];

            var cks = $('div[class=photo_list] > ul > input');

            for(var i = 0;i<cks.length;i++){
                if(cks[i].checked&&isupload){
                    params.paths.push({photoPath:cks[i].value});
                    uids.push($(cks[i]).attr('uid'));
                }else{
                    params.dels.push({photoPath:cks[i].value});
                    uids.push($(cks[i]).attr('uid'));
                }
            }

            if(params.paths.length == 0 && isupload){
                alert(_SX_SETTINGS.messages.noPhotoToUploadError);
                $('#closetp').attr('class','qd_button');
                return;
            }

            $('#closetp').unbind('click');

            str = $.SxUpload.jsonToStr(params);

            str = camera.UploadPhotos(str);
            var result = jQuery.parseJSON(str);

            $.SxUpload.addFiles('fine-uploader',result);

            $.each(uids,function(i,value){
                $('[uid='+value+']').remove();
            });
            $.SxUpload.closeDiv('photo_content','fade');
        },
        takePhote:function(){
            var imglimit = _SX_SETTINGS.validation.itemLimit,
                sefiles = $('#fine-uploader').fineUploader('getUploads'),
                cks = $('div.photo_list ul input'),
                count = sefiles.length;

            $.each(sefiles,function(i,value){
                var st = value.status;
                if(st==qq.status.CANCELED||st==qq.status.DELETED){
                    count--;
                }
            });

            $.each(cks,function(i,value){
                if(value.checked)
                    count++;
            });
            if(count>=imglimit){
                alert(_SX_SETTINGS.messages.tooManyItemsError1);
                return;
            }
            if(cks.length>=4){
                alert(_SX_SETTINGS.messages.tooManyTPItemsError);
                return;
            }


            //拍照
            if (camera != null){
                var params = {modeName:_SX_SETTINGS.modeName,paths : [],dels : []},
                    str,
                    uid = qq.getUniqueId();

                var path = camera.Cap().replace(/\\\\|\\/g,'/');

                var html = '<li uid="'+uid+'" class="on"><a pid="#'+uid+'" ><img src=" file:///'+path+ '" width="106" height="78"></a></li>\
							<input style="display:none;" checked="true" uid="'+uid+'" name="" type="checkbox" value="'+path+'" class="photo_input">';

                var plist = $('div[class=photo_list] > ul').append(html);

                var html1 = '\
					<div uid="'+uid+'" id="'+uid+'" style="display:none;"><img src="file:///'+path+'"></div>';
                var preplist = $('.white_content').append(html1);

                $('.photo_list li[uid='+ uid +'] a').fancyZoom({scaleImg: true, closeOnClick: true});
            }
        },
        initTakePhoto:function(){
            /*******	拍照上传***********************************************/
            //弹出拍照区域
            $('#photoButton').click(function(){
                $.SxUpload.showDiv('photo_content','fade');
                $('#closetp').attr('class','qd_button');
                var camlist = jQuery.parseJSON(camera.ShowCamera());
                if(camlist.length>0)
                    camera.OpenCamera(0);
                else{
                    alert(_SX_SETTINGS.messages.noTakePhotoDeviceError);
                    $.SxUpload.closeDiv('photo_content','fade');
                    return;
                }

                if(camlist.length>1){
                    $('.sel_device').css('display','block');
                    $('#takephoto_device').html('');
                    $.each(camlist,function(i,value){
                        $('#takephoto_device').append('<option value="'+value.cameraID+'" '+(value.cameraID==0?'selected':'')+'>'+value.name+'</option>');
                    });
                    $('#takephoto_device').change(function(){
                        camera.OpenCamera($('#takephoto_device').val());
                    });
                }

                //上传文件
                $('#closetp').bind('click',function(){
                    $('#closetp').attr('class','uploading');
                    clearTimeout(uploadpic);
                    var uploadpic = setTimeout(function(){
                        $.SxUpload.dealPhotos(true);
                    },1000);
                });
            });
            //关闭上传区域
            $('.head_close').click(function(){
                $.SxUpload.dealPhotos();
            });

            //为拍摄图像增加选择事件
            $('.photo_list ul li').live('click',function(){

                clearTimeout($.SxUpload.TimeFn);
                var li_current = $(this);
                $.SxUpload.TimeFn = setTimeout(function(){
                    if(li_current.hasClass('on')){
                        li_current.removeClass('on');
                        li_current.next().removeAttr('checked');
                    }else{
                        li_current.addClass('on');
                        li_current.next().attr('checked','true');
                    }
                },500);

            });
            //增加图片预览到页面
            $('#takePhoto').click(function(){
                $.SxUpload.takePhote();
            });

            //拍摄区域可拖拽
            $('#photo_content').draggable({scroll: false});
            /*******	拍照上传结束***********************************************/
        }
    });

    //为jquery对象增加SxUpload命名空间
    $.extend({
        SxUpload:SxUpload
    });

    $.fn.extend({
        upload:function(){
            var $fu = $(this);
            $fu.fineUploader('uploadStoredFiles');
        }
    });
})(jQuery);
