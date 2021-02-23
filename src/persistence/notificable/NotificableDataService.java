package persistence.notificable;

import java.sql.SQLException;
import java.util.List;

import model.Notificable;
import persistence.MySQLCon;

public interface NotificableDataService {

	public MySQLCon getDbConnection();
	
	public List<Notificable> getNotificableList() throws SQLException;
	

}
