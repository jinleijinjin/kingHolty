$(".adduser").click(function(e){
	$('#add_formtip').html('');
	e.preventDefault();
	$('#addUserDiv').modal('show');	
});

//查看user信息
$(".viewuser").click(function(e){
	var vi_id = $(this).attr('id');
	$.ajax({
		url:'/SLSailSystem/backend/getuser.html',
		type:'POST',
		data:{id:vi_id},
		dataType:'json',
		timeout:1000,
		error:function(){
			alert("查看信息过程中粗错了！");
		},
		success:function(result){
			if (result=="failed") {
				alert("操作超时！");
			}else if(result=="nodata"){
				alert("没有数据！");
			}else {
				m = result;
				$("#v_id").val(m.id);
				$("#v_rolename").val(m.roleName);
				$("#v_logincode").val(m.loginCode);
				$("#v_username").val(m.userName);
				$("#dasdasd").val(m.birthday);
				$("#v_cardtypename").val(m.cardTypeName);
				$("#v_rolename").val(m.roleName);
				$("#v_usertypename").val(m.userTypeName);
				$("#v_sex").val(m.sex);
				$("#v_idcard").val(m.idCard);
				$("#v_country").val(m.country);
				$("#v_mobile").val(m.mobile);
				$("#v_email").val(m.email);
				$("#v_postcode").val(m.postCode);
				$("#v_bankcode").val(m.bankAccount);
				$("#v_bankpepo").val(m.accountHolder);
				$("#v_bankname").val(m.bankName);
				$("#v_cdate").val(m.createTime);
				$("#v_referCode").val(m.referCode);
				var isStart = m.isStart;
				if (isStart==1) {
					$("#v_isStart").append("<option value=\"1\" selected=\"selected\">启用</option>");
				}else {
					$("#v_isStart").append("<option value=\"2\" >未启用</option>");
				}
				
				$("#v_userAddress").val(m.userAddress);
				var v_idcardpicpath = m.idCardPicPath;
				if (v_idcardpicpath==null || v_idcardpicpath=="") {
					$("#v_idPic").append("暂无！");
				}else {
					$("#v_idPic").append("<p><img src=\""+v_idcardpicpath+"?m="+Math.random()+"\"/></p>");						
				}
				var v_bankPicPath = m.bankPicPath;
				if (v_bankPicPath==null || v_bankPicPath=="") {
					$("#v_bankPic").append("暂无！");
				}else {
					$("#v_bankPic").append("<p><img src=\""+v_bankPicPath+"?m="+Math.random()+"\"/></p>");						
				}
				e.preventDefault();
				$('#viewUserDiv').modal('show');
			}
		}
	});	
});

