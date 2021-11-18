package me.jacktym.skyblockflipper.commands;

import me.jacktym.skyblockflipper.api.LowestBIN;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class PriceCalculator extends CommandBase {

	@Override
	public String func_71517_b() {
		return "pricecalculate";
	}

	@Override
	public String func_71518_a(ICommandSender sender) {
		return "/pricecalculate item_name enchant_name_level";
	}
	
	@Override
	public int func_82362_a() {
	    return 0;
	  }

	@Override
	public void func_71515_b(ICommandSender sender, String[] args) throws CommandException {
		System.out.println(LowestBIN.lowestBin);
		if (args.length == 0) {
		 	(Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.RED + "Incorrect Usage"));
		    (Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GOLD + "/pricecalculate item_name enchant_name_level extra_information*amount"));
		    (Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.WHITE + "Example: /pricecalculate flower_of_truth ultimate_wise_v hot_potato_book*5 recombobulator_3000"));
		}
		else {
			int itemWorth = calculate(args);
			
			int itemWorthAfterTax = (int) (itemWorth - (itemWorth * 0.01));
			Minecraft.func_71410_x().field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(String.valueOf(itemWorth)));
		}
	}
	
	public int calculate(String[] args) {
		int itemWorth = 0;
		int itemWorthAfterTax = 0;
		for (int i = 0; i < args.length; i++) {
			
			String argument = args[i].toLowerCase();
			
			if (argument.contains("*")) {
				itemWorth = itemWorth + ((LowestBIN.getPriceInt(argument.replace("*", "/").split("/")[0])) * (Integer.parseInt(argument.replace("*", "/").split("/")[1])));
			} else {
			itemWorth = itemWorth + LowestBIN.getPriceInt(argument);
			}
		}
		return itemWorth;
	}

}
