$(document).ready(function(){
    formatYm(".dateYm");
    formatYmd(".dateYmd");
    zzjgdm(".zzjgdm");
    zdygs("[format]");
});

function formatYm(input){
    $(input).css("ime-mode","disabled");

    $(input).live("focus",function(){
        if (IsYearMonthFormat($(this).val()))
        {
            $(this).select();
            return;
        }
        $(this).val("____-__");
        $(this).select();
    });
    $(input).live("click",function(){
        var carePos = $(this).val().indexOf("_")!=-1?$(this).val().indexOf("_"):$(this).val().length;
        setCaretPosition(this,carePos);
    });
    $(input).live("keydown",function(eventTag){
        var event = eventTag||window.event;
        var keycode = event.charCode||event.keyCode;

        if(keycode==8)//back space
        {
            var strValue=new String(this.value);
            var pos=strValue.indexOf("_");

            if (parseInt(pos)>=0)
            {
                if(parseInt(pos)==0){
                    if(window.event)
                        window.event.returnValue = false;//ie
                    else
                        event.preventDefault();//for firefox
                }else if(parseInt(pos)==5){
                    this.value=strValue.substring(0,parseInt(pos-2))+"_"+strValue.substring(pos-1,strValue.length);
                    if(window.event)
                        window.event.returnValue = false;//ie
                    else
                        event.preventDefault();//for firefox
                    SetCursor(this,pos-2);
                }else{
                    this.value=strValue.substring(0,parseInt(pos-1))+"_"+strValue.substring(pos,strValue.length);
                    if(window.event)
                        window.event.returnValue = false;//ie
                    else
                        event.preventDefault();//for firefox
                    SetCursor(this,pos-1);
                }
            }else{
                if(IsYearMonthFormat(strValue)){
                    this.value=strValue.substring(0,strValue.length-1)+"_";
                    if(window.event)
                        window.event.returnValue = false;//ie
                    else
                        event.preventDefault();//for firefox
                    SetCursor(this,6);
                }else{
                    this.value="____-__";
                    if(window.event)
                        window.event.returnValue = false;//ie
                    else
                        event.preventDefault();//for firefox
                    SetCursor(this,0);
                }
            }
        }
    });


    $(input).live("keypress",function(eventTag){
        var event = eventTag||window.event;
        var keycode = event.charCode||event.keyCode;

        if(!IsNumberInput(keycode)){
            if(window.event)
                window.event.returnValue = false;//ie
            else
                event.preventDefault();//for firefox
            return;
        }

        var strTemp=new String(this.value);
        //日期格式的重新输入
        if (IsYearMonthFormat(strTemp)){
            if(window.event)
                window.event.returnValue = false;//ie
            else
                event.preventDefault();//for firefox
        }else{
            var strValue=new String(this.value);
            if (strValue.indexOf("_")>=0){
                var pos=strValue.indexOf("_");
                //年第一位只能是1,2
                if (pos=="0"){
                    if (keycode=="49" || keycode=="50"){
                        this.value=KeyCodeToChar(keycode)+"___-__"
                        SetCursor(this,1);
                    }
                    if(window.event)
                        window.event.returnValue = false;//ie
                    else
                        event.preventDefault();//for firefox
                    return;
                }
                //年前二位只能是19,20
                if (pos=="1"){
                    if (strValue.substring(0,1)=="1" ){
                        if (keycode!="57"){
                            if(window.event)
                                window.event.returnValue = false;//ie
                            else
                                event.preventDefault();//for firefox
                            return;
                        }
                    }else if (strValue.substring(0,1)=="2" ){
                        if (keycode!="48"){
                            if(window.event)
                                window.event.returnValue = false;//ie
                            else
                                event.preventDefault();//for firefox
                            return;
                        }
                    }
                }
                //月第一位不是0,1的转换格式(2006-8_转换为2006-08)
                if (pos=="5"){
                    if (!(keycode=="48" || keycode=="49")){
                        this.value=strValue.substring(0,parseInt(pos))+"0"+KeyCodeToChar(keycode);
                        if(window.event)
                            window.event.returnValue = false;//ie
                        else
                            event.preventDefault();//for firefox
                        SetCursor(this,7);
                        return;
                    }
                }
                //月第一位是1的第二位只能是0,1,2,
                if (pos=="6"){
                    if (strValue.substring(5,6)=="1" ){
                        if (!(keycode=="48" || keycode=="49" || keycode=="50")){
                            if(window.event)
                                window.event.returnValue = false;//ie
                            else
                                event.preventDefault();//for firefox
                            return;
                        }
                    }else if (strValue.substring(5,6)=="0" ){
                        if (keycode=="48"){
                            if(window.event)
                                window.event.returnValue = false;//ie
                            else
                                event.preventDefault();//for firefox
                            return;
                        }
                    }
                }
                this.value=strValue.substring(0,parseInt(pos))+KeyCodeToChar(keycode)
                    +strValue.substring(pos+1,strValue.length);
                if (pos=="3"){
                    SetCursor(this,pos+2);
                }else{
                    SetCursor(this,pos+1);
                }
                if(window.event)
                    window.event.returnValue = false;//ie
                else
                    event.preventDefault();//for firefox
            }
        }
    });


    //失去焦点时,对未输完的(2006-1_)数据进行补充,对不是日期的数据进行清空
    $(input).live("blur",function(){
        if (!IsYearMonthFormat(this.value))
        {
            if (this.value=="____-__"){
                this.value="";
                return;
            }
            if (this.value.substring(5,6)=="1")
            {
                this.value=this.value.substring(0,5)+"01";
            }else{
                this.value="";
                alert("年月日期格式不正确，请重新输入！");
            }
        }
    });

    $(input).bind("paste",function(eventTag){
        var event = eventTag||window.event;

        if(window.event)
            window.event.returnValue = false;//ie
        else
            event.preventDefault();//for firefox
    });
}

