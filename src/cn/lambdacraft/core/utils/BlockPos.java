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
package cn.lambdacraft.core.utils;

import net.minecraft.tileentity.TileEntity;

/**
 * 提供方块位置信息的实用类。
 * 
 * @author WeAthFolD
 * 
 */
public class BlockPos {

	public int x, y, z, blockID;

	public BlockPos(int par1, int par2, int par3, int bID) {
		x = par1;
		y = par2;
		z = par3;
		blockID = bID;
	}
	
	public BlockPos(TileEntity te) {
		this(te.xCoord, te.yCoord, te.zCoord, te.worldObj.getBlockId(te.xCoord, te.yCoord, te.zCoord));
	}

	@Override
	public boolean equals(Object b) {
		if (b == null || !(b instanceof BlockPos))
			return false;
		BlockPos bp = (BlockPos) b;
		return (x == bp.x && y == bp.y && z == bp.z);
	}
	
	@Override
	public int hashCode() {
		return x << 20 + y << 10 + z;
	}

	public BlockPos copy() {
		return new BlockPos(x, y, z, blockID);
	}

}
