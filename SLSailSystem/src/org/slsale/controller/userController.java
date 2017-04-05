package org.slsale.controller;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.slsale.common.Constants;
import org.slsale.common.JsonDateValueProcessor;
import org.slsale.common.PageSupport;
import org.slsale.common.SQLTools;
import org.slsale.pojo.DataDictionary;
import org.slsale.pojo.Role;
import org.slsale.pojo.User;
import org.slsale.service.dataDictionary.DataDictionaryService;
import org.slsale.service.role.RoleService;
import org.slsale.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class userController extends BaseController {

	Logger logger = Logger.getLogger(BaseController.class);

	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService ;
	@Resource
	private DataDictionaryService dataDictionaryService;

	@RequestMapping("/backend/modifyPwd.html")
	@ResponseBody
	public Object modifyPwd(@RequestParam String userJson) {
		User sessionUser = this.getCurrentUser();
		if (userJson == null || userJson.equals("")) {
			return "nodata";
		} else {
			JSONObject JSONObuser = JSONObject.fromObject(userJson);
			User user = (User) JSONObject.toBean(JSONObuser, User.class);
			user.setLoginCode(sessionUser.getLoginCode());
			user.setId(sessionUser.getId());
			try {
				if (userService.getLoginUser(user) != null) {
					user.setPassword(user.getPassword2());
					user.setPassword2(null);
					userService.modifyUser(user);
				} else {
					return "oldpwdwrong";
				}
			} catch (Exception e) {
				return "failed";
			}
		}
		return "success";
	}

	@RequestMapping("backend/userlist.html")
	public ModelAndView userlist(Model model, HttpSession httpSession,
			@RequestParam(value = "currentPage", required = false) Integer currentPage,
			@RequestParam(value = "s_loginCode", required = false) String s_loginCode,
			@RequestParam(value = "s_referCode", required = false) String s_referCode,
			@RequestParam(value = "s_roleId", required = false) String s_roleId,
			@RequestParam(value = "s_isStart", required = false) String s_isStart) {
		
		String s = s_loginCode+s_referCode+s_roleId+s_isStart;
		logger.debug(s+"=================================");
		Map<String, Object> baseMidel = (Map<String, Object>) httpSession.getAttribute(Constants.SESSION_BASE_MODEL);
		if (baseMidel == null) {
			return new ModelAndView("redirect:/index.jsp");
		} else {
			//获取RoleList andCardTypeList
			List<Role> roleList = null;
			DataDictionary dataDictionary = new DataDictionary();
			dataDictionary.setTypeCode("CARD_TYPE");
			List<DataDictionary> cardTypeList = null;
			try {
				roleList=roleService.getRoleList();
				cardTypeList = dataDictionaryService.getDataDictionarys(dataDictionary);
			} catch (Exception e) {
				e.printStackTrace();
			}
			User user = new User();
			if (s_loginCode!=null) {
				user.setLoginCode("%"+SQLTools.transfer(s_loginCode)+"%");				
			}
			if (s_referCode!=null) {				
				user.setReferCode("%"+SQLTools.transfer(s_referCode)+"%");	
			}
			
			if (!StringUtils.isNullOrEmpty(s_isStart)) {
				Integer i = Integer.valueOf(s_isStart);
				logger.debug(i+"s_isStarts_isStarts_isStarts_isStarts_isStarts_isStarts_isStarts_isStarts_isStarts_isStarts_isStarts_isStarts_isStart");
				user.setIsStart(i);
			}else {
				user.setIsStart(null);
			}
			logger.debug(s_roleId+"s_roleIds_roleIds_roleIds_roleIds_roleIds_roleIds_roleIds_roleIds_roleId");
			if (!StringUtils.isNullOrEmpty(s_roleId)) {				
				user.setRoleId(Integer.valueOf(s_roleId));
			}else {
				user.setRoleId(null);
			}
			PageSupport page = new PageSupport();
			try {
				page.setTotalCount(userService.count(user));
			} catch (Exception e) {
				
				e.printStackTrace();
				page.setTotalCount(0);
			}
			if (page.getTotalCount()>0) {
				if (currentPage!=null) {
					page.setPage(currentPage);
				}else {
					page.setPage(1);
				}
				if (page.getPage()<0) {
					page.setPage(1);
				}if (page.getPage()>page.getPageCount()) {
					page.setPage(page.getPageCount());
				}
				//查询 limit分页查询 （起始位置，偏移量）参数从0开始
				user.setStarNum((page.getPage()-1)*(page.getPageSize()));
				user.setPageSize(page.getPageSize());
				List<User> userList = null;
				try {
					logger.debug(user+"44444444444444444444444444");
					userList= userService.getUserList(user);
					
				} catch (Exception e) {
					// TODO: handle exception
					
					userList = null;
					if (page==null) {
						page = new PageSupport();
						page.setItems(null);
					}
					e.printStackTrace();
				}
				page.setItems(userList);
			}else {
				page.setItems(null);
			}
			model.addAttribute("cardTypeList",cardTypeList);
			model.addAttribute("page",page);
			model.addAttribute("roleList", roleList);
			model.addAttribute("mList",baseMidel.get("mList"));
			model.addAttribute("s_loginCode",s_loginCode);
			model.addAttribute("s_referCode",s_referCode);
			model.addAttribute("s_roleId",s_roleId);
			model.addAttribute("s_isStart",s_isStart);
			return new ModelAndView("backend/userlist");
		}

	}
	
	@RequestMapping(value="backend/loadUserTypeList.html",produces={"text/html;charset=UTF-8"})
	@ResponseBody
	public Object loadUserTypeList(@RequestParam(value="s_roleId",required = false) String s_roleId){
		String cjson = "";
		DataDictionary dataDictionary = new DataDictionary();
		dataDictionary.setTypeCode("USER_TYPE");
		try {
			List<DataDictionary> userTypeList = dataDictionaryService.getDataDictionarys(dataDictionary);
			cjson = JSONArray.fromObject(userTypeList).toString();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cjson;
	}
	
	
	//验证用户名是否存在
	
	@RequestMapping(value="backend/logincodeisexit.html",produces={"text/html;charset='UTF-8'"})
	@ResponseBody
	public String logincodeisexit(@RequestParam(value="loginCode",required=false) String loginCode,
			@RequestParam(value="id",required=false) String id){
		logger.debug("logincodeisexit loginCode===========>"+loginCode);
		logger.debug("logincodeisexit id===========>"+id);
		String result =  "faild";
		User user = new User();
		user.setLoginCode(loginCode);
		if (!id.equals("-1")) {
			//修改操作同名判断
			user.setId(Integer.valueOf(id));
		}
		try {
			int returnCodew = userService.loginCodeIsExit(user);
			if (returnCodew==0) {
				result = "only";
			}else {
				result = "repete";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return result;
		}
		
		return result;
	}
	
	//添加用户信息验证
	@RequestMapping(value="backend/adduser.html",method = RequestMethod.POST)
	public ModelAndView addUser(HttpSession session,@ModelAttribute("addUser") User addUser){
		if (session.getAttribute(Constants.SESSION_BASE_MODEL)==null) {
			return new ModelAndView("redirect:/index.jsp");
		}else {
			try {
				String idCard = addUser.getIdCard();
				String ps = idCard.substring(idCard.length()-6);
				addUser.setPassword(ps);
				addUser.setPassword2(ps);
				addUser.setCreateTime(new Date());
				addUser.setReferId(this.getCurrentUser().getId());
				addUser.setReferCode(this.getCurrentUser().getLoginCode());
				addUser.setLastUpdateTime(new Date());
				userService.addUser(addUser);
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return new ModelAndView("redirect:/backend/userlist.html");
		}		
	}
	
	@RequestMapping(value = "backend/upload.html",produces={"text/html;charset=UTF-8"})
	@ResponseBody
	public Object upload(@RequestParam(value = "a_fileInputID",required=false) MultipartFile cardFile,
						@RequestParam(value = "a_fileInputBank",required=false) MultipartFile bankFile,
						@RequestParam(value = "m_fileInputID",required=false) MultipartFile mcardFile,
						@RequestParam(value = "m_fileInputBank",required=false) MultipartFile mbankFile,						
						HttpServletRequest request){
		//根据服务器系统自动获取路径
		String path = request.getSession().getServletContext().getRealPath("/statics"+File.separator+"uploadfiles");
		DataDictionary dataDictionary = new DataDictionary();
		List<DataDictionary> list = null;
		dataDictionary.setTypeCode("PERSONALFILE_SIZE");
		try {
			list= dataDictionaryService.getDataDictionarys(dataDictionary);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int fileSize = 50000;
		if (list!=null) {
			if (list.size()==1) {
				fileSize = Integer.valueOf(list.get(0).getValueName());
			}
		}
		if (cardFile!=null) {
			String oldName = cardFile.getOriginalFilename();
			String perfix = FilenameUtils.getExtension(oldName);
			if (cardFile.getSize()>fileSize) {
				return "1";
			}else if (perfix.equalsIgnoreCase("jpg") || perfix.equalsIgnoreCase("jpeg") || 
					perfix.equalsIgnoreCase("png") ||perfix.equalsIgnoreCase("pneg")) {
				//文件重命名：系统毫秒+100w内的随机数
				String fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_IDCARD.jpg";
				File targetFile = new File(path,fileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				//保存操作
				try {
					cardFile.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String url = request.getContextPath()+"/statics/uploadfiles/"+fileName;
				//String url2 = targetFile.getPath();
				return url;
			}else {
				return "2";
			}
		}	
		if (bankFile!=null) {
			String oldName = bankFile.getOriginalFilename();
			String perfix = FilenameUtils.getExtension(oldName);
			if (bankFile.getSize()>fileSize) {
				return "1";
			}else if (perfix.equalsIgnoreCase("jpg") || perfix.equalsIgnoreCase("jpeg") || 
					perfix.equalsIgnoreCase("png") ||perfix.equalsIgnoreCase("pneg")) {
				//文件重命名：系统毫秒+100w内的随机数
				String fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_BANK.jpg";
				File targetFile = new File(path,fileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				//保存操作
				try {
					bankFile.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String url = request.getContextPath()+"/statics/uploadfiles/"+fileName;
				//String url2 = targetFile.getPath();
				return url;
			}else {
				return "2";
			}
		}		
		if (mcardFile!=null) {
			String oldName = mcardFile.getOriginalFilename();
			String perfix = FilenameUtils.getExtension(oldName);
			if (mcardFile.getSize()>fileSize) {
				return "1";
			}else if (perfix.equalsIgnoreCase("jpg") || perfix.equalsIgnoreCase("jpeg") || 
					perfix.equalsIgnoreCase("png") ||perfix.equalsIgnoreCase("pneg")) {
				//文件重命名：系统毫秒+100w内的随机数
				String fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_IDCARD.jpg";
				File targetFile = new File(path,fileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				//保存操作
				try {
					mcardFile.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String url = request.getContextPath()+"/statics/uploadfiles/"+fileName;
				//String url2 = targetFile.getPath();
				return url;
			}else {
				return "2";
			}
		}		
		if (mbankFile!=null) {
			String oldName = mbankFile.getOriginalFilename();
			String perfix = FilenameUtils.getExtension(oldName);
			if (mbankFile.getSize()>fileSize) {
				return "1";
			}else if (perfix.equalsIgnoreCase("jpg") || perfix.equalsIgnoreCase("jpeg") || 
					perfix.equalsIgnoreCase("png") ||perfix.equalsIgnoreCase("pneg")) {
				//文件重命名：系统毫秒+100w内的随机数
				String fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_BANK.jpg";
				File targetFile = new File(path,fileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				//保存操作
				try {
					mbankFile.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String url = request.getContextPath()+"/statics/uploadfiles/"+fileName;
				//String url2 = targetFile.getPath();
				return url;
			}else {
				return "2";
			}
		}		
		return cardFile;		
	}
	
	//图片删除
	@RequestMapping(value = "backend/delpic.html",produces={"text/html;charset=UTF-8"})
	@ResponseBody
	public String delPic(@RequestParam(value="picpath",required=false) String picpath,
			 @RequestParam(value="id",required=false) String id,
			HttpServletRequest request,HttpSession session){
		String result= "failed" ;
		if(picpath == null || picpath.equals("")){
			result = "success"; 
		}else{
		String[] paths = picpath.split("/");
		String path = request.getSession().getServletContext().getRealPath(paths[2]+File.separator+paths[3]+File.separator+paths[4]);  
		File file = new File(path);
		if(file.exists()){
			logger.debug("=====================>图片存在删除路径"+picpath);
			if(file.delete()){//删除文件
				if(id.equals("0")){//添加用户时，删除上传的图片
				 result = "success";
				}else{//修改用户时，删除上传的图片
					User _user = new User();
					_user.setId(Integer.valueOf(id));
					if(picpath.indexOf("_IDCARD.jpg") != -1)
						_user.setIdCardPicPath(picpath);
					else if(picpath.indexOf("_BANK.jpg") != -1)
						_user.setBankPicPath(picpath);
				try {
					if(userService.delUserPic(_user) > 0){//删除数据库记录
						result = "success";
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return result;
				}
			  }
			}
		}else {
			logger.debug("=====================>图片不存在删除路径"+picpath);
			User _user = new User();
			_user.setId(Integer.valueOf(id));
			if(picpath.indexOf("_IDCARD.jpg") != -1)
				_user.setIdCardPicPath(picpath);
			else if(picpath.indexOf("_BANK.jpg") != -1)
				_user.setBankPicPath(picpath);
		try {
			logger.debug("=====================>图片不存在删除路径user"+_user);
			if(userService.delUserPic(_user) > 0){//删除数据库记录
				result = "success";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return result;
		}
		}
	}
	return result;
}
	
	
	//获取用户信息
	@RequestMapping(value="backend/getuser.html",produces={"text/html;charset=UTF-8"})
	@ResponseBody
	public Object getuser(@RequestParam(value="id",required=false) String id){
		String cjson = "";
		if (id==null || id.equals("")) {
			return "nodata";
		}else {
			try {
				User user = new User();
				user.setId(Integer.valueOf(id));
				user = userService.getUserById(user);
				//注册json日期转换
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
				cjson = JSONObject.fromObject(user,jsonConfig).toString();
				JSONObject json = JSONObject.fromObject(user);
				System.out.println(json.get("birthday")+"============================");
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
			return cjson;
		}				
	}	
	
	//修改用户信息
	@RequestMapping(value="backend/modifyuser.html",method = RequestMethod.POST)
	public ModelAndView modifyuser(HttpSession session,@ModelAttribute("modifyUser") User modifyUser){
		if (session.getAttribute(Constants.SESSION_BASE_MODEL)==null) {
			return new ModelAndView("redirect:/index.jsp");
		}else {
			try {			
				modifyUser.setLastUpdateTime(new Date());				
				userService.modifyUser(modifyUser);
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return new ModelAndView("redirect:/backend/userlist.html");
		}		
	}
	
	//删除用户 backend/deluser.html
	@RequestMapping(value = "/backend/deluser.html", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String delUser(@RequestParam(value="id",required=false) String id,
						  @RequestParam(value="idCardPicPath",required=false) String idCardPicPath,			  
						  @RequestParam(value="bankPicPath",required=false) String bankPicPath,			  
						  @RequestParam(value="userType",required=false) String userType,			  
						  HttpServletRequest request,HttpSession session){
		
		String result= "false" ;
		User delUser = new User();
		delUser.setId(Integer.valueOf(id));
		try {
			//若被删除的用户为：普通消费会员、VIP会员、加盟店  则不可被删除
			if(userType.equals("2") || userType.equals("3") || userType.equals("4")){
				result = "noallow";
			}else{
				if(this.delPic(idCardPicPath,id,request,session).equals("success") && this.delPic(bankPicPath,id,request,session).equals("success")){
					if(userService.deleteUser(delUser) > 0)
						result = "success";
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
}



   

