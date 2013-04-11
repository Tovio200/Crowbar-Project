package cbproject.elements.renderers;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Vec3;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.TextureFXManager;

public class RendererUtils {
	
	/** The minimum X value for rendering (default 0.0). */
    public double minX;

    /** The maximum X value for rendering (default 1.0). */
    public double maxX;

    /** The minimum Y value for rendering (default 0.0). */
    public double minY;

    /** The maximum Y value for rendering (default 1.0). */
    public double maxY;

    /** The minimum Z value for rendering (default 0.0). */
    public double minZ;

    /** The maximum Z value for rendering (default 1.0). */
    public double maxZ;
    
    public static void addVertex(Vec3 vec3, double texU, double texV){
    	Tessellator tessellator = Tessellator.instance;
    	tessellator.addVertexWithUV(vec3.xCoord, vec3.yCoord, vec3.zCoord, texU, texV);
    }
    
    protected void setBound(Block block){
    	
    	minX = block.getBlockBoundsMinX();
    	minY = block.getBlockBoundsMinY();
    	minZ = block.getBlockBoundsMinZ();
    	maxX = block.getBlockBoundsMaxX();
    	maxY = block.getBlockBoundsMaxY();
    	maxZ = block.getBlockBoundsMaxZ();
    	
    }
    
    protected int getTexture(String path){
    	return FMLClientHandler.instance().getClient().renderEngine.getTexture(path);
    }
    
    public void setBlockBounds(double par1, double par3, double par5, double par7, double par9, double par11)
    {
            this.minX = par1;
            this.maxX = par7;
            this.minY = par3;
            this.maxY = par9;
            this.minZ = par5;
            this.maxZ = par11;
    }
    
    public void addCoord(double offX, double offY, double offZ){
    	
    	minX += offX;
    	maxX += offX;
    	minY += offY;
    	maxY += offY;
    	minZ += offZ;
    	maxZ += offZ;
    	
    }
    
    public static void renderItemIn2d(Tessellator t, float u1, float v1, float u2, float v2, double w){
    	
    	Vec3 a1 = newV3(0, 0, w), 
   			 a2 = newV3(1, 0, w),
   			 a3 = newV3(1, 1, w),
   			 a4 = newV3(0, 1, w),
   			 
   			 a5 = newV3(0, 0, -w),
   			 a6 = newV3(1, 0, -w),
   			 a7 = newV3(1, 1, -w),
   			 a8 = newV3(0, 1, -w);
   			
   		t = Tessellator.instance;
   		
   		t.startDrawingQuads();
   		t.setNormal(0.0F, 0.0F, 1.0F);
   		addVertex(a1, u2, v2);
   		addVertex(a2, u1, v2);
   		addVertex(a3, u1, v1);
   		addVertex(a4, u2, v1);
   		t.draw();
   		
   	    t.startDrawingQuads();
   	    t.setNormal(0.0F, 0.0F, -1.0F); 
   	    addVertex(a8, u2, v1);
   	    addVertex(a7, u1, v1);
   	    addVertex(a6, u1, v2);
   	    addVertex(a5, u2, v2);
   	    t.draw();
   	    
           int var7;
           float var8;
           float var9;
           float var10;
           /* Gets the width/16 of the currently bound texture, used
            * to fix the side rendering issues on textures != 16 */
           int tileSize = TextureFXManager.instance().getTextureDimensions(GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D)).width / 16;
           float tx = 1.0f / (32 * tileSize);
           float tz = 1.0f /  tileSize;
           
           t.startDrawingQuads();
           t.setNormal(-1.0F, 0.0F, 0.0F);
           for (var7 = 0; var7 < tileSize; ++var7)
           { //从上到下绘制tileSize个平面，
               var8 = (float)var7 / tileSize; //绘制的像素位移(0-32)
               var9 = u2 - (u2 - u1) * var8 - tx; //u的位移
               var10 = 1.0F * var8; //=像素位移
               t.addVertexWithUV((double)var10, 0.0D, -w, (double)var9, (double)v2);
               t.addVertexWithUV((double)var10, 0.0D, w, (double)var9, (double)v2);
               t.addVertexWithUV((double)var10, 1.0D, w, (double)var9, (double)v1);
               t.addVertexWithUV((double)var10, 1.0D, -w, (double)var9, (double)v1);
               
               t.addVertexWithUV((double)var10, 1.0D, w, (double)var9, (double)v1);
               t.addVertexWithUV((double)var10, 0.0D, w, (double)var9, (double)v2);
               t.addVertexWithUV((double)var10, 0.0D, -w, (double)var9, (double)v2);
               t.addVertexWithUV((double)var10, 1.0D, -w, (double)var9, (double)v1);
           }
           t.draw();
           
    }
    
    public static Vec3 newV3(double x, double y, double z){
    	return Vec3.createVectorHelper(x, y, z);
    }
    
    
    
}