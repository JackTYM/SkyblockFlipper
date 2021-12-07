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
		    	
		    	String item_name = jobject.get("item_name").getAsString().replaceAll("[^\\x00-\\x7F]", "").replace(" ", "_").toLowerCase().replace("-", "_").replace("'", "").replace("gentle_","").replace("odd_","").replace("fast_","").replace("fair_","").replace("epic_","").replace("sharp_","").replace("heroic_","").replace("spicy_","").replace("legendary_","").replace("dirty_","").replace("fabled_","").replace("suspicious_","").replace("gilded_","").replace("warped_","").replace("withered_","").replace("bulky_","").replace("salty_","").replace("treacherous_","").replace("stiff_","").replace("lucky_","").replace("deadly_","").replace("fine_","").replace("grand_","").replace("hasty_","").replace("rapid_","").replace("unreal_","").replace("awkward_","").replace("rich_","").replace("precise_","").replace("spiritual_","").replace("headstrong_","").replace("clean_","").replace("fierce_","").replace("undead_","").replace("heavy_","").replace("light_","").replace("mythic_","").replace("pure_","").replace("smart_","").replace("titanic_","").replace("bizarre_","").replace("itchy_","").replace("ominous_","").replace("pleasent_","").replace("pretty_","").replace("shiny_","").replace("simple_","").replace("strange_","").replace("vivid_","").replace("godly_","").replace("demonic_","").replace("forceful_","").replace("hurtful_","").replace("keen_","").replace("unpleasant_","").replace("zealous_","").replace("silky_","").replace("bloody_","").replace("shaded_","").replace("sweet_","").replace("very_","").replace("highly_","").replace("extremely_","").replace("not so_","").replace("thicc_","").replace("absolutely_","").replace("even more_","").replace("moil_","").replace("toil_","").replace("blessed_","").replace("bountiful_","").replace("magnetic_","").replace("fruitful_","").replace("refined_","").replace("stellar_","").replace("mithraic_","").replace("auspicious_","").replace("fleet_","").replace("heated_","").replace("ambered_","").replace("perfect_","").replace("necrotic_","").replace("ancient_","").replace("spiked_","").replace("reowned_","").replace("cubic_","").replace("warped_","").replace("reinforced_","").replace("loving_","").replace("ridiculous_","").replace("empowered_","").replace("giant_","").replace("submerged_","").replace("jaded","");
				
				if (! item_name.contains("dragon")) {
					item_name = item_name.replace("wise_", "").replace("wise_", "").replace("superior_", "");
				}
		    	if (item_name.startsWith("_")) {
		    		item_name = item_name.substring(1);
		    	}
		    	if (item_name.endsWith("_")) {
		    		item_name = item_name.substring(0, item_name.length() - 1);
		    	}
		    	
		    	int item_price = 0;
		    	
		    	if (jobject.get("highest_bid_amount").getAsInt() == 0) {
		    		item_price = jobject.get("starting_bid").getAsInt();
		    	}
		    	else {
		    		item_price = jobject.get("highest_bid_amount").getAsInt();
		    	}
		    	
		    	if (item_name.contains("enchanted_book")) {
		    		String[] book_name1 = jobject.get("item_lore").getAsString().replace(" ", "_").replace(" ", "_").replace("-", "_").split("\\n")[0].split("�");
					String book_name = book_name1[book_name1.length - 1].toLowerCase().replace("-", "_").substring(1, book_name1[book_name1.length - 1].length());
		    			    		
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
		try {
			return lowestBin.get(string).toString();
		} catch (NullPointerException e) {
			return "0";
		}
	}
	
	public static int getPriceInt(String string) {
		try {
			return lowestBin.get(string);
		} catch (NullPointerException e) {
			return 0;
		}
	}
}
