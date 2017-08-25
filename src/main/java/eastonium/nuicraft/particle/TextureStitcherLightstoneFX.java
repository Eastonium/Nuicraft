package eastonium.nuicraft.particle;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TextureStitcherLightstoneFX
{
  @SubscribeEvent
  public void stitcherEventPre(TextureStitchEvent.Pre event) {
    ResourceLocation particleRL = new ResourceLocation(NuiCraft.MODID + ":particle/lightstone_particle");
    event.getMap().registerSprite(particleRL);
  }
}