//修改user信息
$(".modifyuser").click(function(e){
	var m_id = $(this).attr('id');
	$.ajax({
		url:'/SLSailSystem/backend/getuser.html',
		type:'POST',
		data:{id:m_id},
		dataType:'json',
		timeout:1000,
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			 alert(XMLHttpRequest.status);
			 alert(XMLHttpRequest.readyState);
			 alert(textStatus);
		},
		success:function(result){
			if (result=="failed") {
				alert("操作超时！");
			}else if(result=="nodata"){
				alert("没有数据！");
			}else {
				mo = result;
				$("#m_id").val(mo.id);
				$("#m_rolename").val(mo.roleName);
				$("#m_logincode").val(mo.loginCode);
				$("#m_username").val(mo.userName);
				$("#m_birthday").val(mo.birthday);
				$("#m_cardtypename").val(mo.cardTypeName);
				var cardType = mo.cardType;
				var cardTypeName = mo.cardTypeName;
				$("#m_selectcardtype").html('');
				
				if (cardType==null) {
					
					$("#m_selectcardtype").append("<option value=\"\" selected=\"selected\">--请选择--</option>");				
				}
				
				for (var i = 0; i < cartTypeListJson.length-1; i++) {
					
						if (cartTypeListJson[i].valueId==cardType) {
							$("#m_selectcardtype").append("<option value=\""+cardType+"\" selected=\"selected\">"+mo.cardTypeName+"</option>");
						}else {
							$("#m_selectcardtype").append("<option value=\""+cartTypeListJson[i].valueId+"\" >"+cartTypeListJson[i].valueName+"</option>");
						}
					}
				
				
				var roleId = mo.roleId;
				var roleName = mo.roleName;
				$("#m_roleid").html("");
				$("#m_rolename").val(roleName);
				if (roleId==null || roleId=="") {
					$("#m_roleid").append("<option value=\"\" selected=\"selected\">--请选择--</option>");
				}
				for (var j = 0; j < roleListJson.length-1; j++) {
					if (roleListJson[j].id==roleId) {
						$("#m_roleid").append("<option value=\""+roleId+"\" selected=\"selected\">"+roleName+"</option>");
					}else {
						$("#m_roleid").append("<option value=\""+roleListJson[j].id+"\" >"+roleListJson[j].roleName+"</option>");
					}
				}
				
				
				$("#m_selectUserTypename").val(mo.userTypeName);
				$("#m_selectUserType").html('');
				if (roleId==2) {
					var userType = mo.userType;
					var userTypeName = mo.userTypeName;
					if (userType==null || userType=="") {
						$("#m_selectUserType").append("<option value=\"\" selected=\"selected\">--请选择--</option>");
					}
					$.post("/SLSailSystem/backend/loadUserTypeList.html",{"s_roleId":roleId},function(r){
						if (r!=null && r!="") {
							for (var i = 0; i < r.length; i++) {
								if (r[i].valueId==userType) {
									$("#m_selectUserType").append("<option value=\""+userType+"\" selected=\"selected\">"+userTypeName+"</option>");
								}else {
									$("#m_selectUserType").append("<option value=\""+r[i].valueId+"\" >"+r[i].valueName+"</option>");
								}
							}
						}else {
							alert("用户类型加载失败！");
						}
						
					},'json');
				}else {
					$("#m_selectUserType").append("<option value=\"\" selected=\"selected\">--请选择--</option>");
				}
				
				
				var sex = mo.sex;
				$("#m_sex").html("");
				if (sex=="") {
					$("#m_sex").append("<option value=\"\" selected=\"selected\">--请选择--</option><option value=\"男\" >男</option><option value=\"女\" >女</option>");
				}else if (sex=="男") {
					$("#m_sex").append("<option value=\"男\" selected=\"selected\">男</option><option value=\"女\" >女</option>");
				}else {
					$("#m_sex").append("<option value=\"男\" >男</option><option value=\"女\" selected=\"selected\">女</option>");
				}
				
				$("#m_cardtypename").val(mo.cardTypeName);
				$("#m_rolename").val(mo.roleName);							
				$("#m_idcard").val(mo.idCard);
				$("#m_country").val(mo.country);
				$("#m_mobile").val(mo.mobile);
				$("#m_email").val(mo.email);
				$("#m_postcode").val(mo.postCode);
				$("#m_bankcode").val(mo.bankAccount);
				$("#m_bankpepo").val(mo.accountHolder);
				$("#m_bankname").val(mo.bankName);
				$("#m_cdate").val(mo.createTime);
				$("#m_referCode").val(mo.referCode);
				var isstart = mo.isStart;										
				if (isstart=='1') {
					$("#m_isStart").append("<option value=\"1\" selected=\"selected\">启用</option><option value=\"2\" >未启用</option>");
				}else {
					$("#m_isStart").append("<option value=\"1\" >启用</option><option value=\"2\" selected=\"selected\">未启用</option>");
				}			
				$("#m_userAddress").val(mo.userAddress);
				
				
				var m_idcardpicpath = mo.idCardPicPath;
				$("#m_fileInputIDPath").val(mo.idCardPicPath);
				if (m_idcardpicpath==null || m_idcardpicpath=="") {
					$("#m_uploadbtnID").show();
					$("#m_idPic").append("暂无！");
				}else {
					$("#m_uploadbtnID").hide();//                  id,closeSpan,uploadBtn,obj,picpath,picText,fileinputid
					$("#m_idPic").append("<p><span onclick=\"delpic('"+mo.id+"','m_idPic','m_uploadbtnID',this,'"+m_idcardpicpath+"','m_fileInputIDPath','m_fileInputID');\">x</span>" +
							"<img src=\""+m_idcardpicpath+"?m="+Math.random()+"\"/></p>");						
				}
				var m_bankPicPath = mo.bankPicPath;
				$("#m_fileInputBankPath").val(mo.bankPicPath);
				if (m_bankPicPath==null || m_bankPicPath=="") {
					$("#m_uploadbtnBank").show();
					$("#m_bankPic").append("暂无！");
				}else {
					$("#m_uploadbtnBank").hide();
					$("#m_bankPic").append("<p><span onclick=\"delpic('"+mo.id+"','m_bankPic','m_uploadbtnBank',this,'"+m_bankPicPath+"','m_fileInputBankPath','m_fileInputBank');\">x</span>" +
							"<img src=\""+m_bankPicPath+"?m="+Math.random()+"\"/></p>");					
				}
				e.preventDefault();
				$('#modifyUserDiv').modal('show');
			}
		}
	});	
});
//邮箱格式验证
function checkEmail(str){
	var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if(str == null || str == "" || reg.test(str))
		return true;
	else
		return false;
}
//添加取消清空
$('.addusercancel').click(function(e){
	$("#add_formtip").html('');
	$("#a_idPic").html('');
	$("#a_bankPic").html('');
	$("#selectrole").val('');
	$("#selectusertype").val('');
	$("#selectusertype").html('<option value=\"\" selected=\"selected\">--请选择--</option>');
	$("#a_logincode").val('');
	$("#a_username").val('');
	$("#selectcardtype").val('');
	$("#a_idcard").val('');
	$("#a_mobile").val('');
	$("#a_email").val('');
	$("#a_postCode").val('');
	$("#a_bankname").val('');
	$("#a_bankaccount").val('');
	$("#a_accountholder").val('');
	$("#a_useraddress").val('');
});
//查看取消清空
$(".viewusercancel").click(function(e){
	$("#v_bankPic").html('');
	$("#v_idPic").html('');
	$("#v_isStart").html('');
});
//修改取消清空
$(".modifyusercancel").click(function(e){
	$("#m_bankPic").html('');
	$("#m_idPic").html('');
	$("#m_isStart").html('');
	$("#modify_formtip").html('');
});
//添加动态加载角色
$("#selectrole").change(function(){
	$("#selectUserType").empty();
	$("#selectUserType").append("<option value=\"\" selected=\"selected\">--请选择--</option>");
	var sec_rol = $("#selectrole").val();
	if (sec_rol==2) {
		$.post("/SLSailSystem/backend/loadUserTypeList.html",{"s_roleId":sec_rol},function(result){
			if (result!=null && result!="") {
				for (var i = 0; i < result.length; i++) {
					$("#selectUserType").append("<option value=\""+result[i].valueId+"\">"+result[i].valueName+"</option>");
				}
			}else {
				alert("用户类型加载失败！");
			}
			
		},'json');
	}	
});

