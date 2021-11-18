package me.jacktym.skyblockflipper.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.javafx.scene.paint.GradientUtils.Parser;

import me.jacktym.skyblockflipper.Main;
import me.jacktym.skyblockflipper.commands.SBF;
import me.jacktym.skyblockflipper.config.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent.Action;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class AuctionChecker {

	static String apiKey = ConfigHandler.getApiKey();
	static HashMap<String, String> notificationItems = new HashMap<String, String>();
	static int binRefresh = 0; 
	static boolean binRefreshQueue = true;
	static String totalArguments = "";
	static String lastMethod = "";
	static boolean sameMethod = false;
	static List updatedTimes = new ArrayList<Long>();
	public static List uuids = new ArrayList<String>();

	public static void refreshItems() {
		String items = ConfigHandler.getAlertItems();

		if (items.contains("], ")) {
			String[] itemList = items.split("], ");

			for (int i = 1; i < itemList.length; i++) {

				String item = itemList[i].replace("[", "").replace("]", "").split(",")[0];
				String max_price = itemList[i].replace("[", "").replace("]", "").split(",")[1];

				if (! item.contains("item_name")) {
					notificationItems.put(item, max_price);
				}
			}
		}
	}

	public static void main() {
		URL auctionUrl = null;
		HttpURLConnection connection = null;
		InputStream response = null;
		String responsebody = "";
		JsonElement jelement = null;
		JsonObject  jobject = null;
		JsonArray auctions = null;
		int pages = 0;
		long lastUpdated = 0;
		try {
			auctionUrl = new URL("https://api.hypixel.net/skyblock/auctions");
		} catch (MalformedURLException e2) {
			e2.printStackTrace();
		}

		try {
			connection = (HttpURLConnection) auctionUrl.openConnection();
			connection.setRequestProperty("API-Key", apiKey);
			response = connection.getInputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try (Scanner scanner = new Scanner(response)) {

			responsebody = scanner.useDelimiter("\\A").next();

			jelement = new JsonParser().parse(responsebody);
			jobject = jelement.getAsJsonObject();

			pages = jobject.get("totalPages").getAsInt();
			lastUpdated = jobject.get("lastUpdated").getAsLong();

			auctions = jobject.getAsJsonArray("auctions");

			refreshItems();

			String result = "";

			if (binRefreshQueue == true) {

				for (int x = 1; x < pages; x++) {

					URL auctionUrl2 = null;
					HttpURLConnection connection2 = null;
					InputStream response2 = null;
					String responsebody2 = "";
					JsonElement jelement2 = null;
					JsonObject  jobject2 = null;
					JsonArray auctions2 = null;
					try {
						auctionUrl2 = new URL("https://api.hypixel.net/skyblock/auctions");
					} catch (MalformedURLException e2) {
						e2.printStackTrace();
					}

					try {
						connection2 = (HttpURLConnection) auctionUrl2.openConnection();
						connection2.setRequestProperty("API-Key", apiKey);
						connection2.setRequestProperty("pages", Integer.toString(x));
						response2 = connection2.getInputStream();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					try (Scanner scanner2 = new Scanner(response2)) {

						responsebody2 = scanner2.useDelimiter("\\A").next();

						jelement2 = new JsonParser().parse(responsebody2);
						jobject2 = jelement2.getAsJsonObject();

						auctions2 = jobject2.getAsJsonArray("auctions");

						LowestBIN.parse(auctions2);
					}
				}
			}
			for (int z = 0; z < (auctions.size()); z++) {
				jelement = auctions.get(z);

				jobject = jelement.getAsJsonObject();

				int item_price = 0;

				if (jobject.get("highest_bid_amount").getAsInt() == 0) {
					item_price = jobject.get("starting_bid").getAsInt();
				}
				else {
					item_price = jobject.get("highest_bid_amount").getAsInt();
				}

				String item_name = jobject.get("item_name").getAsString().replace(" ", "_").toLowerCase().replace("-", "_").replaceAll("[^\\x00-\\x7F]", "").replace("_ ", "").replace(" _", "").replace("__", "_").replace("__", "_").replace("'", "");

				if (String.valueOf(item_name.charAt(item_name.length()-1)).contains("_")) {
					item_name = item_name.substring(0, item_name.length() - 1);
				}

				if (String.valueOf(item_name.charAt(0)).contains("_")) {
					item_name = item_name.substring(1, item_name.length());
				}

				String uuid = jobject.get("uuid").getAsString();

				if (item_name.contains("enchanted_book") || item_name.contains("enchantedbook")) {
					String[] book_name1 = jobject.get("item_lore").getAsString().replace(" ", "_").replace(" ", "_").replace("-", "_").split("\\n")[0].split("�");
					String book_name = book_name1[book_name1.length - 1].toLowerCase().replace("-", "_").substring(1);

					item_name = book_name;
				}

				if (item_name.contains("[lvl")) {
					String rarity = jobject.get("tier").getAsString().toLowerCase();

					item_name = rarity + "_" + item_name;
				}

				int item_worth = LowestBIN.getPriceInt(item_name);
				
				if (item_worth == 0) {
					LowestBIN.lowestBin.put(item_name, item_price);
				}
				
				if (ConfigHandler.getDeveloperMode()) {
					SBF.chatAlert(item_name, item_price, uuid, item_worth);
				}

				if (! uuids.contains(uuid)) {
					uuids.add(uuid);
					if (notificationItems.keySet().contains(item_name)) {
						if (Integer.parseInt(notificationItems.get(item_name)) <= item_price || Integer.parseInt(notificationItems.get(item_name)) == 0) {
							SBF.chatAlert(item_name, item_price, uuid, item_worth);
						}
					}
					if (LowestBIN.getPriceInt(item_name) > item_price && ConfigHandler.getLowestProfits() <= (item_price - LowestBIN.getPriceInt(item_name))) {
						SBF.chatAlert(item_name, item_price, uuid, item_worth);
					}
				}
			}
			if (binRefreshQueue == true) {
				binRefreshQueue = false;
			}
		}
		timeKeep();
		return;
	}

	public static void timeKeeper(long lastUpdated, long currentTime) {
		if (! updatedTimes.contains(lastUpdated)) {
			updatedTimes.add(lastUpdated);
			while (true) {
				if ((lastUpdated + 61000) <= System.currentTimeMillis() || (currentTime + 60000) <= System.currentTimeMillis()) {
					main();

					binRefresh++;

					if (binRefresh >= 30) {
						binRefresh = 0;

						binRefreshQueue = true;
					}
					return;
				}
			}
		} else {
			return;
		}
	}

	public static void timeKeep() {
		new Thread(() -> {
			URL auctionUrl = null;
			HttpURLConnection connection = null;
			InputStream response = null;
			String responsebody = "";
			JsonElement jelement = null;
			JsonObject  jobject = null;

			long lastUpdated = 0;
			try {
				auctionUrl = new URL("https://api.hypixel.net/skyblock/auctions");
			} catch (MalformedURLException e2) {
				e2.printStackTrace();
			}

			try {
				connection = (HttpURLConnection) auctionUrl.openConnection();
				connection.setRequestProperty("API-Key", apiKey);
				response = connection.getInputStream();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			try (Scanner scanner = new Scanner(response)) {

				responsebody = scanner.useDelimiter("\\A").next();

				jelement = new JsonParser().parse(responsebody);
				jobject = jelement.getAsJsonObject();

				lastUpdated = jobject.get("lastUpdated").getAsLong();
			}

			while (true) {
				if ((lastUpdated + 61000) <= System.currentTimeMillis()) {
					new Thread(() -> {
						main();			
					}).start();
					binRefresh++;

					if (binRefresh >= 30) {
						binRefresh = 0;

						binRefreshQueue = true;
					}
					return;
				}
			}
		}).start();
	}

}
