package org.slsale.service.dataDictionary;

import java.util.List;

import org.slsale.pojo.DataDictionary;

public interface DataDictionaryService {

	//根据CART_TYPE获取DataDictionary集合
		public List<DataDictionary> getDataDictionarys(DataDictionary dataDictionary) throws Exception;
}
