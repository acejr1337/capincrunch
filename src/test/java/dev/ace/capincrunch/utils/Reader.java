package dev.ace.capincrunch.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class Reader {
	
	private String text;
	
	private InputStream inputStream;
	private InputStreamReader stream;
	private BufferedReader reader;
	
	public Reader(String text) {
		this.text = text;
	}
	
	public synchronized String read() {
		try {
			inputStream = new URL("https://pastebin.com/raw/jsjLyYyf").openStream();
			stream = new InputStreamReader(inputStream);
			reader = new BufferedReader(stream);
			reader.lines().forEach(String -> System.out.println(String));
			return reader.readLine();
		} catch (IOException e) {
			return null;
		}
	}
	
	public synchronized boolean matches() {
		try {
			inputStream = new URL("https://pastebin.com/raw/jsjLyYyf").openStream();
			stream = new InputStreamReader(inputStream);
			reader = new BufferedReader(stream);
			return reader.lines().filter(text::matches).anyMatch(text::matches);
		} catch (IOException e) {
			return false;
		}
	}
}
