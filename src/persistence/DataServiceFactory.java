package persistence;

import persistence.notificable.NotificableDataService;


public interface DataServiceFactory {
	
	public NotificableDataService getNotificableDataService();
		
}
