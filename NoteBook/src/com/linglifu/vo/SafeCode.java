package com.linglifu.vo;

public class SafeCode {
	private String code;
	private int answer;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "SafeCode [code=" + code + ", answer=" + answer + "]";
	}

}
