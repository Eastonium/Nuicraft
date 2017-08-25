package eastonium.nuicraft.machine.maskForge.recipe;

import eastonium.nuicraft.kanohi.ItemColoredMask;
import eastonium.nuicraft.machine.maskForge.TileInventoryMaskForge;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class ColoredMetalMaskMFRecipe implements IMFRecipe {
	private ItemStack recipeOutput;
	private NonNullList<ItemStack> returnStacks = NonNullList.<ItemStack>withSize(TileInventoryMaskForge.INPUT_SLOTS_COUNT, ItemStack.EMPTY);

	@Override
	public boolean matches(NonNullList<ItemStack> inputStacks){
		returnStacks = inputStacks;
		ItemColoredMask maskItem = null;
		ItemStack itemstackMask = ItemStack.EMPTY;
		int metal = 0;
		for(int i = 0; i < returnStacks.size(); i++){
			if(!returnStacks.get(i).isEmpty()){
				if(itemstackMask.isEmpty() && returnStacks.get(i).getItem() instanceof ItemColoredMask){
					maskItem = (ItemColoredMask)returnStacks.get(i).getItem();
					itemstackMask = returnStacks.get(i).copy();
					returnStacks.set(i, ItemStack.EMPTY);
				}else if(metal == 0){
					int[] oreDictIDs = OreDictionary.getOreIDs(returnStacks.get(i));
					for(int j = 0; j < oreDictIDs.length; j++){
						String oreName = OreDictionary.getOreName(oreDictIDs[j]);
						if(oreName.equals("ingotGold")){
							metal = 1;
							returnStacks.get(i).shrink(1);;
							break;
						}else if(oreName.equals("ingotSilver") || oreName.equals("ingotIron")){
							metal = 2;
							returnStacks.get(i).shrink(1);;
							break;
						}else if(oreName.equals("ingotBronze")){
							metal = 3;
							returnStacks.get(i).shrink(1);;
							break;
						}
					}					
				}
			}
		}
		if(metal != 0 && !itemstackMask.isEmpty()){
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
	public NonNullList<ItemStack> getRemainingItems(){
		for(int i = 0; i < returnStacks.size(); i++){
			if(!returnStacks.get(i).isEmpty() && returnStacks.get(i).getCount() <= 0){
				returnStacks.set(i, ItemStack.EMPTY);
			}
		}
		return returnStacks;
	}
}
