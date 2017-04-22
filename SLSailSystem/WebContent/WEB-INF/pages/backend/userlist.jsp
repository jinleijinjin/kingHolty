<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="/WEB-INF/pages/common/head.jsp"%>	

<div id="content" class="span10">
	<div>
	<ul class="breadcrumb">
		<li>
			<a href="#">后台管理</a> <span class="divider">/</span>
		</li>
		<li>
			<a href="/SLSailSystem/backend/userlist.html">用户管理</a>
		</li>
	</ul>
	</div>
<!-- git测试 -->


		<div class="box-header well" data-original-title>
			<h2><i class="icon-user"></i> 用户管理</h2>
			<div class="box-icon">
				<span class="icon32 icon-color icon-add custom-setting adduser"></span>
			</div>
		</div>
		<div class="box-content">
		<form action="/SLSailSystem/backend/userlist.html" method="post">
			<div class="searcharea">
				用户名称：<input type="text" name="s_loginCode" value="${s_loginCode }">
			推荐人：<input type="text" name="s_referCode" value="${s_referCode }">
			
				角色：<select name="s_roleId" style="width:100px">
						<option value="" selected="selected">--请选择--</option>
						<c:forEach items="${roleList}" var="role">
					 			<option <c:if test="${s_roleId == role.id}">selected = "selected"</c:if>
					 			value="${role.id}">${role.roleName}</option>
					 	</c:forEach>
					</select>
			是否启用：<select name="s_isStart" style="width:100px">
						<option value="" selected="selected">--请选择--</option>
						<c:if test="${s_isStart==1 }">
							<option value="1" selected="selected">启用</option>
							<option value="2" >不启用</option>
						</c:if>
						<c:if test="${s_isStart==2 }">
							<option value="1" >启用</option>
							<option value="2" selected="selected">不启用</option>
						</c:if>
						<c:if test="${s_isStart==null || s_isStart==''}">
							<option value="1" >启用</option>
							<option value="2" >不启用</option>
						</c:if>
						</select>
				<button class="btn btn-primary"><i class="icon-search icon-white"></i>查询</button>
			</div>
		
		</form>
		<table class="table table-striped table-bordered bootstrap-datatable datatable">
		 <thead>
				  <tr>
					  <th>用户名</th>
					  <th>角色</th>
					  <th>会员类型</th>
					  <th>推荐人</th>
					  <th>启用状态（启用/禁用）</th>
					  <th>注册时间</th>
					  <th>操作</th>
				  </tr>
			  </thead>  
			  
			  <tbody>
			  
			  
			  <c:if test="${page.items !=null}">
			  <c:forEach items="${page.items}" var="user">
			  	<tr>
			  		<td class="center">${user.loginCode}</td>
					<td class="center">${user.roleName}</td>
					<td class="center">${user.userTypeName}</td>
					<td class="center">${user.referCode}</td>
			  		<td class="center">
						<c:if test="${user.isStart==2 }"><input type="checkbox" disabled="disabled"></c:if>
						<c:if test="${user.isStart==1 }"><input type="checkbox" disabled="disabled" checked="true"></c:if>
					</td>
			  		<td class="center"><fmt:formatDate value="${user.createTime }" pattern="yyyy-MM-dd"/></td>
			  		<td class="center">
						<a class="btn btn-success viewuser" href="#" id="${user.id }" >
							<i class="icon-zoom-in icon-white"></i>  
							查看                                            
						</a>
						<a class="btn btn-info modifyuser" href="#" id="${user.id }">
							<i class="icon-edit icon-white"></i>  
							修改                                            
						</a>
						<a class="btn btn-danger deleteuser" href="#" usertype="${user.userType}" usertypename="${user.userTypeName}" logincode="${user.loginCode}" id="${user.id}" idcardpicpath="${user.idCardPicPath}" bankpicpath="${user.bankPicPath}">
							<i class="icon-trash icon-white" ></i> 
							删除
						</a>
					</td>
			  	</tr>
			  </c:forEach>
			  </c:if>
			  
		</tbody>
		</table>
				  <div class="pagination pagination-centered">
				  	<ul>
				  	<c:choose>
				  		<c:when test="${page.page==1 }">
				  			<li class="active"><a href="javascript:void()" title="首页">首页</a></li>
				  		</c:when>
				  		<c:otherwise>
				  			<li><a href="/SLSailSystem/backend/userlist.html?currentPage=1&s_loginCode=${s_loginCode }&s_referCode=${s_referCode}&s_isStart=${s_isStart}&s_roleid=${s_roleid} " title="首页">首页</a></li>
				  		</c:otherwise>				  	
				  	</c:choose> 
				  	<c:if test="${page.prevPages!=null}">
							<c:forEach items="${page.prevPages}" var="num">
								<li><a href="/SLSailSystem/backend/userlist.html?currentpage=${num}&s_loginCode=${s_loginCode}&s_referCode=${s_referCode}&s_roleId=${s_roleId}&s_isStart=${s_isStart}"
									class="number" title="${num}">${num}</a></li>
							</c:forEach>
						</c:if>
				   	<li class="active"><a href="#" title="${page.page }">${page.page }</a></li> 
				  	<c:if test="${page.nextPages!=null}">
				  		<c:forEach items="${page.nextPages}" var="num">
				  			<li><a href="/SLSailSystem/backend/userlist.html?currentPage=${num}&s_loginCode=${s_loginCode }&s_referCode=${s_referCode}&s_isStart=${s_isStart}&s_roleid=${s_roleid} " title="${num }">${num }</a></li>
				  		</c:forEach>
				  	</c:if>	
				  	
				  		
				  	
				  	<c:if test="${page.pageCount !=null}">
				  		<c:choose>
				  		<c:when test="${page.page==page.pageCount }">
				  			<li class="active"><a href="javascript:void()" title="尾页">尾页</a></li>
				  		</c:when>
				  		<c:otherwise>
				  			<li><a href="/SLSailSystem/backend/userlist.html?currentPage=${page.pageCount} &s_loginCode=${s_loginCode }&s_referCode=${s_referCode}&s_isStart=${s_isStart}&s_roleid=${s_roleid} " title="尾页">尾页</a></li>
				  		</c:otherwise>				  	
				  	</c:choose> 
				  	</c:if>
				  	<c:if test="${page.pageCount ==null}">
				  		<li class="active"><a href="javascript:void()" title="尾页">尾页</a></li>
				  	</c:if>
				  	</ul>				  
