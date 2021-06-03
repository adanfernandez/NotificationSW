package business;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

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
			TelegramConfigurationService.saveNotifyConfig(chatId, 0);
			message.setText("Entendido! No recibirás más notificaciones");
		} catch (Exception ex) {
			message.setText("Lo siento, ha ocurrido un error Inténtalo de nuevo.");
		}
	}

	private static void manageEmail(SendMessage message, String mail) {
		try {
			long user_id = 30;
			TelegramConfigurationService.getIdByEmail(mail);
			if (user_id == 0) {
				message.setText("No he podido encontrar tu email, introducelo de nuevo, por favor.");
			} else {
				Long chatId = Long.getLong(message.getChatId());
				TelegramConfigurationService.saveTelegramConfig(user_id, chatId);
				message.setText(
						"Perfecto! Por último, indica cuantos minutos antes (30, 60 o 180) quieres ser notificado.\n"
								+ "Utiliza el comando /time <tiempo>. ");
			}
		} catch (Exception ex) {
			message.setText("Lo siento, ha ocurrido un error Inténtalo de nuevo.");
		}
	}

	private static void manageTime(SendMessage message, String setTime) {
		try {
			int time = Integer.valueOf(setTime);
			Long chatId = Long.parseLong(message.getChatId());
			if ((time == 30 || time == 60 || time == 180) && TelegramConfigurationService.saveNotifyConfig(chatId, time)) {
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


}
