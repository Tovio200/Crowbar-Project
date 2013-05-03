package cbproject.deathmatch.keys;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import cbproject.core.register.IKeyProcess;
import cbproject.deathmatch.items.wpns.WeaponGeneral;
import cbproject.deathmatch.network.NetDeathmatch;
import cbproject.deathmatch.utils.InformationWeapon;

public class DMMode implements IKeyProcess {

	@Override
	public void onKeyDown() {
		EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
		if(player == null)
			return;
		ItemStack currentItem = player.inventory.getCurrentItem();
		if (currentItem != null
				&& currentItem.getItem() instanceof WeaponGeneral) {
			WeaponGeneral wpn = (WeaponGeneral) currentItem.getItem();
			InformationWeapon inf = wpn.loadInformation(currentItem, player);
			onModeChange(currentItem, inf, player, wpn.maxModes);
		}
	}

	@Override
	public void onKeyUp() {
	}

	private void onModeChange(ItemStack itemStack, InformationWeapon inf,
			EntityPlayer player, int maxModes) {
		if (!player.worldObj.isRemote)
			return;

		WeaponGeneral wpn = (WeaponGeneral) itemStack.getItem();
		int stackInSlot = -1;
		for (int i = 0; i < player.inventory.mainInventory.length; i++) {
			if (player.inventory.mainInventory[i] == itemStack) {
				stackInSlot = i;
				break;
			}
		}
		if (stackInSlot == -1)
			return;

		if (inf == null)
			return;
		inf.mode = (maxModes - 1 <= inf.mode) ? 0 : inf.mode + 1;
		player.sendChatToPlayer(StatCollector.translateToLocal("mode.new")
				+ ": \u00a74"
				+ StatCollector.translateToLocal(wpn
						.getModeDescription(inf.mode)));
		NetDeathmatch.sendModePacket(stackInSlot, (short) 0, inf.mode);

	}

}
