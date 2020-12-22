
//=========================================================
//校验互斥项必须选择一个（必须与it同时使用）
//=========================================================
function checkhc(formName){
    var default_hcgroup_name="";
    var isRight=true;
    var showMsg="";
    $("[name='"+formName+"']").find("input[hcgroup]").each(function(){
        //如果该组group已经校验过了。则跳过
        if($(this).attr("hcgroup")==default_hcgroup_name){
            return true;
        }
        //如果无需校验，则跳过。
        if($(this).attr("[notcheck]")+""!="undefined"){
            default_hcgroup_name=$(this).attr("hcgroup");
            return true;
        }
        var isOneCheck=false;
        $("[hcgroup='"+$(this).attr("hcgroup")+"']").each(function(){
            if($(this).attr("checked")=="checked"){
                isOneCheck=true;
                return false;
            }
        });

        if(isOneCheck==false){
            showMsg=$(this).attr("hcmsg");
            isRight=false;
            return false;
        }
        default_hcgroup_name=$(this).attr("hcgroup");

    });

    if(isRight==false){
        alert(showMsg+"必须选择一项。");
    }
    return isRight;
}