function formatYmd(input){
    $(input).css("ime-mode","disabled");

    $(input).live("focus",function(){
        if (IsDateFormat($(this).val()))
        {
            $(this).select();
            return;
        }
        $(this).val("____-__-__");
        $(this).select();

    });
    $(input).live("click",function(){
        var carePos = $(this).val().indexOf("_")!=-1?$(this).val().indexOf("_"):$(this).val().length;
        setCaretPosition(this,carePos);
    });
    $(input).live("keydown",function(eventTag){
        var event = eventTag||window.event;
        var keycode = event.charCode||event.keyCode;

        if(keycode==8)//back space
        {
            var strValue=new String(this.value);
            var pos=strValue.indexOf("_");

            if (parseInt(pos)>=0)
            {
                if(parseInt(pos)==0){
                    if(window.event)
                        window.event.returnValue = false;//ie
                    else
                        event.preventDefault();//for firefox
                }else if(parseInt(pos)==5){
                    this.value=strValue.substring(0,parseInt(pos-2))+"_"+strValue.substring(pos-1,strValue.length);
                    if(window.event)
                        window.event.returnValue = false;//ie
                    else
                        event.preventDefault();//for firefox
                    SetCursor(this,pos-2);
                }else if(parseInt(pos)==8){
                    this.value=strValue.substring(0,parseInt(pos-2))+"_"+strValue.substring(pos-1,strValue.length);
                    if(window.event)
                        window.event.returnValue = false;//ie
                    else
                        event.preventDefault();//for firefox
                    SetCursor(this,pos-2);
                }else{
                    this.value=strValue.substring(0,parseInt(pos-1))+"_"+strValue.substring(pos,strValue.length);
                    if(window.event)
                        window.event.returnValue = false;//ie
                    else
                        event.preventDefault();//for firefox
                    SetCursor(this,pos-1);
                }
            }else{
                if(IsDateFormat(strValue)){
                    this.value=strValue.substring(0,strValue.length-1)+"_";
                    if(window.event)
                        window.event.returnValue = false;//ie
                    else
                        event.preventDefault();//for firefox
                    SetCursor(this,9);
                }else{
                    this.value="____-__-__";
                    if(window.event)
                        window.event.returnValue = false;//ie
                    else
                        event.preventDefault();//for firefox
                    SetCursor(this,0);
                }
            }
        }
    });

    $(input).live("keypress",function(eventTag){
        var event = eventTag||window.event;
        var keycode = event.charCode||event.keyCode;

        if(!IsNumberInput(keycode)){
            if(window.event)
                window.event.returnValue = false;//ie
            else
                event.preventDefault();//for firefox
            return;
        }

        var strTemp=new String(this.value);
        //日期格式的重新输入
        if (IsDateFormat(strTemp)){
            if(window.event)
                window.event.returnValue = false;//ie
            else
                event.preventDefault();//for firefox
        }else{
            var strValue=new String(this.value);
            if (strValue.indexOf("_")>=0){
                var pos=strValue.indexOf("_");
                //年第一位只能是1,2
                if (pos=="0"){
                    if (keycode=="49" || keycode=="50"){
                        this.value=KeyCodeToChar(keycode)+"___-__-__"
                        SetCursor(this,1);
                    }
                    if(window.event)
                        window.event.returnValue = false;//ie
                    else
                        event.preventDefault();//for firefox
                    return;
                }
                //年前二位只能是19,20
                if (pos=="1"){
                    if (strValue.substring(0,1)=="1" ){
                        if (keycode!="57"){
                            if(window.event)
                                window.event.returnValue = false;//ie
                            else
                                event.preventDefault();//for firefox
                            return;
                        }
                    }else if (strValue.substring(0,1)=="2" ){
                        if (keycode!="48"){
                            if(window.event)
                                window.event.returnValue = false;//ie
                            else
                                event.preventDefault();//for firefox
                            return;
                        }
                    }
                }
                //月第一位不是0,1的转换格式(2006-8_转换为2006-08)
                if (pos=="5"){
                    if (!(keycode=="48" || keycode=="49")){
                        this.value=strValue.substring(0,parseInt(pos))+"0"+KeyCodeToChar(keycode)+"-__";
                        if(window.event)
                            window.event.returnValue = false;//ie
                        else
                            event.preventDefault();//for firefox
                        SetCursor(this,8);
                        return;
                    }
                }
                //月第一位是1的第二位只能是0,1,2,
                if (pos=="6"){
                    if (strValue.substring(5,6)=="1" ){
                        if (!(keycode=="48" || keycode=="49" || keycode=="50")){
                            if(window.event)
                                window.event.returnValue = false;//ie
                            else
                                event.preventDefault();//for firefox
                            return;
                        }
                    }else if (strValue.substring(5,6)=="0" ){
                        if (keycode=="48"){
                            if(window.event)
                                window.event.returnValue = false;//ie
                            else
                                event.preventDefault();//for firefox
                            return;
                        }
                    }
                }
                //天的第一位
                if(pos=="8"){
                    //2月天数第一位是0,1,2
                    var monthStr = strValue.substring(5,7);//月
                    if(monthStr=="02"){
                        if (!(keycode=="48" || keycode=="49" || keycode=="50")){
                            if(window.event)
                                window.event.returnValue = false;//ie
                            else
                                event.preventDefault();//for firefox
                            return;
                        }
                    }else if(monthStr=="04" || monthStr=="06" || monthStr=="09" || monthStr=="11"){
                        if(keycode=="51")
                        {
                            this.value=strValue.substring(0,parseInt(pos))+KeyCodeToChar(keycode)+"0";
                            if(window.event)
                                window.event.returnValue = false;//ie
                            else
                                event.preventDefault();//for firefox
                            SetCursor(this,10);
                            return;
                        }else if(!(keycode=="48" || keycode=="49" || keycode=="50")){
                            if(window.event)
                                window.event.returnValue = false;//ie
                            else
                                event.preventDefault();//for firefox
                            return;
                        }
                    }else{
                        if (!(keycode=="48" || keycode=="49" || keycode=="50" || keycode=="51")){
                            if(window.event)
                                window.event.returnValue = false;//ie
                            else
                                event.preventDefault();//for firefox
                            return;
                        }
                    }
                }
                if(pos=="9"){
                    var dayStr1 = strValue.substring(8,9);//天的第一位
                    var yearStr = strValue.substring(0,4);//年
                    var monthStr = strValue.substring(5,7);//月

                    if(monthStr == "02" && dayStr1 == "2"){
                        if(!isLeapYear(yearStr)){
                            if(keycode=="57"){
                                if(window.event)
                                    window.event.returnValue = false;//ie
                                else
                                    event.preventDefault();//for firefox
                                return;
                            }
                        }
                    }
                    else if(monthStr=="04" || monthStr=="06" || monthStr=="09" || monthStr=="11"){
                        if(dayStr1=="3"){
                            if(keycode!="48"){
                                if(window.event)
                                    window.event.returnValue = false;//ie
                                else
                                    event.preventDefault();//for firefox
                                return;
                            }
                        }
                    }else{
                        if(dayStr1=="3"){
                            if(!(keycode=="48"||keycode=="49")){
                                if(window.event)
                                    window.event.returnValue = false;//ie
                                else
                                    event.preventDefault();//for firefox
                                return;
                            }
                        }
                    }

                    if(dayStr1=="0"){
                        if(keycode=="48")
                        {
                            if(window.event)
                                window.event.returnValue = false;//ie
                            else
                                event.preventDefault();//for firefox
                            return;
                        }
                    }
                }

                this.value=strValue.substring(0,parseInt(pos))+KeyCodeToChar(keycode)
                    +strValue.substring(pos+1,strValue.length);
                if (pos=="3" || pos=="6"){
                    SetCursor(this,pos+2);
                }else{
                    SetCursor(this,pos+1);
                }
                if(window.event)
                    window.event.returnValue = false;//ie
                else
                    event.preventDefault();//for firefox
            }
        }
    });

    //失去焦点时,对未输完的(2006-1_)数据进行补充,对不是日期的数据进行清空
    $(input).live("blur",function(){
        if (!IsDateFormat(this.value))
        {
            var dayStr1 = this.value.substring(8,9);
            if (this.value=="____-__-__"){
                this.value="";
                return;
            }
            if (dayStr1=="1" || dayStr1=="2" || dayStr1=="3")
            {
                this.value=this.value.substring(0,8)+"0"+dayStr1;
            }else{
                this.value="";
                alert("年月日期格式不正确，请重新输入！");
            }
        }
    });

    $(input).bind("paste",function(eventTag){
        var event = eventTag||window.event;

        if(window.event)
            window.event.returnValue = false;//ie
        else
            event.preventDefault();//for firefox
    });
}

