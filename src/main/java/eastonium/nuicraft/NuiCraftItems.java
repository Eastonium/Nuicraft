package eastonium.nuicraft;

import eastonium.nuicraft.item.ItemBlockKoro;
import eastonium.nuicraft.item.ItemGenericMeta;
import eastonium.nuicraft.kanohi.ItemColoredMask;
import eastonium.nuicraft.kanohi.ItemGoldMataMask;
import eastonium.nuicraft.kanohi.ItemMask;
import eastonium.nuicraft.kanohi.ItemMaskMeta;
import eastonium.nuicraft.util.NuiCraftItemMeshDef;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@GameRegistry.ObjectHolder(NuiCraft.MODID)
public class NuiCraftItems {
	public static final Item purifier_item = null;
	
	public static final Item generic_item = null;	
	
	//public static final Item kolhii_ball = null;	
//Kanoka
	public static final Item kanoka_bamboo = null;
	public static final Item kanoka_disc = null;
	
//Weapons
	public static final Item disc_launcher = null;	
	public static final Item protodermis_sword = null;
//Tools
	public static final Item protodermis_pick = null;
	public static final Item protodermis_axe = null;
	public static final Item protodermis_shovel = null;
	public static final Item protodermis_scythe = null;
	public static final Item heatstone_lighter = null;
	public static final Item sluice = null;
//Masks	
	public static final ItemGoldMataMask mask_mata_gold = null;	
	public static final ItemColoredMask mask_mata_kakama = null;
	public static final ItemColoredMask mask_mata_pakari = null;
	public static final ItemColoredMask mask_mata_kaukau = null;
	public static final ItemColoredMask mask_mata_miru = null;
	public static final ItemColoredMask mask_mata_hau = null;
	public static final ItemColoredMask mask_mata_akaku = null;
	
	public static final ItemMask mask_nuva_kakama = null;
	public static final ItemMask mask_nuva_pakari = null;
	public static final ItemMask mask_nuva_kaukau = null;
	public static final ItemMask mask_nuva_miru = null;
	public static final ItemMask mask_nuva_hau = null;
	public static final ItemMask mask_nuva_akaku = null;

	public static final ItemMaskMeta mask_ignika = null;
	public static final ItemMaskMeta mask_vahi = null;
	
	@SideOnly(Side.CLIENT)
    public static void initModels() {			
		ModelLoader.setCustomModelResourceLocation(purifier_item, 0, new ModelResourceLocation(NuiCraftBlocks.purifier.getRegistryName(), "inventory"));

		ItemBlockKoro.setTextureLocations();
		ItemGenericMeta.setTextureLocations();
		
		setTextureLocation(kanoka_bamboo);
		ModelLoader.setCustomMeshDefinition(kanoka_disc, NuiCraft.itemMeshDef);
		ModelBakery.registerItemVariants(kanoka_disc, NuiCraftItemMeshDef.kanokaModLocs);		
		
		setTextureLocation(disc_launcher);
	    setTextureLocation(protodermis_sword);
	    
	    setTextureLocation(protodermis_pick);	    
	    setTextureLocation(protodermis_axe);	    
	    setTextureLocation(protodermis_shovel);	    
	    setTextureLocation(protodermis_scythe);
		ModelLoader.setCustomMeshDefinition(heatstone_lighter, NuiCraft.itemMeshDef);
		ModelBakery.registerItemVariants(heatstone_lighter, NuiCraftItemMeshDef.heatLighterModLocs);
		setTextureLocation(sluice);
				
		ItemGoldMataMask.setTextureLocations();
		
		setColoredMaskTextureLocation(mask_mata_kakama);
		setColoredMaskTextureLocation(mask_mata_pakari);
		setColoredMaskTextureLocation(mask_mata_kaukau);
		setColoredMaskTextureLocation(mask_mata_miru);
		setColoredMaskTextureLocation(mask_mata_hau);
		setColoredMaskTextureLocation(mask_mata_akaku);
		
		setMaskTextureLocation(mask_nuva_kakama);
		setMaskTextureLocation(mask_nuva_pakari);
		setMaskTextureLocation(mask_nuva_kaukau);
		setMaskTextureLocation(mask_nuva_miru);
		setMaskTextureLocation(mask_nuva_hau);
		setMaskTextureLocation(mask_nuva_akaku);
				
		setMaskMetaTextureLocation(mask_ignika);
		setMaskMetaTextureLocation(mask_vahi);
    }
	
	private static void setTextureLocation(Item item){
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
	private static void setColoredMaskTextureLocation(ItemColoredMask item){
		ModelLoader.setCustomMeshDefinition(item, NuiCraft.itemMeshDef);
		ModelResourceLocation[] models = new ModelResourceLocation[ItemColoredMask.METAL_COLORS.length + 1];
		ItemStack itemstack = new ItemStack(item);
		item.setColor(itemstack, 0);
		models[0] = NuiCraft.itemMeshDef.getModelLocation(itemstack);
		item.removeColor(itemstack);
		for (byte i = 1; i <= ItemColoredMask.METAL_COLORS.length; i++) {
			item.setMetal(itemstack, i);
			models[i] = NuiCraft.itemMeshDef.getModelLocation(itemstack);
		}
		ModelBakery.registerItemVariants(item, models);
	}
	
	private static void setMaskTextureLocation(ItemMask item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, 
			new ModelResourceLocation(NuiCraft.MODID + ":mask_normal",
					"name=" + item.getRegistryName().getResourcePath().substring(5)));
	}
	
	private static void setMaskMetaTextureLocation(ItemMaskMeta item){
		for(int i = 0; i < item.numberOfSubitems; i++){
			ModelLoader.setCustomModelResourceLocation(item, i, 
				new ModelResourceLocation(NuiCraft.MODID + ":mask_normal", 
					"name=" + item.getRegistryName().getResourcePath().substring(5) + "_" + i));
		}
	}
}