package com.infy.config;

import java.util.Arrays;
import java.util.List;

import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.infy.util.CountryCodeConverter;

@Configuration
public class CommonConfigs {

	@Value("${api.url}")
	String climateBaseUrl;

	@Bean
	public WebClient webClient() {

		WebClient webClient = WebClient.builder().baseUrl(climateBaseUrl)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();

		return webClient;
	}

//	@Bean
//	public DozerBeanMapper mapper() {
//		DozerBeanMapper mapper = new DozerBeanMapper();
//		mapper.setMappingFiles(Arrays.asList("dozer_mapping2.xml"));
//		return mapper;
//	}

	@Bean
	@Qualifier
	public DozerBeanMapper mapper() {
	    List<String> mappingFiles = Arrays.asList("dozer_mapping2.xml");
	    
	    List<CustomConverter> converters = Arrays.asList(new CountryCodeConverter());

	    	    DozerBeanMapper dozerBean = new DozerBeanMapper();
	    	    dozerBean.setMappingFiles(mappingFiles);
	    	    dozerBean.setCustomConverters(converters);
	    	    return dozerBean;
	}
}