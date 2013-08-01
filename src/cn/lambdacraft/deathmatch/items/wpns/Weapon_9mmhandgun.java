package cn.lambdacraft.deathmatch.items.wpns;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cn.lambdacraft.api.hud.IHudTip;
import cn.lambdacraft.api.weapon.WeaponGeneralBullet;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.crafting.register.CBCItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * 9mm Handgun. Mode I : low speed, high accuracy Mode II : high speed, low
 * accuracy
 * 
 * @author WeAthFolD
 * 
 */
public class Weapon_9mmhandgun extends WeaponGeneralBullet {

	public Weapon_9mmhandgun(int par1) {

		super(par1, CBCItems.ammo_9mm.itemID, 2);

		setUnlocalizedName("weapon_9mmhandgun");
		setCreativeTab(CBCMod.cct);
		setMaxStackSize(1);
		setMaxDamage(18);
		setIconName("weapon_9mmhandgun");
		setNoRepair();

		int shootTime[] = { 10, 5 }, dmg[] = { 3, 3 }, off[] = { 2, 8 };
		double push[] = { 0.5, 0.5 };

		setReloadTime(60);
		setJamTime(10);

	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		super.onBulletWpnUpdate(par1ItemStack, par2World, par3Entity, par4,
				par5);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		processRightClick(par1ItemStack, par2World, par3EntityPlayer);
		return par1ItemStack;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer, int par4) {
		super.onPlayerStoppedUsing(par1ItemStack, par2World, par3EntityPlayer,
				par4);
	}

	@Override
	public String getSoundShoot(int mode) {
		return "cbc.weapons.plgun_c";
	}

	@Override
	public String getSoundJam(int mode) {
		return "cbc.weapons.gunjam_a";
	}

	@Override
	public String getSoundReload(int mode) {
		return "cbc.weapons.nmmclipa";
	}

	@Override
	public int getShootTime(int mode) {
		return (mode == 0) ? 10 : 5;
	}

	@Override
	public double getPushForce(int mode) {
		return 0.5;
	}

	@Override
	public int getDamage(int mode) {
		return 3;
	}

	@Override
	public int getOffset(int mode) {
		return mode == 0 ? 0 : 8;
	}

	@Override
	public String getModeDescription(int mode) {
		return mode == 0 ? "mode.9mmhg1" : "mode.9mmhg2";
	}

}
