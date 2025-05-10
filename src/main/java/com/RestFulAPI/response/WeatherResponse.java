package com.RestFulAPI.response;

import java.util.List;

import lombok.Data;

@Data
public class WeatherResponse {
;
	private Current current;


	@Data
	public class Current {
		private String observation_time;
		private int temperature;
		private List<String> weather_descriptions;
		private int feelslike;
		
	}


}
