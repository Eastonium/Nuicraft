package eastonium.nuicraft.machine.maskForge.recipe;

import java.util.Iterator;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.Level;

import eastonium.nuicraft.NuiCraft;
import eastonium.nuicraft.util.CountedIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class MaskForgeRecipe implements IMFRecipe {

	@Nonnull
	protected ItemStack output = ItemStack.EMPTY;
	protected NonNullList<CountedIngredient> reqInputs = NonNullList.create();
	private NonNullList<ItemStack> returnStacks = NonNullList.create();


	public MaskForgeRecipe(ItemStack output, CountedIngredient... inputs) {
		this.output = output;
		for (CountedIngredient input : inputs) this.reqInputs.add(input);
	}
	
	@Override
    public boolean matches(NonNullList<ItemStack> inputStacks) {
		NuiCraft.logger.log(Level.INFO, "checking for match");
        NonNullList<CountedIngredient> required = requiredIngCopy();
        returnStacks.clear();
        returnStacks.addAll(inputStacks);
        for (ItemStack stack : returnStacks) {
            if (stack.isEmpty()) continue;
            
            boolean inRecipe = false;
            Iterator<CountedIngredient> req = required.iterator();
            while (req.hasNext()) {
                if (req.next().apply(stack)) inRecipe = true;
                if (stack.isEmpty()) break;
            }
            if (!inRecipe) return false;
        }
        Iterator<CountedIngredient> req = required.iterator();
        while (req.hasNext()) {
        	if (req.next().count > 0) return false;
        }
        return true;
    }
	
	private NonNullList<CountedIngredient> requiredIngCopy() {
		NonNullList<CountedIngredient> copy = NonNullList.create();
		Iterator<CountedIngredient> ings = reqInputs.iterator();
		while (ings.hasNext()) {
			copy.add(ings.next().copy());
		}
		return copy;
	}

//	@Override
//	public boolean matches(NonNullList<ItemStack> inputStacks) {
//		Object[] reqInputs = new Object[requiredInput.length];
//		for (int i = 0; i < requiredInput.length; i++) {
//			if (requiredInput[i] instanceof ItemStack) {
//				reqInputs[i] = ((ItemStack)requiredInput[i]).copy();
//			} else {
//				reqInputs[i] = requiredInput[i];
//			}
//		}
//		returnStacks = inputStacks;
//		for (int i = 0; i < returnStacks.size(); i++) {
//			if (returnStacks.get(i).isEmpty()) continue;
//			boolean flag = false;
//			inputLoop:
//			for (int j = 0; j < reqInputs.length; j++) {
//				if (reqInputs[j] instanceof ItemStack) {
//					if (ItemStack.areItemsEqual(returnStacks.get(i), ((ItemStack)reqInputs[j]) )) {
//						flag = true;
//						int commonSize = Math.min(returnStacks.get(i).getCount(), ((ItemStack)reqInputs[j]).getCount());
//						returnStacks.get(i).shrink(commonSize);
//						((ItemStack)reqInputs[j]).shrink(commonSize);
//						if (returnStacks.get(i).isEmpty()) {
//							returnStacks.set(i, ItemStack.EMPTY);
//							break inputLoop;
//						}
//					}
//				} else { // Ore dict
//					String reqInputODName = ((String)reqInputs[j]).substring(2);
//					int[] oreIDs = OreDictionary.getOreIDs(returnStacks.get(i));
//					for (int oreID : oreIDs) {
//						if (OreDictionary.getOreName(oreID).equals(reqInputODName)) {
//							flag = true;
//							int reqInputStackSize = Integer.parseInt(((String)reqInputs[j]).substring(0, 2));
//							if (reqInputStackSize > 0) {
//								int commonSize = Math.min(returnStacks.get(i).getCount(), reqInputStackSize);
//								returnStacks.get(i).shrink(commonSize);
//								reqInputStackSize -= commonSize;
//								reqInputs[j] = String.format("%02d", reqInputStackSize) + reqInputODName;
//								if (returnStacks.get(i).isEmpty()) {
//									returnStacks.set(i, ItemStack.EMPTY);
//									break inputLoop;
//								}
//							}
//						}
//					}
//				}
//			}
//			if (!flag) return false;
//			
//		}
//		for (int i = 0; i < reqInputs.length; i++) {
//			if (reqInputs[i] instanceof ItemStack) {
//				if (((ItemStack)reqInputs[i]).getCount() > 0) return false;
//			} else if (!((String)reqInputs[i]).startsWith("00")) {
//				return false;
//			}
//		}
//		return true;
//	}

	@Override
	public ItemStack getOutput() {
		return this.output.copy();
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems() {
		for(int i = 0; i < returnStacks.size(); i++){
			if (returnStacks.get(i).isEmpty()) returnStacks.remove(i);
		}
		return returnStacks;
	}
	
	@Override
	public NonNullList<CountedIngredient> getIngredients() { 
		return reqInputs;
	}
}