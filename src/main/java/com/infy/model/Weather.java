package com.infy.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Weather {
	
	private String city;
	private String country;
	private int countryCode;
	private long date;
	private double temperatureInCelcius;
	private String weather;
	private String weatherDesc;
	private double tempFeelsLike;
	private double tempMin;
	private double tempMax;
	@JsonAlias({"pressure","weather_pressure"})
	private double pressure;
	private double humidity;

}