</div>   
</div>
</div>

<div class="modal hide fade" id="addUserDiv">

<form action="/SLSailSystem/backend/adduser.html" method="post" enctype="muiltpart/form-data" onsubmit="return addUserFunction();">
	<div class="modal-header">
			<button type="button" class="close addusercancel" data-dismiss="modal">×</button>
			<h3>添加用户信息</h3>
	</div>
	<div class="modal-body">
		<ul id="add_formtip"></ul>
		<ul class="topul">
			<li>
				<label >角色</label>
					<input id="selectrolename" type="hidden" value="" name="roleName">
				<select id="selectrole" style="width:100px" name="roleId">
					<option value="" selected="selected">--请选择--</option>
					<c:if test="${roleList!=null }">
						<c:forEach items="${roleList }" var="role">
							<option value="${role.id }" >${role.roleName }</option>
						</c:forEach>
					</c:if>
				</select>
				<span style="color:red ;font-weight:blod;">*</span>
			</li>
			
			<li>
				<label >会员类型</label>
				<input id="selectUserTypename" type="hidden" value="" name="userTypeName">
				<select id="selectUserType" style="width:100px" name="userType">
					<option value="" selected="selected">--请选择--</option>
				<!-- 动态填充会员类型 -->
				</select>
			</li>
			
			<li>
				<label >用户名</label>
				<input id="a_logincode" type="text" value="" name="loginCode" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')">				
				<span style="color:red ;font-weight:blod;">*</span>
			</li>
			
			<li>
				<label >姓名</label>
				<input id="a_username" type="text" value="" name="userName" >				
				<span style="color:red ;font-weight:blod;">*</span>
			</li>
			<li>
				  <label>性别：</label>
	 			  <select id="sex" name="sex" style="width:100px;">
	 			    <option value="" selected="selected">--请选择--</option>
	 				<option value="男">男</option>
	 				<option value="女">女</option>
	 			  </select> 
			</li> 
			
			
			<li>
				<label >证件类型</label>
				<input id="selectcardtypename" type="hidden" value="" name="cardTypeName">
				<select id="selectcardtype" style="width:100px" name="cardType">
					<option value="" selected="selected">--请选择--</option>
					<c:if test="${cardTypeList!=null }">
						<c:forEach items="${cardTypeList }" var="cardType">
							<option value="${cardType.valueId }" >${cardType.valueName }</option>
						</c:forEach>
					</c:if>
				</select>
				<span style="color:red ;font-weight:blod;">*</span>
			</li>
			
			<li>
				<label >证件号码</label>
				<input id="a_idcard" type="text" value="" name="idCard" >				
				<span style="color:red ;font-weight:blod;">*</span>
			</li>
			
			<li>
				  <label>生日：</label>
				    <input class="Wdate" id="v_birthday" size="15" name="birthday" readonly="readonly"  type="text" onClick="WdatePicker();"/>
				   <!-- <input type="text" id="v_birthday" class="laydate-icon" name="birthday" value="" readonly="readonly"/> -->
					
			</li>
			<li>
				  <label>收货国家：</label>
				   <input type="text" name="country" value=""/>
			</li>
			<li>
				  <label>联系电话：</label><input type="text" id="a_mobile" name="mobile" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
				  <span style="color:red;font-weight: bold;">*</span>
			</li>
			<li>
				  <label>email：</label>
				   <input type="text" id="a_email" name="email" value="" />
				    <span style="color:red;font-weight: bold;">*</span>
			</li>
			<li>
				  <label>邮政编码：</label>
				   <input type="text" id="a_postcode" name="postCode" value=""/>
			</li>
			<li>
				  <label>开户行：</label>
				   <input type="text" id="a_bankname" name="bankName" value=""/>
				   <span style="color:red ;font-weight:blod;">*</span>
			</li>
			<li>
				  <label>开户卡号：</label>
				   <input type="text" id="a_bankcode" name="bankAccount" value=""/>
				   <span style="color:red ;font-weight:blod;">*</span>
			</li>
			<li>
				  <label>开户人：</label>
				   <input type="text" id="a_bankpepo" name="accountHolder" value=""/>
				   <span style="color:red ;font-weight:blod;">*</span>
			</li>
			
			<li>
				<label >推荐人</label>
				<input type="text" name="referCode" size="15" readonly="readonly" value="${user.loginCode }"/>
			</li>
			<li>
				  <label>注册时间：</label>
				   <input type="text" id="a_cdate" name="createTime" value="" readonly="readonly"/>
			</li>
			
			<li>
				  <label>是否启用：</label>
	 			  <select id="a_isStart" name="isStart" style="width:100px;">
	 				<option value="1" selected="selected">启用</option>
	 				<option value="2">不启用</option>
	 			  </select> <span style="color:red;font-weight: bold;">*</span>
			</li>
			<li class="lastli">
				  <label>收货地址：</label>
				  <textarea name="userAddress"></textarea>
			</li>
			
			
		</ul> 
		<div class="clear"> 
			<ul class="downul">
					<li>
					<label>上传身份证图片：</label>
						<input type="hidden" id="a_fileInputIDPath" name="idCardPicPath" value=""/>
						<input id="a_fileInputID" name="a_fileInputID" type="file"/>
						<input type="button" id="a_uploadbtnID" value="上传"/>
						<p><span style="color:red;font-weight: bold;">*注：1、正反面.2、大小不得超过50k.3、图片格式：jpg、png、jpeg、pneg</span></p>
						<div id="a_idPic"></div>
					 </li>
				</ul>
				<ul class="downul">
					<li>
					<label>上传银行卡图片：</label>
						<input type="hidden" id="a_fileInputBankPath" name="bankPicPath" value=""/>
						<input id="a_fileInputBank" name="a_fileInputBank" type="file"/>
						<input type="button" id="a_uploadbtnBank" value="上传"/>
						<p><span style="color:red;font-weight: bold;">*注：1、大小不得超过50k.2、图片格式：jpg、png、jpeg、pneg</span></p>
						<div id="a_bankPic"></div>
					 </li>
				</ul>
		</div>
		
			
	</div>
	<div class="modal-footer">
		<a href="#" class="btn addusercancel" data-dismiss="modal">取消</a>
		<button type="submit" class="btn btn-primary" >保存</button>
