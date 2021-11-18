package me.jacktym.skyblockflipper.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.jacktym.skyblockflipper.config.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class LowestBIN {
	
	public static HashMap<String, Integer> lowestBin = new HashMap<String, Integer>();
	
	public static void parse(JsonArray auctions) {
		new Thread(() -> {
		    String result = "";
		    for (int i = 0; i < (auctions.size()); i++) {
		    	JsonObject jobject = auctions.get(i).getAsJsonObject();
		    	
		    	String item_name = jobject.get("item_name").getAsString().replace(" ", "_").toLowerCase().replace("-", "_").replace("'", "");
		    	
		    	int item_price = 0;
		    	
		    	if (jobject.get("highest_bid_amount").getAsInt() == 0) {
		    		item_price = jobject.get("starting_bid").getAsInt();
		    	}
		    	else {
		    		item_price = jobject.get("highest_bid_amount").getAsInt();
		    	}
		    	
		    	if (item_name.contains("enchanted_book")) {
		    		String[] book_name1 = jobject.get("item_lore").getAsString().replace(" ", "_").replace(" ", "_").replace("-", "_").split("\\n")[0].split("�");
		    		String book_name = book_name1[book_name1.length - 1].toLowerCase().replace("-", "_").substring(1);
		    			    		
		    		item_name = book_name;
		    	}
		    	
		    	if (item_name.contains("[lvl")) {
					String rarity = jobject.get("tier").getAsString().toLowerCase();

					item_name = rarity + "_" + item_name;
				}
		    	
		    	if (lowestBin.keySet().contains(item_name)) {
		    		if (lowestBin.get(item_name) >= item_price) {
		    			lowestBin.put(item_name, item_price);
		    		}
		    	}
		    	else {
	    			lowestBin.put(item_name, item_price);
		    	}
		    }
		    return;
		}).start();
	}
	
	public static String getPriceString(String string) {
		if (lowestBin.keySet().contains(string)) {
			return lowestBin.get(string).toString();
		} else {
			return "0";
		}
	}
	
	public static int getPriceInt(String string) {
		if (lowestBin.keySet().contains(string)) {
			return lowestBin.get(string);
		} else 
			return 0;
	}
}
