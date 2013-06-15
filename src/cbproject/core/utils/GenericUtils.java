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
package cbproject.core.utils;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;

/**
 * @author Administrator
 *
 */
public class GenericUtils {

	public static IEntitySelector selectorLiving = new EntitySelectorLiving();
	
	public static double distanceSqTo(Entity ent, double x, double y, double z) {
		if(ent == null)
			throw new NullPointerException();
		double dx = (ent.posX - x), dy = (ent.posY - y), dz = (ent.posZ - z);
		return dx * dx + dy * dy + dz * dz;
	}
	
	public static double distanceTo(Entity ent, double x, double y, double z) {
		return Math.sqrt(distanceSqTo(ent, x, y, z));
	}
	
}
