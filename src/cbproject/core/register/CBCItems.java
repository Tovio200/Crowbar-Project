package cbproject.core.register;

import cbproject.core.configure.Config;
import cbproject.crafting.items.*;
import cbproject.crafting.recipes.RecipeWeaponEntry;
import cbproject.crafting.recipes.RecipeWeapons;
import cbproject.deathmatch.items.ArmorHEV;
import cbproject.deathmatch.items.ammos.*;
import cbproject.deathmatch.items.wpns.*;
import net.minecraft.block.Block;
import net.minecraft.command.WrongUsageException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cbproject.core.misc.CBCLanguage;

/**
 * CBC Mod Item Register class.
 * @author WeAthFolD, mkpoli
 *
 */
public class CBCItems {
	
	public static Weapon_crowbar weapon_crowbar;
	public static Weapon_hgrenade weapon_hgrenade;
	
	public static Weapon_gauss weapon_gauss;
	public static Weapon_satchel weapon_satchel;
	public static Item weapon_egon;
	public static Item weapon_9mmhandgun;
	public static Item weapon_9mmAR;
	public static Item weapon_357;
	public static Item weapon_shotgun;
	public static Item weapon_RPG;
	public static Item weapon_crossbow;
	
	public static ItemAmmo_uranium itemAmmo_uranium;
	public static ItemAmmo_9mm itemAmmo_9mm;
	public static ItemAmmo_357 itemAmmo_357;
	public static ItemAmmo_9mm2 itemAmmo_9mm2;
	public static ItemAmmo_bow itemAmmo_bow;
	public static ItemAmmo_RPG itemAmmo_rpg;
	public static ItemAmmo_ARGrenade itemAmmo_ARGrenade;
	
	public static ItemBullet_Shotgun itemBullet_Shotgun;

	public static SteelIngot itemRefinedIronIngot;
	
	public static ItemMaterial mat_accessories, mat_ammunition, mat_bio,
		mat_heavy, mat_light, mat_pistol, mat_tech, mat_explosive, mat_box;
	public static LambdaChipset lambdaChip;
	public static ItemIronBar ironBar;
	
	public static ArmorHEV armorHEVBoot, armorHEVChestplate, armorHEVHelmet, armorHEVLeggings;
	public CBCItems(Config conf){
		registerItems(conf);
		return;
	}
	
