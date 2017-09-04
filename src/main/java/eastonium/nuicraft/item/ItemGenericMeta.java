package eastonium.nuicraft.item;

import java.util.List;

import org.apache.logging.log4j.Level;

import com.google.common.collect.Lists;

import eastonium.nuicraft.NuiCraft;
import eastonium.nuicraft.NuiCraftBlocks;
import eastonium.nuicraft.NuiCraftItems;
import eastonium.nuicraft.block.BlockKoro.EnumKoroBlock;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGenericMeta extends Item {	
	public ItemGenericMeta() {
		super();
		setHasSubtypes(true);
		setCreativeTab(NuiCraft.nuicraftTab);
		setRegistryName("generic_item");
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item." + NuiCraft.MODID + "." + EnumGenericItem.byMetadata(getMetadata(stack)).getName();		
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasEffect(ItemStack stack){
		return EnumGenericItem.byMetadata(getMetadata(stack)).isShiny();
	}
	
	@Override
	public int getMetadata(int metadata){
		return metadata;
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		for (EnumGenericItem enumItem : EnumGenericItem.values()) {
			if (enumItem.showInTab() && getCreativeTab() == tab) {
				items.add(enumItem.getStack(1));				
			}
		}
    }
	
	public static void setTextureLocations() {
		for (EnumGenericItem enumItem : EnumGenericItem.values()) {
			ModelLoader.setCustomModelResourceLocation(
					NuiCraftItems.generic_item, 
					enumItem.getMetadata(), 
					new ModelResourceLocation(NuiCraft.MODID + ":normal_items", 
							"name=" + enumItem.getName()));
		}
	}
	
	public enum EnumGenericItem implements IStringSerializable {
		PROTO_INGOT(0, "ingot_protodermis", false, true),
		PROTOSTEEL_INGOT(1, "ingot_protosteel", false, true),
		PROTO_NUGGET(2, "nugget_protodermis", false, true),
		PROTOSTEEL_NUGGET(3, "nugget_protosteel", false, true),
		PROTO_DUST(4, "dust_protodermis", false, true),
		PROTOSTEEL_DUST(5, "dust_protosteel", false, true),
		PROTO_BLOB(6, "protodermis_blob", false, true),
		HEATSTONE(7, "heatstone", false, true),
		BAMBOO_STICK(8, "bamboo_stick", false, true),
		KANOKA_TIME(9, "kanoka_time", true, true),
		DYE_MASK(10, "dye_mask", false, false),
		KANOKA_FLYING_BLACK(11, "kanoka_flying_black", false, false),
		KANOKA_FLIYNG_WHITE(12, "kanoka_flying_white", false, false);

		private static final EnumGenericItem[] META_LOOKUP = new EnumGenericItem[values().length];
		private final int meta;
		private final String name;
		private final boolean shiny;
		private final boolean inTab;
		
		private EnumGenericItem(int meta, String name, boolean shiny, boolean inTab) {
			this.meta = meta;
			this.name = name;
			this.shiny = shiny;
			this.inTab = inTab;
		}
		
		public ItemStack getStack(int count) {
    		return new ItemStack(NuiCraftItems.generic_item, count, this.getMetadata());
    	}
		
		public int getMetadata() {
    		return this.meta;
    	}
    	
    	@Override
		public String getName() {
			return this.name;
		}
    	
    	public boolean isShiny() { 
    		return this.shiny;
    	}
    	
    	public boolean showInTab() {
    		return this.inTab;
    	}
    	
    	@Override
    	public String toString() {
            return this.name;
        }
    	
    	public static EnumGenericItem byMetadata(int meta) {
            return META_LOOKUP[meta % META_LOOKUP.length];
        }

		static {
	        for (EnumGenericItem enumItem : values()) {
	            META_LOOKUP[enumItem.getMetadata()] = enumItem;
	        }
	    }	
	}
}