function zzjgdm(input){
    $(input).css("ime-mode","disabled");

    $(input).live("focus",function(){
        if (IsZzjgdmFormat(this.value))
        {
            this.select();
            return;
        }
        this.value="________-_";
        this.select();
    });
    $(input).live("click",function(){
        var carePos = $(this).val().indexOf("_")!=-1?$(this).val().indexOf("_"):$(this).val().length;
        setCaretPosition(this,carePos);
    });
    $(input).live("keydown",function(eventTag){
        var event = eventTag||window.event;
        var keycode = event.charCode||event.keyCode;

        if(keycode==8)  //退格键盘
        {
            var strValue=new String(this.value);
            var pos=strValue.indexOf("_");
            if (parseInt(pos)>=0)
            {
                if(parseInt(pos)==0){
                    if(window.event)
                        window.event.returnValue = false;//ie
                    else
                        event.preventDefault();//for firefox
                }else if(parseInt(pos)==9){
                    this.value=strValue.substring(0,parseInt(pos-2))+"_"+strValue.substring(pos-1,strValue.length);
                    if(window.event)
                        window.event.returnValue = false;//ie
                    else
                        event.preventDefault();//for firefox
                    SetCursor(this,pos-2);
                }else{
                    this.value=strValue.substring(0,parseInt(pos-1))+"_"+strValue.substring(pos,strValue.length);
                    if(window.event)
                        window.event.returnValue = false;//ie
                    else
                        event.preventDefault();//for firefox
                    SetCursor(this,pos-1);
                }
            }else{
                if(IsZzjgdmFormat(strValue)){
                    this.value=strValue.substring(0,strValue.length-1)+"_";
                    if(window.event)
                        window.event.returnValue = false;//ie
                    else
                        event.preventDefault();//for firefox
                    SetCursor(this,9);
                }else{
                    this.value="________-_";
                    if(window.event)
                        window.event.returnValue = false;//ie
                    else
                        event.preventDefault();//for firefox
                    SetCursor(this,0);
                }
            }
        }
    });

    $(input).live("keypress",function(eventTag){
        var event = eventTag||window.event;
        var keycode = event.charCode||event.keyCode;

        //只能输入数字和字母
        if(!IsCharInput(keycode)){
            if(window.event)
                window.event.returnValue = false;//ie
            else
                event.preventDefault();//for firefox
            return;
        }
        //
        var strTemp=new String(this.value);

        if (IsZzjgdmFormat(strTemp)){
            this.value = (this.value).toUpperCase();
            if(window.event)
                window.event.returnValue = false;//ie
            else
                event.preventDefault();//for firefox
        }else{
            var strValue=new String(this.value);
            if (strValue.indexOf("_")>=0){
                var pos=strValue.indexOf("_");

                this.value=strValue.substring(0,parseInt(pos))+KeyCodeToChar(keycode)
                    +strValue.substring(pos+1,strValue.length);
                if (pos=="7"){
                    SetCursor(this,pos+2);
                }else{
                    SetCursor(this,pos+1);
                }

            }
            if(window.event)
                window.event.returnValue = false;//ie
            else
                event.preventDefault();//for firefox
        }
    });

    $(input).live("blur",function(){
        if (!IsZzjgdmFormat(this.value))
        {
            if (this.value=="________-_"){
                this.value="";
                return;
            }else{
                this.value="";
                alert("组织机构代码格式不正确，请重新输入！");
            }
        }else{
            this.value = (this.value).toUpperCase();
        }
    });

    $(input).bind("paste",function(eventTag){
        var event = eventTag||window.event;

        if (this.value=="________-_"){
            this.value="";
            return;
        }else{
            this.value="________-_";
            if(window.event)
                window.event.returnValue = false;//ie
            else
                event.preventDefault();//for firefox
        }
    });
}
function zdygs(input){
    var valformat = $(input).attr("format");//格式配置
    var formatlimited = $(input).attr("formatlimited");//格式限制
    var formatname = $(input).attr("formatname");

    if(formatlimited  == null||formatlimited  == "" || formatlimited  == "undefined" || formatlimited .length==0){
        formatlimited = valformat;
    }
    $(input).css("ime-mode","disabled");//
    $(input).live("focus",function(){

        if(IsPfwhFormat(this.value,formatlimited)){
            this.select();
            return;
        }

        this.value = valformat;
        this.select();
    });

    $(input).live("click",function(){
        var carePos = $(this).val().indexOf("_")!=-1?$(this).val().indexOf("_"):$(this).val().length;
        setCaretPosition(this,carePos);
    });
    $(input).live("keydown",function(eventTag){
        var event = eventTag||window.event;
        var keycode = event.charCode||event.keyCode;

        if(keycode==8)  //退格键盘
        {
            var strValue=new String(this.value);
            var pos=getCursortPosition(this);
            if (parseInt(pos)>=0)
            {
                if(parseInt(pos)==valformat.indexOf("_")){
                    if(window.event)
                        window.event.returnValue = false;//ie
                    else
                        event.preventDefault();//for firefox

                }else{
                    this.value=strValue.substring(0,parseInt(pos-1))+valformat.substring(parseInt(pos-1),strValue.length);
                    if(window.event)
                        window.event.returnValue = false;//ie
                    else
                        event.preventDefault();//for firefox
                    if(getLengthPos(pos,valformat) == 0){
                        setCaretPosition(this,pos-1);

                    }else{
                        setCaretPosition(this,pos-getLengthPos(pos,valformat));
                    }
                }
            }else{
                if(IsPfwhFormat(strValue,formatlimited)){

                    this.value=strValue.substring(0,valformat.lastIndexOf("_"))+valformat.substring(valformat.lastIndexOf("_"),valformat.length);

                    if(window.event)
                        window.event.returnValue = false;//ie
                    else
                        event.preventDefault();//for firefox
                    setCaretPosition(this,strValue.lastIndexOf("_"));
                }else{
                    this.value = valformat;
                    if(window.event)
                        window.event.returnValue = false;//ie
                    else
                        event.preventDefault();//for firefox
                    setCaretPosition(this,valformat.indexOf("_"));
                }
            }
        }

    });

    $(input).live("keypress",function(eventTag){
        var event = eventTag||window.event;
        var keycode = event.charCode||event.keyCode;

        //只能输入数字和字母
        if(!IsCharInput(keycode)){
            if(window.event)
                window.event.returnValue = false;//ie
            else
                event.preventDefault();//for firefox
            return;
        }

        //==================================================
        var pos = this.value.indexOf("_");

        if(pos >= 0){
            if(formatlimited.substr(pos,1) == "N"){

                if(!IsNumberInput(keycode)){
                    if(window.event)
                        window.event.returnValue = false;//ie
                    else
                        event.preventDefault();//for firefox
                    return;
                }
            }
            if(formatlimited.substr(pos,1) == "C"){

                if(IsNumberInput(keycode) || IscharInput(keycode)){
                    if(window.event)
                        window.event.returnValue = false;//ie
                    else
                        event.preventDefault();//for firefox
                    // setCaretPosition(this,format.indexOf("C"));
                    // alert("只能填写A-Z大写英文字母，请重新填写！");
                    return;
                }
            }
            if(formatlimited.substr(pos,1) == "c"){

                if(!IscharInput(keycode)){
                    if(window.event)
                        window.event.returnValue = false;//ie
                    else
                        event.preventDefault();//for firefox
                    // setCaretPosition(this,format.indexOf("c"));
                    //alert("只能填写a-z小写英文字母，请重新填写！");
                    return;
                }
            }
        }
        //===================================================
        var strTemp=new String(this.value);

        if (IsPfwhFormat(strTemp,formatlimited)){
            //this.value = (this.value).toUpperCase();
            if(window.event)
                window.event.returnValue = false;//ie
            else
                event.preventDefault();//for firefox
        }else{
            var strValue=new String(this.value);
            if (strValue.indexOf("_")>=0){
                var pos=strValue.indexOf("_");

                this.value=strValue.substring(0,parseInt(pos))+KeyCodeToChar(keycode)
                    +strValue.substring(pos+1,strValue.length);
                if (pos=="7"){
                    setCaretPosition(this,pos+2);
                }else{
                    setCaretPosition(this,pos+1);
                }

            }
            if(window.event)
                window.event.returnValue = false;//ie
            else
                event.preventDefault();//for firefox
        }
    });

    $(input).live("blur",function(){
        if (!IsPfwhFormat(this.value,formatlimited))
        {
            if (this.value==valformat){
                this.value="";
                return;
            }else{
                this.value="";

                if(formatname != ""){
                    alert(formatname+"不正确，请重新输入！");
                }else{
                    alert("格式错误，请重新输入！");
                }
            }
        }//else{
        //this.value = (this.value).toUpperCase();
        //}
    });

    $(input).bind("paste",function(eventTag){
        var event = eventTag||window.event;

        if (this.value==valformat){
            this.value="";
            return;
        }else{
            this.value=valformat;
            if(window.event)
                window.event.returnValue = false;//ie
            else
                event.preventDefault();//for firefox
        }
    });

}

