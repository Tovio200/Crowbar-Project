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
import cbproject.api.energy.item.ICustomEnItem;
import cbproject.api.energy.item.IEnItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

/**
 * @author WeAthFolD
 *
 */
public class TileGeneratorFire extends TileGeneratorBase implements IInventory{

	public ItemStack[] slots = new ItemStack[2];
	public boolean isBurning = false;
	public int tickLeft = 0, maxBurnTime = 0;
	
	/**
	 * @param tier
	 * @param store
	 */
	public TileGeneratorFire() {
		super(1, 5000);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public void updateEntity() {
		super.updateEntity();
		if(worldObj.isRemote)
			return;
		
		if(isBurning) {
			tickLeft--;
			currentEnergy += this.sendEnergy(5);
			if(currentEnergy > maxStorage)
				currentEnergy = maxStorage;
			if(tickLeft <= 0)
				isBurning = false;
		} else {
			if(currentEnergy < maxStorage)
				tryBurn();
		}
		
		if(currentEnergy > 0){
			int all = currentEnergy > 5 ?  5 : currentEnergy;
			int rev = sendEnergy(all);
			currentEnergy -= (all - rev);
			if(slots[1] != null) {
				currentEnergy -= ((ICustomEnItem)slots[1].getItem()).charge(slots[1], currentEnergy, 2, false, false);
			}
		}
		
	}
	
	private void tryBurn() {
		if(slots[0] != null) {
			this.tickLeft = TileEntityFurnace.getItemBurnTime(slots[0]) / 4;
    		this.maxBurnTime = this.tickLeft;
    		if(maxBurnTime == 0)
    			return;
    		if(slots[0].itemID == Item.bucketLava.itemID) {
    			slots[0].itemID = Item.bucketEmpty.itemID;
    		} else {
    			if(--slots[0].stackSize <= 1)
    				slots[0] = null;
    		}
    		isBurning = true;
		}
	}
	
    /**
     * Reads a tile entity from NBT.
     */
	@Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        for(int i = 0; i < slots.length; i++){
        	short id = nbt.getShort("id" + i), damage = nbt.getShort("damage" + i);
        	byte count = nbt.getByte("count" + i);
        	if(id == 0)
        		continue;
        	ItemStack is = new ItemStack(id, count, damage);
        	slots[i] = is;
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    @Override
	public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        for(int i = 0; i < slots.length; i++){
        	if(slots[i] == null)
        		continue;
        	nbt.setShort("id"+i, (short) slots[i].itemID);
        	nbt.setByte("count"+i, (byte) slots[i].stackSize);
        	nbt.setShort("damage"+i, (short)slots[i].getItemDamage());
        }
    }
	
	@Override
	public int getMaxEnergyOutput() {
		return 5;
	}


	@Override
	public int getSizeInventory() {
		return 2;
	}


	@Override
	public ItemStack getStackInSlot(int i) {
		return slots[i];
	}


	@Override
	public ItemStack decrStackSize(int slot, int amt) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			if (stack.stackSize <= amt) {
				setInventorySlotContents(slot, null);
			} else {
				stack = stack.splitStack(amt);
				if (stack.stackSize == 0) {
					setInventorySlotContents(slot, null);
				}
			}
		}
		return stack;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5,
				zCoord + 0.5) <= 64;
	}


	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return slots[i];
	}


	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		slots[i] = itemstack;
	}


	@Override
	public String getInvName() {
		return "cbc.tile.genfire";
	}


	@Override
	public boolean isInvNameLocalized() {
		return false;
	}


	@Override
	public int getInventoryStackLimit() {
		return 64;
	}


	@Override
	public void openChest() {}


	@Override
	public void closeChest() {}


	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		return true;
	}
}
