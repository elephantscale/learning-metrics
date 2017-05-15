package com.elephantscale.learn_metrics;

import java.util.Random;

public class Utils {
	
	private static final Random random = new Random();
	
	public static void randomDelay (int upto) {
		try {
			int sleepInterval = random.nextInt(upto);
			Thread.sleep(sleepInterval);
		} catch (InterruptedException e) { }
		
	}

	public static void sleepFor (int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) { }
		
	}
	

}
