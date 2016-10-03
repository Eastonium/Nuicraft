package eastonium.nuicraft.machine.maskForge.recipe;

import eastonium.nuicraft.kanohi.ItemColoredMask;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ColoredMetalMaskMFRecipe implements IMFRecipe {
	private ItemStack recipeOutput;
	private ItemStack[] returnStacks = null;

	@Override
	public boolean matches(ItemStack[] inputStacks){
		returnStacks = inputStacks;
		ItemColoredMask maskItem = null;
		ItemStack itemstackMask = null;
		int metal = 0;
		for(int i = 0; i < returnStacks.length; i++){
			if(returnStacks[i] != null){
				if(itemstackMask == null && returnStacks[i].getItem() instanceof ItemColoredMask){
					maskItem = (ItemColoredMask)returnStacks[i].getItem();
					itemstackMask = returnStacks[i].copy();
					returnStacks[i] = null;
				}else if(metal == 0){
					int[] oreDictIDs = OreDictionary.getOreIDs(returnStacks[i]);
					for(int j = 0; j < oreDictIDs.length; j++){
						String oreName = OreDictionary.getOreName(oreDictIDs[j]);
						if(oreName.equals("ingotGold")){
							metal = 1;
							returnStacks[i].stackSize--;
							break;
						}else if(oreName.equals("ingotSilver") || oreName.equals("ingotIron")){
							metal = 2;
							returnStacks[i].stackSize--;
							break;
						}else if(oreName.equals("ingotBronze")){
							metal = 3;
							returnStacks[i].stackSize--;
							break;
						}
					}					
				}
			}
		}
		if(metal != 0 && itemstackMask != null){
			maskItem.removeColor(itemstackMask);
			maskItem.setMetal(itemstackMask, (byte)metal);
			this.recipeOutput = itemstackMask;
			return true;
		}
		return false;
	}

	@Override
	public ItemStack getOutput(){
		return recipeOutput.copy();
	}

	@Override
	public ItemStack[] getRemainingItems(){
		for(int i = 0; i < returnStacks.length; i++){
			if(returnStacks[i] != null && returnStacks[i].stackSize <= 0){
				returnStacks[i] = null;
			}
		}
		return returnStacks;
	}
}
