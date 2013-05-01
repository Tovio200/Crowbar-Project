package cbproject.crafting.items;

import cbproject.core.CBCMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;

public class Bullet_steelbow extends ItemBullet{

	public Bullet_steelbow(int id) {
		super(id);
		setCreativeTab(CBCMod.cct);
		setUnlocalizedName("steelbow");
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister par1IconRegister)
    {
        this.iconIndex = par1IconRegister.registerIcon("lambdacraft:steelbow");
    }

}