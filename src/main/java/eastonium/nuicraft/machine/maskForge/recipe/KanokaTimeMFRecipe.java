package eastonium.nuicraft.machine.maskForge.recipe;

import eastonium.nuicraft.Bionicle;
import eastonium.nuicraft.kanoka.ItemKanokaDisc;
import net.minecraft.item.ItemStack;

public class KanokaTimeMFRecipe implements IMFRecipe {

	private ItemStack[] returnStacks = null;

	@Override
	public boolean matches(ItemStack[] inputStacks) {
		int[] kanokaLoc = new int[6];
		int i = 0;
		for(ItemStack stack : inputStacks){
			if(stack == null) return false;
			if(stack.getItem() != Bionicle.kanokaDisc) return false;
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
		return new ItemStack(Bionicle.kanokaTime, 1);
	}

	@Override
	public ItemStack[] getRemainingItems() {
		for(int i = 0; i < returnStacks.length; i++){
			if(returnStacks[i] != null){
				returnStacks[i].stackSize--;
				if(returnStacks[i].stackSize <= 0){
					returnStacks[i] = null;
				}
			}
		}
		return returnStacks;
	}
}
