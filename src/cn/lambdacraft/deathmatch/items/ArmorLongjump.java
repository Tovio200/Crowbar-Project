package cn.lambdacraft.deathmatch.items;

import cn.lambdacraft.core.item.CBCGenericArmor;
import cn.lambdacraft.core.proxy.ClientProps;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.EnumHelper;

public class ArmorLongjump extends CBCGenericArmor {

	public static int reductionAmount[] = { 0, 0, 0, 0 };
	public static EnumArmorMaterial material = EnumHelper.addArmorMaterial(
			"armorLJ", 0, reductionAmount, 0);

	public ArmorLongjump(int par1, int armorType) {
		super(par1, material, 2, armorType);
		setIAndU("longjump");
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot,
			int layer) {
		return ClientProps.LONGJUMP_ARMOR_PATH;
	}
}