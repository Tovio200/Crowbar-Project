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
package cbproject.core.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import cbproject.api.energy.events.EnergyTileLoadEvent;
import cbproject.api.energy.events.EnergyTileSourceEvent;
import cbproject.api.energy.events.EnergyTileUnloadEvent;
import cbproject.api.energy.tile.IEnergyTile;
import cbproject.core.CBCMod;
/**
 * @author WeAthFolD
 *
 */
public abstract class TileElectrical extends CBCTileEntity implements IEnergyTile,ic2.api.energy.tile.IEnergySink {

	public boolean addedToNet = false;
	
	/**
	 * 
	 */
	public TileElectrical() {
		
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if(!this.addedToNet) {
			if (CBCMod.ic2Installed)
				MinecraftForge.EVENT_BUS.post(new ic2.api.energy.event.EnergyTileLoadEvent(this));			
			
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(worldObj, this));
			System.out.println("Adding " + this + " to Net");
			this.addedToNet = true;
		}
	}

    @Override
    public void invalidate()
    {
        if (this.addedToNet)
        {
			if (CBCMod.ic2Installed)
				MinecraftForge.EVENT_BUS.post(new ic2.api.energy.event.EnergyTileUnloadEvent(this));	
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(worldObj,this));
            this.addedToNet = false;
        }

        super.invalidate();
    }
	
	@Override
	public void onTileUnload() {
		super.onTileUnload();
		
		if (CBCMod.ic2Installed)
			MinecraftForge.EVENT_BUS.post(new ic2.api.energy.event.EnergyTileUnloadEvent(this));
		
		
		MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(worldObj, this));
		this.addedToNet = false;
	}
	
	@Override
	public boolean isAddToEnergyNet() {
		return addedToNet;
	}
	
	//IC2
	public boolean isAddedToEnergyNet(){
		return addedToNet;
	}
	
	public int sendEnergy(int amm) {
		int amount=0;
		EnergyTileSourceEvent event = new EnergyTileSourceEvent(worldObj, this, amm);
		MinecraftForge.EVENT_BUS.post(event);
		amount+= event.amount;
		if (CBCMod.ic2Installed){
			ic2.api.energy.event.EnergyTileSourceEvent event2 = new ic2.api.energy.event.EnergyTileSourceEvent((ic2.api.energy.tile.IEnergySource)this, amm);
			MinecraftForge.EVENT_BUS.post(event2);
			amount= event2.amount;
		}
		return amount;
	}

	
}
