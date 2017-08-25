package eastonium.nuicraft;

import eastonium.nuicraft.kanohi.ItemColoredMask;
import eastonium.nuicraft.kanohi.ItemGoldMataMask;
import eastonium.nuicraft.kanohi.ItemMaskMeta;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@GameRegistry.ObjectHolder(NuiCraft.MODID)
public class NuiCraftItems {
	public static final Item purifier_item = null;
	
	public static final Item ingot_protodermis = null;
	public static final Item ingot_protosteel = null;
	public static final Item nugget_protodermis = null;
	public static final Item nugget_protosteel = null;
	public static final Item raw_protodermis = null;
	public static final Item raw_heatstone = null;
	public static final Item bamboo_stick = null;
	
	//public static final Item kolhii_ball = null;	
//Kanoka
	public static final Item kanoka_flying = null;
	public static final Item kanoka_bamboo = null;
	public static final Item kanoka_disc = null;
	public static final Item kanoka_time = null;
	
//Weapons
	public static final Item disc_launcher = null;	
	public static final Item protodermis_sword = null;
//Tools
	public static final Item protodermis_pick = null;
	public static final Item protodermis_axe = null;
	public static final Item protodermis_shovel = null;
	public static final Item protodermis_sythe = null;
	public static final Item heatstone_lighter = null;
	public static final Item sluice = null;
//Masks	
	public static final Item mask_mata_gold = null;	
	public static final Item mask_mata_kakama = null;
	public static final Item mask_mata_pakari = null;
	public static final Item mask_mata_kaukau = null;
	public static final Item mask_mata_miru = null;
	public static final Item mask_mata_hau = null;
	public static final Item mask_mata_akaku = null;
	
	public static final Item mask_nuva_kakama = null;
	public static final Item mask_nuva_pakari = null;
	public static final Item mask_nuva_kaukau = null;
	public static final Item mask_nuva_miru = null;
	public static final Item mask_nuva_hau = null;
	public static final Item mask_nuva_akaku = null;

	public static final Item mask_ignika = null;
	public static final Item mask_vahi = null;
	
