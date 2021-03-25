package com.npst.upiserver;

import java.util.Random;

public class RNDTest {
	public static void main(String[] args) {
		RNDTest test = new RNDTest();
		System.out.println("Random  number is as :" + test.getRandomNumber(100000, 10000));

	}

	private String getRandomNumber(int max, int min) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		Random r = new Random();
		return String.valueOf((r.nextInt((max - min) + 1) + min));
	}
}
