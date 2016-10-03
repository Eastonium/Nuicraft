package eastonium.nuicraft;

import eastonium.nuicraft.kanohi.ItemColoredMask;
import eastonium.nuicraft.kanoka.ItemKanokaDisc;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

public class BionicleItemMeshDef implements ItemMeshDefinition{
	
	public static final ModelResourceLocation[] heatLighterModLocs = new ModelResourceLocation[]{
			new ModelResourceLocation(Bionicle.MODID + ":heatstoneLighter", "inventory"),
			new ModelResourceLocation(Bionicle.MODID + ":heatstoneLighter_spent", "inventory")
	};
	
	public static final ModelResourceLocation[] kanokaModLocs = new ModelResourceLocation[]{
			new ModelResourceLocation(Bionicle.MODID + ":Kanoka_0", "inventory"),
			new ModelResourceLocation(Bionicle.MODID + ":Kanoka_1", "inventory"),
			new ModelResourceLocation(Bionicle.MODID + ":Kanoka_2", "inventory"),
			new ModelResourceLocation(Bionicle.MODID + ":Kanoka_3", "inventory"),
			new ModelResourceLocation(Bionicle.MODID + ":Kanoka_4", "inventory"),
			new ModelResourceLocation(Bionicle.MODID + ":Kanoka_5", "inventory"),
			new ModelResourceLocation(Bionicle.MODID + ":Kanoka_6", "inventory"),
			new ModelResourceLocation(Bionicle.MODID + ":Kanoka_7", "inventory"),
			new ModelResourceLocation(Bionicle.MODID + ":Kanoka_8", "inventory"),
			new ModelResourceLocation(Bionicle.MODID + ":Kanoka_9", "inventory"),
			new ModelResourceLocation(Bionicle.MODID + ":Kanoka_10", "inventory"),
			new ModelResourceLocation(Bionicle.MODID + ":Kanoka_11", "inventory"),
			new ModelResourceLocation(Bionicle.MODID + ":Kanoka_12", "inventory"),
	};

	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack) {
		if(stack.getItem() == Bionicle.heatstoneLighter){
			if(stack.getItemDamage() == Bionicle.heatstoneLighter.getMaxDamage()){
				return heatLighterModLocs[1];
			}else return heatLighterModLocs[0];
			
		}else if(stack.getItem() instanceof ItemColoredMask){
			ItemColoredMask maskItem = (ItemColoredMask)stack.getItem();
			if(!maskItem.hasColor(stack)){
				return new ModelResourceLocation(maskItem.getRegistryName() + "_" + maskItem.getMetal(stack), "inventory");
			}else return new ModelResourceLocation(maskItem.getRegistryName(), "inventory");
			
		}else if (stack.getItem() == Bionicle.kanokaDisc){			
			byte[] discNumbers = ItemKanokaDisc.getKanokaNumber(stack);
			if(discNumbers == null) return new ModelResourceLocation(Bionicle.MODID + ":Kanoka_0", "inventory");
			if(discNumbers[2] == 9) return new ModelResourceLocation(Bionicle.MODID + ":Kanoka_" + (discNumbers[0] + 6), "inventory");
			return new ModelResourceLocation(Bionicle.MODID + ":Kanoka_" + discNumbers[0], "inventory");
			
		}else return null;
	}
}