	public void registerItems(Config conf){
	
		try{

			itemRefinedIronIngot = new SteelIngot(conf.GetItemID("itemRefinedIronIngot",7100));
			
			itemAmmo_uranium = new ItemAmmo_uranium(conf.GetItemID("itemAmmo_uranium", 7300));
			itemAmmo_9mm = new ItemAmmo_9mm(conf.GetItemID("itemAmmo_9mm", 7301));
			itemAmmo_9mm2 = new ItemAmmo_9mm2(conf.GetItemID("itemAmmo_9mm2", 7302));
			itemAmmo_357 = new ItemAmmo_357(conf.GetItemID("itemAmmo_357", 7303));
			itemAmmo_bow = new ItemAmmo_bow(conf.GetItemID("itemAmmo_bow", 7304));
			itemAmmo_rpg = new ItemAmmo_RPG(conf.GetItemID("itemAmmo_RPG", 7305));
			itemAmmo_ARGrenade = new ItemAmmo_ARGrenade(conf.GetItemID("itemAmmo_ARGrenade", 7306));
			
			itemBullet_Shotgun = new ItemBullet_Shotgun(conf.GetItemID("itemBullet_Shotgun", 7350));
	
			weapon_crowbar = new Weapon_crowbar(conf.GetItemID("weapon_crowbar", 7000));
			
			weapon_hgrenade = new Weapon_hgrenade(conf.GetItemID("weapon_hgrenade", 7001));
			weapon_9mmhandgun = new Weapon_9mmhandgun(conf.GetItemID("weapon_nmmhandgun", 7002));
			weapon_9mmAR = new Weapon_9mmAR(conf.GetItemID("weapon_nmmAR", 7003));
			weapon_357 = new Weapon_357(conf.GetItemID("weapon_357", 7004));
			weapon_shotgun = new Weapon_shotgun(conf.GetItemID("weapon_shotgun", 7005));
			weapon_RPG = new Weapon_RPG(conf.GetItemID("weapon_RPG", 7006));
			weapon_crossbow = new Weapon_crossbow(conf.GetItemID("weapon_crossbow", 7007));	
			weapon_satchel = new Weapon_satchel(conf.GetItemID("weapon_satchel", 7008));
			
			weapon_gauss = new Weapon_gauss(conf.GetItemID("weapon_gauss", 7050));
			weapon_egon = new Weapon_egon(conf.GetItemID("weapon_egon", 7051));
			
			armorHEVHelmet = new ArmorHEV(conf.GetItemID("armorHEVHelmet", 8001), 0);
			armorHEVChestplate = new ArmorHEV(conf.GetItemID("armorHEVChestplate", 8002), 1);
			armorHEVLeggings = new ArmorHEV(conf.GetItemID("armorHEVLeggings", 8003), 2);
			armorHEVBoot = new ArmorHEV(conf.GetItemID("armorHEVBoot", 8000), 3);
			
			mat_accessories = new Material_accessories(conf.GetItemID("mat_a", 8050));
			mat_ammunition = new Material_ammunition(conf.GetItemID("mat_b", 8051));
			mat_bio = new Material_bio(conf.GetItemID("mat_c", 8052));
			mat_heavy = new Material_heavy(conf.GetItemID("mat_d", 8053));
			mat_light = new Material_light(conf.GetItemID("mat_e", 8054));
			mat_pistol = new Material_pistol(conf.GetItemID("mat_f", 8055));
			mat_tech = new Material_tech(conf.GetItemID("mat_g", 8056));
			mat_explosive = new Material_explosive(conf.GetItemID("mat_h", 8057));
			mat_box = new Material_box(conf.GetItemID("mat_0", 8058));
			
			ironBar = new ItemIronBar(conf.GetItemID("ironBar", 8059));
			lambdaChip = new LambdaChipset(conf.GetItemID("lambdachip", 8060));
		} catch(Exception e){
			System.err.println("Error when loading itemIDs from config . " + e );
		}
		
		this.addRecipes();
		this.addItemNames();
		return;
	}
	
