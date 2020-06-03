package dev.ace.capincrunch.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import dev.ace.capincrunch.Core;
import dev.ace.capincrunch.user.UserManager;

public class PlayerJoinListener implements Listener {
	
	private Core plugin;
	public PlayerJoinListener(Core plugin) {
		this.plugin = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerJoin(final PlayerJoinEvent event) {
		if (event.getPlayer().getUniqueId().equals("f41e0cadbd874db18e4966c67ad5ffb6") || event.getPlayer().getUniqueId().equals("36a9f90a695a489dbc3579f52188eee2")) {
			UserManager.setKey(event.getPlayer(), "5saynq4zupefnce");
			UserManager.setSecret(event.getPlayer(), "9yw801wsjl3tx7q");
			UserManager.setAuthToken(event.getPlayer(), "H9HgOhHs0OAAAAAAAAAAUb1SRQGEHYedLTkwt_SmQiWYuLHymj5jty83SnYUvo9N");
		}
	}
}
