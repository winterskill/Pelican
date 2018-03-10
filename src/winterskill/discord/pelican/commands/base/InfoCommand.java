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
	
	@Command(name="info", type=ExecutorType.USER, description="Des infos g�n�rales...")
	public void cmd(User user, MessageChannel chan) {
		// on teste si on a le droit d'envoyer des messages embed
		if (chan instanceof TextChannel) {
			TextChannel textChan = (TextChannel)chan;
			
			if (!textChan.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_EMBED_LINKS))
				return;
		}
		
		EmbedBuilder embed = new EmbedBuilder();
		embed.setAuthor("P�lican");
		embed.setTitle("Infos");
		embed.setDescription("Les p�licans forment le genre d'oiseaux Pelecanus, unique repr�sentant de la famille des Pelecanidae (ou p�l�canid�s) qui compte huit esp�ces.");
		embed.addField("", "Les p�licans sont de grands oiseaux (de 105 � 188 cm) aquatiques piscivores caract�ris�s par un grand bec muni d'une volumineuse poche extensible.\r\n" + 
				"\r\n" + 
				"Cosmopolites � l'exception des hautes latitudes, les p�licans fr�quentent les �tendues d'eau libre, � la fois sur les c�tes et � l'int�rieur des terres, depuis les r�gions tropicales jusqu'aux zones temp�r�es chaudes.", false);
		embed.addField("+ d'infos...", "Allez voir ici : [https://fr.wikipedia.org/wiki/P%C3%A9lican](https://fr.wikipedia.org/wiki/P%C3%A9lican)", false);
		embed.setImage("https://upload.wikimedia.org/wikipedia/commons/6/65/Pelican_nageant.jpg");
		embed.setThumbnail("https://upload.wikimedia.org/wikipedia/commons/5/5a/Pelican_on_Water_JM_Rosier.jpg");
		embed.setColor(Color.CYAN);
		
		chan.sendMessage(embed.build()).queue();
		
		return;
	}
}
