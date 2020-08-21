package com.phenix.util;

import com.phenix.data.Security;
import com.phenix.exception.DuplicateCriteriaKeyException;
import com.phenix.exception.IllegalDataException;
import lombok.Getter;
import org.jdom2.Element;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ConfigMatchCriteria implements Comparable<ConfigMatchCriteria> {
	private Map<String, String> criterias;

	@Getter
	private int priority;
	
	public ConfigMatchCriteria(int priority_, Element e_) {
		priority = priority_;
		criterias = new HashMap<>();

		for(Element e : e_.getChildren()) {
			addMatchCriteria(e.getName(), e.getValue());
		}
	}
	
	public ConfigMatchCriteria(int priority_, Map<String, String> data_) {
		priority = priority_;
		criterias = new HashMap<>();

		for(Map.Entry<String, String> a : data_.entrySet()) {
			addMatchCriteria(a.getKey(), a.getValue());
		}
	}
	
	private void addMatchCriteria(String k_, String v_) {
		if(criterias.containsKey(k_)) {
			throw new DuplicateCriteriaKeyException(String.format("[%s]=[%s] criteria has a duplicated key", k_, v_));
		} else {
			criterias.put(k_, v_);
		}
	}

	public boolean match(Security s_) {
		Field[] fields = s_.getClass().getDeclaredFields();
		AccessibleObject.setAccessible(fields, true);

		for (Field f : fields) {
			if(criterias.containsKey(f.getName())) {
				try {
					Object obj = f.get(s_);
					if(obj == null)
						return false;
					String val = criterias.get(f.getName());
					if(val == null)
						return false;
					if(!obj.toString().matches(val)) {
						return false;
					}
				} catch (IllegalAccessException e_) {
					throw new IllegalDataException(e_);
				}
			}
		}

		return true;
	}
	
	@Override
	public int compareTo(ConfigMatchCriteria o_) {
		return priority - o_.priority;
	}
}
