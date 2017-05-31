package com.elephantscale.learn_metrics.archive;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.codahale.metrics.Timer;
import com.elephantscale.learn_metrics.MyMetricsRegistry;
import com.elephantscale.learn_metrics.MyUtils;

public class Timers {
	
	private static final Timer timerProcessingTime = MyMetricsRegistry.metrics.timer("processing_time");
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
		final Timer.Context timerContext = timerProcessingTime.time();
		MyUtils.randomDelay(300);  // simulate doing something cool :-)
		//LOG.info("doing something cool");
		timerContext.stop();
		
	}

}
