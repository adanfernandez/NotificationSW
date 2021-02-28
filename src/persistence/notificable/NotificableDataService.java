package persistence.notificable;

import java.sql.SQLException;
import java.util.List;

import model.Notificable;
import model.TelegramConfiguration;
import persistence.MySQLCon;

public interface NotificableDataService {

	public MySQLCon getDbConnection();
	
	public List<Notificable> getNotificableList() throws SQLException;
	
	public long getUserByEmail(String email) throws SQLException;
	
	public boolean saveTelegramConfiguration(TelegramConfiguration telegramConfig) throws SQLException;

	public boolean saveNotifyConfiguration(Long chatId, int time) throws SQLException;

	public void markAsNotified(long taskId) throws SQLException;

	

}
