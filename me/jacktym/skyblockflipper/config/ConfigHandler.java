package me.jacktym.skyblockflipper.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigHandler {
	
	public static Configuration config;
	
	private static Property apiKey;
	
	private static Property alertItems;
	
	private static Property alertOn;
	
	private static Property lowestProfit;
	
	private static Property developerMode;
	
	public static void init(File file) {
		config = new Configuration(file);
		config.load();
		String category = "API";
		config.addCustomCategoryComment(category, "API Settings");
		apiKey = config.get(category, "apiKey", "abcd", "The Hypixel API Key used to make requests (Get using /api new)");
		
		category = "Item Alerts";
		config.addCustomCategoryComment(category, "Chat alerts for specific items under a certain price");
		alertItems = config.get(category, "alertItems", "[item_name,max_price]");
		alertOn = config.get(category, "alertBoolean", true);
		
		category = "Flipping Settings";
		lowestProfit = config.get(category, "lowestProfit", 100);
		
		category = "Developer Settings";
		developerMode = config.get(category, "developerMode", false);
		
		config.save();
	}
	
	public static String getApiKey() {
//		return apiKey.getString();
		return "7aa3e5de-cd10-442e-9283-081c2657592c";
	}
	
	public static void setApiKey(String value) {
		apiKey.set(value);
	}
	
	public static String getAlertItems() {
		return alertItems.getString();
	}
	
	public static void addAlertItems(String value) {
		alertItems.set(alertItems.getString() + ", " + value);
	}
	
	public static void setAlertItems(String value) {
		alertItems.set(value);
	}
	
	public static boolean getAlertOn() {
		return alertOn.getBoolean();
	}
	
	public static void setAlertOn(boolean value) {
		alertOn.set(value);
	}
	
	public static void saveConfig() {
	    config.save();
	  }
	
	public static void setLowestProfit(int value) {
		lowestProfit.set(value);
	}
	
	public static int getLowestProfits() {
		return lowestProfit.getInt();
	}
	
	public static void setDeveloperMode(boolean value) {
		developerMode.set(value);
	}
	public static boolean getDeveloperMode() {
		return developerMode.getBoolean();
	}
}
