package cbproject.deathmatch.items.wpns;

import cbproject.core.CBCMod;
import cbproject.core.utils.CBCWeaponInformation;
import cbproject.deathmatch.utils.AmmoManager;
import cbproject.deathmatch.utils.BulletManager;
import cbproject.deathmatch.utils.InformationBullet;
import cbproject.deathmatch.utils.InformationEnergy;
import cbproject.deathmatch.utils.InformationSet;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public abstract  class WeaponGeneralEnergy extends WeaponGeneral {

	public int jamTime;

	public WeaponGeneralEnergy(int par1, int par2AmmoID, int par3MaxModes) {
		super(par1, par2AmmoID, par3MaxModes);
		type = 1;
	}

	/**
	 * get the shoot time for the weapon corresponding to the mode.
	 * @param mode
	 * @return shootTime
	 */
	public abstract int getShootTime(int mode);
	
	/**
	 * get the damage for the weapon corresponding to the mode.
	 * @param mode
	 * @return damage
	 */
	public abstract int getDamage(int mode);
	
	/**
	 * Get the shoot sound path corresponding to the mode.
	 * @param mode
	 * @return sound path
	 */
	public abstract String getSoundShoot(int mode);
	
	/**
	 * Get the jam sound path corresponding to the mode.
	 * @param mode
	 * @return sound path
	 */
	public abstract String getSoundJam(int mode);
	
	/**
	 * Set the jam time.
	 * @param jamTime.
	 */
	public final void setJamTime(int par1){
		jamTime = par1;
	}
	
	
	public abstract void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5);
	
	/**
	 * Generally do the itemRightClick processing.
	 */
	public void processRightClick(InformationEnergy information, ItemStack par1ItemStack, 
			World par2World, EntityPlayer par3EntityPlayer){

		int mode = information.mode;
		
		Boolean canUse = canShoot(par3EntityPlayer, par1ItemStack);
		if(doesShoot(information, par1ItemStack, par3EntityPlayer))
			onEnergyWpnShoot(par1ItemStack, par2World, par3EntityPlayer, information);

		information.isShooting = true;
		par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack) );
		par3EntityPlayer.setEating(false);
		
	}
	
	public InformationEnergy onEnergyWpnUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5){
		
		InformationEnergy information = (InformationEnergy) super.onWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);

		if(information == null){
    		information = getInformation(par1ItemStack, par2World);
    		if(information == null)
    			return null;
    		
    		information.isShooting = false;
    		return null;
    	}
		information.updateTick();
		
		int ticksExisted = information.ticksExisted;
		int lastTick = information.lastTick;
		int mode = information.mode;

		Boolean isShooting = information.isShooting;
		EntityPlayer player = (EntityPlayer) par3Entity;
		
		if( doesShoot(information, par1ItemStack, player) ){
			this.onEnergyWpnShoot(par1ItemStack, par2World, player, information);
			return information;
		}

		if( doesJam(information, par1ItemStack, player) ){
			this.onEnergyWpnJam(par1ItemStack, par2World, player, information);
			return information;
		}
		return information;
		
	}
	
    /**
     * Determine if the shoot method should be called this tick.
     * @return If the shoot method should be called in this tick or not.
     */
	public Boolean doesShoot(InformationEnergy inf, ItemStack itemStack, EntityPlayer player){
		Boolean canUse = canShoot(player, itemStack);
		return getShootTime(inf.mode) > 0 && inf.isShooting && canUse && inf.getDeltaTick() >= getShootTime(inf.mode);
	}
	
    /**
     * Determine if the jam method should be called this tick.
     * @return If the jam method should be called in this tick or not.
     */
	public Boolean doesJam(InformationEnergy inf, ItemStack itemStack, EntityPlayer player){
		Boolean canUse = canShoot(player, itemStack);
		return inf.isShooting && !canUse && inf.getDeltaTick() >= jamTime;
	}
	
    /**
     * Very normal Energy shooting process, currently not used.
     */
	public void onEnergyWpnShoot(ItemStack par1ItemStack, World par2World, EntityPlayer par3Entity, InformationEnergy information ){
		
		int mode = information.mode;
		
		par2World.playSoundAtEntity(par3Entity, getSoundShoot(mode), 0.5F, 1.0F);	
		BulletManager.Shoot(par1ItemStack, (EntityLiving) par3Entity, par2World, "smoke");
    	information.setLastTick();

    	if(par3Entity instanceof EntityPlayer){
    		doUplift(information, (EntityPlayer) par3Entity);
    		EntityPlayer player = (EntityPlayer) par3Entity;
    		if(!((EntityPlayer)par3Entity).capabilities.isCreativeMode ){
    				AmmoManager.consumeAmmo(player, this, 1);
    		}
    	}
    	
		return;
	}
	
	public void onEnergyWpnJam(ItemStack par1ItemStack, World par2World, EntityPlayer par3Entity, InformationEnergy information ){
		
    	int maxDmg = par1ItemStack.getMaxDamage();
    	int mode = information.mode;
		if( par1ItemStack.getItemDamage() < maxDmg){
			return;
		}
		
		par2World.playSoundAtEntity(par3Entity, getSoundJam(mode), 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		information.setLastTick();
		
	}
	
	@Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) 
	{
		InformationEnergy inf = getInformation(par1ItemStack, par2World);
		inf.isShooting = false;
	}
	
    public Boolean canShoot(EntityPlayer player, ItemStack is){
    	return AmmoManager.hasAmmo(this, player) || player.capabilities.isCreativeMode;
    }

	@Override
	public InformationEnergy loadInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer){
		
		InformationEnergy inf = getInformation(par1ItemStack, par2EntityPlayer.worldObj);
		if(inf != null)
			return inf;
		
		double uniqueID = Math.random()*65535D;
		
		inf = CBCMod.wpnInformation.addToList(uniqueID, createInformation(par1ItemStack, par2EntityPlayer)).getProperEnergy(par2EntityPlayer.worldObj);
		
		if(par1ItemStack.stackTagCompound == null)
			par1ItemStack.stackTagCompound = new NBTTagCompound();
		par1ItemStack.getTagCompound().setDouble("uniqueID", uniqueID);
		return inf;
		
	}

	@Override
	public InformationEnergy getInformation(ItemStack itemStack, World world){	
	   InformationSet set = CBCWeaponInformation.getInformation(itemStack);
	   return set == null ? null : set.getProperEnergy(world);
	}
	
	private InformationSet createInformation(ItemStack is, EntityPlayer player){
		InformationEnergy inf = new InformationEnergy(is, player);
		InformationEnergy inf2 = new InformationEnergy(is, player);
		return new InformationSet(inf, inf2);
	}

}
