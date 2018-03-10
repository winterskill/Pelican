package winterskill.discord.pelican.commands.base;

import winterskill.discord.pelican.Main;
import winterskill.discord.pelican.commands.Command;
import winterskill.discord.pelican.commands.ExecutorType;

public class StopCommand {
	private Main m;
	
	public StopCommand(Main m) {
		this.m = m;
	}
	
	@Command(name="stop",type=ExecutorType.CONSOLE, description="stop the execution of the bot")
	public void cmd() {
		this.m.setRunning(false);
	}
}
