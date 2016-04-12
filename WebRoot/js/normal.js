/**
 * Created by Gnet on 2015/11/17.
 */
$(function(){

    $("#tablist li").click( function(){
        $("#tablist li").removeClass("active");
        $(this).addClass("active");
        $("#tabcon .item").hide();
        var _index=$(this).index();
        $("#tabcon .item:eq("+_index+")").show();
    })

    $(".o_topnav li").click(function(){
        $(".o_topnav li").removeClass("active");
        $(this).closest("li").addClass("active");
        $(this).addClass("active");
    });
    
    $(".rig_table tbody tr").click(function(){
        $(".rig_table tbody tr").removeClass("active");
        $(this).closest("tr").addClass("active");
        $(this).addClass("active");
    });

    $(".rig_block .device_list .device_li").click(function(){
        $(".rig_block .device_list .device_li").removeClass("active");
        $(this).closest("div").addClass("active");
        $(this).addClass("active");
    });

    document.onkeydown = function(e){
    	   if(!e){
    		   e = window.event;
    	   }
    	   if((e.keyCode || e.which) == 13){
    		   if($("div.item:visible").hasClass("loginUser")){
    			   checUserkLogin();
    		   }else if($("div.item:visible").hasClass("loginCompinany")){
    			   checkCompanyLogin();
    		   }
    	   }
    }



}); 
   