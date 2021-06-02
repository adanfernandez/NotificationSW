package controller;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import business.BotInteractionService;
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
			BotInteractionService.manage(text, message);
			
			try {
				execute(message); // Call method to send the message
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
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



}
