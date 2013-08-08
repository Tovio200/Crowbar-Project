package cn.lambdacraft.deathmatch.items.wpns;

import cn.lambdacraft.api.weapon.WeaponGeneralBullet;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.crafting.register.CBCItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * .357 Magnum weapon class.
 * 
 * @author WeAthFolD
 * 
 */
public class Weapon_357 extends WeaponGeneralBullet {

	public Weapon_357(int par1) {

		super(par1, CBCItems.ammo_357.itemID);

		setUnlocalizedName("weapon_357");
		setCreativeTab(CBCMod.cct);
		setMaxStackSize(1);
		setMaxDamage(7);
		setNoRepair();

		setReloadTime(70);
		setJamTime(20);
		setLiftProps(25, 3);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon("lambdacraft:weapon_357");
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		super.onBulletWpnUpdate(par1ItemStack, par2World, par3Entity, par4,
				par5);
	}

	@Override
	public String getSoundShoot(boolean side) {
		return itemRand.nextInt(2) == 0 ? "cbc.weapons.pyt_shota" : "cbc.weapons.pyt_shotb";
	}

	@Override
	public String getSoundJam(boolean side) {
		return side ? "cbc.weapon.gunjam_a" : "";
	}

	@Override
	public String getSoundReload(boolean side) {
		return "cbc.weapons.pyt_reloada";
	}

	@Override
	public int getShootTime(boolean side) {
		return side ? 20 : 0;
	}

	@Override
	public double getPushForce(boolean side) {
		return 1;
	}

	@Override
	public int getDamage(boolean side) {
		return 7;
	}

	@Override
	public int getOffset(boolean side) {
		return 1;
	}

}
