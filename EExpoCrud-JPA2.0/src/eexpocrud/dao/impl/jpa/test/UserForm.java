package eexpocrud.dao.impl.jpa.test;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class UserForm extends UserEntity{
	
	public Map<Integer, String> idDeptAll(){
		LinkedHashMap<Integer, String> value_labelMap = new LinkedHashMap<>();
		for(int i=0; i<15; i++){
			value_labelMap.put(i, "Label "+i);
		}		
		return value_labelMap;
	}
}
