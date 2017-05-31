package com.elephantscale.learn_metrics.archive;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.codahale.metrics.Histogram;
import com.elephantscale.learn_metrics.MyMetricsRegistry;
import com.elephantscale.learn_metrics.MyUtils;

public class Histograms {
	
	private static final Histogram metricsHistMessageSize = MyMetricsRegistry.metrics.histogram("message_size_bytes");
	private static final Logger LOG = LogManager.getLogger();

	public static void main(String[] args) {
		
		Random random = new Random();
		
		while (true) {
			int packetSize = 100 + random.nextInt(500 - 100 + 1);
			metricsHistMessageSize.update(packetSize);

			MyUtils.randomDelay(300);  // simulate doing something cool :-)
			
		}

	}
	
}
