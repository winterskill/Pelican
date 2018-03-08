package winterskill.discord.pelican.commands.base;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import winterskill.discord.pelican.Main;
import winterskill.discord.pelican.commands.Command;
import winterskill.discord.pelican.commands.ExecutorType;

public class HelloWorldCommand {
	private Main m;
	
	public HelloWorldCommand(Main m) {
		this.m = m;
	}
	
	@Command(name="hello_world", type=ExecutorType.USER)
	public void cmd(User user, MessageChannel channel) {
		// commande
		channel.sendMessage(user.getAsMention() + " dans le channel " + channel.getName()).complete();
	}
}
