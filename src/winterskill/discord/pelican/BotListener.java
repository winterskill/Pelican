package winterskill.discord.pelican;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;

public class BotListener implements EventListener {
	@Override
	public void onEvent(Event event) {
		if (event instanceof MessageReceivedEvent)
			this.onMessage((MessageReceivedEvent) event);
	}
	
	private void onMessage(MessageReceivedEvent event) {
		if (event.getAuthor().equals(event.getJDA().getSelfUser()))
			return;
		
		if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE))
			event.getTextChannel().sendMessage("Hello World!").complete();
	}
}
