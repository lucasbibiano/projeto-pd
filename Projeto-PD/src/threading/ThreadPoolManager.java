package threading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolManager {
	
	private static ThreadPoolManager instance;
	
	private ExecutorService executor;
	
	public static ThreadPoolManager getInstance() {
		if (instance == null)
			instance = new ThreadPoolManager();
		
		return instance;
	}
	
	private ThreadPoolManager() {
		executor = Executors.newCachedThreadPool();
	}
	
	public ExecutorService getExecutorService() {
		return executor;
	}
}
