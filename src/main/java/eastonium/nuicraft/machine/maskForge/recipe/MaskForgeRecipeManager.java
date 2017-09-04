package eastonium.nuicraft.machine.maskForge.recipe;

import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.Level;

import com.google.common.collect.Lists;

import eastonium.nuicraft.NuiCraft;
import eastonium.nuicraft.NuiCraftBlocks;
import eastonium.nuicraft.NuiCraftItems;
import eastonium.nuicraft.item.ItemGenericMeta.EnumGenericItem;
import eastonium.nuicraft.util.CountedIngredient;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreIngredient;

public class MaskForgeRecipeManager {
	private static MaskForgeRecipeManager maskForgeRecipes = new MaskForgeRecipeManager();
	private List<IMFRecipe> recipeList = Lists.<IMFRecipe>newArrayList();

	public static MaskForgeRecipeManager getInstance(){
		return maskForgeRecipes;
	}

	private MaskForgeRecipeManager(){
		recipeList.add(new MetalizeMaskRecipe());
		recipeList.add(new RandomKanokaRecipe());
		recipeList.add(new KanokaTimeRecipe());
		/*
		 * 1 - Reconstitution at Random
		 * 2 - Freezing
		 * 3 - Weakening
		 * 4 - Remove Poison
		 * 5 - Enlarging
		 * 6 - Shrinking
		 * 7 - Regeneration
		 * 8 - Teleportation 
		*/
		recipeList.add(new MaskRecipe(new ItemStack(NuiCraftItems.mask_mata_kakama), 1, 8));
		recipeList.add(new MaskRecipe(new ItemStack(NuiCraftItems.mask_mata_pakari), 4, 5, 7));
		recipeList.add(new MaskRecipe(new ItemStack(NuiCraftItems.mask_mata_kaukau), 4, 6, 7));
		recipeList.add(new MaskRecipe(new ItemStack(NuiCraftItems.mask_mata_miru),   3, 8));
		recipeList.add(new MaskRecipe(new ItemStack(NuiCraftItems.mask_mata_hau),    5, 8));
		recipeList.add(new MaskRecipe(new ItemStack(NuiCraftItems.mask_mata_akaku),  7, 8));
		
		recipeList.add(new MaskForgeRecipe(new ItemStack(NuiCraftItems.mask_vahi),
				new CountedIngredient(EnumGenericItem.KANOKA_TIME.getStack(1))));
		
		recipeList.add(new MaskForgeRecipe(EnumGenericItem.PROTOSTEEL_INGOT.getStack(1),
				new CountedIngredient(new OreIngredient("ingotProtodermis"), 2),
				new CountedIngredient(new OreIngredient("ingotIron"), 2),
				new CountedIngredient(Items.COAL, 2)));
		
		recipeList.add(new MaskForgeRecipe(new ItemStack(NuiCraftBlocks.lightstone, 4),
				new CountedIngredient(Items.GLOWSTONE_DUST, 1),
				new CountedIngredient(new OreIngredient("ingotProtodermis"), 1)));
	}

	public List<IMFRecipe> getRecipeList(){
		return recipeList;
	}
	
	public IMFRecipe getMatchingRecipe(NonNullList<ItemStack> inputItemStacks){
		//NuiCraft.logger.log(Level.INFO, "looking for recipe");
		if (inputItemStacks.isEmpty()) return null;
		NonNullList<ItemStack> input = NonNullList.create();
		Iterator<ItemStack> stacks;
		for (IMFRecipe recipe : recipeList){
			stacks = inputItemStacks.iterator();
			while (stacks.hasNext()) {
				input.add(stacks.next().copy());
			}
//			for(int i = 0; i < inputItemStacks.size(); i++){
//				if(!inputItemStacks.get(i).isEmpty()){
//					input.set(i, inputItemStacks.get(i).copy());
//				}
//			}
			if (recipe.matches(input)) return recipe;
			input.clear();
		}
		return null;
	}

//	public void addRecipe(ItemStack output, CountedIngredient... inputs){
//		Object[] input = new Object[inputs.length];
//		byte i = 0;
//		for(Object object : inputs){
//			if (object instanceof ItemStack){
//				input[i] = ((ItemStack)object).copy();
//				i++;
//			}else if(object instanceof Item){
//				input[i] = new ItemStack((Item)object);
//				i++;
//			}else if(object instanceof Block){
//				input[i] = new ItemStack((Block)object);
//				i++;            
//			}else{
//				/*if (!(object instanceof String)){
//					throw new IllegalArgumentException("Invalid shapeless recipe: unknown type " + object.getClass().getName() + "!");
//				}*/
//				input[i] = (String)object;
//				i++;
//			}
//		}
//		recipeList.add(new MaskForgeRecipe(output, input));
//	}	
//
//	public void addOreDictRecipe(Object output, Object... inputs){
//		//output is formatted like this: %02d%s % (count, oreDict)
//		for(Object input : inputs){
//			if(input instanceof String && !OreDictionary.doesOreNameExist(((String)input).substring(2))){
//				return;
//			}
//		}
//		if(output instanceof String){
//			List<ItemStack> outputVariations = OreDictionary.getOres(((String)output).substring(2));
//			if(!outputVariations.isEmpty()){
//				ItemStack output1 = outputVariations.get(0);
//				output1.setCount(Integer.parseInt(((String)output).substring(0, 2)));
//				System.out.println(output1);
//				addRecipe(output1, inputs);
//			}
//		}else if(output instanceof ItemStack){
//			addRecipe((ItemStack)output, inputs);
//		}else return;
//	}
}