package eastonium.nuicraft.util;

import eastonium.nuicraft.kanoka.freezeEntity.EntityFreezeIce;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NuiCraftEventHooks {

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onMount(EntityMountEvent event) {
		if (event.getEntityBeingMounted() instanceof EntityFreezeIce && event.isDismounting()) {
			if (event.getEntityMounting() instanceof EntityPlayer && ((EntityPlayer)event.getEntityMounting()).capabilities.isCreativeMode) return;
			if (event.getEntityBeingMounted().isDead) {
				if (event.getEntityBeingMounted().isRiding()) {
					Entity mount = event.getEntityBeingMounted().getRidingEntity();
					event.getEntityBeingMounted().dismountRidingEntity();
					event.getEntityMounting().startRiding(mount);	
				}
				return;
			}			
			if (event.getEntityMounting().isDead) return;
			event.setCanceled(true);			
		}				
	}
}