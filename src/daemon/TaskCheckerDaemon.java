package daemon;

import java.sql.SQLException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import infraestructure.FactoryHelper;
import model.Notificable;

public class TaskCheckerDaemon  implements Runnable {

	@Override
	public void run() {
		System.out.println("Starting looking for notificables...");
		try {
			FactoryHelper.dataServices
			.getNotificableDataService().getNotificableList()
			.forEach(notificable -> this.sendNotification(notificable)); 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("End of notificable...");
		
	}
	
	private void sendNotification(Notificable notificable) {
		Client client = Client.create();
		WebResource wr = client.resource("https://api.telegram.org/bot1606514999:AAFaitiR11wUhVks5myvhRBks4VT9urNdhI/sendmessage");
		wr.queryParam("chat_id", String.valueOf(notificable.getTgUserId())).queryParam("text", notificable.getNotication()).get(ClientResponse.class);
		
		try {
			FactoryHelper.dataServices.getNotificableDataService().markAsNotified(notificable.getTaskId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
