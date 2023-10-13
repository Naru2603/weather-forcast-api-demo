package com.infy.dto;

import org.dozer.Mapping;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
@JsonInclude(Include.NON_EMPTY)
public class Climate {
	
	private String city;
	//mandatory
	private String country;
	//mandatory
	private String countryCode;
	//mandatory
	private String date;
	//mandatory
	@Mapping("temperatureInCelcius")
	private double temperatureInFht;

	private String weather;
	//optional
	private String weatherDesc;
	//optional
	private double tempFeelsLike;
	private double tempMin;
	private double tempMax;
	private double pressure;
	//mandatory
	@JsonIgnore
	private double humidity;

}