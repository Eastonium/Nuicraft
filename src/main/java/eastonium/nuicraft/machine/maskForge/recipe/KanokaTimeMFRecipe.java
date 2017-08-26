package eastonium.nuicraft.machine.maskForge.recipe;

import eastonium.nuicraft.NuiCraftItems;
import eastonium.nuicraft.kanoka.ItemKanokaDisc;
import eastonium.nuicraft.machine.maskForge.TileInventoryMaskForge;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class KanokaTimeMFRecipe implements IMFRecipe {

	private NonNullList<ItemStack> returnStacks = NonNullList.<ItemStack>withSize(TileInventoryMaskForge.INPUT_SLOTS_COUNT, ItemStack.EMPTY);;

	@Override
	public boolean matches(NonNullList<ItemStack> inputStacks) {
		int[] kanokaLoc = new int[6];
		int i = 0;
		for(ItemStack stack : inputStacks){
			if(stack.isEmpty()) return false;
			if(stack.getItem() != NuiCraftItems.kanoka_disc) return false;
			byte[] discNums = ItemKanokaDisc.getKanokaNumber(stack);
			if(discNums[2] < 9) return false;
			kanokaLoc[i] = discNums[0];
			i++;
		}
		returnStacks = inputStacks;
		return !hasDuplicates(kanokaLoc);
	}

	public static boolean hasDuplicates(int [] par1){
		for (int i = 0; i < par1.length; i++){
			for (int j= i + 1; j < par1.length; j++){
				if (par1[j]==par1[i]) return true;
			}
		}
		return false;
	}

	@Override
	public ItemStack getOutput() {
		return NuiCraftItems.getGIIS("kanoka_time", 1);
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems() {
		for(int i = 0; i < returnStacks.size(); i++){
			if(!returnStacks.get(i).isEmpty()){
				returnStacks.get(i).shrink(1);
				if(returnStacks.get(i).getCount() <= 0){
					returnStacks.set(i, ItemStack.EMPTY);
				}
			}
		}
		return returnStacks;
	}
}
