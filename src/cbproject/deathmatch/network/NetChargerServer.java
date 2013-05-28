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
package cbproject.deathmatch.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cbproject.core.props.GeneralProps;
import cbproject.core.register.CBCNetHandler;
import cbproject.core.register.IChannelProcess;
import cbproject.deathmatch.blocks.tileentities.TileEntityArmorCharger;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

/**
 * @author Administrator
 *
 */
public class NetChargerServer implements IChannelProcess {

	public static void sendChargerPacket(TileEntityArmorCharger te){
		ByteArrayOutputStream bos = CBCNetHandler.getStream(GeneralProps.NET_ID_CHARGER_SV, 15);
		DataOutputStream outputStream = new DataOutputStream(bos);
		
		try {
	        outputStream.writeInt(te.xCoord);
	        outputStream.writeShort(te.yCoord);
	        outputStream.writeInt(te.zCoord);
	        outputStream.writeInt(te.currentEnergy);
	        outputStream.writeBoolean(te.isRSActivated);
		} catch (Exception ex) {
	        ex.printStackTrace();
		}
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = GeneralProps.NET_CHANNEL_CLIENT;
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		PacketDispatcher.sendPacketToAllAround(te.xCoord, te.yCoord, te.zCoord, 24,
				te.worldObj.getWorldInfo().getDimension(), packet);
	}
	
	@Override
	public void onPacketData(DataInputStream stream, Player player) {
		World world = ((EntityPlayerSP)player).worldObj;
		
		int x, y, z;
		int energy;
		boolean rs;
		
		try {
			x=stream.readInt();
			y=stream.readShort();
			z=stream.readInt();
			energy = stream.readInt();
			rs = stream.readBoolean();
			TileEntity te = world.getBlockTileEntity(x,y,z);
			if(te == null || !(te instanceof TileEntityArmorCharger))
				return;
			else {
				TileEntityArmorCharger tt = (TileEntityArmorCharger) te;
				tt.currentEnergy = energy;
				tt.isRSActivated = rs;
			}
			
		} catch (Exception ex) {
	        ex.printStackTrace();
		}
		
		
	}
}