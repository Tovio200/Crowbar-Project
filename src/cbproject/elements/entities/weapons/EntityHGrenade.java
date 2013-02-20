package cbproject.elements.entities.weapons;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityHGrenade extends EntitySnowball {

	public EntityHGrenade(World par1World) {
		super(par1World);
	}

    public EntityHGrenade(World par1World, EntityLiving par2EntityLiving)
    {
        super(par1World,par2EntityLiving);
        float var3 = 0.2F; //�ٶ�Ϊѩ���һ��
        this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * var3);
        this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * var3);
        this.motionY = (double)(-MathHelper.sin((this.rotationPitch + this.func_70183_g()) / 180.0F * (float)Math.PI) * var3);
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, this.func_70182_d(), 1.0F);
        System.out.println("Entity has been inited.");
    }


	public EntityHGrenade(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
	{
	    if (par1MovingObjectPosition.entityHit != null)
	    {
	        byte var2 = 0;

	        if (par1MovingObjectPosition.entityHit instanceof EntityBlaze)
	        {
	            var2 = 3;
	        }

	        par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), var2);
	    }

	    for (int var3 = 0; var3 < 8; ++var3)
	    {
	            this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
	    }
	    float var1=4.0F;
	    this.worldObj.createExplosion((Entity)null, this.posX, this.posY, this.posZ, var1, true);
	    System.out.println("Generated explosion");
	    this.setDead();
	    
	    if (!this.worldObj.isRemote)
	    {
	            this.setDead();
	    }
	    
	}
	


}
