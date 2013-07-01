package cn.lambdacraft.core;

import cn.lambdacraft.deathmatch.items.ArmorHEV;
import cn.lambdacraft.deathmatch.items.ArmorLongjump;
import cn.lambdacraft.deathmatch.items.ArmorHEV.EnumAttachment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.src.PlayerAPI;
import net.minecraft.src.PlayerBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.MathHelper;

public class CBCPlayer extends PlayerBase {

	public static final float LJ_VEL_RADIUS = 1.5F, BHOP_VEL_SCALE = 0.003F, SPEED_REDUCE_SCALE = 0.0005F;
	private float lastTickRotationYaw;
	private GameSettings gameSettings;
	private int fallingTick;
	private boolean hevArmorStatus[] = new boolean[4];
	
	public CBCPlayer(PlayerAPI var1) {
		super(var1);
		gameSettings = Minecraft.getMinecraft().gameSettings;
	}

	//---------------通用支持部分------------------
	@Override
	public void beforeOnUpdate() {
		//Update Armor Status
		for(int i = 0; i < 4; i ++) 
			hevArmorStatus[i] = player.inventory.armorInventory[i] != null && 
				player.inventory.armorInventory[i].getItem() instanceof ArmorHEV;
	}
	
	@Override
	public void afterOnLivingUpdate() {
		lastTickRotationYaw = player.rotationYaw;
	}
	
	
	//---------------LongJump-----------------------
	/**
	 * 自建跳跃，长跳包支持。
	 */
	@Override
	public void jump() {
		jumpIgnoringMotion();
		
		ItemStack slotChestplate = player.inventory.armorInventory[2];
		if (slotChestplate != null && player.isSneaking()) {
			Boolean b = false;
			Item item = slotChestplate.getItem();
			if (item instanceof ArmorLongjump)
				b = true;
			else if (item instanceof ArmorHEV) {
				ArmorHEV hev = (ArmorHEV) item;
				if (hev.hasAttach(slotChestplate, EnumAttachment.LONGJUMP))
					b = true;
			}
			if (b) {
				player.motionX = -MathHelper.sin(player.rotationYaw / 180.0F
						* (float) Math.PI)
						* MathHelper.cos(player.rotationPitch / 180.0F
								* (float) Math.PI) * LJ_VEL_RADIUS;
				player.motionZ = MathHelper.cos(player.rotationYaw / 180.0F
						* (float) Math.PI)
						* MathHelper.cos(player.rotationPitch / 180.0F
								* (float) Math.PI) * LJ_VEL_RADIUS;
			}
		}
		
		
		ItemStack slotLeggings = player.inventory.armorInventory[1];
		if(slotLeggings == null || !(slotLeggings.getItem() instanceof ArmorHEV)) {
			if (player.isSprinting())
	        {
	            float var1 = player.rotationYaw * 0.017453292F;
	            player.motionX -= (double)(MathHelper.sin(var1) * 0.2F);
	            player.motionZ += (double)(MathHelper.cos(var1) * 0.2F);
	        }
		}
		
	}
	
	//--------------------Bhop-------------------
	/**
	 * Update函数，连跳支持。
	 */
	@Override
	public void onLivingUpdate() {
		
		if(!useBhop()) {
			player.localOnLivingUpdate();
			return;
		}
		
		//calculate and strafe!
		double velToAdd = player.movementInput.moveStrafe * (1.0 - Math.abs(player.movementInput.moveForward));
		float changedYaw = player.rotationYaw - lastTickRotationYaw;
		if(Math.abs(changedYaw) > 10) {
			changedYaw = changedYaw > 0 ? 10 : -10;
		}
		velToAdd *= -changedYaw;
		if(!player.onGround) {
			fallingTick = -1;
		} else ++fallingTick;
		
		if(velToAdd == 0.0 || velToAdd == -0.0) {
			player.localOnLivingUpdate();
			return;
		}
		//加速
		player.motionX += -MathHelper.sin(player.rotationYaw/ 180.0F
					* (float) Math.PI)
					* MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI)
					* velToAdd * BHOP_VEL_SCALE;
		player.motionZ += MathHelper.cos(player.rotationYaw/ 180.0F
					* (float) Math.PI)
					* MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI)
					* velToAdd * BHOP_VEL_SCALE;
		//变向
		double vel = Math.sqrt(player.motionX * player.motionX + player.motionZ * player.motionZ);
		float motionYaw = getMotionRotationYaw();
		changedYaw = wrapYawAngle(player.rotationYaw - lastTickRotationYaw);
		if(changedYaw > 20)
			changedYaw = changedYaw > 0 ? 20 : -20;
		motionYaw -= changedYaw;
		vel -= changedYaw * SPEED_REDUCE_SCALE;
		motionYaw = wrapYawAngle(motionYaw);
		player.motionX = MathHelper.sin(motionYaw * (float) Math.PI / 180.0F) * vel;
		player.motionZ = MathHelper.cos(motionYaw / 180.0F * (float) Math.PI) * vel;
		//调用原本的Update
		player.localOnLivingUpdate();
		
	}
	
	@Override
    public void moveEntityWithHeading(float var1, float var2)
    {
		double preMotionZ = player.motionZ, preMotionX = player.motionX;
		player.localMoveEntityWithHeading(var1, var2);
    	if(useBhop() && fallingTick < 2 && !player.isCollidedHorizontally) {
    		double speedReduction = player.onGround? 0.51 : 0.98;
    		player.motionX = preMotionX * speedReduction;
    		player.motionZ = preMotionZ * speedReduction;
    	}
    }
	
	//-----------------------效用函数----------------------------
	
	private boolean useBhop() {
		return hevArmorStatus[1] && ArmorHEV.getAttachments(player.inventory.armorInventory[1]).contains(EnumAttachment.BHOP) && 
				!(player.handleLavaMovement() || player.handleWaterMovement() || player.isOnLadder() || player.capabilities.isCreativeMode);
	}
	
	private float wrapYawAngle(float f) {
		if(f > 180.0F)
			f -= 360.0F;
		else if(f < -180.0F)
			f = 360.0F - f;
		return f;
	}
	
	private float getMotionRotationYaw() {
		double par1 = player.motionX, par3 = player.motionY, par5 = player.motionZ;
		float f2 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5
				* par5);
		par1 /= f2;
		par3 /= f2;
		par5 /= f2;
		return (float) (Math.atan2(par1, par5) * 180.0D / Math.PI);
	}
	
	private void jumpIgnoringMotion() {
		player.motionY = 0.41999998688697815D;

        if (player.isPotionActive(Potion.jump))
        {
        	player.motionY += (double)((float)(player.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
        }

        player.isAirBorne = true;
        player.addStat(StatList.jumpStat, 1);

        if (player.isSprinting())
        {
        	player.addExhaustion(0.8F);
        }
        else
        {
        	player.addExhaustion(0.2F);
        }
	}

}
