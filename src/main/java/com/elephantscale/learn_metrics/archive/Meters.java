package com.elephantscale.learn_metrics.archive;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.codahale.metrics.Meter;
import com.elephantscale.learn_metrics.MyMetricsRegistry;
import com.elephantscale.learn_metrics.Utils;

public class Meters {
	
	private static final Meter metricsMeterRequests = MyMetricsRegistry.metrics.meter("requests");
	private static final Logger LOG = LogManager.getLogger();

	public static void main(String[] args) {
		
		while (true) {
			doSomethingCool();
			
//			try {
//				Thread.sleep(300); 
//			}
//			catch(InterruptedException e) {}
			
		}

	}
	
	public static void doSomethingCool() {
		Utils.randomDelay(300);  // simulate doing something cool :-)
		LOG.info("doing something cool");
		metricsMeterRequests.mark();
		
	}

}
