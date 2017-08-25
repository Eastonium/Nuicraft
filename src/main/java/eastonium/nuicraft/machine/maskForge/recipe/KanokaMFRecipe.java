package eastonium.nuicraft.machine.maskForge.recipe;

import eastonium.nuicraft.NuiCraftItems;
import eastonium.nuicraft.kanoka.ItemKanokaDisc;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class KanokaMFRecipe implements IMFRecipe {

	NonNullList<ItemStack> inputStacks;

	@Override
	public boolean matches(NonNullList<ItemStack> inputStacks) {
		int totalProtoIngots = 0;
		for(ItemStack stack : inputStacks){
			if(!stack.isEmpty()){
				if(stack.getItem() == NuiCraftItems.ingot_protodermis){
					totalProtoIngots += stack.getCount();
				}else return false;
			}
		}
		this.inputStacks = inputStacks;
		return totalProtoIngots >= 3;
	}

	@Override
	public ItemStack getOutput() {
		ItemStack randKanoka = new ItemStack(NuiCraftItems.kanoka_disc);
		ItemKanokaDisc.setKanokaNumber(randKanoka, 0, 0, 0);
		return randKanoka;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems() {
		int remaining = 3;
		for(int i = 0; i < inputStacks.size(); i++){
			if(!inputStacks.get(i).isEmpty()){
				int min = Math.min(inputStacks.get(i).getCount(), remaining);
				inputStacks.get(i).shrink(min);
				remaining -= min;
				if(inputStacks.get(i).getCount() <= 0) inputStacks.set(i, ItemStack.EMPTY);
			}
			if(remaining <= 0) break;
		}
		return this.inputStacks;
	}
}
