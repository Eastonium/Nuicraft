package eastonium.nuicraft.machine.maskForge.recipe;

import java.util.List;

import com.google.common.collect.Lists;

import eastonium.nuicraft.NuiCraftBlocks;
import eastonium.nuicraft.NuiCraftItems;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;

public class MFRecipeManager {
	private static MFRecipeManager maskForgeRecipes = new MFRecipeManager();
	private List<IMFRecipe> recipeList = Lists.<IMFRecipe>newArrayList();

	public static MFRecipeManager getInstance(){
		return maskForgeRecipes;
	}

	private MFRecipeManager(){
		recipeList.add(new ColoredMetalMaskMFRecipe());
		recipeList.add(new KanokaMFRecipe());
		recipeList.add(new KanokaTimeMFRecipe());
		// 1 - Reconstitution at Random
		// 2 - Freezing
		// 3 - Weakening
		// 4 - Remove Poison
		// 5 - Enlarging
		// 6 - Shrinking
		// 7 - Regeneration
		// 8 - Teleportation
		recipeList.add(new MaskMFRecipe(new ItemStack(NuiCraftItems.mask_mata_kakama), new int[]{1, 8}));
		recipeList.add(new MaskMFRecipe(new ItemStack(NuiCraftItems.mask_mata_pakari), new int[]{4, 5, 7}));
		recipeList.add(new MaskMFRecipe(new ItemStack(NuiCraftItems.mask_mata_kaukau), new int[]{4, 6, 7}));
		recipeList.add(new MaskMFRecipe(new ItemStack(NuiCraftItems.mask_mata_miru), new int[]{3, 8}));
		recipeList.add(new MaskMFRecipe(new ItemStack(NuiCraftItems.mask_mata_hau), new int[]{5, 8}));
		recipeList.add(new MaskMFRecipe(new ItemStack(NuiCraftItems.mask_mata_akaku), new int[]{7, 8}));
		
		addRecipe(new ItemStack(NuiCraftItems.mask_vahi), new ItemStack(NuiCraftItems.kanoka_time));
		
		addOreDictRecipe(new ItemStack(NuiCraftItems.ingot_protosteel), new ItemStack(NuiCraftItems.ingot_protodermis, 8), "08ingotIron", "08coal");
		
		addRecipe(new ItemStack(NuiCraftBlocks.lightstone, 4), Items.GLOWSTONE_DUST, NuiCraftItems.ingot_protodermis);
	}

	public List<IMFRecipe> getRecipeList(){
		return recipeList;
	}

	public void addRecipe(ItemStack output, Object... inputs){
		Object[] input = new Object[inputs.length];
		byte i = 0;
		for(Object object : inputs){
			if (object instanceof ItemStack){
				input[i] = ((ItemStack)object).copy();
				i++;
			}else if(object instanceof Item){
				input[i] = new ItemStack((Item)object);
				i++;
			}else if(object instanceof Block){
				input[i] = new ItemStack((Block)object);
				i++;            
			}else{
				/*if (!(object instanceof String)){
					throw new IllegalArgumentException("Invalid shapeless recipe: unknown type " + object.getClass().getName() + "!");
				}*/
				input[i] = (String)object;
				i++;
			}
		}
		recipeList.add(new GenericMFRecipe(output, input));
	}	

	public void addOreDictRecipe(Object output, Object... inputs){
		//output is formatted something like this: 05ingotProtodermis <2digitStackSize><oredictName>
		for(Object input : inputs){
			if(input instanceof String && !OreDictionary.doesOreNameExist(((String)input).substring(2))){
				return;
			}
		}
		if(output instanceof String){
			List<ItemStack> outputVariations = OreDictionary.getOres(((String)output).substring(2));
			if(!outputVariations.isEmpty()){
				ItemStack output1 = outputVariations.get(0);
				output1.setCount(Integer.parseInt(((String)output).substring(0, 2)));
				System.out.println(output1);
				addRecipe(output1, inputs);
			}
		}else if(output instanceof ItemStack){
			addRecipe((ItemStack)output, inputs);
		}else return;
	}

	public IMFRecipe getMatchingRecipe(NonNullList<ItemStack> inputItemStacks){
		NonNullList<ItemStack> input = NonNullList.<ItemStack>withSize(inputItemStacks.size(), ItemStack.EMPTY);
		for (IMFRecipe recipe : recipeList){
			for(int i = 0; i < inputItemStacks.size(); i++){
				if(!inputItemStacks.get(i).isEmpty()){
					input.set(i, inputItemStacks.get(i).copy());
				}
			}
			if (recipe.matches(input)){
				return recipe;
			}
		}
		return null;
	}
}