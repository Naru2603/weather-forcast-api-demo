package com.infy.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConverterUtil {
	
	
	public static Map<Integer, String> getCountryCode(){
		HashMap< Integer, String> countryCode = new HashMap<Integer, String>();
		countryCode.put(91, "INDIA");
		countryCode.put(05, "US");
		countryCode.put(80, "UK");
		return Collections.unmodifiableMap(countryCode);
	}

}