</div>
</form>
</div>
<!-- 用户查看开始 -->
<!-- 查看用户信息模式窗口 -->
<div class="modal hide fade" id="viewUserDiv">


	<div class="modal-header">
			<button type="button" class="close viewusercancel" data-dismiss="modal">×</button>
			<h3>查看用户信息</h3>
	</div>
	<div class="modal-body">
		<ul id="add_formtip"></ul>
		<ul class="topul">
			<li>
				<label >角色</label>
				<input id="v_id" type="hidden" value="" >
					<input id="v_rolename" type="text" value="" >				
				<span style="color:red ;font-weight:blod;">*</span>
			</li>
			
			<li>
				<label >会员类型</label>
				<input id="v_usertypename" type="text" value="" >
			</li>
			
			<li>
				<label >用户名</label>
				<input id="v_logincode" type="text" value="" >
				<span style="color:red ;font-weight:blod;">*</span>
			</li>
			
			<li>
				<label >姓名</label>
				<input id="v_username" type="text" value="" >
				<span style="color:red ;font-weight:blod;">*</span>
			</li>
			<li>
				  <label>性别：</label>
	 			  <input id="v_sex" type="text" value="" >
			</li> 
						
			<li>
				<label >证件类型</label>
				 <input id="v_cardtypename" type="text" value="" >
				<span style="color:red ;font-weight:blod;">*</span>
			</li>
			
			<li>
				<label >证件号码</label>
				<input id="v_idcard" type="text" value=""  >				
				<span style="color:red ;font-weight:blod;">*</span>
			</li>
			
			<li>
				  <label>生日：</label>
				   <input type="text" id="dasdasd" name="birthday" value="" readonly="readonly"/>
			</li> 
			
			
			<li>
				  <label>收货国家：</label>
				   <input type="text" id="v_country" value=""/>
			</li>
			<li>
				  <label>联系电话：</label>
				  <input type="text" id="v_mobile"  value=""/>
				  <span style="color:red;font-weight: bold;">*</span>
			</li>
			<li>
				  <label>email：</label>
				   <input type="text" id="v_email" value="" />
				    <span style="color:red;font-weight: bold;">*</span>
			</li>
			<li>
				  <label>邮政编码：</label>
				   <input type="text" id="v_postcode" name="postCode" value=""/>
			</li>
			<li>
				  <label>开户行：</label>
				   <input type="text" id="v_bankname" name="bankName" value=""/>
				   <span style="color:red ;font-weight:blod;">*</span>
			</li>
			<li>
				  <label>开户卡号：</label>
				   <input type="text" id="v_bankcode" name="bankAccount" value=""/>
				   <span style="color:red ;font-weight:blod;">*</span>
			</li>
			<li>
				  <label>开户人：</label>
				   <input type="text" id="v_bankpepo" name="accountHolder" value=""/>
				   <span style="color:red ;font-weight:blod;">*</span>
			</li>
			
			<li>
				<label >推荐人</label>
				<input type="text" name="v_referCode" size="15" readonly="readonly" value=""/>
			</li>
			 
			<li>
				  <label>注册时间：</label>
				   <input type="text" id="v_cdate" name="createTime" value="" readonly="readonly"/>
			</li> 
			
			
			
			<li>
				  <label>是否启用：</label>
				  <select id="v_isStart" style="width:100px;" readonly="readonly">
	 			  </select> <span style="color:red;font-weight: bold;">*</span>
			</li>
			<li class="lastli">
				  <label>收货地址：</label>
				  <textarea name="userAddress" id="v_userAddress"></textarea>
			</li>
			
			
		</ul> 
		<div class="clear"> 
			<ul class="downul">
					<li>
					<label>身份证图片：</label>
						<input type="hidden" id="a_fileInputIDPath" name="idCardPicPath" value=""/>
						<div id="v_idPic"></div>
					 </li>
			</ul>
			<ul class="downul">
					<li>
					<label>银行卡图片：</label>
						<input type="hidden" id="a_fileInputBankPath" name="bankPicPath" value=""/>
						<div id="v_bankPic"></div>
					 </li>
			</ul>
		</div>
		
			
	</div>
	<div class="modal-footer">
		<a href="#" class="btn viewusercancel" data-dismiss="modal">关闭</a>		
