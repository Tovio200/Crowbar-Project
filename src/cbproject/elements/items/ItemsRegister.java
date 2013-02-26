package cbproject.elements.items;

import cbproject.configure.Config;
import cbproject.elements.items.ammos.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cbproject.elements.items.weapons.*;

public class ItemsRegister {
	
	public static Weapon_crowbar weapon_crowbar;
	public static Weapon_hgrenade weapon_hgrenade;
	
	public static Weapon_gauss weapon_gauss;
	public static Weapon_egon weapon_egon;
	public static Weapon_9mmhandgun weapon_9mmhandgun;
	public static Weapon_9mmAR weapon_9mmAR;
	
	public static ItemAmmo_uranium itemAmmo_uranium;
	public static ItemAmmo_9mm itemAmmo_9mm;
	
	public ItemsRegister(Config conf){
		registerItems(conf);
		return;
	}
	
	public void registerItems(Config conf){
		//TODO:添加调用config的部分
		
		itemAmmo_uranium = new ItemAmmo_uranium(10004);
		itemAmmo_9mm = new ItemAmmo_9mm(10005);
		
		weapon_crowbar = new Weapon_crowbar(10001);
		weapon_hgrenade = new Weapon_hgrenade(10002);
		weapon_9mmhandgun = new Weapon_9mmhandgun(10003);
		

		LanguageRegistry.addName(itemAmmo_9mm, "9mm Ammo");
		LanguageRegistry.addName(itemAmmo_uranium , "Uranium Ammo");
		
        LanguageRegistry.addName(weapon_crowbar, "Crowbar");
        LanguageRegistry.addName(weapon_hgrenade, "Hand Grenade");
        LanguageRegistry.addName(weapon_9mmhandgun , "9mm Handgun");
        
        
        //合成表：撬棍
        ItemStack rosereddyeStack = new ItemStack(351,1,0);
        ItemStack ingotIronStack = new ItemStack(Item.ingotIron);
        ItemStack crowbarStack = new ItemStack(weapon_crowbar);
        ModLoader.addShapelessRecipe(crowbarStack, rosereddyeStack);
        GameRegistry.addShapelessRecipe(crowbarStack, rosereddyeStack,ingotIronStack);
        //End Crowbar
        
        
		return;
	}
}