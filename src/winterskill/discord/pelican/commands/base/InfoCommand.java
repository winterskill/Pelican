package winterskill.discord.pelican.commands.base;

import java.awt.Color;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import winterskill.discord.pelican.Main;
import winterskill.discord.pelican.commands.Command;
import winterskill.discord.pelican.commands.ExecutorType;

public class InfoCommand {
	private Main m;
	
	public InfoCommand(Main m) {
		this.m = m;
	}
	
	@Command(name="info", type=ExecutorType.USER, description="Des infos générales...")
	public void cmd(User user, MessageChannel chan) {
		// on teste si on a le droit d'envoyer des messages embed
		if (chan instanceof TextChannel) {
			TextChannel textChan = (TextChannel)chan;
			
			if (!textChan.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_EMBED_LINKS))
				return;
		}
		
		EmbedBuilder embed = new EmbedBuilder();
		embed.setAuthor("Pélican");
		embed.setTitle("Infos");
		embed.setDescription("Les pélicans forment le genre d'oiseaux Pelecanus, unique représentant de la famille des Pelecanidae (ou pélécanidés) qui compte huit espèces.");
		embed.addField("", "Les pélicans sont de grands oiseaux (de 105 à 188 cm) aquatiques piscivores caractérisés par un grand bec muni d'une volumineuse poche extensible.\r\n" + 
				"\r\n" + 
				"Cosmopolites à l'exception des hautes latitudes, les pélicans fréquentent les étendues d'eau libre, à la fois sur les côtes et à l'intérieur des terres, depuis les régions tropicales jusqu'aux zones tempérées chaudes.", false);
		embed.addField("+ d'infos...", "Allez voir ici : [https://fr.wikipedia.org/wiki/P%C3%A9lican](https://fr.wikipedia.org/wiki/P%C3%A9lican)", false);
		embed.setImage("https://upload.wikimedia.org/wikipedia/commons/6/65/Pelican_nageant.jpg");
		embed.setThumbnail("https://upload.wikimedia.org/wikipedia/commons/5/5a/Pelican_on_Water_JM_Rosier.jpg");
		embed.setColor(Color.CYAN);
		
		chan.sendMessage(embed.build()).queue();
		
		return;
	}
}
