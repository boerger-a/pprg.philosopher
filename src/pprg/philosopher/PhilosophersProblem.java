package pprg.philosopher;

public class PhilosophersProblem {

	public static int numberPhilosophers;
	public static long maxThinkingTime;
	public static long maxEatingTime;
	public static final int WAITING_TIME = 100;
	public static final int RUNTIME = 10000;

	public static Philosopher[] forks;
	public static Philosopher[] philosophers;
	public static long[] philosophersEatingAmount;

	/**
	 * PhilosophersProblem Main Class
	 * 
	 * @param args
	 *            number of philosophers, max. thinking time in ms, max. eating
	 *            time in ms
	 */
	public static void main(String[] args) {
		if (args == null || args.length != 3) {
			throw new IllegalArgumentException(
					"Wrong number of arguments. Needed: number of philosophers, max. thinking time in ms, max. eating time in ms ");
		}
		// read inputs
		numberPhilosophers = Integer.parseInt(args[0]);
		maxThinkingTime = Long.parseLong(args[1]);
		maxEatingTime = Long.parseLong(args[2]);

		// setting forks
		forks = new Philosopher[numberPhilosophers];
		philosophers = new Philosopher[numberPhilosophers];
		philosophersEatingAmount = new long[numberPhilosophers];

		System.out.println("Starting into the day...");
		
		// place left handed philosopher
		philosophers[0] = new LeftHandedPhilosopher(0);
		
		// place all other philosophers at the table
		for(int i = 0; i < philosophers.length; i++)	{
			if(philosophers[i] == null)	{
				philosophers[i] = new Philosopher(i);
			}
		}
		
		// mahlzeit!
		for(Philosopher p : philosophers)	{
			p.start();
		}
		
		new PhilosopherTimer().start();
	}
}
