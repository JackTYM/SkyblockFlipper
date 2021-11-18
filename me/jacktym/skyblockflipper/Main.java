package me.jacktym.skyblockflipper;

import java.io.File;

import me.jacktym.skyblockflipper.api.AuctionChecker;
import me.jacktym.skyblockflipper.commands.PriceCalculator;
import me.jacktym.skyblockflipper.commands.SBF;
import me.jacktym.skyblockflipper.config.ConfigHandler;
import me.jacktym.skyblockflipper.listeners.OnChatReceive;
import me.jacktym.skyblockflipper.proxy.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.command.ICommand;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod(modid = NGGlobal.MOD_ID, name = NGGlobal.MOD_NAME, version = NGGlobal.VERSION)
public class Main {
	
	@Instance(NGGlobal.MOD_ID)
	public static Main instance;
	
	@SidedProxy(clientSide = NGGlobal.NG_CLIENT_PROXY, serverSide = NGGlobal.NG_COMMON_PROXY)
	public static CommonProxy proxy;
	
	public static boolean mainGuiOpened;
	
	public static int ticks = 0;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent preEvent) {
		
		this.proxy.preInit(preEvent);
		
		File configDir = new File(preEvent.getModConfigurationDirectory() + "/SkyblockFlipper");
	    configDir.mkdirs();
	    System.out.println(configDir.getAbsolutePath());
	    ConfigHandler.init(new File(configDir.getPath(), "SkyblockFlipper.cfg"));
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		this.proxy.init(event);
		
		ClientCommandHandler.instance.func_71560_a((ICommand) new PriceCalculator());
		ClientCommandHandler.instance.func_71560_a((ICommand) new SBF());
		
		MinecraftForge.EVENT_BUS.register(this);
		
		MinecraftForge.EVENT_BUS.register(new OnChatReceive());
		
		MinecraftForge.EVENT_BUS.register(new AuctionChecker());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent postEvent) {
		
		this.proxy.postInit(postEvent);
		
		new Thread(() -> {
			AuctionChecker.main();
		}).start();
	}

}
