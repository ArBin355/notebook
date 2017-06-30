package com.linglifu.enumtype;

public enum MoodEnum {
	GOOD("good", "好"), BAD("bad", "差");
	private String name;
	private String value;

	private MoodEnum(String name, String value) {
		this.name = name;
		this.value = value;
	}


	public String getValue() {
		return this.value;
	}
}
