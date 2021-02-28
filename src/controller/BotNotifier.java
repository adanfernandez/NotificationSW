package controller;

import java.sql.SQLException;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import infraestructure.FactoryHelper;
import model.TelegramConfiguration;
import persistence.DataServiceFactory;
import persistence.SimpleDataServiceFactory;

public class BotNotifier extends TelegramLongPollingBot {

	DataServiceFactory dataService = new SimpleDataServiceFactory();

	@Override
	public void onUpdateReceived(Update update) {
		// TODO Auto-generated method stub
		if (update.hasMessage() && update.getMessage().hasText()) {
			String text = update.getMessage().getText();
			SendMessage message = new SendMessage();
			message.setChatId(update.getMessage().getChatId().toString());
			if (text.startsWith("/start")) {
				message.setText(this.askForEmail());
			} else if (text.startsWith("/email")) {
				long user_id = getIdByEmail(text.split(" ")[1]);
				if (user_id == 0) {
					message.setText(notFindMsg());
				} else {
					saveTelegramConfig(user_id, update.getMessage().getChatId());
					message.setText(this.askWhenNotify());
				}
			} else if (text.startsWith("/time")) {
				int time = getTime(text.split(" ")[1]);
				if ((time == 30 || time == 60 || time == 180)
						&& saveNotifyConfig(update.getMessage().getChatId(), time)) {
					;
					message.setText("Gracias! Tu configuración se ha guardado correctamente!");
				} else {
					message.setText(incorrectTimeMsg());
				}
			} else if (text.startsWith("/stop")) {
				saveNotifyConfig(update.getMessage().getChatId(), 0);
				message.setText("Entendido! No recibirás más notificaciones");
			}
			else {
				message.setText("No te he entendido");
			}

//		        SendMessage message = new SendMessage();
//		        		message.setChatId(update.getMessage().getChatId().toString());
//		                message.setText(update.getMessage().getText());
			try {
				execute(message); // Call method to send the message
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
	}

	private void saveTelegramConfig(long user_id, Long chatId) {
		TelegramConfiguration tc = new TelegramConfiguration();
		tc.setTelegramId(chatId);
		tc.setUserId(user_id);
		try {
			FactoryHelper.dataServices
			.getNotificableDataService().saveTelegramConfiguration(tc);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean saveNotifyConfig(Long chatId, int time) {
		try {
			return FactoryHelper.dataServices
					.getNotificableDataService().saveNotifyConfiguration(chatId, time);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private int getTime(String time) {
		try {
			return Integer.valueOf(time);
		} catch (Exception ex) {
			System.out.println("No es un entero.");
		}
		return 0;
	}

	private long getIdByEmail(String email) {
		try {
			return FactoryHelper.dataServices
					.getNotificableDataService().getUserByEmail(email);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	@Override
	public String getBotUsername() {
		// TODO Auto-generated method stub
		return "WIPAppNotifierbot";
	}

	@Override
	public String getBotToken() {
		// TODO Auto-generated method stub
		return "1606514999:AAFaitiR11wUhVks5myvhRBks4VT9urNdhI";
	}

	private String askForEmail() {
		return "Hola! bienvenido a WIPApp, dime tu email para saber quien eres!\n"
				+ "Utiliza el comando /email <tu-email>";
	}

	private String notFindMsg() {
		return "No he podido encontrar tu email, introducelo de nuevo, por favor.";
	}

	private String askWhenNotify() {
		return "Perfecto! Por último, indica cuantos minutos antes (30, 60 o 180) quieres ser notificado.\n"
				+ "Utiliza el comando /time <tiempo>. ";
	}

	private String incorrectTimeMsg() {
		return "No has introducido un valor válido (30, 60, 120). Intentalo de nuevo.";
	}

}