function IsPfwhFormat(theStr,format)
{
    if(format == null||format == "" || format == "undefined" || format.length==0){
        return;
    }

    var tempChar = "";
    var temp = "";
    for(var i=0;i<format.length;i++){
        tempChar = format.substr(i,1);
        if(tempChar!="_"){
            if(tempChar == "N"){
                temp += "([0-9]){1}";
            }else if(tempChar == "C"){
                temp += "([A-Z]){1}";
            }else if(tempChar == "c"){
                temp += "([a-z]{1})";
            }else{
                temp += ("\\"+tempChar+"{1}");
            }
        }else{
            temp +="([A-Z]|[a-z]|[0-9]){1}";
        }
    }
    //alert(temp);
    var reg = new RegExp("^"+temp+"$");

    if(!reg.test(theStr)){

        return false;
    }
    return true;
}
function SetCursor(ctrl, pos){
    if(ctrl.setSelectionRange)
    {
        //ctrl.focus();
        ctrl.setSelectionRange(pos,pos);
    }
    else if (ctrl.createTextRange) {
        var range = ctrl.createTextRange();
        range.collapse(true);
        range.moveEnd('character', pos);
        range.moveStart('character', pos);
        range.select();
    }
}
//判断字符串是否符合日期格式，如1999-03-07
function IsDateFormat(theStr)
{
    var strObj=new String(theStr);
    var strObjTemp;
    //1.theStr.length<>10
    if(strObj.length!=10)
        return false;
    //2.判断第五位、第八位是"-"
    if(strObj.substring(4,5)!="-")
        return false;
    if (strObj.substring(7,8)!="-")
        return false;
    //3.校验年部分是数字，并在1900~2100之间，月部分是数字，并在1~12之间，日部分是数字，并在1~31之间
    strObjTemp=new String(strObj.substring(0,4));
    if(!(IsNumber(strObjTemp)) || parseInt(strObjTemp,10)<=1900 || parseInt(strObjTemp,10)>2100)
        return false;
    strObjTemp=new String(strObj.substring(5,7));
    if  (!(IsNumber(strObjTemp)) || parseInt(strObjTemp,10) < 1  || parseInt(strObjTemp,10)>12)
        return false;
    strObjTemp=new String(strObj.substring(8,10));
    if(!(IsNumber(strObjTemp)) || parseInt(strObjTemp,10)<1 || parseInt(strObjTemp,10)>31)
        return false;
    return true;
}
//判断字符串是否符合年月格式，如1999-03
function IsYearMonthFormat(theStr)
{
    var strObj=new String(theStr);
    var strObjTemp;
    //1.theStr.length<>10
    if(strObj.length!=7)
        return false;
    //2.判断第五位是"-"
    if(strObj.substring(4,5)!="-")
        return false;
    //3.校验年部分是数字，并在1900~2100之间，月部分是数字，并在1~12之间
    strObjTemp=new String(strObj.substring(0,4));
    if(!(IsNumber(strObjTemp)) || parseInt(strObjTemp,10)<=1900 || parseInt(strObjTemp,10)>2100)
        return false;
    strObjTemp=new String(strObj.substring(5,7));
    if  (!(IsNumber(strObjTemp)) || parseInt(strObjTemp,10) < 1  || parseInt(strObjTemp,10)>12)
        return false;
    return true;
}
//判断输入的KeyCode是否数字
//0~9KeyCode:48~57
function IsNumberInput(theStr)
{
    var numberkeycode= "48,49,50,51,52,53,54,55,56,57";
    if(!IsNumber(theStr)){//判断是否数值型
        return false;
    }
    else{
        var theNum = Number(theStr);
        if(theNum>=48 && theNum <= 57 )
            return true;
        else
            return false;
        //alert(numberkeycode.indexOf(theStr) >= 0);
    }
}
function IsZzjgdmFormat(theStr)
{
    var reg = /^([A-Z]|[a-z]|[0-9]){8}(\-){1}([A-Z]|[a-z]|[0-9]){1}$/;

    if(!reg.test(theStr)){
        return false;
    }
    return true;
}
//小键盘码值转化
function KeyCodeToChar(theStr){
    var strTemp=parseInt(theStr);
    return String.fromCharCode(strTemp);
}
function isLeapYear(yearStr)
{
    var yearNum = Number(yearStr);

    if(yearNum%4==0)
    {
        if(yearNum%100==0)
        {
            if(yearNum%400==0)//被4和100和400同时整除true
            {
                return true;
            }
            else{//被4和100整除,不被400整除false
                return false;
            }
        }else{//被4整除,不能被100整除true
            return true;
        }
    }else{//不能被4整除false
        return false;
    }
}
//判断输入的KeyCode是否数字
//A~ZKeyCode:65~50  数字键0-9keycode：48-57 小键盘0-9keycode：48-57  a-z KeyCode:97-122
function IsCharInput(theStr)
{
    if((theStr>=65&&theStr<=90)||(theStr>=48&&theStr<=57)||(theStr>=97&&theStr<=122)){
        return true;
    }
    return false;
}

