package business;

import java.sql.SQLException;

import infraestructure.FactoryHelper;
import model.TelegramConfiguration;

public class TelegramConfigurationService {
	
	public static boolean saveNotifyConfig(Long chatId, int time) throws SQLException {
		try {
			return FactoryHelper.dataServices.getNotificableDataService().saveNotifyConfiguration(chatId, time);
		} catch (SQLException ex) {
			throw ex;
		}
	}

	public static void saveTelegramConfig(long user_id, Long chatId) throws SQLException {
		TelegramConfiguration tc = new TelegramConfiguration();
		tc.setTelegramId(chatId);
		tc.setUserId(user_id);
		try {
			FactoryHelper.dataServices.getNotificableDataService().saveTelegramConfiguration(tc);
		} catch (SQLException ex) {
			throw ex;
		}
	}

	public static long getIdByEmail(String email) throws SQLException {
		try {
			return FactoryHelper.dataServices.getNotificableDataService().getUserByEmail(email);
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

}
