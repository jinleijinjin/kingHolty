package org.slsale.dao.dataDictionary;

import java.util.List;

import org.slsale.pojo.DataDictionary;

public interface DataDictionaryMapper {

	//根据CART_TYPE获取DataDictionary集合
	public List<DataDictionary> getDataDictionarys(DataDictionary dataDictionary) throws Exception;
}
