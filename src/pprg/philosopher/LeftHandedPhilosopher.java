package pprg.philosopher;

public class LeftHandedPhilosopher extends Philosopher {

	public LeftHandedPhilosopher(int index) {
		super(index);
	}

	int getRightFork() {
		return super.getLeftFork();
	}

	int getLeftFork() {
		return super.getRightFork();
	}

}
