/** 
 * Copyright (c) Lambda Innovation Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.cn/
 * 
 * The mod is open-source. It is distributed under the terms of the
 * Lambda Innovation Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * 本Mod是完全开源的，你允许参考、使用、引用其中的任何代码段，但不允许将其用于商业用途，在引用的时候，必须注明原作者。
 */
package cn.weaponmod;


import net.minecraft.command.CommandHandler;
import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.Logger;

import cn.weaponmod.events.WMEventListener;
import cn.weaponmod.network.MessageClicking;
import cn.weaponmod.network.MessageDM;
import cn.weaponmod.proxy.WMCommonProxy;
import cn.weaponmod.proxy.WMGeneralProps;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/**
 * 自定义武器mod的主注册类。
 * @author WeAthFolD
 */
@Mod(modid = "Weaponry", name = "MyWeaponry API", version = WeaponMod.VERSION)
public class WeaponMod {

	public static final String VERSION = "1.3.0";
	
	public static final String DEPENDENCY = "required-after:Weaponry@" + VERSION;
	
	/**
	 * 日志
	 */
	public static Logger log = FMLLog.getLogger();
	
	@SidedProxy(serverSide = "cn.weaponmod.proxy.WMCommonProxy", clientSide = "cn.weaponmod.proxy.WMClientProxy")
	public static WMCommonProxy proxy;
	
	public static final boolean DEBUG = true; //请在编译时设置为false
	
	public static SimpleNetworkWrapper netHandler = NetworkRegistry.INSTANCE.newSimpleChannel(WMGeneralProps.NET_CHANNEL);
	private static int nextId = 0;

	/**
	 * 预加载（设置、世界生成、注册Event）
	 * @param event
	 */
	@EventHandler()
	public void preInit(FMLPreInitializationEvent event) {

		log.info("Starting MyWeaponary " + VERSION);
		log.info("Copyright (c) Lambda Innovation, 2013");
		log.info("http://www.lambdacraft.cn");
		
		//TODO: Network Registration
		MinecraftForge.EVENT_BUS.register(new WMEventListener());
		netHandler.registerMessage(MessageDM.Handler.class, MessageDM.class, nextId++, Side.SERVER);
		netHandler.registerMessage(MessageClicking.Handler.class, MessageClicking.class, nextId++, Side.SERVER);
		
		proxy.preInit();
	}
	
	/**
	 * 加载（方块、物品、网络处理、其他)
	 * 
	 * @param Init
	 */
	@EventHandler()
	public void init(FMLInitializationEvent Init) {
		proxy.init();
	}
	
	/**
	 * 加载后（保存设置）
	 * 
	 * @param Init
	 */
	@EventHandler()
	public void postInit(FMLPostInitializationEvent Init) {
		
	}
	
	/**
	 * 服务器加载（注册指令）
	 * 
	 * @param event
	 */
	@EventHandler()
	public void serverStarting(FMLServerStartingEvent event) {
		CommandHandler cm = (CommandHandler) event.getServer().getCommandManager();
		if(DEBUG) {
		}
	}

}
