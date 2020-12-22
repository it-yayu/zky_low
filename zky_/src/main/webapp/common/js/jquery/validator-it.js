//==============================================================================================//
//================Intelligent Tools(智能工具,全称:客户端智能验证工具)-1.3.5=====================//
//======================================作者：赵冠锦============================================//
//======================================鸣谢：陈跃鹏============================================//
//======================================QQ：86066292============================================//
//====================================日期：2012-10-12==========================================//
//		现有功能:																				//
//				1.页面全选效果的自动绑定														//
//				2.页面复选框的全选+互斥效果的自动绑定											//
//				3.金额的自动转换																//
//				4.表单的校验(包含非空,非法字符,特殊格式校验,有效性校验)							//
//				5.页面禁止输入特殊字符(包含<>和'还有shift+insert)								//
//				6.自动进行最大长度的输入限制(特殊格式,如组织机构代码不进行此限制)				//
//				7.按下回车,则焦点跳转到下一个文本域												//
//==============================================================================================//
//		名词解释:																				//
//				1.复选框:类型为checkbox的input元素												//
//				2.全选复选框:用来实现点击之后全部选中或者全部不选中的复选框						//
//				3.要控制的复选框:即全选复选框,点击之后,选中或不选中的哪些复选框的统称			//
//																								//
//			注意:																				//
//				1.标注为基础方法的.禁止在页面调用,在不能彻底明白本工具的原理时,禁止任何修改!	//
//==============================================================================================//
//		更新记录:																				//
//				1.0.0: 2012-09-21 创建此文件,增加全选效果自动绑定								//
//				1.1.0: 2012-09-24 增加互斥效果的自动绑定和金额的自动转换						//
//				1.1.1: 2012-09-25 增加表单的自动校验,修复了之前遗留的bug						//
//				1.2.0: 2012-09-27 增加默认加载部分的功能,并优化了运行方式,增加全局变量设置   	//
//				1.2.1: 2012-09-28 1.增加有效性的校验,修复了测试时候发现的BUG				   	//
//								  2.更新全选功能,支持同页面多个该事件的绑定,优化其绑定方式		//
//				1.2.2: 2012-10-12 1.修复了特殊类型校验的BUG									   	//
//								  2.更新有效性校验,增加包含和不包含,增加了指定name或者value设置 //
//								  3.新增另一套有效性校验可识别的字符(例如:==等同于eq)			//
//				1.3.0: 2012-10-15 1.更新有效性校验,增加支持表达式(日期和数值)				   	//
//								  2.更新有效性校验,增加对逻辑表达式的支持						//
//								  3.增加自定义属性notnext,可针对某一文本域设置按下回车换行		//
//				1.3.1: 2012-10-18 1.增加失去焦点后,进行校验									   	//
//								  2.增加失去焦点跳转指定连接的功能.								//
//								  3.增加自定义验证类型(类型为:default)							//
//				1.3.2  2012-10-25 1.增加必须输入正确才能跳转的设置(mustright)					//
//								  2.修复金额位数转换时,小数点后全是0的校验.						//
//								  3.修复验证非空时,不能验证换行符,制表符等						//
//								  4.增加调试码,对应设置错误的信息,直接搜索调试码定位			//
//								  5.优化提示信息,更人性化										//
//				1.3.3  2012-11-30 1.优化对非法字符的校验										//
//								  2.新增Integer和double两种特殊校验类型.						//
//				1.3.4  2012-12-18 1.优化对非法字符的校验,与服务器过滤器一致.					//
//								  2.修复如果禁用依然获取焦点的BUG.								//
//								  3.新增自定义校验类型,支持数据Array							//
//				1.3.5  2013-03-25 1.修复对部分特殊身份证号校验不通过的BUG.						//
//==============================================================================================//


//文档加载完毕,开始初始化
$(document).ready(function(){
    _init_load();
});

//==============================================================================//
//各配置属性																	//
//==============================================================================//
var _init_stop_the_char="true";//是否禁用字符
var _init_enter_next_focus="true";//是否按下回车自动跳转到下一个文本域
var _init_control_the_maxlength="true";//是否需要自动控制其最大输入长度
var _init_hcbz_auto_back="true";//是否需要互斥备注禁用时候,如果清空,则下次自动恢复上次的值
var _init_format_not_max=new Array("zzjgdm","dateymd","dateym");//所有特殊校验格式的class属性值
var _it_codedata_objectpath="";
//==============================================================================//
//各自定义属性名的初始化														//
//==============================================================================//
var _init_qxcbo_cboname_name="cboname";//全选复选框,指定其要控制的复选框的属性名

var _init_hccbo_hcgroup_name="hcgroup";//互斥全选复选框,表明其为互斥复选框的id属性值(以此值为开头)

var _init_hccbo_hcflag_name="hcflag";//互斥时候,表明该复选框的互斥标识的属性名
var _init_hccbo_id_value="_hc_cbo_";//互斥全选复选框,表明其为互斥复选框的id属性值(以此值为开头)
var _init_hccbo_bzdisabled_name="disableflag";//互斥时候,表明要禁用的复选框的属性名(其值范围是:空(不禁用),和两个互斥复选框的hcflag值)
var _init_hccbo_bzid_value="bz_";//互斥时候,表明备注框的id属性值(以此值为开头)(格式:bz_加上对应复选框的下标)
var _init_hccbo_bznotclear_name="notclear";//互斥时候,表明禁用备注框时候,是否清空其值的属性名(值为true则不清空,否则清空)

var _init_money_jqws_name="jqws";//自动转化金额时,精确位数的标示,同时也是自动绑定该方法的标示(不是有效正整数,则默认为2位有效数字)

var _init_validate_flag_name="valiflag";//表单校验时,指定校验规则的属性名
var _init_validate_validate="validate";//表单校验类型(值为:blur或者submit)
var _init_blur_url="url";//onblur时跳转的链接
var _init_blur_paramter="paramter";//失去焦点跳转时,传递的参数
var _init_blur_notdisabled="notdisabled";//失去焦点跳转回来.不禁用文本框
var _init_blur_disclass="disclass";//失去焦点跳转回来,如果禁用文本框,禁用的class样式名字,默认是"inputline"
var _init_blur_disabled_btn_name="disablebtn";//失去焦点跳转时,要禁用的按钮名字
var _init_right_move="mustright";//设置必须输入正确才能离开(在需要失去焦点跳转时或者form的validate属性设置为blur时候有效)
var _init_validate_msgtype_name="msgtype";//表单校验时,指定提示信息类型的属性名(值为:1.alert提示,2.右侧文字提示)
var _init_validate_msg_value="_it_auto_msg_span";//右侧提示信息的span标签的name属性值
var _init_validate_relation_name="relation";//表单校验时,设置有效性校验的属性名
var _init_validate_relation_msg_name="relmsg";//表单校验时,设置有效性的提示信息
var _init_validate_reg_msg_name="regmsg";//表单校验时,正则表达式不成立的自定义提示信息

var _init_config_enter_not_next="notnext";//按下回车,不自动换行

var _init_codedata_codecolumn="codecolumn";//自动补全,代码表名称的属性名称
var itCode=new _It_CodeData();//自动补全,选中后回写的对象
var customMethod=new Array();
//==============================================================================//
//初始化方法,在此设置															//
//==============================================================================//
function _init_load(){
    _init_loadconfig();//初始化配置
    _init_loadqx();//初始化全选事件
    _init_loadhc();//初始化互斥事件
    _init_loadjy();//初始化校验事件
    _init_loadqt();//初始化其他事件
    _init_loadcode();//初始化代码表加载
    _init_loadgrouphc();//初始化单独互斥事件
}
//==============================================================================//
//各配置属性设置																//
//==============================================================================//

//设置是否设置禁用字符输入(默认为true)
function setStopChar(_value){
    if(_value+""!="true"&&_value+""!="false"){
        _value="true";
    }
    _init_stop_the_char=_value+"";
}
//设置是否设置回车跳转下一个文本域(默认为true)
function setEnterNext(_value){
    if(_value+""!="true"&&_value+""!="false"){
        _value="true";
    }
    _init_enter_next_focus=_value+"";
}

//设置是否设置自动限制输入字符个数(默认为true)
function setControlLength(_value){
    if(_value+""!="true"&&_value+""!="false"){
        _value="true";
    }
    _init_control_the_maxlength=_value+"";
}

//设置是否设置互斥备注清空后,下次自动恢复(默认为true)
function setHcbzBack(_value){
    if(_value+""!="true"&&_value+""!="false"){
        _value="true";
    }
    _init_hcbz_auto_back=_value+"";
}

//添加特殊的格式样式
function addFormat(_value){
    _init_format_not_max[_init_format_not_max.length]=_value;
}

//设置用户自定义校验类型(支持正则和字符串)
function setDefault(_value){
    _validate_type_default=_value;
}
function setDefault1(_value){
    _validate_type_default1=_value;
}
function setDefault2(_value){
    _validate_type_default2=_value;
}
function setDefault3(_value){
    _validate_type_default3=_value;
}


//设置用户路径
function setPath(path){
    _it_codedata_objectpath=path;
}
//==============================================================================//
//初始化配置(回车跳转下个焦点,设置最大输入长度)									//
//==============================================================================//
function _init_loadconfig(){
    $(":input:enabled").live("keydown",function(_event){
        var _key_code=_event.keyCode?_event.keyCode:_event.which?_event.which:_event.charCode;
        //如果文本框类型为文件,则按下退格键和delete键时,清空该文本值
        if($(this).attr("type")=="file"){
            if(_key_code==8||_key_code==46){
                $(this).select();
                return true;
            }
        }
        //判断按下的是否是禁用字符
        if(!_is_stop_char(_event)){
            return false;
        }
        //设置输入值不能超过最大长度
        if(_key_code!=13){
            if(_init_control_the_maxlength=="true"){

                var _validate_flag=$(this).attr(_init_validate_flag_name);
                if(_validate_flag+""!="undefined"&&_validate_flag!=""){
                    var _validate_properties=_validate_flag.split(",");
                    var _data_length=_to_trim_all(_validate_properties[0])+"";
                    var _class_name=(_to_trim_all($(this).attr("class")+"")+"").toLowerCase();
                    var _htcurl=_to_trim_all($(this).attr("htcurl")+"");
                    if(_htcurl+""!="undefined"&&_htcurl!=""){
                        return true;
                    }
                    //如果是特殊格式的,则不进行长度限制
                    for(var i=0;i<_init_format_not_max.length;i++){
                        if(_to_trim_all(_init_format_not_max[i])==_class_name){
                            return true;
                        }
                    }
                    if(_data_length+""!="undefined"&&_data_length!=""&&_is_number(_data_length)==true){
                        $(this).attr("maxlength",_data_length);
//						if(2=1){
//							var _is_to_be_max=_control_the_maxlength($(this).val(),_data_length,_event);
//							if(_is_to_be_max==false){
//								return false;
//							}
//						}
                    }
                }
            }
        }else if(_init_enter_next_focus=="true"){
            var _not_next=$(this).attr(_init_config_enter_not_next);
            if(_not_next+""!="undefined"&&_not_next!="false"){
                return true;
            }
            _event.preventDefault();//阻止浏览器默认行为
            var _next_index=$(":input:enabled").index(this)+1;
            $(":input:enabled:eq("+_next_index+")").focus();
            return false;
        }
        return true;
    });
}

