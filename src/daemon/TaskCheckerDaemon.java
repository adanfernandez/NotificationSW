package daemon;

import java.sql.SQLException;

import infraestructure.FactoryHelper;

public class TaskCheckerDaemon  implements Runnable {

	@Override
	public void run() {
		System.out.println("Starting looking for notificables...");
		try {
			FactoryHelper.dataServices
			.getTaskDataService().getNotificableList()
			.forEach(notificable -> System.out.println("Notifying..."));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("End of notificable...");
		
	}

	
}
