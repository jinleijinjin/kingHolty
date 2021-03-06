package org.slsale.dao.function;

import java.util.List;

import org.slsale.pojo.Authority;
import org.slsale.pojo.Function;

public interface FunctionMapper {
	//获取主功能菜单
	public List<Function> getMainFunctionList(Authority authority) throws Exception;
	
	//获取子功能菜单
	public List<Function> getSubFunctionList(Function function) throws Exception;
}
