package pl.softmil.test.utils.waituntil.blueprint;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;

import org.hamcrest.Matchers;
import org.junit.*;
import org.junit.rules.TemporaryFolder;

import pl.softmil.test.utils.waituntil.*;

public class WaitUntilFileIsStableIntegrationTest {
	private String fileName;
	private final CountDownLatch startSignal = new CountDownLatch(1);
	private final CountDownLatch doneSignal = new CountDownLatch(2);
	private Collection<Exception> exceptions = new LinkedList<Exception>();

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	@Before
	public void setUp() throws IOException {
		fileName = temporaryFolder.newFile("testFile.txt").getCanonicalPath();
	}

	@Test
	public void timeoutWhenThresholdStabilizationCanNotBeSatisfied()
			throws InterruptedException {
		buildModifyFileThread(10, 500).start();
		waitUntilStableFileThread(1000, 300, 4).start();

		startThreads();

		waitForCompletion();
		assertTimeOutException();
	}

	@Test
	public void timeoutWhenModificationTimeGreaterThanWaitTimeAndtHigherWriteFrequency()
			throws InterruptedException {
		buildModifyFileThread(10, 250).start();
		waitUntilStableFileThread(2000, 300, 2).start();
		startThreads();

		waitForCompletion();

		assertTimeOutException();
	}

	@Test
	public void timeoutWhenModificationTimeEqualWaitTimeButHigherWriteFrequency()
			throws InterruptedException {
		buildModifyFileThread(10, 200).start();
		waitUntilStableFileThread(2000, 300, 5).start();

		startThreads();

		waitForCompletion();

		assertTimeOutException();
	}

	@Test
	public void successWhenHigherWriteFrequencyButWaitTimeIsEnoughLongetToStabilize()
			throws InterruptedException {
		buildModifyFileThread(5, 200).start();
		waitUntilStableFileThread(2000, 300, 3).start();

		startThreads();

		waitForCompletion();

		assertEverythingIsFine();
	}

	@Test
	public void successWhenCheckFrequenyIsGreaterThanWriteFrequency()
			throws InterruptedException {
		buildModifyFileThread(3, 800).start();
		waitUntilStableFileThread(2000, 200, 3).start();

		startThreads();

		waitForCompletion();

		assertEverythingIsFine();
	}

	@Test
	public void timeoutWhenHistoryTooShort() throws InterruptedException {
		buildModifyFileThread(3, 500).start();
		waitUntilStableFileThread(2000, 300, 4).start();

		startThreads();

		waitForCompletion();

		assertTimeOutException();
	}

	private void assertEverythingIsFine() {
		assertThat(exceptions, Matchers.<Exception> empty());

	}

	private void waitForCompletion() throws InterruptedException {
		doneSignal.await();
	}

	private void startThreads() {
		startSignal.countDown();
	}

	private void assertTimeOutException() {
		assertThat(exceptions, Matchers.not(Matchers.<Exception> empty()));
		assertThat(exceptions.size(), Matchers.equalTo(1));
		assertThat(exceptions, Matchers.<Exception> hasItem(Matchers
				.instanceOf(TimeoutException.class)));
	}

	private Thread waitUntilStableFileThread(final int waitMaxMillisToWait,
			final int waitSleepInterval, final int stabilizationThreshold) {
		Thread waitUntiThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					startSignal.await();
					WaitUntil<Map<String, List<Long>>> waitUntil = new WaitUntil<Map<String, List<Long>>>(
							waitMaxMillisToWait, waitSleepInterval,
							new UntilFileIsStable(fileName,
									stabilizationThreshold));
					waitUntil.waitFor();
				} catch (Exception e) {
					recordException(e);
				} finally {
					doneSignal.countDown();
				}
			}
		});
		return waitUntiThread;
	}

	private Thread buildModifyFileThread(final int numberOfLinesToWrite,
			final int perWriteSleep) {
		return new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					startSignal.await();
					ModifyFileOperation modifyFile = new ModifyFileOperation(fileName);
					modifyFile.doWork(numberOfLinesToWrite, perWriteSleep);
				} catch (Exception e) {
					recordException(e);
				} finally {
					doneSignal.countDown();
				}
			}
		});
	}

	private void recordException(Exception e) {
		exceptions.add(e);
	}

}
