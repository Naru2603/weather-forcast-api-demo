package com.infy.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.infy.model.Weather;
import com.infy.dto.Climate;
import com.infy.util.ConverterUtil;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

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

		Mono<Climate> weatherMono = webClient.post().uri("/climates")
				.body(Mono.just(convertToClimate(weather)), Weather.class).retrieve().bodyToMono(Climate.class);
		return weatherMono;
	}

	private Climate convertToClimate(Weather weather) {
		Climate climate = mapper.map(weather, Climate.class);
		String countryCode = ConverterUtil.getCountryCode().get(weather.getCountryCode());
		climate.setCountryCode(countryCode);
		// logic toconvert epoch into Java Date - in string format
		Date date = new Date(weather.getDate() * 1000);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		String isoDate = format.format(date);
		climate.setDate(isoDate);
		// logic to convert tempreture
		climate.setTemperatureInFht(celsiusToFahrenheit(weather.getTemperatureInCelcius()));
		log.info("Weather object converter to climate DTO successfully... {} ", climate);
		return climate;
	}

	public static double celsiusToFahrenheit(double celsius) {
		return (celsius * 9 / 5) + 32;
	}

	@Recover
	public String getBackendResponseFallback(ArithmeticException e, String param1, String param2) {
		return "Backend is unavailable even after 3 rety attempts!!!!";
	}
	
	@Override
	@Retryable
	public Mono<Climate> getWeatherByCity(String city) {
		log.info("Inside get weather method..!!");
		Mono<Climate> weatherMono = webClient.get().uri("/climates/" + city)
//			      .body(Mono.just(convertToClimate(weather)), Weather.class)
				.retrieve()
				.bodyToMono(Climate.class)
				.retryWhen(Retry
                        .backoff(3, Duration.ofSeconds(2))    // Retry 3 times with a 2-second delay
                        .maxBackoff(Duration.ofSeconds(10))      // Maximum backoff duration
                )
				.onErrorResume(Throwable.class, this::handleRecovery);
		return weatherMono;
	}
	
	 private Mono<Climate> handleRecovery(Throwable error) {
	        // Define your recovery logic here
	        // You can return a default value, fetch data from a backup source, or perform any other action
		 log.info("**********inside handleRecovery()***********");
		 Climate climate = new Climate();
		 climate.setWeatherDesc("ERRRRRRROOOOORRRRRR");
		 
	        return Mono.just(climate);
	    }

}