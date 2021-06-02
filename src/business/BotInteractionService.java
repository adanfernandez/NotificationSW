package business;

import java.sql.SQLException;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import infraestructure.FactoryHelper;
import model.TelegramConfiguration;

public class BotInteractionService {

	public static void manage(String text, SendMessage message) {
		String action = text.split(" ")[0];
		switch (action) {
		case "/start":
			manageStart(message);
			break;
		case "/stop":
			manageStop(message);
			break;
		case "/email":
			manageEmail(message, text.split(" ")[1]);
			break;
		case "/time":
			manageTime(message, text.split(" ")[1]);
			break;
		default:
			manageUnknow(message);
			break;
		}
	}

	private static void manageStart(SendMessage message) {
		message.setText("Hola! bienvenido a WIPApp, dime tu email para saber quien eres!\n"
				+ "Utiliza el comando /email <tu-email>");
	}

	private static void manageStop(SendMessage message) {
		try {
			Long chatId = Long.getLong(message.getChatId());
			saveNotifyConfig(chatId, 0);
			message.setText("Entendido! No recibirás más notificaciones");
		} catch (Exception ex) {
			message.setText("Lo siento, ha ocurrido un error Inténtalo de nuevo.");
		}
	}

	private static void manageEmail(SendMessage message, String mail) {
		try {
			long user_id = 30;getIdByEmail(mail);
			if (user_id == 0) {
				message.setText("No he podido encontrar tu email, introducelo de nuevo, por favor.");
			} else {
				Long chatId = Long.getLong(message.getChatId());
				saveTelegramConfig(user_id, chatId);
				message.setText(
						"Perfecto! Por último, indica cuantos minutos antes (30, 60 o 180) quieres ser notificado.\n"
								+ "Utiliza el comando /time <tiempo>. ");
			}
		} catch (Exception ex) {
			message.setText("Lo siento, ha ocurrido un error Inténtalo de nuevo.");
		}
	}

	private static void manageTime(SendMessage message, String stTime) {
		try {
			int time = Integer.valueOf(stTime);
			Long chatId = Long.parseLong(message.getChatId());
			if ((time == 30 || time == 60 || time == 180) && saveNotifyConfig(chatId, time)) {
				message.setText("Gracias! Tu configuración se ha guardado correctamente!");
			} else {
				message.setText("El tiempo introducido no es uno válido \n" + " Introduce 30, 60 o 180 (minutos). ");
			}
		} catch (NumberFormatException ex) {
			message.setText("El tiempo introducido no es uno válido \n" + " Introduce 30, 60 o 180. ");
		} catch (Exception ex) {
			message.setText("Lo siento, ha ocurrido un error Inténtalo de nuevo.");
		}
	}

	private static void manageUnknow(SendMessage message) {
		message.setText("No te he entendido");
	}

	private static boolean saveNotifyConfig(Long chatId, int time) throws SQLException {
		try {
			return FactoryHelper.dataServices.getNotificableDataService().saveNotifyConfiguration(chatId, time);
		} catch (SQLException ex) {
			throw ex;
		}
	}

	private static void saveTelegramConfig(long user_id, Long chatId) throws SQLException {
		TelegramConfiguration tc = new TelegramConfiguration();
		tc.setTelegramId(chatId);
		tc.setUserId(user_id);
		try {
			FactoryHelper.dataServices.getNotificableDataService().saveTelegramConfiguration(tc);
		} catch (SQLException ex) {
			throw ex;
		}
	}

	private static long getIdByEmail(String email) throws SQLException {
		try {
			return FactoryHelper.dataServices.getNotificableDataService().getUserByEmail(email);
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

}
