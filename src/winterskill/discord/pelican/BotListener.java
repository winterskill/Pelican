package winterskill.discord.pelican;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;
import winterskill.discord.pelican.commands.CommandMap;

public class BotListener implements EventListener {
	private final CommandMap commandMap;
	
	public BotListener(CommandMap cm) {
		this.commandMap = cm;
	}
	
	@Override
	public void onEvent(Event event) {
		if (event instanceof MessageReceivedEvent)
			this.onMessage((MessageReceivedEvent) event);
	}
	
	private void onMessage(MessageReceivedEvent event) {
		if (event.getAuthor().equals(event.getJDA().getSelfUser()))
			// si c'est le bot qui émet le message, on annule la commande,
			// pour éviter les boucles infinies
			return;
		
		String message = event.getMessage().getContentRaw();
		
		if (message.startsWith(commandMap.getTag())) {
			// si le message commence par l'opérateur de commande
			// en l'occurence, /
			message = message.replaceFirst(commandMap.getTag(), "");
			
			if (commandMap.commandUser(event.getAuthor(), message, event.getMessage())) {
				// #ici
				if (event.getTextChannel() != null && event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
					event.getMessage().delete().queue();
				}
			}
		}
	}
}
