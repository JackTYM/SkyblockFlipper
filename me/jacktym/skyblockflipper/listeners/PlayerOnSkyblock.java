package me.jacktym.skyblockflipper.listeners;

import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;

public class PlayerOnSkyblock {
	
	public boolean onSkyblock;
	
	public boolean isOnSkyblock() {
		return onSkyblock;
	}
	
	public void updateScoreboard() {
		if (Minecraft.func_71410_x() != null && Minecraft.func_71410_x().field_71441_e != null && Minecraft.func_71410_x().field_71439_g != null) {
			if (Minecraft.func_71410_x().func_71356_B() || Minecraft.func_71410_x().field_71439_g.func_142021_k() == null || !Minecraft.func_71410_x().field_71439_g.func_142021_k().toLowerCase().contains("hypixel")) {
				onSkyblock = false;
			}
			Scoreboard sb = Minecraft.func_71410_x().field_71441_e.func_96441_U();
			ScoreObjective sbo = sb.func_96539_a(1);
			if (sbo != null) {
				String objective = sbo.func_96678_d().replaceAll("(?i)\\u00A7.", "");
				if (objective.startsWith("SKYBLOCK")) {
					onSkyblock = true;
				}
			}
			onSkyblock = false;
		}
	}

}
