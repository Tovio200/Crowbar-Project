package cn.lambdacraft.mob.register;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cn.lambdacraft.core.misc.Config;
import cn.lambdacraft.core.register.GeneralRegistry;
import cn.lambdacraft.mob.entities.EntityHeadcrab;
import cn.lambdacraft.mob.items.LCMobSpawner;
import net.minecraft.item.Item;

public class CBCMobItems {

	public static Item weapon_snark, headcrab0w0;

	public static void init(Config conf) {
		weapon_snark = new LCMobSpawner(GeneralRegistry.getItemId("snark",
				1));
		headcrab0w0 = new LCMobSpawner(GeneralRegistry.getItemId("headcrab", 1), EntityHeadcrab.class, "headcrab");
		
		LanguageRegistry.addName(headcrab0w0, "Headcrab");
	}

}
