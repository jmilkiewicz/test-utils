package pl.softmil.test.utils.waituntil;

public class WaitUntil<T> {
	private long maxMillisToWait;
	private long sleepInterval;
	private Until<T> until;

	public WaitUntil(WaitUntilTimes waitUntilTimes, Until<T> until) {
		super();
		this.maxMillisToWait = waitUntilTimes.maxMillisToWait;
		this.sleepInterval = waitUntilTimes.sleepInterval;
		this.until = until;
	}

	public void waitFor() {
		T context = until.getContext();
		long start = System.currentTimeMillis();
		while (untiIsNotTrue(context)) {
			throwExceptionIfExpired(start);
			sleep();
		}
	}

	private boolean untiIsNotTrue(T context) {
		return !until.isTrue(context);
	}

	private void throwExceptionIfExpired(long start) {
		if (timeoutExpired(start)) {
			throw new TimeoutException(maxMillisToWait, until);
		}
	}

	private void sleep() {
		try {
			Thread.sleep(sleepInterval);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private boolean timeoutExpired(long start) {
		return System.currentTimeMillis() - start > maxMillisToWait;
	}
}
