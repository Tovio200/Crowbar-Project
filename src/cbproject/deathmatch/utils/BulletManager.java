package cbproject.deathmatch.utils;

import java.util.List;

import cbproject.deathmatch.entities.EntityBullet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BulletManager {

	private static final double BB_SIZE = 0.5D;
	private static final int ENTITY_TRACE_RANGE = 128;
	
	public static void Shoot(ItemStack itemStack, EntityLiving entityPlayer, World worldObj, String effect){
		worldObj.spawnEntityInWorld(new EntityBullet(worldObj, entityPlayer, itemStack, effect));
	}
	
	public static void doEntityAttack(Entity ent, DamageSource ds, int damage, double dx, double dy, double dz){
		if(ent instanceof EntityLiving){
			((EntityLiving)ent).attackEntityFrom(ds, damage);
		}
		if(ent instanceof EntityDragonPart){
			((EntityDragonPart)ent).attackEntityFrom(ds, damage);
		}
		if(ent instanceof EntityEnderCrystal)
			((EntityEnderCrystal)ent).attackEntityFrom(ds, damage);
		ent.addVelocity(dx, dy, dz);
	}
	
	public static void Explode(World world,Entity entity, float strengh, double radius, double posX, double posY, double posZ, int additionalDamage){
		
		Explosion explosion =  world.createExplosion(entity, posX, posY, posZ, strengh, true);
        
		if(additionalDamage <= 0)
			return;
		
		AxisAlignedBB par2 = AxisAlignedBB.getBoundingBox(posX-4, posY-4, posZ-4, posX+4, posY+4, posZ+4);
		List entitylist = world.getEntitiesWithinAABBExcludingEntity(null, par2);
		if(entitylist.size() > 0){
			for(int i=0;i<entitylist.size();i++){
				Entity ent = (Entity)entitylist.get(i);
				if(ent instanceof EntityLiving){
					double distance = Math.sqrt(Math.pow(ent.posX-posX,2) + Math.pow(ent.posY-posY,2) + Math.pow(ent.posZ-posZ,2));
					int damage = (int) ((1 - distance/6.928) * additionalDamage);
					System.out.println("explosion damage for distance " + distance + " : " + damage);
					ent.attackEntityFrom(DamageSource.setExplosionSource(explosion), damage);
				}
			}
		}
	}
	
}