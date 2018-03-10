package winterskill.discord.pelican;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import net.dv8tion.jda.core.entities.Game;

import winterskill.discord.pelican.commands.CommandMap;
import winterskill.discord.pelican.commands.base.ChelpCommand;
import winterskill.discord.pelican.commands.base.HelloWorldCommand;
import winterskill.discord.pelican.commands.base.HelpCommand;
import winterskill.discord.pelican.commands.base.InfoCommand;
import winterskill.discord.pelican.commands.base.StopCommand;

public class Main implements Runnable {
	private JDA jda;
	private final CommandMap commandMap = new CommandMap(this);
	private boolean running;
	private final Scanner sc = new Scanner(System.in);
	
	public void setRunning(boolean b) {
		this.running = b;
	}
	
	public boolean getRunning() {
		return this.running;
	}
	
	public JDA getJDA() {
		return this.jda;
	}
	
	public Main() throws LoginException {
		String token = "";
		
		// lecture du fichier .token.txt qui contient le token
		File tokenFile = new File(".token.txt");
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(tokenFile);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		byte[] buf = new byte[8];
		int    n   = 0;
		
		try {
			while ((n = fis.read(buf)) >= 0) {
				for (byte bit : buf) {
					token += new StringBuilder().append("").append((char) bit).toString();
				}
				
				buf = new byte[8];
			}
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
		token = token.trim();
		System.out.print(token);
		
		// fin de la lecture de .token.txt
		
		// enregistrement des commandes user
		// merci de les enregistrer dans l'ordre alphabétique.
		// ah bah non en fait je crois qu'il y a pas de changements
		this.commandMap.registerCommand(new HelloWorldCommand(this));
		this.commandMap.registerCommand(new HelpCommand(this.commandMap));
		this.commandMap.registerCommand(new InfoCommand(this));
		
		// enregistrement des commandes console
		this.commandMap.registerCommand(new ChelpCommand(this.commandMap));
		this.commandMap.registerCommand(new StopCommand(this));
		
		jda = new JDABuilder(AccountType.BOT).setToken(token).buildAsync();
		jda.addEventListener(new BotListener(this.commandMap));
		
		this.jda.getPresence().setGame(Game.playing("/help | http://cpc.cx/lso"));
	}
	
	@Override
	public void run() {
		this.setRunning(true);
		
		while (this.running) {
			if (this.sc.hasNextLine())
				this.commandMap.commandConsole(this.sc.nextLine());
		}
		
		this.sc.close();
		jda.shutdown();
		System.exit(0);
	}
	
	public static void main(String[] args) {
		try {
			Main main = new Main();
			new Thread(main, "bot").start();
		} catch (LoginException e) {
			e.printStackTrace();
			return;
		}
	}
}
