package daemon;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DaemonManager implements ServletContextListener {
	
	private ScheduledExecutorService scheduler;

	@Override
	public void contextInitialized(ServletContextEvent event) {
	    scheduler = Executors.newSingleThreadScheduledExecutor();
	    scheduler.execute(new BotDaemon());
	    scheduler.scheduleAtFixedRate(new TaskCheckerDaemon(), 0, 5, TimeUnit.SECONDS);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	    scheduler.shutdownNow();
	 }

}
