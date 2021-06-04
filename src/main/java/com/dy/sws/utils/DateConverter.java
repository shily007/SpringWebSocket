package com.dy.sws.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class DateConverter {
	
	private String [] pattern = {"yyyy-MM-dd HH:mm:ss","yyyy年MM月dd日 HH时mm分ss秒"};
	
	@Bean
    public Converter<String, LocalDateTime> LocalDateTimeConvert() {
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
            	for (String str : pattern) {
            		try {
            			return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(str));
					} catch (Exception e) {
						continue;
					}
				}
            	return null;
            }
        };
    }
	
	private String [] pattern2 = {"yyyy-MM-dd","yyyy年MM月dd日","yyyy-mm-dd"};
	
	@Bean
    public Converter<String, LocalDate> LocalDateConvert() {
        return new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String source) {
            	for (String str : pattern2) {
            		try {
            			return LocalDate.parse(source, DateTimeFormatter.ofPattern(str));
					} catch (Exception e) {
						continue;
					}
				}
            	return null;
            }
        };
    }

}
