package com.infy.service;

import org.springframework.http.ResponseEntity;

import com.infy.model.Weather;
import com.infy.dto.Climate;

import reactor.core.publisher.Mono;

public interface WeatherService {
	

	public Mono<Climate> createWeather(Weather weather); 

	public Weather getWeatherByCity(String city); 

}