package eastonium.nuicraft.machine.maskForge.recipe;

import eastonium.nuicraft.Bionicle;
import eastonium.nuicraft.kanoka.ItemKanokaDisc;
import net.minecraft.item.ItemStack;

public class KanokaMFRecipe implements IMFRecipe{

	ItemStack[] inputStacks;

	@Override
	public boolean matches(ItemStack[] inputStacks) {
		int totalProtoIngots = 0;
		for(ItemStack stack : inputStacks){
			if(stack != null){
				if(stack.getItem() == Bionicle.ingotProtodermis){
					totalProtoIngots += stack.stackSize;
				}else return false;
			}
		}
		this.inputStacks = inputStacks;
		return totalProtoIngots >= 3;
	}

	@Override
	public ItemStack getOutput() {
		ItemStack randKanoka = new ItemStack(Bionicle.kanokaDisc);
		ItemKanokaDisc.setKanokaNumber(randKanoka, 0, 0, 0);
		return randKanoka;
	}

	@Override
	public ItemStack[] getRemainingItems() {
		int remaining = 3;
		for(int i = 0; i < inputStacks.length; i++){
			if(inputStacks[i] != null){
				int min = Math.min(inputStacks[i].stackSize, remaining);
				inputStacks[i].stackSize -= min;
				remaining -= min;
				if(inputStacks[i].stackSize <= 0) inputStacks[i] = null;
			}
			if(remaining <= 0) break;
		}
		return this.inputStacks;
	}
}
