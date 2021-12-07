package me.jacktym.skyblockflipper.listeners;

import me.jacktym.skyblockflipper.api.ApiKeyChecker;
import me.jacktym.skyblockflipper.config.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class OnChatReceive {
	
	@SubscribeEvent
	public void onChatReceive(ClientChatReceivedEvent event) {
		String unformattedText = event.message.func_150260_c();
		String formattedText = event.message.func_150260_c();
		
		if (unformattedText.startsWith("Your new API key is ")) {
			ConfigHandler.setApiKey(unformattedText.split("Your new API key is ")[1]);
			Minecraft.func_71410_x().field_71439_g.func_145747_a((IChatComponent) new ChatComponentText(EnumChatFormatting.GOLD + "[SBF] API Key Automatically Configured!"));
			ApiKeyChecker.checkKey();
			
			Minecraft.func_71410_x().field_71439_g.func_145747_a((IChatComponent) new ChatComponentText(formattedText));
		}
	}
	

}
