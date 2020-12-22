// JavaScript Document
//????
//function sjld(xzqh,path,num1,num2,num3,num4){
function sjld(xzqh,path){
    var sel_first="";
    var sel_second="";
    var sel_third="";
    var sel_fourth="";
    sel_first=xzqh.substr(0,2);
    sel_second=xzqh.substr(2,4);
    sel_third=xzqh.substr(6,3);
    sel_fourth=xzqh.substr(9,3);


//==============================================================

    var ajaxcommon1 = AjaxCommon.getInstance({
        url : path,
        data : {lb: "0" ,param: sel_first,sel: sel_first},
        operSuc : function(data, textStatus){
            $("[id='qj']").empty();
            $("[id='sj']").append(data.returnData.html);

            if($("select[id='qj']").length>0){
                var ajaxcommon2 = AjaxCommon.getInstance({
                    url : path,
                    data : {lb: "1" ,param: $("[id='sj']").val(),sel: sel_second},
                    operSuc : function(data, textStatus){
                        $("[id='qj']").append("<option value=\"000000000000\">"+"请选择"+"</option>").append(data.returnData.html);


                        if($("select[id='jj']").length>0){
                            var param1 = sel_first + sel_second ;
                            var ajaxcommon3 = AjaxCommon.getInstance({
                                url : path,
                                data : {lb: "2" ,param: $("[id='qj']").val(),sel: sel_third},
                                operSuc : function(data, textStatus){
                                    $("[id='jj']").append("<option value=\"000000000000\">"+"请选择"+"</option>").append(data.returnData.html);


                                    if($("select[id='cj']").length>0){
                                        var param2 = sel_first + sel_second + sel_third ;
                                        var ajaxcommon4 = AjaxCommon.getInstance({
                                            url : path,
                                            data : {lb: "3" ,param: $("[id='jj']").val(),sel: sel_fourth},
                                            operSuc : function(data, textStatus){
                                                $("[id='cj']").append("<option value=\"000000000000\">"+"请选择"+"</option>").append(data.returnData.html);

                                            }
                                        });
                                        ajaxcommon4.submit();
                                    }
                                }
                            });
                            ajaxcommon3.submit();
                        }
                    }
                });
                ajaxcommon2.submit();
            }
        }
    });

    ajaxcommon1.submit();
}



function onFirst(path){//$(a).attr("id")  ???
    var Default="000000000000";
    $("[id='qj']").empty();//#second
    $("[id='jj']").empty();//#third
    $("[id='cj']").empty();//#fourth
    var fws= $("[id='sj']").val();
    if(fws=='00'){
        $("[id='qj']").append("<option value=\""+Default+"\">"+"请选择"+"</option>");//#second
        $("[id='jj']").append("<option value=\""+Default+"\"'>"+"请选择"+"</option>");//#third
        $("[id='cj']").append("<option value=\""+Default+"\">"+"请选择"+"</option>");//#fourth
        return;
    }else{
        var ajaxcommon = AjaxCommon.getInstance({
            url : path,
            data : {lb: "1" ,param: fws},
            operSuc : function(data, textStatus){
                $("[id='qj']").append("<option value=\""+Default+"\">"+"请选择"+"</option>").append(data.returnData.html);//#second
                $("[id='jj']").append("<option value=\""+Default+"\">"+"请选择"+"</option>");//#third
                $("[id='cj']").append("<option value=\""+Default+"\">"+"请选择"+"</option>");//#fourth

            }
        });
        ajaxcommon.submit();
    }
}

function onSecond(path){//???
    var Default="000000000000";
    $("[id='jj']").empty();//#third
    $("[id='cj']").empty();//#fourth
    var third = $("[id='qj']").val();

    var xzqh=third;


    if(third=='000'){
        $("[id='cj']").append("<option value=\""+Default+"\">"+"请选择"+"</option>");//#fourth"
        return;
    }else{
        var ajaxcommon = AjaxCommon.getInstance({
            url : path,
            data : {lb: "2" ,param: xzqh},
            operSuc : function(data, textStatus){
                $("[id='jj']").append("<option value=\""+Default+"\">"+"请选择"+"</option>").append(data.returnData.html);//#third
                $("[id='cj']").append("<option value=\""+Default+"\">"+"请选择"+"</option>");//#fourth

            }
        });
        ajaxcommon.submit();
    }
}


function onThird(path) //???
{
    var Default="000000000000";
    $("[id='cj']").empty();//#fourth
    var fourth = $("[id='jj']").val();
    var xzqh=fourth;

    if(fourth=='000'){
        $("[id='cj']").append("<option value=\""+Default+"\">"+"请选择"+"</option>");//#fourth
        return;
    }else{
        var ajaxcommon = AjaxCommon.getInstance({
            url : path,
            data : {lb: "3" ,param: xzqh},
            operSuc : function(data, textStatus){
                $("[id='cj']").append("<option value=\""+Default+"\">"+"请选择"+"</option>").append(data.returnData.html);//#fourth

            }
        });
        ajaxcommon.submit();
    }
}