//控制最大长度
function _control_the_maxlength(_input_value,_maxlength,_event){
    var _key_code=_event.keyCode?_event.keyCode:_event.which?_event.which:_event.charCode;
    if(_byte_count(_input_value)>=_maxlength){
        if(_key_code>47||_key_code==32)
            return false;
        else
            return true;
    }
    return true;
}

function _is_stop_char(_event){//禁用字符
    if(_init_stop_the_char=="true"){//如果需要禁用字符,则进行禁用

        var _key_code=_event.keyCode?_event.keyCode:_event.which?_event.which:_event.charCode;
        if(_event.shiftKey){//'<','>'还有shift+insert
            if(_key_code==190||_key_code==188||_key_code==45){
                return false;
            }
        }
        if(_key_code==222){//"'"字符
            return false;
        }
    }
    return true;
}
//==============================================================================//
//判断是否需要加载全选,如果需要,则加载,否则 跳过								//
//==============================================================================//
function _init_loadqx(){

    //如果没有指定格式的全选按钮,则直接返回
    if($("["+_init_qxcbo_cboname_name+"]").length==0){
        return;
    }
    $("["+_init_qxcbo_cboname_name+"]").each(function(i){
        var _qx_cboname=_to_trim_all($(this).attr(_init_qxcbo_cboname_name));
        if(_qx_cboname+""!="undefined"&&_qx_cboname!=""){
            $(this).live("click",function(){_auto_qxuan_temp(_qx_cboname,$(this));});
            var $qx_cbo=$(this);
            $("input[name='"+_qx_cboname+"']").each(function(i){
                if($(this).get(0).disabled!=true){
                    $(this).live("click",function(){_auto_xzcjl_temp(_qx_cboname,$qx_cbo);});
                }
            });
        }
    });

}
//==============================================================================//
//判断是否需要加载单独互斥,如果需要,则加载,否则 跳过							//
//==============================================================================//
function _init_loadgrouphc(){
    //如果没有指定格式的全选按钮,则直接返回
    if($("["+_init_hccbo_hcgroup_name+"]").length==0){
        return;
    }
    $("["+_init_hccbo_hcgroup_name+"]").each(function(i){
        var _qx_groupname=_to_trim_all($(this).attr(_init_hccbo_hcgroup_name));
        $(this).live("click",function(){_auto_grouphc(_qx_groupname,$(this));});
    });
}


//==============================================================================//
//判断是否需要加载互斥,如果需要,则加载,否则 跳过							    //
//==============================================================================//
var _thistype="";//互斥第一个复选框的hcflag属性值
var _othertype="";//互斥第二个复选框的hcflag属性值
var _autodisabled_flag="";//禁用复选框的属性标识
var _notclear="false";//是否要清空
var _clear_bz_array=new Array();//备注的数组
function _init_loadhc(){
    var _hc_qxcbo=$("input[id^='"+_init_hccbo_id_value+"']");
    //如果没有两个指定格式的全选按钮,则直接返回
    if(_hc_qxcbo.length!=2){
        return;
    }
    var i=0;
    //设置type
    $("input[id^='"+_init_hccbo_id_value+"']").each(function(i){
        var _thisId=$(this).attr("id");
        //var _otherId=$(this).attr(_init_hccbo_otherid_name);
        var _cbo_flag=_thisId.substring(_thisId.lastIndexOf('_')+1);
        if(i==0){
            _thistype=_cbo_flag;
            i=i+1;
        }else{
            _othertype=_cbo_flag;
        }
    });

    //设置禁用备注文本框的相关字段
    if($("["+_init_hccbo_bzdisabled_name+"]").length>0){
        _autodisabled_flag=$("["+_init_hccbo_bzdisabled_name+"]").attr(_init_hccbo_bzdisabled_name);
        _notclear=$("["+_init_hccbo_bzdisabled_name+"]").attr(_init_hccbo_bznotclear_name);
        if(_notclear==""){
            _notclear="true";
        }
    }

    //循环遍历设置单击事件
    $("input[id^='"+_init_hccbo_id_value+"']").each(function(i){
        var _thisId=$(this).attr("id");
        var _otherId=_init_hccbo_id_value+_thistype;
        var _cbo_flag=_thisId.substring(_thisId.lastIndexOf('_')+1);
        if(_cbo_flag==_thistype){
            _otherId=_init_hccbo_id_value+_othertype;
        }
        $(this).live("click",function(){
            _hcqx_get(_thisId,_otherId);
        });
        $("input["+_init_hccbo_hcflag_name+"='"+_cbo_flag+"']").each(function(i){
            var _temp_type=_thistype;
            if($(this).attr(_init_hccbo_hcflag_name)==_thistype){
                _temp_type=_othertype;
            }
            if(_notclear!="true"&&_init_hcbz_auto_back=="true"){
                var _temp_index=$(this).attr("id").substring($(this).attr("id").lastIndexOf("_")+1);
                _clear_bz_array[_temp_index]=$("#"+_init_hccbo_bzid_value+_temp_index).val();
            }
            if($(this).get(0).disabled!=true){
                $(this).live("click",function(){_hcxz_get($(this),_temp_type);});
            }
        });
    });
}

//==============================================================================//
//判断是否需要加载失去焦点进行校验,如果需要,则加载,否则 跳过				    //
//==============================================================================//
var _this_input_is_right=true;//失去焦点校验时,这个文本框是否成功
function getIsRight(){
    return _this_input_is_right;
}
var _default_value_array=new Array();
var _default_value_blur=new Array();
var _default_blurinput_value="";
function _init_loadjy(){
    for(var kk=0;kk<$("["+_init_validate_validate+"]").length+1;kk++){
        _default_value_blur[kk]="-";
        _default_value_array[kk]="-";

    }
    $("["+_init_validate_validate+"]").each(function(i){
        var _validate=$(this).attr(_init_validate_validate);
        if(_validate=="blur"){
            var _form_name=$(this).attr("name");
            $("form[name='"+_form_name+"'] ["+_init_validate_flag_name+"]").each(function(i){
                if($(this).attr("disabled")+""!="disabled"){
                    var _load_blur="true";
                    var _url=$(this).attr(_init_blur_url);
                    //如果设置了失去焦点跳转,则不进行此绑定.进行另一种
                    if(_url+""!="undefined"&&_to_trim_all(_url)!=""){
                        _load_blur="false";
                    }
                    if(_load_blur=="true"){
                        $(this).blur(function(){
                            _this_input_is_right=_on_blur_validate(_form_name,$(this),"normal",i);
                        });
                    }
                    $(this).focus(function(i){
                        var _this_config=_validate_loadconfig($("form[name='"+_form_name+"']"));
                        if(_this_config[0]+""=="2"){
                            _auto_msg_rightline_clear_by_input($(this));
                        }
                    });
                }
            });
        }
    });
    $("["+_init_blur_url+"]").each(function(i){
        _default_blurinput_value=$(this).val();
        if($(this).attr(_init_blur_notdisabled)+""!="undefined"&&_to_trim_all($(this).attr(_init_blur_notdisabled))!="false"){
        }else{
            //如果该文本框有值,则不绑定(因为此时代表不需要进行onblur跳转了)
            if(_to_trim_all($(this).val())!=""){
                var _class="inputline";
                var _class_temp=$(this).attr(_init_blur_disclass);
                if(_class_temp+""!="undefined"&&_to_trim_all(_class_temp)!=""){
                    _class=_class_temp;
                }
                $(this).addClass(_class);
                $(this).attr("readonly",true);
                return;
            }
        }
        //获取所属表单名字
        var _form_name=$(this).parents("form").attr("name");
        //绑定onblur事件
        $(this).blur(function(){
            var _is_right=_on_blur_validate(_form_name,$(this),"tz",i);
            if(_is_right==true){
                //如果值为空,则不进行跳转
                if(_to_trim_all($(this).val())==""||_default_blurinput_value==_to_trim_all($(this).val())){
                    return;
                }
                var _url=$(this).attr(_init_blur_url);
                if(_url+""!="undefined"&&_to_trim_all(_url)!=""){
                    //链接拼接上本文本域的值
                    if(_url.indexOf("?")==-1){
                        _url+="?";
                    }else{
                        _url+="&";
                    }
                    _url+=$(this).attr("name")+"="+$(this).val();
                    //判断用户是否设置了参数
                    var _paramter=$(this).attr(_init_blur_paramter);
                    if(_paramter+""!="undefined"&&_to_trim_all(_paramter)!=""){
                        var _params=_paramter.split(",");
                        var _param=new Array();
                        var _index_temp;
                        for(var i=0;i<_params.length;i++){
                            _url+="&"+_params[i];
                        }
                    }
                    var _dis_btn=$(this).attr(_init_blur_disabled_btn_name);
                    //判断是否需要禁用按钮
                    if(_dis_btn+""!="undefined"&&_to_trim_all(_dis_btn)!=""){
                        var _btns=_dis_btn.split(" ");
                        var _btn_info=new Array();
                        for(var j=0;j<_btns.length;j++){
                            if(_to_trim_all(_btns[j])==""){
                                continue;
                            }
                            _btn_info=_btns[j].split(",");
                            if(_btn_info[1]+""!="undefined"&&_to_trim_all(_btn_info[1])!=""){
                                $("[name='"+_btn_info[0]+"']").val(_btn_info[1]);
                            }
                            $("[name='"+_btn_info[0]+"']").attr("disabled",true);
                        }
                    }
                    window.location.href=_url;
                }
            }
        });
        $(this).focus(function(i){
            var _this_config=_validate_loadconfig($("form[name='"+_form_name+"']"));
            if(_this_config[0]+""=="2"){
                _auto_msg_rightline_clear_by_input($(this));
            }
        });
    });
}

