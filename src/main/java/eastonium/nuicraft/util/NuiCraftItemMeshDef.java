package eastonium.nuicraft.util;

import eastonium.nuicraft.NuiCraft;
import eastonium.nuicraft.NuiCraftItems;
import eastonium.nuicraft.kanohi.ItemColoredMask;
import eastonium.nuicraft.kanoka.ItemKanokaDisc;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

public class NuiCraftItemMeshDef implements ItemMeshDefinition {
	
	public static final ModelResourceLocation[] heatLighterModLocs = new ModelResourceLocation[] {
			new ModelResourceLocation(NuiCraft.MODID + ":heatstone_lighter", "spent=true"),
			new ModelResourceLocation(NuiCraft.MODID + ":heatstone_lighter", "spent=false")
	};
	
	public static final ModelResourceLocation[] kanokaModLocs = new ModelResourceLocation[] {
			new ModelResourceLocation(NuiCraft.MODID + ":kanoka_disc", "type=null"),
			new ModelResourceLocation(NuiCraft.MODID + ":kanoka_disc", "type=ta"),
			new ModelResourceLocation(NuiCraft.MODID + ":kanoka_disc", "type=ga"),
			new ModelResourceLocation(NuiCraft.MODID + ":kanoka_disc", "type=po"),
			new ModelResourceLocation(NuiCraft.MODID + ":kanoka_disc", "type=ko"),
			new ModelResourceLocation(NuiCraft.MODID + ":kanoka_disc", "type=le"),
			new ModelResourceLocation(NuiCraft.MODID + ":kanoka_disc", "type=onu"),
			new ModelResourceLocation(NuiCraft.MODID + ":kanoka_disc", "type=ta9"),
			new ModelResourceLocation(NuiCraft.MODID + ":kanoka_disc", "type=ga9"),
			new ModelResourceLocation(NuiCraft.MODID + ":kanoka_disc", "type=po9"),
			new ModelResourceLocation(NuiCraft.MODID + ":kanoka_disc", "type=ko9"),
			new ModelResourceLocation(NuiCraft.MODID + ":kanoka_disc", "type=le9"),
			new ModelResourceLocation(NuiCraft.MODID + ":kanoka_disc", "type=onu9"),
	};

	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack) {
		if (stack.getItem() == NuiCraftItems.heatstone_lighter) {
			if (stack.getItemDamage() == NuiCraftItems.heatstone_lighter.getMaxDamage()) {
				return heatLighterModLocs[0];
			} else {
				return heatLighterModLocs[1];
			}
			
		} else if (stack.getItem() == NuiCraftItems.kanoka_disc) {			
			byte[] discNumbers = ItemKanokaDisc.getKanokaNumber(stack);
			if (discNumbers == null) return kanokaModLocs[0];
			if (discNumbers[2] == 9) return kanokaModLocs[discNumbers[0] + 6];
			return kanokaModLocs[discNumbers[0]];
			
		} else return null;
	}
}
