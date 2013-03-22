package cbproject.utils.weapons;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class InformationBulletWeapon {
	//357,9mmAR,9mmhandgun等的辅助类
	public int ticksExisted, lastTick;
	public AmmoManager ammoManager;
	public Boolean canUse, isShooting,isReloading, rsp;
	public Boolean isRecovering; //枪口上抬视角恢复相关
	public double originPitch;
	public double signID;
	public int recoverTick;

	public InformationBulletWeapon(double par1ID, Boolean par2CanUse, Boolean par3Shooting, Boolean par4Reloading, int par6Tick, EntityPlayer par7Player, ItemStack par8Weapon) {
		ticksExisted = 0;
		lastTick = 0;
		
		signID = par1ID;
		canUse = par2CanUse;
		isShooting = par3Shooting;
		isReloading = par4Reloading;
		isRecovering = false;
		recoverTick = 0;
		ammoManager = new AmmoManager(par7Player, par8Weapon);
		rsp = false;

	}
	
	public void updateTick(){
		ticksExisted++;
	}
	
	public void setLastTick(){
		lastTick = ticksExisted;
	}

}
