package com.linglifu.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.linglifu.vo.SafeCode;

public class RandomUtil {
	private static Map<Integer, String> fuhaoMap;
	static {
		fuhaoMap = new HashMap<Integer, String>();
		fuhaoMap.put(0, "+");
		fuhaoMap.put(1, "-");
		fuhaoMap.put(2, "*");
		fuhaoMap.put(3, "/");
	}

	/**
	 * 随机取符号
	 * 
	 * @return index
	 */
	private static int getFuhao() {
		return new Random().nextInt(4);
	}

	/**
	 * 1-5随机数
	 * 
	 * @return
	 */
	private static int getNum() {
		return new Random().nextInt(4) + 1;
	}

	/**
	 * 计算结果
	 * 
	 * @param fh1
	 * @param fh2
	 * @param num1
	 * @param num2
	 * @param num3
	 * @return
	 */
	private static int calculate(int fh1, int fh2, int num1, int num2, int num3) {
		int answer = 0;
		if (fh1 == 0) {
			switch (fh2) {
			case 0:
				answer = num1 + num2 + num3;
				break;
			case 1:
				answer = num1 + num2 - num3;
				break;
			case 2:
				answer = num1 + num2 * num3;
				break;
			case 3:
				answer = num1 + num2 / num3;
				break;

			}
		} else if (fh1 == 1) {
			switch (fh2) {
			case 0:
				answer = num1 - num2 + num3;
				break;
			case 1:
				answer = num1 - num2 - num3;
				break;
			case 2:
				answer = num1 - num2 * num3;
				break;
			case 3:
				answer = num1 - num2 / num3;
				break;
			}
		} else if (fh1 == 2) {
			switch (fh2) {
			case 0:
				answer = num1 * num2 + num3;
				break;
			case 1:
				answer = num1 * num2 - num3;
				break;
			case 2:
				answer = num1 * num2 * num3;
				break;
			case 3:
				answer = num1 * num2 / num3;
				break;
			}
		} else if (fh1 == 3) {
			switch (fh2) {
			case 0:
				answer = num1 / num2 + num3;
				break;
			case 1:
				answer = num1 / num2 - num3;
				break;
			case 2:
				answer = num1 / num2 * num3;
				break;
			case 3:
				answer = num1 / num2 / num3;
				break;
			}
		}

		return answer;
	}

	/**
	 * 获取验证码
	 * 
	 * @return
	 */
	public static SafeCode getSafeCode() {
		SafeCode sc = new SafeCode();
		int num1 = getNum();
		int num2 = getNum();
		int num3 = getNum();
		int fh1 = getFuhao();
		int fh2 = getFuhao();
		String fhStr1 = fuhaoMap.get(fh1);
		String fhStr2 = fuhaoMap.get(fh2);
		int answer = calculate(fh1, fh2, num1, num2, num3);
		String code = num1 + " " + fuhaoMap.get(fh1) + " " + num2 + " "
				+ fuhaoMap.get(fh2) + " " + num3 + " = ?";
		sc.setCode(code);
		sc.setAnswer(answer);
		System.out.println(sc);
		return sc;
	}
}
