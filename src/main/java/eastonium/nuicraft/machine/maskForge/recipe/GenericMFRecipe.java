package eastonium.nuicraft.machine.maskForge.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class GenericMFRecipe implements IMFRecipe {

	private final ItemStack recipeOutput;
	private final Object[] requiredInput;
	private ItemStack[] returnStacks = null;


	public GenericMFRecipe(ItemStack output, Object[] inputs){
		this.recipeOutput = output;
		this.requiredInput = inputs;
	}

	@Override
	public boolean matches(ItemStack[] inputStacks){
		Object[] reqInputs = new Object[requiredInput.length];
		for(int i = 0; i < requiredInput.length; i++){
			if(requiredInput[i] instanceof ItemStack){
				reqInputs[i] = ((ItemStack)requiredInput[i]).copy();
			}else{
				reqInputs[i] = requiredInput[i];
			}
		}
		returnStacks = inputStacks;
		for(int i = 0; i < returnStacks.length; i++){
			if(returnStacks[i] != null){
				inputLoop:
				for(int j = 0; j < reqInputs.length; j++){
					if(reqInputs[j] instanceof ItemStack){
						if(returnStacks[i].isItemEqual((ItemStack)reqInputs[j])){
							int commonSize = Math.min(returnStacks[i].stackSize, ((ItemStack)reqInputs[j]).stackSize);
							returnStacks[i].stackSize -= commonSize;
							((ItemStack)reqInputs[j]).stackSize -= commonSize;
							if(returnStacks[i].stackSize <= 0){
								returnStacks[i] = null;
								break inputLoop;
							}
						}
					}else/* if(reqInputs[j] instanceof String)*/{
						int reqInputStackSize = Integer.parseInt(((String)reqInputs[j]).substring(0, 2));
						if(reqInputStackSize > 0){
							String reqInputODName = ((String)reqInputs[j]).substring(2);
							int[] oreIDs = OreDictionary.getOreIDs(returnStacks[i]);
							for(int oreID : oreIDs){
								if(OreDictionary.getOreName(oreID).equals(reqInputODName)){
									int commonSize = Math.min(returnStacks[i].stackSize, reqInputStackSize);
									returnStacks[i].stackSize -= commonSize;
									reqInputStackSize -= commonSize;
									reqInputs[j] = String.format("%02d", reqInputStackSize) + reqInputODName;
									if(returnStacks[i].stackSize <= 0){
										returnStacks[i] = null;
										break inputLoop;
									}
								}
							}
						}
					}//else throw new IllegalArgumentException("Non-String, Non-ItemStack got into the Mask Forge Recipes!!!");
				}
			}
		}
		for(int i = 0; i < reqInputs.length; i++){
			if(reqInputs[i] instanceof ItemStack){
				if(((ItemStack)reqInputs[i]).stackSize > 0) return false;
			}else if(!((String)reqInputs[i]).startsWith("00")){
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getOutput(){
		return this.recipeOutput.copy();
	}

	@Override
	public ItemStack[] getRemainingItems(){
		return returnStacks;
	}
}