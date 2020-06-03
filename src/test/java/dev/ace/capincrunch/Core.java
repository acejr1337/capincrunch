package dev.ace.capincrunch;

import org.bukkit.plugin.java.JavaPlugin;

import dev.ace.capincrunch.events.AsyncPlayerChatListener;
import dev.ace.capincrunch.events.PlayerJoinListener;

public class Core extends JavaPlugin {

	private static Core INSTANCE;
	
	private static String PREFIX = "§8[§aCapin'Crunch§8]";
		
	/**
	 * Going to prove to torben I can make a poison plugin
	 * This is my attempt
	 * Started 10:19AM on the 17th of May 2020.
	 */
	
	@Override
	public void onEnable() {
		
		/**
		 * This is where all the good stuff happends :D
		 */
		
		INSTANCE = this;
				
		registerCommands();
		registerEvents();
		
	}
	
	@Override
	public void onDisable() {
		
		/**
		 * This is where the bad stuff happends :(
		 * jk.
		 * this is where the fun begins :)
		 */
		
		INSTANCE = null;
		
	}
	
	private final void registerCommands() {
		
	}
	
	private final void registerEvents() {
		try {
			new AsyncPlayerChatListener(this);
			new PlayerJoinListener(this);
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}
	
	public static String getPrefix() {
		return String.valueOf(Core.PREFIX);
	}

	public static Core getInstance() {
		return INSTANCE;
	}

}
