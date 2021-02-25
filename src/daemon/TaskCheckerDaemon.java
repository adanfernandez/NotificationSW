package daemon;

import java.sql.SQLException;

import infraestructure.FactoryHelper;
import model.Notificable;
import sender.BotNotifierSender;

public class TaskCheckerDaemon  implements Runnable {

	@Override
	public void run() {
		System.out.println("Starting looking for notificables...");
		try {
			FactoryHelper.dataServices
			.getTaskDataService().getNotificableList()
			.forEach(notificable -> this.sendNotification(notificable));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("End of notificable...");
		
	}
	
	private void sendNotification(Notificable notificable) {
		
		BotNotifierSender.sendNotification(notificable);
	}

	
}