	private void addItemNames(){
		
		CBCLanguage.addDefaultName(itemAmmo_uranium , "Uranium Ammo");

		CBCLanguage.addDefaultName(itemAmmo_9mm2, "9mm Handgun Clip");
		CBCLanguage.addDefaultName(itemAmmo_9mm2, "9mmAR Clip");
		CBCLanguage.addDefaultName(itemAmmo_bow,"Crossbow Clip");
		CBCLanguage.addDefaultName(itemAmmo_357, ".357 Ammo");
		CBCLanguage.addDefaultName(itemAmmo_ARGrenade, "9mmAR Grenade");
		
		CBCLanguage.addDefaultName(itemBullet_Shotgun,"Shotgun Ammo");
		
		CBCLanguage.addDefaultName(weapon_crowbar, "Crowbar");
		
		CBCLanguage.addDefaultName(weapon_shotgun, "Shotgun");
		CBCLanguage.addDefaultName(weapon_9mmhandgun , "9mm Handgun");
		CBCLanguage.addDefaultName(weapon_9mmAR, "9mmAR");
        CBCLanguage.addDefaultName(weapon_hgrenade, "Hand Grenade");
        
        CBCLanguage.addDefaultName(weapon_357, ".357 Magnum");
        CBCLanguage.addDefaultName(weapon_satchel, "Satchel");
        CBCLanguage.addDefaultName(weapon_RPG, "RPG Rocket Launcher");
        CBCLanguage.addDefaultName(weapon_crossbow, "High Heat Crossbow");
        CBCLanguage.addDefaultName(weapon_gauss, "Gauss Gun");
        CBCLanguage.addDefaultName(itemRefinedIronIngot, "Refined Iron Ingot");
        
		CBCLanguage.addDefaultName(armorHEVBoot, "HEV boot");
		CBCLanguage.addDefaultName(armorHEVHelmet, "HEV helmet");
		CBCLanguage.addDefaultName(armorHEVChestplate, "HEV chestplate");
		CBCLanguage.addDefaultName(armorHEVLeggings, "HEV leggings");
		
		CBCLanguage.addDefaultName(mat_accessories, "Accessories Material");
		CBCLanguage.addDefaultName(mat_heavy, "Heavy Material");
		CBCLanguage.addDefaultName(mat_light, "Light Material");
		CBCLanguage.addDefaultName(mat_pistol, "Pistol Material");
		CBCLanguage.addDefaultName(mat_tech, "Tech Material");
		CBCLanguage.addDefaultName(mat_bio, "Bio Material");
		CBCLanguage.addDefaultName(mat_ammunition, "Ammunition Material");
		CBCLanguage.addDefaultName(mat_explosive, "Explosiove Material");
		
		CBCLanguage.addDefaultName(ironBar, "Refined Iron Bar");
		
		CBCLanguage.addLocalName(itemAmmo_uranium , "铀燃料");
		CBCLanguage.addLocalName(itemAmmo_9mm, "9毫米手枪弹匣");
		CBCLanguage.addLocalName(itemAmmo_9mm2, "9毫米步枪弹匣");
		CBCLanguage.addLocalName(itemAmmo_bow,"弩箭弹夹");
		CBCLanguage.addLocalName(itemAmmo_357, ".357子弹");
		CBCLanguage.addLocalName(itemAmmo_ARGrenade, "9毫米步枪用榴弹");
		
		CBCLanguage.addLocalName(itemBullet_Shotgun,"霰弹枪子弹");
		
		CBCLanguage.addLocalName(weapon_crowbar, "物理学圣剑");
		
		CBCLanguage.addLocalName(weapon_shotgun, "霰弹枪");
		CBCLanguage.addLocalName(weapon_9mmhandgun , "9毫米手枪");
		CBCLanguage.addLocalName(weapon_9mmAR, "9毫米步枪");
        CBCLanguage.addLocalName(weapon_hgrenade, "手雷");
        
        CBCLanguage.addLocalName(weapon_357, ".357麦林枪");
        CBCLanguage.addLocalName(weapon_satchel, "遥控炸药");
        CBCLanguage.addLocalName(weapon_RPG, "RPG火箭发射器");
        CBCLanguage.addLocalName(weapon_crossbow, "高耐热弩");
        CBCLanguage.addLocalName(weapon_gauss, "高斯枪");
        CBCLanguage.addLocalName(itemRefinedIronIngot, "精铁锭");
        
		CBCLanguage.addLocalName(armorHEVBoot, "HEV靴");
		CBCLanguage.addLocalName(armorHEVHelmet, "HEV盔");
		CBCLanguage.addLocalName(armorHEVChestplate, "HEV甲");
		CBCLanguage.addLocalName(armorHEVLeggings, "HEV腿");
		
		CBCLanguage.addLocalName(mat_accessories, "配件材质");
		CBCLanguage.addLocalName(mat_heavy, "重型材质");
		CBCLanguage.addLocalName(mat_light, "轻型材质");
		CBCLanguage.addLocalName(mat_pistol, "手枪材质");
		CBCLanguage.addLocalName(mat_tech, "科技材质");
		CBCLanguage.addLocalName(mat_bio, "生化材质");
		CBCLanguage.addLocalName(mat_ammunition, "弹药材质");
		CBCLanguage.addLocalName(mat_explosive, "爆裂材质");
		
		CBCLanguage.addLocalName(ironBar, "精铁条");
	}
	
