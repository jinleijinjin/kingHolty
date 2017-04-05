package org.slsale.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.slsale.common.Constants;
import org.slsale.common.RedisAPI;
import org.slsale.pojo.Authority;
import org.slsale.pojo.Function;
import org.slsale.pojo.Menu;
import org.slsale.pojo.User;
import org.slsale.service.function.FunctionService;
import org.slsale.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class loginController extends BaseController {

	private Logger logger = Logger.getLogger(loginController.class);

	@Resource
	private UserService userService;
	@Resource
	private FunctionService functionService;
	@Resource
	private RedisAPI redisAPI;

	@RequestMapping("/main.html")
	public ModelAndView main(HttpSession session) {
		
	
		logger.debug("进入main.html==========================");
		User user = this.getCurrentUser();

		List<Menu> mList = null;
		if (user != null) {
			Map<String, Object> model = new HashMap<>();
			model.put("user", user);
			// redis里没有数据
			/*
			 * key格式：menuList+roleid
			 */
			if (!redisAPI.exist("menuList" + user.getRoleId())) {
				// 根据当前角色获取菜单
				mList = getFunctionByCurrentUser(user.getRoleId());
				// json
				if (null != mList) {
					JSONArray array = JSONArray.fromObject(mList);
					String jsonString = array.toString();
					model.put("mList", jsonString);
					redisAPI.set("menuList" + user.getRoleId(), jsonString);
				}
			} else {
				String redisMenuList = redisAPI.get("menuList" + user.getRoleId());
				logger.debug(redisMenuList);
				if (redisMenuList != "" && redisMenuList != null) {
					model.put("mList", redisMenuList);
				} else {
					return new ModelAndView("redirect:index.jsp");
				}
			}
			session.setAttribute(Constants.SESSION_BASE_MODEL, model);
			return new ModelAndView("main", model);
		}
		return new ModelAndView("redirect:index.jsp");
	}

	// 根据当前角色获取菜单
	protected List<Menu> getFunctionByCurrentUser(int roleId) {
		List<Menu> meList = new ArrayList<Menu>();
		Authority authority = new Authority();
		authority.setRoleId(roleId);
		try {
			List<Function> mlist = functionService.getMainFunctionList(authority);
			if (mlist != null) {
				for (Function function : mlist) {
					Menu menu = new Menu();
					menu.setMainMenu(function);
					function.setRoleId(roleId);
					List<Function> subList = functionService.getSubFunctionList(function);
					if (subList != null) {
						menu.setSubMenus(subList);
					}
					meList.add(menu);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return meList;
	}

	@RequestMapping("/login.html")
	@ResponseBody
	public Object login(HttpSession session, @RequestParam String user) {
		if (user == null || "".equals(user)) {
			return "nodata";
		} else {
			JSONObject userObject = JSONObject.fromObject(user);
			User userobj = (User) JSONObject.toBean(userObject, User.class);
			try {
				if (userService.loginCodeIsExit(userobj) == 0) {
					return "nologincode";
				} else {
					// System.out.println(userobj);
					User suser = userService.getLoginUser(userobj);
					if (suser != null) {
						// 保存用户到session
						session.setAttribute(Constants.SESSION_USER, suser);
						// 更新用户最后登录时间lastLoginTime
						User lastLoginTimeUser = new User();
						lastLoginTimeUser.setId(suser.getId());
						lastLoginTimeUser.setLastLoginTime(new Date());
						userService.modifyUser(lastLoginTimeUser);
						lastLoginTimeUser = null;
						return "success";
					} else {
						return "pwderror";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
		}
	}
	//注销功能
	@RequestMapping("logout.html")
	public String logout(HttpSession httpSession){
		httpSession.removeAttribute(Constants.SESSION_USER);
		httpSession.invalidate();
		this.setCurrentUser(null);
		return "redirect:/index.jsp";
	}

}
