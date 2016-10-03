package eastonium.nuicraft.particle;

import eastonium.nuicraft.Bionicle;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TextureStitcherLightstoneFX
{
  @SubscribeEvent
  public void stitcherEventPre(TextureStitchEvent.Pre event) {
    ResourceLocation particleRL = new ResourceLocation(Bionicle.MODID + ":particle/lightstoneParticle");
    //event.map.registerSprite(particleRL);
    event.getMap().registerSprite(particleRL);
  }
}