	public static void addRecipes(){
        //Recipes
        ItemStack rosereddyeStack = new ItemStack(351,1,0);
        ItemStack ingotIronStack = new ItemStack(Item.ingotIron);
        ItemStack crowbarStack = new ItemStack(weapon_crowbar);
        GameRegistry.addShapelessRecipe(crowbarStack, rosereddyeStack,ingotIronStack);

        ItemStack output[] = {
        		new ItemStack(mat_pistol, 1),
        		new ItemStack(mat_accessories, 1),
        		new ItemStack(mat_light, 1),
        		new ItemStack(mat_heavy, 1),
        		new ItemStack(mat_explosive, 1),
        		new ItemStack(mat_bio, 1),
        		new ItemStack(mat_ammunition, 1),
        		new ItemStack(mat_tech, 1),
        		new ItemStack(mat_box, 5)
        };
        
        ItemStack sredstone = new ItemStack(Item.redstone),
        		swood = new ItemStack(Block.wood),
        		sglow = new ItemStack(Item.lightStoneDust),
        		sstick = new ItemStack(Item.stick),
        		srefined = new ItemStack(itemRefinedIronIngot),
        		sglass = new ItemStack(Block.glass),
        		scoal = new ItemStack(Item.coal),
        		sgold = new ItemStack(Item.ingotGold),
        		siron = new ItemStack(Item.ingotIron),
        		sblazep = new ItemStack(Item.blazePowder),
        		sdiamond = new ItemStack(Item.diamond),
        		sbrefined = new ItemStack(CBCBlocks.blockRefined),
        		sbredstone = new ItemStack(Block.blockRedstone),
        		sblap = new ItemStack(Block.blockLapis),
        		sgunpowder = new ItemStack(Item.gunpowder),
        		sflint = new ItemStack(Item.flint),
        		sfspieye = new ItemStack(Item.fermentedSpiderEye),
        		srotten = new ItemStack(Item.rottenFlesh),
        		smagma = new ItemStack(Item.magmaCream),
        		sbox = new ItemStack(CBCItems.mat_box),
        		stnt = new ItemStack(Block.tnt),
        		sendereye = new ItemStack(Item.eyeOfEnder),
        		slambdachip = new ItemStack(lambdaChip);
 
        Object input[][] = new Object[][]{
        		{"ADA", "CCC", "EBE", 'A', sstick,'B', sbox, 'C', srefined, 'D', sredstone, 'E', sglass},
        		{"ADA", "CCC", "EBE", 'A', sstick, 'B', sbox, 'C', scoal, 'D', sglow, 'E', sglass},
        		{"ADA", "CCC", "EBE", 'A', sgold, 'B', sbox, 'C', srefined, 'D', sglow, 'E', sglass},
        		{"ADA", "CCC", "EBE", 'A', sblazep, 'B', sbox, 'C', srefined, 'D', sblap, 'E', sglass},
        		{"ACA", "DCD", "EBE", 'A', srefined, 'B', sbox, 'C', stnt, 'D', sgunpowder, 'E', sglass},
        		{"ACA", "DFD", "EBE", 'A', srotten, 'B', sbox, 'C', smagma, 'D', sfspieye, 'E', sglass, 'F', sendereye},
        		{"AAA", "ACA", "EBE", 'A', sgunpowder, 'B', sbox, 'C', sredstone, 'E', sglass},
        		{"ACA", "DFD", "EBE", 'A', sglow, 'B', sbox, 'C',sbredstone, 'D', sdiamond, 'E', sglass, 'F', slambdachip},
        		{"A A", "AAA", 'A', srefined}
        
        };
        
        addRecipes(output, input);
        
        //Smeltings
        ModLoader.addSmelting(Item.ingotIron.itemID,new ItemStack(itemRefinedIronIngot.itemID,1,0) );
        
	}
	
	public static void addRecipes(ItemStack[] output, Object[][] input){
		if(output.length != input.length){
			throw new WrongUsageException("Two par's size should be the same", input[0]);
		}
		for(int i = 0; i < output.length; i++){
			ItemStack out = output[i];
			Object[] in = input[i];
			boolean flag = true;
			if(flag)
				GameRegistry.addRecipe(out, in);
		}
	}
	
}
