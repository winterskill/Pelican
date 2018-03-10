package winterskill.discord.pelican.commands.base;

import winterskill.discord.pelican.commands.Command;
import winterskill.discord.pelican.commands.CommandMap;
import winterskill.discord.pelican.commands.ExecutorType;
import winterskill.discord.pelican.commands.SimpleCommand;

public class ChelpCommand {
	private CommandMap m;
	
	public ChelpCommand(CommandMap m) {
		this.m = m;
	}
	
	@Command(name="chelp", type=ExecutorType.CONSOLE, description="Show the console commands help")
	public void cmd() {
		String output = "Liste des commandes disponibles\r\n\r\n";
		
		for (SimpleCommand command : m.getCommands()) {
			if (command.getType() == ExecutorType.USER)
				continue;
			
			output += command.getName() + " : " + command.getDescription() + "\r\n";
		}
		
		System.out.print(output);
	}
}
