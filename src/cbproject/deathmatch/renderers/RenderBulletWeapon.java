package cbproject.deathmatch.renderers;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cbproject.core.props.ClientProps;
import cbproject.core.renderers.RenderUtils;
import cbproject.deathmatch.items.wpns.WeaponGeneralBullet;
import cbproject.deathmatch.utils.InformationBullet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

public class RenderBulletWeapon implements IItemRenderer {

	Tessellator t = Tessellator.instance;
	Minecraft mc = Minecraft.getMinecraft();
	float tx = 0, ty = 0, tz = 0;
	float width = 0.1F;
	private WeaponGeneralBullet weaponType;
	
	public RenderBulletWeapon(WeaponGeneralBullet weapon, float width) {
		weaponType = weapon;
	}
	
	public RenderBulletWeapon(WeaponGeneralBullet weapon, float width, float x, float y, float z) {
		tx = x;
		ty = y;
		tz = z;
		weaponType = weapon;
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		// TODO Auto-generated method stub
		switch (type) {
		case EQUIPPED:
			return true;
		default:
			return false;
		}

	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		switch (helper) {
		case ENTITY_ROTATION:
		case ENTITY_BOBBING:
			return true;

		default:
			return false;

		}
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch (type) {
		case EQUIPPED:
			renderEquipped(item, (RenderBlocks) data[0], (EntityLiving) data[1]);
			break;
		default:
			break;

		}

	}
	
	public void renderEquipped(ItemStack item, RenderBlocks render,
			EntityLiving entity) {
		
		if(item.stackTagCompound == null)
			item.stackTagCompound = new NBTTagCompound();
		int mode = item.getTagCompound().getInteger("mode");
		GL11.glPushMatrix();
		
		bindTextureByItem(item);
		Icon icon = entity.getItemIcon(item, 0);

        if (icon == null)
        {
            GL11.glPopMatrix();
            return;
        }

        if (item.getItemSpriteNumber() == 0)
        {
            this.mc.renderEngine.bindTexture("/terrain.png");
        }
        else
        {
            this.mc.renderEngine.bindTexture("/gui/items.png");
        }

        Tessellator tessellator = Tessellator.instance;
        float f = icon.getMinU();
        float f1 = icon.getMaxU();
        float f2 = icon.getMinV();
        float f3 = icon.getMaxV();
        RenderUtils.renderItemIn2D(tessellator, f1, f2, f, f3, icon.getSheetWidth(), icon.getSheetHeight(), width);
		
		boolean rendMuz = false;
		InformationBullet inf = weaponType.getInformation(item, entity.worldObj);
		if(inf != null){
			if(inf.isShooting && inf.getDeltaTick() < 3)
				rendMuz = true;
		}
		if(rendMuz)
			RenderMuzzleFlash.renderItemIn2d(t, tx, ty, tz);
		GL11.glPopMatrix();

	}
	
	protected void addVertex(Vec3 vec3, double texU, double texV) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.addVertexWithUV(vec3.xCoord, vec3.yCoord, vec3.zCoord,
				texU, texV);
	}
	
	private void bindTextureByItem(ItemStack item){
		if(item.stackTagCompound == null)
			item.stackTagCompound = new NBTTagCompound();
		int mode = item.getTagCompound().getInteger("mode");
		int tex = RenderUtils.getTexture(ClientProps.ITEM_SATCHEL_PATH[mode]);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
	}

}