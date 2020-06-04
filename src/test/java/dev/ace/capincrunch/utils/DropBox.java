package dev.ace.capincrunch.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Locale;

import org.bukkit.entity.Player;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.util.IOUtil;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.WriteMode;

import dev.ace.capincrunch.Core;

public class DropBox {

	private String key;
	private String secret;
	private String authToken;
	private DbxClientV2 dbxClient;
	private Player player;
	
	@SuppressWarnings("deprecation")
	public DropBox(Player player, String key, String secret, String authToken) {
		this.key = key;
		this.secret = secret;
		this.authToken = authToken;
		this.player = player;
		
		try {
			DbxAppInfo dbxAppInfo = new DbxAppInfo(key, secret);
			DbxRequestConfig dbxRequestConfig = new DbxRequestConfig("Request", Locale.getDefault().toString());
			//DbxWebAuthNoRedirect dbxWebAuthNoRedirect = new DbxWebAuthNoRedirect(dbxRequestConfig, dbxAppInfo);
			//String authorizeUrl = dbxWebAuthNoRedirect.start();
			//player.sendMessage(Core.getPrefix() + " §6Please go to URL and click Allow:" + authorizeUrl);
			//player.sendMessage(Core.getPrefix() + " §6Once you have the code, please type !!authorize [code]");
			//String dropboxAuthCode = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
			//DbxAuthFinish authFinish = dbxWebAuthNoRedirect.finish(dropboxAuthCode);
			//String authAccessToken = authFinish.getAccessToken();
			dbxClient = new DbxClientV2(dbxRequestConfig, authToken);
		} catch (Exception e) {
			player.sendMessage(C.translate(Core.getPrefix() + " §6Invalid authorization please check your details!"));
		}
	}
	public FileMetadata uploadFile(File file) throws Exception {
		try {
			InputStream is = new FileInputStream(file);
			try {
				FileMetadata fileMetadata = dbxClient.files().uploadBuilder("/" + file.getName()).withMode(WriteMode.ADD).uploadAndFinish(is);
				player.sendMessage(Core.getPrefix() + " §6Uploaded complete!");
				return fileMetadata;
			} finally {
				IOUtil.closeInput(is);
			}
		} catch (Exception e) {
			player.sendMessage(Core.getPrefix() + " §6Upload failed, please try again!");
		}
		return null;
	}
	public String getKey() {
		return key;
	}
	public String getSecret() {
		return secret;
	}
	public String getAuthToken() {
		return authToken;
	}
	public String getEmail() {
		try {
			return dbxClient.users().getCurrentAccount().getEmail();
		} catch (DbxException e) {
			return "§6No Email Present!";
		}
	}
	public String getCountry() {
		try {
			return dbxClient.users().getCurrentAccount().getCountry();
		} catch (DbxException e) {
			return "§6No Country Present!";
		}
	}
}