//修改动态加载角色
$("#m_roleid").change(function(){
	$("#m_selectUserType").empty();
	$("#m_selectUserType").append("<option value=\"\" selected=\"selected\">--请选择--</option>");
	var m_rol = $("#m_roleid").val();
	if (m_rol==2) {
		$.post("/SLSailSystem/backend/loadUserTypeList.html",{"s_roleId":m_rol},function(result){
			if (result!=null && result!="") {
				for (var i = 0; i < result.length; i++) {					
					$("#m_selectUserType").append("<option value=\""+result[i].valueId+"\">"+result[i].valueName+"</option>");
				}
			}else {
				alert("用户类型加载失败！");
			}
			
		},'json');
	}	
});


//添加判断是否重名
$("#a_logincode").blur(function(){
	var logincode =  $("#a_logincode").val();
	if (logincode!="") {
		//异步同名判断
		$.post("/SLSailSystem/backend/logincodeisexit.html",{'loginCode':logincode,"id":'-1'},function(result){
			if (result=='repete') {
				$("#add_formtip").css("color","red");
				$("#add_formtip").html("<li>该用户名已存在！</li>");
				$("#add_formtip").attr("namekey",1);
			}else if (result=="faild") {
				alert("操作超时。");
			}else if(result='only'){
				$("#add_formtip").css("color","green");
				$("#add_formtip").html("<li>该用户名可以正常使用</li>");
				$("#add_formtip").attr("namekey",0);
			}
			
		},'html');
	}	
});
//修改判断是否重名
$("#m_logincode").blur(function(){
	var logincode =  $("#m_logincode").val();
	if (logincode!="") {
		//异步同名判断
		$.post("/SLSailSystem/backend/logincodeisexit.html",{'loginCode':logincode,"id":$("#m_id").val()},function(result){
			if (result=='repete') {
				$("#modify_formtip").css("color","red");
				$("#modify_formtip").html("<li>该用户名已存在！</li>");
				$("#modify_formtip").attr("namekey",1);
			}else if (result=="faild") {
				alert("操作超时。");
			}else if(result='only'){
				$("#modify_formtip").css("color","green");
				$("#modify_formtip").html("<li>该用户名可以正常使用</li>");
				$("#modify_formtip").attr("namekey",0);
			}
			
		},'html');
	}	
});

