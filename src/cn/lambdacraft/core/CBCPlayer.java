package cn.lambdacraft.core;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.src.PlayerAPI;
import net.minecraft.src.PlayerBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import cn.lambdacraft.core.utils.GenericUtils;
import cn.lambdacraft.deathmatch.items.ArmorHEV;
import cn.lambdacraft.deathmatch.items.ArmorHEV.EnumAttachment;
import cn.lambdacraft.deathmatch.register.DMItems;
public class CBCPlayer extends PlayerBase {

	public static final float LJ_VEL_RADIUS = 1.5F, BHOP_VEL_SCALE = 0.003F, SPEED_REDUCE_SCALE = 0.0005F;
	private float lastTickRotationYaw;
	private GameSettings gameSettings;
	private Minecraft mc = Minecraft.getMinecraft();
	private int fallingTick;
	private int tickSinceLastSound = 0;
	private int lastHealth = 0;
	public static boolean drawArmorTip = false;
	public static boolean armorStat[] = new boolean[4];
	
	ArmorHEV hevHead = DMItems.armorHEVHelmet, hevChest = DMItems.armorHEVChestplate,
			hevBoots = DMItems.armorHEVBoot, hevLeggings = DMItems.armorHEVLeggings;
	
	public CBCPlayer(PlayerAPI var1) {
		super(var1);
		gameSettings = Minecraft.getMinecraft().gameSettings;
	}
	
	//---------------玩家状态显示部分---------------
	public static enum EnumStatus {
		FIRE(128), OXYGEN(96), RADIATION(192), BADEFF(160), ELECTROLYZE(224), NONE(0, 128);
		public int u, v;
		private EnumStatus(int texX, int texY) {
			u = texX;
			v = texY;
		}
		private EnumStatus(int texX) {
			u = texX;
			v = 96;
		}
	}
	
	public static EnumStatus playerStat = EnumStatus.NONE;

	//---------------通用支持部分------------------
	@Override
	public void beforeOnUpdate() {
		
		boolean preOnHEV = armorStat[2] &&  armorStat[3];
		
		//Update Armor Status
		for(int i = 0; i < 4; i ++) {
			boolean b = player.inventory.armorInventory[i] != null && 
				player.inventory.armorInventory[i].getItem() instanceof ArmorHEV;
			if(b) {
				ArmorHEV hev = (ArmorHEV) player.inventory.armorInventory[i].getItem();
				if(hev.getItemCharge(player.inventory.armorInventory[i]) <= 0) {
					b = false;
				}
			}
			armorStat[i] = b;
		}
		
		if(armorStat[3]) {
			drawArmorTip = hevLeggings.hasAttach(player.inventory.armorInventory[3], EnumAttachment.WPNMANAGE);
		} else drawArmorTip = false;
		
		ItemStack chest = player.inventory.armorInventory[2], helmet = player.inventory.armorInventory[3];
		//putting HEV on at this tick
		if(!preOnHEV && (armorStat[2] && armorStat[3])) {
			mc.sndManager.playSoundFX("cbc.hev.hev_logon", 0.5F, 1.0F);
		} else if(preOnHEV && !(armorStat[2] && armorStat[3])) { //HEV 'Broke down' because player action or energy critical
			if(player.inventory.armorInventory[2] != null && player.inventory.armorInventory[3] != null) {
				mc.sndManager.playSoundFX("cbc.hev.hev_shutdown", 0.5F, 1.0F);
			}
		}
		
		if(player.isBurning()) {
			playerStat = EnumStatus.FIRE;
		} else if(player.getAir() <= 0) {
			playerStat = EnumStatus.OXYGEN;
		} else {
			playerStat = EnumStatus.NONE;
		}
		
	}
	
	@Override
	public void afterOnLivingUpdate() {
		lastTickRotationYaw = player.rotationYaw;
	}
	
	@Override
	public void afterOnUpdate() {
		++tickSinceLastSound;
		if((armorStat[2] &&  armorStat[3])) {
			if(player.getHealth() - lastHealth < 0 && player.getHealth() <= 5) {
				if(tickSinceLastSound > 30) {
					mc.sndManager.playSoundFX("cbc.hev.health_critical", 0.5F, 1.0F);
					tickSinceLastSound = 0;
				}
			}
		}
		lastHealth = player.getHealth();
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
			Item item = slotChestplate.getItem();
			if (item instanceof ArmorHEV) {
				ArmorHEV hev = (ArmorHEV) item;
				if (hev.hasAttach(slotChestplate, EnumAttachment.LONGJUMP))
					if (hev.discharge(slotChestplate, 200, 2, true, false) == 200) {
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
		changedYaw = GenericUtils.wrapYawAngle(player.rotationYaw - lastTickRotationYaw);
		if(changedYaw > 20)
			changedYaw = changedYaw > 0 ? 20 : -20;
		motionYaw -= changedYaw;
		vel -= changedYaw * SPEED_REDUCE_SCALE;
		motionYaw = GenericUtils.wrapYawAngle(motionYaw);
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
		EnumSet<EnumAttachment> attach = ArmorHEV.getAttachments(player.inventory.armorInventory[1]);
		if(attach == null)
			return false;
		return armorStat[1] && attach.contains(EnumAttachment.BHOP) && 
				!(player.handleLavaMovement() || player.handleWaterMovement() || player.isOnLadder() || player.capabilities.isCreativeMode);
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
	
	//----------------Sounds-----------------
	/*
	@Override
	public void beforeAttackEntityFrom(DamageSource var1, int var2) {
		String s = null;
		if(var1 == DamageSource.fall) {
			if(var2 >= 13)
			s = "cbc.hev.major_fracture";
		} else if(var1 == DamageSource.inFire) {
			s = "cbc.hev.heat_damage";
		} else if(var1.getEntity() != null && var2 > 5) {
			s = "cbc.hev.blood_loss";
		}
		if(s != null && player.ticksExisted - lastSoundTick > 50 ) {
			mc.sndManager.playSoundFX(s, 0.5F, 1.0F);
		}
		System.out.println("Me getting called! " + var1 + " " + var2);
		lastHealth = player.getHealth();
	}
	
	@Override
	public void afterAttackEntityFrom(DamageSource var1, int var2) {
		if(player.getHealth() <= 5 && lastHealth > 5) {
			mc.sndManager.playSoundFX("cbc.hev.health_critical", 0.5F, 1.0F);
		}
	}
	*/

}