function getCursortPosition (ctrl) {//获取光标位置函数
    var CaretPos = 0;	// IE Support
    if (document.selection) {
        ctrl.focus ();
        var Sel = document.selection.createRange ();
        Sel.moveStart ('character', -ctrl.value.length);
        CaretPos = Sel.text.length;
    }
    // Firefox support
    else if (ctrl.selectionStart || ctrl.selectionStart == '0')
        CaretPos = ctrl.selectionStart;
    return (CaretPos);
}

function setCaretPosition(ctrl, pos){//设置光标位置函数
    if(ctrl.setSelectionRange)
    {
        ctrl.focus();
        ctrl.setSelectionRange(pos,pos);
    }
    else if (ctrl.createTextRange) {
        var range = ctrl.createTextRange();
        range.collapse(true);
        range.moveEnd('character', pos);
        range.moveStart('character', pos);
        range.select();
    }
}

//判断字符串是否数字
function IsNumber(theStr)
{
    var flag = !isNaN(theStr);//判断不是数值型字符串
    return flag;
}

//转换为格式
function formatReplace(theStr){
    var str = theStr.replace(/[NCc]/g,"_");
    return str;
}

//判定小写字母
function IscharInput(theStr)
{
    if(theStr>=97&&theStr<=122){
        return true;
    }
    return false;
}

function getLimitedChar(format,formatlimited,pos){

    if(format.substr(pos,1) != formatlimited.substr(pos,1)){
        return formatlimited.substr(pos,1);
    }
    return "";
}

function getLengthPos(pos,format){

    var s =format.substring(0,pos);
    var m = 0;
    if(s.lastIndexOf("_") >= 0){
        m =s.substring(s.lastIndexOf("_")+1).length;
    }
    return m;
}

