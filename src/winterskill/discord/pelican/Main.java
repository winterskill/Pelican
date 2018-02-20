package winterskill.discord.pelican;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

public class Main {
	public static JDA jda;
	
	public static void main(String[] args) {
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
		
		try {
			jda = new JDABuilder(AccountType.BOT).setToken(token).buildAsync();
			jda.addEventListener(new BotListener());
		} catch (LoginException e) {
			e.printStackTrace();
			return;
		}
	}
}
