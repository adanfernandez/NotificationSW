package sender;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;

import conf.Config;
import model.Notificable;

public class BotNotifierSender {
	
	public static void sendNotification(Notificable notificable) {
		TelegramBot bot = new TelegramBot(Config.getInstance().get("TG_TOKEN"));

		bot.execute(new SendMessage(
				notificable.getTgUserId(), notificable.getNotication()));
	}
	

}
