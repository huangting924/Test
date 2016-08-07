package com.sim_launchermove.socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunnableExecutor {

	private static final int THREAD_NUMBER = 15;

	private static ExecutorService threadPool = Executors
			.newFixedThreadPool(THREAD_NUMBER);

	/**
	 * Execute the request thread.
	 * 
	 * @param command
	 *            thread to be executed.
	 */
	public static void doAsync(Runnable command) {
		threadPool.execute(command);
	}
}