//失去焦点时候,执行的校验
function _on_blur_validate(_form_name,_input,_type,_index){
    var _this_config=_validate_loadconfig($("form[name='"+_form_name+"']"));
    var _msg="";
    var _validate_type="";
    var _data_length="";
    _validate_flag=_input.attr(_init_validate_flag_name);
    var _validate_properties=_validate_flag.split(",");
    _data_length=_validate_properties[0];//最大长度
    _validate_type=_validate_properties[1];//校验类型
    _msg=_validate_properties[2];//提示信息
    var _is_canbe_empty=_validate_properties[3];//是否为空
    if(_is_canbe_empty+""=="undefined"||_to_trim_all(_is_canbe_empty)==""){
        var _star_index=_get_the_msg_havezf(_input).indexOf("*");
        _is_canbe_empty="true";
        if(_star_index!=-1){
            _is_canbe_empty="false";
        }
    }
    //如果提示信息为空,则默认查找文本框前的文字
    if(_msg+""=="undefined"||_to_trim_all(_msg)==""){
        _msg=_get_the_msg(_input);
    }
    if(_is_number(_data_length)==false){
        _data_length=-1;
    }
    //alert提示.则只在值改变时才进行校验
    if(_this_config[0]=="1"){
        //如果值等于之前的值,则不进行校验
        var default_value=_default_value_array[_index];
        if (_type=="tz"){
            default_value=_default_value_blur[_index];
        }
        if(default_value==undefined){
            default_value="";
        }
        if(_input.attr(_init_right_move)+""=="undefined"){
            if(_input.val()==default_value){
                return false;
            }
        }

        if (_type=="tz"){
            _default_value_blur[_index]=_input.val();
        }else{
            _default_value_array[_index]=_input.val();
        }
    }
    //校验
    var _is_right=_get_validate_result(_input,_msg,_data_length,_validate_type,_is_canbe_empty,_this_config);
    return _is_right;

}
//==============================================================================//
//加载校验的配置信息   														    //
//==============================================================================//
function _validate_loadconfig(_validate_form){
    var _msg_type="";
    var _msg_color="";
    //加载提示类型,如果没有设置,则默认为alert提示
    if($("["+_init_validate_msgtype_name+"]").length==0){
        _msg_type='1';
        return new Array(_this_config[0]);
    }
    var _msg_type_temp=$("["+_init_validate_msgtype_name+"]").attr(_init_validate_msgtype_name).split(",");
    if(_msg_type_temp[0]=='2'){
        _msg_type='2';
        var _valiedate_color=_to_trim_all(_msg_type_temp[1]);
        if(_valiedate_color+""!="undefined" && _valiedate_color!=""){
            _msg_color=_valiedate_color;
        }else{
            _msg_color="#FF0000";//默认为红色
        }
    }else{
        _msg_type='1';
    }
    return new Array(_msg_type,_msg_color);
}

//==============================================================================//
//表单校验	(datalength值如果不是自然数,则不进行任何校验)						//
//==============================================================================//

function validate(_form_name){
    //如果没有指定格式的,则跳过校验
    if($("form[name='"+_form_name+"'] ["+_init_validate_flag_name+"]").length==0){
        alert("401:请确定是否正确指定了格式[即:"+_init_validate_flag_name+"属性]!");
        return false;
    }
    //清空之前的提示信息
    _auto_msg_rightline_clear($("form[name='"+_form_name+"']"));
    //加载配置信息
    var _this_config=_validate_loadconfig($("form[name='"+_form_name+"']"));
    var _msg="";
    var _validate_type="";
    var _data_length="";
    var _is_right=true;
    $("form[name='"+_form_name+"'] ["+_init_validate_flag_name+"]").each(function(i){

        _validate_flag=$(this).attr(_init_validate_flag_name);
        var _validate_properties=_validate_flag.split(",");
        _data_length=_validate_properties[0];
        _validate_type=_validate_properties[1];
        _msg=_validate_properties[2];
        var _is_canbe_empty=_validate_properties[3];
        //alert("长度:"+_data_length+"  信息:"+_msg+"  验证类型:"+_validate_type);
        if(_msg+""=="undefined"||_to_trim_all(_msg)==""){
            _msg=_get_the_msg($(this));
        }
        if(_is_number(_data_length)==false){
            _data_length=-1;
        }
        if(_is_canbe_empty+""=="undefined"||_to_trim_all(_is_canbe_empty)==""){
            var _star_index=_get_the_msg_havezf($(this)).indexOf("*");
            _is_canbe_empty="true";
            if(_star_index!=-1){
                _is_canbe_empty="false";
            }
        }
        _default_value_array[i]=$(this).val();
        //校验
        var _this_is_right=_get_validate_result($(this),_msg,_data_length,_validate_type,_is_canbe_empty,_this_config);
        if(_this_is_right==false){
            _is_right=false;
            if(_this_config[0]=="1"){
                return false;
            }
        }
    });
    return _is_right;

}
//默认的校验,包含,非空,非法字符,长度
function _get_validate_result(_input,_msg,_data_length,_validate_type,_is_canbe_empty,_this_config){
    var _input_value=_input.val();
    //校验开始,验证是否为空
    if(_is_canbe_empty=="false"){
        if(_to_trim_all(_input_value)==""){
            var showmsg=_msg+"不能为空!";
            if(_input.children("option").length>0){
                showmsg="请选择"+_msg+"!";
            }
            _auto_showmsg(_input,showmsg,_this_config);
            return false;
        }
    }
    if(_validate_type+""=="undefined"||_to_trim_all(_validate_type)==""){
        //验证是否有非法字符
        if(_auto_validate_ffzf(_input,_msg,"full",_this_config)==false){
            return false;
        }
    }else{
        var _validate_type_array=_validate_type.split(" ");
        var _the_validate_is_right="true";
        for(var i=0;i<_validate_type_array.length;i=i+1){
            //指定类型的校验
            if(_to_trim_all(_validate_type_array[i])==""){
                continue;
            }
            if(_auto_validate_by_type(_input,_msg,_data_length,_to_trim_all(_validate_type_array[i]),_this_config)==false){
                _the_validate_is_right="false";
                return false;
            }
        }
        if(_the_validate_is_right=="false"){
            return false;
        }
    }

    //验证长度是否超限
    if(_check_length(_input,_msg,_data_length,_this_config)==false){
        return false;
    }
    var _relation=_input.attr(_init_validate_relation_name);
    //验证有效性
    if(_relation+""!="undefined" && _to_trim_all(_relation)!=""){
        if(_check_relation(_input,_msg,_this_config)==false){
            return false;
        }
    }
    return true;
}

//检查有效性
function _check_relation(_input,_msg,_this_config){
    var _relation=_input.attr(_init_validate_relation_name);
    var _new_relation=_relation;
    var _relation_exps=new Array();//存放每一个表达式
    var _relation_result=new Array();//每个表达式的成立结果
    var _relation_andor=new Array();//存放逻辑表达式
    var _andor_temp=_is_setting_andOr(_new_relation,"andor");
    var _temp_i=0;
    //设置逻辑表达式
    while(_andor_temp!=""){
        //设置逻辑字符
        _relation_andor[_temp_i]=_andor_temp;
        //获取逻辑字符前的表达式
        _relation_exps[_temp_i]=_new_relation.substring(0,_new_relation.indexOf(_andor_temp));
        _new_relation=_new_relation.substring(_new_relation.indexOf(_andor_temp)+_andor_temp.length);
        _temp_i++;
        _andor_temp=_is_setting_andOr(_new_relation,"andor");
    }
    _relation_exps[_temp_i]=_new_relation;

    //校验所有的表达式
    for(var i=0;i<_relation_exps.length;i++){
        _relation_result[i]=_the_exp_is_right(_input,_relation_exps[i],_msg);
    }
    var _is_right_result=_relation_result[0];
    //根据表达式校验结果,验证逻辑表达式
    for(var i=0;i<_relation_andor.length;i++){
//		alert(_is_right_result+" "+_relation_andor[i]+" "+_relation_result[i+1]);
        if(_relation_andor[i]=="and"){
            if(_is_right_result==true&&_relation_result[i+1]==true){
                _is_right_result=true;
            }else{
                _is_right_result=false;
            }
        }else{
            if(_is_right_result==true||_relation_result[i+1]==true){
                _is_right_result=true;
            }else{
                _is_right_result=false;
            }
        }
    }

    if(_is_right_result==false){
        var _show_msg=_input.attr(_init_validate_relation_msg_name);
        if(_to_trim_all(_show_msg)==""||_show_msg+""=="undefined"){
            _show_msg=_msg+"的值不符合表达式条件!";
        }
        _auto_showmsg(_input,_show_msg,_this_config);
        return false;
    }
    return true;
}

