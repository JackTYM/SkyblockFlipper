package me.jacktym.skyblockflipper.commands;

import java.util.ArrayList;
import java.util.List;

import me.jacktym.skyblockflipper.Main;
import me.jacktym.skyblockflipper.api.ApiKeyChecker;
import me.jacktym.skyblockflipper.api.AuctionChecker;
import me.jacktym.skyblockflipper.api.LowestBIN;
import me.jacktym.skyblockflipper.config.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent.Action;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class SBF extends CommandBase {
	
	public static Boolean inWorld = false;

	public String func_71517_b() {
		return "sbf";
	}
	
	public List func_71514_a() {
		List al = new ArrayList<String>();
		al.add("skyblockflipper");
		return al;
	}

	public String func_71518_a(ICommandSender sender) {
		return "/sbf help";
	}
	
	public int func_82362_a() {
	    return 0;
	  }
	
	public void func_71515_b(ICommandSender sender, String[] args) throws CommandException {
		if (args.length == 0 || args[0].contains("help")) {
			(Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GRAY + "SkyblockFlipper Help Menu"));
		 	(Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GRAY + "/sbf apikey {key} - Saves New Hypixel API Key to Config"));
		 	(Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GRAY + "/pricecalculate {item_name} {item_name}*{amount}"));
		 	(Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GRAY + "/sbf alerts [add/remove/list/on/off] {item_name}"));
		 	(Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GRAY + "/sbf lowestprofit {number} - Sets the lowest profit for a flip " + EnumChatFormatting.GRAY + "to be shown"));
		 	(Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GRAY + "/sbf alerttype [gui/chat] - Sets the place flip alerts will be " + EnumChatFormatting.GRAY + "shown"));

		} else {
			if (args[0].contains("help")) {
			 	(Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GRAY + "SkyblockFlipper Help Menu"));
			 	(Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GRAY + "/sbf apikey {key} - Saves New Hypixel API Key to Config"));
			 	(Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GRAY + "/pricecalculate {item_name} {item_name}*{amount}"));
			 	(Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GRAY + "/sbf alerts [add/remove/list/on/off] {item_name}"));
			}
			
			if (args[0].contains("apikey")) {
				if (args.length > 1) {
					ConfigHandler.setApiKey(args[1]);
					ConfigHandler.saveConfig();
					(Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.WHITE + "API Key Saved"));
					ApiKeyChecker.checkKey();
				} else {
				 	(Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.RED + "No API Key Provided"));
				}
			}
			
			if (args[0].contains("alerts")) {
				AuctionChecker.refreshItems();
				if (args.length < 2) {
					(Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GRAY + "Sends chat alerts when items are listed on the Auction House"));
					(Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GRAY + "/sbf alerts [add/remove/list/on/off] {item_name}"));
				} else {
					if (args[1].contains("on")) {
						ConfigHandler.setAlertOn(true);
						ConfigHandler.saveConfig();
					} else if (args[1].contains("off")) {
						ConfigHandler.setAlertOn(false);
						ConfigHandler.saveConfig();
					} else if (args[1].contains("add")) {
						if (args.length < 3) {
							(Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GRAY + "Sends chat alerts when items are listed on the Auction House"));
							(Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GRAY + "/sbf alerts [add/remove/list/on/off] {item_name}"));
						} else {
							String alert_item = "";
							
							if (args.length >= 4) {
								alert_item = "[" + args[2] + "," + args[3] + "]";
							} else if(args.length <= 3) {
								alert_item = "[" + args[2] + ",0]";
							}
							
							ConfigHandler.addAlertItems(alert_item);
							
							ConfigHandler.saveConfig();
							
							(Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GRAY + "Added " + args[2] + " to the item alert list."));
						}
					} else if (args[1].contains("remove")) {
						if (args.length < 3) {
							(Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GRAY + "Sends chat alerts when items are listed on the Auction House"));
							(Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GRAY + "/sbf alerts [add/remove/list/on/off] {item_name}"));
						} else {
							String items = ConfigHandler.getAlertItems();
							
							if (items.contains(args[2])) {
								String itemList = items;
								
								String test = "[item_name,max_price], [arg_item,max_price]";
								
								String beforeRemoval = itemList.split((", \\[" + args[2]))[0];
								
								String maxPrice = itemList.split(", \\[" + args[2])[1].split(",")[1].split("\\]")[0];
								
								System.out.println(maxPrice);
								
								if (itemList.split(", \\[" + args[2] + "," + maxPrice + "]").length == 1) {
									itemList = beforeRemoval;
									System.out.println(itemList);
								} else {
									String afterRemoval = itemList.split(", \\[" + args[2] + "," + maxPrice + "\\]")[1];
									
									itemList = beforeRemoval + afterRemoval;
									
									System.out.println(beforeRemoval);
									System.out.println(afterRemoval);
								}
								
								ConfigHandler.setAlertItems(itemList);
								
								ConfigHandler.saveConfig();
								
								(Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GRAY + "Item Alerts Removed For " + args[2]));
							} else {
								(Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GRAY + "No Alert Item Found Under The Name " + args[2]));
							}
						}
					} else if (args[1].contains("list")) {
						String items = ConfigHandler.getAlertItems();
						
						if (items.contains("], ")) {
							(Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GRAY + "Alert Item List:"));
							String[] itemList = items.split("], ");
							
							for (int i = 1; i < itemList.length; i++) {
								
								String item = itemList[i].replace("[", "").replace("]", "").split(",")[0];
								String max_price = itemList[i].replace("[", "").replace("]", "").split(",")[1];
								
								if (! item.contains("item_name")) {
								(Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GRAY + item + " with a max price of " + max_price));
								}
							}
						} else {
							(Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GRAY + "Alert Item List Empty"));
						}
					} else {
						(Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.RED + "Invalid arguments."));
						(Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GRAY + "/sbf alerts [add/remove/list/on/off] {item_name} {max_price)"));
					}
				}
			}
		}
	}
	
	public static void chatAlert(String item_name, int item_price, String uuid, int worth) {
		new Thread(() -> {
			String itemPriceString = "";
			String worthString = "";
			try {
				if (item_price > 999) {
					int exp = (int) (Math.log(item_price) / Math.log(1000));
					itemPriceString = String.format("%.1f%c", item_price / Math.pow(1000, exp), "kM".charAt(exp-1));
				} if (item_price < 999) {
					itemPriceString = String.valueOf(item_price);
				}
				if (worth > 999) {
					int exp2 = (int) (Math.log(worth) / Math.log(1000));
					worthString = String.format("%.1f%c", worth / Math.pow(1000, exp2), "kM".charAt(exp2-1));
				} if (item_price < 999) {
					worthString = String.valueOf(worth);
				}
				if (ConfigHandler.getAlertOn() == true) {
					IChatComponent clickableMessage = new ChatComponentText(EnumChatFormatting.BLUE + " " + EnumChatFormatting.BLUE + "C" + EnumChatFormatting.BLUE + "l" + EnumChatFormatting.BLUE + "i" + EnumChatFormatting.BLUE + "c" + EnumChatFormatting.BLUE + "k" + EnumChatFormatting.BLUE + "!");
					ChatStyle clickable = new ChatStyle().setChatClickEvent(new ClickEvent(Action.RUN_COMMAND, "/viewauction " + uuid));
					
					clickableMessage.setChatStyle(clickable);
					String messageString = (item_name.replace("_", " ") + " | " + itemPriceString + " | Worth " + worthString);
					IChatComponent messageChat = new ChatComponentText(EnumChatFormatting.GRAY + "");
					for (int i = 0; i <= messageString.length() - 1; i++) {
						messageChat.appendText(EnumChatFormatting.GRAY + messageString.substring(i, i + 1));
						//System.out.println(messageString.substring(i - 1, i + 1));
					}
					Minecraft.getMinecraft().thePlayer.addChatMessage(messageChat.appendSibling(clickableMessage));
				}
			} catch (NullPointerException e) {
				
			}
			return;
		}).start();
	}

}
