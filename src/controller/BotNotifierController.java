package controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.persistence.oxm.MediaType;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;

import conf.Config;
import model.Notificable;
import model.TelegramConfiguration;

@Path("api/update")
public class BotNotifierController {

	private TelegramBot bot = new TelegramBot(Config.getInstance().get("TG_TOKEN"));

	public void sendNotification(Notificable notificable) {

		bot.execute(new SendMessage(
				notificable.getTgUserId(), notificable.getNotication()));
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String setUserTgId(TelegramConfiguration telegramConfig) {
		
	}	

}
