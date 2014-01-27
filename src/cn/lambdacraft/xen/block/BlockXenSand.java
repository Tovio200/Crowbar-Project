package cn.lambdacraft.xen.block;

import net.minecraft.block.material.Material;
import cn.lambdacraft.core.block.CBCBlock;

/**
 * Xen沙
 * @author F
 *
 */
public class BlockXenSand extends CBCBlock {

	public BlockXenSand(int par1)
	{
		super(par1, Material.sand);
		this.setUnlocalizedName("xensand");
		this.setIconName("xen_sand");
		this.setHardness(0.5F);
		this.setStepSound(soundSandFootstep);
	}
}
