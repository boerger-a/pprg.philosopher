package pprg.philosopher;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PhilosopherTimer extends Thread {
	
	public void run() {
		// wait to cancel philosophers
		try {
			sleep(PhilosophersProblem.RUNTIME);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		// killing philosophers
		for (int i = 0; i < PhilosophersProblem.philosophers.length; i++) {
			PhilosophersProblem.philosophers[i] = null;
		}

		// waiting for philosophers to stop
		long timeToStop = PhilosophersProblem.maxThinkingTime < PhilosophersProblem.maxEatingTime? PhilosophersProblem.maxEatingTime : PhilosophersProblem.maxThinkingTime;
		try {
			sleep(timeToStop + 1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		
		// print statistics
		System.out.println("--------------");
		for (int i = 0; i < PhilosophersProblem.philosophersEatingAmount.length; i++) {
			System.out.printf("%s: #%03d ate %5d schnitzel.\n", getCurrentTime(), i,
					PhilosophersProblem.philosophersEatingAmount[i]);
		}
		System.out.println("--------------");

	}

	private String getCurrentTime() {
		SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss.SSS");
		return df.format(new Date());
	}
}