//添加邮件验证
$("#a_email").blur(function(){
	var emailCheck = checkEmail($("#a_email").val());
	if (emailCheck == false) {
		$("#add_formtip").css("color","red");
		$("#add_formtip").html("<li>eamil格式不正确！</li>");
		$("#add_formtip").attr("emailkey",1);
	}else {		
		$("#add_formtip").html("");
		$("#add_formtip").attr("emailkey",0);
	}
	
});
//修改邮件验证
$("#m_email").blur(function(){
	var emailCheck = checkEmail($("#m_email").val());
	if (emailCheck == false) {
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").html("<li>eamil格式不正确！</li>");
		$("#modify_formtip").attr("emailkey",1);
	}else {		
		$("#modify_formtip").html("");
		$("#modify_formtip").attr("emailkey",0);
	}
	
});

//添加信息验证
function addUserFunction(){
	$("#add_formtip").html("");
	var valresult = true;
	if ($("#selectrole").val()==null || $.trim($("#selectrole").val())=="") {
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>角色不能为空！</li>");
		valresult = false;
	}
	
	if ($("#a_logincode").val()==null || $.trim($("#a_logincode").val())=="") {
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>用户名不能为空！</li>");
		valresult = false;
	}else {
		//重名不能提交
		if ($("#add_formtip").attr("namekey")==1) {
			$("#add_formtip").css("color","red");
			$("#add_formtip").append("<li>用户名已存在！</li>");
			valresult = false;
		}
	}
	//姓名必填验证
	if ($("#a_username").val()==null || $.trim($("#a_username").val())=="") {
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>角色不能为空！</li>");
		valresult = false;
	}
	//证件类型必填验证
	if ($("#selectcardtype").val()==null || $.trim($("#selectcardtype").val())=="") {
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>请选择证件类型！</li>");
		valresult = false;
	}
	//证件号码验证
	if ($("#a_idcard").val()==null || $.trim($("#a_idcard").val())=="" || $.trim($("#a_idcard").val()).length<6) {
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>请填写证件号码！</li>");
		valresult = false;
	}	
	//电话验证
	if ($("#a_mobile").val()==null || $.trim($("#a_mobile").val())=="" || $.trim($("#a_mobile").val()).length<5) {
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>请填写电话号码！</li>");
		valresult = false;
	}	
	
	//邮箱验证
	if ($("#a_email").val()==null || $.trim($("#a_email").val())=="" || $("#add_formtip").attr("emailkey")==1) {
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>email格式不正确！</li>");
		valresult = false;
	}
	
	//邮编验证
	if ($("#a_postcode").val()==null || $.trim($("#a_postcode").val())=="") {
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>邮编格式不正确！</li>");
		valresult = false;
	}
	//开户行名称非空验证
	if ($("#a_bankname").val()==null || $.trim($("#a_bankname").val())=="") {
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>email格式不正确！</li>");
		valresult = false;
	}
	//开户卡号验证
	if ($("#a_bankcode").val()==null || $.trim($("#a_bankcode").val())=="" || $.trim($("#a_bankcode").val()).length<6) {
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>您输入的卡号不正确！</li>");
		valresult = false;
	}
	//开户人验证
	if ($("#a_bankpepo").val()==null || $.trim($("#a_bankpepo").val())=="") {
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>请输入开户人！</li>");
		valresult = false;
	}
	
	
	if(valresult==true){
		alert("添加成功！");		
	}
	return valresult;	
}

