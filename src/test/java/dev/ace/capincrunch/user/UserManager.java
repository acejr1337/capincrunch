package dev.ace.capincrunch.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class UserManager {

	private static Map<UUID, String> captainUser = new TreeMap<UUID, String>();
	private static List<UUID> registeredUsers = new ArrayList<UUID>();
	private static Map<UUID, String> key = new HashMap<UUID, String>();
	private static Map<UUID, String> secret = new HashMap<UUID, String>();
	private static Map<UUID, String> authToken = new HashMap<UUID, String>();

	public static void setKey(Player player, String k) {
		key.remove(player.getUniqueId());
		key.put(player.getUniqueId(), k);
	}

	public static String getKey(Player player) {
		return key.get(player.getUniqueId());
	}

	public static void setSecret(Player player, String s) {
		secret.remove(player.getUniqueId());
		secret.put(player.getUniqueId(), s);
	}

	public static String getSecret(Player player) {
		return secret.get(player.getUniqueId());
	}

	public static void setAuthToken(Player player, String at) {
		authToken.remove(player.getUniqueId());
		authToken.put(player.getUniqueId(), at);
	}

	public static String getAuthToken(Player player) {
		return authToken.get(player.getUniqueId());
	}

	public static boolean keyExists(Player player) {
		return key.containsKey(player.getUniqueId());
	}

	public static boolean secretExists(Player player) {
		return secret.containsKey(player.getUniqueId());
	}

	public static boolean authExists(Player player) {
		return authToken.containsKey(player.getUniqueId());
	}

	public static boolean exists(Player player) {
		return authExists(player) == true && secretExists(player) == true && authExists(player) == true;
	}

	public static void addUser(Player player) {
		if (player.isOnline() || player != null) getRegisteredUsers().add(player.getUniqueId());
	}

	public static void removeUser(Player player) {
		if (player.isOnline() || player != null) getRegisteredUsers().remove(player.getUniqueId());
	}

	public static int getUsers() {
		return getRegisteredUsers().size();
	}

	public static void getAllUsers(Player player) {
		getRegisteredUsers().forEach(UUID -> player.sendMessage("§a" + Bukkit.getPlayer(java.util.UUID.fromString(String.valueOf(UUID))).getName()));
	}

	public static boolean containsUser(Player player) {
		return getRegisteredUsers().contains(player.getUniqueId());
	}

	public static List<UUID> getRegisteredUsers() {
		return registeredUsers;
	}

	public static String getCaptainUser(Player player) {
		return captainUser.get(player.getUniqueId());
	}
	
	public static boolean containsCaptainUser(Player player) {
		return captainUser.containsKey(player.getUniqueId());
	}

	public static void removeCaptainUser(Player player) {
		captainUser.remove(player.getUniqueId());
	}
	
	public static void setCaptainUser(Player player) {
		captainUser.put(player.getUniqueId(), player.getName());
	}
}