	@SideOnly(Side.CLIENT)
    public static void initModels() {			
		ModelLoader.setCustomModelResourceLocation(purifier_item, 0, new ModelResourceLocation(NuiCraftBlocks.purifier.getRegistryName(), "inventory"));
		
		Item item = Item.REGISTRY.getObject(NuiCraftBlocks.stone_koro_block.getRegistryName());
		for(int i = 0; i < 9; i++){
			ModelResourceLocation modReLoc = new ModelResourceLocation(NuiCraftBlocks.stone_koro_block.getRegistryName().toString(), "meta=" + i);
			ModelLoader.setCustomModelResourceLocation(item, i, modReLoc);
		}		
		item = Item.REGISTRY.getObject(NuiCraftBlocks.lit_koro_block.getRegistryName());
		for(int i = 0; i < 2; i++){
			ModelResourceLocation modReLoc = new ModelResourceLocation(NuiCraftBlocks.lit_koro_block.getRegistryName().toString(), "meta=" + i);
			ModelLoader.setCustomModelResourceLocation(item, i, modReLoc);
		}		
		item = Item.REGISTRY.getObject(NuiCraftBlocks.icy_koro_block.getRegistryName());
		for(int i = 0; i < 2; i++){
			ModelResourceLocation modReLoc = new ModelResourceLocation(NuiCraftBlocks.icy_koro_block.getRegistryName().toString(), "meta=" + i);
			ModelLoader.setCustomModelResourceLocation(item, i, modReLoc);
		}
		item = Item.REGISTRY.getObject(NuiCraftBlocks.leafy_koro_block.getRegistryName());
		for(int i = 0; i < 3; i++){
			ModelResourceLocation modReLoc = new ModelResourceLocation(NuiCraftBlocks.leafy_koro_block.getRegistryName().toString(), "meta=" + i);
			ModelLoader.setCustomModelResourceLocation(item, i, modReLoc);
		}
		
		setTextureLocation(ingot_protodermis);
		setTextureLocation(ingot_protosteel);
		setTextureLocation(nugget_protodermis);
		setTextureLocation(nugget_protosteel);
		//setTextureLocation(rod_protodermis);
		//setTextureLocation(rod_protosteel);
		
		setTextureLocation(raw_protodermis);
		setTextureLocation(raw_heatstone);
		setTextureLocation(bamboo_stick);		
		
		setTextureLocation(kanoka_flying);
		setTextureLocation(kanoka_bamboo);
		ModelLoader.setCustomMeshDefinition(kanoka_disc, NuiCraft.itemMeshDef);
		ModelBakery.registerItemVariants(kanoka_disc, NuiCraftItemMeshDef.kanokaModLocs);		
		setTextureLocation(kanoka_time);
		
		setTextureLocation(disc_launcher);
	    setTextureLocation(protodermis_sword);
	    
	    setTextureLocation(protodermis_pick);	    
	    setTextureLocation(protodermis_axe);	    
	    setTextureLocation(protodermis_shovel);	    
	    setTextureLocation(protodermis_sythe);
		ModelLoader.setCustomMeshDefinition(heatstone_lighter, NuiCraft.itemMeshDef);
		ModelBakery.registerItemVariants(heatstone_lighter, NuiCraftItemMeshDef.heatLighterModLocs);
		setTextureLocation(sluice);
				
		for(int i = 0; i < 6; i++){
			ModelLoader.setCustomModelResourceLocation(mask_mata_gold, i, new ModelResourceLocation(NuiCraft.MODID + ":mask_mata_" + ItemGoldMataMask.mataNames[i] + "_1", "inventory"));
		}		
		setColoredMaskTextureLocation(mask_mata_kakama);
		setColoredMaskTextureLocation(mask_mata_pakari);
		setColoredMaskTextureLocation(mask_mata_kaukau);
		setColoredMaskTextureLocation(mask_mata_miru);
		setColoredMaskTextureLocation(mask_mata_hau);
		setColoredMaskTextureLocation(mask_mata_akaku);
		
		setTextureLocation(mask_nuva_kakama);
		setTextureLocation(mask_nuva_pakari);
		setTextureLocation(mask_nuva_kaukau);
		setTextureLocation(mask_nuva_miru);
		setTextureLocation(mask_nuva_hau);
		setTextureLocation(mask_nuva_akaku);
				
		setMaskMetaTextureLocation((ItemMaskMeta)mask_ignika);
		setMaskMetaTextureLocation((ItemMaskMeta)mask_vahi);
    }
	
	private static void setTextureLocation(Item item){
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
	private static void setMaskMetaTextureLocation(ItemMaskMeta item){
		for(int i = 0; i < item.numberOfSubitems; i++){
			ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName() + "_" + i, "inventory"));
		}
	}
	
	private static void setColoredMaskTextureLocation(Item item){
		ItemColoredMask maskItem = (ItemColoredMask)item;
		ModelLoader.setCustomMeshDefinition(item, NuiCraft.itemMeshDef);
		ItemStack itemstack = new ItemStack(maskItem);
		maskItem.setColor(itemstack, 0);
		ModelResourceLocation ColorMaskLoc = NuiCraft.itemMeshDef.getModelLocation(itemstack);
		maskItem.removeColor(itemstack);
		maskItem.setMetal(itemstack, (byte)1);
		ModelResourceLocation GoldMaskLoc = NuiCraft.itemMeshDef.getModelLocation(itemstack);
		maskItem.setMetal(itemstack, (byte)2);
		ModelResourceLocation SilvMaskLoc = NuiCraft.itemMeshDef.getModelLocation(itemstack);
		maskItem.setMetal(itemstack, (byte)3);
		ModelResourceLocation BronzMaskLoc = NuiCraft.itemMeshDef.getModelLocation(itemstack);
		ModelBakery.registerItemVariants(maskItem, ColorMaskLoc, GoldMaskLoc, SilvMaskLoc, BronzMaskLoc);
	}
}