</div>
</div>		
<!-- 用户查看结束 -->

<!-- 用户修改开始 -->
<div class="modal hide fade" id="modifyUserDiv">


	<form action="/SLSailSystem/backend/modifyuser.html" method="post" enctype="muiltpart/form-data" onsubmit="return modifyUserFunction();">
	<div class="modal-header">
			<button type="button" class="close modifyusercancel" data-dismiss="modal">×</button>
			<h3>修改用户信息</h3>
	</div>
	<div class="modal-body">
		<!-- 修改信息错误提示框 -->
		<ul id="modify_formtip"></ul>
		<ul class="topul">
		
			<li>
				<label >角色</label>
				<input id="m_id" type="hidden" value="" name="id">
				<input id="m_rolename" type="hidden" value="" name="roleName">
				<select id="m_roleid" style="width:100px" name="roleId">
					<!-- 动态显示角色信息 -->
				</select>
				<span style="color:red ;font-weight:blod;">*</span>
			</li>
			
			<li>
				<label >会员类型</label>
				<input id="m_selectUserTypename" type="hidden" value="" name="userTypeName">
				<select id="m_selectUserType" style="width:100px" name="userType">
				<!-- 动态填充会员类型 -->
				</select>
			</li>
			
			<li>
				<label >用户名</label>
				<input id="m_logincode" type="text" value="" name="loginCode" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')">				
				<span style="color:red ;font-weight:blod;">*</span>
			</li>
			
			<li>
				<label >姓名</label>
				<input id="m_username" type="text" value="" name="userName" >				
				<span style="color:red ;font-weight:blod;">*</span>
			</li>
			<li>
				  <label>性别：</label>
	 			  <select id="m_sex" name="sex" style="width:100px;">
	 			   
	 			  </select> 
			</li> 
			
			
			<li>
				<label >证件类型</label>
				<input id="m_selectcardtypename" type="hidden" value="" name="cardTypeName">
				<select id="m_selectcardtype" style="width:100px" name="cardType">
					
				</select>
				<span style="color:red ;font-weight:blod;">*</span>
			</li>
			
			<li>
				<label >证件号码</label>
				<input id="m_idcard" type="text" value="" name="idCard" >				
				<span style="color:red ;font-weight:blod;">*</span>
			</li>
			
			<li>
				  <label>生日：</label>
				    <input class="Wdate" id="m_birthday" size="15" name="birthday" readonly="readonly"  type="text" onClick="WdatePicker();"/>
				   <!-- <input type="text" id="v_birthday" class="laydate-icon" name="birthday" value="" readonly="readonly"/> -->
					
			</li>
			<li>
				  <label>收货国家：</label>
				   <input id="m_country" type="text" name="country" value=""/>
			</li>
			<li>
				  <label>联系电话：</label><input type="text" id="m_mobile" name="mobile" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
				  <span style="color:red;font-weight: bold;">*</span>
			</li>
			<li>
				  <label>email：</label>
				   <input type="text" id="m_email" name="email" value="" />
				    <span style="color:red;font-weight: bold;">*</span>
			</li>
			<li>
				  <label>邮政编码：</label>
				   <input type="text" id="m_postcode" name="postCode" value=""/>
			</li>
			<li>
				  <label>开户行：</label>
				   <input type="text" id="m_bankname" name="bankName" value=""/>
				   <span style="color:red ;font-weight:blod;">*</span>
			</li>
			<li>
				  <label>开户卡号：</label>
				   <input type="text" id="m_bankcode" name="bankAccount" value=""/>
				   <span style="color:red ;font-weight:blod;">*</span>
			</li>
			<li>
				  <label>开户人：</label>
				   <input type="text" id="m_bankpepo" name="accountHolder" value=""/>
				   <span style="color:red ;font-weight:blod;">*</span>
			</li>
			
			<li>
				<label >推荐人</label>
				<input type="text" id="m_refercode" name="referCode" size="15" readonly="readonly" value=""/>
			</li>
			<li>
				  <label>注册时间：</label>
				   <input type="text" id="m_cdate" name="createTime" value="" readonly="readonly"/>
			</li>
			
			<li>
				  <label>是否启用：</label>
	 			  <select id="m_isStart" name="isStart" style="width:100px;">
	 				
	 			  </select> 
	 			  <span style="color:red;font-weight: bold;">*</span>
			</li>
			<li class="lastli">
				  <label>收货地址：</label>
				  <textarea id="m_userAddress" name="userAddress"></textarea>
			</li>
			
			
		</ul> 
		<div class="clear"> 
			<ul class="downul">
					<li>
					<label>身份证图片：</label>
						<input type="hidden" id="m_fileInputIDPath" name="idCardPicPath" value=""/>
						<input id="m_fileInputID" name="m_fileInputID" type="file"/>
						<input type="button" id="m_uploadbtnID" value="上传" style="display: none;"/>
						<p><span style="color:red;font-weight: bold;">*注：1、正反面.2、大小不得超过50k.3、图片格式：jpg、png、jpeg、pneg</span></p>
						<div id="m_idPic"></div>
					 </li>
				</ul>
				<ul class="downul">
					<li>
					<label>银行卡图片：</label>
						<input type="hidden" id="m_fileInputBankPath" name="bankPicPath" value=""/>
						<input id="m_fileInputBank" name="m_fileInputBank" type="file"/>
						<input type="button" id="m_uploadbtnBank" value="上传" style="display: none;"/>
						<p><span style="color:red;font-weight: bold;">*注：1、大小不得超过50k.2、图片格式：jpg、png、jpeg、pneg</span></p>
						<div id="m_bankPic"></div>
					 </li>
				</ul>
		</div>
		
			
	</div>
	<div class="modal-footer">
		<a href="#" class="btn modifyusercancel" data-dismiss="modal">取消</a>
		<button type="submit" class="btn btn-primary" >保存</button>
</div>
</form>

</div>
<!-- 用户修改结束 -->

<%@include file="/WEB-INF/pages/common/foot.jsp"%>
<script type="text/javascript">
var cartTypeListJson =[ <c:forEach items="${cardTypeList }" var="cardType">
			{"valueId":"${cardType.valueId }","valueName":"${cardType.valueName }"},
			</c:forEach>
			{"valueId":"over","valueName":"over"}];
var roleListJson=[ <c:forEach items="${roleList}" var="role"> 
		{"id":"${role.id}","roleName":"${role.roleName}"},
		</c:forEach>{"id":"over","roleName":"over"}];
</script>


<script src="/SLSailSystem/statics/localjs/userlist.js?Math.random()"></script>
<!-- <script src="/SLSailSystem/statics/laydate/laydate.js" type="text/javascript"></script>
<script>
laydate({
  elem: '#v_birthday', 
  event: 'focus' 
});
</script> -->

</html>