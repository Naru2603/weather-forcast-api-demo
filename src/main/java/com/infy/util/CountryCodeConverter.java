package com.infy.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.dozer.CustomConverter;
import org.springframework.stereotype.Component;

import com.infy.model.Weather;
import com.infy.dto.Climate;

@Component
public class CountryCodeConverter implements CustomConverter {
	
    public Object convert(Object dest, Object source, Class<?> arg2, Class<?> arg3) {
        if (source == null) 
            return null;
        
        if (source instanceof Weather) {
        	Weather weather = (Weather) source;
        	//logic toconvert epoch into Java Date - in string format
            Date date = new Date(weather.getDate() * 1000);
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            String isoDate = format.format(date);
            
            String countryCode = getCountryCode(weather.getCountryCode());
            
            Climate climate = new Climate();
            climate.setCity(weather.getCity());
            climate.setCountry(weather.getCountry());
            climate.setCountryCode(countryCode);
            climate.setDate(isoDate);
            return climate;

        }else
		return null; 
        
    }

	private String getCountryCode(int countryCode) {
		
		return ConverterUtil.getCountryCode().get(countryCode);
	}
	
}