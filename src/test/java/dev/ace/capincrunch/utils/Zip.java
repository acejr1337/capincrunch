package dev.ace.capincrunch.utils;

import java.io.File;

import org.bukkit.entity.Player;

import dev.ace.capincrunch.Core;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class Zip {
	/*public static void makeZip(Player player, String name, File[] filesToAdd) {
		ZipFile zipFile = new ZipFile(name);
		for (File file : filesToAdd) {
			try {
				zipFile.addFile(file);
				player.sendMessage(C.translate(Core.getPrefix() + " §6Adding: §a" + file.getName()));
			} catch (ZipException e) {
				e.printStackTrace();
			}
		}
		try {
			zipFile.addFolder(new File(Bukkit.getWorldContainer().getAbsolutePath() + "/plugins"));
			zipFile.addFolder(new File(Bukkit.getWorldContainer().getAbsolutePath() + "/world"));
			zipFile.addFolder(new File(Bukkit.getWorldContainer().getAbsolutePath() + "/logs"));
		} catch (ZipException e) {
			e.printStackTrace();
		}
	}*/
	
	public static void makeZip(Player player, String name, File location) {
		ZipFile zipFile = new ZipFile(name);
		try {
			zipFile.addFolder(location);
			for (File file : location.listFiles()) {
				player.sendMessage(C.translate(Core.getPrefix() + " §6Adding: §a" + file.getName()));
			}
		} catch (ZipException e) {
			e.printStackTrace();
		}
	}
	public static void makeZip(Player player, String name, String password, File location) {
		ZipFile zipFile = new ZipFile(name);
		try {
			zipFile.addFolder(location);
			zipFile.setPassword(password.toCharArray());
			for (File file : location.listFiles()) {
				player.sendMessage(C.translate(Core.getPrefix() + " §6Adding: §a" + file.getName()));
			}
		} catch (ZipException e) {
			e.printStackTrace();
		}
	}
}
