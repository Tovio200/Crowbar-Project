package cbproject.deathmatch.items.ammos;

import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.core.CBCMod;

public class Ammo_357 extends ItemAmmo {

	public Ammo_357(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cct);
		setIAndU("ammo_357");
	} 
}
