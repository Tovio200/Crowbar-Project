package cbproject.Elements.Blocks;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cbproject.Misc.CCT;
import cbproject.Proxy.ClientProxy;
import cbproject.CBCMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.IArmorTextureProvider;

public class Test_Block extends Block {
	public Test_Block(int id,Material material) {
		super(id,material);//��ձ��Material����
		//������������
		setBlockName("Test_Block");
		setCreativeTab(CBCMod.cct);
		setTextureFile(ClientProxy.BLOCKS_TEXTURE_PATH );
		setHardness(10.0F);
		

	}

}