//修改信息验证
function modifyUserFunction(){
	$("#modify_formtip").html("");
	var valresult = true;
	if ($("#m_rolename").val()==null || $.trim($("#m_rolename").val())=="") {
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>角色不能为空！</li>");
		valresult = false;
	}
	
	if ($("#m_logincode").val()==null || $.trim($("#m_logincode").val())=="") {
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>用户名不能为空！</li>");
		valresult = false;
	}else {
		//重名不能提交
		if ($("#modify_formtip").attr("namekey")==1) {
			$("#modify_formtip").css("color","red");
			$("#modify_formtip").append("<li>用户名已存在！</li>");
			valresult = false;
		}
	}
	//姓名必填验证
	if ($("#m_username").val()==null || $.trim($("#m_username").val())=="") {
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>角色不能为空！</li>");
		valresult = false;
	}
	//证件类型必填验证
	if ($("#m_selectcardtype").val()==null || $.trim($("#m_selectcardtype").val())=="") {
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>请选择证件类型！</li>");
		valresult = false;
	}
	//证件号码验证
	if ($("#m_idcard").val()==null || $.trim($("#m_idcard").val())=="" || $.trim($("#m_idcard").val()).length<6) {
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>请填写证件号码！</li>");
		valresult = false;
	}	
	//电话验证
	if ($("#m_mobile").val()==null || $.trim($("#m_mobile").val())=="" || $.trim($("#m_mobile").val()).length<5) {
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>请填写电话号码！</li>");
		valresult = false;
	}	
	
	//邮箱验证
	if ($("#m_email").val()==null || $.trim($("#m_email").val())=="" || $("#modify_formtip").attr("emailkey")==1) {
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>email格式不正确！</li>");
		valresult = false;
	}
	
	//邮编验证
	if ($("#m_postcode").val()==null || $.trim($("#m_postcode").val())=="") {
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>email格式不正确！</li>");
		valresult = false;
	}
	//开户行名称非空验证
	if ($("#m_bankname").val()==null || $.trim($("#m_bankname").val())=="") {
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>email格式不正确！</li>");
		valresult = false;
	}
	//开户卡号验证
	if ($("#m_bankcode").val()==null || $.trim($("#m_bankcode").val())=="" || $.trim($("#m_bankcode").val()).length<6) {
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>您输入的卡号不正确！</li>");
		valresult = false;
	}
	//开户人验证
	if ($("#m_bankpepo").val()==null || $.trim($("#m_bankpepo").val())=="") {
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>请输入开户人！</li>");
		valresult = false;
	}
	
	
	if(valresult==true){
		alert("修改成功！");		
	}
	return valresult;	
}
//填充几项始值
$("#selectrole").change(function(){
	$("#selectrolename").val($("#selectrole").find("option:selected").text());	
});
$("#selectUserType").change(function(){
	$("#selectUserTypename").val($("#selectUserType").find("option:selected").text());	
});
$("#selectcardtype").change(function(){
	$("#selectcardtypename").val($("#selectcardtype").find("option:selected").text());	
});
//修改填充值
$("#m_roleid").change(function(){
	$("#m_rolename").val($("#m_roleid").find("option:selected").text());	
});
$("#m_selectUserType").change(function(){
	$("#m_selectUserTypename").val($("#m_selectUserType").find("option:selected").text());	
});
$("#m_selectcardtype").change(function(){
	$("#m_selectcardtypename").val($("#m_selectcardtype").find("option:selected").text());	
});

//添加身份证图片上传
$("#a_uploadbtnID").click(function(){
	TajaxFileUpload('0','a_fileInputID','a_uploadbtnID','a_idPic','a_fileInputIDPath');
});
//添加银行卡图片上传
$("#a_uploadbtnBank").click(function(){
	TajaxFileUpload('0','a_fileInputBank','a_uploadbtnBank','a_bankPic','a_fileInputBankPath');
});
//修改身份证图片上传
$("#m_uploadbtnID").click(function(){
	TajaxFileUpload($("#m_id").val(),'m_fileInputID','m_uploadbtnID','m_idPic','m_fileInputIDPath');
});
//修改银行卡图片上传
$("#m_uploadbtnBank").click(function(){
	TajaxFileUpload($("#m_id").val(),'m_fileInputBank','m_uploadbtnBank','m_bankPic','m_fileInputBankPath');
});