//判断该表达式是否成立
function _the_exp_is_right(_input,_relation_exp,_msg){
    var _relation=$.trim(_relation_exp);
    var _relation_temp=_relation.split(" ");
    var _the_relation=_to_trim_all(_relation_temp[0]).toLowerCase();
    var _input_value=(_input.val()+"").replace(/-/g,"");
    if(_input_value==""){
        return true;
    }
    var _value_or_name=_relation_temp[2]+"";
    var _the_value=_to_trim_all((_relation_temp[1]+"").replace(/-/g,""));
    var _the_msg="["+_the_value+"]";//另一个文本框的提示信息,默认是值
    _the_value=_the_value.replace(/_/g,"");
    if(_the_value==""){
        return true;
    }
    if(_value_or_name=="val"){
    }else if(_value_or_name=="exp"){
        var _exp_values=new Array();//每个值
        var _exp_char=new Array();//符号(+或者-)
        var _exp=_relation_temp[1];
        var _temp_exp_char=_is_setting_andOr(_exp,"+-");
        var _temp_i=0;
        while(_temp_exp_char!=""){
            _exp_char[_temp_i]=_temp_exp_char;
            _exp_values[_temp_i]=_exp.substring(0,_exp.indexOf(_temp_exp_char));
            _temp_i++;
            _exp=_exp.substring(_exp.indexOf(_temp_exp_char)+_temp_exp_char.length);
            _temp_exp_char=_is_setting_andOr(_exp,"+-");
        }
        _exp_values[_temp_i]=_exp;
        var _is_date_temp=false;
        for(var i=0;i<_exp_values.length;i++){
            if(_the_exp_is_date(_exp_values[i])==true){
                _is_date_temp=true;
                break;
            }
        }

        //处理日期类型
        if(_is_date_temp==true){
            var _value_date=_to_trim_all($("[name='"+_exp_values[0]+"']").val()+"").replace(/-/g,"");
            //如果对方值为空,则跳过此校验
            if(_value_date==""||_date_temp=="undefined"){
                return true;
            }

            var _last_char="";
            var _integer_temp=0;
            var _date_temp=_value_date;
            //循环遍历整个表达式
            for(var i=1;i<_exp_values.length;i++){
                //如果该元素属于日期字符
                if(_the_exp_is_date(_exp_values[i])==true){
                    //获取最后一个字符
                    _last_char=_exp_values[i].substring(_exp_values[i].length-1).toLowerCase();
                    if(_exp_char[i-1]=="-"){
                        _integer_temp=parseInt(_exp_values[i].substring(0,_exp_values[i].length-1),10)*-1;
                    }else if(_exp_char[i-1]=="+"){
                        _integer_temp=parseInt(_exp_values[i].substring(0,_exp_values[i].length-1),10);
                    }else{
                        alert("502:"+_exp_char[i-1]+"是不支持的符号类型,请检查设置!");
                        return false;
                    }
                    _date_temp=_exp_add_date(_date_temp,_integer_temp,_last_char);

                }else{//如果属于另一个文本框的name属性值

                    var date_temp=_to_trim_all($("[name='"+_exp_values[i]+"']").val());
//					alert("表达式:"+_relation_temp[1]+"  属性:"+_exp_values[i]+"  值:"+date_temp);
                    if(date_temp==""){
                        return true;
                    }else if(date_temp+""=="undefined"){
                        alert("503:要比较的值无效,请检查目标是否存在!");
                        return false;
                    }else{
                        var _index=date_temp.indexOf("-");
                        //如果不是yyyy-mm-dd类型的.则进行字符串组装,组装成yyy-MM-dd格式
                        if(_index==-1){
                            if(date_temp.length==8){
                                date_temp=date_temp.substr(0,4)+"-"+date_temp.substr(4,2)+"-"+date_temp.substr(6,2);
                            }else if(date_temp.length==6){
                                date_temp=date_temp.substr(0,4)+"-"+date_temp.substr(4,2);
                            }else{
                                alert("504:要比较的值不是日期类型,无法进行计算.请检查!");
                                return false;
                            }
                        }
                        if(_is_date(date_temp)==false){
                            alert("505:要比较的值不是日期类型,无法进行计算.请检查!");
                            return false;
                        }
                        //分割各部分属性值
                        var _date_data=date_temp.split("-");
                        //根据符号进行加减
                        if(_exp_char[i-1]=="+"){
                            _date_temp=_exp_add_date(_date_temp,parseInt(_date_data[0],10),"y");
                            _date_temp=_exp_add_date(_date_temp,parseInt(_date_data[1],10),"m");
                            if(_date_data.length==3){
                                _date_temp=_exp_add_date(_date_temp,parseInt(_date_data[2],10),"d");
                            }
                        }else if(_exp_char[i-1]=="-"){
                            _date_temp=_exp_add_date(_date_temp,-1*parseInt(_date_data[0],10),"y");
                            _date_temp=_exp_add_date(_date_temp,-1*parseInt(_date_data[1],10),"m");
                            if(_date_data.length==3){
                                _date_temp=_exp_add_date(_date_temp,-1*parseInt(_date_data[2],10),"d");
                            }
                        }else{
                            alert("507:"+_exp_char[i-1]+"是不支持的符号类型,请检查设置!");
                            return false;
                        }
                    }
                }
            }

            var _year=_date_temp.substr(0,4);
            var _month=_date_temp.substr(4,2);
            _the_value=_year+_month;
            if(_value_date.length==8){
                var _day=_date_temp.substr(6,2);
                _the_value+=_day;
            }
            //alert("表达式:"+_relation_temp[1]+"  日期值:"+_value_date+"  计算结果:"+_the_value);
        }else{
            //处理值的表达式
            var _value_result_temp=_get_value_by_name(_exp_values[0]);
            if(_value_result_temp==""){
                return true;
            }
            if(_value_result_temp=="false"){
                alert("508:"+_msg+"的表达式中,存在非值类型!");
                return false;
            }
            var _value_result=parseFloat(_value_result_temp);
            var _value_temp=""
            for(var i=0;i<_exp_char.length;i++){
                _value_temp=_get_value_by_name(_exp_values[i+1]);
                if(_value_result_temp==""){
                    return true;
                }
                if(_value_result_temp=="false"){
                    alert("509:"+_msg+"的表达式中,存在非值类型!");
                    return false;
                }
                if(_exp_char[i]=="+"){
                    _value_result=_value_result+parseFloat(_value_temp);
                }else{
                    _value_result=_value_result-parseFloat(_value_temp);
                }
            }
            _the_value=_value_result+"";
            //alert("表达式:"+_relation_temp[1]+"  属性值:"+_value_result_temp+"  计算结果:"+_the_value);
        }
    }else if(_value_or_name=="name"||_is_money(_the_value)==false){
        var _name_temp=_the_value;
        _the_value=_to_trim_all(($("[name='"+_name_temp+"']").val()+"").replace(/-/g,""));
        if(_the_value==""){
            return true;
        }
    }
    if("[eq][nq][con][ncon][==][!=][^][!^]".indexOf("["+_the_relation+"]")==-1){
        _the_value=_the_value.replace(/_/g,"");
        if(_the_value==""){
            return true;
        }
        if(_the_value==undefined){
            alert("500:"+_msg+"的"+_init_validate_relation_name+"属性设置有误,请检查指定文本框name属性是否正确!");
            return false;
        }
        if(_is_money(_the_value)==false){
            alert("510:"+_msg+"的"+_init_validate_relation_name+"属性设置有误,请检查语法格式是否符合规范!");
            return false;
        }
    }
    //长度与要比较的值保持一致
//	_input_value=_input_value.substr(0,_the_value.length);
    var _is_right=_is_right_relation(_the_relation,_input,_the_value,_msg,_the_msg);
    if(_is_right=="thistypeisnotfind"){
        alert("511:"+_msg+"的"+_init_validate_relation_name+"属性设置有误,请检查关系运算符是否设置正确!");
        return false;
    }
    return _is_right;
}
//根据指定类型.增加日期
function _exp_add_date(_date,_number,_type){
    var _date_temp="";
    var year=parseInt(_date.substring(0,4),10);
    var month=parseInt(_date.substring(4,6),10);
    var day=0;
    if(_date.length==8){
        day=parseInt(_date.substring(6),10);
    }
    //alert(_date+" 年:"+year+" 月:"+month+"  日:"+day+" 增量:"+_number+" 类型:"+_type);
    if(_type=="y"){
        year+=_number;
    }else if(_type=="m"){
        //转换number,如果增加的月数
        while(Math.abs(_number)>=12){
            year+=(_number/Math.abs(_number));//如果是负数,则会返回-1
            _number-=(_number/Math.abs(_number))*12;
        }
        month+=_number;
        if(month<=0){
            year-=1;
            month+=12;
        }else if(month>12){
            year+=1;
            month-=12;
        }
        //计算本月应该是多少天
        var _day_month=_get_days_by_month(month,year);
        if(_day_month<day){
            day=_day_month;
        }
    }else if(_type=="d"){
        if(day!=0){
            var _date_temp=new Date(year,month-1,day);
            _date_temp.setDate(_date_temp.getDate()+_number);
            year=_date_temp.getYear();
            month=_date_temp.getMonth()+1;
            day=_date_temp.getDate();
        }
    }

    _date_temp=year+""+((month+"").length==1?"0"+month:month);
    if(_date.length==8){
        _date_temp+=""+((day+"").length==1?"0"+day:day);
    }
    //alert("日期是:"+_date+"  加上:"+_number+_type+"  计算结果:"+_date_temp);
    return _date_temp;
}

function _get_days_by_month(_month,_year){
    switch (parseInt(_month+"",10)){
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
            return 31;
        case 4:
        case 6:
        case 9:
        case 11:
            return 30;
        case 2:
            if((_year%4==0 && _year%100!=0) || _year%400==0){
                return 29;
            }else{
                return 28;
            }
    }
    return 0;
}

//根据参数获取值,如果参数本身是值,则返回其本身,否则返回对应name的input的值
function _get_value_by_name(valname){
    if(_is_money(valname)==false){
        var _value=$("[name='"+valname+"']").val();
        _value=_value.replace(/-/g,"");
        if(_value==""||_value+""=="undefined"){
            return "";
        }
        if(_is_money(_value)==false){
            return "false";
        }
        return $("[name='"+valname+"']").val();
    }
    return valname;
}

//表达式是否是日期类型
function _the_exp_is_date(_exp){
    var _exp_temp=_exp.toLowerCase();
    var _last_char=_exp_temp.substring(_exp_temp.length-1);//取得最后一个字符
    if(_last_char=="y"||_last_char=="m"||_last_char=="d"){
        var _char=_exp_temp.substring(0,_exp_temp.length-1);
        if(_is_number(_char)==true){
            return true;
        }
    }
    return false;
}
//是否设置了and或者or(+号或者-号),并返回第一个逻辑表达符号
function _is_setting_andOr(_relation_value,_type){
    var _the_char=new Array("and","or");
    if(_type=="+-"){
        _the_char=new Array("+","-");
    }
    var _index_temp_and=_relation_value.indexOf(_the_char[0]);
    var _index_temp_or=_relation_value.indexOf(_the_char[1]);
    if(_index_temp_and==-1&&_index_temp_or==-1){
        return "";
    }
    if(_index_temp_and!=-1&&_index_temp_or==-1){
        return _the_char[0];
    }
    if(_index_temp_and==-1&&_index_temp_or!=-1){
        return _the_char[1];
    }
    return _index_temp_and<_index_temp_or?_the_char[0]:_the_char[1];
}


//根据设置的有效性,检查是否符合
function _is_right_relation(_the_relation,_input,_the_value,_msg,_the_msg){
    var _is_right=true;
    var _input_value=_to_trim_all(_input.val()+"");
    //如果是日期类型，转换
    if(_is_date_format(_input_value)){
        _input_value=_input_value.replace(/-/g,"");
    }
    //如果是日期类型，转换
    if(_is_date_format(_the_value)){
        _the_value=_the_value.replace(/-/g,"");
    }
    var _relation=_to_trim_all(_the_relation);
    var _msg_temp="";
    switch(_relation.toLowerCase()){

        case "eq":
        case "==":
            _is_right= _input_value==_the_value;
            _msg_temp="等于";
            break;
        case "ge":
        case ">=":
            _is_right= parseFloat(_input_value)>=parseFloat(_the_value);
            _msg_temp="大于或等于";
            break;
        case "le":
        case "<=":
            _is_right= parseFloat(_input_value)<=parseFloat(_the_value);
            _msg_temp="小于或等于";
            break;
        case "gt":
        case  ">":
            _is_right= parseFloat(_input_value)>parseFloat(_the_value);
            _msg_temp="大于";
            break;
        case "lt":
        case  "<":
            _is_right= parseFloat(_input_value)<parseFloat(_the_value);
            _msg_temp="小于";
            break;
        case "nq":
        case "!=":
            _is_right= _input_value!=_the_value;
            _msg_temp="不等于";
            break;
        case "con":
        case   "^":
            var _index_temp=_input_value.indexOf(_the_value);
            _is_right=_index_temp!=-1;
            _msg_temp="包含";
            break;
        case "ncon":
        case   "!^":
            var _index_temp=_input_value.indexOf(_the_value);
            _is_right=_index_temp==-1;
            _msg_temp="不包含";
            break;
        default:
            _is_right=false;
            _msg_temp="thistypeisnotfind";
    }
    return _is_right;
}

