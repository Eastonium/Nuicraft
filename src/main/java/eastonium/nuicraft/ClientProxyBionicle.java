package eastonium.nuicraft;

import eastonium.nuicraft.blocks.BlockBamboo;
import eastonium.nuicraft.blocks.BlockProtodermisDeposit;
import eastonium.nuicraft.items.ItemBlockKoro;
import eastonium.nuicraft.kanohi.ItemColoredMask;
import eastonium.nuicraft.kanohi.ItemGoldMataMask;
import eastonium.nuicraft.kanohi.ItemMaskMeta;
import eastonium.nuicraft.kanohi.ModelAkakuMataMask;
import eastonium.nuicraft.kanohi.ModelLongMask;
import eastonium.nuicraft.kanohi.ModelMaskIgnika;
import eastonium.nuicraft.kanoka.EntityDisc;
import eastonium.nuicraft.mobs.mahi.EntityMahi;
import eastonium.nuicraft.mobs.mahi.ModelMahi;
import eastonium.nuicraft.mobs.mahi.RenderMahi;
import eastonium.nuicraft.particle.TextureStitcherLightstoneFX;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxyBionicle extends CommonProxyBionicle
{	
	private static final ModelLongMask maskLong = new ModelLongMask();
	private static final ModelAkakuMataMask maskMataAkaku = new ModelAkakuMataMask();
	private static final ModelMaskIgnika maskIgnika = new ModelMaskIgnika();	
	
	@Override
	public void preInit(){
		super.preInit();
		OBJLoader.INSTANCE.addDomain(Bionicle.MODID);
		MinecraftForge.EVENT_BUS.register(new TextureStitcherLightstoneFX());
		MinecraftForge.EVENT_BUS.register(new ClientTickHandler());
		
		//MinecraftForge.EVENT_BUS.register(new BionicleKeyHandler());		
		//RenderingRegistry.registerEntityRenderingHandler(EntityKolhiiBall.class, new RenderKolhiiBall());

		RenderingRegistry.registerEntityRenderingHandler(EntityDisc.class, new IRenderFactory<EntityDisc>(){
			public Render<? super EntityDisc> createRenderFor(RenderManager manager) {
			return new RenderSnowball(manager, Bionicle.kanokaFlying, Minecraft.getMinecraft().getRenderItem());
		}});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityMahi.class, new IRenderFactory<EntityMahi>(){
			public Render<? super EntityMahi> createRenderFor(RenderManager manager) {
			return new RenderMahi(manager, new ModelMahi(), 0.2F);
		}});
		
		setTextureLocation(Bionicle.MaskForge);
		
		ModelLoader.setCustomModelResourceLocation(Item.REGISTRY.getObject(Bionicle.Purifier.getRegistryName()), 0, new ModelResourceLocation(Bionicle.Purifier.getRegistryName(), "normal"));
		ModelLoader.setCustomModelResourceLocation(Bionicle.purifierItem, 0, new ModelResourceLocation(Bionicle.Purifier.getRegistryName(), "inventory"));
		
		//setTextureLocation(Bionicle.StoneKoroBlock);
		//setTextureLocation(Bionicle.LitKoroBlock);
		//setTextureLocation(Bionicle.IceKoroBlock);
		//setTextureLocation(Bionicle.LeafyKoroBlock);

		Item item = Item.REGISTRY.getObject(Bionicle.StoneKoroBlock.getRegistryName());
		for(int i = 0; i < 9; i++){
			ModelResourceLocation modReLoc = new ModelResourceLocation(Bionicle.StoneKoroBlock.getRegistryName().toString(), "meta=" + i);
			ModelLoader.setCustomModelResourceLocation(item, i, modReLoc);
		}		
		item = Item.REGISTRY.getObject(Bionicle.LitKoroBlock.getRegistryName());
		for(int i = 0; i < 2; i++){
			ModelResourceLocation modReLoc = new ModelResourceLocation(Bionicle.LitKoroBlock.getRegistryName().toString(), "meta=" + i);
			ModelLoader.setCustomModelResourceLocation(item, i, modReLoc);
		}		
		item = Item.REGISTRY.getObject(Bionicle.IceKoroBlock.getRegistryName());
		for(int i = 0; i < 2; i++){
			ModelResourceLocation modReLoc = new ModelResourceLocation(Bionicle.IceKoroBlock.getRegistryName().toString(), "meta=" + i);
			ModelLoader.setCustomModelResourceLocation(item, i, modReLoc);
		}
		item = Item.REGISTRY.getObject(Bionicle.LeafyKoroBlock.getRegistryName());
		for(int i = 0; i < 3; i++){
			ModelResourceLocation modReLoc = new ModelResourceLocation(Bionicle.LeafyKoroBlock.getRegistryName().toString(), "meta=" + i);
			ModelLoader.setCustomModelResourceLocation(item, i, modReLoc);
		}
		
		setTextureLocation(Bionicle.NuvaCube);
		//setTextureLocation(Bionicle.CrystalizedProtodermis);
		setTextureLocation(Bionicle.MataNuiStone);
		setTextureLocation(Bionicle.MakutaStone);
		ModelLoader.setCustomModelResourceLocation(Item.REGISTRY.getObject(Bionicle.Lightstone.getRegistryName()), 0, new ModelResourceLocation(Bionicle.Lightstone.getRegistryName(), "inventory"));
		setTextureLocation(Bionicle.LightstoneOre);
		setTextureLocation(Bionicle.HeatstoneOre);
		
		ModelLoader.setCustomStateMapper(Bionicle.ProtodermisOre, (new StateMap.Builder()).ignore(new IProperty[] {BlockProtodermisDeposit.DROPS}).build());
		setTextureLocation(Bionicle.ProtodermisOre);
		
		ModelLoader.setCustomStateMapper(Bionicle.Bamboo, (new StateMap.Builder()).ignore(new IProperty[] {BlockBamboo.AGE}).build());
		setTextureLocation(Bionicle.Bamboo);
		
		setTextureLocation(Bionicle.BlockProtodermis);
		setTextureLocation(Bionicle.BlockProtosteel);
		
		setTextureLocation(Bionicle.ingotProtodermis);
		setTextureLocation(Bionicle.ingotProtosteel);
		setTextureLocation(Bionicle.nuggetProtodermis);
		setTextureLocation(Bionicle.nuggetProtosteel);
		setTextureLocation(Bionicle.nuggetIron);
		//setTextureLocation(Bionicle.rodProtodermis);
		//setTextureLocation(Bionicle.rodProtosteel);
		
		setTextureLocation(Bionicle.rawProtodermis);
		setTextureLocation(Bionicle.rawHeatstone);
		setTextureLocation(Bionicle.bambooStick);		
		
		setTextureLocation(Bionicle.kanokaFlying);
		setTextureLocation(Bionicle.bambooDisc);
		ModelLoader.setCustomMeshDefinition(Bionicle.kanokaDisc, Bionicle.itemMeshDef);
		ModelBakery.registerItemVariants(Bionicle.kanokaDisc, BionicleItemMeshDef.kanokaModLocs);		
		setTextureLocation(Bionicle.kanokaTime);
		
		setTextureLocation(Bionicle.discLauncher);
	    setTextureLocation(Bionicle.swordProtodermis);
	    
	    setTextureLocation(Bionicle.pickProtodermis);	    
	    setTextureLocation(Bionicle.hatchetProtodermis);	    
	    setTextureLocation(Bionicle.shovelProtodermis);	    
	    setTextureLocation(Bionicle.sytheProtodermis);
		//setTextureLocation(Bionicle.heatstoneLighter);
		setTextureLocation(Bionicle.sluice);
		
		ModelLoader.setCustomMeshDefinition(Bionicle.heatstoneLighter, Bionicle.itemMeshDef);
		ModelBakery.registerItemVariants(Bionicle.heatstoneLighter, BionicleItemMeshDef.heatLighterModLocs);
				
		for(int i = 0; i < 6; i++){
			ModelLoader.setCustomModelResourceLocation(Bionicle.maskMataGold, i, new ModelResourceLocation(Bionicle.MODID + ":maskMata" + ItemGoldMataMask.mataNames[i] + "_1", "inventory"));
		}
		
		setColoredMaskTextureLocation(Bionicle.maskMataKakama);
		setColoredMaskTextureLocation(Bionicle.maskMataPakari);
		setColoredMaskTextureLocation(Bionicle.maskMataKaukau);
		setColoredMaskTextureLocation(Bionicle.maskMataMiru);
		setColoredMaskTextureLocation(Bionicle.maskMataHau);
		setColoredMaskTextureLocation(Bionicle.maskMataAkaku);
		
		setTextureLocation(Bionicle.maskNuvaKakama);
		setTextureLocation(Bionicle.maskNuvaPakari);
		setTextureLocation(Bionicle.maskNuvaKaukau);
		setTextureLocation(Bionicle.maskNuvaMiru);
		setTextureLocation(Bionicle.maskNuvaHau);
		setTextureLocation(Bionicle.maskNuvaAkaku);
				
		setMaskMetaTextureLocation(Bionicle.maskIgnika);
		setMaskMetaTextureLocation(Bionicle.maskVahi);
	}
	
	private static void setColoredMaskTextureLocation(Item item){
		ItemColoredMask maskItem = (ItemColoredMask)item;
		ModelLoader.setCustomMeshDefinition(item, Bionicle.itemMeshDef);
		ItemStack itemstack = new ItemStack(maskItem);
		maskItem.setColor(itemstack, 0);
		ModelResourceLocation ColorMaskLoc = Bionicle.itemMeshDef.getModelLocation(itemstack);
		maskItem.removeColor(itemstack);
		maskItem.setMetal(itemstack, (byte)1);
		ModelResourceLocation GoldMaskLoc = Bionicle.itemMeshDef.getModelLocation(itemstack);
		maskItem.setMetal(itemstack, (byte)2);
		ModelResourceLocation SilvMaskLoc = Bionicle.itemMeshDef.getModelLocation(itemstack);
		maskItem.setMetal(itemstack, (byte)3);
		ModelResourceLocation BronzMaskLoc = Bionicle.itemMeshDef.getModelLocation(itemstack);
		ModelBakery.registerItemVariants(maskItem, ColorMaskLoc, GoldMaskLoc, SilvMaskLoc, BronzMaskLoc);
	}
	
	private static void setMaskMetaTextureLocation(Item item){
		ItemMaskMeta maskItem = (ItemMaskMeta)item;
		for(int i = 0; i < maskItem.numberOfSubitems; i++){
			ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName() + "_" + i, "inventory"));
		}
	}
	
	private static void setTextureLocation(Block block){
		setTextureLocation(Item.REGISTRY.getObject(block.getRegistryName()));
	}
	
	private static void setTextureLocation(Item item){
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	public ModelBiped getArmorModel(int id){
		switch (id){
		case 0:	return maskMataAkaku;
		case 1:	return maskIgnika;
		default: return maskLong;
		}		
	}
	
	@Override
	public void init(){
		super.init();
				
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor(){
            public int getColorFromItemstack(ItemStack stack, int tintIndex){
            	if (tintIndex > 0 || !((ItemColoredMask)stack.getItem()).hasColor(stack)){
            		return -1;
            	}else return ((ItemColoredMask)stack.getItem()).getColor(stack);
            }
        }, new Item[] {Bionicle.maskMataKakama, Bionicle.maskMataPakari, Bionicle.maskMataKaukau, 
        		Bionicle.maskMataMiru, Bionicle.maskMataHau, Bionicle.maskMataAkaku});
	}
}
