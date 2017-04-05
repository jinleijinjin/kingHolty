package org.slsale.controller;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.slsale.common.Constants;
import org.slsale.pojo.User;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javafx.scene.chart.PieChart.Data;

public class BaseController {
	
	private Logger logger = Logger.getLogger(BaseController.class);
	
	private User currentUser =null;
	
	public User getCurrentUser(){
		if(null==this.currentUser){
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			HttpSession session = request.getSession(false);
			if(session!=null){
				currentUser=(User) session.getAttribute(Constants.SESSION_USER);
				
			}else {
				currentUser=null;
			}
		}
		
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
/*
 * 日期国际化
 * */
	
	@InitBinder
	public void InitBinder(WebDataBinder initBinder){
		initBinder.registerCustomEditor(Data.class, new PropertyEditorSupport(){
			public void setAsText(String value){
				try {
					setValue(new SimpleDateFormat("yyyy-MM-dd").parse(value));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	 
			}
			public String getAsText(){
				return new SimpleDateFormat("yyyy-MM-dd").format((Date)getValue());
			}
		});
		
	}
	
	//日期的格式化
		@InitBinder
		protected void initBinder(WebDataBinder binder) {
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		}
	
	
}