//验证非法字符
//var _ffzf_full="~!@#$%^&*+|`=\{}[]:\";\'<>?,/ ";
//var _ffzf_normal="~#$%^&*+|`=\{}\"\'<>/ ";
//var _ffzf_less="'";
//full类型 0616增加#
var _ffzf_full=new Array(":","?","\"","[","]","{","}","`","=","#","^","&","!","*","|",";","$","%","@","\'","<",">","(",")","+","\r","\n",",","\\","../"," ");
var _ffzf_normal=new Array("|",";","$","%","\'","<",">","`","(",")","+","\r","\n",",","\\","../"," ","*");
var _ffzf_less=new Array();
var _ffzf_basic=new Array("'","--","'--","/*","*/","||","\"");
var _ffdc_normal=new Array("chr(39)","null");//"and ","or ","insert ","select ","delete ","drop ","update "," count"," master","truncate ","declare ");
function _auto_validate_ffzf(_input,_msg,_type,_this_config){
    var _input_value=_input.val();
    if(_input_value!=""){
        var _reg_ffzf=_ffzf_full;
        if(_type=="normal"){
            _reg_ffzf=_ffzf_normal;
        }else if(_type=="less"){
            _reg_ffzf=_ffzf_less;
        }else if(_type!="full"&&_to_trim_all(_type)!=""){
            _reg_ffzf=_get_validate_type(_type);
        }
        var _msg_ffzf="";
        //基本字符校验
        for(var k=0;k<_ffzf_basic.length;k++){
            if(_input_value.indexOf(_ffzf_basic[k])!=-1){
                _msg_ffzf=_ffzf_basic[k];
                if(_ffzf_basic[k]==" "){
                    _msg_ffzf="空格";
                }else if(_ffzf_basic[k]=="\n"){
                    _msg_ffzf="换行符";
                }else if(_ffzf_basic[k]=="\r"){
                    _msg_ffzf="制表符";
                }
                _auto_showmsg(_input,_msg+"包含非法字符【"+_msg_ffzf+"】,请重新输入!",_this_config);
                return false;
            }
        }

        //自定义字符校验
        if(jQuery.type(_reg_ffzf)=="string"){
            for(var i =0;i<_input_value.length;i++){
                var zf=_input_value.charAt(i);
                if(_reg_ffzf.indexOf(zf)>=0){
                    _msg_ffzf=zf;
                    if(zf==" "){
                        _msg_ffzf="空格";
                    }else if(zf=="\n"){
                        _msg_ffzf="换行符";
                    }else if(zf=="\r"){
                        _msg_ffzf="制表符";
                    }
                    _auto_showmsg(_input,_msg+"包含非法字符【"+_msg_ffzf+"】,请重新输入!",_this_config);
                    return false;
                }
            }
        }else{
            for(var i=0;i<_reg_ffzf.length;i++){
                if(_input_value.indexOf(_reg_ffzf[i])!=-1){
                    _msg_ffzf=_reg_ffzf[i];
                    if(_reg_ffzf[i]==" "){
                        _msg_ffzf="空格";
                    }else if(_reg_ffzf[i]=="\n"){
                        _msg_ffzf="换行符";
                    }else if(_reg_ffzf[i]=="\r"){
                        _msg_ffzf="制表符";
                    }
                    _auto_showmsg(_input,_msg+"包含非法字符【"+_msg_ffzf+"】,请重新输入!",_this_config);
                    return false;
                }
            }
        }
        //非法单词校验
        for(var j=0;j<_ffdc_normal.length;j++){
            if(_input_value.toLowerCase().indexOf(_ffdc_normal[j])!=-1){
                _auto_showmsg(_input,_msg+"包含非法单词【"+_ffdc_normal[j]+"】,请重新输入!",_this_config);
                return false;
            }

        }

    }
    return true;
}

//判断长度是否超限
function _check_length(_input,_msg,_datalength,_this_config){
    var _input_value=_input.val();
    if(_to_trim_all(_input_value)==""){
        return true;
    }
    if(_datalength==-1){
        return true;
    }
    var _len=_byte_count(_input_value);
    if(_len>_datalength){
        _auto_showmsg(_input,_msg+"长度不能超过"+_datalength+", 当前长度:"+_len+"(中文和全角符号长度计为2)!",_this_config);
        return false;
    }
    return true;
}

//获取长度
function _byte_count(_strvalue) {
    _strvalue = _strvalue.replace(/([\u0391-\uFFE5])/ig,'xs');
    var count = _strvalue.length;
    return count;
}

