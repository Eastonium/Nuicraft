package eastonium.nuicraft;

import eastonium.nuicraft.kanohi.ItemColoredMask;
import eastonium.nuicraft.kanoka.ItemKanokaDisc;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

public class NuiCraftItemMeshDef implements ItemMeshDefinition{
	
	public static final ModelResourceLocation[] heatLighterModLocs = new ModelResourceLocation[]{
			new ModelResourceLocation(NuiCraft.MODID + ":heatstone_lighter", "inventory"),
			new ModelResourceLocation(NuiCraft.MODID + ":heatstone_lighter_spent", "inventory")
	};
	
	public static final ModelResourceLocation[] kanokaModLocs = new ModelResourceLocation[]{
			new ModelResourceLocation(NuiCraft.MODID + ":kanoka_0", "inventory"),
			new ModelResourceLocation(NuiCraft.MODID + ":kanoka_1", "inventory"),
			new ModelResourceLocation(NuiCraft.MODID + ":kanoka_2", "inventory"),
			new ModelResourceLocation(NuiCraft.MODID + ":kanoka_3", "inventory"),
			new ModelResourceLocation(NuiCraft.MODID + ":kanoka_4", "inventory"),
			new ModelResourceLocation(NuiCraft.MODID + ":kanoka_5", "inventory"),
			new ModelResourceLocation(NuiCraft.MODID + ":kanoka_6", "inventory"),
			new ModelResourceLocation(NuiCraft.MODID + ":kanoka_7", "inventory"),
			new ModelResourceLocation(NuiCraft.MODID + ":kanoka_8", "inventory"),
			new ModelResourceLocation(NuiCraft.MODID + ":kanoka_9", "inventory"),
			new ModelResourceLocation(NuiCraft.MODID + ":kanoka_10", "inventory"),
			new ModelResourceLocation(NuiCraft.MODID + ":kanoka_11", "inventory"),
			new ModelResourceLocation(NuiCraft.MODID + ":kanoka_12", "inventory"),
	};

	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack) {
		if (stack.getItem() == NuiCraftItems.heatstone_lighter){
			if (stack.getItemDamage() == NuiCraftItems.heatstone_lighter.getMaxDamage()) {
				return heatLighterModLocs[1];
			} else return heatLighterModLocs[0];
			
		} else if(stack.getItem() instanceof ItemColoredMask){
			ItemColoredMask maskItem = (ItemColoredMask)stack.getItem();
			if(!maskItem.hasColor(stack)) {
				return new ModelResourceLocation(maskItem.getRegistryName() + "_" + maskItem.getMetal(stack), "inventory");
			} else return new ModelResourceLocation(maskItem.getRegistryName(), "inventory");
			
		} else if (stack.getItem() == NuiCraftItems.kanoka_disc) {			
			byte[] discNumbers = ItemKanokaDisc.getKanokaNumber(stack);
			if (discNumbers == null) return new ModelResourceLocation(NuiCraft.MODID + ":kanoka_0", "inventory");
			if (discNumbers[2] == 9) return new ModelResourceLocation(NuiCraft.MODID + ":kanoka_" + (discNumbers[0] + 6), "inventory");
			return new ModelResourceLocation(NuiCraft.MODID + ":kanoka_" + discNumbers[0], "inventory");
			
		} else return null;
	}
}
