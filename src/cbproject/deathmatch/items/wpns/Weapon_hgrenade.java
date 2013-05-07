package cbproject.deathmatch.items.wpns;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.core.CBCMod;
import cbproject.deathmatch.entities.EntityHGrenade;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Hand grenade weapon class.
 * @author WeAthFolD
 *
 */
public class Weapon_hgrenade extends Item {

	public Weapon_hgrenade(int par1ID) {
		super(par1ID);
		setUnlocalizedName("weapon_hgrenade");
		setCreativeTab( CBCMod.cct );
		setMaxStackSize(4);
		this.maxStackSize = 8;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("lambdacraft:weapon_hgrenade");
    }
	
	@Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
    {

		if( par4 >= 395 ){
			return;
		}
		
		int duration = (par4 > 340) ? 400 - par4 : 60 ; //used time: if large than 3s use 3s

		if ( par1ItemStack.stackSize > 0)
			par2World.spawnEntityInWorld(new EntityHGrenade(par2World, par3EntityPlayer, duration));
		
        if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
            --par1ItemStack.stackSize;
            if(par1ItemStack.stackSize == 0)
            	par1ItemStack = null;
        }
		
		return;
    }
	

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
	
        par2World.playSoundAtEntity(par3EntityPlayer, "cbc.weapons.hgrenadepin", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
        
    }
	
    @Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 400; //20s
    }
    
    
}
