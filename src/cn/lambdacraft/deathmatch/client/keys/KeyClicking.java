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
package cn.lambdacraft.deathmatch.client.keys;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cn.lambdacraft.api.weapon.ISpecialUseable;
import cn.lambdacraft.core.register.IKeyProcess;
import cn.lambdacraft.deathmatch.utils.ItemHelper;

/**
 * @author WeAthFolD
 * 
 */
public class KeyClicking implements IKeyProcess {
	
	private boolean isLeft;
	
	public KeyClicking(boolean left) {
		isLeft = left;
	}

	@Override
	public void onKeyDown(boolean isEnd) {
		if(isEnd)
			return;
		Minecraft mc = Minecraft.getMinecraft();
		EntityClientPlayerMP player = mc.thePlayer;
		if (player == null)
			return;
		ItemStack stack = player.getCurrentEquippedItem();
		if (stack != null) {
			Item item = stack.getItem();
			if (item instanceof ISpecialUseable) {
				((ISpecialUseable) item).onItemClick(mc.theWorld, player, stack, isLeft);
			}
		}
	}

	@Override
	public void onKeyUp(boolean isEnd) {
		if(!isEnd)
			return;
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.thePlayer;
		if (player == null)
			return;
		ItemHelper.stopUsingItem(player);
	}

}
