package controller;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.sun.jersey.api.client.ClientResponse.Status;

import conf.Config;
import model.Notificable;
import model.TelegramConfiguration;
import persistence.notificable.NotificableDAO;
import persistence.notificable.NotificableDataService;

@Path("api/update")
public class BotNotifierController {

	private TelegramBot bot = new TelegramBot(Config.getInstance().get("TG_TOKEN"));

	public void sendNotification(Notificable notificable) {

		bot.execute(new SendMessage(
				notificable.getTgUserId(), notificable.getNotication()));
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.APPLICATION_JSON)
	public Response setUserTgId(TelegramConfiguration telegramConfig) {
		NotificableDataService dataPersitence = new NotificableDAO();
		try {
			boolean operation = dataPersitence.saveTelegramConfiguration(telegramConfig);
			return operation ? Response.status(Status.NO_CONTENT).build() : Response.status(Status.BAD_REQUEST).build();
		} catch (SQLException e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}	
}