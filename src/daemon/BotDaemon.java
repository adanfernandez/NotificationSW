package daemon;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import controller.BotNotifier;

public class BotDaemon implements Runnable {

	@Override
	public void run() {
		
		   try {
               TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
               telegramBotsApi.registerBot(new BotNotifier());
           } catch (TelegramApiException e) {
               e.printStackTrace();
           }
		
	}


}
