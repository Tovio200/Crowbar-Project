﻿package cn.lambdacraft.api.energy;

import cn.lambdacraft.api.LCDirection;
import net.minecraft.tileentity.TileEntity;

public abstract interface IEnergyEmitter extends IEnergyTile {
	public abstract boolean emitsEnergyTo(TileEntity paramTileEntity,
			LCDirection paramDirection);
}