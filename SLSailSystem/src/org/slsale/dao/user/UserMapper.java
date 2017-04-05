package org.slsale.dao.user;

import java.util.List;

import org.slsale.pojo.User;
/*
 * @UserMapper
 * @author king
 * @date 2017-2-25
 * */
public interface UserMapper {
	//查询登陆用户
	public User getLoginUser(User user) throws Exception;
	//查询登陆账号是否存在
	public int loginCodeIsExit(User user) throws Exception;
	//修改User
	public int modifyUser(User user) throws Exception;
	//查询总记录数
	public int count(User user) throws Exception;
	//查询用户列表
	public List<User> getUserList(User user) throws Exception;
	//添加用户
	public int addUser(User user) throws Exception;
	//图片删除
	public int delUserPic(User user) throws Exception;
	//获取用户
	public User getUserById(User user) throws Exception;
	//用户删除 
	public int deleteUser(User user) throws Exception;
	
}	
