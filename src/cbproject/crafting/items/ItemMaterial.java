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
package cbproject.crafting.items;

import java.util.HashMap;
import java.util.List;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.core.CBCMod;
import cbproject.core.item.CBCGenericItem;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemMaterial extends CBCGenericItem {
	
	private Icon icons[] = new Icon[10]; //声明Icon对象数组
	
	public enum EnumMaterial {
		BOX(0), AMMUNITION(1), ARMOR(2), ACCESSORIES(3), BIO(4), EXPLOSIVE(5), HEAVY(
				6), LIGHT(7), PISTOL(8), TECH(9);
		
		private int id;

		private static HashMap<Integer, EnumMaterial> map = new HashMap();
		static {
			for (EnumMaterial a : EnumMaterial.values()) {
				map.put(a.ordinal(), a);
			}
		}

		private EnumMaterial(int i) {
			this.id = i;
		}

		@Override
		public String toString() {
			return this.name().toLowerCase();
		}
	}
	
	EnumMaterial mat = EnumMaterial.BOX; //声明mat为材料枚举中的盒子
	
	public ItemMaterial(int par1) {
		super(par1);
		this.setUnlocalizedName("material"); //默认UnlocalizedName
	}
	
	/**
	 * 注册Icons
	 * 
	 * @param ir IconRegister
	 */
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir)
    {
        for(EnumMaterial i : EnumMaterial.values()) {
        	icons[i.id] = ir.registerIcon("lambdacraft:mat_" + i.name().toLowerCase());
        }
    }
	
    /**
     * 
     * 返回一个这个物品的UnlocalizedName，这个版本接受一个ItemStack，所以不同的ItemStacks基于它们的伤害值或者NBT Tag可以有不同的名字
     * 
     * @param par1ItemStack ItemStack
     */
    @Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        return "item.mat_" + EnumMaterial.map.get(par1ItemStack.getItemDamage());
    }
	
	public ItemStack newStack(int stackSize, EnumMaterial mat) {
		return new ItemStack(this.itemID, stackSize, mat.id);
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int par1)
    {
        return this.icons[par1];
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	for(int i = 0; i < 10; i++)
    		par3List.add(new ItemStack(par1, 1, i));
    }

}
