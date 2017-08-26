package eastonium.nuicraft.items;

import java.util.List;
import java.util.logging.Level;

import com.google.common.collect.Lists;

import eastonium.nuicraft.NuiCraft;
import eastonium.nuicraft.NuiCraftItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGenericMeta extends Item {
	private static List<String> names = Lists.<String>newArrayList();
	private static List<CreativeTabs> tabs = Lists.<CreativeTabs>newArrayList();
	private static List<Boolean> effects = Lists.<Boolean>newArrayList();
	
	public static void addGenericItem(String name, CreativeTabs tab, boolean effect) {
		names.add(name);
		tabs.add(tab);
		effects.add(effect);
	}
	public static void addGenericItem(String name, CreativeTabs tab) {
		addGenericItem(name, tab, false);
	}
	public static void addGenericItem(String name, boolean effect) {
		addGenericItem(name, NuiCraft.bio_material_tab, effect);
	}
	public static void addGenericItem(String name) {
		addGenericItem(name, NuiCraft.bio_material_tab, false);
	}
	
	public static void setTextureLocations() {
		for (int meta = 0; meta < names.size(); meta++) {
			ModelLoader.setCustomModelResourceLocation(
				NuiCraftItems.generic_item, meta, new ModelResourceLocation(NuiCraft.MODID + ":" + names.get(meta), "inventory")
			);
		}
	}
	
	public ItemGenericMeta() {
		super();
		setHasSubtypes(true);
		setRegistryName("generic_item");
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return NuiCraft.MODID + "." + names.get(getMetadata(stack) % names.size());		
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasEffect(ItemStack stack){
		return effects.get(getMetadata(stack) % effects.size());
	}
	
	@Override
	public int getMetadata(int metadata){
		return metadata;
	}
	
	public static int getMetaFromName(String name) {
		int meta = names.indexOf(name);
		if (meta < 0) NuiCraft.logger.log(Level.WARNING, "Invalid name requested from ItemGenericMeta.getMetaFromName()");
		return meta;
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		for (int meta = 0; meta < names.size(); meta++) {
			if (tabs.get(meta) != null && tabs.get(meta) == tab) {
				items.add(new ItemStack(this, 1, meta));				
			}
		}
    }
}
