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
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》。你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cbproject.deathmatch.entities;

import cbproject.core.utils.MotionXYZ;
import cbproject.deathmatch.items.wpns.WeaponGeneral;
import cbproject.deathmatch.utils.BulletManager;
import cbproject.deathmatch.utils.InformationWeapon;



import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * Bullet entity class which handles all bullet weapons.
 * @author WeAthFolD
 *
 */
public class EntityBullet extends EntityThrowable {
	
	protected InformationWeapon information;
	protected ItemStack itemStack;
	protected MotionXYZ motion;
	
	public EntityBullet(World par1World, EntityLiving par2EntityLiving, ItemStack par3itemStack) {
		
		super(par1World, par2EntityLiving);
		
		itemStack = par3itemStack;
		if( itemStack == null || !(itemStack.getItem() instanceof WeaponGeneral) )
			this.setDead();
		this.setSize(0.0F, 0.0F);
        this.setLocationAndAngles(par2EntityLiving.posX, par2EntityLiving.posY + par2EntityLiving.getEyeHeight(), par2EntityLiving.posZ, par2EntityLiving.rotationYawHead, par2EntityLiving.rotationPitch);
        this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
        this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0F;
        float f = 0.4F;
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f;
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f;
        this.motionY = -MathHelper.sin((this.rotationPitch + this.func_70183_g()) / 180.0F * (float)Math.PI) * f;
        motion = new MotionXYZ(this);
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, this.func_70182_d(), 1.0F);
        
	}
	
	@Override
	public void onUpdate(){
		super.onUpdate();
		if(ticksExisted > 50)
			this.setDead();
	}

	
	@Override
	protected void entityInit() {
	}
	
	public EntityBullet(World world){
		super(world);
	}

    @Override
	protected float getGravityVelocity()
    {
        return 0.0F;
    }
    

    
	@Override
	protected void onImpact(MovingObjectPosition par1)
	{    
	    switch(par1.typeOfHit){
	    case TILE:
	    	doBlockCollision(par1);
	    	break;
	    case ENTITY:
	    	doEntityCollision(par1);
	    	break;
	    }
	    this.setDead();
	}
	
	protected void doBlockCollision(MovingObjectPosition result){	
	}
	
	public void doEntityCollision(MovingObjectPosition result){
	
		if( result.entityHit == null)
			return;
		if(!(result.entityHit instanceof EntityLiving || result.entityHit instanceof EntityDragonPart || result.entityHit instanceof EntityEnderCrystal))
			return;
		WeaponGeneral item = (WeaponGeneral) itemStack.getItem();
		InformationWeapon inf = item.getInformation(itemStack, worldObj);
		int mode = item.getMode(itemStack);
		double pf = item.getPushForce(mode);
		double dx = motion.motionX * pf, dy = motion.motionY * pf, dz = motion.motionZ * pf;
		BulletManager.doEntityAttack(result.entityHit, DamageSource.causeMobDamage(getThrower()), item.getDamage(mode), dx, dy, dz);
		
	}
	
	public int getBulletDamage(int mode){
		WeaponGeneral item = (WeaponGeneral) itemStack.getItem();
		return item.getDamage(mode);
	}
	
	@Override
	public boolean canBeCollidedWith()
	{
	    return false;
	}

	@Override
	protected float func_70182_d() {
		return 50.0F;
	}
	
}
