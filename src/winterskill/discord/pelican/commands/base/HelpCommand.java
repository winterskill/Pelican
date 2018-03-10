package winterskill.discord.pelican.commands.base;

import java.awt.Color;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import winterskill.discord.pelican.commands.Command;
import winterskill.discord.pelican.commands.CommandMap;
import winterskill.discord.pelican.commands.ExecutorType;
import winterskill.discord.pelican.commands.SimpleCommand;

public class HelpCommand {
	private CommandMap m;
	
	public HelpCommand(CommandMap m) {
		this.m = m;
	}
	
	@Command(name="help", type=ExecutorType.USER, description="Affiche la liste des commandes.")
	public void cmd(User user, MessageChannel chan) {
		// on vérifie si on a l'autorisation d'envoyer des messages embed
		if (chan instanceof TextChannel) {
			TextChannel tchan = (TextChannel) chan;
			
			if (!tchan.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_EMBED_LINKS))
				return;
		}
		
		EmbedBuilder builder = new EmbedBuilder();

		builder.setTitle("Liste des commandes");
		builder.setColor(Color.GREEN);
		
		for (SimpleCommand command : m.getCommands()) {
			if (command.getType() == ExecutorType.CONSOLE)
				continue;
			
			builder.addField(command.getName(), command.getDescription(), false);
		}
		
		chan.sendMessage(builder.build()).queue();
	}
}