//公用方法（flag=标记,t1=文件名id,t2=按钮id,t3=图片框id,t4=隐藏id）
function TajaxFileUpload(flag,t1,t2,t3,t4)
{   
	if($("#"+t1+"").val() == '' || $("#"+t1+"").val() == null){
		alert("请选择上传文件！");
	}else{
		$.ajaxFileUpload
	    ({ 
	           url:'/SLSailSystem/backend/upload.html', //处理上传文件的服务端
	           secureuri:false,
	           fileElementId:t1,
	           dataType: 'json',
	           success: function(data) { 
	        	   data = data.replace(/(^\s*)|(\s*$)/g, "");
	        	   if(data == "1"){
	        		   alert("上传图片大小不得超过50K！");
	        		   $("#uniform-"+t1+" span:first").html('无文件');
	        		   $("input[name='"+t1+"']").change(function(){
	        			   var fn = $("input[name='"+t1+"']").val(); 
	        			   if($.browser.msie){
	        				   fn = fn.substring(fn.lastIndexOf("\\")+1);
	        			   }
	        			   $("#uniform-"+t1+" span:first").html(fn);
	        		   });
	        	   }else if(data == "2"){
	        		   alert("上传图片格式不正确！");
	        		   $("#uniform-"+t1+" span:first").html('无文件');
	        		   $("input[name='"+t1+"']").change(function(){
	        			   var fn = $("input[name='"+t1+"']").val(); 
	        			   if($.browser.msie){//防ie全路径名显示
	        				   fn = fn.substring(fn.lastIndexOf("\\")+1);
	        			   }
	        			   $("#uniform-"+t1+" span:first").html(fn);
	        		   });
	        	   } else{
	        		   //添加随机数解决浏览器缓存，防止只显示一张图
	        		   $("#"+t3+"").append("<p><span onclick=\"delpic('"+flag+"','"+t3+"','"+t2+"',this,'"+data+"','"+t4+"','"+t1+"');\">x</span><img src=\""+data+"?m="+Math.random()+"\" /></p>");
	        		   $("#"+t2+"").hide();
	        		   $("#"+t4+"").val(data);
	        		   $("input[name='"+t1+"']").change(function(){
	        			   var fn = $("input[name='"+t1+"']").val(); 
	        			   if($.browser.msie){
	        				   fn = fn.substring(fn.lastIndexOf("\\")+1);
	        			   }
	        			   $("#uniform-"+t1+" span:first").html(fn);
	        		   });
	        	   }
	           },  
	           error: function() {  
	              alert("上传失败！");
	           } 
	        });
	}
}
//图片删除
function delpic(id,closeSpan,uploadBtn,obj,picpath,picText,fileinputid){
	//delete
	$.post("/SLSailSystem/backend/delpic.html",{'id':id,'picpath':picpath},function(result){
		if("success" == result){
			alert("删除成功！");
			$('#'+picText).val('');
   			$("#uniform-"+fileinputid+" span:first").html('无文件');
			document.getElementById(closeSpan).removeChild(obj.parentElement);
			$('#'+uploadBtn).show();
		}else
			alert("删除失败！");
	},'html');
	
}

//删除用户
$(".deleteuser").click(function(){
	var del = $(this);
	var del_id = del.attr("id");
	var del_usertype = del.attr("usertype");
	var del_usertypename = del.attr("usertypename");
	var del_logincode = del.attr("logincode");
	var del_idcardpicpath = del.attr("idcardpicpath");
	var del_bankpicpath = del.attr("bankpicpath");
	if (confirm("您确定要删除用户【"+del_logincode+"】吗？")) {
		$.post("/SLSailSystem/backend/deluser.html",{'id':del_id,'userType':del_usertype,'userTypeName':del_usertypename,'idCardPicPath':del_idcardpicpath,'bankPicPath':del_bankpicpath,},function(result){
			if ("success"==result) {
				alert("删除成功！");
				window.location.href=("/SLSailSystem/backend/userlist.html");
			}else if ("noallow"==result) {
				alert("该类型的用户不能删除！");
			}else {
				alert("删除失败！");
			}
		},'html');
	}
	
});