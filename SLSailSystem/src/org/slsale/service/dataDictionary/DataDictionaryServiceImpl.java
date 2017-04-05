package org.slsale.service.dataDictionary;

import java.util.List;

import javax.annotation.Resource;

import org.slsale.dao.dataDictionary.DataDictionaryMapper;
import org.slsale.pojo.DataDictionary;
import org.springframework.stereotype.Service;
@Service
public class DataDictionaryServiceImpl implements DataDictionaryService {
	@Resource
	DataDictionaryMapper dataDictionaryMapper;

	@Override
	public List<DataDictionary> getDataDictionarys(DataDictionary 
			dataDictionary) throws Exception {
		// TODO Auto-generated method stub
		return dataDictionaryMapper.getDataDictionarys(dataDictionary);
	}

}
