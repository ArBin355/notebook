package com.linglifu.vo;

public class Diary {
	private String id;
	private String userId;
	private String date;
	private String weather;
	private String mood;
	private String title;
	private String content;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getMood() {
		return mood;
	}

	public void setMood(String mood) {
		this.mood = mood;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Diary [id=" + id + ", userId=" + userId + ", date=" + date
				+ ", weather=" + weather + ", mood=" + mood + ", title="
				+ title + ", content=" + content + "]";
	}

}
