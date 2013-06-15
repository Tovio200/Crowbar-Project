/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 鐗堟潈璁稿彲锛歀ambdaCraft 鍒朵綔灏忕粍锛�2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft鏄畬鍏ㄥ紑婧愮殑銆傚畠鐨勫彂甯冮伒浠庛�LambdaCraft寮�簮鍗忚銆嬨�浣犲厑璁搁槄璇伙紝淇敼浠ュ強璋冭瘯杩愯
 * 婧愪唬鐮侊紝 鐒惰�浣犱笉鍏佽灏嗘簮浠ｇ爜浠ュ彟澶栦换浣曠殑鏂瑰紡鍙戝竷锛岄櫎闈炰綘寰楀埌浜嗙増鏉冩墍鏈夎�鐨勮鍙�
 */
package cbproject.crafting.blocks;

import ic2.api.Direction;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.MinecraftForge;
import cbproject.api.LCDirection;
import cbproject.api.energy.events.EnergyTileLoadEvent;
import cbproject.api.energy.events.EnergyTileSourceEvent;
import cbproject.api.energy.tile.IEnergySink;
import cbproject.core.utils.EnergyUtils;
import cbproject.crafting.blocks.BlockWeaponCrafter.CrafterIconType;
import cbproject.crafting.items.ItemMaterial;
import cbproject.crafting.recipes.RecipeCrafter;
import cbproject.crafting.recipes.RecipeRepair;
import cbproject.crafting.recipes.RecipeWeapons;
import cbproject.crafting.register.CBCBlocks;
import cbproject.deathmatch.utils.AmmoManager;

/**
 * @author WeAthFolD
 *
 */
public class TileElCrafter extends TileWeaponCrafter implements IEnergySink,ic2.api.energy.tile.IEnergySink {

	/**
	 * 鏈�ぇ瀛樺偍鐑噺銆�
	 */
	public static int MAX_STORAGE = 80000;
	
	public int currentEnergy;
	public boolean isLoad = false;
	
	public TileElCrafter(){
		super();
		this.maxHeat = 10000;
	}
	
	@Override
	public void updateEntity() {
		if(!isLoad) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(worldObj, this));
			isLoad = true;
			this.writeRecipeInfoToSlot();
		}
		
		if(worldObj.isRemote)
			return;
		
		if(heat > 0)
			heat--;
		
		if(iconType == CrafterIconType.NOMATERIAL && worldObj.getWorldTime() - lastTime > 20){
			iconType = isCrafting? CrafterIconType.CRAFTING : CrafterIconType.NONE;
		}
		
		if(isCrafting && currentRecipe != null){
			if(currentRecipe.heatRequired <= this.heat && hasEnoughMaterial(currentRecipe)){
				craftItem();
			}
        	if(currentEnergy >= 7) {
        		currentEnergy -= 7;
        		heat += 3;
        	}
        	if(worldObj.getWorldTime() - lastTime > 1000L){
        		isCrafting = false;
        	}
        }
		
		int energyReq = MAX_STORAGE - currentEnergy;
		if(inventory[1] != null && energyReq > 0) {
			currentEnergy += EnergyUtils.tryChargeFromStack(inventory[1], energyReq);
			if(inventory[1].stackSize <= 0)
				inventory[1] = null;
		}
		
		if(++this.tickUpdate > 3)
			this.onInventoryChanged();
	}
	
	@Override
	protected void writeRecipeInfoToSlot() {
		clearRecipeInfo();
		int length;
		length = RecipeWeapons.getECRecipeLength(this.page);
		
		for (int i = 0; i < length && i < 3; i++) {
			RecipeCrafter r = RecipeWeapons.getECRecipe(this.page, i+ scrollFactor);
			if(r == null)
				return;
			for (int j = 0; j < 3; j++) {
				if (r.input.length > j)
					this.setInventorySlotContents(j + i * 3, r.input[j]);
			}
			this.setInventorySlotContents(9 + i, r.output);
		}
	}

	@Override
	public String getInvName() {
		return "lambdacraft:elcrafter";
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		if (i > 12 && itemstack.getItem() instanceof ItemMaterial)
			return true;
		return true;
	}
	
    /**
     * Reads a tile entity from NBT.
     */
	@Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.currentEnergy = nbt.getInteger("energy");
    }

    /**
     * Writes a tile entity to NBT.
     */
    @Override
	public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("energy", currentEnergy);
    }
	
	public void addScrollFactor(boolean isForward) {
		if (!RecipeWeapons.doesECNeedScrollBar(page))
			return;
		List<RecipeCrafter> recipes[] = RecipeWeapons.recipeEC;
		if (isForward) {
			if (scrollFactor < recipes[page].size() - 3) {
				scrollFactor++;
			}
		} else {
			if (scrollFactor > 0) {
				scrollFactor--;
			}
		}
		this.writeRecipeInfoToSlot();
	}
	
	public void addPage(boolean isForward) {
		List<RecipeCrafter> recipes[] = RecipeWeapons.recipeEC;
		if (isForward) {
			if (page < recipes.length - 1) {
				page++;
			}
		} else {
			if (page > 0) {
				page--;
			}
		}
		scrollFactor = 0;
		this.writeRecipeInfoToSlot();
	}

	public RecipeCrafter getRecipeBySlotAndScroll(int slot, int factor) {
		int i = 0;
		if (slot == 0)
			i = 0;
		if (slot == 4)
			i = 1;
		if (slot == 8)
			i = 2;
		return RecipeWeapons.getECRecipe(page, factor + i);
	}
	
	public int sendEnergy(int amm) {
		EnergyTileSourceEvent event = new EnergyTileSourceEvent(worldObj, this, amm);
		MinecraftForge.EVENT_BUS.post(event);
		return event.amount;
	}

	@Override
	public boolean acceptsEnergyFrom(TileEntity paramTileEntity,
			LCDirection paramDirection) {
		return true;
	}

	@Override
	public boolean isAddToEnergyNet() {
		return isLoad;
	}

	@Override
	public int demandsEnergy() {
		return MAX_STORAGE - currentEnergy;
	}

	@Override
	public int injectEnergy(LCDirection paramDirection, int paramInt) {
		this.currentEnergy += paramInt;
		if(currentEnergy > MAX_STORAGE) {
			int amt = currentEnergy - MAX_STORAGE;
			currentEnergy = MAX_STORAGE;
			return amt;
		}
		return 0;
	}

	@Override
	public int getMaxSafeInput() {
		return 128;
	}

	
	
	//IC2 Compatibility
	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, Direction direction) {
		return true;
	}

	@Override
	public boolean isAddedToEnergyNet() {
		return isLoad;
	}

	@Override
	public int injectEnergy(Direction directionFrom, int amount) {
        //if (amount > this.maxStorage)
        //{
        	//this.worldObj.createExplosion(null, this.xCoord,  this.yCoord,  this.zCoord, 2F, true);
        	//invalidate();
            //return 0;
        //}
        //else
        {
            this.currentEnergy += amount;
            int var3 = 0;
            if (this.currentEnergy > this.MAX_STORAGE)
            {
                var3 = this.currentEnergy - this.MAX_STORAGE;
                this.currentEnergy = this.MAX_STORAGE;
            }
            return var3;
        }
	}

	
	
}
