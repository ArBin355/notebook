package com.linglifu.enumtype;

public enum WeatherEnum {
	SUNNY("sunny", "晴"), OVERCAST("overcast", "阴"), RAIN("rain", "雨"), SNOW(
			"snow", "雪");
	private String name;
	private String value;

	private WeatherEnum(String name, String value) {
		this.name = name;
		this.value = value;
	}


	public String getValue() {
		return this.value;
	}
}
