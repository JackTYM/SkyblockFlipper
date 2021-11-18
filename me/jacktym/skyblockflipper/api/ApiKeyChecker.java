package me.jacktym.skyblockflipper.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import me.jacktym.skyblockflipper.config.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class ApiKeyChecker {
	
	public static void checkKey() {
		
		String apiKey = ConfigHandler.getApiKey();
		
		URL keyUrl = null;
		HttpURLConnection connection = null;
		InputStream response = null;
		try {
			keyUrl = new URL("https://api.hypixel.net/key");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		try {
			connection = (HttpURLConnection) keyUrl.openConnection();
			connection.setRequestProperty("API-Key", apiKey);
			response = connection.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String responsebody;
		
		try (Scanner scanner = new Scanner(response)) {
			responsebody = scanner.useDelimiter("\\A").next();
			
			String result = parse(responsebody);
			
			if (result == "false") {
				(Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.RED + "Invalid API Key. Please do /api new or Add one via /sbf apikey {key}"));
			}
		}
		
	}
	
	public static String parse(String jsonLine) {
	    JsonElement jelement = new JsonParser().parse(jsonLine);
	    JsonObject  jobject = jelement.getAsJsonObject();
	    
	    String result = jobject.get("success").toString();
	    return result;
	}
	
	
	
}
