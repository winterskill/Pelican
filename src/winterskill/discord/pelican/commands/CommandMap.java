package winterskill.discord.pelican.commands;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import winterskill.discord.pelican.Main;

public final class CommandMap {
	private final Map<String, SimpleCommand> commands = new HashMap<>();
	private final String                     tag      = "/";
	private       Main                       main;
	
	public CommandMap(Main m) {
		this.main = m;
	}
	
	public String getTag() {
		return this.tag;
	}
	
	public Collection<SimpleCommand> getCommands() {
		return this.commands.values();
	}
	
	public Object[] getCommand(String command) {
		String[] splited = command.split(" ");
		String[] args    = new String[splited.length - 1];
		
		for (int i = 1; i < splited.length; i++)
			args[i - 1] = splited[i];
		
		SimpleCommand sc = commands.get(splited[0]);
		
		return new Object[] {sc, args};
	}
	
	public void registerCommands(Object...objects) {
		for (Object o : objects)
			this.registerCommands(o);
		
		return;
	}
	
	public void registerCommand(Object object) {
		for (Method method : object.getClass().getDeclaredMethods()) {
			if (method.isAnnotationPresent(Command.class)) {
				Command command = method.getAnnotation(Command.class);
				method.setAccessible(true);
				SimpleCommand simpleCommand = new SimpleCommand(command.name(), command.description(), command.type(), object, method);
				this.commands.put(command.name(), simpleCommand);
			}
		}
	}
	
	public boolean commandConsole(String command) {
		Object[] object = this.getCommand(command);
		
		if (object[0] == null || ((SimpleCommand) object[0]).getType() == ExecutorType.USER) {
			System.err.println("[ERROR/winterskill.discord.pelican.commands.CommandMap.commandConsole] : Unknown command : " + command);
			
			return false;
		}
		
		try {
			this.execute(((SimpleCommand)object[0]), command,(String[])object[1], null);
		} catch (Exception ex) {
			System.err.println("[ERROR/winterskill.discord.pelican.commands.CommandMap.commandConsole] : La commande " + ((SimpleCommand) object[0]).getMethod().getName() + " n'est pas correctement initialisée!");
		}
		
		return true;
	}
	
	public boolean commandUser(User user, String command, Message message) {
		Object[] object = this.getCommand(command);
		
		if (object[0] == null || ((SimpleCommand) object[0]).getType() == ExecutorType.CONSOLE)
			return false;
		
		try {
			this.execute(((SimpleCommand)object[0]), command,(String[])object[1], message);
		} catch (Exception e) {
			System.err.println("[ERROR/winterskill.discord.pelican.commands.CommandMap.commandConsole] : La commande " + ((SimpleCommand) object[0]).getMethod().getName() + " n'est pas correctement initialisée!");
		}
		
		return true;
	}
	
	private void execute(SimpleCommand simpleCommand, String command, String[] args, Message message) throws Exception {
		Parameter[] parameters = simpleCommand.getMethod().getParameters();
		Object[] objects = new Object[parameters.length];
		
		for (int i = 0; i < parameters.length; i++) {
			if (parameters[i].getType() == String[].class)
				objects[i] = args;
			else if (parameters[i].getType() == User.class)
				objects[i] = (message == null) ? null : message.getAuthor();
			else if (parameters[i].getType() == TextChannel.class)
				objects[i] = (message == null) ? null : message.getTextChannel();
			else if (parameters[i].getType() == PrivateChannel.class)
				objects[i] = (message == null) ? null : message.getPrivateChannel();
			else if (parameters[i].getType() == Guild.class)
				objects[i] = (message == null) ? null : message.getGuild();
			else if (parameters[i].getType() == String.class)
				objects[i] = command;
			else if (parameters[i].getType() == Message.class)
				objects[i] = message;
			else if (parameters[i].getType() == JDA.class)
				objects[i] = this.main.getJDA();
			else if (parameters[i].getType() == MessageChannel.class)
				objects[i] = (message == null) ? null : message.getChannel();
		}
		
		simpleCommand.getMethod().invoke(simpleCommand.getObject(), objects);
	}
}
