package com.infy.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.infy.model.Weather;
import com.infy.dto.Climate;
import com.infy.util.ConverterUtil;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;
@Log4j2
@Service
public class WeatherServiceImpl implements WeatherService {

	@Autowired
	private final WebClient webClient;

	@Autowired
	private DozerBeanMapper mapper;
	
	public WeatherServiceImpl(WebClient webClient) {
		super();
		this.webClient = webClient;
	}

	@Override
	public Mono<Climate> createWeather(Weather weather) {
		
		Mono<Climate> weatherMono = webClient.post()
		  .uri("/climates")
	      .body(Mono.just(convertToClimate(weather)), Weather.class)
	      .retrieve()
	      .bodyToMono(Climate.class);
	    return weatherMono;
	}

	private Climate convertToClimate(Weather weather) {
		Climate climate = mapper.map(weather, Climate.class);
		String countryCode = ConverterUtil.getCountryCode().get(weather.getCountryCode());
		climate.setCountryCode(countryCode);
    	//logic toconvert epoch into Java Date - in string format
        Date date = new Date(weather.getDate() * 1000);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String isoDate = format.format(date);
        climate.setDate(isoDate);
        //logic to convert tempreture
        climate.setTemperatureInFht(celsiusToFahrenheit(weather.getTemperatureInCelcius()));
        log.info("Weather object converter to climate DTO successfully... {} ", climate);
		return climate;
	}
	
	public static double celsiusToFahrenheit(double celsius) {
        return (celsius * 9/5) + 32;
    }
	
	@Override
	public Weather getWeatherByCity(String city) {
		// TODO Auto-generated method stub
		return null;
	}

}