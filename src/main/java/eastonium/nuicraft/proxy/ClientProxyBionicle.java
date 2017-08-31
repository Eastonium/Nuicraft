package eastonium.nuicraft.proxy;

import eastonium.nuicraft.ClientTickHandler;
import eastonium.nuicraft.NuiCraft;
import eastonium.nuicraft.NuiCraftBlocks;
import eastonium.nuicraft.NuiCraftFluids;
import eastonium.nuicraft.NuiCraftItems;
import eastonium.nuicraft.kanohi.ItemColoredMask;
import eastonium.nuicraft.kanohi.ModelAkakuMataMask;
import eastonium.nuicraft.kanohi.ModelLongMask;
import eastonium.nuicraft.kanohi.ModelMaskIgnika;
import eastonium.nuicraft.kanoka.EntityKanoka;
import eastonium.nuicraft.kanoka.RenderKanoka;
import eastonium.nuicraft.mobs.fikou.EntityFikou;
import eastonium.nuicraft.mobs.fikou.ModelFikou;
import eastonium.nuicraft.mobs.fikou.RenderFikou;
import eastonium.nuicraft.mobs.hoi.EntityHoi;
import eastonium.nuicraft.mobs.hoi.ModelHoi;
import eastonium.nuicraft.mobs.hoi.RenderHoi;
import eastonium.nuicraft.mobs.mahi.EntityMahi;
import eastonium.nuicraft.mobs.mahi.ModelMahi;
import eastonium.nuicraft.mobs.mahi.RenderMahi;
import eastonium.nuicraft.particle.TextureStitcherLightstoneFX;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxyBionicle extends CommonProxyBionicle
{	
	private static final ModelLongMask maskLong = new ModelLongMask();
	private static final ModelAkakuMataMask maskMataAkaku = new ModelAkakuMataMask();
	private static final ModelMaskIgnika maskIgnika = new ModelMaskIgnika();	
	
	@Override
	public void preInit(){
		super.preInit();
		OBJLoader.INSTANCE.addDomain(NuiCraft.MODID);
		MinecraftForge.EVENT_BUS.register(new TextureStitcherLightstoneFX());
		MinecraftForge.EVENT_BUS.register(new ClientTickHandler());

		RenderingRegistry.registerEntityRenderingHandler(EntityKanoka.class, new IRenderFactory<EntityKanoka>(){
			public Render<? super EntityKanoka> createRenderFor(RenderManager manager) {
			return new RenderKanoka(manager, Minecraft.getMinecraft().getRenderItem());
		}});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityMahi.class, new IRenderFactory<EntityMahi>(){
			public Render<? super EntityMahi> createRenderFor(RenderManager manager) {
			return new RenderMahi(manager, new ModelMahi(), 0.6F);
		}});

		RenderingRegistry.registerEntityRenderingHandler(EntityFikou.class, new IRenderFactory<EntityFikou>(){
			public Render<? super EntityFikou> createRenderFor(RenderManager manager) {
				return new RenderFikou(manager, new ModelFikou(), 0.3F);
			}});

        RenderingRegistry.registerEntityRenderingHandler(EntityHoi.class, new IRenderFactory<EntityHoi>(){
            public Render<? super EntityHoi> createRenderFor(RenderManager manager) {
                return new RenderHoi(manager, new ModelHoi(), 0.3F);
            }});
	}
	
	@Override
	public void init(){
		super.init();
		
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor(){
            public int getColorFromItemstack(ItemStack stack, int tintIndex){
            	if (tintIndex > 0 || !((ItemColoredMask)stack.getItem()).hasColor(stack)){
            		return -1;
            	} else return ((ItemColoredMask)stack.getItem()).getColor(stack);
            }
        }, NuiCraftItems.mask_mata_kakama, NuiCraftItems.mask_mata_pakari, NuiCraftItems.mask_mata_kaukau, 
        		NuiCraftItems.mask_mata_miru, NuiCraftItems.mask_mata_hau, NuiCraftItems.mask_mata_akaku);
	}
	
	public void postInit(){
		
	}

	public ModelBiped getArmorModel(int id){
		switch (id){
		case 0:	return maskMataAkaku;
		case 1:	return maskIgnika;
		default: return maskLong;
		}		
	}
	
	@SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        NuiCraftBlocks.initModels();
        NuiCraftFluids.initModels();
		NuiCraftItems.initModels();
    }
}
