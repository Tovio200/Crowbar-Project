package cn.lambdacraft.deathmatch.item.ammos;

import cn.lambdacraft.core.CBCMod;

public class Ammo_9mm extends ItemAmmo {

	public Ammo_9mm(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cct);
		setIAndU("ammo_9mmhandgun");
		setMaxDamage(19);
	}
}