//==============================================================================//
//指定类型的校验																//
//==============================================================================//
var _validate_type_yb=/^[0-9]{6}$/;//邮编 (6位纯数字)
var _validate_type_sjh=/^1\d{10}$/;//手机号 （1开头，11位数字）
var _validate_type_email=/^[-\w]?\w+([-+.]\w+)*[-\w]?@[-\w]?\w+([-.]\w+)*[-\w]?\.[-\w]?\w+([-.]\w+)*[-\w]?$/;//邮箱
var _validate_type_gddh=/^((\(\d{3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}$/;//固定电话
var _validate_type_lxdh=/^[0-9]{11}/;//联系电话(11位半角数字)
var _validate_type_url=/^http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/;//网址
var _validate_type_English=/^[A-Za-z]+$/;//英文
var _validate_type_Chinese=/^[\u0391-\uFFE5]+$/;//中文
var _validate_type_QQ=/^[1-9]\d{4,9}$/;//QQ
var _validate_type_number=/^\d+$/;//自然数
var _validate_type_num=/^[1-9]\d*$/;//正整数
var _validate_type_integer=/^[-\+]?\d+$/;//整数
var _validate_type_double=/^[-\+]?\d+(\.\d+)?$/;//小数
var _validate_type_posdouble=/^\d+(\.\d+)?$/;//正小数
var _validate_type_money="";//金额(使用_is_money方法进行校验)
var _validate_type_sfzhm="";//身份证号码(使用_sfzhm_is_right方法进行校验)
var _validate_type_date="";//日期(使用_is_date方法进行校验)
var _validate_type_default=/.+/;//默认的
var _validate_type_default1=/.+/;//默认的
var _validate_type_default2=/.+/;//默认的
var _validate_type_default3=/.+/;//默认的

//根据校验类型,进行校验
function _auto_validate_by_type(_input,_msg,_data_length,_type,_this_config){
    var _input_value=_input.val();
    //如果值为空,则不进行校验
    if(_to_trim_all(_input_value)==""){
        return true;
    }
    var _validate_type=_type.toLowerCase();
    var _is_right=false;
    if(_validate_type=="sfzhm"){
        _is_right=_sfzhm_is_right(_input,_msg,_this_config);
        if(_is_right==false){
            return false;
        }
    }else if(_validate_type=="money"){
        _is_right=_is_money(_input_value);
        if(_is_right==false){
            _auto_showmsg(_input,_msg+"必须是有效数字!",_this_config);
        }
    }else if(_validate_type=="date"){
        _is_right=_is_date(_input_value);
        if(_is_right==false){
            _auto_showmsg(_input,_msg+"必须是有效日期!",_this_config);
        }
    }else{
        var _reg_validate=_get_validate_type(_validate_type);
        if(_reg_validate=="thistypeisnotfind"){
            if(customMethod[_type]==undefined){
                alert("601:["+_type+"]是未知的校验类型,请检查设置!");
                return false;
            }else{
                var inputC=new inputConfig();
                inputC.owner=_input;
                inputC.name=_input.attr("name");
                inputC.value=_input.val();
                inputC.msgName=_msg;
                return customMethod[_type](inputC);
            }
        }
        if(jQuery.type(_reg_validate)=="string"||jQuery.type(_reg_validate)=="array"){
            _is_right=_auto_validate_ffzf(_input,_msg,_validate_type,_this_config);
        }else{
            _is_right=_is_right_by_reg(_input,_msg,_reg_validate,_this_config);
        }
    }
    return _is_right;
}
function inputConfig(){
    this.owner;//文本框对象
    this.name="";//文本框的那么属性
    this.value="";//文本框的值
    this.msgName="";//文本框的中文名称
}
function _get_validate_type(_validate_type){
    switch(_validate_type.toLowerCase()){
        case "yb": return _validate_type_yb;
        case "sjh":return _validate_type_sjh;
        case "lxdh":return _validate_type_lxdh;
        case "email":return _validate_type_email;
        case "gddh":return _validate_type_gddh;
        case "url":return _validate_type_url;
        case "english":return _validate_type_English;
        case "chinese":return _validate_type_Chinese;
        case "qq":return _validate_type_QQ;
        case "number":return _validate_type_number;
        case "num":return _validate_type_num;
        case "money":return _validate_type_money;
        case "default":return _validate_type_default;
        case "default1":return _validate_type_default1;
        case "default2":return _validate_type_default2;
        case "default3":return _validate_type_default3;
        case "full": return _ffzf_full;
        case "normal": return _ffzf_normal;
        case "less": return _ffzf_less;
        case "integer":return _validate_type_integer;
        case "double": return _validate_type_double;
        case "posdouble": return _validate_type_posdouble;
        default:return "thistypeisnotfind";
    }
}

//是否符合正则规则
function _is_right_by_reg(_input,_msg,_validate_reg,_this_config){
    var _input_value=_input.val();
    if(_input_value!=""){
        if(_validate_reg.exec(_input_value)==null){
            var _reg_msg=_input.attr(_init_validate_reg_msg_name);
            var _showmsg=_msg+"格式不正确!";
            if(_to_trim_all(_reg_msg)!=""&&_reg_msg+""!="undefined"){
                _showmsg=_reg_msg;
            }
            _auto_showmsg(_input,_showmsg,_this_config);
            return false;
        }
    }
    return true;
}
//是否是身份证号码
function _sfzhm_is_right(_input,_msg,_this_config){

    if(_to_trim_all(_input.val())!=""){
        if(_check_sfzhm(_input,_msg,_this_config)==false){
            return false;
        }
    }
    return true;
}

function _check_sfzhm(_input,_msg,_this_config){
    _input.val(_input.val().toUpperCase());
    var _input_value_temp=_input.val()+"";
    var _input_value = new String(_input_value_temp);
    var  _l_l_jym= new Array(7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1);
    var  _l_l_total = 0;
    //位数校验
    if (_input_value.length!=15 && _input_value.length!=18){
        _auto_showmsg(_input,_msg+"必须为15位或18位!",_this_config);
        return false;
    }
    //15校验
    if (_input_value.length==15){
        if(_is_number(_input_value)==false){
            _auto_showmsg(_input,_msg+"输入错误，应全为数字!",_this_config);
            return false;
        }
        //15位转18位
        _input_value=_convert_the_sfzhm(_input_value);
    }
    var _l_s_temp = _input_value.substr(0,17);
    if (_is_number(_l_s_temp)==false){
        _auto_showmsg(_input,_msg+"前17位输入错误，应全为数字!",_this_config);
        return false;
    }
    var _last_char = _input_value.substring(17,18);
    if (_is_number(_last_char)==false && _last_char!="x" && _last_char!="X"){
        _auto_showmsg(_input,_msg+"最后一位输入错误!",_this_config);
        return false;
    }
    var _l_s_temp_temp = _input_value.substr(6,8);
    var _l_s_temp  = new String(_l_s_temp_temp);
    var _year  = _l_s_temp.substring(0,4);
    var _month = _l_s_temp.substring(4,6);
    var _day   = _l_s_temp.substring(6,8);
    var _l_l_temp_temp;
    var _l_s_csny = _year + "-" + _month + "-" + _day;
    //是否是合法日期
    if (_is_date(_l_s_csny)==false){
        _auto_showmsg(_input,_msg+"的出生年月日不正确!",_this_config);
        return false;
    }
    for(var i=0;i<_input_value.length - 1;i++){
        _l_l_temp_temp = parseInt(_input_value.substr(i,1),10) * _l_l_jym[i];
        _l_l_total += _l_l_temp_temp;
    }
    if (_is_number(_input_value.substring(17,18))){
        _l_l_total += parseInt(_input_value.substring(17,18),10);
    }
    if (_input_value.substring(17,18)=="X" || _input_value.substring(17,18)=="x"){
        _l_l_total += 10;
    }
    _l_l_total --;
    if (_l_l_total%11!=0){
        _auto_showmsg(_input,_msg+"输入不正确!",_this_config);
        return false;
    }
    _input.val(_input_value);
    return true;
}

//15位身份证号转成18位
function _convert_the_sfzhm(_sfzhm){
    var  _l_l_jym= new Array(7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1);
    var  _l_l_total = 0;
    var  _last_char;
    var _input_value = new String(_sfzhm);
    if (_input_value.length==15){
        var _input_value_temp = _input_value.substring(0,6) + "19" + _input_value.substring(6,15);
        for(var i=0;i<_input_value_temp.length;i++){
            var _l_l_temp_temp = parseInt(_input_value_temp.substr(i,1),10) * _l_l_jym[i];
            _l_l_total += _l_l_temp_temp;
        }
        _l_l_total --;
        var _last_number = _l_l_total % 11;//最后一位
        if (_last_number==0){
            _last_char = 0;
        }else{
            if (_last_number==1){
                _last_char="X";
            }
            else{
                _last_char = 11 - _last_number;
            }
        }
        _input_value_temp = _input_value_temp + _last_char;
        return _input_value_temp;
    }else{
        return _input_value;
    }
}
//==============================================================================//
//判断是否需要加载其他事件,如果需要,则加载,否则 跳过							//
//==============================================================================//
function _init_loadqt(){
    //如果没有指定精确位数的.则跳过
    if($("["+_init_money_jqws_name+"]").length==0){
        return;
    }
    $("["+_init_money_jqws_name+"]").each(function(){
        var _jqws=$(this).attr(_init_money_jqws_name);
        var _jqws_temp=_jqws.split(",");
        jqws=_jqws_temp[0];
        var _init_money_msg=_jqws_temp[1];
        if(_is_number(jqws)==false){
            _jqws=2;
        }
        $(this).blur(function(){
            _change_the_money($(this),parseInt(_jqws,10),_init_money_msg);
        });

        $(this).blur();
    });

}
//金额自动转换(保留几位有效数字)												//
//			1.第一个参数是文本框本身(jquery对象)								//
//			2.第二个参数是精确多少位,(不是有效数字,默认是2)						//
var _default_money="";
function _change_the_money(_input,_jqws,_init_money_msg){
    var _input_value=_to_trim_all(_input.val());
    if(_input.attr(_init_right_move)+""=="undefined"){
        if(_input_value==""||_default_money==_input_value){
            _input.val(_input_value);
            return;
        }
    }
    var _index=_input_value.indexOf(".");

    if(isNaN(_input_value)||_index==0||_input_value.toUpperCase().indexOf("E")!=-1){
        var _msg="";
        if(_init_money_msg+""=="undefined"||_init_money_msg==""){
            _msg=_get_the_msg(_input);
        }else{
            _msg=_init_money_msg;
        }
        if(_msg==""){
            _msg="金额";
        }
        alert(_msg+"必须是有效数字!");
        _default_money="";
        _input.focus();
        _input.val("");
        return;
    }else{

        var i=-1;
        if(_index!=-1){
            i=_input_value.length-(_index+1);
        }
        if(_jqws<i){
            var _value_temp=_input_value.substring(0,_index+_jqws+2)*(Math.pow(10,_jqws));
            _value_temp=Math.round(_value_temp);
            _value_temp=_value_temp/Math.pow(10,_jqws);
            _input.val(_value_temp);
            _change_the_money(_input,_jqws,_init_money_msg)
        }else{
            if(i==-1 && _jqws==0){
                return;
            }
            for(;i<_jqws;i=i+1){

                if(i==-1){
                    _input_value=_input_value+".0";
                    i=0;
                }else{
                    _input_value=_input_value+"0";
                }
            }
            _input.val(_input_value);
            _default_money=_input_value;
        }
    }
}


//==============================================================================//
//小工具,(去空格,判断是否为正整数(包含0))										//
//==============================================================================//
//去空格
function _to_trim_all(strValue){
    return (strValue+"").replace(/\s+/g,"");
}

function _to_trim_allForCode(strValue){
    if("undefined"==strValue+""){
        return "";
    }
    return $.trim(strValue);
}
//判断是不是正整数
function _is_number(strValue){
    if(_to_trim_all(strValue)==""){
        return false;
    }
    if(! /^[0-9]*$/.test(strValue)){
        return false;
    }
    return true;
}

function _is_money(strValue){
    var _input_value=_to_trim_all(strValue);
    var _index=_input_value.indexOf(".");
    if(isNaN(_input_value)||_index==0||_index==_input_value.length-1||_input_value.toUpperCase().indexOf("E")!=-1){
        return false;
    }

    return true;
}

//获取文本框对应的文字提示信息(去除特殊字符)(适用于: <td>*姓名:</td><td><input type="text"/></td>)
function _get_the_msg(_input){
    return _input.parent().prev().text().replace(/[ *:：]/g,"");
}

//获取用户定义的文字提示信息
function _get_valiflag_msg(_input){
    var _validate_flag=$(this).attr(_init_validate_flag_name);
    var _validate_properties=(_validate_flag+"").split(",");
    var _msg=_validate_properties[2];
    if(_msg+""=="undefined"||_to_trim_all(_msg)==""){
        return "";
    }
    return _msg;
}
//获取文本框对应的文字提示信息(适用于: <td>*姓名:</td><td><input type="text"/></td>)
function _get_the_msg_havezf(_input){
    return _input.parent().prev().text();
}

//判断是否是日期格式
function _is_date_format(theStr){
    var strObj=new String(theStr);
    var strObjTemp;
    //1.theStr.length<>10
    if(strObj.length!=10&&strObj.length!=7){
        return false;
    }
    //2.判断第五位、第八位是"-"
    if(strObj.substring(4,5)!="-"){
        return false;
    }
    if(strObj.length==10){
        if (strObj.substring(7,8)!="-"){
            return false;
        }
    }
    return true;
}

//判断字符串是否符合日期格式，如1999-03-07
function _is_date(theStr) {
    var strObj=new String(theStr);

    var strObjTemp;
    //1.theStr.length<>10
    if(strObj.length!=10&&strObj.length!=7){
        return false;
    }
    //2.判断第五位、第八位是"-"
    if(strObj.substring(4,5)!="-"){
        return false;
    }
    if(strObj.length==10){
        if (strObj.substring(7,8)!="-"){
            return false;
        }
    }
    //3.校验年部分是数字，并在1900~2100之间，月部分是数字，并在1~12之间，日部分是数字，并在1~31之间
    strObjTemp=new String(strObj.substring(0,4));

    if(_is_number(strObjTemp)==false || parseInt(strObjTemp,10)<=1900 || parseInt(strObjTemp,10)>2100)
        return false;
    strObjTemp=new String(strObj.substring(5,7));

    if  (_is_number(strObjTemp)==false || parseInt(strObjTemp,10) < 1  || parseInt(strObjTemp,10)>12)
        return false;

    if(strObj.length==10){
        strObjTemp=new String(strObj.substring(8,10));
        if(_is_number(strObjTemp)==false || parseInt(strObjTemp,10)<1 || parseInt(strObjTemp,10)>31)
            return false;
    }

    if(strObj.length==10){
        if(_is_right_date(theStr)==false){
            return false;
        }
    }
    return true;
}
//判断是不是合法日期
function _is_right_date(theStr) {
    var strObj=new String(theStr);
    var theYear=parseInt(strObj.substring(0,4),10);
    var theMonth=parseInt(strObj.substring(5,7),10);
    var theDay=parseInt(strObj.substring(8,10),10);
    switch(theMonth){
        case 4:
        case 6:
        case 9:
        case 11:
            if(theDay==31)
                return false;
            else
                break;
        case 2:
            if((theYear%4==0 && theYear%100!=0) || theYear%400==0){//润年2月份29天
                if(theDay>29) return false;
            }
            else{
                if(theDay>28) return false;
            }
            break;
        default: break;
    }
    return true;
}

//==============================================================================//
//================================【基础方法,不懂勿动】=========================//
//==============================================================================//

//信息提示方法
function _auto_showmsg(_input,_msg,_msg_config){
    var _is_disabled=false;
    //判断是否禁用了该文本框
    if(_to_trim_all(_input.attr("disabled"))=="disabled"){
        _is_disabled=true;
    }
    if(_msg_config[0]=="2"){
        _auto_msg_rightline(_input,_msg,_msg_config[1]);
    }else{
        alert(_msg);
        if(_is_disabled==true){
            _input.attr("disabled",false);
            _input.focus();
            _input.attr("disabled",true);
        }else{
            _input.focus();
        }
    }
}

//设置右侧提示信息
function _auto_msg_rightline(_input,_msg,_color){
    _input.after("<span name='"+_init_validate_msg_value+"' style='color:"+_color+";margin-left:5px;'>"+_msg+"</span>");
}
//清空右侧提示信息
function _auto_msg_rightline_clear(_validate_form){
    $("[name='"+_init_validate_msg_value+"']").remove();
}

function _auto_msg_rightline_clear_by_input(_input){
    _input.next("[name='"+_init_validate_msg_value+"']").remove();
}


//全选复选框单击事件基础方法
function _auto_qxuan_temp(_checkName,_qx_cbo){
    var _che = _qx_cbo.attr("checked");
    if("undefined"==_che+""){
        _che=false;
    }
    $("input[name='"+_checkName+"']").each(function(i){
        if($(this).get(0).disabled!=true){
            $(this).attr("checked",_che);
        }
    });
}

//复选框单击事件,基础方法
function _auto_xzcjl_temp(_ele_name,_qx_cbo){
    var _qxzhong=true;
    //循环遍历,是否所有复选框都被选择了
    $("input[name='"+_ele_name+"']").each(function(i){
        if($(this).get(0).disabled!=true){
            if($(this).get(0).checked==false){
                _qxzhong=false;
            }
        }
    });
    _qx_cbo.attr("checked",_qxzhong);
}
//互斥的复选框单击事件,基础方法
function _xzcjl_temp_hc(_ele_hcflag,_qxid){
    var _qxzhong=true;
    //循环遍历,是否所有复选框都被选择了
    $("input["+_init_hccbo_hcflag_name+"='"+_ele_hcflag+"']").each(function(i){
        if($(this).get(0).disabled!=true){
            if($(this).get(0).checked==false){
                _qxzhong=false;
            }
        }
    });
    $("#"+_qxid).attr("checked",_qxzhong);
}
//互斥的全选复选框单击事件基础方法
function _qxuan_temp_hc(_ele_hcflag,_idName){
    var _che = $("#"+_idName).attr("checked");
    if("undefined"==_che+""){
        _che=false;
    }
    $("input["+_init_hccbo_hcflag_name+"='"+_ele_hcflag+"']").each(function(i){
        if($(this).get(0).disabled!=true){
            $(this).attr("checked",_che);
        }
    });
}

function _hcqx_get(_thisId,_otherid){
    $("#"+_otherid).attr("checked",false);
    var _ele_hcflag=_thisId.substring(_thisId.lastIndexOf("_")+1);
    _qxuan_temp_hc(_thisId.substring(_thisId.lastIndexOf("_")+1),_thisId);
    _qxuan_temp_hc(_otherid.substring(_otherid.lastIndexOf("_")+1),_otherid);
    if(_autodisabled_flag==_ele_hcflag||$("#"+_thisId).attr("checked")+""=="undefined"){
        $("["+_init_hccbo_bzdisabled_name+"]").each(function(i){
            if(_notclear!="true"){
                if(_init_hcbz_auto_back=="true"&&$(this).attr("disabled")!="disabled"){
                    var _temp_index=$(this).attr("id").substring($(this).attr("id").lastIndexOf("_")+1);
                    _clear_bz_array[_temp_index]=$("#"+_init_hccbo_bzid_value+_temp_index).val();
                }
                $(this).val("");
            }
            $(this).attr("disabled",true);
        });
    }else if(_autodisabled_flag!=""){
        $("["+_init_hccbo_bzdisabled_name+"]").each(function(i){
            if(_notclear!="true"&&_init_hcbz_auto_back=="true"){
                var _temp_index=$(this).attr("id").substring($(this).attr("id").lastIndexOf("_")+1);
                if(_clear_bz_array[_temp_index]+""=="undefined"){
                    _clear_bz_array[_temp_index]="";
                }
                $(this).val(_clear_bz_array[_temp_index]);
            }
            $(this).attr("disabled",false);
        });
    }
}

function _hcxz_get(_ele,_othertype){
    var _thistype=_ele.attr(_init_hccbo_hcflag_name);
    //设置另一个的选中状态为false
    var _temp_index=_ele.attr("id").indexOf("_")+1;
    var _temp_index=_ele.attr("id").substring(_temp_index);
    $("#"+_othertype+"_"+_temp_index).attr("checked",false);
    //判断当前点击的复选框状态.如果选中
    if(_ele.attr("checked")=="checked"){
        _xzcjl_temp_hc(_thistype,"_hc_cbo_"+_thistype);
        $("#"+_init_hccbo_id_value+_othertype).attr("checked",false);
    }else{
        $("#"+_init_hccbo_id_value+_thistype).attr("checked",false);
        $("#"+_init_hccbo_id_value+_othertype).attr("checked",false);
    }
    if(_autodisabled_flag==_thistype||_ele.attr("checked")+""=="undefined"){
        if(_notclear!="true"&&$("#"+_init_hccbo_bzid_value+_temp_index).attr("disabled")!="disabled"){
            if(_init_hcbz_auto_back=="true"){
                _clear_bz_array[_temp_index]=$("#"+_init_hccbo_bzid_value+_temp_index).val();
            }
            $("#"+_init_hccbo_bzid_value+_temp_index).val("");
        }
        $("#"+_init_hccbo_bzid_value+_temp_index).attr("disabled",true);
    }else if(_autodisabled_flag!=""){
        if(_notclear!="true"&&_init_hcbz_auto_back=="true"){
            if(_clear_bz_array[_temp_index]+""=="undefined"){
                _clear_bz_array[_temp_index]="";
            }
            $("#"+_init_hccbo_bzid_value+_temp_index).val(_clear_bz_array[_temp_index]);
        }
        $("#"+_init_hccbo_bzid_value+_temp_index).attr("disabled",false);

    }
}


function _auto_grouphc(groupname,_input){

    var _current_checked=_input.attr("checked");
    if(_to_trim_all(_current_checked)==""){
        _current_checked=false;
    }
    $("["+_init_hccbo_hcgroup_name+"='"+groupname+"']").attr("checked",false);

    _input.attr("checked",_current_checked);
}




//---------------------------------------------------------------------
//初始化代码
var _default_inputValue_arr=new Array();//记录各文本框当前值
function _init_loadcode(){

    if($("["+_init_codedata_codecolumn+"]").length==0){
        return;
    }
    window.onload=function(){
        $(document).click(function(e){
            $("[flag='_it_codedata']").hide();//slideUp("normal",function(){});
            _select_noneHide();
        });
        $("[flag='_it_codedata']").live("click",function(e){
            e.stopPropagation();//阻止事件冒泡
        });
    }
    $("["+_init_codedata_codecolumn+"]").each(function(i){
        var thenewName="_it_code_"+$(this).attr("name");
        if(_to_trim_all($(this).val())!="" && $(this).attr("unlock")==undefined){
            $(this).attr("_itCode_lock","true");
        }
        $(this).addClass("itCode");
        $(this).after("<input type='hidden' dataflag='"+thenewName+"' name='"+$(this).attr("name")+"' value='"+$(this).attr("code")+"' />");
        _default_inputValue_arr[thenewName]=$(this).val();
        $(this).attr("oldname",$(this).attr("name"));
        $(this).attr("name",thenewName);
    });
    $("["+_init_codedata_codecolumn+"]").live("keyup",function(_event){
        if($(this).val()!=_default_inputValue_arr[$(this).attr("name")] || itCode.clearCache==true){
            var _key_code=_event.keyCode?_event.keyCode:_event.which?_event.which:_event.charCode;

            var allchange=$(this).attr("allchange");

            if($(this).attr("_itCode_lock")+""!="undefined"){
                if(_key_code==8 || _key_code==46){
                    _itcode_clearvalue($(this).attr("oldname"));
                }else{
                    return false;
                }
            }

            if(_key_code!=13 && _key_code!=40 && _key_code!=38 && (allchange+""=="" || allchange=="true")){
                _key_code=32;
            }

            if(_key_code==32){//如果按下的是空格或者退格键
                _default_inputValue_arr[$(this).attr("name")]=$(this).val();
                _itcode_getdata($(this).attr(""+_init_codedata_codecolumn+""),$(this).val(),$(this).attr("showformat"),$(this).attr("sqlres"),$(this).attr("rows"),$(this).attr("param"),$(this));
            }
        }else{
            $("[flag='_it_codedata']").show();
            _select_noneShow();
            $(this).val($.trim(_default_inputValue_arr[$(this).attr("name")]));
        }
    });
    //$("["+_init_codedata_tablename+"]").blur(function(){
    //	$("[flag='_it_codedata']").remove();
    //});

    $("["+_init_codedata_codecolumn+"]").live("blur",function(_event){
        var _key_code=_event.keyCode?_event.keyCode:_event.which?_event.which:_event.charCode;
        if($(this).attr("_itCode_lock")+""=="undefined" && $(this).attr("unlock")+""=="undefined"){
            _itcode_clearvalue($(this).attr("oldname"));
        }else{
            var theobjname=$(this).attr("name");
            $("[dataflag='"+theobjname+"']").val($(this).val());
            itCode.value=$(this).attr("inputvalue");
            itCode.text=$(this).text();
            itCode.name=$("[dataflag='"+theobjname+"']").attr("name");
            itCode.select();
        }

    });

    $("["+_init_codedata_codecolumn+"]").live("keydown",function(_event){
        var _key_code=_event.keyCode?_event.keyCode:_event.which?_event.which:_event.charCode;
        if($(this).attr("_itCode_lock")+""!="undefined"){
            if(_key_code==8 || _key_code==46){
                _itcode_clearvalue($(this).attr("oldname"));
            }else{
                return false;
            }
        }

    });
    $("["+_init_codedata_codecolumn+"]").live("keydown",function(_event){
        if($("ul[flag='_it_codedata_ul']").attr("isempty")!='1'){
            var _key_code=_event.keyCode?_event.keyCode:_event.which?_event.which:_event.charCode;
            if(_key_code=="40" || _key_code=="38"){
                _move_focus_item(_key_code,$(this),_event);
            }else if(_key_code=="13"){
                _select_this_item($("[selectItem='1']"));
            }
        }
    });

    $("li[flag='_it_codedata_li']").live("keydown",function(_event){
        var _key_code=_event.keyCode?_event.keyCode:_event.which?_event.which:_event.charCode;
        if(_key_code=="13"){
            if($("ul[flag='_it_codedata_ul']").attr("isempty")!='1'){
                _select_this_item($(this));
            }
        }
    });

}

//清空联动的值
function _itcode_clearvalue(oldname){
    var obj=$("[oldname='"+oldname+"']");
    if($(obj).attr("unlock")+""!="undefined"){
        return;
    }
    var oldname=$(obj).attr("oldname");
    if(itCode.objectArr[oldname]==null || itCode.objectArr[oldname].length==0){
        var arr=new Array();
        arr[0]=$(obj);
        itCode.objectArr[oldname]=arr;
    }

    var theArr=itCode.objectArr[oldname];
    for(var i=0;i<theArr.length;i=i+1){
        if($(theArr[i]).attr("unlock")+""=="undefined"){
            //alert($(theArr[i]).parent().html()+"==="+$(theArr[i]).attr("name"));
            $(theArr[i]).val("");
            $("[dataflag='"+$(theArr[i]).attr("name")+"']").val("");
            $(theArr[i]).removeAttr("_itCode_lock");
        }
    }


    itCode.key="";
    itCode.value="";
    itCode.name="";
    itCode.text="";

}

//获取代码表数据
function _itcode_getdata(codecolumn,code,showformat,sqlres,rows,param,obj){

    $.post(_it_codedata_objectpath+"/ItCodeAction.do?method=getCodeData",{ codecolumn: encodeURI(_to_trim_allForCode(codecolumn)),
            code: encodeURI(_to_trim_allForCode(code)),
            sqlres:encodeURI(_to_trim_allForCode(sqlres)),
            showformat: encodeURI(_to_trim_allForCode(showformat)),
            param: encodeURI(_to_trim_allForCode(param)),
            rows: encodeURI(_to_trim_allForCode(rows))
        },
        function(data){
            //alert(data);
            _show_dataDiv(data,obj);
        });
}
//创建显示的DIV对象
function _show_dataDiv(data,obj){
    $("[flag='_it_codedata']").remove();
    var divObj=$("<div flag='_it_codedata' style='display:none;position:absolute;z-index:999;margin:0 auto;width:"+obj.outerWidth()+";padding:0;border:1px solid #000000;background:#ffffff;'></div>");
    var tempArr;

    var tempHtml="<ul flag='_it_codedata_ul' isempty='1' style='list-style:none;margin:0px;padding:0px;'>";
    //如果有查询结果，则绑定事件
    var bindListener="";
    if(_to_trim_all(data)!="-"){
        tempHtml="<ul flag='_it_codedata_ul' style='list-style:none;margin:0px;padding:0px;'>";
        bindListener="onmouseover='_select_item(this)' onmouseout='_unselect_item(this)' onclick=_select_this_item(this)";

    }

    var dataArr=data.split(";")
    for(var i=0;i<dataArr.length;i=i+1){
        tempArr=dataArr[i].split("'");
        //alert(dataArr[i]);
        tempHtml+="<li flag='_it_codedata_li' data='"+tempArr[0]+"' inputname='"+obj.attr("name")+"' inputvalue='"+tempArr[1]+"' style='cursor:pointer;padding:2px;margin:0px;width:100%' "+bindListener+">"+_to_trim_allForCode(tempArr[2])+"</li>";

    }
    tempHtml+="</ul>"
    divObj.append($(tempHtml));
    $("body").append(divObj);
    //alert(tempHtml);
    var offset=obj.offset();
    var left=offset.left;
    var top=offset.top+obj.outerHeight();
    $("[flag='_it_codedata']").offset({top: top , left: left});
    $("[flag='_it_codedata']").show();//slideDown("normal",function(){});
    if(divObj.height()>=200){
        divObj.css("height","200px");
        divObj.css("overflow-y","scroll");
    }
    _select_noneShow();
    obj.val(_to_trim_allForCode(obj.val()));
    obj.focus();
}
var _it_codedata_hideSel=new Array();
function _select_noneShow(){
    if($("[flag='_it_codedata']").length==0)
        return;
    var offset=$("[flag='_it_codedata']").offset();
    var height=$("[flag='_it_codedata']").height();
    var width=$("[flag='_it_codedata']").width();

    var index=0;
    _it_codedata_hideSel=new Array();
    if ( $.browser.msie ){
        if($.browser.version=="6.0"){
            $("select:visible").each(function(){
                var offsetSel=$(this).offset();
                var heightSel=$(this).height();
                var widthSel=$(this).width();
                //if((offsetSel.top>offset.top&&offsetSel.left>offset.left
                //	&& offsetSel.top<offset.top+height && offsetSel.left<offset.left+width)
                //	|| (offsetSel.top+heightSel>offset.top&&offsetSel.left+widthSel>offset.left
                //	&& offsetSel.top+heightSel<offset.top+height && offsetSel.left+widthSel<offset.left+width)){

                //假设相交，获取相交的面，即两个点坐标
                var minX=Math.max(offset.left,offsetSel.left);
                var minY=Math.max(offset.top,offsetSel.top);

                var maxX=Math.min(offset.left+width,offsetSel.left+widthSel);
                var maxY=Math.min(offset.top+height,offsetSel.top+heightSel);
                //alert("Div：height:"+height+"  width:"+width+"  top:"+offset.top+"  left:"+offset.left+"\n"
                //+"下拉列表：height:"+heightSel+"  width:"+widthSel+" top:"+offsetSel.top+"  left:"+offsetSel.left
                //	+"\n  minX:"+minX+"  minY:"+minY+"  maxX:"+maxX+"  maxY:"+maxY);
                if(!(minX>maxX || minY>maxY)){
                    $(this).css("visibility","hidden");
                    _it_codedata_hideSel[index]=$(this);
                    index=index+1;
                }
            });
        }
    }
}

function _select_noneHide(){
    for(var i=0;i<_it_codedata_hideSel.length;i=i+1){
        if(_it_codedata_hideSel[i]+""!="undefined"){
            _it_codedata_hideSel[i].css("visibility","visible");
        }
    }
    _it_codedata_hideSel=new Array();
}
function _move_focus_item(keycode,obj,_event){
    if(keycode=="32"){
        if($("[selectItem='1']").length>0){
            _select_this_item($("[selectItem='1']"),obj.attr("name"));
            _event.preventDefault();
        }
    }else{
        if($("[selectItem='1']").length==0){
            $("[flag='_it_codedata']").children("ul").children("li").first().css("background-color","gray").attr("selectItem",'1');
        }else{
            var tempObj;
            var obj=$("[selectItem='1']");
            obj.css("background-color","");
            obj.removeAttr("selectItem");
            if(keycode=="40"){
                tempObj=obj.next();
            }else{
                tempObj=obj.prev();
            }
            if(tempObj.length==0){
                tempObj=obj;
            }
            //alert(tempObj.position().top+"  "+tempObj.outerHeight()+"  "+$("[flag='_it_codedata']").height());
            if(keycode=="40" && tempObj.position().top+tempObj.outerHeight()>$("[flag='_it_codedata']").height()){
                $("[flag='_it_codedata']").scrollTop($("[flag='_it_codedata']").scrollTop()+tempObj.outerHeight());
            }else if(keycode=="38" && tempObj.position().top<0){
                $("[flag='_it_codedata']").scrollTop($("[flag='_it_codedata']").scrollTop()-tempObj.outerHeight());
            }
            tempObj.css("background-color","gray");
            tempObj.attr("selectItem",'1');

        }
    }
}

function _select_item(obj){
    if($("[selectItem='1']").length>0){
        $("[selectItem='1']").css("background-color","").removeAttr("selectItem");
    }
    $(obj).css("background-color","gray");
    $(obj).attr("selectItem",'1');
}
function _unselect_item(obj){
    if($("[selectItem='1']").length>0){
        $("[selectItem='1']").css("background-color","").removeAttr("selectItem");
    }
    $(obj).css("background-color","").removeAttr("selectItem");
}
function _select_this_item(obj){
    var theobjname=$(obj).attr("inputname");
    $("[dataflag='"+theobjname+"']").val($(obj).attr("data"));

    //alert($(obj).text());
    $("[name='"+theobjname+"']").val($(obj).attr("inputvalue"));
    $("[flag='_it_codedata']").remove();
    _select_noneHide();
    $("[name='"+theobjname+"']").focus();
    $("[name='"+theobjname+"']").select();

    var oldname=$("[name='"+theobjname+"']").attr("oldname");

    if(itCode.objectArr[oldname]==null || itCode.objectArr[oldname].length==0){
        var arr=new Array();
        arr[0]=$("[name='"+theobjname+"']");
        itCode.objectArr[oldname]=arr;
    }
    var theArr=itCode.objectArr[oldname];

    for(var i=0;i<theArr.length;i=i+1){
        if($(theArr[i]).attr("unlock")==undefined){
            $(theArr[i]).attr("_itCode_lock","true");
            $(theArr[i]).live("keydown",function(_event){
                var _key_code=_event.keyCode?_event.keyCode:_event.which?_event.which:_event.charCode;
                if($(this).attr("_itCode_lock")+""!="undefined"){
                    if(_key_code==8 || _key_code==46){

                        _itcode_clearvalue(oldname);
                    }else{
                        return false;
                    }
                }
            });
        }
    }

    //$("["+_init_codedata_codecolumn+"]").attr("_itCode_lock","true");
    itCode.value=$(obj).attr("inputvalue");
    itCode.text=$(obj).text();
    itCode.name=$("[dataflag='"+theobjname+"']").attr("name");
    itCode.select();

    _default_inputValue_arr[theobjname]=$(obj).attr("data");
}

//自动补全提供的对象
function _It_CodeData(){
    this.key;//主键，即隐藏域的值
    this.value;//显示到文本框的值
    this.text;//Li显示的值
    this.name;//触发此事件的文本框name属性
    this.objectArr=new Array();//联动的数组对象
    this.select=function(){};
    this.clearCache=false;
}

//获取结果集个数
function getResultCount(){
    if($("ul[flag='_it_codedata_ul']").attr("isempty")=='1'){
        return 0
    }else{
        return $("li[flag='_it_codedata_li']").length;
    }
}

//===============================================
//加载配置
function loadConfig(jsonStr){
    //alert(jsonStr);
    var json=eval('('+jsonStr+")");
    //alert(json);
    //设置form表单属性
    var formName="form2";

    if(json.formName!=undefined){
        formName=json.formName;
    }
    var formObj=$("form[name='"+formName+"']");
    if(formObj.length<=0){
        alert("初始化加载失败，无法找到name="+formName+"的form表单，请检查后台模板设置！");
        return;
    }
    //msgType
    var msgType="1";
    if(json.msgType!=undefined){
        var msgcolor=json.msgColor;
        if(json.msgColor==undefined){
            msgcolor="red";
        }
        msgType=json.msgType+","+_to_trim_all(msgcolor);
    }
    formObj.attr("msgtype",msgType);

    //validateType
    if(json.validateType!=undefined){
        formObj.attr("validate",json.validateType);
    }

    var inputs=json.inputConfigs;
    //alert(inputs.length);
    for(var i=0;i<inputs.length;i++){
        var name=inputs[i].name;
        var inputObj=$("[name='"+name+"']");
        if(inputObj.length<=0){
            alert("初始化加载失败，无法找到name="+name+"的input，请检查后台模板设置！");
            return;
        }
        //设置valiflag
        var valiflag="";
        valiflag=inputs[i].maxLength+",";
        if(inputs[i].valiflagKey!=undefined){
            valiflag=valiflag+inputs[i].valiflagKey;
        }
        valiflag=valiflag+",";
        if(inputs[i].msgName!=undefined){
            valiflag=valiflag+inputs[i].msgName;
        }
        valiflag=valiflag+",";
        if(inputs[i].isCanEmpty!=undefined){
            valiflag=valiflag+inputs[i].isCanEmpty;
        }
        inputObj.attr("valiflag",valiflag);

        //设置relation
        if(inputs[i].relation!=undefined){
            inputObj.attr("relation",inputs[i].relation);
        }

        //设置regMsg
        if(inputs[i].regMsg!=undefined){
            inputObj.attr("regmsg",inputs[i].regMsg);
        }

        //设置relmsg
        if(inputs[i].relMsg!=undefined){
            inputObj.attr("relmsg",inputs[i].relMsg);
        }

    }
}

//扩展jquery对象
(function($){
    $.fn.extend({
        //快速设置某对象的valiflag属性
        valiflag:function(setting){
            if(setting==undefined){
                return jQuery(this).attr("valiflag");
            }
            jQuery(this).attr("valiflag",setting);
        },
        //对单个文本框进行校验
        validate:function(){
            var formObj=jQuery(this).parentsUntil("form");
            return _on_blur_validate(formObj.attr("name"),jQuery(this),"normal",formObj.index(jQuery(this)));
        }
    });
})(jQuery);


//扩展js内置String对象
String.prototype.trim=function(){
    return (this+"").replace(/(^\s*)|(\s*$)/g, "");
}
String.prototype.trimAll=function(){
    return (this+"").replace(/\s+/g,"");
}

