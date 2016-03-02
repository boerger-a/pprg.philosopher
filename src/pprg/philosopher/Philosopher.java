package pprg.philosopher;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Philosopher extends Thread {

	private int index;

	public Philosopher(int index) {
		this.index = index;
	}

	public void run() {
		while (PhilosophersProblem.philosophers[index] != null) {
			// first of all a lot of thinking...
			long thinkingTime = (long) (Math.random() * PhilosophersProblem.maxThinkingTime);
			try {
				sleep(thinkingTime);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}

			System.out.printf("%s: #%03d wants to eat now.\n", getCurrentTime(), index);
			System.out.printf("%s: #%03d took right fork #%03d.\n", getCurrentTime(), index, takeFork(getRightFork()));
			System.out.printf("%s: #%03d took left  fork #%03d.\n", getCurrentTime(), index, takeFork(getLeftFork()));

			// nom nom nom...
			eating();

			System.out.printf("%s: #%03d is done eating.\n", getCurrentTime(), index);
			try {
				putBackForks();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void eating() {
		long eatingTime = (long) (Math.random() * PhilosophersProblem.maxEatingTime);
		try {
			sleep(eatingTime);
			
			synchronized (PhilosophersProblem.philosophersEatingAmount) {
				PhilosophersProblem.philosophersEatingAmount[index] += eatingTime;
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private long takeFork(int forkIndex) {
		while (PhilosophersProblem.philosophers[index] != null) {
			System.out.printf("%s: #%03d is waiting for fork #%03d.\n", getCurrentTime(), index, forkIndex);
			synchronized (PhilosophersProblem.forks) {
				if (PhilosophersProblem.forks[forkIndex] != null) {
					try {
						sleep(PhilosophersProblem.WAITING_TIME);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				} else {
					PhilosophersProblem.forks[forkIndex] = this;
					break;
				}
			}
		}

		return forkIndex;
	}

	private synchronized void putBackForks() throws Exception {
		int right = getRightFork();
		int left = getLeftFork();

		if (PhilosophersProblem.forks[right] == this) {
			PhilosophersProblem.forks[right] = null;
		}

		if (PhilosophersProblem.forks[left] == this) {
			PhilosophersProblem.forks[left] = null;
		}
	}

	int getRightFork() {
		return index;
	}

	int getLeftFork() {
		return (index + 1) % (PhilosophersProblem.numberPhilosophers);
	}

	private String getCurrentTime() {
		SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss.SSS");
		return df.format(new Date());
	}
}
