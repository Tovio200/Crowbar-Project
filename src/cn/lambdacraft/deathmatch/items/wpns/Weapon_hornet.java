/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.deathmatch.items.wpns;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cn.lambdacraft.api.weapon.InformationBullet;
import cn.lambdacraft.api.weapon.WeaponGeneralBullet;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.deathmatch.entities.EntityHornet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * @author WeAthFolD.
 * 
 */
public class Weapon_Hornet extends WeaponGeneralBullet {

	public static final int RECOVER_TIME = 10;

	public Weapon_Hornet(int par1) {
		super(par1, 0);
		setMaxDamage(9);
		setCreativeTab(CBCMod.cct);
		setIAndU("weapon_hornet");
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		InformationBullet inf = onBulletWpnUpdate(par1ItemStack, par2World,
				par3Entity, par4, par5);
		if (inf == null)
			return;
		int dt = inf.ticksExisted;
		EntityPlayer player = (EntityPlayer) par3Entity;
		if (dt % RECOVER_TIME == 0 && !(this.canShoot(player, par1ItemStack) && player.isUsingItem())) {
			if (par1ItemStack.getItemDamage() > 0)
				par1ItemStack.setItemDamage(par1ItemStack.getItemDamage() - 1);
		}
	}

	@Override
	public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World,
			EntityPlayer player, InformationBullet information, boolean left) {

		if (!(player.capabilities.isCreativeMode))
			par1ItemStack.damageItem(1, player);
		player.setItemInUse(par1ItemStack,
				this.getMaxItemUseDuration(par1ItemStack));
		if (!par2World.isRemote)
			par2World.spawnEntityInWorld(new EntityHornet(par2World, player, left));
		par2World.playSoundAtEntity(player,
				this.getSoundShoot(left), 0.5F, 1.0F);
		information.setLastTick();
		return;
	}

	@Override
	public void onBulletWpnReload(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3Entity, InformationBullet information) {}

	@Override
	public double getPushForce(boolean left) {
		return 0;
	}

	@Override
	public int getDamage(boolean left) {
		return 4;
	}

	@Override
	public int getOffset(boolean left) {
		return 0;
	}

	@Override
	public String getSoundShoot(boolean left) {
		int random = (int) (itemRand.nextFloat() * 3);
		return random == 0 ? "cbc.weapons.ag_firea"
				: (random == 1 ? "cbc.weapons.ag_fireb"
						: "cbc.weapons.ag_firec");
	}

	@Override
	public String getSoundJam(boolean left) {
		return "";
	}

	@Override
	public String getSoundReload(boolean left) {
		return "";
	}

	@Override
	public int getShootTime(boolean left) {
		return left ? 5 : 2;
	}

}
