package persistence;

import persistence.notificable.NotificableDAO;
import persistence.notificable.NotificableDataService;



public class SimpleDataServiceFactory implements DataServiceFactory {


	@Override
	public NotificableDataService getNotificableDataService() {
		return new NotificableDAO();
	}
}
